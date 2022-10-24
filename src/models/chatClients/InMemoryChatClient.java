package models.chatClients;

import gui.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InMemoryChatClient implements ChatClient {
    private String loggedUser;
    private List<String> loggedUsers;
    private List<Message> messages;
    private  List<ActionListener> listennerLoggedUsersChanged = new ArrayList<>();
    private List<ActionListener> listenerMessagesChanged = new ArrayList<>();

    public InMemoryChatClient(){
        loggedUsers = new ArrayList<>();
        messages = new ArrayList<>();
    }


    @Override
    public void sendMessage(String text) {
        messages.add(new Message(loggedUser, text));
        raiseEventMessagesChanged();

    }

    @Override
    public void login(String userName) {
        loggedUser = userName;
        loggedUsers.add(userName);
        addSystemMessage(Message.USER_LOGGED_IN, userName);
        raiseEventLoggedUsersChanged();
    }

    @Override
    public void logout() {
        loggedUsers.remove(loggedUser);
        addSystemMessage(Message.USER_LOGGED_IN, loggedUser);
        loggedUser = null;
        raiseEventLoggedUsersChanged();
    }

    @Override
    public Boolean isAuthenticated() {
        return loggedUser!=null;
    }

    @Override
    public List<String> getLoggedUsers() {
        return loggedUsers;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public void addActionListenerLoggedUsersChanged(ActionListener toAdd) {
        listennerLoggedUsersChanged.add(toAdd);
    }

    @Override
    public void addActionListenerMessagesChanged(ActionListener toAdd) {
        listenerMessagesChanged.add(toAdd);
    }

    private void raiseEventLoggedUsersChanged(){
        for (ActionListener al:
             listennerLoggedUsersChanged) {
            al.actionPerformed(new ActionEvent(this, 1, "userChanged"));
        }
    }
    private void raiseEventMessagesChanged(){
        for (ActionListener al:
             listenerMessagesChanged) {
            al.actionPerformed(
                    new ActionEvent(this, 1, "messageChanged")
            );
        }
    }

    private void  addSystemMessage(int type, String userName){
        messages.add(new Message(type, userName));
    }
}
