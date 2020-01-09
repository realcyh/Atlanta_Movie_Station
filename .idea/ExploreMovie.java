package CS4400Final;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXDatePicker;

import CS4400Final.manageCompany.CheckButtonEditor;

public class ExploreMovie extends JFrame{
	
	private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    
    private JButton buttonFilter;
    private JButton buttonView;
    private JButton buttonBack;
    
    private JTextField field1;
    
    JComboBox<String> comboBox1;
    
    java.util.List<String> companyList;
    JComboBox<String> comboBox2;
    
    java.util.List<String> stateList;
    JComboBox<String> comboBox3;
    
    JComboBox<String> comboBox4;

    private JTable jTable;
    private TableColumn column;
    DefaultTableModel model;
    private JScrollPane jscrollpane;
    
    final JXDatePicker datepick1 = new JXDatePicker();
    final JXDatePicker datepick2 = new JXDatePicker();
    
    private int mark = 0;

    // Contain state of checkbox
    protected List<Boolean>checkList;
    
	protected String username;
	protected String userType;
	
    public ExploreMovie(String str){
    	username=str;
        init();
        addComponent();
        addListener();
    }
    
    private void init(){
        this.setSize(800,450);
        this.setVisible(true);
        this.setTitle("Explore Movie");
        this.setLayout(null);
        this.setLocation(500, 200);
    }
	
