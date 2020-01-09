package CS4400Final;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRegister extends JFrame{
    
	//label1 is the header of this window
    JLabel label1;
    
    //label is the name of the text region
    JLabel firstNameLabel;
    JLabel lastNameLabel;
    JLabel userNameLabel;
    JLabel passwordLabel;
    JLabel confirmPasswordLabel;

    //textfield for text input
    JTextField firstName;
    JTextField lastName;
    JTextField userName;

    //passwordfield for password input
    JPasswordField password;
    JPasswordField confirmPassword;


    //button1 is return button
    //button2 is regist button
    JButton button1 ;
    JButton button2;


    public void init()
    {
        this.setSize(650,450);
        this.setVisible(true);
        this.setTitle("User Register");
        this.setLayout(null);
        this.setLocation(500, 100);
        this.setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addComponent(){
      label1 = new JLabel("User Registration");
      label1.setSize(400,200);
      label1.setFont(new Font("Dialog",Font.BOLD, 25));
      label1.setLocation(210,-50);
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

      passwordLabel = new JLabel("Password");
      passwordLabel.setSize(100,30);
      passwordLabel.setLocation(20,200);
      this.add(passwordLabel);

      confirmPasswordLabel = new JLabel("Confirm Password");
      confirmPasswordLabel.setSize(150,30);
      confirmPasswordLabel.setLocation(320,200);
      this.add(confirmPasswordLabel);

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

      button1 = new JButton("Back");
      button1.setSize(140,30);
      button1.setLocation(120,340);
      this.add(button1);

      button2 = new JButton("Register");
      button2.setSize(140,30);
      button2.setLocation(350,340);
      this.add(button2);
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
                String lastNameInput  = lastName.getText();
                String passwordInput = password.getText();
                String confirmPasswordInput = confirmPassword.getText();
                //Here we check whether 2 passwords are the same
                if (!passwordInput.equals(confirmPasswordInput)) {
                    JOptionPane.showMessageDialog(null, "Password must be consistent!");
                        return;
                    }
                
                // All fields required
                if(usernameInput.length() == 0 || firstNameInput.length() == 0 || lastNameInput.length() == 0
                        || passwordInput.length() == 0 || confirmPasswordInput.length() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!");
                    return;
                }
                
                // Password length
                if(passwordInput.length()<8)
                {
                    JOptionPane.showMessageDialog(null, "Password must have at least 8 characters!");
                    return;
                }
                
                //Here we make the connection to MySQL
                DBManager dbManager = new DBManager();
                try{
                    //Here we call the stored procedure
                    CallableStatement statement =
                            dbManager.connection.prepareCall("call user_register(?,?,?,?)");
                    //Here we put the data into the procedure
                    statement.setString(1,usernameInput);
                    statement.setString(2,passwordInput);
                    statement.setString(3,firstNameInput);
                    statement.setString(4,lastNameInput);
                    statement.execute();
                    JOptionPane.showMessageDialog(null, "User registration succeed!");
                    firstName.setText(null);
                    lastName.setText(null);            
                    statement.close();
                    dbManager.close();
                }catch (Exception event){
                    JOptionPane.showMessageDialog(null, "Username already existed, please try again!");
                    event.printStackTrace();
                }finally {
                	userName.setText(null);
                	password.setText(null);
                	confirmPassword.setText(null);
                }
            }
        });
    }

    public UserRegister(String title){
        super(title);
        init();
        addComponent();
        addListener();
    }
}
