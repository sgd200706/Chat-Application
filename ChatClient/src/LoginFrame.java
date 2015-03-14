import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;


public class LoginFrame extends JFrame {

	private JPanel contentPane;
	public JTextField txtUsername;
	public JTextField txtPassword;
	public JButton btnLogIn;
	public JButton btnSignUp;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(73, 44, 54, 15);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(73, 112, 54, 15);
		contentPane.add(lblPassword);
		
		btnLogIn = new JButton("Log in");
		btnLogIn.setBounds(73, 200, 93, 23);
		contentPane.add(btnLogIn);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(137, 41, 159, 21);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(137, 109, 159, 21);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		btnSignUp = new JButton("Sign up");
		btnSignUp.setBounds(203, 200, 93, 23);
		contentPane.add(btnSignUp);
	}

}
