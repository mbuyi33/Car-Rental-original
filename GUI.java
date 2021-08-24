import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GUI extends JFrame implements ActionListener, DocumentListener
{
    private String foodCompanyName = "Car Rental Agency";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    private Server server;

    private Color colorGrey = new Color(172, 172, 172);

    private JMenuItem miAddCustomer = new JMenuItem("Add Customer");
    private JMenuItem miAddVehicle = new JMenuItem("Add Vehicle");
    private JMenuItem miRentVehicle = new JMenuItem("Rent Vehicle");
    private JMenuItem miRentals = new JMenuItem("Rentals");
    private JMenuItem miExit = new JMenuItem("Exit");

    private JTable tableVehicles = new JTable(new TableModel());
    private JTable tableCustomers = new JTable(new TableModel());
    private JTable tableRentals = new JTable(new TableModel());

    private JCheckBox chkBoxShowAvailable = new JCheckBox("Show Available");
    private JCheckBox chkBoxShowCanRent = new JCheckBox("Show Can Rent");

    private JButton btnRent = new JButton("Rent");

    private JComboBox<String> cmbFilterCategories = new JComboBox<>(new String[]{"All","Sedan","SUV"});
    private JTextField txtFldSearchVehicle = new JTextField();
    private JTextField txtFldSearchCustomer = new JTextField();

    private JTextField txtFldSearchRental = new JTextField();
    private JComboBox<String> cmbRentalFilter = new JComboBox<>(new String[]{"All"});
    private JCheckBox chkBoxOutstanding = new JCheckBox("Filter Outstanding Rentals");
    private JButton btnReturn = new JButton("Return");

    private JPanel panelSwitch = new JPanel(new CardLayout());

    private JPanel panelAddVehicle = new JPanel(new GridBagLayout());

    private JComboBox<String> cmbCategories = new JComboBox<>(new String[]{"Sedan","SUV"});
    private JTextField txtFldMake = new JTextField();

    private JPanel panelAddCustomer = new JPanel(new GridBagLayout());

    private JTextField txtFldName = new JTextField();
    private JTextField txtFldSurname = new JTextField();
    private JTextField txtIdNum = new JTextField();
    private JTextField txtPhoneNum = new JTextField();

    public GUI()
    {
        setTitle(foodCompanyName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800,700));
        setLayout(new BorderLayout());

        createGUI();
        setListeners();

        setVisible(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        server = new Server();
        refreshRentalFilterCombobox();
        refreshCustomerTable();
        refreshVehicleTable();
        refreshRentalTable();
    }

    private void createGUI()
    {
        //MenuBar
        JMenu menuGeneral = new JMenu("General");
        menuGeneral.add(miRentVehicle);
        menuGeneral.add(miRentals);
        menuGeneral.addSeparator();
        menuGeneral.add(miExit);

        JMenu menuControl = new JMenu("Control");
        menuControl.add(miAddCustomer);
        menuControl.add(miAddVehicle);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuGeneral);
        menuBar.add(menuControl);

        setJMenuBar(menuBar);

        //TOP
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setPreferredSize(new Dimension(0,100));
        panelTop.setBorder(BorderFactory.createMatteBorder(0,0,1,0, colorGrey));

        JLabel labelFoodCompanyName = new JLabel(foodCompanyName);
        labelFoodCompanyName.setHorizontalAlignment(SwingConstants.CENTER);
        labelFoodCompanyName.setFont(new Font("Dialog",Font.BOLD,35));
        labelFoodCompanyName.setForeground(colorGrey);

        panelTop.add(labelFoodCompanyName, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);

        //BOTTOM
        JPanel panelBottom = new JPanel();
        panelBottom.setPreferredSize(new Dimension(0,50));
        panelBottom.setBorder(BorderFactory.createMatteBorder(1,0,0,0, colorGrey));

        JLabel labelTime = new JLabel();
        labelTime.setFont(new Font("Dialog", Font.ITALIC, 15));
        labelTime.setForeground(colorGrey);
        panelBottom.add(labelTime);

        new Timer(100, (e) -> labelTime.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a").format(Calendar.getInstance().getTime()))).start();

        add(panelBottom, BorderLayout.SOUTH);

        //Rent Vehicle
        JPanel panelSalesTransaction = new JPanel(new GridBagLayout());

        ((TableModel) tableVehicles.getModel()).addColumn("No.");
        ((TableModel) tableVehicles.getModel()).addColumn("Make");
        ((TableModel) tableVehicles.getModel()).addColumn("Category");
        ((TableModel) tableVehicles.getModel()).addColumn("Price");
        ((TableModel) tableVehicles.getModel()).addColumn("Available");
        tableVehicles.setRowSorter(new TableRowSorter<>((TableModel) tableVehicles.getModel()));
        tableVehicles.getTableHeader().setReorderingAllowed(false);

        ((TableModel) tableCustomers.getModel()).addColumn("No.");
        ((TableModel) tableCustomers.getModel()).addColumn("Name");
        ((TableModel) tableCustomers.getModel()).addColumn("Surname");
        ((TableModel) tableCustomers.getModel()).addColumn("ID Number");
        ((TableModel) tableCustomers.getModel()).addColumn("Phone Number");
        ((TableModel) tableCustomers.getModel()).addColumn("Can Rent");
        tableCustomers.setRowSorter(new TableRowSorter<>((TableModel) tableCustomers.getModel()));
        tableCustomers.getTableHeader().setReorderingAllowed(false);

        GridBagConstraints gbc = new GridBagConstraints();
        resetConstraints(gbc);

        JLabel labelMenu = new JLabel("Vehicles");
        labelMenu.setFont(new Font("Dialog", Font.BOLD, 20));
        labelMenu.setForeground(colorGrey);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 10;

        panelSalesTransaction.add(labelMenu, gbc);

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;

        panelSalesTransaction.add(new JLabel("Search: "), gbc);

        resetConstraints(gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.ipadx = 100;
        gbc.anchor = GridBagConstraints.WEST;

        panelSalesTransaction.add(txtFldSearchVehicle, gbc);

        resetConstraints(gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;

        panelSalesTransaction.add(new JLabel("Filter Category:"), gbc);

        resetConstraints(gbc);
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;

        panelSalesTransaction.add(cmbFilterCategories, gbc);

        resetConstraints(gbc);
        gbc.gridx = 5;
        gbc.gridy = 2;

        panelSalesTransaction.add(chkBoxShowAvailable, gbc);

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 10;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        panelSalesTransaction.add(new JScrollPane(tableVehicles), gbc);

        JLabel labelCart = new JLabel("Customers");
        labelCart.setFont(new Font("Dialog", Font.BOLD, 20));
        labelCart.setForeground(colorGrey);

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 10;

        panelSalesTransaction.add(labelCart, gbc);

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;

        panelSalesTransaction.add(new Label("Search: "), gbc);

        resetConstraints(gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.ipadx = 100;

        panelSalesTransaction.add(txtFldSearchCustomer, gbc);

        resetConstraints(gbc);
        gbc.gridx = 3;
        gbc.gridy = 5;

        panelSalesTransaction.add(chkBoxShowCanRent, gbc);

        resetConstraints(gbc);
        gbc.gridx = 4;
        gbc.gridy = 5;

        btnRent.setEnabled(false);

        panelSalesTransaction.add(btnRent, gbc);

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 10;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        panelSalesTransaction.add(new JScrollPane(tableCustomers), gbc);

        panelSwitch.add(panelSalesTransaction, "Sales Transaction");

        //Rentals
        JPanel panelSalesReport = new JPanel(new GridBagLayout());

        ((TableModel) tableRentals.getModel()).addColumn("No.");
        ((TableModel) tableRentals.getModel()).addColumn("Rent Date");
        ((TableModel) tableRentals.getModel()).addColumn("Returned Date");
        ((TableModel) tableRentals.getModel()).addColumn("Price Per Day");
        ((TableModel) tableRentals.getModel()).addColumn("Total Price");
        ((TableModel) tableRentals.getModel()).addColumn("Customer");
        ((TableModel) tableRentals.getModel()).addColumn("Vehicle");
        tableRentals.setRowSorter(new TableRowSorter<>((TableModel) tableRentals.getModel()));
        tableRentals.getTableHeader().setReorderingAllowed(false);

        JLabel labelSalesReport = new JLabel("Rentals");
        labelSalesReport.setFont(new Font("Dialog", Font.BOLD, 20));
        labelSalesReport.setForeground(colorGrey);

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 10;

        panelSalesReport.add(labelSalesReport, gbc);

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;

        panelSalesReport.add(new JLabel("Search Rental: "), gbc);

        resetConstraints(gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.ipadx = 100;
        gbc.anchor = GridBagConstraints.WEST;

        panelSalesReport.add(txtFldSearchRental, gbc);

        resetConstraints(gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;

        panelSalesReport.add(new JLabel("Filter Rental: "), gbc);

        resetConstraints(gbc);
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.ipadx = 20;
        gbc.anchor = GridBagConstraints.WEST;

        panelSalesReport.add(cmbRentalFilter, gbc);

        resetConstraints(gbc);
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;

        panelSalesReport.add(chkBoxOutstanding, gbc);

        resetConstraints(gbc);
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;

        btnReturn.setEnabled(false);

        panelSalesReport.add(btnReturn, gbc);

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 10;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        panelSalesReport.add(new JScrollPane(tableRentals), gbc);

        panelSwitch.add(panelSalesReport,"Sales Report");

        add(panelSwitch, BorderLayout.CENTER);

        //ADD Vehicle

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;

        panelAddVehicle.add(new JLabel("Category: "), gbc);

        resetConstraints(gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.ipadx = 18;

        panelAddVehicle.add(cmbCategories,gbc);

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;

        panelAddVehicle.add(new JLabel("Make: "), gbc);

        resetConstraints(gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.ipadx = 80;

        panelAddVehicle.add(txtFldMake,gbc);

        //ADD Customer

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;

        panelAddCustomer.add(new JLabel("Name: "), gbc);

        resetConstraints(gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.ipadx = 120;

        panelAddCustomer.add(txtFldName,gbc);

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;

        panelAddCustomer.add(new JLabel("Surname: "), gbc);

        resetConstraints(gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.ipadx = 120;

        panelAddCustomer.add(txtFldSurname,gbc);

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;

        panelAddCustomer.add(new JLabel("ID Number: "), gbc);

        resetConstraints(gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.ipadx = 120;

        panelAddCustomer.add(txtIdNum,gbc);

        resetConstraints(gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;

        panelAddCustomer.add(new JLabel("Phone Number: "), gbc);

        resetConstraints(gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.ipadx = 120;

        panelAddCustomer.add(txtPhoneNum,gbc);

    }

    private void resetConstraints(GridBagConstraints constraints)
    {
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        constraints.insets = new Insets(5,5,5,5);
    }

    private void setListeners()
    {
        miRentVehicle.addActionListener(this);
        miRentals.addActionListener(this);
        miAddCustomer.addActionListener(this);
        miAddVehicle.addActionListener(this);
        btnRent.addActionListener(this);
        btnReturn.addActionListener(this);

        txtFldSearchCustomer.getDocument().addDocumentListener(this);
        chkBoxShowCanRent.addActionListener(this);

        txtFldSearchVehicle.getDocument().addDocumentListener(this);
        chkBoxShowAvailable.addActionListener(this);
        cmbFilterCategories.addActionListener(this);

        txtFldSearchRental.getDocument().addDocumentListener(this);
        chkBoxOutstanding.addActionListener(this);
        cmbRentalFilter.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == miExit)
        {
            System.exit(0);
        }
        else if(e.getSource() == miRentVehicle)
        {
            ((CardLayout)panelSwitch.getLayout()).show(panelSwitch,"Sales Transaction");
        }
        else if(e.getSource() == miRentals)
        {
            ((CardLayout)panelSwitch.getLayout()).show(panelSwitch,"Sales Report");
        }
        else if(e.getSource() == miAddCustomer)
        {
            int option = JOptionPane.showConfirmDialog(this, panelAddCustomer, "New Customer", JOptionPane.DEFAULT_OPTION);

            if(option == JOptionPane.OK_OPTION)
            {
                String name = txtFldName.getText();
                String surname = txtFldSurname.getText();
                String idNum = txtIdNum.getText();
                String phoneNum = txtPhoneNum.getText();

                if(name.isEmpty() || surname.isEmpty() || idNum.isEmpty() || phoneNum.isEmpty())
                {
                    JOptionPane.showMessageDialog(this,"Please check if all inputs has been entered.","Input Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(!idNum.matches("[0-9]{13}"))
                {
                    JOptionPane.showMessageDialog(this,"Please enter a valid ID number.","Input Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(!phoneNum.matches("[0-9]{10}"))
                {
                    JOptionPane.showMessageDialog(this,"Please enter a valid phone number.","Input Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(server.idNumberExist(idNum))
                {
                    JOptionPane.showMessageDialog(this,"This ID number is already associated with another customer.","Input Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(server.phoneNumberExist(phoneNum))
                {
                    JOptionPane.showMessageDialog(this,"This phone number is already associated with another customer.","Input Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    Customer customer = new Customer(name, surname, idNum, phoneNum, true);

                    boolean success = server.addCustomer(customer);

                    if(success)
                    {
                        JOptionPane.showMessageDialog(this,"Customer has been successfully added.","Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this,"Customer hasn't been added due to a technical failure.","Failure", JOptionPane.ERROR_MESSAGE);
                    }

                    txtFldName.setText("");
                    txtFldSurname.setText("");
                    txtIdNum.setText("");
                    txtPhoneNum.setText("");

                    refreshCustomerTable();
                }
            }
            else
            {
                txtFldName.setText("");
                txtFldSurname.setText("");
                txtIdNum.setText("");
                txtPhoneNum.setText("");
            }
        }
        else if(e.getSource() == miAddVehicle)
        {
            int option = JOptionPane.showConfirmDialog(this, panelAddVehicle, "New Vehicle", JOptionPane.DEFAULT_OPTION);

            if(option == JOptionPane.OK_OPTION)
            {
                String make = txtFldMake.getText();
                int cat = cmbCategories.getSelectedIndex();

                if(make.isEmpty())
                {
                    JOptionPane.showMessageDialog(this,"Please check if a make was entered.","Input Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(server.makeExist(make))
                {
                    JOptionPane.showMessageDialog(this,"This vehicle make already exists.","Input Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    Vehicle vehicle = new Vehicle(make, cat + 1);

                    boolean success = server.addVehicle(vehicle);

                    if(success)
                    {
                        JOptionPane.showMessageDialog(this,"Vehicle has been successfully added.","Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this,"Vehicle hasn't been added due to a technical failure.","Failure", JOptionPane.ERROR_MESSAGE);
                    }

                    txtFldMake.setText("");

                    refreshVehicleTable();
                }
            }
            else
            {
                txtFldMake.setText("");
            }
        }
        else if(e.getSource() == btnRent)
        {
            if(tableVehicles.getSelectedRow() < 0)
            {
                JOptionPane.showMessageDialog(this,"Please select a vehicle to rent.","Select Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(tableCustomers.getSelectedRow() < 0)
            {
                JOptionPane.showMessageDialog(this,"Please select a customer to rent the vehicle.","Select Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int vehNumber = (Integer) tableVehicles.getValueAt(tableVehicles.getSelectedRow(), 0);
                double price = (Double) tableVehicles.getValueAt(tableVehicles.getSelectedRow(), 3);
                int custNumber = (Integer) tableCustomers.getValueAt(tableCustomers.getSelectedRow(), 0);

                Rental rental = new Rental(new Date(), price, custNumber, vehNumber);

                boolean success = server.rent(rental);

                if(success)
                {
                    JOptionPane.showMessageDialog(this,"The vehicle has been successfully rented.","Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(this,"The vehicle hasn't been rented due to a technical failure.","Failure", JOptionPane.ERROR_MESSAGE);
                }

                refreshVehicleTable();
                refreshCustomerTable();
                refreshRentalTable();
                refreshRentalFilterCombobox();
            }
        }
        else if(e.getSource() == btnReturn)
        {
            if(tableRentals.getSelectedRow() < 0)
            {
                JOptionPane.showMessageDialog(this,"Please select a rental to return vehicle.","Select Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int rentalNumber = (Integer) tableRentals.getValueAt(tableRentals.getSelectedRow(),0);
                Date dateRented = null;
                double price = (Double) tableRentals.getValueAt(tableRentals.getSelectedRow(),3);
                int custNumber = (Integer) tableRentals.getValueAt(tableRentals.getSelectedRow(),5);
                int vehNumber = (Integer) tableRentals.getValueAt(tableRentals.getSelectedRow(),6);

                try
                {
                    dateRented = new SimpleDateFormat("yyyy/MM/dd").parse((String) tableRentals.getValueAt(tableRentals.getSelectedRow(),1));
                }
                catch (ParseException exc)
                {
                    System.out.println(exc);
                }

                Rental rental = new Rental(rentalNumber, dateRented, new Date(), price, custNumber, vehNumber);

                boolean success = server.returnRental(rental);

                if(success)
                {
                    JOptionPane.showMessageDialog(this,"The vehicle has been successfully returned.","Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(this,"The vehicle hasn't been returned due to a technical failure.","Failure", JOptionPane.ERROR_MESSAGE);
                }

                refreshVehicleTable();
                refreshCustomerTable();
                refreshRentalTable();
            }
        }
        else if(e.getSource() == chkBoxShowCanRent)
        {
            filterCustomers();

            if(chkBoxShowCanRent.isSelected() && chkBoxShowAvailable.isSelected())
            {
                btnRent.setEnabled(true);
            }
            else
            {
                btnRent.setEnabled(false);
            }
        }
        else if(e.getSource() == chkBoxShowAvailable || e.getSource() == cmbFilterCategories)
        {
            if(chkBoxShowCanRent.isSelected() && chkBoxShowAvailable.isSelected())
            {
                btnRent.setEnabled(true);
            }
            else
            {
                btnRent.setEnabled(false);
            }

            filterVehicles();
        }
        else if(e.getSource() == chkBoxOutstanding || e.getSource() == cmbRentalFilter)
        {
            if(chkBoxOutstanding.isSelected())
            {
                btnReturn.setEnabled(true);
            }
            else
            {
                btnReturn.setEnabled(false);
            }

            filterRentals();
        }
    }

    @Override
    public void update(DocumentEvent e)
    {
        if(e.getDocument() == txtFldSearchVehicle.getDocument())
        {
            filterVehicles();
        }
        else if(e.getDocument() == txtFldSearchCustomer.getDocument())
        {
            filterCustomers();
        }
        else if(e.getDocument() == txtFldSearchRental.getDocument())
        {
            filterRentals();
        }
    }

    public void refreshCustomerTable()
    {
        ArrayList<Customer> customers = server.getCustomers();

        ((TableModel)tableCustomers.getModel()).setRowCount(0);


        for (Customer customer : customers)
        {
            String canRent = "no";

            if(customer.canRent())
            {
                canRent = "yes";
            }

            ((TableModel)tableCustomers.getModel()).addRow(new Object[]{customer.getCustNum(), customer.getName(), customer.getSurname(), customer.getIdNum(), customer.getPhoneNum(), canRent});
        }

        filterCustomers();
    }

    public void refreshVehicleTable()
    {
        ArrayList<Vehicle> vehicles = server.getVehicles();

        ((TableModel)tableVehicles.getModel()).setRowCount(0);

        for (Vehicle vehicle : vehicles)
        {
            String avail = "no";

            if(vehicle.isAvailableForRent())
            {
                avail = "yes";
            }

            ((TableModel)tableVehicles.getModel()).addRow(new Object[]{vehicle.getVehNum(), vehicle.getMake(), vehicle.getCategory(), vehicle.getRentalPrice(), avail});
        }

        filterVehicles();
    }

    public void refreshRentalTable()
    {
        ArrayList<Rental> rentals = server.getRentals();

        ((TableModel)tableRentals.getModel()).setRowCount(0);

        for (Rental rental : rentals)
        {
            String dateRented = dateFormat.format(rental.getDateRented());

            String dateReturned = "NA";

            if(rental.getDateReturned() != null)
            {
                dateReturned = dateFormat.format(rental.getDateReturned());
            }

            ((TableModel)tableRentals.getModel()).addRow(new Object[]{rental.getRentalNumber(), dateRented, dateReturned, rental.getPricePerDay(), rental.getTotalRental(), rental.getCustNumber(), rental.getVehNumber()});
        }

        filterRentals();
    }

    public void refreshRentalFilterCombobox()
    {
        ArrayList<String> rentalDates = server.getRentalDates();

        cmbRentalFilter.removeAllItems();
        cmbRentalFilter.addItem("All");

        for (String rentalDate : rentalDates)
        {
            cmbRentalFilter.addItem(rentalDate);
        }
    }

    private void filterCustomers()
    {
        String search = txtFldSearchCustomer.getText();

        String canRent = "";

        if(chkBoxShowCanRent.isSelected())
        {
            canRent = "yes";
        }

        ArrayList<RowFilter<Object, Object>> filters = new ArrayList<>();

        filters.add(RowFilter.regexFilter("(?i)^" + search,1,2,3,4));
        filters.add(RowFilter.regexFilter(canRent,5));

        ((TableRowSorter<TableModel>)tableCustomers.getRowSorter()).setRowFilter(RowFilter.andFilter(filters));
    }

    private void filterVehicles()
    {
        String search = txtFldSearchVehicle.getText();
        String cat =  "";
        String avail = "";

        if(cmbFilterCategories.getSelectedIndex() > 0)
        {
            cat = (String)cmbFilterCategories.getSelectedItem();
        }

        if(chkBoxShowAvailable.isSelected())
        {
            avail = "yes";
        }

        ArrayList<RowFilter<Object, Object>> filters = new ArrayList<>();

        filters.add(RowFilter.regexFilter("(?i)^" + search,1));
        filters.add(RowFilter.regexFilter(cat,2));
        filters.add(RowFilter.regexFilter(avail,4));

        ((TableRowSorter<TableModel>)tableVehicles.getRowSorter()).setRowFilter(RowFilter.andFilter(filters));
    }

    private void filterRentals()
    {
        String search = txtFldSearchRental.getText();
        String date = "";
        String out = "";

        if(chkBoxOutstanding.isSelected())
        {
            out = "NA";
        }

        if(cmbRentalFilter.getSelectedIndex() >= 0)
        {
            date = (String)cmbRentalFilter.getSelectedItem();

            if(date.equalsIgnoreCase("All"))
            {
                date = "";
            }
        }

        ArrayList<RowFilter<Object, Object>> filters = new ArrayList<>();

        filters.add(RowFilter.regexFilter("(?i)^" + search,0));
        filters.add(RowFilter.regexFilter(date,1));
        filters.add(RowFilter.regexFilter(out,2));

        ((TableRowSorter<TableModel>)tableRentals.getRowSorter()).setRowFilter(RowFilter.andFilter(filters));
    }

    public static void main(String[] args)
    {
        new GUI();
    }
}
