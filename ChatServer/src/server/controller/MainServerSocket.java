package server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.common.CommonData;


public class MainServerSocket {
	 private static final MainServerSocket mainSocket = new MainServerSocket();
	    
	    // listening to socket
	    private static ServerSocket serverSocket;
	    
	    // client Socket
	    private static Socket clientSocket;
	    
	    // Whether to continue listening
	    private boolean isListen = true; 
	    
	    private MainServerSocket()
	    {
	        try
	        {
	            serverSocket = new ServerSocket(CommonData.getListenPort());
	            
	        } catch(IOException e)
	        {
	            e.printStackTrace();
	        }
	    }
	    
	    /**
	     * get main socket instance
	     */
	    public static MainServerSocket getMainSocket()
	    {
	        return MainServerSocket.mainSocket;
	    }
	    
	    /**
	     * start listening
	     */
	    public void beginListen()
	    {
	        try
	        {
	            while(isListen)
	            {
	                System.out.println("listening...");
	                
	                // listening
	                clientSocket = serverSocket.accept();
	                
	                System.out.println("one client connect");
	                
	                //After accept the client connection, start a new sever thread
	                new ServerThread(clientSocket).start();
	            }
	            
	        } catch(IOException e)
	        {
	            e.printStackTrace();
	        }
	    }
}
