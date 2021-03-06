package characters;

import characters.bullets.Bullet;
import characters.bullets.GunBullet;
import characters.players.GhostPlayer;
import com.mygdx.game.SuperSmashShoot;
import general.Converter;
import general.DataManager;
import maps.Map;
import screens.CharacterSelection;
import ui.Chat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ServerSpeaker extends Thread {

    private Socket socket;
    private DatagramSocket udp;
    private DatagramPacket udpPacket;
    private byte message[];
    private DataInputStream input;
    private DataOutputStream output;
    private boolean stop;
    private List<String> toSend;

    private List<String> friendsList, requestsList, partyList, statsList;

    public static PlayerDataPackage pdp;
    public static String HOST;
    public static String response;

    public ServerSpeaker(String host){
        super.setDaemon(true);
        this.toSend = new ArrayList<>();
        this.requestsList = new ArrayList<>();
        this.partyList = new ArrayList<>();
        this.friendsList = new ArrayList<>();
        this.statsList = new ArrayList<>();
        this.toSend.add("NAN");
        this.stop = false;
        ServerSpeaker.HOST = host;

        try {
            this.socket = new Socket(host, SuperSmashShoot.PORT);
            this.input = new DataInputStream(this.socket.getInputStream());
            this.output = new DataOutputStream(this.socket.getOutputStream());

            this.message = new byte[1024];

            this.udp = new DatagramSocket();
            this.udpPacket = new DatagramPacket(this.message, this.message.length, InetAddress.getByName(host), 6866);
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

        response = "";
        boolean loop = true;

        while(loop){
            if(!this.toSend.get(0).equals("NAN") && this.socket.isConnected()){
                try {
                    switch (this.toSend.get(0)) {
                        case "CONNECT" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.writeBytes(this.toSend.get(2) + "\r\n");
                            this.output.writeBytes(this.socket.getLocalAddress().getHostAddress() + "\r\n");
                            this.output.writeBytes(String.valueOf(ServerListener.udpPort) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "CLOSE" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "FRIEND LIST" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "REGISTER" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.writeBytes(this.toSend.get(2) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "FRIEND REQUEST" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "PARTY REQUEST" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "SEND PARTY REQUEST" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "SEND PARTY INVITATION" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.writeBytes(this.toSend.get(2) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "SEND MESSAGE" : {
                            Chat.addNewMessage(DataManager.userName, this.toSend.get(1));
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            for (String s : SuperSmashShoot.partyList.getList())
                                this.output.writeBytes(s + "\r\n");
                            this.output.writeBytes("END" + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "JOIN PARTY" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "CREATE PARTY" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "ADD FRIEND" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "ACCEPT FRIEND" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "LEAVE PARTY" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            for (String friend : SuperSmashShoot.partyList.getList())
                                this.output.writeBytes(friend + "\r\n");
                            this.output.writeBytes("END" + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "STATS LIST" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "REMOVE FRIEND" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "LOAD CHARACTER SELECTOR" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            for (String friend : SuperSmashShoot.partyList.getList()) {
                                this.output.writeBytes(friend + "\r\n");
                            }
                            this.output.writeBytes("END" + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "LOAD MAP SELECTOR" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(CharacterSelection.chosenCharacter.toString() + "\r\n");
                            this.output.writeBytes(String.valueOf(CharacterSelection.skin) + "\r\n");
                            this.output.writeBytes((DataManager.partyID == Converter.userNameToPartyId()) ? "IS HOST" + "\r\n" : "NOT HOST" + "\r\n");
                            for (String friend : SuperSmashShoot.partyList.getList()) {
                                this.output.writeBytes(friend + "\r\n");
                            }
                            this.output.writeBytes("END" + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "START FIGHT" : {
                            this.output.writeBytes(this.toSend.get(0) + "\r\n");
                            this.output.writeBytes(this.toSend.get(1) + "\r\n");
                            this.output.flush();

                            break;
                        }
                        case "SEND PLAYER DATA PACKAGE" : {
                            if (ServerListener.startUdpPackages) {
                                String packet = this.toSend.get(0) + ":" + ServerSpeaker.pdp.getUsr() + ":" + String.valueOf(ServerSpeaker.pdp.getPosition()[0])
                                        + ":" + String.valueOf(ServerSpeaker.pdp.getPosition()[1]) + ":" + ServerSpeaker.pdp.getAnim() + ":" +
                                        (String.valueOf((ServerSpeaker.pdp.isFlipAnim()) ? "1" : "0"));

                                for (GhostPlayer gp : Map.ghostPlayers) {
                                    packet += ":USER|" + gp.getUsr() + "|" + gp.getLife();
                                }

                                /*--------------------- UDP VERSION ---------------------**/
                                try {
                                    List<Bullet> bullets = Map.bullets;
                                    for (Bullet b : bullets) {
                                        String bulletPacket = ":BULLET:" + DataManager.userName + ":" + (int) b.getSprite().getX() + ":" + (int) b.getSprite().getY() + ":"
                                                + GunBullet.bulletID;
                                        packet += bulletPacket;
                                    }
                                } catch (NullPointerException | ConcurrentModificationException ignored) {
                                }

                                this.udpPacket.setData(packet.getBytes(), 0, packet.getBytes().length);
                                this.udp.send(this.udpPacket);
                            }

                            break;
                        }
                    }


                    if (!DataManager.playing && !response.equals("STOP TCP")) {
                        response = this.input.readLine();
                    }


                    if (response != null && !response.equals("STOP TCP")){
                        switch (response) {
                            case "PARTY": {
                                String friendName = this.input.readLine();
                                SuperSmashShoot.ms_message.update(friendName + " has invited you to a party");

                                break;
                            }

                            case "FRIEND REQUEST": {
                                String requester = this.input.readLine();
                                SuperSmashShoot.ms_message.update(requester + " has sent you a friend request");

                                break;
                            }

                            case "CLOSE OK": {
                                break;
                            }

                            case "SENDING FRIEND LIST": {
                                this.friendsList = new ArrayList<>();
                                while (!(response = this.input.readLine()).equals("END")) {
                                    this.friendsList.add(response);
                                }

                                List<String> toSend = new ArrayList<>();
                                toSend.add("FRIEND REQUEST");
                                this.toSend = new ArrayList<>(toSend);

                                break;
                            }

                            case "CONNECT OK": {
                                DataManager.connected = true;
                                SuperSmashShoot.ms_message.update("Connected Successfully!");
                                DataManager.userName = this.toSend.get(1);
                                List<String> toSend = new ArrayList<>();
                                toSend.add("FRIEND LIST");
                                this.toSend = new ArrayList<>(toSend);

                                break;
                            }

                            case "CONNECT ERROR": {
                                SuperSmashShoot.ms_message.update("Error while logging");
                                this.resetToSend();

                                break;
                            }

                            case "REGISTER OK": {
                                String usr = this.input.readLine();
                                SuperSmashShoot.ms_message.update("Registered user " + usr);
                                this.resetToSend();

                                break;
                            }

                            case "REGISTER REPEATED": {
                                SuperSmashShoot.ms_message.update("Error while registering");
                                this.resetToSend();

                                break;
                            }

                            case "SENDING REQUEST LIST": {
                                this.requestsList = new ArrayList<>();
                                while (!(response = this.input.readLine()).equals("END"))
                                    this.requestsList.add(response);
                                List<String> toSend = new ArrayList<>();
                                toSend.add("PARTY REQUEST");
                                this.toSend = new ArrayList<>(toSend);

                                break;
                            }

                            case "SENDING PARTY REQUESTS": {
                                this.partyList = new ArrayList<>();
                                while (!(response = this.input.readLine()).equals("END"))
                                    this.partyList.add(response);
                                List<String> toSend = new ArrayList<>();
                                toSend.add("STATS LIST");
                                this.toSend = new ArrayList<>(toSend);

                                break;
                            }

                            case "INVITATION SENT": {
                                SuperSmashShoot.ms_message.update("Invitation sent");
                                List<String> toSend = new ArrayList<>();
                                toSend.add("CREATE PARTY");
                                this.toSend = new ArrayList<>(toSend);

                                break;
                            }

                            case "INVITATION OFFLINE": {
                                SuperSmashShoot.ms_message.update("Player is offline");
                                this.resetToSend();

                                break;
                            }

                            case "INVITATION REPEATED": {
                                SuperSmashShoot.ms_message.update("Invitation already sent!!");
                                this.resetToSend();

                                break;
                            }

                            case "PARTY CREATED": {
                                DataManager.partyID = Converter.userNameToPartyId();
                                this.resetToSend();

                                break;
                            }

                            case "JOIN OK": {
                                String host = this.input.readLine();
                                DataManager.partyID = Integer.parseInt(this.input.readLine());
                                System.out.println(DataManager.partyID);
                                SuperSmashShoot.ms_message.update("You have joined " + host + " party");
                                String playersInParty;

                                while (!(playersInParty = this.input.readLine()).equals("END"))
                                    SuperSmashShoot.partyList.addItem(playersInParty);

                                List<String> toSend = new ArrayList<>();
                                toSend.add("PARTY REQUEST");
                                this.toSend = new ArrayList<>(toSend);

                                break;
                            }

                            case "PARTY FULL": {
                                SuperSmashShoot.ms_message.update("Can't join, party is full");
                                this.resetToSend();

                                break;
                            }

                            case "FRIEND REQUEST SENT": {
                                SuperSmashShoot.ms_message.update("Friend request sent");
                                this.resetToSend();

                                break;
                            }

                            case "ALREADY FRIEND": {
                                SuperSmashShoot.ms_message.update("You are already friends or invitation has been sent");
                                this.resetToSend();

                                break;
                            }

                            case "NO PLAYER": {
                                SuperSmashShoot.ms_message.update("No player found :(");
                                this.resetToSend();

                                break;
                            }

                            case "MESSAGE SENT": {
                                this.resetToSend();

                                break;
                            }

                            case "ACCEPT FRIEND OK": {
                                SuperSmashShoot.ms_message.update("Friend added");
                                List<String> toSend = new ArrayList<>();
                                toSend.add("FRIEND LIST");
                                this.toSend = new ArrayList<>(toSend);

                                break;
                            }

                            case "ACCEPT FRIEND ERROR": {
                                SuperSmashShoot.ms_message.update("Couldn't add friend :(");
                                this.resetToSend();

                                break;
                            }

                            case "PARTY LEFT": {
                                DataManager.partyID = -1;
                                SuperSmashShoot.partyList.resetList();
                                SuperSmashShoot.ms_message.update("You have left the party");
                                this.resetToSend();

                                break;
                            }

                            case "SENDING STATS LIST": {
                                this.statsList = new ArrayList<>();
                                while (!(response = this.input.readLine()).equals("END"))
                                    this.statsList.add(response);
                                this.resetToSend();

                                break;
                            }

                            case "FRIEND REMOVED": {
                                SuperSmashShoot.ms_message.update("Friend removed");
                                List<String> toSend = new ArrayList<>();
                                toSend.add("FRIEND LIST");
                                this.toSend = new ArrayList<>(toSend);

                                break;
                            }

                            case "FRIEND REMOVE ERROR": {
                                SuperSmashShoot.ms_message.update("Error removing, friend not found");
                                this.resetToSend();

                                break;
                            }

                            case "OK": {
                                this.resetToSend();

                                break;
                            }

                            case "START FIGHT": {
                                if (ServerSpeaker.pdp == null) {
                                    ServerSpeaker.pdp = new PlayerDataPackage(CharacterSelection.chosenCharacter, CharacterSelection.skin, DataManager.userName);
                                }

                                List<String> toSend = new ArrayList<>();
                                toSend.add("SEND PLAYER DATA PACKAGE");
                                this.toSend = new ArrayList<>(toSend);
                                response = "STOP TCP";
                                DataManager.playing = true;

                                break;
                            }

                            case "SEND PLAYER DATA PACKAGE": {
                                if (DataManager.playing) {
                                    List<String> toSend = new ArrayList<>();
                                    toSend.add("SEND PLAYER DATA PACKAGE");
                                    this.toSend = new ArrayList<>(toSend);
                                } else {
                                    this.resetToSend();
                                }

                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                   e.printStackTrace();
                }

                if(response.equals("CLOSE OK"))
                    break;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            this.input.close();
            this.output.close();
            this.socket.close();
            this.udp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetToSend(){
        this.toSend = new ArrayList<>();
        this.toSend.add("NAN");
    }
}
