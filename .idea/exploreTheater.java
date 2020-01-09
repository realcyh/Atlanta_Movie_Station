/*The user type should be proceed When backing*/
package CS4400Final;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class exploreTheater extends JFrame {
    private JLabel label1;
    private JLabel labelTheaterName;
    private JLabel labelCompanyName;
    private JLabel labelCityName;
    private JLabel labelStateName;
    private JLabel labelVisitDate;

    private JButton buttonFilter;
    private JButton buttonBack;
    private JButton buttonLogVisit;

    private JTextField city;

    private JComboBox<String> comboBoxTheater;
    private JComboBox<String> comboBoxCompanyName;
    private JComboBox<String> comboBoxState;

    protected String username;
    protected String userType;

    protected List<String> companyList;
    protected List<String> theaterList;
    protected List<String> stateList;

    private JTable table;
    DefaultTableModel model;
    private JScrollPane panel;

    protected int rowTheaterCount;
    protected int rowFilterResult;
    protected int initMark = 0;

    protected String inTheaterName = "ALL";
    protected String inCompanyName = "ALL";
    protected String inCity = "";
    protected String inState = "ALL";

    protected List<Boolean> checkList;

    final JXDatePicker datePicker = new JXDatePicker();

    public exploreTheater() {
        init();
        addComponent();
        addListener();
    }

    private void init() {
        this.setSize(750, 450);
        this.setVisible(true);
        this.setTitle("Manage Company");
        this.setLayout(null);
        this.setLocation(500, 200);
    }

    private void addComponent() {
        label1 = new JLabel("Explore Theater");
        label1.setSize(400, 200);
        label1.setFont(new Font("Dialog", Font.BOLD, 25));
        label1.setLocation(260, -60);
        this.add(label1);

        labelTheaterName = new JLabel("Theater Name");
        labelTheaterName.setSize(90, 20);
        labelTheaterName.setLocation(100, 70);
        this.add(labelTheaterName);

        labelCompanyName = new JLabel("Company Name");
        labelCompanyName.setSize(100, 20);
        labelCompanyName.setLocation(370, 70);
        this.add(labelCompanyName);

        labelCityName = new JLabel("City");
        labelCityName.setSize(40, 20);
        labelCityName.setLocation(100, 110);
        this.add(labelCityName);

        labelStateName = new JLabel("State");
        labelStateName.setSize(100, 20);
        labelStateName.setLocation(370, 110);
        this.add(labelStateName);

        buttonFilter = new JButton("Filter");
        buttonFilter.setSize(80, 20);
        buttonFilter.setLocation(320, 140);
        buttonFilter.setBackground(Color.white);
        buttonFilter.setFont(new Font("Dialog", Font.BOLD, 12));
        this.add(buttonFilter);

        buttonBack = new JButton("Back");
        buttonBack.setSize(100, 20);
        buttonBack.setLocation(120, 370);
        buttonBack.setBackground(Color.white);
        buttonBack.setFont(new Font("Dialog", Font.BOLD, 18));
        this.add(buttonBack);

        buttonLogVisit = new JButton("Log Visit");
        buttonLogVisit.setSize(150, 20);
        buttonLogVisit.setLocation(500, 370);
        buttonLogVisit.setBackground(Color.white);
        buttonLogVisit.setFont(new Font("Dialog", Font.BOLD, 18));
        this.add(buttonLogVisit);

        city = new JTextField();
        city.setSize(120, 20);
        city.setLocation(200, 110);
        this.add(city);

        //Here we set the company name comboBox
        companyList = new ArrayList<String>(Arrays.asList(new String[]
                {"4400 Theater Company", "AI Theater Company", "Awesome Theater Company", "EZ Theater Company"}));
        comboBoxCompanyName = new JComboBox<String>();
        comboBoxCompanyName.addItem("ALL");
        for (String tempName : companyList) {
            comboBoxCompanyName.addItem(tempName);
        }
        comboBoxCompanyName.setLocation(480, 70);
        comboBoxCompanyName.setSize(150, 20);
        this.add(comboBoxCompanyName);

        //Here we set the state name comboBox
        stateList = new ArrayList<String>(Arrays.asList(new String[]
                {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID",
                        "IL", "IN", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS"
                        , "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR"
                        , "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"}));
        comboBoxState = new JComboBox<String>();
        comboBoxState.addItem("ALL");
        for (String tempname : stateList) {
            comboBoxState.addItem(tempname);
        }
        comboBoxState.setLocation(480, 110);
        comboBoxState.setSize(150, 20);
        this.add(comboBoxState);

        //Here we set the theater name comboBox
        comboBoxTheater = new JComboBox<String>();
        comboBoxTheater.addItem("ALL");
        DBManager dbManager = new DBManager();
        try {
            java.sql.Statement statement = dbManager.connection.createStatement
                    (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery("select theaterName from team25.theater;");
            //All the company do not have theater
            if (!result.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "No result!");
                theaterList = new ArrayList<String>();
                theaterList.add("None");
            }
            //Here we get the total number of theater
            result.last();
            rowTheaterCount = result.getRow();
            result.first();
            String tempTheater = result.getString(1);
            comboBoxTheater.addItem(tempTheater);
            while (result.next()) {
                tempTheater = result.getString(1);
                comboBoxTheater.addItem(tempTheater);
            }
            comboBoxTheater.setSize(120, 20);
            comboBoxTheater.setLocation(200, 70);
            this.add(comboBoxTheater);

        } catch (Exception event) {
            JOptionPane.showMessageDialog(null, "System error!");
            event.printStackTrace();
            return;
        }

        //Here we add the date picker
        labelVisitDate = new JLabel("Visit Date");
        labelVisitDate.setSize(100, 20);
        labelVisitDate.setLocation(240, 370);
        this.add(labelVisitDate);

        //Here we set the date
        Date date = new Date();
        datePicker.setDate(date);
        datePicker.setSize(150, 30);
        datePicker.setLocation(300, 365);
        this.add(datePicker);

        this.setBackground(Color.blue);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void addListener() {
        //Filter Button
        buttonFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inTheaterName = (String) comboBoxTheater.getSelectedItem();
                inCompanyName = (String) comboBoxCompanyName.getSelectedItem();
                inCity = city.getText();
                inState = (String) comboBoxState.getSelectedItem();
                DBManager dbManager = new DBManager();
                try {
                    CallableStatement statement = dbManager.connection.prepareCall("call user_filter_th(?,?,?,?)");
                    statement.setString(1, inTheaterName);
                    statement.setString(2, inCompanyName);
                    statement.setString(3, inCity);
                    statement.setString(4, inState);
                    statement.execute();
                    //Here we want to get the row number of the table
                    java.sql.Statement statement1 = dbManager.connection.createStatement
                            (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet result = statement1.executeQuery("select * from team25.userfilterth");
                    // No matching user
                    if (!result.isBeforeFirst()) {
                        JOptionPane.showMessageDialog(null, "No result!");
                        city.setText(null);
                        
                        table.setVisible(false);
                        
                        return;
                    } else {
                        result.last();
                        rowFilterResult = result.getRow();
                        result.first();
                        checkList = new ArrayList<Boolean>();
                        for (int i = 0; i < rowFilterResult; i++) {
                            checkList.add(false);
                        }
                        String[] tableTile = {"Theater", "Address", "Company"};
                        if (initMark == 0) {
                            model = new DefaultTableModel(tableTile, rowFilterResult);
                            table = new JTable(model);
                            table.setRowHeight(30);
                            panel = new JScrollPane(table);
                            panel.setSize(700, 150);
                            panel.setLocation(20, 200);
                            add(panel);
                        }
                        initMark = 1;
                        model = new DefaultTableModel(rowFilterResult, rowFilterResult);
                        Object obj[][] = new Object[rowFilterResult][4];
                        for (int i = 0; i < rowFilterResult; i++) {
                            obj[i][0] = new JCheckBox(Integer.toString(i + 1));
                        }
                        String tempTheaterName = result.getString(1);
                        String tempAddress = result.getString(2)+"," + result.getString(3)
                                +"," +result.getString(4) +" "+ result.getString(5);
                        String tempCompany = result.getString(6);
                        obj[0][1] = tempTheaterName;
                        obj[0][2] = tempAddress;
                        obj[0][3] = tempCompany;
                        int i = 1;
                        while (result.next()) {
                            tempTheaterName = result.getString(1);
                            tempAddress = result.getString(2)+", " + result.getString(3)
                            +", " +result.getString(4) +" "+ result.getString(5);
                            tempCompany = result.getString(6);
                            obj[i][1] = tempTheaterName;
                            obj[i][2] = tempAddress;
                            obj[i][3] = tempCompany;
                            i++;
                        }
                        model.setDataVector(obj, new Object[]{"", "Theater", "Address", "Company"});
                        table.setModel(model);
                    }
                    table.repaint();
                    table.getColumn("").setCellEditor(new CheckButtonEditor(new JCheckBox()));
                    table.getColumn("").setCellRenderer(new CheckBoxRenderer());
                    table.setVisible(true);
                    panel.repaint();
                } catch (Exception event) {
                    JOptionPane.showMessageDialog(null, "System error!");
                    event.printStackTrace();
                    return;
                }
            }
        });
        
     // Click "Back"
        buttonBack.addActionListener(new ActionListener() {
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
        
        //Log Visit Button
        buttonLogVisit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int chooseRow = -1;
                for(int i = 0;i<checkList.size();i++)
                {
                    if(!checkList.get(i))
                        continue;
                    else
                    {
                        chooseRow =i;
                        break;
                    }
                }
                if(chooseRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please choose a theater");
                    return;
                }
                String inThName = (String) table.getValueAt(chooseRow,1);
                String inCoName = (String) table.getValueAt(chooseRow,3);
                Date inDateUtil = datePicker.getDate();
                java.sql.Date inDate = new java.sql.Date(inDateUtil.getTime());
                String inUsername = username;
                DBManager dbManager = new DBManager();
                try{
                    CallableStatement statement = dbManager.connection.prepareCall("call user_visit_th(?,?,?,?)");
                    statement.setString(1,inThName);
                    statement.setString(2,inCoName);
                    statement.setDate(3, (java.sql.Date) inDate);
                    statement.setString(4,inUsername);
                    statement.execute();
                    JOptionPane.showMessageDialog(null, "Log visit succeed");
                    statement.close();
                }catch (Exception event){
                    JOptionPane.showMessageDialog(null, "System error!");
                    event.printStackTrace();
                    return;
                }
            }
        });
        //combobox theater
        comboBoxCompanyName.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int mark = rowTheaterCount;
                while (mark > 0) {
                    Object obj = comboBoxTheater.getItemAt(mark);
                    comboBoxTheater.removeItem(obj);
                    mark--;
                }
                DBManager dbManager = new DBManager();
                try {
                    java.sql.Statement statement = dbManager.connection.createStatement
                            (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    String companyName = (String) comboBoxCompanyName.getSelectedItem();
                    String sql = "select theaterName from team25.theater where companyName = \"" + companyName + "\";";
                    if (companyName.equals("ALL")) {
                        sql = "select theaterName from team25.theater;";
                    }
                    ResultSet result = statement.executeQuery(sql);
                    //All the company do not have theater
                    if (!result.isBeforeFirst()) {
                        JOptionPane.showMessageDialog(null, "No result!");
                        theaterList = new ArrayList<String>();
                        theaterList.add("None");
                    }
                    //Here we get the total number of theater
                    result.last();
                    rowTheaterCount = result.getRow();
                    result.first();
                    String tempTheater = result.getString(1);
                    comboBoxTheater.addItem(tempTheater);
                    while (result.next()) {
                        tempTheater = result.getString(1);
                        comboBoxTheater.addItem(tempTheater);
                    }
                } catch (Exception event) {
                    JOptionPane.showMessageDialog(null, "System error!");
                    event.printStackTrace();
                    return;
                }
            }
        });
    }
    class CheckButtonEditor extends DefaultCellEditor
            implements ItemListener {
        private JCheckBox button;

        public CheckButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (value==null) return null;
            button = (JCheckBox)value;
            button.addItemListener(this);
            return (Component)value;
        }

        public Object getCellEditorValue() {
            button.removeItemListener(this);
            return button;
        }

        public void itemStateChanged(ItemEvent e) {
            super.fireEditingStopped();
            String sID = ((JCheckBox)e.getSource()).getLabel();
            int id = Integer.parseInt(sID) - 1;
            if(checkList.get(id))
                checkList.set(id,false);
            else {
                if(checkList.contains(true))
                {
                    JOptionPane.showMessageDialog(null, "Only one company can be selected!");
                    return;
                }
                checkList.set(id,true);
            }
        }
    }
}


