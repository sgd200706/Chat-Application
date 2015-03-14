package server.common;

import java.util.ArrayList;
import java.util.HashMap;

import server.controller.ServerThread;
import server.model.Database;
import server.model.Room;

/************************************************
 this class holds common data on the server side
 
 *************************************************/ 

public class CommonData {
	
	private static HashMap<String, ServerThread>  clientThreadMap;  //holds all the client thread connection
	private static ArrayList<Room>  roomList;
	private static int listenPort;                                  //listening port
	private static Database db;
	
	static{
		clientThreadMap = new HashMap<String, ServerThread>();
		roomList = new ArrayList<Room>();
		listenPort = 8888;
		db = new Database("Data.xml");
	}
	
	public static HashMap<String, ServerThread> getClientThreadMap(){
		return CommonData.clientThreadMap;
	}
	
	public static void setServerIp(HashMap<String, ServerThread> clientThreadMap){
		CommonData.clientThreadMap = clientThreadMap;
	}
	
	public static ArrayList<Room> getRoomList(){
		return CommonData.roomList;
	}
	
	public static Database getDatabase(){
		return CommonData.db;
	}
	
	public static int getListenPort(){
		return CommonData.listenPort;
	}
	public static void setListenPort(int listenPort){
		CommonData.listenPort = listenPort;
	}
}
