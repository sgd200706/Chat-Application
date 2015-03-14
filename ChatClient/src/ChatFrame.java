import chat.model.entity.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lightlycat on 10/9/14.
 */
public class ChatFrame extends JFrame {

    private JPanel msgPanel, writingPanel;
    private Client client;
    private JScrollPane scrollPane, userPane;
    public DefaultListModel msgModel,userModel;
    public String chatroomID;
    JList msgList = new JList();
    JList userList = new JList();
    JTextField input ;
    JButton btnSend;

    public ChatFrame(String chatroom){
        chatroomID = chatroom;
    }
    public void setChatFrame(Client cli) {

        client = cli;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(360, 100, 330, 481);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout(5, 5));

        //msg area
        scrollPane = new JScrollPane();
        msgList.setModel(msgModel = new DefaultListModel());

        if(client != null){


            System.out.println("try to show message " );




        }else{
            System.out.println("No receive massage in ChatFrame");
        }  
        
        scrollPane.setViewportView(msgList);
        //user list
        //TextArea ta = new TextArea("Participators: ", 15,10);
        userPane  = new JScrollPane();
        userList.setModel(userModel = new DefaultListModel());
       
        
        if(client != null){

//            String[] users = client.getUserList().split(",");
//            for(String user: users){
//                userModel.addElement(user);
//            }
         

        }else{
            System.out.println("No client instance in ChatFrame");
        }

        userPane.setViewportView(userList);

        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userPane ,scrollPane);

        panel.add(jSplitPane);
        //writing area
        writingPanel = new JPanel();
        input = new JTextField(20);
        btnSend = new JButton("Send Message");
        writingPanel.add(input);
        writingPanel.add(btnSend);

        //component and it's Layout

        add(panel, BorderLayout.NORTH);
        add(writingPanel, BorderLayout.SOUTH);
        setTitle(chatroomID);
        //setSize(500,400);

        pack();

        btnSend.addActionListener(new ActionHandler());
    }
    class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //to do
            // send messge to server  ; MD5
            // show message on the screen
            if(e.getSource() == btnSend ){
                    // output.setText(input.getText());
                //msgModel.addElement(input.getText());

                if(client != null) {
                    Message msg = new Message("message", client.username, input.getText(), chatroomID);
                    client.send(msg);

                }
                input.setText("");
            }
        }
    }
    public void updateUserList(String userlist){
    	 if(client != null){

             String[] users = client.getUserList().split(",");
             for(String user: users){
                 userModel.addElement(user);
             }
             userPane.setViewportView(userList);

         }else{
             System.out.println("No client instance in ChatFrame");
         }
    }

}
