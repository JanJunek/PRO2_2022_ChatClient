package gui;

import models.chatClients.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    ChatClient chatClient;

    JTextArea txtChat;
    public MainFrame(int width, int height, ChatClient chatClient){
        super("PRO2 2022 ChatClient skB");
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.chatClient = chatClient;
        initGui();
        setVisible(true);
    }

    private void initGui(){
        JPanel panelMain = new JPanel(new BorderLayout());
        panelMain.add(initLoginPanel(), BorderLayout.NORTH);
        panelMain.add(initChatPanel(), BorderLayout.CENTER);
        panelMain.add(initLoggedUsersPanel(), BorderLayout.EAST);
        panelMain.add(initMessagePanel(), BorderLayout.SOUTH);
        add(panelMain);
    }
    private JPanel initLoginPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Username"));
        JTextField txtInputUsername = new JTextField("", 30);
        panel.add(txtInputUsername);
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn login clicked - " + txtInputUsername.getText());

                if (chatClient.isAuthenticated()) {
                    chatClient.logout();
                    btnLogin.setText("Login");
                    txtInputUsername.setEditable(true);
                    txtChat.setEnabled(false);


                } else {
                    String userName = txtInputUsername.getText();
                    if (userName.length()<1) {
                        JOptionPane.showMessageDialog(null, "Enter ur username0",
                                "error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    chatClient.login(txtInputUsername.getText());
                    btnLogin.setText("Logout");
                    txtInputUsername.setEditable(false);
                    txtChat.setEnabled(true);
                }
                //chatClient.login(txtInputUsername.getName());
            }
        });
        panel.add(btnLogin);
        return panel;
    }
    private JPanel initChatPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        txtChat = new JTextArea();
        txtChat.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(txtChat);

        panel.add(scrollPane);
        chatClient.addActionListenerLoggedUsersChanged(e -> {
            refreshMessages();
        });
        return panel;
    }
    private JPanel initMessagePanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtInputMessage = new JTextField("", 50);
        panel.add(txtInputMessage);
        JButton btnSendMessage = new JButton("Send");
        btnSendMessage.addActionListener(e -> {
            String messageText = txtInputMessage.getText();
            System.out.println("btn send clicked - " + messageText);
            if (messageText.length() < 1) {
                return;
            }
            if (!chatClient.isAuthenticated()) {
                return;
            }
            chatClient.sendMessage(messageText);
            //txtChat.append(txtInputMessage.getText() + "\n");
            txtInputMessage.setText("");

            refreshMessages();
        });
        panel.add(btnSendMessage);
        return panel;
    }
    private JPanel initLoggedUsersPanel(){
            JPanel panel = new JPanel();
            /*
            Object[][] data = new Object[][]{
                    {"1,1", "2,2"},
                    {"2,1", "2,2"},
                    {"sdasdas", "sdfds"}
            };
            String[] columnNames = new  String[] {"Col A", "B"};

            JTable tblLoggedUsers = new JTable(data,columnNames);
             */
            JTable tblLoggedUsers = new JTable();
            LoggedUsersTableModel LoggedUsersTableModel = new LoggedUsersTableModel(chatClient);
            tblLoggedUsers.setModel(LoggedUsersTableModel);

            chatClient.addActionListenerLoggedUsersChanged(e -> {
                LoggedUsersTableModel.fireTableDataChanged();
            });

            JScrollPane scrollPane = new JScrollPane(tblLoggedUsers);
            scrollPane.setPreferredSize(new Dimension(250,500));
            panel.add(scrollPane);
            return panel;
    }
    private void refreshMessages(){
        if (!chatClient.isAuthenticated() ) {
            return;
        }

        txtChat.setText("");
        for (Message msg:
             chatClient.getMessages()) {
            txtChat.append(msg.getText());
        }
    }
}
