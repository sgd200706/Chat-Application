package server.model.biz;

import java.util.Iterator;
import java.util.Map.Entry;

import chat.model.entity.Message;
import server.common.CommonData;
import server.controller.ServerThread;
import server.model.Room;



public class ChatHandler {
	private static final ChatHandler chatHandler = new ChatHandler();

	private ChatHandler()
	{

	}

	/**
	 * return the chathandler instance
	 * 
	 * @return
	 */
	public static ChatHandler getChatHandler()
	{
		return ChatHandler.chatHandler;
	}

	/**
	 * handle room chat
	 * 
	 * @param msgS_
	 */
	public void roomChatHandle(Message msg)
	{

		String fromUserName = msg.sender;
		String roomName = msg.recipient;
		String content = msg.content;

		//find the room
		Iterator<Room> roomListIterator = CommonData.getRoomList().iterator(); 
		while(roomListIterator.hasNext()){  
			Room room= roomListIterator.next();
			if (roomName.equals(room.getRoomName())){
				Iterator<String> clientListIterator = room.getClients().iterator(); 
				while(clientListIterator.hasNext()){  
					String name= clientListIterator.next(); //name of clients in this room
					Message sendMsg = new Message("message", roomName, fromUserName+" : "+content, name);//send the message to the user in this room
					findSeverThread(name).send(sendMsg);

					System.out.println(roomName + fromUserName + " say: " + " : " + content);
				}
			}
		}

	}  




	/**
	 * handle single chat
	 * 
	 * @param msgStr_
	 */
	public void singleChatHandle(String msgStr_)
	{

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
