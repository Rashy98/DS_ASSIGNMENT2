import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginGUI {

	private JFrame frame;
	private JTextField textUserName;
	private JTextField textPassword;

	/**
	 * Launch the application.
	 */
	public static void Login() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI window = new LoginGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textUserName = new JTextField();
		textUserName.setBounds(107, 88, 247, 26);
		frame.getContentPane().add(textUserName);
		textUserName.setColumns(10);
		
		textPassword = new JTextField();
		textPassword.setColumns(10);
		textPassword.setBounds(107, 162, 247, 26);
		frame.getContentPane().add(textPassword);
		
		JLabel lblNewLabel = new JLabel("Username :");
		lblNewLabel.setBounds(107, 60, 75, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password :");
		lblNewLabel_1.setBounds(107, 134, 75, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		//action on login button pushed
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username = textUserName.getText();
				String password = textPassword.getText();
				
				if(username.equals("admin") && password.equals("admin")) {
					AdminGUI.AdminGUI();
					frame.dispose();
					ClientGUI.frame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Invalid Credentials");
					textPassword.setText("");
					textUserName.setText("");
				}
			}
		});
		btnLogin.setBounds(178, 214, 117, 29);
		frame.getContentPane().add(btnLogin);
	}
}
