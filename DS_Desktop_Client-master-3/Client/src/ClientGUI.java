import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import com.model.FloorDetails;

public class ClientGUI {

	public static JFrame frame;
	private ClientMain clientMain = new ClientMain();
	private JTable table;
	private Timer timer;
	ArrayList<FloorDetails> initialAlertList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
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
	public ClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //setting the table
	    table = executeTable();
	    frame.getContentPane().add(table.getTableHeader(), BorderLayout.PAGE_START);
	    frame.getContentPane().add(table, BorderLayout.CENTER);
	    
	    //action on login button push
	    JButton btnLogin = new JButton("Login");
	    btnLogin.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		timer.stop();
	    		LoginGUI.Login();
	    	}
	    });
	    frame.getContentPane().add(btnLogin, BorderLayout.SOUTH);
	    
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setTitle("Sensor Details");
	    refreshTimer();
	}
	
	//creating the table
	public JTable executeTable(  ) {
		
		initialAlertList = new ArrayList<FloorDetails>();
		
		//table columns
	    Object[] columns = new String[] {
	            "ID", "Room No", "Floor No", "CO2 Level", "Smoke Level", "Status"
	    };

	    //getting table values by calling getSensors() method
	    ArrayList<FloorDetails> arrayList = clientMain.getSensors();

	    Object[][] data = new Object[arrayList.size()][6];

	    //setting the values to 2D array
	    for(int i = 0; i < arrayList.size(); i++) {
	        data[i][0] = arrayList.get(i).getId();
	        data[i][1] = arrayList.get(i).getRoomNo();
	        data[i][2] = arrayList.get(i).getFloorNo();
	        data[i][3] = arrayList.get(i).getCo2Level();
	        data[i][4] = arrayList.get(i).getSmokeLevel();
	        data[i][5] = arrayList.get(i).getStatus();
	        
	        if( (arrayList.get(i).getCo2Level() >= 5) || (arrayList.get(i).getSmokeLevel() >= 5)) {
	        	initialAlertList.add(arrayList.get(i));
	        }	     
	    }
	    
        //sending alert message body	
		clientMain.sendInitialAlert(initialAlertList);

	    //creating the table
	    table = new JTable(data,columns) {
	    	
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				//return column == 0 || column == 3 || column == 4 || column == 5 ? true : false;
				return false;
			}
			
			@Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				
                Component component = super.prepareRenderer(renderer, row, col);
                int co2Level = (int) getValueAt(row, 3);
                int smokeLevel = (int) getValueAt(row, 4);
                
                /**
                 * checking the co2 and smoke level
                 * if greater than 5, mark the row in red colour
                 */
                if ( (co2Level >= 5) || (smokeLevel >= 5) ) {
                	
                    component.setBackground(Color.RED);
                    component.setForeground(Color.BLACK);
                    
                } else {
                	
                    component.setBackground(super.getBackground());
                    component.setForeground(super.getForeground());
                }
                return component;
            }
	    	
	    };
	    
	    frame.setTitle("Sensor Details");
	    return table;
	}
 
	//timer function to refresh in every 5 seconds
	public void refreshTimer() {
	 
		timer = new Timer(0, new ActionListener() {
		 
		@Override
		public void actionPerformed(ActionEvent e) {
			   
			ArrayList<FloorDetails> newAlertList = new ArrayList<FloorDetails>();
			   
			Object[] columns = new String[] {
					"ID", "Room No", "Floor No", "CO2 Level", "Smoke Level", "Status"
			};
			   
			ArrayList<FloorDetails> arrayList = clientMain.getSensors();

			Object[][] data = new Object[arrayList.size()][6];

			for(int i = 0; i < arrayList.size(); i++) {
				data[i][0] = arrayList.get(i).getId();
				data[i][1] = arrayList.get(i).getRoomNo();
				data[i][2] = arrayList.get(i).getFloorNo();
				data[i][3] = arrayList.get(i).getCo2Level();
				data[i][4] = arrayList.get(i).getSmokeLevel();
				data[i][5] = arrayList.get(i).getStatus();
			  
			}
			
			clientMain.sendNewAlert();
			    
			TableModel tableModel = new DefaultTableModel(data, columns);
		      
			table.setDefaultRenderer(Object.class, (TableCellRenderer) new DefaultTableCellRenderer(){
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		                
					super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
						return this;
					}   
			});
		        
		  
			table.removeAll();
			table.setModel(tableModel);
			System.out.println("REFRESHED");
			   
		}});

		timer.setDelay(30000); // delay for 30 seconds
		timer.start();
	}
}
