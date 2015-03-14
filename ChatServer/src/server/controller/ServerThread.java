package server.controller;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Map.Entry;

import chat.model.entity.Message;
import server.common.CommonData;
import server.model.biz.ChatHandler;
import server.model.biz.LoginSignupHandler;


public class ServerThread extends Thread{
	private Socket socket = null;
	private ObjectInputStream  in  = null;
	private ObjectOutputStream out = null;
	private boolean isListen = false;
	private String clientName = "";   // client name of this thread

	public ServerThread(Socket socket)
	{
		try
		{
			this.socket = socket;

			// initialize in and out stream
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());

		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * broadcast message to all other clients
	 */
	public void broadcast(Message msg)
	{
		// traverse the whole user list
		Iterator<Entry<String, ServerThread>> iter = CommonData.getClientThreadMap().entrySet().iterator();

		while (iter.hasNext())
		{
			Entry<String, ServerThread> entry = (Entry<String, ServerThread>) iter.next();
			ServerThread clientThread = (ServerThread) entry.getValue();
			if (clientThread != this)
			{
				clientThread.send(msg);
			}
			// String key = entry.getKey().toString();
		}
	}

	public void setCLientName(String name){
		this.clientName = name;
	}



	/**
	 * send message to this client
	 * @param msg
	 */
	public void send(Message msg)
	{
		try
		{
			out.writeObject(msg);
			out.flush();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * get socket of this thread instance
	 * @return
	 */
	public Socket getSocket()
	{
		return this.socket;
	}

	/**
	 * Whether to continue listening
	 * @param value
	 */
	public void setListen(boolean value)
	{
		this.isListen = value;
	}

	@Override
	public void run()
	{
		try
		{
			Message msg;
			// whether to receive message
			isListen = true;

			while (isListen)
			{
				// Acquire message
				msg = (Message) in.readObject();


				// get the message type
				MessageTypeEnum msgType = MessageTypeEnum.valueOf(msg.type);

				switch (msgType)
				{
				case login:
				{
					// get client name
					clientName = msg.sender;

					// hand to the handler
					LoginSignupHandler.getLoginSignupHandler().clientLoginHandle(msg, this);

					break;
				}
				case signup:
				{
					LoginSignupHandler.getLoginSignupHandler().clientSignupHandle(msg, this);
				}
				case message:
				{
					// hand to the handler 
					ChatHandler.getChatHandler().roomChatHandle(msg);

					break;
				}
				case selectroom:
				{
					// hand to the handler
					LoginSignupHandler.getLoginSignupHandler().enterRoomHandle(msg, this);
                    System.out.println("handler selectroom");
                    break;
				}

				default:
					break;
				}
			}

		} catch(SocketException e)
		{
			try
			{
				// the client offline
				LoginSignupHandler.getLoginSignupHandler().clientOffLineHandle(clientName, this);

				// close the socket
				out.flush();
				out.close();
				in.close();
				socket.close();

			} catch(IOException e1)
			{
				e1.printStackTrace();
			}

		} catch(IOException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}