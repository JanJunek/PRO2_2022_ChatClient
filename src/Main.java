import gui.MainFrame;
import models.chatClients.ChatClient;
import models.chatClients.InMemoryChatClient;

public class Main {

    public static void main(String[] args) {

        MainFrame window = new MainFrame(800, 600);
    }
    /*
    private static void test(){

        ChatClient client = new InMemoryChatClient();

        client.login("junek");

        System.out.println(client.isAuthenticated());

        client.sendMessage("hello");
        client.sendMessage("hez");

        for (Message msg :
                client.getMessages()) {
            System.out.println(msg.getText());
        }
    }
     */
}