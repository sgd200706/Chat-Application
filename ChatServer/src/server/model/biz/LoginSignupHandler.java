package server.model.biz;

import java.util.Iterator;
import java.util.Map.Entry;

import chat.model.entity.Message;
import server.common.CommonData;
import server.controller.ServerThread;
import server.model.Room;



public class LoginSignupHandler {
	private static final LoginSignupHandler loginSignupHandler = new LoginSignupHandler();

	public LoginSignupHandler()
	{

	}

	/**
	 * return LoingHanler instance
	 */
	public static LoginSignupHandler getLoginSignupHandler()
	{
		return LoginSignupHandler.loginSignupHandler;
	}

	/**
	 * handle client login
	 * 
	 * @param msg
	 * @param serverThread
	 */
	public void clientLoginHandle(Message msg, ServerThread serverThread)
	{

		String username = msg.sender;
		String password = msg.content;
		//user is not online
		if(findSeverThread(username) == null){

			//check database
			if(CommonData.getDatabase().checkLogin(username, password)){

				//set the client name of this server thread
				serverThread.setCLientName(username)
				;
				// add this client to online user list
				CommonData.getClientThreadMap().put(username, serverThread);

				//send back login result
				serverThread.send(new Message("login", "SERVER", "TRUE", username));
				System.out.println("Login succeed, initializing... ");

				//send the room list
				Iterator<Room> roomListIterator = CommonData.getRoomList().iterator();
				serverThread.send(new Message("roomNo","SEVER", Integer.toString(CommonData.getRoomList().size()),username));
				while(roomListIterator.hasNext()){  
					Room room= roomListIterator.next();
					serverThread.send(new Message("newroom","SEVER",room.getRoomName(),username));
				}  

			}
			else{
				serverThread.send(new Message("login", "SERVER", "FALSE", msg.sender));

			} 
		}
		else{
			serverThread.send(new Message("login", "SERVER", "FALSE", msg.sender));
			System.out.println("The same user is online, Logon failed !!");
		}

	}
	/**
	 * handle client sign up
	 * 
	 * @param msg
	 * @param serverThread
	 */
	public void clientSignupHandle(Message msg, ServerThread serverThread)
	{

		String username = msg.sender;
		String password = msg.content;
		//user is not online
		if(findSeverThread(username) == null){
			if(!CommonData.getDatabase().userExists(username)){
				CommonData.getDatabase().addUser(username, password);
				//send back result
				serverThread.send(new Message("signup", "SERVER", "TRUE", username));

				System.out.println("Sign up succeed, waiting for login");

			}
			else{
				serverThread.send(new Message("signup", "SERVER", "FALSE", msg.sender));
			}
		}
		else{
			serverThread.send(new Message("signup", "SERVER", "FALSE", msg.sender));
		}

	}

	/**
	 * handle client off-line
	 * 
	 * @param clientName
	 * @param serverThread
	 */
	public void clientOffLineHandle(String clientName, ServerThread serverThread)
	{
		System.out.println(clientName+" off-line");

		// remove this client from online user list
		CommonData.getClientThreadMap().remove(clientName);

		//remove this client from all the rooms
		Iterator<Room> roomListIterator = CommonData.getRoomList().iterator(); 
		while(roomListIterator.hasNext()){  
			Room room= roomListIterator.next();
			room.removeClient(clientName);
		}  
		// Construct the message
		Message sendMsg = new Message("useroffline","SERVER",clientName,"ALL");

		//broadcast the user offline
		serverThread.broadcast(sendMsg);
	}

	public void enterRoomHandle(Message msg, ServerThread serverThread){
		String clientName = msg.sender;
		String roomName = msg.content;
		//add the client to the room

		Iterator<Room> roomListIterator = CommonData.getRoomList().iterator(); 
		while(roomListIterator.hasNext()){  
			Room room= roomListIterator.next();
			//find the room
			if (roomName.equals(room.getRoomName())){

				//add the client to the room
				room.addClient(clientName);

				//send the client list of this room
				String foundName = "";
				Iterator<String> clientListIterator = room.getClients().iterator(); 
				while(clientListIterator.hasNext()){  
					//					name += "," + clientListIterator.next(); //name of clients in this room
					foundName = clientListIterator.next();
					Message sendMsg = new Message("newuser",roomName,foundName,clientName);
					serverThread.send(sendMsg);
					//inform the other user 
					if(!foundName.equals(clientName)){
						findSeverThread(foundName).send(new Message("newuser",roomName,clientName,foundName));
					}
				}
				//               Message sendMsg = new Message("newuser",roomName,name,clientName);
				//                serverThread.send(sendMsg);

			}  

		}	
	}

	/**
	 * find the server thread of a given client name
	 * @param clientName
	 * @return
	 */
	private ServerThread findSeverThread(String clientName ){
		Iterator<Entry<String, ServerThread>> iter = CommonData.getClientThreadMap().entrySet().iterator();
		while (iter.hasNext())
		{
			Entry<String, ServerThread> entry = (Entry<String, ServerThread>) iter.next();

			// if the same name exists
			if (entry.getKey().toString().equals(clientName))
			{
				return entry.getValue();
			}
		}
		return null;

	}
}
