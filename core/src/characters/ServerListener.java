package characters;

import characters.bullets.BulletDataPackage;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.SuperSmashShoot;
import general.DataManager;
import general.IDs;
import screens.CharacterSelection;
import ui.Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServerListener extends Thread {

    private SuperSmashShoot game;

    private Socket socket;
    private DatagramSocket udp;
    private DatagramPacket udpPacket;
    private byte message[];
    private DataInputStream input;
    private DataOutputStream output;
    private boolean stop;

    public static int udpPort = new Random().nextInt((7000 - 6869) + 1) + 6869;

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
                                        "SEND PLAYER DATA PACKAGE"      //12
    };

    public List<PlayerDataPackage> pdpList;
    public static List<BulletDataPackage> bdpList;
    public static boolean startUdpPackages;

    public ServerListener(SuperSmashShoot game, String host){
        super.setDaemon(true);
        this.game = game;
        this.stop = false;
        this.loadCharacterSelectorF = false;
        this.loadMapSelectorF = false;
        this.loadMapF = false;
        ServerListener.startUdpPackages = false;

        this.pdpList = new ArrayList<>();
        ServerListener.bdpList = new ArrayList<>();

        try {
            this.socket = new Socket(host, SuperSmashShoot.PORT);
            this.input = new DataInputStream(this.socket.getInputStream());
            this.output = new DataOutputStream(this.socket.getOutputStream());

            this.message = new byte[256];

            this.udp = new DatagramSocket(ServerListener.udpPort);

            this.udpPacket = new DatagramPacket(this.message, this.message.length);
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
                String request;

                if(!DataManager.playing){
                    request = this.input.readLine();
                }else
                    request = "SEND PLAYER DATA PACKAGE";

                if(request.split(":").length > 1)
                    request = request.split(":")[0];

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
                    System.out.println(DataManager.userName + " hace el update de la party");
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
                    System.out.println("vuelve a entrar al loading");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.mapF = this.input.readLine();
                    int ghostPlayers = Integer.parseInt(this.input.readLine());
                    String playersAndSkins = this.input.readLine();

                    String users[] = playersAndSkins.split("USER:");

                    if(ServerSpeaker.pdp == null){
                        ServerSpeaker.pdp = new PlayerDataPackage(CharacterSelection.chosenCharacter, CharacterSelection.skin, DataManager.userName);
                        DataManager.playing = true;
                    }

                    for(int i = 0; i < ghostPlayers; i++) {
                        for(String user : users){
                            if(SuperSmashShoot.partyList.getList().get(i).equals(user.split(":")[0])) {
                                this.pdpList.add(new PlayerDataPackage(IDs.CharacterType.valueOf(user.split(":")[1]),
                                        Integer.parseInt(user.split(":")[2]), SuperSmashShoot.partyList.getList().get(i)));
                            }
                        }
                    }

                    this.loadMapF = true;
                    ServerSpeaker.response = "STOP TCP";
                    ServerListener.startUdpPackages = true;

                    List<String> toSend = new ArrayList<>();
                    toSend.add("SEND PLAYER DATA PACKAGE");
                    SuperSmashShoot.serverSpeaker.setToSend(toSend);
                }

                else if(this.COMMANDS[12].equals(request)){
                    /**--------------------- TCP VERSION --------------------**/
//                    String usr = this.input.readLine();
//                    int x = Integer.valueOf(this.input.readLine());
//                    int y = Integer.valueOf(this.input.readLine());
//                    String anim = this.input.readLine();
//                    boolean flipAnim = Boolean.parseBoolean(this.input.readLine());
//                    for(PlayerDataPackage pdps : this.pdpList)
//                        if(usr.equals(pdps.getUsr())) {
//                            pdps.update(new int[]{x, y}, anim);
//                            pdps.setFlipAnim(flipAnim);
//                        }


                    /**--------------------- UDP VERSION ---------------------**/
                    this.udp.receive(this.udpPacket);

                    String packet[] = new String(this.udpPacket.getData(), 0, this.udpPacket.getLength()).replaceAll("\r\n", "").split(":");

                    String usr = packet[1];
                    int x = Integer.parseInt(packet[2]);
                    int y = Integer.parseInt(packet[3]);
                    String anim = packet[4];
                    boolean flipAnim = (packet[5].equals("1"));

                    for(String pack : packet){
                        if(pack.contains("USER")){
                            if(pack.split("\\|")[1].equals(DataManager.userName)){
                                int updatedLife = Integer.parseInt(pack.split("\\|")[2]);
                                ServerSpeaker.pdp.setLife(updatedLife);
                            }
                        }
                    }

                    for(PlayerDataPackage pdps : this.pdpList)
                        if(usr.equals(pdps.getUsr())) {
                            pdps.update(new int[]{x, y}, anim);
                            pdps.setFlipAnim(flipAnim);
                        }

                    if(new String(this.udpPacket.getData(), 0, this.udpPacket.getLength()).contains("BULLET")){
                        String bullets[] = new String(this.udpPacket.getData(), 0, this.udpPacket.getLength())
                                .replaceAll("\r\n", "").split("BULLET:");

                        for(int i  = 1; i < bullets.length; i++){
                            String bullet[] = bullets[i].split(":");
                            String player = bullet[0];
                            int bx = Integer.parseInt(bullet[1]);
                            int by = Integer.parseInt(bullet[2]);
                            int id = Integer.parseInt(bullet[3]);

                            if(ServerListener.bdpList.isEmpty() && !player.equals(DataManager.userName)) {
                                for(PlayerDataPackage pdps : this.pdpList){
                                    if(player.equals(pdps.getUsr())) {
                                        ServerListener.bdpList.add(new BulletDataPackage(pdps.getCharacterType(), player, id, new Vector2(bx, by)));
                                    }
                                }
                            }else{
                                boolean isInList = false;
                                List<BulletDataPackage> toDestroy = new ArrayList<>(ServerListener.bdpList);
                                for(BulletDataPackage bdp : ServerListener.bdpList){
                                    if(bdp.getId() == id && bdp.getPlayer().equals(player) && !player.equals(DataManager.userName)) {
                                        bdp.update(new Vector2(bx, by));
                                        toDestroy.remove(bdp);
                                        isInList = true;
                                    }
                                }

                                if(!isInList && !player.equals(DataManager.userName)){
                                    for(PlayerDataPackage pdps : this.pdpList){
                                        if(player.equals(pdps.getUsr())) {
                                            ServerListener.bdpList.add(new BulletDataPackage(pdps.getCharacterType(), player, id, new Vector2(bx, by)));
                                        }
                                    }
                                }

                                ServerListener.bdpList.removeAll(toDestroy);
                            }
                        }
                    }
                }

            }catch (IOException e){
                e.printStackTrace();
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

    private void updateLists(){
        List<String> toSend = new ArrayList<>();
        toSend.add("FRIEND LIST");
        SuperSmashShoot.serverSpeaker.setToSend(toSend);
    }
}
