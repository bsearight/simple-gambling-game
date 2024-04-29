package Client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClientDiceRollGameView extends ClientGameView
{
    private ClientController controller;
    private JFrame frame;
    private JButton quitButton;
    private JButton betButton;
    private JButton flipButton;
    private JButton okayButton;
    private JPanel switchPanel;
    private JPanel defaultState;
    private JPanel postBetState;
    private JPanel cleanupState;
    private JLabel diceRoll;
    private JLabel dice1;
    private JLabel dice2;
    private JLabel dice3;
    private JLabel dice4;
    private JLabel dice5;
    private JLabel dice6;
    private ImageIcon icon;

    public ClientDiceRollGameView(ClientController controller)
    {
        this.controller = controller;
        init();
    }
    private void init()
    {
        frame = new JFrame("Dice Roll Game");
        switchPanel = new JPanel(new CardLayout()); 
        defaultState = new JPanel(new FlowLayout(FlowLayout.CENTER));
        postBetState = new JPanel(new BorderLayout());
        cleanupState = new JPanel(new BorderLayout());
        initButtons();
        switchPanel.add(defaultState, "Default");
        switchPanel.add(postBetState, "Post Bet");
        switchPanel.add(cleanupState, "Cleanup");
        frame.add(BorderLayout.CENTER, switchPanel);
        diceRoll = getIcon("/Resources/diceroll.gif");
        dice1 = getIcon("/Resources/diceroll1.png");
        dice2 = getIcon("/Resources/diceroll2.png");
        dice3 = getIcon("/Resources/diceroll3.png");
        dice4 = getIcon("/Resources/diceroll4.png");
        dice5 = getIcon("/Resources/diceroll5.png");
        dice6 = getIcon("/Resources/diceroll6.png");
        defaultState.add(betButton, BorderLayout.SOUTH);
        defaultState.add(quitButton, BorderLayout.SOUTH);
        postBetState.add(flipButton, BorderLayout.SOUTH);
        postBetState.add(diceRoll, BorderLayout.CENTER);
        cleanupState.add(okayButton, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowEvent)
            {
                controller.view.setGameViewOpen(false);
                controller.view.showWindow();
            }
        });
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
    private JLabel getIcon(String location) 
    {
        try
        {
            icon = new ImageIcon(getClass().getResource(location));
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        if (icon != null) return new JLabel(icon);
        else return new JLabel("image not found");
    }
    private void openBettingPane()
    {
        new ClientBettingPane(controller, this);
    }
    protected void cancelBetting()
    {
        defaultState();
    }
    protected void confirmBetting()
    {
        postBetState();
    }
    private void defaultState()
    {
        CardLayout layout = (CardLayout) switchPanel.getLayout();
        layout.show(switchPanel, "Default");
    }
    private void postBetState()
    {
        CardLayout layout = (CardLayout) switchPanel.getLayout();
        layout.show(switchPanel, "Post Bet");
    }
    private void cleanupState(int result)
    {
        CardLayout layout = (CardLayout) switchPanel.getLayout();
        switch (result)
        {
            case 1:
            cleanupState.add(dice1, BorderLayout.CENTER);
            break;
            case 2:
            cleanupState.add(dice2, BorderLayout.CENTER);
            break;
            case 3:
            cleanupState.add(dice3, BorderLayout.CENTER);
            break;
            case 4:
            cleanupState.add(dice4, BorderLayout.CENTER);
            break;
            case 5:
            cleanupState.add(dice5, BorderLayout.CENTER);
            break;
            case 6:
            cleanupState.add(dice6, BorderLayout.CENTER);
            break;
            default:
            cleanupState.add(new JLabel("result error"));
            break;
        }
        layout.show(switchPanel, "Cleanup");
    }
    private void initButtons()
    {
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.view.setGameViewOpen(false);
                controller.view.showWindow();
                frame.dispose();
            }
        });
        betButton = new JButton("Bet");
        betButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                openBettingPane();
            }
        });
        flipButton = new JButton("Flip");
        flipButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cleanupState(controller.getDiceRoll());
            }
        });
        okayButton = new JButton("Okay");
        okayButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                defaultState();
            }
        });
    }
}
