package CS4400Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.CallableStatement;
import java.sql.ResultSet;

public  class LoginWindow extends JFrame{
	private JLabel lable1;
    private JLabel lable2;
    //Atlantic Movie Head
    private JLabel label3;

    private JTextField userName;
    private JPasswordField password;

    private JButton bt1;
    private JButton bt2;
   
    public LoginWindow()
    {
        this.init();
        this.addComponent();
        this.addListener();
    }

    public void init()
    {
        this.setSize(500,300);
        this.setVisible(true);
        this.setTitle("Atalanta Movie Login");
        this.setLayout(null);
        this.setLocation(500, 200);
    }
    private void addComponent()
    {
        lable1 = new JLabel("Username");
        lable1.setSize(120,70);
        lable1.setLocation(100,80);
        this.add(lable1);
        lable2 = new JLabel("Password");
        lable2.setSize(120,70);
        lable2.setLocation(100,130);
        this.add(lable2);
        //Add the Atlantic Movie Head
        label3 = new JLabel("Atlanta Movie Login");
        label3.setSize(400,200);
        label3.setFont(new Font("Dialog",Font.BOLD, 25));
        label3.setLocation(120,-50);
        this.add(label3);

        userName = new JTextField();
        userName.setSize(180,30);
        userName.setLocation(160,100);
        this.add(userName);
        
        password = new JPasswordField();
        password.setSize(180,30);
        password.setLocation(160,150);
        this.add(password);

        bt1 = new JButton("Login");
        bt1.setSize(140,30);
        bt1.setLocation(100,205);
        bt1.setBackground(Color.white);
        this.add(bt1);

        bt2 = new JButton("Register");
        bt2.setSize(140,30);
        bt2.setLocation(250,205);
        bt2.setBackground(Color.white);
        this.add(bt2);
        this.setBackground(Color.blue);
     
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void addListener()
    {   // Click "login"
        bt1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	//We first get the message
                String usernameInput = userName.getText();
                String passwordInput = password.getText();
                
                // All fields required
                if(usernameInput.length() == 0 || passwordInput.length() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!");
                    return;
                }
                
                // Password length
                if(passwordInput.length()<8)
                {
                    JOptionPane.showMessageDialog(null, "Password must have at least 8 characters!");
                    userName.setText(null);
                	password.setText(null);
                    return;
                }
                
                // Connect to DB
                DBManager dbManager = new DBManager();
                // Register as user and customer
                try{
                    //Here we call the stored procedure
                    CallableStatement statement =
                            dbManager.connection.prepareCall("call user_login(?,?)");
                    //Here we put the data into the procedure
                    statement.setString(1,usernameInput);
                    statement.setString(2,passwordInput);
                    statement.execute();      
                    ResultSet result = statement.executeQuery("select * from UserLogin");
                    // No matching user
                    if(!result.next())
                    {
                    	JOptionPane.showMessageDialog(null, "Wrong username or password, please try again!");
                    	userName.setText(null);
                    	password.setText(null);
                    	return;
                    }
                    else
                    {
                    	String status = result.getString(2);
                    	int isCustomer = result.getInt(3);
                    	int isAdmin = result.getInt(4);
                    	int isManager = result.getInt(5);
                    	
                    	// check status
                    	if(status.equals("Pending")||status.equals("Declined"))
                    	{
                    		JOptionPane.showMessageDialog(null, "Unauthorized user!");
                        	userName.setText(null);
                        	password.setText(null);
                        	return;
                    	}
                    	if(isAdmin==1)
                    	{
                    		if(isCustomer==1)
                    		{
                    			adminCustomerFunc f = new adminCustomerFunc();
                    			f.username = new String(usernameInput);
                                dispose();
                    		}
                    		else
                    		{
                    			adminOnlyFunc f = new adminOnlyFunc();
                    			f.username = usernameInput;
                                dispose();
                    		}
                    	}
                    	else if(isCustomer==1)
                    	{
                    		if(isManager==1)
                    		{
                    			managerCustomerFunc f = new managerCustomerFunc();
                    			f.username = usernameInput;
                                dispose();
                    		}
                    		else
                    		{
                    			customerOnlyFunc f = new customerOnlyFunc();
                    			f.username = usernameInput;
                                dispose();
                    		}
                    	}
                    	else
                    	{
                    		if(isManager==1)
                    		{
                    			managerOnlyFunc f = new managerOnlyFunc();
                    			f.username = usernameInput;
                                dispose();
                    		}
                    		else
                    		{
                    			userOnlyFunc f = new userOnlyFunc();
                    			f.username = usernameInput;
                                dispose();
                    		}
                    	}
                    }
                    statement.close();
                }catch (Exception event){
                    JOptionPane.showMessageDialog(null, "System error!");
                	userName.setText(null);
                	password.setText(null);
                    event.printStackTrace();
                    dbManager.close();
                    return;
                }
            }
        });

        // Click "register"
        bt2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new registerNavigation();
                dispose();
            }
        });

    }

    public static void main(String[] args) {
        new LoginWindow();
    	//new manageUser();
        //new ExploreMovie("adminCustomer");
    	//new manageCompany();
    	//new exploreTheater();
    	//new CreateMovie();
        //new ViewHistory();
    }
}

