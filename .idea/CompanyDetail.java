package CS4400Final;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import  java.util.*;
import java.util.List;

public class CompanyDetail extends JFrame {
	//label1 is the head of this window
	JLabel label1;
	//label2 to label6 are the labels for test
	JLabel companyNameLabel;
	JLabel companyNameDataLabel;
	JLabel employeeLabel;
	JLabel employeeDataLabel;

	JButton button1;

	private DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane scrollPane;
	private int mark = 0;

	String employees = new String();

	protected String username;
	protected String userType;
	protected String companyName;

	public CompanyDetail(){
		init();
		addComponent();
		addListener();
	}

	public void init()
	{
		this.setSize(660,530);
		this.setVisible(true);
		this.setTitle("Company Detail");
		this.setLayout(null);
		this.setLocation(500, 200);
		this.setBackground(Color.blue);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void addComponent(){
		label1 = new JLabel("Company Detail");
		label1.setSize(400,200);
		label1.setFont(new Font("Dialog",Font.BOLD, 25));
		label1.setLocation(250,-50);
		this.add(label1);

		companyNameLabel = new JLabel("Name");
		companyNameLabel.setSize(100,30);
		companyNameLabel.setLocation(30,100);
		this.add(companyNameLabel);

		employeeLabel = new JLabel("Employees");
		employeeLabel.setSize(100,30);
		employeeLabel.setLocation(30,150);
		this.add(employeeLabel);

		employeeDataLabel = new JLabel();
        employeeDataLabel.setSize(520,30);
        employeeDataLabel.setLocation(130,150);
		this.add(employeeDataLabel);

		companyNameDataLabel = new JLabel(companyName);
        companyNameDataLabel.setSize(520,30);
        companyNameDataLabel.setLocation(130,100);
        this.add(companyNameDataLabel);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				DBManager dbManager = new DBManager();
				StringBuffer employee = new StringBuffer();
				try{
					//Here we call the stored procedure
					CallableStatement statement =
							dbManager.connection.prepareCall("call admin_view_comDetail_emp(?)");
					//Here we put the data into the procedure
					statement.setString(1,companyName);
					statement.execute();

					ResultSet result = statement.executeQuery("select * from AdComDetailEmp");
					//employee.append(result.getString(1)); //+" "+result.getString(2)
					result.next();
					employee.append("<html>"+result.getString(1)+" "+result.getString(2));
					while (result.next()) {
						employee.append(", "+result.getString(1)+" "+result.getString(2));
					}
					employee.append("</html>");
					employees = employee.toString();
					employeeDataLabel.setText(employees);
					companyNameDataLabel.setText(companyName);

					statement.close();
					dbManager.close();
				}catch (Exception event){
					JOptionPane.showMessageDialog(null, "Error!");
					event.printStackTrace();
					dbManager.close();
					return;
				}
			}
		});

		button1 = new JButton("Back");
		button1.setSize(140,30);
		button1.setLocation(260,450);
		this.add(button1);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent windowEvent) {
				DBManager dbManager = new DBManager();
				// Register as user and customer
				try{
					//Here we call the stored procedure
					CallableStatement statement =
							dbManager.connection.prepareCall("call admin_view_comDetail_th(?)");
					//Here we put the data into the procedure
					statement.setString(1,companyName);
					statement.execute();
					ResultSet result = statement.executeQuery("select * from AdComDetailTh");

                    Statement statement2 = dbManager.connection.createStatement();
                    ResultSet result2 = statement2.executeQuery("select username, firstName, lastName from users where username in (select thManagerUsername from AdComDetailTh)");

                    // No matching user
					if(!result.isBeforeFirst() || !result2.isBeforeFirst())
					{
						JOptionPane.showMessageDialog(null, "No result!");
						String[] dataTitle = {"Name", "Manager", "City", "State", "Capacity"};
						if (mark == 0) {
							tableModel = new DefaultTableModel(dataTitle, 0);
							table = new JTable(tableModel);
							table.setRowHeight(30);
							scrollPane = new JScrollPane(table);
							scrollPane.setSize(600, 230);
							scrollPane.setLocation(30, 270);
							add(scrollPane);
							mark++;
						}
						tableModel = new DefaultTableModel(dataTitle, 0);
						return;
					}
					else
					{
						result.last();
						int rowCount = result.getRow();
						result.beforeFirst();

						String[] dataTitle = {"Name", "Manager", "City", "State", "Capacity"};

						if (mark == 0) {
							tableModel = new DefaultTableModel(dataTitle, rowCount);
							table = new JTable(tableModel);
							table.setRowHeight(30);
							scrollPane = new JScrollPane(table);
							scrollPane.setSize(600, 230);
							scrollPane.setLocation(30, 200);
							add(scrollPane);
							mark++;
						}
						tableModel = new DefaultTableModel(dataTitle, rowCount);
						Object obj[][]= new Object[rowCount][5];

						int i=0;
						while(result.next())
						{
						    while (result2.next()) {
                                String theater = result.getString(1);
                                String managerID = result.getString(2);
                                String city = result.getString(3);
                                String state = result.getString(4);
                                String capacity = result.getString(5);

                                if (result2.getString(1).equals(managerID)) {
                                    StringBuffer name = new StringBuffer();
                                    name.append(result2.getString(2)+" "+result2.getString(3));
                                    obj[i][0]=theater;
                                    obj[i][1] = name;
                                    obj[i][2] = city;
                                    obj[i][3] = state;
                                    obj[i][4] = capacity;
                                    break;
                                }
                            }
                            result2.beforeFirst();
                            i++;
						}
						tableModel.setDataVector(obj, new Object[]{"Name", "Manager", "City", "State", "Capacity"});
						table.setModel(tableModel);

					}
//                    table.repaint();
//                    scrollPane.repaint();

					statement2.close();
					statement.close();
					dbManager.close();
				}catch (Exception event){
					JOptionPane.showMessageDialog(null, "System error!");
					event.printStackTrace();
					dbManager.close();
					return;
				}
			}
		});

	}

	private void addListener(){

		// Click "Back"
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageCompany f = new manageCompany();
				f.username = username;
				f.usertype=userType;
				dispose();
			}
		});
	}

	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

}
