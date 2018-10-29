package characters;

import com.mygdx.game.SuperSmashShoot;
import ui.Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerListener extends Thread {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private boolean stop;

    private final String COMMANDS[] = { "PARTY REQUEST SENT",           //0
                                        "CLOSE OK",                     //1
                                        "INVITATION RECEIVED",          //2
                                        "UPDATE PARTY",                 //3
                                        "NEW FRIEND",                   //4
                                        "RECEIVE MESSAGE"               //5
    };

    public ServerListener(){
        super.setDaemon(true);
        this.stop = false;

        try {
            this.socket = new Socket("localhost", SuperSmashShoot.PORT);
            this.input = new DataInputStream(this.socket.getInputStream());
            this.output = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            try{
                String request = this.input.readLine();
                System.out.println("SERVER_LISTENER: " + request);

                if(this.COMMANDS[0].equals(request)){
                    String friend = this.input.readLine();
                    SuperSmashShoot.ms_message.update(friend + " has invited you to a party");
                    this.updateLists();
                }

                else if(this.COMMANDS[1].equals(request)){
                    break;
                }

                else if(this.COMMANDS[2].equals(request)){
                    String host = this.input.readLine();
                    SuperSmashShoot.ms_message.update(host + " has invited you to his party");
                    this.updateLists();
                }

                else if(this.COMMANDS[3].equals(request)){
                    String newPlayer = this.input.readLine();
                    SuperSmashShoot.ms_message.update(newPlayer + " has joined the party");
                    SuperSmashShoot.partyList.addItem(newPlayer);
                    this.updateLists();
                }

                else if(this.COMMANDS[4].equals(request)){
                    String newFriend = this.input.readLine();
                    SuperSmashShoot.ms_message.update(newFriend + " has sent you a friend request");
                    this.updateLists();
                }

                else if(this.COMMANDS[5].equals(request)){
                    String sender = this.input.readLine();
                    String message = this.input.readLine();
                    SuperSmashShoot.ms_message.update("new message from " + sender);
                    Chat.addNewMessage(sender, message);
                }

            }catch (IOException e){
                e.printStackTrace();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            this.input.close();
            this.output.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateLists(){
        List<String> toSend = new ArrayList<>();
        toSend.add("FRIEND LIST");
        SuperSmashShoot.serverSpeaker.setToSend(toSend);
    }
}
