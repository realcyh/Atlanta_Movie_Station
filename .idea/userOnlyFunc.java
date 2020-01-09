package CS4400Final;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class userOnlyFunc extends JFrame {
    private JLabel label;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    
    protected String username;

    public userOnlyFunc(){
        init();
        addComponent();
        addListener();
    }

    private void init(){
        this.setSize(500,350);
        this.setVisible(true);
        this.setTitle("User Functionality");
        this.setLayout(null);
        this.setLocation(500, 200);
    }

    private void addComponent(){
        label = new JLabel("User Functionality");
        label.setSize(400,200);
        label.setFont(new Font("Dialog",Font.BOLD, 25));
        label.setLocation(140,-60);
        this.add(label);

        button1 = new JButton("Explore Theater");
        button1.setSize(200,50);
        button1.setLocation(150,80);
        button1.setBackground(Color.white);
        button1.setFont(new Font("Dialog",Font.BOLD, 20));
        this.add(button1);

        button2 = new JButton("Visit History");
        button2.setSize(200,50);
        button2.setLocation(150,140);
        button2.setBackground(Color.white);
        button2.setFont(new Font("Dialog",Font.BOLD, 22));
        this.add(button2);

        button3 = new JButton("Back");
        button3.setSize(200,50);
        button3.setLocation(150,200);
        button3.setBackground(Color.white);
        button3.setFont(new Font("Dialog",Font.BOLD, 22));
        this.add(button3);

        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private void addListener(){
        // Click "back"
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginWindow();
                dispose();
            }
        });
        // Click "Explore theater"
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exploreTheater f = new exploreTheater();
                f.username=username;
                f.userType="user";
                dispose();
            }
        });
        // Click "Visit History"
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VisitHistory f= new VisitHistory();
                f.username=username;
                f.userType="user";
                dispose();
            }
        });
        
    }
    
    }