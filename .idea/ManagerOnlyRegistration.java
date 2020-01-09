package CS4400Final;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.CallableStatement;
import  java.util.*;


public class ManagerOnlyRegistration extends JFrame {
    //label1 is the head of this window
    JLabel label1;
    //label2 to label6 are the labels for test
    JLabel firstNameLabel;
    JLabel lastNameLabel;
    JLabel userNameLabel;
    JLabel companyNameLabel;
    JLabel passwordLabel;
    JLabel confirmPasswordLabel;
    JLabel streetAddressLabel;
    JLabel cityLabel;
    JLabel zipcodeLabel;
    JLabel stateLabel;

    JTextField firstName;
    JTextField lastName;
    JTextField userName;
    JTextField streetAddress;
    JTextField city;
    JTextField zipCode;
    
    JPasswordField password;
    JPasswordField confirmPassword;

    JButton button1;
    JButton button2;

    java.util.List<String> stateList;
    java.util.List<String> companyList;
    JComboBox<String> comboBox1;
    JComboBox<String> comboBox2;
    
    public void init()
    {
        this.setSize(650,650);
        this.setVisible(true);
        this.setTitle("Manager-Only Registration");
        this.setLayout(null);
        this.setLocation(500, 100);
        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addComponent(){
        label1 = new JLabel("Manager-Only Registration");
        label1.setSize(400,200);
        label1.setFont(new Font("Dialog",Font.BOLD, 25));
        label1.setLocation(160,-50);
        this.add(label1);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setSize(100,30);
        firstNameLabel.setLocation(20,80);
        this.add(firstNameLabel);

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setSize(100,30);
        lastNameLabel.setLocation(320,80);
        this.add(lastNameLabel);

        userNameLabel = new JLabel("Username");
        userNameLabel.setSize(100,30);
        userNameLabel.setLocation(20,140);
        this.add(userNameLabel);
        
        companyNameLabel = new JLabel("Company");
        companyNameLabel.setSize(100,30);
        companyNameLabel.setLocation(320,140);
        this.add(companyNameLabel);

        passwordLabel = new JLabel("Password");
        passwordLabel.setSize(100,30);
        passwordLabel.setLocation(20,200);
        this.add(passwordLabel);

        confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setSize(150,30);
        confirmPasswordLabel.setLocation(320,200);
        this.add(confirmPasswordLabel);

        streetAddressLabel = new JLabel("Street Address");
        streetAddressLabel.setSize(100,30);
        streetAddressLabel.setLocation(20,260);
        this.add(streetAddressLabel);

        cityLabel = new JLabel("City");
        cityLabel.setSize(80,30);
        cityLabel.setLocation(20,320);
        this.add(cityLabel);

        stateLabel = new JLabel("State");
        stateLabel.setSize(80,30);
        stateLabel.setLocation(250,320);
        this.add(stateLabel);

        zipcodeLabel = new JLabel("zipCode");
        zipcodeLabel.setSize(80,30);
        zipcodeLabel.setLocation(380,320);
        this.add(zipcodeLabel);

        firstName = new JTextField();
        firstName.setSize(150,30);
        firstName.setLocation(120,80);
        this.add(firstName);

        lastName = new JTextField();
        lastName.setSize(150,30);
        lastName.setLocation(450,80);
        this.add(lastName);

        userName = new JTextField();
        userName.setSize(150,30);
        userName.setLocation(120,140);
        this.add(userName);

        password = new JPasswordField();
        password.setSize(150,30);
        password.setLocation(120,200);
        this.add(password);

        confirmPassword = new JPasswordField();
        confirmPassword.setSize(150,30);
        confirmPassword.setLocation(450,200);
        this.add(confirmPassword);

        streetAddress = new JTextField();
        streetAddress.setSize(480,30);
        streetAddress.setLocation(120,260);
        this.add(streetAddress);

        city = new JTextField();
        city.setLocation(120,320);
        city.setSize(100,30);
        this.add(city);

        zipCode = new JTextField();
        zipCode.setSize(150,30);
        zipCode.setLocation(450,320);
        this.add(zipCode);

        button1 = new JButton("Back");
        button1.setSize(140,30);
        button1.setLocation(120,440);
        this.add(button1);

        button2 = new JButton("Register");
        button2.setSize(140,30);
        button2.setLocation(350,440);
        this.add(button2);

        java.util.List<String> stateList = new java.util.ArrayList<String>(Arrays.asList(new String[] {
                "AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID",
                "IL","IN","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS"
                ,"MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR"
                ,"PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"}
                ));
        comboBox1 = new JComboBox<String>();
        comboBox1.addItem("");
        for(String state : stateList){
            comboBox1.addItem(state);
        }
        comboBox1.setLocation(300,320);
        comboBox1.setSize(60,30);
        this.add(comboBox1);
        
        java.util.List<String> companyList = new java.util.ArrayList<String>(Arrays.asList(new String[] {
                "4400 Theater Company", "AI Theater Company", "Awesome Theater Company", "EZ Theater Company"}
                ));
        comboBox2 = new JComboBox<String>();
        comboBox2.addItem("");
        for(String company : companyList){
            comboBox2.addItem(company);
        }
        comboBox2.setLocation(450,140);
        comboBox2.setSize(150,30);
        this.add(comboBox2);
    }
    
    private void addListener(){ 	
        // Click "Back"
    	button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new registerNavigation();
                dispose();
            }
        });
   
    // Click "Register"
    button2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            //Here we add the user register data into the database

            //We first get the message
            String usernameInput = userName.getText();
            String firstNameInput =  firstName.getText();
            String lastNameInput = lastName.getText();
            String passwordInput = password.getText();
            String companyInput = (String)comboBox2.getSelectedItem();
            String confirmPasswordInput = confirmPassword.getText();
            String streetAddressInput = streetAddress.getText();
            String cityInput = city.getText();
            String stateInput = (String)comboBox1.getSelectedItem();
            String zipCodeInput = zipCode.getText();

            // All fields required
            if(usernameInput.length() == 0 || firstNameInput.length() == 0 || lastNameInput.length() == 0
                    || passwordInput.length() == 0 || confirmPasswordInput.length() == 0 || 
                    companyInput.length()==0 || streetAddressInput.length()==0 ||
                    cityInput.length()==0 || stateInput.length()==0||zipCodeInput.length()==0)
            {
                JOptionPane.showMessageDialog(null, "Please fill all fields!");
                return;
            }
            
            //Here we check whether 2 passwords are the same
            if (!passwordInput.equals(confirmPasswordInput)) {
                JOptionPane.showMessageDialog(null, "Password must be consistent!");
                    return;
                }
            
            // Password length
            if(passwordInput.length()<8)
            {
                JOptionPane.showMessageDialog(null, "Password must have at least 8 characters!");
                return;
            }
            
            // zipCode must have 5 digits
            if(zipCodeInput.length()!=5||!isNumeric(zipCodeInput))
            {
            	JOptionPane.showMessageDialog(null, "Invalid zipcode!");
                return;
            }
            
            //Here we make the connection to MySQL
            DBManager dbManager = new DBManager();
            // Register as user and customer
            try{
                //Here we call the stored procedure
                CallableStatement statement =
                        dbManager.connection.prepareCall("call manager_only_register(?,?,?,?,?,?,?,?,?)");
                //Here we put the data into the procedure
                statement.setString(1,usernameInput);
                statement.setString(2,passwordInput);
                statement.setString(3,firstNameInput);
                statement.setString(4,lastNameInput);
                statement.setString(5,companyInput);
                statement.setString(6,streetAddressInput);
                statement.setString(7,cityInput);
                statement.setString(8,stateInput);
                statement.setString(9,zipCodeInput);
                statement.execute();        
                JOptionPane.showMessageDialog(null, "Manager registration succeed!");
                statement.close();
                dbManager.close();
            }catch (Exception event){
                JOptionPane.showMessageDialog(null, "Duplicate username or address, please try again!");
            	userName.setText(null);
            	password.setText(null);
            	confirmPassword.setText(null);
                event.printStackTrace();
                dbManager.close();
                return;
            }
        }
        });
    }
    
    public ManagerOnlyRegistration(String title){
    	super(title);
        init();
        addComponent();
        addListener();
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
