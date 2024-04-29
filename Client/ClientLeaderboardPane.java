package Client;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ClientLeaderboardPane
{
    ClientController controller;
    JTextArea leaderboard;
    public ClientLeaderboardPane(ClientController controller)
    {
        System.out.println("Entered: Leaderboard Constructor");
        this.controller = controller;
        init();
    }
    private void init()
    {
        System.out.println("Entered: Client Leaderboard initiator");
        leaderboard = new JTextArea(controller.getLeaderboard());
        JOptionPane.showMessageDialog(null, leaderboard, "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }
}
