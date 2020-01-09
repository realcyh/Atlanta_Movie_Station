package CS4400Final;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import  java.util.*;
import org.jdesktop.swingx.JXDatePicker;


public class CreateMovie extends JFrame {
    //label1 is the head of this window
    JLabel label1;
    //label2 to label6 are the labels for test
    JLabel movieNameLabel;
    JLabel durationLabel;
    JLabel releaseDateLabel;

    JTextField movieName;
    JTextField duration;

    JButton button1;
    JButton button2;

    final JXDatePicker datepick = new JXDatePicker();
    Date date = new Date();

    protected String username;
    protected String userType;

    public CreateMovie(){
        init();
        addComponent();
        addListener();
    }

    public void init()
    {
        this.setSize(500,290);
        this.setVisible(true);
        this.setTitle("Create Movie");
        this.setLayout(null);
        this.setLocation(500, 200);
        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addComponent(){
        label1 = new JLabel("Create Movie");
        label1.setSize(400,200);
        label1.setFont(new Font("Dialog",Font.BOLD, 25));
        label1.setLocation(180,-50);
        this.add(label1);

        movieNameLabel = new JLabel("Name");
        movieNameLabel.setSize(50,30);
        movieNameLabel.setLocation(20,100);
        this.add(movieNameLabel);

        movieName = new JTextField();
        movieName.setSize(150,30);
        movieName.setLocation(70,100);
        this.add(movieName);

        durationLabel = new JLabel("Duration");
        durationLabel.setSize(80, 30);
        durationLabel.setLocation(290, 100);
        this.add(durationLabel);

        duration = new JTextField();
        duration.setSize(100, 30);
        duration.setLocation(350, 100);
        this.add(duration);

        releaseDateLabel = new JLabel("Release Date");
        releaseDateLabel.setSize(100,30);
        releaseDateLabel.setLocation(70,140);
        this.add(releaseDateLabel);

        datepick.setDate(date);
        datepick.setSize(150,30);
        datepick.setLocation(160,140);
        this.add(datepick);

        button1 = new JButton("Back");
        button1.setSize(140,30);
        button1.setLocation(60,200);
        this.add(button1);

        button2 = new JButton("Create");
        button2.setSize(140,30);
        button2.setLocation(300,200);
        this.add(button2);

    }

    private void addListener(){

        // Click "Back"
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (userType.equals("admin")) {
                    adminOnlyFunc f = new adminOnlyFunc();
                    f.username = username;
                    dispose();
                }
                if (userType.equals("adminCustomer")) {
                    adminCustomerFunc f = new adminCustomerFunc();
                    f.username = username;
                    dispose();
                }
            }
        });

        // Click "Create"
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Here we add the user register data into the database

                String movName = movieName.getText();
                String movDuration = duration.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d = datepick.getDate();
                String releaseDate = sdf.format(d);

                if (movName.length()<=0 || movDuration.length()<=0) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!");
                    return;
                }
                if (!isNumeric(movDuration)) {
                    JOptionPane.showMessageDialog(null, "Duration must be a number!");
                    return;
                }

                //Here we make the connection to MySQL
                DBManager dbManager = new DBManager();
                // Register as user and customer
                try{
                    //Here we call the stored procedure
                    CallableStatement statement =
                            dbManager.connection.prepareCall("call admin_create_mov(?,?,?)");
                    //Here we put the data into the procedure

                    statement.setString(1, movName);
                    statement.setString(2, movDuration);
                    statement.setString(3,releaseDate);
                    statement.execute();
                    JOptionPane.showMessageDialog(null, "Succeed!");
                    statement.close();
                    dbManager.close();
                }catch (Exception event){
                    JOptionPane.showMessageDialog(null, "The movie already exists!");
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