    private void addComponent(){
        label1 = new JLabel("Explore Movie");
        label1.setSize(400,200);
        label1.setFont(new Font("Dialog",Font.BOLD, 25));
        label1.setLocation(290,-60);
        this.add(label1);

        label2 = new JLabel("Movie Name");
        label2.setSize(80,30);
        label2.setFont(new Font("Dialog",Font.BOLD, 12));
        label2.setLocation(170,80);
        this.add(label2);

        label3 = new JLabel("Company Name");
        label3.setSize(100,30);
        label3.setFont(new Font("Dialog",Font.BOLD, 12));
        label3.setLocation(390,80);
        this.add(label3);
        
        label4 = new JLabel("City");
        label4.setSize(80,30);
        label4.setFont(new Font("Dialog",Font.BOLD, 12));
        label4.setLocation(170,120);
        this.add(label4);
        
        label5 = new JLabel("State");
        label5.setSize(50,30);
        label5.setFont(new Font("Dialog",Font.BOLD, 12));
        label5.setLocation(390,120);
        this.add(label5);
        
        label6 = new JLabel("Movie Play Date");
        label6.setSize(100,30);
        label6.setFont(new Font("Dialog",Font.BOLD, 12));
        label6.setLocation(170,160);
        this.add(label6);
        
        label7 = new JLabel("------");
        label7.setSize(50,30);
        label7.setFont(new Font("Dialog",Font.BOLD, 12));
        label7.setLocation(435,160);
        this.add(label7);
        
        label8 = new JLabel("Card Number");
        label8.setSize(80,30);
        label8.setFont(new Font("Dialog",Font.BOLD, 12));
        label8.setLocation(290,365);
        this.add(label8);
        
        field1 = new JTextField();
        field1.setSize(120,20);
        field1.setLocation(260,125);
        this.add(field1);

        buttonFilter = new JButton("Filter");
        buttonFilter.setSize(100,20);
        buttonFilter.setLocation(330,200);
        buttonFilter.setBackground(Color.white);
        buttonFilter.setFont(new Font("Dialog",Font.BOLD, 12));
        this.add(buttonFilter);

        buttonView = new JButton("View");
        buttonView.setSize(100,20);
        buttonView.setLocation(540,370);
        buttonView.setBackground(Color.white);
        buttonView.setFont(new Font("Dialog",Font.BOLD, 12));
        this.add(buttonView);

        buttonBack = new JButton("Back");
        buttonBack.setSize(100,20);
        buttonBack.setLocation(170,370);
        buttonBack.setBackground(Color.white);
        buttonBack.setFont(new Font("Dialog",Font.BOLD, 12));
        this.add(buttonBack);
        
        // Initialize movieList
        // Connect to DB
        DBManager dbManager = new DBManager();
        try
        {
        	//Here we call the stored procedure
        	java.sql.Statement statement = dbManager.connection.createStatement
            		(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery("select movieName from team25.movie");
            comboBox1 = new JComboBox<String>();
            comboBox1.addItem("ALL");
            while(result.next())
            {
            	comboBox1.addItem(result.getString(1));
            }
            comboBox1.setLocation(260,85);
            comboBox1.setSize(120,20);
            this.add(comboBox1);
            statement.close();
            
		}
        catch (Exception e)
        {
        	JOptionPane.showMessageDialog(null, "Initialization Failed!");
         	e.printStackTrace();
         	return;
		}
        
        companyList = new java.util.ArrayList<String>(Arrays.asList(new String[] {
                "EZ Theater Company", "4400 Theater Company", "Awesome Theater Company","AI Theater Company"}));
        comboBox2 = new JComboBox<String>();
        comboBox2.addItem("ALL");
        for(String company : companyList){
            comboBox2.addItem(company);
        }
        comboBox2.setLocation(500,85);
        comboBox2.setSize(130,20);
        this.add(comboBox2);
        
        stateList = new java.util.ArrayList<String>(Arrays.asList(new String[] {
                "AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID",
                "IL","IN","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS"
                ,"MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR"
                ,"PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"}
                ));
        comboBox3 = new JComboBox<String>();
        comboBox3.addItem("ALL");
        for(String state : stateList){
            comboBox3.addItem(state);
        }
        comboBox3.setLocation(440,125);
        comboBox3.setSize(50,20);
        this.add(comboBox3);
        
        try
        {
        	//Here we call the stored procedure
        	java.sql.Statement statement = dbManager.connection.createStatement
            		(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery("select creditCardNum from team25.creditcard where username="+"\""+username+"\"");
            comboBox4 = new JComboBox<String>();
            while(result.next())
            {
            	comboBox4.addItem(result.getString(1));
            }
            comboBox4.setLocation(370,370);
            comboBox4.setSize(150,20);
            this.add(comboBox4);
            statement.close();
            
		}
        catch (Exception e)
        {
        	JOptionPane.showMessageDialog(null, "Initialization Failed!");
         	e.printStackTrace();
         	return;
		}
        
        Date date1 = new Date();
        Date date2 = new Date();

        datepick1.setDate(date1);
        datepick1.setSize(150,20);
        datepick1.setLocation(280,165);
        this.add(datepick1);

        datepick2.setDate(date2);
        datepick2.setSize(150,20);
        datepick2.setLocation(470,165);
        this.add(datepick2);
        
        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    
    private void addListener(){
        // Click "Back"
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(userType.equals("adminCustomer"))
                {
                    adminCustomerFunc f = new adminCustomerFunc();
                    f.username=username;
                }
                if(userType.equals("customer"))
                {
                    customerOnlyFunc f = new customerOnlyFunc();
                    f.username=username;
                }
                if(userType.equals("managerCustomer"))
                {
                    managerCustomerFunc f = new managerCustomerFunc();
                    f.username=username;
                }
                dispose();
            }
        });
        
        // Click "Filter"
        buttonFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String movieNameInput = (String) comboBox1.getSelectedItem();
                String companyNameInput = (String) comboBox2.getSelectedItem();
                String cityInput = field1.getText();
                String stateInput = (String) comboBox3.getSelectedItem();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d1 = datepick1.getDate();
                String minPlayDate = sdf.format(d1);
                Date d2 = datepick2.getDate();
                String maxPlayDate = sdf.format(d2);
 
                DBManager dbManager = new DBManager();
                try 
                {
                	//Here we call the stored procedure
                    CallableStatement statement =
                            dbManager.connection.prepareCall("call customer_filter_mov(?,?,?,?,?,?)");
                    //Here we put the data into the procedure
                    statement.setString(1, movieNameInput);
                    statement.setString(2, companyNameInput);
                    statement.setString(3, cityInput);
                    statement.setString(4, stateInput);
                    statement.setString(5, minPlayDate);
                    statement.setString(6, maxPlayDate);
                    statement.execute(); 
                    java.sql.Statement statement1 = dbManager.connection.createStatement
                    		(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    ResultSet result = statement1.executeQuery("select * from CosFilterMovie");

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
                         
                         String[] dataTitle = {"","Movie", "Theater", "Address", "Company", "Play Date" };
                    	if(mark==0)
                    	{   
                            model = new DefaultTableModel(dataTitle, rowCount);
                            jTable = new JTable(model);
                            jTable.setRowHeight(30);
                            jscrollpane = new JScrollPane(jTable);
                            jscrollpane.setSize(700, 120);
                            jscrollpane.setLocation(50, 230);
                            
                            add(jscrollpane);
                    	}
                    	mark++;
                       
                        model = new DefaultTableModel(dataTitle, rowCount);
                        Object obj[][]= new Object[rowCount][6];
                        for(int i=0;i<rowCount;i++)
                        {
                        	obj[i][0]=new JCheckBox(Integer.toString(i+1));
                        }
                        
                        //Print first row
                       	String movieName = result.getString(1);
                       	String theater = result.getString(2);
                    	String address = result.getString(3)+", "+result.getString(4)+", "+result.getString(5)+" "+result.getString(6);		
                    	String companyName = result.getString(7);
                    	String playDate =result.getString(8);
                    	obj[0][1]=movieName;
                    	obj[0][2]=theater;
                    	obj[0][3]=address;
                    	obj[0][4]=companyName;
                    	obj[0][5]=playDate;
                    	
                    	int i=1;
                        while(result.next())
                        {
                            //Here we try to add button into table
                           	movieName = result.getString(1);
                           	theater = result.getString(2);
                        	address = result.getString(3)+", "+result.getString(4)+", "+result.getString(5)+" "+result.getString(6);		
                        	companyName = result.getString(7);
                        	playDate =result.getString(8);
                        	obj[i][1]=movieName;
                        	obj[i][2]=theater;
                        	obj[i][3]=address;
                        	obj[i][4]=companyName;
                            obj[i][5]=playDate;
                        	i++;
                        }
                        model.setDataVector(obj, new Object[]{"","Movie", "Theater", "Address", "Company", "Play Date"});
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
        
        // Click "View"
        buttonView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	DBManager dbManager = new DBManager();
                for(int i=0;i<checkList.size();i++)
                {
                	if(checkList.get(i))
                	{
                		try {
                    		//Here we call the stored procedure
                            //Here we put the data into the procedure 
                            java.sql.Statement statement1 = dbManager.connection.createStatement
                            		(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                            ResultSet result = statement1.executeQuery("select releaseDate from team25.movie where movieName =\""+(String)(jTable.getValueAt(i, 1))+"\"");
                            String creditCardInput = (String)comboBox4.getSelectedItem();
                            String movieNameInput = (String)(jTable.getValueAt(i, 1));
                            result.next();
                            String releaseDate = result.getString(1);
                            statement1.close();
                            String theaterInput = (String)(jTable.getValueAt(i, 2));	
                            String companyInput = (String)(jTable.getValueAt(i, 4));
                            String playDateInput = (String)(jTable.getValueAt(i, 5));
                            CallableStatement statement =
                                    dbManager.connection.prepareCall("call customer_view_mov(?,?,?,?,?,?)");
                            statement.setString(1,creditCardInput);
                            statement.setString(2,movieNameInput);
                            statement.setString(3,releaseDate);
                            statement.setString(4,theaterInput);
                            statement.setString(5,companyInput);
                            statement.setString(6,playDateInput);
                            statement.execute(); 
                            JOptionPane.showMessageDialog(null, "View succeed!");
                            statement.close();
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "You have reached maximum view times");
							e2.printStackTrace();
							dbManager.close();
						}
                	}
                }
            }
            }
        );
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
    	if(checkList.contains(true))
    	{
    		JOptionPane.showMessageDialog(null, "Only one movie can be selected!");
    		return;
    	}
		checkList.set(id,true);
	}

    }
    }
}
