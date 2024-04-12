package Client;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ClientView
{
    private ClientController controller;
    private JFrame frame;
    private JPanel switchPanel;
    private JPanel loggedIn;
    private JPanel loggedOut;
    private JLabel title;
    private JButton playButton;
    private JButton loginButton;
    private JButton logoutButton;
    private JButton quitButton;
    private JButton quitButton2;
    private JButton leaderboardButton;
    private ImageIcon icon;
    private boolean isLoggedIn = false;
    private boolean gameViewOpen = false;


    public ClientView(ClientController controller)
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
        title = getIcon();
        playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                openGameView();
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
        loggedIn.add(playButton);
        loggedIn.add(logoutButton);
        loggedIn.add(quitButton2);
        loggedOut.add(loginButton);
        loggedOut.add(quitButton);
        switchPanel.add(loggedOut, "loggedOut");
        switchPanel.add(loggedIn, "loggedIn");
        frame.add(title, BorderLayout.NORTH);
        frame.add(switchPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
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
    private void openGameView()
    {
        new ClientGameView(controller);
    }
    private void openLoginPane()
    {
        new ClientLoginPane(controller);
    }
    public void switchPanelLogin()
    {
        CardLayout layout = (CardLayout) switchPanel.getLayout();
        if (isLoggedIn) layout.show(switchPanel, "loggedIn");
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
}
