package CS4400Final;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.mysql.cj.xdevapi.Result;
import com.mysql.cj.xdevapi.Statement;

import java.awt.*;
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.LinkedList;

public class manageUser extends JFrame {

    private JLabel label1;
    private JLabel label2;
    private JLabel label3;

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    
    private JButton upUserButton;
    private JButton downUserButton;
    private JButton upCardCountButton;
    private JButton downCardCountButton;
    private JButton upUserTypeButton;
    private JButton downUserTypeButton;
    private JButton upUserStatusButton;
    private JButton downUserStatusButton;
    
    private JTextField field1;

    java.util.List<String> statusList;
    JComboBox<String> comboBox1;

    private JTable jTable;
    DefaultTableModel model;
    private JScrollPane jscrollpane;

    protected String username;
    protected String usertype;

    private String sortBy="";
    private String sortDirection="";
    private String sortStatus="ALL";

    private int mark = 0;

    // Contain state of checkBox
    protected List<Boolean>checkList;
    
    public manageUser(){
        init();
        addComponent();
        addListener();
    }

    private void init(){
        this.setSize(500,450);
        this.setVisible(true);
        this.setTitle("Manage User");
        this.setLayout(null);
        this.setLocation(500, 200);
    }

    private void addComponent(){
        label1 = new JLabel("Manage User");
        label1.setSize(400,200);
        label1.setFont(new Font("Dialog",Font.BOLD, 25));
        label1.setLocation(160,-60);
        this.add(label1);

        label2 = new JLabel("Username");
        label2.setSize(80,30);
        label1.setFont(new Font("Dialog",Font.BOLD, 25));
        label2.setLocation(40,80);
        this.add(label2);

        label3 = new JLabel("Status");
        label3.setSize(80,30);
        label1.setFont(new Font("Dialog",Font.BOLD, 25));
        label3.setLocation(260,80);
        this.add(label3);

        field1 = new JTextField();
        field1.setSize(100,30);
        field1.setLocation(110,80);
        this.add(field1);

        button1 = new JButton("Filter");
        button1.setSize(100,30);
        button1.setLocation(40,125);
        button1.setBackground(Color.white);
        button1.setFont(new Font("Dialog",Font.BOLD, 12));
        this.add(button1);

        button2 = new JButton("Approve");
        button2.setSize(100,30);
        button2.setLocation(220,125);
        button2.setBackground(Color.white);
        button2.setFont(new Font("Dialog",Font.BOLD, 12));
        this.add(button2);

        button3 = new JButton("Decline");
        button3.setSize(100,30);
        button3.setLocation(360,125);
        button3.setBackground(Color.white);
        button3.setFont(new Font("Dialog",Font.BOLD, 12));
        this.add(button3);

        button4 = new JButton("Back");
        button4.setSize(100,30);
        button4.setLocation(180,370);
        button4.setBackground(Color.white);
        button4.setFont(new Font("Dialog",Font.BOLD, 12));
        this.add(button4);
        
        upUserButton =new JButton("A");
        upUserButton.setSize(40,30);
        upUserButton.setLocation(108,170);
        upUserButton.setBackground(Color.white);
        upUserButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(upUserButton);
        
        downUserButton =new JButton("D");
        downUserButton.setSize(40,30);
        downUserButton.setLocation(157,170);
        downUserButton.setBackground(Color.white);
        downUserButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(downUserButton);
        
        upCardCountButton =new JButton("A");
        upCardCountButton.setSize(40,30);
        upCardCountButton.setLocation(197,170);
        upCardCountButton.setBackground(Color.white);
        upCardCountButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(upCardCountButton);
        
        downCardCountButton =new JButton("D");
        downCardCountButton.setSize(40,30);
        downCardCountButton.setLocation(245,170);
        downCardCountButton.setBackground(Color.white);
        downCardCountButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(downCardCountButton);
        
        upUserTypeButton =new JButton("A");
        upUserTypeButton.setSize(40,30);
        upUserTypeButton.setLocation(285,170);
        upUserTypeButton.setBackground(Color.white);
        upUserTypeButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(upUserTypeButton);
        
        downUserTypeButton =new JButton("D");
        downUserTypeButton.setSize(40,30);
        downUserTypeButton.setLocation(334,170);
        downUserTypeButton.setBackground(Color.white);
        downUserTypeButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(downUserTypeButton);
        
        upUserStatusButton =new JButton("A");
        upUserStatusButton.setSize(40,30);
        upUserStatusButton.setLocation(374,170);
        upUserStatusButton.setBackground(Color.white);
        upUserStatusButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(upUserStatusButton);
        
        downUserStatusButton =new JButton("D");
        downUserStatusButton.setSize(40,30);
        downUserStatusButton.setLocation(423,170);
        downUserStatusButton.setBackground(Color.white);
        downUserStatusButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(downUserStatusButton);

        statusList = new java.util.ArrayList<String>(Arrays.asList(new String[] {
                "ALL", "Pending","Declined", "Approved"}));
        comboBox1 = new JComboBox<String>();
        for(String status : statusList){
            comboBox1.addItem(status);
        }
        comboBox1.setLocation(310,80);
        comboBox1.setSize(100,30);
        this.add(comboBox1);

        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void addListener(){
    	// Click upUserButton
        upUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="ASC";
            	sortBy="username";
            }
        });
        
    	// Click downUserButton
        downUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="DESC";
            	sortBy="username";
            }
        });
        
    	// Click upCardCountButton
        upCardCountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="ASC";
            	sortBy="creditCardCount";
            }
        });
        
    	// Click downCardCountButton
        downCardCountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="DESC";
            	sortBy="creditCardCount";
            }
        });
        
    	// Click upUserTypeButton
        upUserTypeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="ASC";
            	sortBy="userType";
            }
        });
        
    	// Click downUserTypeButton
        downUserTypeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="DESC";
            	sortBy="userType";
            }
        });
        
    	// Click upUserStatusButton
        upUserStatusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="ASC";
            	sortBy="status";
            }
        });
        
    	// Click downUserStatusButton
        downUserStatusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="DESC";
            	sortBy="status";
            }
        });
    	
        // Click "Filter"
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Here we add the user register data into the database
                //We first get the message
                String usernameInput = field1.getText();
                sortStatus=(String)comboBox1.getSelectedItem();
             // Connect to DB
                DBManager dbManager = new DBManager();
                try{
                    //Here we call the stored procedure
                    CallableStatement statement =
                            dbManager.connection.prepareCall("call admin_filter_user(?,?,?,?)");
                    //Here we put the data into the procedure
                    statement.setString(1,usernameInput);
                    statement.setString(2,sortStatus);
                    statement.setString(3,sortBy);
                    statement.setString(4,sortDirection);
                    statement.execute(); 
                    java.sql.Statement statement1 = dbManager.connection.createStatement
                    		(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    ResultSet result = statement1.executeQuery("select * from AdFilterUser");

                    // No matching user
                    if(!result.isBeforeFirst())
                    {
                    	JOptionPane.showMessageDialog(null, "No result!");
                    	field1.setText(null);
                    	return;
                    }
                    else
                    {
                    	 result.last();
                         int rowCount = result.getRow();
                         result.first();
                         
                         checkList = new ArrayList<Boolean>();
                         //Initialization
                         for(int i=0;i<rowCount;i++)
                         {
                        	 checkList.add(false);
                         }
                         
                         String[] dataTitle = {"","Username", "Credit Card Number", "User Type", "Status" };
                    	if(mark==0)
                    	{   
                            model = new DefaultTableModel(dataTitle, rowCount);
                            jTable = new JTable(model);
                            jTable.setRowHeight(30);
                            jscrollpane = new JScrollPane(jTable);
                            jscrollpane.setSize(460, 150);
                            jscrollpane.setLocation(20, 200);
                            
                            add(jscrollpane);
                    	}
                    	mark++;
                       
                        model = new DefaultTableModel(dataTitle, rowCount);
                        Object obj[][]= new Object[rowCount][5];
                        for(int i=0;i<rowCount;i++)
                        {
                        	obj[i][0]=new JCheckBox(Integer.toString(i+1));
                        }
                        
                        //Print first row
                       	String username = result.getString(1);
                       	String creditCardCount = result.getString(2);
                    	String userStatus = result.getString(3);
                    	String userType = result.getString(4);		

                    	obj[0][1]=username;
                    	obj[0][2]=creditCardCount;
                    	obj[0][3]=userType;
                    	obj[0][4]=userStatus;
                    	
                    	int i=1;
                        while(result.next())
                        {
                            //Here we try to add button into table
                        	username = result.getString(1);
                        	creditCardCount = result.getString(2);
                        	userType = result.getString(4);
                        	userStatus = result.getString(3);
                         
                        	obj[i][1]=username;
                        	obj[i][2]=creditCardCount;
                        	obj[i][3]=userType;
                        	obj[i][4]=userStatus;

                        	i++;
                        }
                        model.setDataVector(obj, new Object[]{"","Username", "Credit Card Number", "User Type", "Status"});
                        jTable.setModel(model); 
                    }
                    jTable.repaint();
                    jTable.getColumn("").setCellEditor(new CheckButtonEditor(new JCheckBox ()));
                    jTable.getColumn("").setCellRenderer(new CheckBoxRenderer());
                    
            		jscrollpane.repaint();
                    statement.close();
                    statement1.close();
                    dbManager.close();
            }
                catch (Exception event) {
                 	JOptionPane.showMessageDialog(null, "System error!");
                 	event.printStackTrace();
                 	return;
				}
            }});
        
        // Click "Approve"
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	DBManager dbManager = new DBManager();
            	if(checkList==null||!checkList.contains(true))
            	{
            		JOptionPane.showMessageDialog(null, "No user selected!");
            		return;
            	}
                for(int i=0;i<checkList.size();i++)
                {
                	if(checkList.get(i))
                	{
                		try {
                    		//Here we call the stored procedure
                            CallableStatement statement =
                                    dbManager.connection.prepareCall("call admin_approve_user(?)");
                            //Here we put the data into the procedure 
                            String usernameInput = (String)(jTable.getValueAt(i, 1));
                            statement.setString(1,usernameInput);
                            statement.execute(); 
                            JOptionPane.showMessageDialog(null, "Approval succeed!");
                            statement.close();
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "Can't approve approved users!");
							e2.printStackTrace();
							dbManager.close();
						}
                	}
                }
            }
        });
        // Click "Decline"
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            	DBManager dbManager = new DBManager();
            	if(checkList==null||!checkList.contains(true))
            	{
            		JOptionPane.showMessageDialog(null, "No user selected!");
            		return;
            	}
                for(int i=0;i<checkList.size();i++)
                {
                	if(checkList.get(i))
                	{
                		if(((String)jTable.getValueAt(i, 4)).equals("Approved"))
                			{
                			JOptionPane.showMessageDialog(null, "Cannot decline approved user!");
                            return;
                			}
                		try {
                    		//Here we call the stored procedure
                            CallableStatement statement =
                                    dbManager.connection.prepareCall("call admin_decline_user(?)");
                            //Here we put the data into the procedure 
                            String usernameInput = (String)(jTable.getValueAt(i, 1));
                            statement.setString(1,usernameInput);
                            statement.execute(); 
                            JOptionPane.showMessageDialog(null, "Decline succeed!");
                            statement.close();
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "Can't decline approved or declined users!");
							e2.printStackTrace();
							dbManager.close();
							return;
						}
                	}
                }
            }
        });
        // Click "Back"
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(usertype.equals("admin"))
                {
                    adminOnlyFunc f = new adminOnlyFunc();
                    f.username=username;
                }
                if(usertype.equals("adminCustomer"))
                {
                    adminCustomerFunc f = new adminCustomerFunc();
                    f.username=username;
                }
                dispose();
            }
        });
    }
    
    class CheckButtonEditor extends DefaultCellEditor
    implements ItemListener {
    private JCheckBox button;

    public CheckButtonEditor(JCheckBox checkBox) {
    super(checkBox);
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
    boolean isSelected, int row, int column) {
    if (value==null) return null;
    button = (JCheckBox)value;
    button.addItemListener(this);
    return (Component)value;
    }

    public Object getCellEditorValue() {
    button.removeItemListener(this);
    return button;
    }

    public void itemStateChanged(ItemEvent e) {
    super.fireEditingStopped();
    String sID = ((JCheckBox)e.getSource()).getLabel();
    int id = Integer.parseInt(sID) - 1;
    if(checkList.get(id))
    	checkList.set(id,false);
    else {
		checkList.set(id,true);
	}
    }
    }


}