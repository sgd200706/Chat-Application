import chat.model.entity.Message;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;


public class Client {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	public String username;
    private String users;
    public ArrayList<String> room_name = new ArrayList<String>();
    public ArrayList<ChatFrame>  cfs = new ArrayList<ChatFrame>();


	String password;
	String serverAddress = "localhost";
    int roomNo = 0;


	private LoginFrame loginFrame = new LoginFrame();
	private RoomSelectionFrame roomSelectionFrame = new RoomSelectionFrame("Main Page");

    public Client(){


		loginFrame.btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				username = loginFrame.txtUsername.getText().trim();
				password = loginFrame.txtPassword.getText();

				if(!username.isEmpty() && !password.isEmpty()){
					send(new Message("login", username, password, "SERVER"));
				}

			}
		});
		loginFrame.btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username = loginFrame.txtUsername.getText().trim();
				password = loginFrame.txtPassword.getText();

				if(!username.isEmpty() && !password.isEmpty()){
					send(new Message("signup", username, password, "SERVER"));
				}

			}
		});
	}

	private void run() throws IOException {

		// Make connection and initialize streams

	
		Socket socket = new Socket(serverAddress, 8888);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());

		// Process all messages from server, according to the protocol.
		while (true) {

			Message msg;
			try {
				msg = (Message) in.readObject();
				System.out.println("Incoming(in client class): "+msg.toString());

				//message
				if(msg.type.equals("message")) {
                    for(int i=0; i< cfs.size();i++){
                        if(msg.sender.equals(cfs.get(i).chatroomID)){
                            cfs.get(i).msgModel.addElement(msg.content);
                        }
                    }


                } else if(msg.type.equals("login")){

                    if(msg.content.equals("TRUE")){
						//login success
						loginFrame.setVisible(false);
						roomSelectionFrame.setVisible(true);


					}
					else{
						JOptionPane.showMessageDialog(null, "Login failed");
					}
				}
				else if(msg.type.equals("test")){

				}
				else if(msg.type.equals("newroom")){

                    roomSelectionFrame.model.addElement(msg.content);
                    room_name.add(msg.content);
                    genCharFrame(msg.content);


                }
                else if(msg.type.equals("roomNo")){

                  roomNo = Integer.parseInt(msg.content);

                }
                else if(msg.type.equals("newuser")){
                    //System.out.println("User List : "+msg.toString());
//                    users = msg.content;
                	for(int i=0; i< cfs.size();i++){
                        if(msg.sender.equals(cfs.get(i).chatroomID)){
                            cfs.get(i).userModel.addElement(msg.content);
                        }
                    }

                }
                else if(msg.type.equals("useroffline")){
                	for(int i=0; i< cfs.size();i++){ 
                        cfs.get(i).userModel.removeElement(msg.content);  
                    }
                }

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		}
	}
    public String getUserList(){
           return users;

    }


    public void send(Message msg){
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("Outgoing : "+msg.toString());
		} 
		catch (IOException ex) {
			System.out.println("Exception SocketClient send()");
		}
	}

    public void genCharFrame(String chat){
    	ChatFrame cf = new ChatFrame(chat);
    	cf.setChatFrame(this);
        cfs.add(cf);
        if(cfs.size() == roomNo){
            System.out.println("room creation done!");
        }


    }
	public static void main(String[] args) throws Exception {
		Client client = new Client();
		client.loginFrame.setVisible(true);
        client.roomSelectionFrame.setClientCon(client);

        System.out.println("login user:" + client.username);

        client.run();




	}
}
