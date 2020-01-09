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


public class ScheduleMovie extends JFrame {
    //label1 is the head of this window
    JLabel label1;
    //label2 to label6 are the labels for test
    JLabel movieNameLabel;
    JLabel releaseDateLabel;
    JLabel playDateLabel;

    JButton button1;
    JButton button2;

    java.util.List<String> movieList;
    JComboBox<String> comboBox1;

    final JXDatePicker datepick1 = new JXDatePicker();
    final JXDatePicker datepick2 = new JXDatePicker();
    Date date1 = new Date();
    Date date2 = new Date();

    protected String username;
    protected String userType;

    public ScheduleMovie(){
        init();
        addComponent();
        addListener();
    }

    public void init()
    {
        this.setSize(560,300);
        this.setVisible(true);
        this.setTitle("Schedule Movie");
        this.setLayout(null);
        this.setLocation(500, 200);
        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addComponent(){
        label1 = new JLabel("Schedule Movie");
        label1.setSize(400,200);
        label1.setFont(new Font("Dialog",Font.BOLD, 25));
        label1.setLocation(160,-50);
        this.add(label1);

        movieNameLabel = new JLabel("Name");
        movieNameLabel.setSize(50,30);
        movieNameLabel.setLocation(20,100);
        this.add(movieNameLabel);

        releaseDateLabel = new JLabel("Release Date");
        releaseDateLabel.setSize(90,30);
        releaseDateLabel.setLocation(290,100);
        this.add(releaseDateLabel);

        playDateLabel = new JLabel("Play Date");
        playDateLabel.setSize(90,30);
        playDateLabel.setLocation(290,140);
        this.add(playDateLabel);

        button1 = new JButton("Back");
        button1.setSize(140,30);
        button1.setLocation(60,190);
        this.add(button1);

        button2 = new JButton("Add");
        button2.setSize(140,30);
        button2.setLocation(350,190);
        this.add(button2);


        movieList = new java.util.ArrayList<String>();
        comboBox1 = new JComboBox<String>();
        comboBox1.addItem(" ");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                DBManager dbManager = new DBManager();
                try{
                    //Here we call the stored procedure
                	Statement statement2 = dbManager.connection.createStatement();
                    ResultSet result2 = statement2.executeQuery("select managerID from theater where managerID = \""+username+"\"");
                    if (!result2.isBeforeFirst()) {
                        dbManager.close();
                        return;
                    }
                    
                    Statement statement = dbManager.connection.createStatement();
                    ResultSet result = statement.executeQuery("select movieName from movie");
                    while (result.next()) {
                        movieList.add(result.getString(1));
                    }

                    for(String movie : movieList){
                        comboBox1.addItem(movie);
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

        comboBox1.setLocation(70,100);
        comboBox1.setSize(200,30);
        this.add(comboBox1);


        datepick1.setDate(date1);
        comboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String movie = (String) comboBox1.getSelectedItem();
                Date releaseDate = new Date();
                DBManager dbManager = new DBManager();
                // Register as user and customer
                try{
                    //Here we call the stored procedure
                    Statement statement = dbManager.connection.createStatement();
                    ResultSet result = statement.executeQuery("select * from movie");
                    while (result.next()) {
                        if (movie.equals(result.getString(1))) {
                            releaseDate = result.getDate(2);
                            break;
                        }
                    }
                    datepick1.setDate(releaseDate);
                    dbManager.close();
                }catch (Exception event){
                    JOptionPane.showMessageDialog(null, "Error!");
                    event.printStackTrace();
                    dbManager.close();
                    return;
                }
            }
        });

        datepick1.setSize(150,30);
        datepick1.setLocation(380,100);
        this.add(datepick1);

        datepick2.setDate(date2);
        datepick2.setSize(150,30);
        datepick2.setLocation(380,140);
        this.add(datepick2);

    }

    private void addListener(){

        // Click "Back"
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (userType.equals("manager")) {
                    managerOnlyFunc f = new managerOnlyFunc();
                    f.username = username;
                    dispose();
                }
                if (userType.equals("managerCustomer")) {
                    managerCustomerFunc f = new managerCustomerFunc();
                    f.username = username;
                    dispose();
                }
            }
        });

        // Click "Add"
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Here we add the user register data into the database

                String usernameInput = username;
                String movieName = (String) comboBox1.getSelectedItem();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d1 = datepick1.getDate();
                String releaseDate = sdf.format(d1);
                Date d2 = datepick2.getDate();
                String playDate = sdf.format(d2);
                if (movieName.equals(" ")) {
                    JOptionPane.showMessageDialog(null,"Please select a movie!");
                    return;
                }
                if (playDate.compareTo(releaseDate)<0) {
                    JOptionPane.showMessageDialog(null, "Cannot schedule a movie before its release date!");
                    return;
                }
                
                //Here we make the connection to MySQL
                DBManager dbManager = new DBManager();
                // Register as user and customer
                try{
                    //Here we call the stored procedure
                    CallableStatement statement =
                            dbManager.connection.prepareCall("call manager_schedule_mov(?,?,?,?)");
                    //Here we put the data into the procedure

                    statement.setString(1,usernameInput);
                    statement.setString(2,movieName);
                    statement.setString(3,releaseDate);
                    statement.setString(4,playDate);
                    statement.execute();
                    JOptionPane.showMessageDialog(null, "Succeed!");
                    statement.close();
                    dbManager.close();
                }catch (Exception event){
                    JOptionPane.showMessageDialog(null, "The movie has been scheduled on this day!");
                    event.printStackTrace();
                    dbManager.close();
                    return;
                }
            }
        });
    }

}
