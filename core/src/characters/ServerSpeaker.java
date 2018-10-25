package characters;

import com.mygdx.game.SuperSmashShoot;
import general.DataManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSpeaker extends Thread {

    private final String RESPONSES[] = {"PARTY", "FRIEND REQUEST", "CLOSE OK", "SENDING FRIEND LIST", "CONNECT OK", "CONNECT ERROR",
    "REGISTER OK", "REGISTER REPEATED"};

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private boolean stop;
    private List<String> toSend;

    private List<String> friendsList;

    public ServerSpeaker(){
        super.setDaemon(true);
        this.toSend = new ArrayList<>();
        this.toSend.add("NAN");
        this.stop = false;

        try {
            this.socket = new Socket("localhost", SuperSmashShoot.PORT);
            this.input = new DataInputStream(this.socket.getInputStream());
            this.output = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setToSend(List<String> toSend){
        this.toSend = new ArrayList<>(toSend);
    }

    public List<String> getFriendsList(){
        return this.friendsList;
    }

    @Override
    public void run() {
        while(true){
            if(!this.toSend.get(0).equals("NAN")){
                try {
                    if(this.toSend.get(0).equals("CONNECT")){
                        this.output.writeBytes(this.toSend.get(0) + "\r\n");
                        this.output.writeBytes(this.toSend.get(1) + "\r\n");
                        this.output.writeBytes(this.toSend.get(2) + "\r\n");
                        this.output.flush();
                    }else if(this.toSend.get(0).equals("CLOSE")){
                        this.output.writeBytes(this.toSend.get(0) + "\r\n");
                        this.output.flush();
                    }else if(this.toSend.get(0).equals("FRIEND LIST")){
                        this.output.writeBytes(this.toSend.get(0) + "\r\n");
                        this.output.flush();
                    }else if(this.toSend.get(0).equals("REGISTER")){
                        this.output.writeBytes(this.toSend.get(0) + "\r\n");
                        this.output.writeBytes(this.toSend.get(1) + "\r\n");
                        this.output.writeBytes(this.toSend.get(2) + "\r\n");
                        this.output.flush();
                    }

                    String response = this.input.readLine();
                    System.out.println(response);

                    if(response != null)
                        if(response.equals(this.RESPONSES[0])){
                            String friendName = this.input.readLine();
                            SuperSmashShoot.ms_message.update(friendName + " has invited you to a party");
                        }

                        else if(response.equals(this.RESPONSES[1])){
                            String requester = this.input.readLine();
                            SuperSmashShoot.ms_message.update(requester + " has sent you a friend request");
                        }

                        else if(response.equals(this.RESPONSES[2])){
                            break;
                        }

                        else if(response.equals(this.RESPONSES[3])){
                            this.friendsList = new ArrayList<>();
                            this.resetToSend();
                            while((response = this.input.readLine()) != null)
                                this.friendsList.add(response);
                        }

                        else if(response.equals(this.RESPONSES[4])){
                            DataManager.connected = true;
                            SuperSmashShoot.ms_message.update("Connected Successfully!");
                            DataManager.userName = this.toSend.get(1);
                            List<String> toSend = new ArrayList<>();
                            toSend.add("FRIEND LIST");
                            this.toSend = new ArrayList<>(toSend);
                        }

                        else if(response.equals(this.RESPONSES[5])){
                            SuperSmashShoot.ms_message.update("Error while logging");
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[6])){
                            String usr = this.input.readLine();
                            SuperSmashShoot.ms_message.update("Registered user " + usr);
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[7])){
                            SuperSmashShoot.ms_message.update("Error while registering");
                            this.resetToSend();
                        }

                } catch (IOException e) {
                   e.printStackTrace();
                }
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

    private void resetToSend(){
        this.toSend = new ArrayList<>();
        this.toSend.add("NAN");
    }

    public void sendUserNameToServer(){
        try {
            this.output.writeBytes(DataManager.userName + "\r\n");
            this.output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSpeaker(){
        List<String> toSend = new ArrayList<>();
        toSend.add("CLOSE");
        this.setToSend(toSend);
    }
}
