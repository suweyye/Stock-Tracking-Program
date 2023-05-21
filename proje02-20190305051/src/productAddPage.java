import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;



public class productAddPage extends JFrame {
    private JTextField txtbarcode;
    private JButton productAddButton;
    private JPanel productAdd;
    private JTextField txtname;
    private JTextField txtPrice;
    private JTextField txtAmount;
    private JTextField txtDate;

    public productAddPage() {
    productAddButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String barcorde=txtbarcode.getText();
            String name=txtname.getText();
            String price=txtPrice.getText();
            String amount=txtAmount.getText();
            String date=txtDate.getText();



            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/20190305051", "root", "");
                String query = "INSERT INTO product (barcorde, name, price, amount, date) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, barcorde);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, price);
                preparedStatement.setString(4, amount);
                preparedStatement.setString(5, date);
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(productAdd, "Data saved successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(productAdd, "Error saving to database: " + ex.getMessage());
            }

        }
    });
}


    public static void main(String[] args) {
        productAddPage h=new productAddPage();
        h.setContentPane(h.productAdd);
        h.setTitle("Store Product Add");
        h.setSize(500,600);
        h.setVisible(true);
        h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);






    }

    private void addProduct(){
    String barcorde=txtbarcode.getText();
    String name=txtname.getText();
    String price=txtPrice.getText();
    String amount=txtAmount.getText();
    String date=txtDate.getText();
     products= addProductDatabase(barcorde,name,price,amount,date);


    }
    public products products;
    private products addProductDatabase(String barcorde,String name,String price,String amount,String date){
        products products=null;
        final String DB_URL="jdbc:mysql://localhost:3306/20190305051";
        final String USERNAME="root";
        final String PASSWORD="";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt=conn.createStatement();
            String sql="INSERT INTO product (name,barcode,price,amount,date,id)"+"(?,?,?,?,?,?)";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,barcorde);
            preparedStatement.setString(3,price);
            preparedStatement.setString(4,amount);
            preparedStatement.setString(5,date);

            int addrows=preparedStatement.executeUpdate();
            if (addrows>0){
                products = new products();
                products.barcorde=barcorde;
                products.name=name;
                products.price=price;
                products.amount=amount;
                products.date=date;


            }
            stmt.close();
            conn.close();

        }
        catch (Exception e){
            e.printStackTrace();

        }
        return products;

    }
}
