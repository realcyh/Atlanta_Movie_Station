package CS4400Final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import  java.util.*;


public class ViewHistory extends JFrame {
    //label1 is the head of this window
    private JLabel label1;
    //label2 to label6 are the labels for test

    private JButton button1;

    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel panel;

    protected String username;
    protected String userType;

    private int mark = 0;

    public ViewHistory(){
        init();
        addComponent();
        addListener();
    }

    public void init()
    {
        this.setSize(660,500);
        this.setVisible(true);
        this.setTitle("View History");
        this.setLayout(null);
        this.setLocation(500, 200);
        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addComponent(){
        label1 = new JLabel("View History");
        label1.setSize(300,200);
        label1.setFont(new Font("Dialog",Font.BOLD, 25));
        label1.setLocation(250,-50);
        this.add(label1);

        button1 = new JButton("Back");
        button1.setSize(140,30);
        button1.setLocation(260,150);
        this.add(button1);

    }

    private void addListener(){

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {
                String usernameInput = username;
                DBManager dbManager = new DBManager();
                // Register as user and customer
                try{
                    //Here we call the stored procedure
                    CallableStatement statement =
                            dbManager.connection.prepareCall("call customer_view_history(?)");
                    //Here we put the data into the procedure
                    statement.setString(1,usernameInput);
                    statement.execute();
                    ResultSet result = statement.executeQuery("select * from CosViewHistory");
                    // No matching user
                    if(!result.isBeforeFirst())
                    {
                        JOptionPane.showMessageDialog(null, "No result!");
                        String[] dataTitle = {"Movie", "Theater", "Company", "Card#", "View Date"};
                        if (mark == 0) {
                            tableModel = new DefaultTableModel(dataTitle, 0);
                            table = new JTable(tableModel);
                            table.setRowHeight(30);
                            scrollPane = new JScrollPane(table);
                            scrollPane.setSize(600, 230);
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

                        String[] dataTitle = {"Movie", "Theater", "Company", "Card#", "View Date"};

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
                            String movie = result.getString(1);
                            String theater = result.getString(2);
                            String company = result.getString(3);
                            String card = result.getString(4);
                            String viewDate = result.getString(5);

                            obj[i][0]=movie;
                            obj[i][1] = theater;
                            obj[i][2] = company;
                            obj[i][3] = card;
                            obj[i][4] = viewDate;
                            i++;

                        }
                        tableModel.setDataVector(obj, new Object[]{"Movie", "Theater", "Company", "Card#", "View Date"});
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
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (userType.equals("adminCustomer")) {
                    adminCustomerFunc f = new adminCustomerFunc();
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
            }
        });
    }

}



