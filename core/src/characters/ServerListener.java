package characters;

import com.mygdx.game.SuperSmashShoot;
import general.DataManager;
import general.IDs;
import screens.CharacterSelection;
import ui.Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerListener extends Thread {

    private SuperSmashShoot game;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private boolean stop;

    // Este hilo tiene una serie de variables (flags) para comunicarse con el hilo principal, ya que debido a como
    // esta programado OpenGL no se puede renderizar desde dos hilos distintos, asi que estos flags me permiten saber
    // cuando ha cambiado algun estado del host de la partida para aplicarlo al los demas clientes si fuese necesario.
    private boolean loadCharacterSelectorF;
    private boolean loadMapSelectorF;
    private boolean loadMapF;
    private String mapF;

    private final String COMMANDS[] = { "PARTY REQUEST SENT",           //0
                                        "CLOSE OK",                     //1
                                        "INVITATION RECEIVED",          //2
                                        "UPDATE PARTY",                 //3
                                        "NEW FRIEND",                   //4
                                        "RECEIVE MESSAGE",              //5
                                        "ACCEPTED FRIEND REQUEST",      //6
                                        "MEMBER LEFT",                  //7
                                        "REMOVED",                      //8
                                        "LOAD CHARACTER SELECTOR",      //9
                                        "LOAD MAP SELECTOR",            //10
                                        "START FIGHT",                  //11
                                        "DATA PACKAGES INFO"            //12
    };

    public List<PlayerDataPackage> pdpList;
    private ObjectInputStream objectInputStream;

    public ServerListener(SuperSmashShoot game){
        super.setDaemon(true);
        this.game = game;
        this.stop = false;
        this.loadCharacterSelectorF = false;
        this.loadMapSelectorF = false;
        this.loadMapF = false;

        this.pdpList = new ArrayList<>();

        try {
            this.socket = new Socket("localhost", SuperSmashShoot.PORT);
            this.input = new DataInputStream(this.socket.getInputStream());
            this.output = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getLoadCharacterSelectorF(){
        return this.loadCharacterSelectorF;
    }

    public void resetLoadCharacterSelectorF(){
        this.loadCharacterSelectorF = false;
    }

    public boolean getLoadMapSelectorF(){
        return this.loadMapSelectorF;
    }

    public void resetLoadMapSelectorF(){
        this.loadMapSelectorF = false;
    }

    public boolean getLoadMapF(){
        return this.loadMapF;
    }

    public void resetLoadMapF(){
        this.loadMapF = false;
    }

    public String getMapF(){
        return this.mapF;
    }

    public void resetMapF(){
        this.mapF = "";
    }

    @Override
    public void run() {
        while(true){
            try{
                String request = this.input.readLine();
                //System.out.println("SERVER_LISTENER: " + request);

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
                    SuperSmashShoot.ms_message.update("new message from " + sender.split("]")[1]
                            .replaceAll("\\[", "").replaceAll("]", ""));
                    Chat.addNewMessage(sender, message);
                }

                else if(this.COMMANDS[6].equals(request)){
                    String newFriend = this.input.readLine();
                    SuperSmashShoot.ms_message.update(newFriend + " is now your friend");
                    this.updateLists();
                }

                else if(this.COMMANDS[7].equals(request)){
                    String left = this.input.readLine();
                    SuperSmashShoot.ms_message.update(left + " has left the party");
                    SuperSmashShoot.partyList.getList().remove(left);

                    if(SuperSmashShoot.partyList.getList().size() == 0){
                        List<String> toSend = new ArrayList<>();
                        toSend.add("LEAVE PARTY");
                        SuperSmashShoot.serverSpeaker.setToSend(toSend);
                    }
                }

                else if(this.COMMANDS[8].equals(request)){
                    this.updateLists();
                }

                else if(this.COMMANDS[9].equals(request)){
                    this.loadCharacterSelectorF = true;
                }

                else if(this.COMMANDS[10].equals(request)){
                    this.loadMapSelectorF = true;
                }

                else if(this.COMMANDS[11].equals(request)){
                    SuperSmashShoot.ms_message.update("Loading...");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.mapF = this.input.readLine();
                    int ghostPlayers = Integer.parseInt(this.input.readLine());

                    if(SuperSmashShoot.serverSpeaker.pdp == null){
                        SuperSmashShoot.serverSpeaker.pdp = new PlayerDataPackage(IDs.SOLDIER_IDLE, DataManager.userName);
                        System.out.println("entra y crea el pdp");
                        DataManager.playing = true;
                    }

                    List<String> toSend = new ArrayList<>();
                    toSend.add("SEND PLAYER DATA PACKAGE");
                    SuperSmashShoot.serverSpeaker.setToSend(toSend);

                    for(int i = 0; i < ghostPlayers; i++)
                        this.pdpList.add(new PlayerDataPackage(IDs.SOLDIER_IDLE, SuperSmashShoot.partyList.getList().get(i)));

                    this.loadMapF = true;
                }

                else if(this.COMMANDS[12].equals(request)){
                    String usr = this.input.readLine();
                    int x = Integer.valueOf(this.input.readLine());
                    int y = Integer.valueOf(this.input.readLine());
                    String anim = this.input.readLine();
                    boolean flipAnim = Boolean.parseBoolean(this.input.readLine());
                    for(PlayerDataPackage pdps : this.pdpList)
                        if(usr.equals(pdps.getUsr())) {
                            pdps.update(new int[]{x, y}, anim);
                            pdps.setFlipAnim(flipAnim);
                        }
                }

            }catch (IOException e){
                e.printStackTrace();
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

    private void updateLists(){
        List<String> toSend = new ArrayList<>();
        toSend.add("FRIEND LIST");
        SuperSmashShoot.serverSpeaker.setToSend(toSend);
    }
}
