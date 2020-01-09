package CS4400Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class registerNavigation extends JFrame {
    private JLabel label;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;

    public registerNavigation(){
        init();
        addComponent();
        addListener();
    }

    private void init(){
        this.setSize(500,450);
        this.setVisible(true);
        this.setTitle("Atalanta Movie Login");
        this.setLayout(null);
        this.setLocation(500, 200);
    }

    private void addComponent(){
        label = new JLabel("Register Navigation");
        label.setSize(400,200);
        label.setFont(new Font("Dialog",Font.BOLD, 25));
        label.setLocation(130,-50);
        this.add(label);

        button1 = new JButton("User Only");
        button1.setSize(200,50);
        button1.setLocation(150,80);
        button1.setBackground(Color.white);
        button1.setFont(new Font("Dialog",Font.BOLD, 24));
        this.add(button1);

        button2 = new JButton("Customer Only");
        button2.setSize(200,50);
        button2.setLocation(150,140);
        button2.setBackground(Color.white);
        button2.setFont(new Font("Dialog",Font.BOLD, 22));
        this.add(button2);

        button3 = new JButton("Manager Only");
        button3.setSize(200,50);
        button3.setLocation(150,200);
        button3.setBackground(Color.white);
        button3.setFont(new Font("Dialog",Font.BOLD, 22));
        this.add(button3);

        button4 = new JButton("Manager-Customer");
        button4.setSize(200,50);
        button4.setLocation(150,260);
        button4.setBackground(Color.white);
        button4.setFont(new Font("Dialog",Font.BOLD, 17));
        this.add(button4);

        button5 = new JButton("Back");
        button5.setSize(200,50);
        button5.setLocation(150,320);
        button5.setBackground(Color.white);
        button5.setFont(new Font("Dialog",Font.BOLD, 24));
        this.add(button5);

        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private void addListener(){
        // Click "back"
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginWindow();
                dispose();
            }
        });
        // Click "User Only"
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UserRegister("User Registration");
                dispose();
            }
        });
        // Click "Manager Only"
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ManagerOnlyRegistration("Manager-Only Registration");
                dispose();
            }
        });
        // Click "Customer Only"
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CustomerRegistration("Customer Registration");
                dispose();
            }
        });
        // Click "Manager-Customer"
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ManagerCustomerRegister("Manager-Customer Registration");
                dispose();
            }
        });
    }
}
