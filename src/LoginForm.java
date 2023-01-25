import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm  extends JDialog{
    private JTextField tfEmail;
    private JPasswordField passwordf;
    private JButton bok;
    private JButton bcancel;
    private JPanel loginpanel;

    public LoginForm(JFrame parint) {
        super(parint);
        setTitle("Form");
        setContentPane(loginpanel);
        setMinimumSize(new Dimension(450,447));
        setModal(true);
        setLocationRelativeTo(parint);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        bok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name=tfEmail.getText();
                String password=String.valueOf(passwordf.getPassword());
                user=getAuthenticatedUser(name,password);
                if(user!=null){
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        bcancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
    public User user;
    private User getAuthenticatedUser(String email, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }

            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }


        return user;
    }
    public static void main(String[] arge){
        LoginForm start=new LoginForm(null);

    }

}
