package characters;

import com.mygdx.game.SuperSmashShoot;
import general.Converter;
import general.DataManager;
import general.IDs;
import ui.Chat;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSpeaker extends Thread {

    private final String RESPONSES[] = {    "PARTY",                        //0
                                            "FRIEND REQUEST",               //1
                                            "CLOSE OK",                     //2
                                            "SENDING FRIEND LIST",          //3
                                            "CONNECT OK",                   //4
                                            "CONNECT ERROR",                //5
                                            "REGISTER OK",                  //6
                                            "REGISTER REPEATED",            //7
                                            "SENDING REQUEST LIST",         //8
                                            "SENDING PARTY REQUESTS",       //9
                                            "INVITATION SENT",              //10
                                            "INVITATION OFFLINE",           //11
                                            "INVITATION REPEATED",          //12
                                            "PARTY CREATED",                //13
                                            "JOIN OK",                      //14
                                            "PARTY FULL",                   //15
                                            "FRIEND REQUEST SENT",          //16
                                            "ALREADY FRIEND",               //17
                                            "NO PLAYER",                    //18
                                            "MESSAGE SENT",                 //19
                                            "ACCEPT FRIEND OK",             //20
                                            "ACCEPT FRIEND ERROR",          //21
                                            "PARTY LEFT",                   //22
                                            "SENDING STATS LIST",           //23
                                            "FRIEND REMOVED",               //24
                                            "FRIEND REMOVE ERROR",          //25
                                            "OK",                           //26
                                            "START FIGHT",                  //27
                                            "SEND PLAYER DATA PACKAGE"      //28
    };

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private boolean stop;
    private List<String> toSend;

    private List<String> friendsList, requestsList, partyList, statsList;

    public PlayerDataPackage pdp;

    public ServerSpeaker(){
        super.setDaemon(true);
        this.toSend = new ArrayList<>();
        this.requestsList = new ArrayList<>();
        this.partyList = new ArrayList<>();
        this.friendsList = new ArrayList<>();
        this.statsList = new ArrayList<>();
        this.toSend.add("NAN");
        this.stop = false;

        try {
            this.socket = new Socket("localhost", SuperSmashShoot.PORT);
            this.input = new DataInputStream(this.socket.getInputStream());
            this.output = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            SuperSmashShoot.ms_message.update("Couldn't connect to server, restart game");
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
    public List<String> getStatsList() {return this.statsList; }

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
                            Chat.addNewMessage(DataManager.userName, this.toSend.get(1));
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            for(String s : SuperSmashShoot.partyList.getList())
                                this.output.writeBytes(s + "\r\n");
                            this.output.writeBytes("END" + "\r\n");
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
                        case "ACCEPT FRIEND":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.flush();
                            break;
                        case "LEAVE PARTY":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            for(String friend : SuperSmashShoot.partyList.getList())
                                this.output.writeBytes(friend + "\r\n");
                            this.output.writeBytes("END" + "\r\n");
                            this.output.flush();
                            break;
                        case "STATS LIST":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.flush();
                            break;
                        case "REMOVE FRIEND":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.flush();
                            break;
                        case "LOAD CHARACTER SELECTOR" :
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            for(String friend : SuperSmashShoot.partyList.getList())
                                this.output.writeBytes(friend + "\r\n");
                            this.output.writeBytes("END" + "\r\n");
                            this.output.flush();
                            break;
                        case "LOAD MAP SELECTOR":
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            for(String friend : SuperSmashShoot.partyList.getList())
                                this.output.writeBytes(friend + "\r\n");
                            this.output.writeBytes("END" + "\r\n");
                            this.output.flush();
                            break;
                        case "START FIGHT" :
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.flush();
                            DataManager.playing = true;
                            break;
                        case "SEND PLAYER DATA PACKAGE" :
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.pdp.getUsr() + "\r\n");
                            this.output.writeBytes(String.valueOf(this.pdp.getPosition()[0]) + "\r\n");
                            this.output.writeBytes(String.valueOf(this.pdp.getPosition()[1]) + "\r\n");
                            this.output.writeBytes(this.pdp.getAnim() + "\r\n");
                            this.output.writeBytes(String.valueOf(this.pdp.isFlipAnim()) + "\r\n");
                            this.output.flush();
                            break;
                    }



                    String response = this.input.readLine();
                    //System.out.println("SERVER_SPEAKER: " + response);



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
                            List<String> toSend = new ArrayList<>();
                            toSend.add("STATS LIST");
                            this.toSend = new ArrayList<>(toSend);
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
                            DataManager.partyID = Converter.userNameToPartyId();
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[14])){
                            String host = this.input.readLine();
                            DataManager.partyID = Integer.parseInt(this.input.readLine());
                            System.out.println(DataManager.partyID);
                            SuperSmashShoot.ms_message.update("You have joined " + host + " party");
                            String playersInParty;

                            while(!(playersInParty = this.input.readLine()).equals("END"))
                                SuperSmashShoot.partyList.addItem(playersInParty);

                            List<String> toSend = new ArrayList<>();
                            toSend.add("PARTY REQUEST");
                            this.toSend = new ArrayList<>(toSend);
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

                        else if(response.equals(this.RESPONSES[19])){
                            this.resetToSend();
                        }
                        else if(response.equals(this.RESPONSES[20])){
                            SuperSmashShoot.ms_message.update("Friend added");
                            List<String> toSend = new ArrayList<>();
                            toSend.add("FRIEND LIST");
                            this.toSend = new ArrayList<>(toSend);
                        }

                        else if(response.equals(this.RESPONSES[21])){
                            SuperSmashShoot.ms_message.update("Couldn't add friend :(");
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[22])){
                            DataManager.partyID = -1;
                            SuperSmashShoot.partyList.resetList();
                            SuperSmashShoot.ms_message.update("You have left the party");
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[23])){
                            this.statsList = new ArrayList<>();
                            while(!(response = this.input.readLine()).equals("END"))
                                this.statsList.add(response);
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[24])){
                            SuperSmashShoot.ms_message.update("Friend removed");
                            List<String> toSend = new ArrayList<>();
                            toSend.add("FRIEND LIST");
                            this.toSend = new ArrayList<>(toSend);
                        }

                        else if(response.equals(this.RESPONSES[25])){
                            SuperSmashShoot.ms_message.update("Error removing, friend not found");
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[26])){
                            this.resetToSend();
                        }

                        else if(response.equals(this.RESPONSES[27])){
                            if(this.pdp == null){
                                this.pdp = new PlayerDataPackage(IDs.SOLDIER_IDLE, DataManager.userName);
                                System.out.println("crea el pdp como host");
                            }

                            List<String> toSend = new ArrayList<>();
                            toSend.add("SEND PLAYER DATA PACKAGE");
                            this.toSend = new ArrayList<>(toSend);
                        }

                        else if(response.equals(this.RESPONSES[28])){
                            if(DataManager.playing){
                                List<String> toSend = new ArrayList<>();
                                toSend.add("SEND PLAYER DATA PACKAGE");
                                this.toSend = new ArrayList<>(toSend);
                            }else{
                                this.resetToSend();
                            }
                        }


                } catch (IOException e) {
                   e.printStackTrace();
                }
            }

            try {
                Thread.sleep(5);
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
