package Client;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ClientBettingPane
{
    ClientController controller;
    ClientGameView gameView;

    public ClientBettingPane(ClientController controller, ClientCoinFlipGameView gameView)
    {
        this.controller = controller;
        this.gameView = gameView;
        init();
    }
    public ClientBettingPane(ClientController controller, ClientDiceRollGameView gameView)
    {
        this.controller = controller;
        this.gameView = gameView;
        init();
    }

    private void init()
    {
        JTextField betValue = new JTextField();
        String[] coinOptions = {"Heads", "Tails"};
        JComboBox<String> coinOption = new JComboBox<>(coinOptions);
        Object[] contents =
        {
            "Bet Amount:", betValue,
            "Coin Option:", coinOption
        };
        int option = JOptionPane.showConfirmDialog(null, contents, "Betting", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.YES_OPTION)
        {
            try
            {
                int retval = Integer.parseInt(betValue.getText());
                if (retval < 1) error();
            }
            catch (NumberFormatException e)
            {
                error();
            }
            JOptionPane.showMessageDialog(null, "Bet Placed", "", JOptionPane.INFORMATION_MESSAGE);
            controller.confirmBetting(betValue.getText(), coinOption.getSelectedItem().toString());
            gameView.confirmBetting();
        }
        else 
        {
            JOptionPane.showMessageDialog(null, "Bet Cancelled", "Error", JOptionPane.ERROR_MESSAGE);
            gameView.cancelBetting();
        }
    }
    private void error()
    {
        JOptionPane.showMessageDialog(null, "Invalid Bet", "Error", JOptionPane.ERROR_MESSAGE);
        gameView.cancelBetting();
    }
}
