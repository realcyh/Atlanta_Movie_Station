package CS4400Final;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import  java.util.*;

public class CreateTheater extends JFrame {
	//label1 is the head of this window
	JLabel label1;
	//label2 to label6 are the labels for test
	JLabel theaterNameLabel;
	JLabel companyLabel;
	JLabel streetLabel;
	JLabel cityLabel;
	JLabel stateLabel;
	JLabel zipcodeLabel;
	JLabel capacityLabel;
	JLabel managerLabel;

	JTextField theaterName;
	JTextField street;
	JTextField city;
	JTextField zipcode;
	JTextField capacity;

	JButton button1;
	JButton button2;

	JComboBox<String> comboBox1;
	JComboBox<String> comboBox2;
	JComboBox<String> comboBox3;
	java.util.List<String> managerList;
	
	protected String username;
	protected String userType;

	public CreateTheater(){
		init();
		addComponent();
		addListener();
	}

	public void init()
	{
		this.setSize(510,380);
		this.setVisible(true);
		this.setTitle("Create Theater");
		this.setLayout(null);
		this.setLocation(500, 200);
		this.setBackground(Color.blue);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void addComponent(){
		label1 = new JLabel("Create Theater");
		label1.setSize(400,200);
		label1.setFont(new Font("Dialog",Font.BOLD, 25));
		label1.setLocation(150,-50);
		this.add(label1);

		theaterNameLabel = new JLabel("Name");
		theaterNameLabel.setSize(50,30);
		theaterNameLabel.setLocation(20,100);
		this.add(theaterNameLabel);

		theaterName = new JTextField();
		theaterName.setSize(150,30);
		theaterName.setLocation(70,100);
		this.add(theaterName);

		companyLabel = new JLabel("Company");
		companyLabel.setSize(80, 30);
		companyLabel.setLocation(250, 100);
		this.add(companyLabel);

		java.util.List<String> companyList = new java.util.ArrayList<String>(Arrays.asList(new String[] {
				"4400 Theater Company", "AI Theater Company", "Awesome Theater Company", "EZ Theater Company"}
		));
		comboBox1 = new JComboBox<String>();
		comboBox1.addItem(" ");
		for(String company : companyList){
			comboBox1.addItem(company);
		}
		comboBox1.setSize(150,30);
		comboBox1.setLocation(330,100);
		this.add(comboBox1);

		streetLabel = new JLabel("Street Address");
		streetLabel.setSize(100, 30);
		streetLabel.setLocation(20,150);
		this.add(streetLabel);

		street = new JTextField();
		street.setSize(360, 30);
		street.setLocation(120, 150);
		this.add(street);

		cityLabel = new JLabel("City");
		cityLabel.setSize(50,30);
		cityLabel.setLocation(20,200);
		this.add(cityLabel);

		city = new JTextField();
		city.setSize(100, 30);
		city.setLocation(70,200);
		this.add(city);

		stateLabel = new JLabel("State");
		stateLabel.setSize(50,30);
		stateLabel.setLocation(180,200);
		this.add(stateLabel);

		java.util.List<String> stateList = new java.util.ArrayList<String>(Arrays.asList(new String[] {
				"AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID",
				"IL","IN","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS"
				,"MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR"
				,"PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"}
		));
		comboBox2 = new JComboBox<String>();
		comboBox2.addItem(" ");
		for(String state : stateList){
			comboBox2.addItem(state);
		}
		comboBox2.setSize(90,30);
		comboBox2.setLocation(230,200);
		this.add(comboBox2);

		zipcodeLabel = new JLabel("Zipcode");
		zipcodeLabel.setSize(50,30);
		zipcodeLabel.setLocation(330,200);
		this.add(zipcodeLabel);

		zipcode = new JTextField();
		zipcode.setSize(100, 30);
		zipcode.setLocation(380,200);
		this.add(zipcode);

		capacityLabel = new JLabel("Capacity");
		capacityLabel.setSize(100,30);
		capacityLabel.setLocation(20,250);
		this.add(capacityLabel);

		capacity = new JTextField();
		capacity.setSize(100, 30);
		capacity.setLocation(120,250);
		this.add(capacity);

		managerLabel = new JLabel("Manager");
		managerLabel.setSize(100,30);
		managerLabel.setLocation(240,250);
		this.add(managerLabel);

		managerList = new java.util.ArrayList<String>();
		comboBox3 = new JComboBox<String>();
		comboBox3.addItem(" ");
		comboBox3.setSize(150,30);
		comboBox3.setLocation(330,250);
		this.add(comboBox3);

		comboBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String companyName = (String) comboBox1.getSelectedItem();
				DBManager dbManager = new DBManager();
				managerList.clear();
				comboBox3.removeAllItems();
				comboBox3.addItem(" ");

				// Register as user and customer
				try{
					//Here we call the stored procedure
					Statement statement = dbManager.connection.createStatement();
					ResultSet result = statement.executeQuery("select username from manager where companyName = \""+companyName+"\" and username not in (select managerID from theater)");
					while (result.next()) {
						managerList.add(result.getString(1));
					}

					for(String manager : managerList){
						comboBox3.addItem(manager);
					}

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
		button1.setLocation(60,300);
		this.add(button1);

		button2 = new JButton("Create");
		button2.setSize(140,30);
		button2.setLocation(300,300);
		this.add(button2);
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

		// Click "Create"
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Here we add the user register data into the database

				String thName = theaterName.getText();
				String comName = (String) comboBox1.getSelectedItem();
				String thStreet = street.getText();
				String thCity = city.getText();
				String thState = (String) comboBox2.getSelectedItem();
				String thZipcode = zipcode.getText();
				String thCapacity = capacity.getText();
				String managerUsername = (String) comboBox3.getSelectedItem();

				if (thName.length()<=0 || comName.equals(" ") || thStreet.length()<=0 || thCity.length()<=0 || thState.equals(" ") || thZipcode.length()<=0 || thCapacity.length()<=0 || managerUsername.equals(" ")) {
					JOptionPane.showMessageDialog(null,"Please fill all fields!");
					return;
				}
				if (!isNumeric(thCapacity)) {
					JOptionPane.showMessageDialog(null, "Capacity must be numeric!");
					return;
				}
				if (thZipcode.length() != 5) {
					JOptionPane.showMessageDialog(null, "Zipcode must have 5 digits!");
					return;
				}
				if (!isNumeric(thZipcode)) {
					JOptionPane.showMessageDialog(null, "Zipcode must be numeric!");
					return;
				}

				//Here we make the connection to MySQL
				DBManager dbManager = new DBManager();
				// Register as user and customer
				try{
					//Here we call the stored procedure
					CallableStatement statement =
							dbManager.connection.prepareCall("call admin_create_theater(?,?,?,?,?,?,?,?)");
					//Here we put the data into the procedure
					statement.setString(1, thName);
					statement.setString(2, comName);
					statement.setString(3, thStreet);
					statement.setString(4, thCity);
					statement.setString(5, thState);
					statement.setString(6, thZipcode);
					statement.setString(7, thCapacity);
					statement.setString(8, managerUsername);
					statement.execute();
					JOptionPane.showMessageDialog(null, "Succeed!");
					statement.close();
					dbManager.close();
				}catch (Exception event){
					JOptionPane.showMessageDialog(null, "The theater already exists!");
					event.printStackTrace();
					dbManager.close();
					return;
				}
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
