import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class productStock extends JFrame {

    Connection con;
    PreparedStatement pst;
    private JPanel stockPanel;
    private JTable table;

    private JTextField searchField;
    private JButton searchButton;

    private DefaultTableModel tableModel;

    public productStock() {
        setTitle("Stock Product");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Arama bileşenleri
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Tablo modeli oluştur
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Barcode");
        tableModel.addColumn("Name");
        tableModel.addColumn("Price");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Date");

        // Tabloyu oluştur ve modeli ayarla
        table = new JTable(tableModel);

        // Verileri tabloya ekle
        insertDataIntoTable();


        // Tabloyu kaydırılabilir yap
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Arama butonuna eylem dinleyici ekle
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                searchProduct(searchText);

            }
        });

        setVisible(true);
    }

    private void insertDataIntoTable() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/20190305051", "root", "");
            String query = "SELECT * FROM product";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Object[] rowData = {
                        resultSet.getInt("id"),
                        resultSet.getString("barcorde"),
                        resultSet.getString("name"),
                        resultSet.getString("price"),
                        resultSet.getString("amount"),
                        resultSet.getInt("date")
                };
                tableModel.addRow(rowData);
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veritabanına erişim hatası: " + ex.getMessage());
        }
    }





    public void connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/20190305051", "root","");
            System.out.println("Successs");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    private void searchProduct(String searchText) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/20190305051", "root", "");
            String query = "SELECT * FROM product WHERE barcorde LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchText + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            // Mevcut tabloyu temizle
            tableModel.setRowCount(0);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String barcode = resultSet.getString("barcorde");
                String name = resultSet.getString("name");
                String price = resultSet.getString("price");
                String amount = resultSet.getString("amount");
                int date = resultSet.getInt("date");

                Object[] row = { id, barcode, name, price, amount, date };
                tableModel.addRow(row);
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database access error: " + ex.getMessage());
        }
    }

















    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new productStock());






    }






}


