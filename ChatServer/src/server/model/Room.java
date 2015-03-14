package server.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Room {
	private String roomName;
	private ArrayList<String> clients;


	public Room(String roomName){
		this.roomName = roomName;
		clients = new ArrayList<String>();
	}

	public String getRoomName(){
		return roomName;
	}

	public ArrayList<String> getClients(){
		return clients;
	}

	public void addClient(String client){
		clients.add(client);
	}

	public void removeClient(String client){
		Iterator<String> sListIterator = clients.iterator();  
		while(sListIterator.hasNext()){  
			String e = sListIterator.next();  
			if(e.equals(client)){  
				sListIterator.remove();  
			}  
		}  
	}
}
