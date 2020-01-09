package CS4400Final;

import org.jdesktop.swingx.JXDatePicker;
import org.w3c.dom.events.EventException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class theaterOverview extends JFrame {
    private JLabel labelTitle;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel movieName;
    private JLabel movieDuration;
    private JLabel movieReleaseDate;
    private JLabel moviePlayDate;


    private JButton buttonFilter;
    private JButton buttonBack;

    private JTextField movieDurationMin;
    private JTextField movieDurationMax;
    private JTextField movieNameText;

    protected List<String> companyList;

    private JTable jTable;
    DefaultTableModel model;
    private JScrollPane jscrollpane;

    protected String username;
    protected String usertype;

    private int mark = 0;

    final JXDatePicker dateReleaseMin = new JXDatePicker();
    final JXDatePicker dateReleaseMax = new JXDatePicker();
    final JXDatePicker datePlayMin = new JXDatePicker();
    final JXDatePicker datePlayMax = new JXDatePicker();

    private JCheckBox checkBox;

    private boolean checkMark=false;

    // Contain state of checkbox
    protected List<Boolean>checkList;

    public theaterOverview(){
        init();
        addComponent();
        addListener();
    }

    private void init(){
        this.setSize(800,520);
        this.setVisible(true);
        this.setTitle("Theater Overview");
        this.setLayout(null);
        this.setLocation(500, 200);
    }

    private void addComponent(){
        labelTitle = new JLabel("Theater Overview");
        labelTitle.setSize(400,200);
        labelTitle.setFont(new Font("Dialog",Font.BOLD, 25));
        labelTitle.setLocation(280,-60);
        this.add(labelTitle);

        movieName = new JLabel("Movie Name");
        movieName.setSize(100,20);
        movieName.setLocation(120,70);
        this.add(movieName);

        movieNameText = new JTextField();
        movieNameText.setSize(100,20);
        movieNameText.setLocation(230,70);
        this.add(movieNameText);

        movieDuration = new JLabel("Movie Duration");
        movieDuration.setSize(100,20);
        movieDuration.setLocation(420,70);
        this.add(movieDuration);

        label1 = new JLabel("------");
        label1.setSize(60,20);
        label1.setLocation(570,70);
        this.add(label1);

        movieReleaseDate = new JLabel("Movie Release Date");
        movieReleaseDate.setSize(150,20);
        movieReleaseDate.setLocation(120,110);
        this.add(movieReleaseDate);

        Date date1min = new Date();
        dateReleaseMin.setDate(date1min);
        dateReleaseMin.setSize(150,30);
        dateReleaseMin.setLocation(280,110);
        this.add(dateReleaseMin);

        label2 = new JLabel("-----------");
        label2.setSize(60,20);
        label2.setLocation(440,110);
        this.add(label2);

        Date date1max = new Date();
        dateReleaseMax.setDate(date1max);
        dateReleaseMax.setSize(150,30);
        dateReleaseMax.setLocation(500,110);
        this.add(dateReleaseMax);

        moviePlayDate = new JLabel("Movie Play Date");
        moviePlayDate.setSize(150,20);
        moviePlayDate.setLocation(120,170);
        this.add(moviePlayDate);

        Date date2Min = new Date();
        datePlayMin.setDate(date2Min);
        datePlayMin.setSize(150,30);
        datePlayMin.setLocation(280,170);
        this.add(datePlayMin);

        label3 = new JLabel("-----------");
        label3.setSize(60,20);
        label3.setLocation(440,170);
        this.add(label3);

        Date date2Max = new Date();
        datePlayMax.setDate(date2Max);
        datePlayMax.setSize(150,30);
        datePlayMax.setLocation(500,170);
        this.add(datePlayMax);

        checkBox = new JCheckBox("Only Include Not Played Movies");
        checkBox.setSize(200,20);
        checkBox.setLocation(280,220);
        this.add(checkBox);

        buttonFilter = new JButton("Filter");
        buttonFilter.setSize(80,20);
        buttonFilter.setLocation(340,250);
        buttonFilter.setBackground(Color.white);
        buttonFilter.setFont(new Font("Dialog",Font.BOLD, 12));
        this.add(buttonFilter);

        buttonBack = new JButton("Back");
        buttonBack.setSize(100,20);
        buttonBack.setLocation(330,450);
        buttonBack.setBackground(Color.white);
        buttonBack.setFont(new Font("Dialog",Font.BOLD, 18));
        this.add(buttonBack);

        movieDurationMin = new JTextField();
        movieDurationMin.setSize(30,20);
        movieDurationMin.setLocation(530,70);
        this.add(movieDurationMin);

        movieDurationMax = new JTextField();
        movieDurationMax.setSize(30,20);
        movieDurationMax.setLocation(610,70);
        this.add(movieDurationMax);

        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void addListener() {
        buttonFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inUserName = username;
                String inMovieName = movieNameText.getText();
                int minDuration;
                if(movieDurationMin.getText().isEmpty())
                {
                       minDuration = 0;
                }
                else
                {
                    minDuration = Integer.parseInt(movieDurationMin.getText());
                }

                int maxDuration;
                if(movieDurationMax.getText().isEmpty())
                {
                    maxDuration = Integer.MAX_VALUE;
                }
                else
                {
                    maxDuration = Integer.parseInt(movieDurationMax.getText());
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d1 = dateReleaseMin.getDate();
                String releaseMin = sdf.format(d1);
                Date d2 = dateReleaseMax.getDate();
                String releaseMax = sdf.format(d2);
                Date d3 = datePlayMin.getDate();
                String playMin = sdf.format(d3);
                Date d4 = datePlayMax.getDate();
                String playMax = sdf.format(d4);
               
                boolean inMark = checkMark;
                DBManager dbManager = new DBManager();
                try {
                    //Here we call the stored procedure
                    CallableStatement statement =
                            dbManager.connection.prepareCall("call manager_filter_th(?,?,?,?,?,?,?,?,?)");
                    //Here we put the data into the procedure
                    statement.setString(1, inUserName);
                    statement.setString(2, inMovieName);
                    statement.setInt(3, minDuration);
                    statement.setInt(4, maxDuration);
                    statement.setString(5, releaseMin);
                    statement.setString(6, releaseMax);
                    statement.setString(7, playMin);
                    statement.setString(8, playMax);
                    statement.setBoolean(9, inMark);
                    statement.execute();
                    java.sql.Statement statement1 = dbManager.connection.createStatement
                            (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet result = statement1.executeQuery("select * from ManFilterTh");
                    // No matching user
                    if (!result.isBeforeFirst()) {
                        JOptionPane.showMessageDialog(null, "No result!");
                        //If this is not the initial filter we should clear the table
                        if(mark == 1)
                        {
                            Object obj[][] = new Object[1][4];
                            obj[0][0] = "";
                            obj[0][1] = "";
                            obj[0][2] = "";
                            obj[0][3] = "";
                            model.setDataVector(obj, new Object[]{"Movie Name", "Duration", "Release Date", "Play Date"});
                            jTable.setModel(model);
                            jTable.setVisible(false);
                            jTable.repaint();
                            jscrollpane.repaint();
                        }
                        return;
                    } else {
                        result.last();
                        int rowCount = result.getRow();
                        result.first();
                        String[] dataTitle = {"Movie Name", "Duration", "Release Date", "Play Date"};
                        if (mark == 0) {
                            model = new DefaultTableModel(dataTitle, rowCount);
                            jTable = new JTable(model);
                            jTable.setRowHeight(30);
                            jscrollpane = new JScrollPane(jTable);
                            jscrollpane.setSize(700, 150);
                            jscrollpane.setLocation(40, 300);
                            add(jscrollpane);
                        }
                        mark = 1;
                        model = new DefaultTableModel(dataTitle, rowCount);
                        Object obj[][] = new Object[rowCount][4];
                        //Print first row
                        String movieName = result.getString(1);
                        int duration = result.getInt(2);
                        Date releaseDate = result.getDate(3);
                        Date playDate = result.getDate(4);
                        obj[0][0] = movieName;
                        obj[0][1] = duration;
                        obj[0][2] = releaseDate.toString();
                        if(playDate==null)
                        	obj[0][3]="";
                        else {
                        	obj[0][3]=playDate.toString();
						}
                        int i = 1;
                        while (result.next()) {
                            //Here we try to add button into table
                            movieName = result.getString(1);
                            duration = result.getInt(2);
                            releaseDate = result.getDate(3);
                            playDate = result.getDate(4);
                            obj[i][0] = movieName.toString();
                            obj[i][1] = duration;
                            if(playDate==null)
                            	obj[i][3]="";
                            else {
                            	obj[i][3] = playDate.toString();
    						}
                            obj[i][2] = releaseDate.toString();
                            i++;
                        }
                        model.setDataVector(obj, new Object[]{"Movie Name", "Duration", "Release Date", "Play Date"});
                        jTable.setModel(model);
                    }
                    jTable.repaint();
                    jscrollpane.repaint();
                    jTable.setVisible(true);
                    statement.close();
                    statement1.close();
                    dbManager.close();
                } catch (Exception event) {
                    JOptionPane.showMessageDialog(null, "No result!");
                    event.printStackTrace();
                    return;
                }
            }
            });
        checkBox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JCheckBox tempBox = (JCheckBox)e.getSource();
                if(tempBox.isSelected()) {
                    checkMark = true;
                }
                else {
                    checkMark = false;
                }
            }
        });
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(usertype.equals("manager"))
                {
                    managerOnlyFunc f = new managerOnlyFunc();
                    f.username = username;
                    dispose();
                }
                else if(usertype.equals("managerCustomer"))
                {
                    managerCustomerFunc f = new managerCustomerFunc();
                    f.username = username;
                    dispose();
                }
            }
        });
    }
}

