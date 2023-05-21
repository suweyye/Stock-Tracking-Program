import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login extends JFrame {
    private JTextField txtName;
    private JPasswordField txtPassword;
    private JButton loginButton;
    private JPanel panelLogin;

    public login() {
    loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {



            String name=txtName.getText();
            String password=txtPassword.getText();


            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/20190305051", "root", "");
                String query = "SELECT * FROM login WHERE name = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "Login successful!");

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password!");
                }

                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database access error: " + ex.getMessage());
            }






        }
    });

    }

    public static void main(String[] args) {

        login h=new login();
        h.setContentPane(h.panelLogin);
        h.setTitle("Login Page");
        h.setSize(400,400);
        h.setVisible(true);
        h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
