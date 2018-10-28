package characters;

import com.mygdx.game.SuperSmashShoot;
import general.Converter;
import general.DataManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSpeaker extends Thread {

    private final String RESPONSES[] = {"PARTY", "FRIEND REQUEST", "CLOSE OK", "SENDING FRIEND LIST", "CONNECT OK", "CONNECT ERROR",
    "REGISTER OK", "REGISTER REPEATED", "SENDING REQUEST LIST", "SENDING PARTY REQUESTS", "INVITATION SENT", "INVITATION OFFLINE",
    "INVITATION REPEATED", "PARTY CREATED", "JOIN OK", "PARTY FULL", "FRIEND REQUEST SENT", "ALREADY FRIEND", "NO PLAYER"};

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private boolean stop;
    private List<String> toSend;

    private List<String> friendsList, requestsList, partyList;

    public ServerSpeaker(){
        super.setDaemon(true);
        this.toSend = new ArrayList<>();
        this.requestsList = new ArrayList<>();
        this.partyList = new ArrayList<>();
        this.toSend.add("NAN");
        this.stop = false;

        try {
            this.socket = new Socket("192.168.1.40", SuperSmashShoot.PORT);
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
    public List<String> getRequestsList(){return this.requestsList; }
    public List<String> getPartyList(){return this.partyList; }

    @Override
    public void run() {
        while(true){
            if(!this.toSend.get(0).equals("NAN") && this.socket.isConnected()){
                try {
                    switch (this.toSend.get(0)) {
                        case "CONNECT":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.writeBytes(this.toSend.get(2) + "\r\n");
                            this.output.flush();
                            break;
                        case "CLOSE":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.flush();
                            break;
                        case "FRIEND LIST":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.flush();
                            break;
                        case "REGISTER":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.writeBytes(this.toSend.get(2) + "\r\n");
                            this.output.flush();
                            break;
                        case "FRIEND REQUEST":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.flush();
                            break;
                        case "PARTY REQUEST":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.flush();
                            break;
                        case "SEND PARTY REQUEST":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.flush();
                            break;
                        case "SEND PARTY INVITATION":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.writeBytes(this.toSend.get(2) + "\r\n");
                            this.output.flush();
                            break;
                        case "SEND MESSAGE":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.writeBytes(this.toSend.get(2) + "\r\n");
                            this.output.flush();
                            break;
                        case "JOIN PARTY":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.flush();
                            break;
                        case "CREATE PARTY":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.flush();
                            break;
                        case "ADD FRIEND":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.flush();
                            break;
                    }



                    String response = this.input.readLine();
                    System.out.println("SERVER_SPEAKER: " + response);



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
                            while(!(response = this.input.readLine()).equals("END")) {
                                this.friendsList.add(response);
                            }

                            List<String> toSend = new ArrayList<>();
                            toSend.add("FRIEND REQUEST");
                            this.toSend = new ArrayList<>(toSend);
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

                        else if(response.equals(this.RESPONSES[8])){
                            this.requestsList = new ArrayList<>();
                            while(!(response = this.input.readLine()).equals("END"))
                                this.requestsList.add(response);
                            List<String> toSend = new ArrayList<>();
                            toSend.add("PARTY REQUEST");
                            this.toSend = new ArrayList<>(toSend);
                        }

                        else if(response.equals(this.RESPONSES[9])){
                            this.partyList = new ArrayList<>();
                            while(!(response = this.input.readLine()).equals("END"))
                                this.partyList.add(response);
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[10])){
                            SuperSmashShoot.ms_message.update("Invitation sent");
                            List<String> toSend = new ArrayList<>();
                            toSend.add("CREATE PARTY");
                            this.toSend = new ArrayList<>(toSend);
                        }

                        else if(response.equals(this.RESPONSES[11])){
                            SuperSmashShoot.ms_message.update("Player is offline");
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[12])){
                            SuperSmashShoot.ms_message.update("Invitation already sent!!");
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[13])){
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[14])){
                            String host = this.input.readLine();
                            SuperSmashShoot.ms_message.update("You have joined " + host + " party");
                            String playersInParty;

                            while(!(playersInParty = this.input.readLine()).equals("END"))
                                SuperSmashShoot.partyList.addItem(playersInParty);
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[15])){
                            SuperSmashShoot.ms_message.update("Can't join, party is full");
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[16])){
                            SuperSmashShoot.ms_message.update("Friend request sent");
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[17])){
                            SuperSmashShoot.ms_message.update("You are already friends or invitation has been sent");
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[18])){
                            SuperSmashShoot.ms_message.update("No player found :(");
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
}
