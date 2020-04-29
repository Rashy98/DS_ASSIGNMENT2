import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.Timer;

import com.model.FloorDetails;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;

public class AdminGUI extends JFrame{

	private JFrame frame;
	private ClientMain clientMain = new ClientMain();
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void AdminGUI() {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI window = new AdminGUI();
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
	public AdminGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //executing the table
	    table = executeTable();
	    frame.getContentPane().add(table.getTableHeader(), BorderLayout.PAGE_START);
	    frame.getContentPane().add(table, BorderLayout.CENTER);
	    
	    JSplitPane splitPane = new JSplitPane();
	    splitPane.setResizeWeight(0.5);
	    frame.getContentPane().add(splitPane, BorderLayout.SOUTH);
	    
	    //action on add sensor button pushed
	    JButton btnAddSensor = new JButton("Add Sensor");
	    btnAddSensor.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		AddSensorGUI.AddSensor();
	    	}
	    });
	    splitPane.setLeftComponent(btnAddSensor);
	    
	    //action on update button pushed
	    JButton btnUpdateRow = new JButton("Update Row");
	    btnUpdateRow.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		int selectedRowIndex = table.getSelectedRow();
	    		
	    		HashMap<String, String> row = new HashMap<String, String>();
	    		
	    		row.put("id", table.getModel().getValueAt(selectedRowIndex, 0).toString());
	    		row.put("roomNo", table.getModel().getValueAt(selectedRowIndex, 1).toString());
	    		row.put("floorNo", table.getModel().getValueAt(selectedRowIndex, 2).toString());
	    		row.put("co2Level", table.getModel().getValueAt(selectedRowIndex, 3).toString());
	    		row.put("smokeLevel", table.getModel().getValueAt(selectedRowIndex, 4).toString());
	    		row.put("status", table.getModel().getValueAt(selectedRowIndex, 5).toString());
	    		
	    		//passing the values 
	    		clientMain.EditSensor(row);
	    		
	    	}
	    });
	    splitPane.setRightComponent(btnUpdateRow);
	    
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setTitle("Sensor Details");
	    //calling the refresh timer to refresh the table
	    refreshTimer();
		
	}
	
	 public JTable executeTable(  ) {
		 //table columns
		    Object[] columns = new String[] {
		            "ID", "Room No", "Floor No", "CO2 Level", "Smoke Level", "Status"
		    };

		    //getting values for the table
		    ArrayList<FloorDetails> arrayList = clientMain.getSensors();

		    Object[][] data = new Object[arrayList.size()][6];

		    //assigning values for the 2D array
		    for(int i = 0; i < arrayList.size(); i++) {
		        data[i][0] = arrayList.get(i).getId();
		        data[i][1] = arrayList.get(i).getRoomNo();
		        data[i][2] = arrayList.get(i).getFloorNo();
		        data[i][3] = arrayList.get(i).getCo2Level();
		        data[i][4] = arrayList.get(i).getSmokeLevel();
		        data[i][5] = arrayList.get(i).getStatus();
		    }

		    table = new JTable(data,columns) {

		    	private static final int CO2_COL = 3;
		    	
		    	///defining the editable cells
				@Override
				public boolean isCellEditable(int row, int column) {
					// TODO Auto-generated method stub
					return column == 0 || column == 1 || column == 2 || column == 5 ? true : false;
				}
				
				/**
                 * checking the co2 and smoke level
                 * if greater than 5, mark the row in red colour
                 */
				@Override
	            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
					
	                Component component = super.prepareRenderer(renderer, row, col);
	                int co2Level = (int) getValueAt(row, CO2_COL);
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
		 Timer timer = new Timer(0, new ActionListener() {

			   @Override
			   public void actionPerformed(ActionEvent e) {
				   
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
				   
			   }
			});

			timer.setDelay(30000); // delay for 30 seconds
			timer.start();
	 }
	 
}
