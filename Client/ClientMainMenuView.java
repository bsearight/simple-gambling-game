package Client;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class ClientMainMenuView
{
    private ClientController controller;
    private JFrame frame;
    private JPanel switchPanel;
    private JPanel loggedIn;
    private JPanel loggedOut;
    private JLabel title;
    private JLabel user;
    private JLabel balance;
    private JButton playCoinFlipButton;
    private JButton playDiceRollButton;
    private JButton loginButton;
    private JButton logoutButton;
    private JButton quitButton;
    private JButton quitButton2;
    private JButton leaderboardButton;
    private JButton registerButton;
    private ImageIcon icon;
    private boolean isLoggedIn = false;
    @SuppressWarnings("unused")
    private boolean isGameViewOpen;
    private static final int COIN_FLIP = 0;
    private static final int DICE_ROLL = 1;


    public ClientMainMenuView(ClientController controller)
    {
        this.controller = controller;
        init();
    }
    private void init()
    {
        frame = new JFrame("Coin Flip Client");
        loggedIn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loggedOut = new JPanel(new FlowLayout(FlowLayout.CENTER));
        switchPanel = new JPanel(new CardLayout());
        user = new JLabel();
        balance = new JLabel();
        title = getIcon();
        initButtons();
        loggedIn.add(playCoinFlipButton);
        loggedIn.add(playDiceRollButton);
        loggedIn.add(logoutButton);
        loggedIn.add(leaderboardButton);
        loggedIn.add(quitButton2);
        loggedIn.add(user);
        loggedIn.add(balance);
        loggedOut.add(loginButton);
        loggedOut.add(registerButton);
        loggedOut.add(quitButton);
        switchPanel.add(loggedOut, "loggedOut");
        switchPanel.add(loggedIn, "loggedIn");
        loggedOut.add(title, BorderLayout.NORTH);
        frame.add(switchPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent)
            {
                controller.quit();
            }
        });
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
    private JLabel getIcon()
    {
        try
        {
            icon = new ImageIcon(getClass().getResource("/Resources/icon.png"));
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        if (icon != null) return new JLabel(icon);
        else return new JLabel("image not found");
    }
    private void openGameView(int option)
    {
        if (option == COIN_FLIP) new ClientCoinFlipGameView(controller);
        else if (option == DICE_ROLL) new ClientDiceRollGameView(controller);
    }
    private void openLoginPane()
    {
        new ClientLoginPane(controller);
    }
    public void switchPanelLogin()
    {
        CardLayout layout = (CardLayout) switchPanel.getLayout();
        if (isLoggedIn)
        {
            user.setText("User: " + controller.getUsername());
            balance.setText("Balance: " + controller.getBalance());
            layout.show(switchPanel, "loggedIn");
        }
        else layout.show(switchPanel, "loggedOut");
    }
    private void openLeaderboard()
    {
        new ClientLeaderboardPane(controller);
    }
    public void setIsLoggedIn(boolean isLoggedIn)
    {
        this.isLoggedIn = isLoggedIn;
        switchPanelLogin();
    }
    public void setGameViewOpen(boolean isOpen)
    {
        isGameViewOpen = isOpen;
    }
    public void showWindow()
    {
        frame.setVisible(true);
    }
    private void openRegisterPane()
    {
        new ClientRegisterPane(controller);
    }
    private void initButtons()
    {
        playCoinFlipButton = new JButton("Play Coin Flip");
        playCoinFlipButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                isGameViewOpen = true;
                openGameView(COIN_FLIP);
                frame.setVisible(false);
            }
        });
        playDiceRollButton = new JButton("Play Dice Roll");
        playDiceRollButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                isGameViewOpen = true;
                openGameView(DICE_ROLL);
                frame.setVisible(false);
            }
        });
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                openLoginPane();
            }
        });
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.logout();
                JOptionPane.showMessageDialog(null, "Logged Out", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.quit();
            }
        });
        quitButton2 = new JButton("Quit");
        quitButton2.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.quit();
            }
        });
        leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                openLeaderboard();
            }
        });
        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                openRegisterPane();
            }
        });
    }
}
