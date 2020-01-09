package CS4400Final;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class adminOnlyFunc extends JFrame {
	// status variables
    private JLabel label;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    
    protected String username;

    public adminOnlyFunc(){
        init();
        addComponent();
        addListener();
    }

    private void init(){
        this.setSize(500,320);
        this.setVisible(true);
        this.setTitle("Admin-Only Functionality");
        this.setLayout(null);
        this.setLocation(500, 200);
    }

    private void addComponent(){
        label = new JLabel("Admin-Only Functionality");
        label.setSize(400,200);
        label.setFont(new Font("Dialog",Font.BOLD, 25));
        label.setLocation(90,-60);
        this.add(label);

        button1 = new JButton("Manage User");
        button1.setSize(200,50);
        button1.setLocation(40,80);
        button1.setBackground(Color.white);
        button1.setFont(new Font("Dialog",Font.BOLD, 22));
        this.add(button1);

        button2 = new JButton("Explore Theater");
        button2.setSize(200,50);
        button2.setLocation(260,80);
        button2.setBackground(Color.white);
        button2.setFont(new Font("Dialog",Font.BOLD, 18));
        this.add(button2);

        button3 = new JButton("Manage Company");
        button3.setSize(200,50);
        button3.setLocation(40,140);
        button3.setBackground(Color.white);
        button3.setFont(new Font("Dialog",Font.BOLD, 18));
        this.add(button3);
        
        button4 = new JButton("Visit History");
        button4.setSize(200,50);
        button4.setLocation(260,140);
        button4.setBackground(Color.white);
        button4.setFont(new Font("Dialog",Font.BOLD, 20));
        this.add(button4);
        
        button5 = new JButton("Create Movie");
        button5.setSize(200,50);
        button5.setLocation(40,200);
        button5.setBackground(Color.white);
        button5.setFont(new Font("Dialog",Font.BOLD, 20));
        this.add(button5);
        
        button6 = new JButton("Back");
        button6.setSize(200,50);
        button6.setLocation(260,200);
        button6.setBackground(Color.white);
        button6.setFont(new Font("Dialog",Font.BOLD, 22));
        this.add(button6);

        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private void addListener(){
        // Click "Manage User"
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manageUser f = new manageUser();
                f.username=username;
                f.usertype="admin";
                dispose();
            }
        });
        // Click "Explore Theater"
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exploreTheater f = new exploreTheater();
                f.username=username;
                f.userType="admin";
                dispose();
            }
        });
        // Click "Manage Company"
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                manageCompany f = new manageCompany();
                f.username=username;
                f.usertype="admin";
                dispose();
            }
        });
        // Click "Visit History"
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VisitHistory f = new VisitHistory();
                f.username=username;
                f.userType="admin";
                dispose();
            }
        });
        // Click "Create Movie"
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateMovie f = new CreateMovie();
                f.username=username;
                f.userType="admin";
                dispose();
            }
        });
        // Click "Back"
        button6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginWindow();
                dispose();
            }
        });

        
    }
    
}