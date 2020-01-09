package CS4400Final;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ManagerCustomerRegister extends JFrame{
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
    JLabel creditCard;

    JLabel credShow1;
    JLabel credShow2;
    JLabel credShow3;
    JLabel credShow4;
    JLabel credShow5;

    JTextField firstName;
    JTextField lastName;
    JTextField userName;
    JPasswordField password;
    JPasswordField confirmPassword;
    JTextField streetAddress;
    JTextField city;
    JTextField zipCode;

    JTextField credNo1;
    JTextField credNo2;
    JTextField credNo3;
    JTextField credNo4;
    JTextField credNo5;

    JButton button1;
    JButton button2;

    JButton buttonCreditAdd1;
    JButton buttonCreditAdd2;
    JButton buttonCreditAdd3;
    JButton buttonCreditAdd4;
    JButton buttonCreditAdd5;

    JButton buttonCreditDel1;
    JButton buttonCreditDel2;
    JButton buttonCreditDel3;
    JButton buttonCreditDel4;
    JButton buttonCreditDel5;


    JPanel panel;

    java.util.List<String> stateList;
    java.util.List<String> companyList;
    JComboBox<String> comboBox1;
    JComboBox<String> comboBox2;

    public void init()
    {
        this.setSize(650,720);
        this.setVisible(true);
        this.setTitle("Manager-Customer Register");
        this.setLayout(null);
        this.setLocation(500, 100);
        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addComponent(){
    	// Must initial credShow 
    	credShow1=new JLabel("");
    	credShow2=new JLabel("");
    	credShow3=new JLabel("");
    	credShow4=new JLabel("");
    	credShow5=new JLabel("");
    	
        label1 = new JLabel("Manager-Customer Registration ");
        label1.setSize(500,200);
        label1.setFont(new Font("Dialog",Font.BOLD, 25));
        label1.setLocation(140,-50);
        this.add(label1);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setSize(100,30);
        firstNameLabel.setLocation(20,80);
        this.add(firstNameLabel);

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setSize(100,30);
        lastNameLabel.setLocation(320,80);
        this.add(lastNameLabel);

        userNameLabel = new JLabel("User Name");
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

        zipcodeLabel = new JLabel("Zipcode");
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
        button1.setLocation(120,620);
        this.add(button1);

        button2 = new JButton("Register");
        button2.setSize(140,30);
        button2.setLocation(350,620);
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

        creditCard = new JLabel("Credit Card #");
        creditCard.setSize(150,30);
        creditCard.setLocation(20,380);
        this.add(creditCard);

        panel = new JPanel();
        panel.setLocation(120,380);
        panel.setSize(400,240);
        //We should use layout but I do not know yet
        panel.setLayout(null);

        credNo1 = new JTextField();
        credNo1.setLocation(0,0);
        credNo1.setSize(200,30);
        credNo2 = new JTextField();
        credNo2.setLocation(0,60);
        credNo2.setSize(200,30);
        credNo3 = new JTextField();
        credNo3.setLocation(0,120);
        credNo3.setSize(200,30);
        credNo4 = new JTextField();
        credNo4.setLocation(0,180);
        credNo4.setSize(200,30);
        credNo5 = new JTextField();
        credNo5.setLocation(0,240);
        credNo5.setSize(200,30);

        buttonCreditAdd1 = new JButton("Add");
        buttonCreditDel1 = new JButton("Remove");
        buttonCreditAdd2 = new JButton("Add");
        buttonCreditDel2 = new JButton("Remove");
        buttonCreditAdd3 = new JButton("Add");
        buttonCreditDel3 = new JButton("Remove");
        buttonCreditAdd4 = new JButton("Add");
        buttonCreditDel4 = new JButton("Remove");
        buttonCreditAdd5 = new JButton("Add");
        buttonCreditDel5 = new JButton("Remove");

        buttonCreditAdd1.setLocation(230,0);
        buttonCreditAdd1.setSize(100,30);
        buttonCreditAdd2.setLocation(230,60);
        buttonCreditAdd2.setSize(100,30);
        buttonCreditAdd3.setLocation(230,120);
        buttonCreditAdd3.setSize(100,30);
        buttonCreditAdd4.setLocation(230,180);
        buttonCreditAdd4.setSize(100,30);
        buttonCreditAdd5.setLocation(230,240);
        buttonCreditAdd5.setSize(100,30);

        panel.add(credNo1);
        panel.add(credNo2);
        panel.add(credNo3);
        panel.add(credNo4);
        panel.add(credNo5);
        panel.add(buttonCreditAdd1);
        panel.add(buttonCreditAdd2);
        panel.add(buttonCreditAdd3);
        panel.add(buttonCreditAdd4);
        panel.add(buttonCreditAdd5);
        this.add(panel);
        panel.setVisible(true);

    }

    private void addListener(){
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new registerNavigation();
                dispose();
            }
        });

        buttonCreditAdd1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cardNo = credNo1.getText();
                credShow1 = new JLabel(cardNo);
                credShow1.setLocation(0,0);
                credShow1.setSize(200,30);
                panel.add(credShow1);
                panel.remove(credNo1);
                buttonCreditDel1.setLocation(230,0);
                buttonCreditDel1.setSize(100,30);
                panel.remove(buttonCreditAdd1);
                panel.remove(credNo1);
                panel.add(credShow1);
                panel.add(buttonCreditDel1);
                panel.updateUI();
                panel.repaint();
            }
        });

        buttonCreditDel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                credNo1.setText(null);
                panel.remove(credShow1);
                panel.add(credNo1);
                panel.add(buttonCreditAdd1);
                panel.remove(buttonCreditDel1);
                panel.updateUI();
                panel.repaint();
            }
        });

        buttonCreditAdd2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cardNo = credNo2.getText();
                credShow2 = new JLabel(cardNo);
                credShow2.setLocation(0,60);
                credShow2.setSize(200,30);
                panel.add(credShow2);
                panel.remove(credNo2);
                buttonCreditDel2.setLocation(230,60);
                buttonCreditDel2.setSize(100,30);
                panel.remove(buttonCreditAdd2);
                panel.remove(credNo2);
                panel.add(credShow2);
                panel.add(buttonCreditDel2);
                panel.updateUI();
                panel.repaint();
            }
        });

        buttonCreditDel2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                credNo2.setText(null);
                panel.remove(credShow2);
                panel.add(credNo2);
                panel.add(buttonCreditAdd2);
                panel.remove(buttonCreditDel2);
                panel.updateUI();
                panel.repaint();
            }
        });

        buttonCreditAdd3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cardNo = credNo3.getText();
                credShow3 = new JLabel(cardNo);
                credShow3.setLocation(0,120);
                credShow3.setSize(200,30);
                panel.add(credShow3);
                panel.remove(credNo3);
                buttonCreditDel3.setLocation(230,120);
                buttonCreditDel3.setSize(100,30);
                panel.remove(buttonCreditAdd3);
                panel.remove(credNo3);
                panel.add(credShow3);
                panel.add(buttonCreditDel3);
                panel.updateUI();
                panel.repaint();
            }
        });

        buttonCreditDel3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                credNo3.setText(null);
                panel.remove(credShow3);
                panel.add(credNo3);
                panel.add(buttonCreditAdd3);
                panel.remove(buttonCreditDel3);
                panel.updateUI();
                panel.repaint();
            }
        });

        buttonCreditAdd4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cardNo = credNo4.getText();
                credShow4 = new JLabel(cardNo);
                credShow4.setLocation(0,180);
                credShow4.setSize(200,30);
                panel.add(credShow4);
                panel.remove(credNo4);
                buttonCreditDel4.setLocation(230,180);
                buttonCreditDel4.setSize(100,30);
                panel.remove(buttonCreditAdd4);
                panel.remove(credNo4);
                panel.add(credShow4);
                panel.add(buttonCreditDel4);
                panel.updateUI();
                panel.repaint();
            }
        });

        buttonCreditDel4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                credNo4.setText(null);
                panel.remove(credShow4);
                panel.add(credNo4);
                panel.add(buttonCreditAdd4);
                panel.remove(buttonCreditDel4);
                panel.updateUI();
                panel.repaint();
            }
        });

        buttonCreditAdd5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cardNo = credNo5.getText();
                credShow5 = new JLabel(cardNo);
                credShow5.setLocation(0,240);
                credShow5.setSize(200,30);
                panel.add(credShow5);
                panel.remove(credNo5);
                buttonCreditDel5.setLocation(230,240);
                buttonCreditDel5.setSize(100,30);
                panel.remove(buttonCreditAdd5);
                panel.remove(credNo5);
                panel.add(credShow5);
                panel.add(buttonCreditDel5);
                panel.updateUI();
                panel.repaint();
            }
        });

        buttonCreditDel5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                credNo5.setText(null);
                panel.remove(credShow5);
                panel.add(credNo5);
                panel.add(buttonCreditAdd5);
                panel.remove(buttonCreditDel5);
                panel.updateUI();
                panel.repaint();
            }
        });
        
        // Click "Register"
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Here we add the user register data into the database

                //We first get the message
                String usernameInput = userName.getText();
                String firstNameInput =  firstName.getText();
                String lastNameInput  = lastName.getText();
                String passwordInput = password.getText();
                String confirmPasswordInput = confirmPassword.getText();
                String companyInput = (String)comboBox2.getSelectedItem();
                String streetAddressInput = streetAddress.getText();
                String cityInput = city.getText();
                String stateInput = (String)comboBox1.getSelectedItem();
                String zipCodeInput = zipCode.getText();
                String creditCard1Input = credShow1.getText();
                String creditCard2Input = credShow2.getText();
                String creditCard3Input = credShow3.getText();
                String creditCard4Input = credShow4.getText();
                String creditCard5Input = credShow5.getText();


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
                
                // Must have at least 1 nonempty credit card
                if(creditCard1Input.length()==0&&creditCard2Input.length()==0&&creditCard3Input.length()==0
                		&&creditCard4Input.length()==0&&creditCard5Input.length()==0)
                {
                    JOptionPane.showMessageDialog(null, "Must enter at least one credit card number!");
                    return;
                }
                
                // Check length and digits of credit card
                if(creditCard1Input.length()!=0&&(creditCard1Input.length()!=16||!isNumeric(creditCard1Input)))
                {
                    JOptionPane.showMessageDialog(null, "Credit card 1 invalid, please confirm again!");
                    return;
                }
                if(creditCard2Input.length()!=0&&(creditCard2Input.length()!=16||!isNumeric(creditCard2Input)))
                {
                    JOptionPane.showMessageDialog(null, "Credit card 2 invalid, please confirm again!");
                    return;
                }
                if(creditCard3Input.length()!=0&&(creditCard3Input.length()!=16||!isNumeric(creditCard3Input)))
                {
                    JOptionPane.showMessageDialog(null, "Credit card 3 invalid, please confirm again!");
                    return;
                }
                if(creditCard4Input.length()!=0&&(creditCard4Input.length()!=16||!isNumeric(creditCard4Input)))
                {
                    JOptionPane.showMessageDialog(null, "Credit card 4 invalid, please confirm again!");
                    return;
                }
                if(creditCard5Input.length()!=0&&(creditCard5Input.length()!=16||!isNumeric(creditCard5Input)))
                {
                    JOptionPane.showMessageDialog(null, "Credit card 5 invalid, please confirm again!");
                    return;
                }
                
                //Here we make the connection to MySQL
                DBManager dbManager = new DBManager();
                // Register as user and customer
                try{
                    //Here we call the stored procedure
                    CallableStatement statement0 =
                            dbManager.connection.prepareCall("call manager_customer_register(?,?,?,?,?,?,?,?,?)");
                    //Here we put the data into the procedure
                    statement0.setString(1,usernameInput);
                    statement0.setString(2,passwordInput);
                    statement0.setString(3,firstNameInput);
                    statement0.setString(4,lastNameInput);
                    statement0.setString(5,companyInput);
                    statement0.setString(6,streetAddressInput);
                    statement0.setString(7,cityInput);
                    statement0.setString(8,stateInput);
                    statement0.setString(9,zipCodeInput);
                    statement0.execute();        
                    statement0.close();
                }catch (Exception event){
                    JOptionPane.showMessageDialog(null, "Duplicate username or address, please try again!");
                	userName.setText(null);
                	password.setText(null);
                	confirmPassword.setText(null);
                    event.printStackTrace();
                    dbManager.close();
                    return;
                }
                
                try{
                    //Here we call ustomer_add_creditcard
                	if(creditCard1Input.length()!=0)
                	{
                        CallableStatement statement1 =
                                dbManager.connection.prepareCall("call manager_customer_add_creditcard(?,?)");
                        //Here we put the data into the procedure
                        statement1.setString(1,usernameInput);
                        statement1.setString(2,creditCard1Input);
                        statement1.execute();
                        statement1.close();
                	}
                	if(creditCard2Input.length()!=0)
                	{
                        CallableStatement statement2 =
                                dbManager.connection.prepareCall("call manager_customer_add_creditcard(?,?)");
                        //Here we put the data into the procedure
                        statement2.setString(1,usernameInput);
                        statement2.setString(2,creditCard2Input);
                        statement2.execute();
                        statement2.close();
                	}
                	if(creditCard3Input.length()!=0)
                	{
                        CallableStatement statement3 =
                                dbManager.connection.prepareCall("call manager_customer_add_creditcard(?,?)");
                        //Here we put the data into the procedure
                        statement3.setString(1,usernameInput);
                        statement3.setString(2,creditCard3Input);
                        statement3.execute();
                        statement3.close();
                	}
                	if(creditCard4Input.length()!=0)
                	{
                        CallableStatement statement4 =
                                dbManager.connection.prepareCall("call manager_customer_add_creditcard(?,?)");
                        //Here we put the data into the procedure
                        statement4.setString(1,usernameInput);
                        statement4.setString(2,creditCard4Input);
                        statement4.execute();
                        statement4.close();
                	}
                	if(creditCard5Input.length()!=0)
                	{
                        CallableStatement statement5 =
                                dbManager.connection.prepareCall("call manager_customer_add_creditcard(?,?)");
                        //Here we put the data into the procedure
                        statement5.setString(1,usernameInput);
                        statement5.setString(2,creditCard5Input);
                        statement5.execute();
                        statement5.close();
                	}
                    JOptionPane.showMessageDialog(null, "Manager_customer registration succeed!");
                    dbManager.close();
                }catch (Exception event){
                    JOptionPane.showMessageDialog(null, "Invalid credit cards, please confirm again!");
                    try {
						Statement statement = dbManager.connection.createStatement();
						statement.execute("DELETE FROM team25.users WHERE (username = \"" +  usernameInput + "\")");
						statement.close();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Delete unqualified user failed!");
						e1.printStackTrace();
					}
                    dbManager.close();
                    event.printStackTrace();
                    return;
                }
            }
        });
        
        
    }

    
    public ManagerCustomerRegister(String title){
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


