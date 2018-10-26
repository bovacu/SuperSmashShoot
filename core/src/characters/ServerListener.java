package characters;

import com.mygdx.game.SuperSmashShoot;

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

    private final String COMMANDS[] = {"PARTY REQUEST SENT"};

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
                    List<String> toSend = new ArrayList<>();
                    toSend.add("FRIEND LIST");
                    SuperSmashShoot.serverSpeaker.setToSend(toSend);
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
    }
}
