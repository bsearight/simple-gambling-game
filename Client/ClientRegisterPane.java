package Client;

import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class ClientRegisterPane
{
    ClientController controller;
    JTextField username;
    JTextField password;
    JTextField confirmPassword;
    public ClientRegisterPane(ClientController controller)
    {
        this.controller = controller;
        init();
    }

    private void init()
    {
        username = new JTextField();
        password = new JTextField();
        confirmPassword = new JTextField();
        Object[] contents =
        {
            "Username:", username,
            "Password:", password,
            "Confirm Password:", confirmPassword
        };
        int option = JOptionPane.showConfirmDialog(null, contents, "Register", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.YES_OPTION)
        {
            if (password.getText().equals(confirmPassword.getText()))
            {
                if (controller.checkDuplicateUser(username.getText()) == false)
                {
                    controller.registerUser(username.getText(), password.getText());
                    JOptionPane.showMessageDialog(null, "Registered Successfully!", "", JOptionPane.INFORMATION_MESSAGE);
                }
                else JOptionPane.showMessageDialog(null, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else JOptionPane.showMessageDialog(null, "Password does not match", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
