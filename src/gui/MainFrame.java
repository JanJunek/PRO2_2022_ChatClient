package gui;

import models.chatClients.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    JTextField txtInputName, txtInputMessage;
    JTextArea txtChat;
    public MainFrame(int width, int height){
        super("ChatClient");
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initGui();
        setVisible(true); // set visible ay po priprave GUI
    }
    private void initGui(){
        JPanel panelMain = new JPanel(new BorderLayout());

        panelMain.add(initLoginPanel(), BorderLayout.NORTH);
        panelMain.add(initChatPanel(), BorderLayout.CENTER);
        panelMain.add(initMessagePanel(), BorderLayout.SOUTH);

        add(panelMain);
    }

    private JPanel initLoginPanel(){
        JPanel panelLogin = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtInputMessage = new JTextField();
        panelLogin.add(new JTextField("", 30));

        panelLogin.add(txtInputName);

        JButton btnLogin = new JButton("login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("login clicked-" + txtInputName.getText());
            }
        });
        panelLogin.add(btnLogin);
        return panelLogin;
    }
    private JPanel initChatPanel(){
        JPanel panelChat = new JPanel();
        panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.X_AXIS));

        JTextArea txtChat = new JTextArea();
        txtChat.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(txtChat);

        for (int i = 0; i < 50; i++) {
            txtChat.append("Message" + i + "\n");
        }

        panelChat.add(scrollPane);

        return panelChat;
    }
    private JPanel initMessagePanel(){
        JPanel panelMessage = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtInputMessage = new JTextField("",50);
        panelMessage.add(txtInputMessage);

        JButton btnSend = new JButton("Send");
        btnSend.addActionListener(e -> {
            System.out.println("btnSend clicked" + txtInputMessage.getText());
            txtChat.append(txtInputMessage.getText() + "\n");
        });
        panelMessage.add(btnSend);

        return panelMessage;
    }
}
