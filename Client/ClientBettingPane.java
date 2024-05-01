package Client;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ClientBettingPane
{
    ClientController controller;
    ClientGameView gameView;
    boolean error = false;

    public ClientBettingPane(ClientController controller, ClientCoinFlipGameView gameView)
    {
        this.controller = controller;
        this.gameView = gameView;
        initCoinFlip();
    }
    public ClientBettingPane(ClientController controller, ClientDiceRollGameView gameView)
    {
        this.controller = controller;
        this.gameView = gameView;
        initDiceRoll();
    }

    private void initCoinFlip()
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
            if (error == false)
            {
                String selectedOption = coinOption.getSelectedItem().toString();
                String retval = "";
                if (selectedOption == "Heads") retval = "1";
                else if (selectedOption == "Tails") retval = "0";
                controller.confirmBetting(betValue.getText(), retval);
                gameView.confirmBetting();
                JOptionPane.showMessageDialog(null, "Bet Placed", "", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else 
        {
            JOptionPane.showMessageDialog(null, "Bet Cancelled", "Error", JOptionPane.ERROR_MESSAGE);
            gameView.cancelBetting();
        }
    }
    
    private void initDiceRoll()
    {
        JTextField betValue = new JTextField();
        String[] diceOptions = {"One", "Two", "Three", "Four", "Five", "Six"};
        JComboBox<String> diceOption = new JComboBox<>(diceOptions);
        Object[] contents =
        {
            "Bet Amount:", betValue,
            "Dice Option:", diceOption
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
            if (error == false)
            {
                String selectedOption = diceOption.getSelectedItem().toString();
                String retval = "";
                switch (selectedOption)
                {
                    case "One":
                    retval = "1";
                    break;
                    case "Two":
                    retval = "2";
                    break;
                    case "Three":
                    retval = "3";
                    break;
                    case "Four":
                    retval = "4";
                    break;
                    case "Five":
                    retval = "5";
                    break;
                    case "Six":
                    retval = "6";
                    break;
                }
                controller.confirmBetting(betValue.getText(), retval);
                gameView.confirmBetting();
                JOptionPane.showMessageDialog(null, "Bet Placed", "", JOptionPane.INFORMATION_MESSAGE);
            }
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
        error = true;
    }
}
