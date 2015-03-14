package server.main;
import server.common.CommonData;
import server.controller.MainServerSocket;
import server.model.Room;

public class ServerStart{
	
	//specify how many rooms
	public static int rooms = 5;

	public static void main(String[] args) throws Exception {
		for(int i =0; i< rooms;i++){
			CommonData.getRoomList().add(new Room("ChatRoom"+i));
		}
		MainServerSocket.getMainSocket().beginListen();
	}
}



