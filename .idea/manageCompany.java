package CS4400Final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import CS4400Final.manageUser.CheckButtonEditor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Array;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class manageCompany extends JFrame {
    private JLabel label1;
    private JLabel labelName;
    private JLabel labelCity;
    private JLabel labelTheater;
    private JLabel labelEmployee;
    private JLabel labelCityDelimiter;
    private JLabel labelTheaterDelimiter;
    private JLabel getLabelEmployeeDelimiter;

    private JButton buttonFilter;
    private JButton buttonCreateTheater;
    private JButton buttonDetail;
    private JButton buttonBack;
    
    private JButton upNameButton;
    private JButton downNameButton;
    private JButton upCityCoveredButton;
    private JButton downCityCoveredButton;
    private JButton upTheatersButton;
    private JButton downTheatersButton;
    private JButton upEmployeeButton;
    private JButton downEmployeeButton;

    private JTextField cityNumMin;
    private JTextField cityNumMax;
    private JTextField theaterNumMin;
    private JTextField theaterNumMax;
    private JTextField employeeNumMin;
    private JTextField employeeNumMax;

    private JComboBox<String> comboBox;
    protected List<String> companyList;

    private JTable jTable;
    DefaultTableModel model;
    private JScrollPane jscrollpane;
    
    protected String username;
    protected String usertype;

    private String sortBy="";
    private String sortDirection="";
    
    private int mark = 0;

    // Contain state of checkbox
    protected List<Boolean>checkList;
    
    public manageCompany(){
        init();
        addComponent();
        addListener();
    }

    private void init(){
        this.setSize(500,450);
        this.setVisible(true);
        this.setTitle("Manage Company");
        this.setLayout(null);
        this.setLocation(500, 200);
    }

    private void addComponent(){
        label1 = new JLabel("Manage Company");
        label1.setSize(400,200);
        label1.setFont(new Font("Dialog",Font.BOLD, 25));
        label1.setLocation(160,-60);
        this.add(label1);

        labelName = new JLabel("Name");
        labelName.setSize(50,20);
        labelName.setLocation(20,70);
        this.add(labelName);

        labelCity = new JLabel("City Covered");
        labelCity.setSize(100,20);
        labelCity.setLocation(250,70);
        this.add(labelCity);

        labelCityDelimiter = new JLabel("------");
        labelCityDelimiter.setSize(60,20);
        labelCityDelimiter.setLocation(400,70);
        this.add(labelCityDelimiter);

        labelTheater = new JLabel("# Theaters");
        labelTheater.setSize(100,20);
        labelTheater.setLocation(20,110);
        this.add(labelTheater);

        labelTheaterDelimiter = new JLabel("------");
        labelTheaterDelimiter.setSize(60,20);
        labelTheaterDelimiter.setLocation(140,110);
        this.add(labelTheaterDelimiter);

        labelEmployee = new JLabel("# Employees");
        labelEmployee.setSize(100,20);
        labelEmployee.setLocation(250,110);
        this.add(labelEmployee);

        labelCityDelimiter = new JLabel("------");
        labelCityDelimiter.setSize(60,20);
        labelCityDelimiter.setLocation(400,110);
        this.add(labelCityDelimiter);

        buttonFilter = new JButton("Filter");
        buttonFilter.setSize(80,20);
        buttonFilter.setLocation(20,140);
        buttonFilter.setBackground(Color.white);
        buttonFilter.setFont(new Font("Dialog",Font.BOLD, 12));
        this.add(buttonFilter);

        buttonCreateTheater = new JButton("Create Theater");
        buttonCreateTheater.setSize(120,20);
        buttonCreateTheater.setLocation(250,140);
        buttonCreateTheater.setBackground(Color.white);
        buttonCreateTheater.setFont(new Font("Dialog",Font.BOLD, 12));
        this.add(buttonCreateTheater);

        buttonDetail = new JButton("Detail");
        buttonDetail.setSize(80,20);
        buttonDetail.setLocation(390,140);
        buttonDetail.setBackground(Color.white);
        buttonDetail.setFont(new Font("Dialog",Font.BOLD, 12));
        this.add(buttonDetail);

        buttonBack = new JButton("Back");
        buttonBack.setSize(100,20);
        buttonBack.setLocation(180,370);
        buttonBack.setBackground(Color.white);
        buttonBack.setFont(new Font("Dialog",Font.BOLD, 18));
        this.add(buttonBack);
        
        upNameButton =new JButton("A");
        upNameButton.setSize(40,30);
        upNameButton.setLocation(110,170);
        upNameButton.setBackground(Color.white);
        upNameButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(upNameButton);
        
        downNameButton =new JButton("D");
        downNameButton.setSize(40,30);
        downNameButton.setLocation(160,170);
        downNameButton.setBackground(Color.white);
        downNameButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(downNameButton);
        
        upCityCoveredButton =new JButton("A");
        upCityCoveredButton.setSize(40,30);
        upCityCoveredButton.setLocation(197,170);
        upCityCoveredButton.setBackground(Color.white);
        upCityCoveredButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(upCityCoveredButton);
        
        downCityCoveredButton =new JButton("D");
        downCityCoveredButton.setSize(40,30);
        downCityCoveredButton.setLocation(249,170);
        downCityCoveredButton.setBackground(Color.white);
        downCityCoveredButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(downCityCoveredButton);
        
        upTheatersButton =new JButton("A");
        upTheatersButton.setSize(40,30);
        upTheatersButton.setLocation(289,170);
        upTheatersButton.setBackground(Color.white);
        upTheatersButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(upTheatersButton);
        
        downTheatersButton =new JButton("D");
        downTheatersButton.setSize(40,30);
        downTheatersButton.setLocation(339,170);
        downTheatersButton.setBackground(Color.white);
        downTheatersButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(downTheatersButton);
        
        upEmployeeButton =new JButton("A");
        upEmployeeButton.setSize(40,30);
        upEmployeeButton.setLocation(379,170);
        upEmployeeButton.setBackground(Color.white);
        upEmployeeButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(upEmployeeButton);
        
        downEmployeeButton =new JButton("D");
        downEmployeeButton.setSize(40,30);
        downEmployeeButton.setLocation(428,170);
        downEmployeeButton.setBackground(Color.white);
        downEmployeeButton.setFont(new Font("Dialog",Font.BOLD, 9));
        this.add(downEmployeeButton);

        cityNumMin = new JTextField();
        cityNumMin.setSize(30,20);
        cityNumMin.setLocation(360,70);
        this.add(cityNumMin);

        cityNumMax = new JTextField();
        cityNumMax.setSize(30,20);
        cityNumMax.setLocation(440,70);
        this.add(cityNumMax);

        theaterNumMin = new JTextField();
        theaterNumMin.setSize(30,20);
        theaterNumMin.setLocation(100,110);
        this.add(theaterNumMin);

        theaterNumMax = new JTextField();
        theaterNumMax.setSize(30,20);
        theaterNumMax.setLocation(180,110);
        this.add(theaterNumMax);

        employeeNumMin = new JTextField();
        employeeNumMin.setSize(30,20);
        employeeNumMin.setLocation(360,110);
        this.add(employeeNumMin);

        employeeNumMax = new JTextField();
        employeeNumMax.setSize(30,20);
        employeeNumMax.setLocation(440,110);
        this.add(employeeNumMax);

        //Here we set the company name comboBox
        companyList = new ArrayList<String>(Arrays.asList(new String[]
                {"4400 Theater Company", "AI Theater Company", "Awesome Theater Company", "EZ Theater Company"}));
        comboBox = new JComboBox<String>();
        comboBox.addItem("ALL");
        for(String tempName : companyList)
        {
            comboBox.addItem(tempName);
        }
        comboBox.setLocation(90,70);
        comboBox.setSize(150,20);
        this.add(comboBox);

        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private void addListener(){
    	// Click upNameButton
        upNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="ASC";
            	sortBy="comName";
            }
        });
        
    	// Click downNameButton
        downNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="DESC";
            	sortBy="comName";
            }
        });
        
    	// Click upCityCoveredButton
        upCityCoveredButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="ASC";
            	sortBy="numCityCover";
            }
        });
        
    	// Click downCityCoveredButton
        downCityCoveredButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="DESC";
            	sortBy="numCityCover";
            }
        });
        
    	// Click upTheatersButton
        upTheatersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="ASC";
            	sortBy="numTheater";
            }
        });
        
    	// Click   downTheatersButton
        downTheatersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="DESC";
            	sortBy="numTheater";
            }
        });
        
    	// Click upEmployeeButton
        upEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="ASC";
            	sortBy="numEmployee";
            }
        });
        
    	// Click downEmployeeButton
        downEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sortDirection="DESC";
            	sortBy="numEmployee";
            }
        });
        
        // Click "Filter"
        buttonFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Here we add the user register data into the database
                //We first get the message
                String comNameInput = (String)comboBox.getSelectedItem();
                int minCityInput;
                int maxCityInput;
                int minTheaterInput;
                int maxTheaterInput;
                int minEmployeeInput;
                int maxEmployeeInput;
                if(cityNumMin.getText().isEmpty())
                {
                	minCityInput=0;
                }
                else
                {
                	 minCityInput= Integer.parseInt(cityNumMin.getText());
				}
                if(cityNumMax.getText().isEmpty())
                {
                	maxCityInput=100;
                }
                else
                {
                	 maxCityInput= Integer.parseInt(cityNumMax.getText());
                }
                if(theaterNumMin.getText().isEmpty())
                {
                	minTheaterInput=0;
                }
                else
                {
                    minTheaterInput=Integer.parseInt(theaterNumMin.getText());
				}
                if(theaterNumMax.getText().isEmpty())
                {
                	maxTheaterInput=100;
                }
                else
                {
                	maxTheaterInput=Integer.parseInt(theaterNumMax.getText());
				}
                if(employeeNumMin.getText().isEmpty())
                {
                	minEmployeeInput=0;
                }
                else
                {
                	minEmployeeInput=Integer.parseInt(employeeNumMin.getText());
				}
                if(employeeNumMax.getText().isEmpty())
                {
                	maxEmployeeInput=100;
                }
                else 
                {
                	maxEmployeeInput=Integer.parseInt(employeeNumMax.getText());
				}
                
             // Connect to DB
                DBManager dbManager = new DBManager();
                try{
                    //Here we call the stored procedure
                    CallableStatement statement =
                            dbManager.connection.prepareCall("call admin_filter_company(?,?,?,?,?,?,?,?,?)");
                    //Here we put the data into the procedure
                 
                    statement.setString(1,comNameInput);
                    statement.setInt(2, minCityInput);
                    statement.setInt(3,maxCityInput);
                    statement.setInt(4,minTheaterInput);
                    statement.setInt(5, maxTheaterInput);
                    statement.setInt(6, minEmployeeInput);
                    statement.setInt(7, maxEmployeeInput);
                    statement.setString(8,sortBy);
                    statement.setString(9,sortDirection);
                    statement.execute(); 
                    java.sql.Statement statement1 = dbManager.connection.createStatement
                    		(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    ResultSet result = statement1.executeQuery("select * from AdFilterCom");

                    // No matching user
                    if(!result.isBeforeFirst())
                    {
                    	JOptionPane.showMessageDialog(null, "No result!");
                    	cityNumMin.setText(null);
                    	cityNumMax.setText(null);
                    	theaterNumMin.setText(null);
                    	theaterNumMax.setText(null);
                    	employeeNumMin.setText(null);
                    	employeeNumMax.setText(null);
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
                         
                         String[] dataTitle = {"","Name", "#CityCovered", "#Theaters", "#Employee" };
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
                       	String comName = result.getString(1);
                       	String cityCovered = result.getString(2);
                    	String numTheater = result.getString(3);		
                    	String numEmployee = result.getString(4);
                    	obj[0][1]=comName;
                    	obj[0][2]=cityCovered;
                    	obj[0][3]=numTheater;
                    	obj[0][4]=numEmployee;
                    	
                    	int i=1;
                        while(result.next())
                        {
                            //Here we try to add button into table
                          	comName = result.getString(1);
                           	cityCovered = result.getString(2);
                        	numTheater = result.getString(3);		
                        	numEmployee = result.getString(4);
                        	obj[i][1]=comName;
                        	obj[i][2]=cityCovered;
                        	obj[i][3]=numTheater;
                        	obj[i][4]=numEmployee;

                        	i++;
                        }
                        model.setDataVector(obj, new Object[]{"","Name", "#CityCovered", "#Theaters", "#Employee"});
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
        
        // Click "Create Theater"
        buttonCreateTheater.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
					CreateTheater f = new CreateTheater();
					f.username=username;
					f.userType=usertype;
					dispose();
            }
            }
        );
        
        // Click "Detail"
        buttonDetail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(checkList==null||!checkList.contains(true))
            	{
            		JOptionPane.showMessageDialog(null, "No company selected!");
            		return;
            	}
            	else
            	{
            		String selectedCompany=(String)jTable.getValueAt(checkList.indexOf(true),1);
					CompanyDetail f = new CompanyDetail();
					f.username=username;
					f.userType=usertype;
					f.companyName=selectedCompany;
					dispose();
				}

            }
            }
        );
       // Click "Back"
        buttonBack.addActionListener(new ActionListener() {
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
    	if(checkList.contains(true))
    	{
    		JOptionPane.showMessageDialog(null, "Only one company can be selected!");
    		return;
    	}
		checkList.set(id,true);
	}

    }
    }
}
