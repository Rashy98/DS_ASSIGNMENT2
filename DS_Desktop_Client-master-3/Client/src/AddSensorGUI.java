import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class AddSensorGUI {

	private JFrame frame;
	private JTextField roomNo;
	private JTextField floorNo;
	private ClientMain clientMain = new ClientMain();
	/**
	 * Launch the application.
	 */
	public static void AddSensor() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddSensorGUI window = new AddSensorGUI();
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
	public AddSensorGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		roomNo = new JTextField();
		roomNo.setColumns(10);
		
		floorNo = new JTextField();
		floorNo.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Floor Number");
		
		JLabel lblNewLabel_1 = new JLabel("Room Number");
		
		//action on add sensor button pushed
		JButton btnAddSensor = new JButton("Add");
		btnAddSensor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				clientMain.AddSensor(roomNo.getText(), floorNo.getText());
				frame.dispose();
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(78)
					.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(66)
					.addComponent(roomNo, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(86))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(78)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
					.addGap(66)
					.addComponent(floorNo)
					.addGap(86))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(148)
					.addComponent(btnAddSensor, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(185))
		);
		
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(97)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(5))
						.addComponent(roomNo))
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(5))
						.addComponent(floorNo))
					.addGap(38)
					.addComponent(btnAddSensor, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(32))
		);
		
		frame.getContentPane().setLayout(groupLayout);
	}
}
