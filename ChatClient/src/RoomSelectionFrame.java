import chat.model.entity.Message;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class RoomSelectionFrame extends JFrame {

	private JPanel contentPane;
    private Client client;
	public DefaultListModel model;
    JList listRoom = new JList();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RoomSelectionFrame frame = new RoomSelectionFrame("Main page");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RoomSelectionFrame(String name) {

        super(name);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 330, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		

		listRoom.setModel(model = new DefaultListModel());
		//model.addElement("default room");
		scrollPane.setViewportView(listRoom);
		
		JButton btnJoin = new JButton("Join");
		contentPane.add(btnJoin, BorderLayout.SOUTH);

        btnJoin.addActionListener(new ActionHandler());
	}
    public void setClientCon(Client cli) {

        client = cli;
    }
    class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {


            if(client != null){

                Message msg = new Message("selectroom", client.username, listRoom.getSelectedValue().toString(),"SERVER");

                client.send(msg);
                for(int i = 0; i < client.roomNo; i++){
                    if(listRoom.getSelectedValue().toString().equals(client.room_name.get(i))) {
//                        client.cfs.get(i).setChatFrame(client);
                        client.cfs.get(i).setVisible(true);

                    }
                }

            }

        }
    }
}
