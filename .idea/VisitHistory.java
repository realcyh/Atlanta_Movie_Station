package CS4400Final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import  java.util.*;
import org.jdesktop.swingx.JXDatePicker;


public class VisitHistory extends JFrame {
    //label1 is the head of this window
    private JLabel label1;
    //label2 to label6 are the labels for test
    private JLabel companyNameLabel;
    private JLabel visitDateLabel;

    private JButton button1;
    private JButton button2;

    private JComboBox<String> comboBox1;

    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel panel;

    final JXDatePicker datepick1 = new JXDatePicker();
    final JXDatePicker datepick2 = new JXDatePicker();

    protected String username;
    protected String userType;

    private int mark = 0;

    public VisitHistory(){
        init();
        addComponent();
        addListener();
    }

    public void init()
    {
        this.setSize(660,520);
        this.setVisible(true);
        this.setTitle("Visit History");
        this.setLayout(null);
        this.setLocation(500, 200);
        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addComponent(){
        label1 = new JLabel("Visit History");
        label1.setSize(300,200);
        label1.setFont(new Font("Dialog",Font.BOLD, 25));
        label1.setLocation(280,-50);
        this.add(label1);

        companyNameLabel = new JLabel("Company Name");
        companyNameLabel.setSize(100,30);
        companyNameLabel.setLocation(20,100);
        this.add(companyNameLabel);

        visitDateLabel = new JLabel("Visit Date");
        visitDateLabel.setSize(70,30);
        visitDateLabel.setLocation(260,100);
        this.add(visitDateLabel);

        button1 = new JButton("Filter");
        button1.setSize(140,30);
        button1.setLocation(260,150);
        this.add(button1);

        button2 = new JButton("Back");
        button2.setSize(140,30);
        button2.setLocation(260,430);
        this.add(button2);

        java.util.List<String> companyList = new java.util.ArrayList<String>(Arrays.asList(new String[] {
                "4400 Theater Company", "AI Theater Company", "Awesome Theater Company", "EZ Theater Company"}
        ));

        comboBox1 = new JComboBox<String>();
        comboBox1.addItem(" ");
        for(String company : companyList){
            comboBox1.addItem(company);
        }
        comboBox1.setLocation(120,100);
        comboBox1.setSize(130,30);
        this.add(comboBox1);

        Date date1 = new Date();
        Date date2 = new Date();

        datepick1.setDate(date1);
        datepick1.setSize(150,30);
        datepick1.setLocation(330,100);
        this.add(datepick1);

        datepick2.setDate(date2);
        datepick2.setSize(150,30);
        datepick2.setLocation(480,100);
        this.add(datepick2);

    }

    private void addListener(){

        // Click "Filter"
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String usernameInput = username;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d1 = datepick1.getDate();
                String minVisitDate = sdf.format(d1);
                Date d2 = datepick2.getDate();
                String maxVisitDate = sdf.format(d2);
                String companyName = (String) comboBox1.getSelectedItem();

                DBManager dbManager = new DBManager();
                // Register as user and customer
                try{
                    //Here we call the stored procedure
                    CallableStatement statement =
                            dbManager.connection.prepareCall("call user_filter_visitHistory(?,?,?)");
                    //Here we put the data into the procedure
                    statement.setString(1,usernameInput);
                    statement.setString(2, minVisitDate);
                    statement.setString(3, maxVisitDate);
                    statement.execute();
                    ResultSet result = statement.executeQuery("select * from UserVisitHistory");
                    // No matching user
                    if(!result.isBeforeFirst())
                    {
                        JOptionPane.showMessageDialog(null, "No result!");
                        String[] dataTitle = {"Theater", "Address", "Company", "VisitDate"};
                        if (mark == 0) {
                            tableModel = new DefaultTableModel(dataTitle, 0);
                            table = new JTable(tableModel);
                            table.setRowHeight(30);
                            scrollPane = new JScrollPane(table);
                            scrollPane.setSize(600, 210);
                            scrollPane.setLocation(30, 200);
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

                        String[] dataTitle = {"Theater", "Address", "Company", "VisitDate"};

                        if (mark == 0) {
                            tableModel = new DefaultTableModel(dataTitle, rowCount);
                            table = new JTable(tableModel);
                            table.setRowHeight(30);
                            scrollPane = new JScrollPane(table);
                            scrollPane.setSize(600, 210);
                            scrollPane.setLocation(30, 200);
                            add(scrollPane);
                            mark++;
                        }

                        tableModel = new DefaultTableModel(dataTitle, rowCount);
                        Object obj[][]= new Object[rowCount][4];

                        int i=0;
                        while(result.next())
                        {
                            String theater = result.getString(1);

                            StringBuffer addr = new StringBuffer();
                            addr.append(result.getString(2));
                            addr.append(", ");
                            addr.append(result.getString(3));
                            addr.append(", ");
                            addr.append(result.getString(4));
                            addr.append(" ");
                            addr.append(result.getString(5));
                            String address = addr.toString();

                            String company = result.getString(6);

                            String visitDate = result.getString(7);


                            if (companyName.equals(" ")) {
                                obj[i][0]=theater;
                                obj[i][1]=address;
                                obj[i][2]=company;
                                obj[i][3]=visitDate;
                                i++;
                            } else if (companyName.equals(company)) {
                                obj[i][0]=theater;
                                obj[i][1]=address;
                                obj[i][2]=company;
                                obj[i][3]=visitDate;
                                i++;
                            }
                        }
                        tableModel.setDataVector(obj, new Object[]{"Theater", "Address", "Company", "VisitDate"});
                        table.setModel(tableModel);

                    }
//                    table.repaint();
//                    scrollPane.repaint();

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


        // Click "Back"
        button2.addActionListener(new ActionListener() {
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
                if (userType.equals("customer")) {
                    customerOnlyFunc f = new customerOnlyFunc();
                    f.username = username;
                    dispose();
                }
                if (userType.equals("user")) {
                    userOnlyFunc f = new userOnlyFunc();
                    f.username = username;
                    dispose();
                }
            }
        });
    }

}



