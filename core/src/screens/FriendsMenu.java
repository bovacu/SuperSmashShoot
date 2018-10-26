package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SuperSmashShoot;
import general.DataManager;
import general.IDs;
import ui.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FriendsMenu extends InputAdapter implements Screen {

    private SuperSmashShoot game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private TextBox tb_addFriend, tb_removeFriend;
    private SpriteButton sb_addFriend, sb_removeFriend;
    private Label lb_addFriend, lb_removeFriend, lb_friendList, lb_friendRequests, lb_partyRequests;
    private PagedListWithButton pl_friendsList, pl_friendRequests, pl_partyRequests;

    private SpriteButton sb_back;

    private final float UPDATE_LIST_TIME = 2f;
    private float timePassed;

    public FriendsMenu(SuperSmashShoot game){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);
        this.viewport = new FitViewport(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT, this.camera);

        this.timePassed = 0;

        this.createBlock1();
        this.createBlock2();
        this.createBlock3();
        this.createBlock4();
        this.createBlock5();
        this.createBackButton();

        Gdx.input.setInputProcessor(this);
    }

    private void createBackButton(){
        this.sb_back = new SpriteButton(IDs.PREVIOUS, IDs.PREVIOUS_DOWN) {
            @Override
            public void action() {
                this.dispose();
                game.setScreen(new MainMenu(game));
            }
        };
        this.sb_back.setPosition(new Vector2(this.lb_addFriend.getPosition().x, this.lb_addFriend.getPosition().x));
    }

    private void createBlock1(){
        this.lb_addFriend = new Label("Add friend", new Vector2(new Vector2(15,
                SuperSmashShoot.SCREEN_HEIGHT)));

        this.tb_addFriend = new TextBox(IDs.TEXT_BOX, 512, 96, new Vector2(this.lb_addFriend.getPosition().x,
                this.lb_addFriend.getPosition().y - this.lb_addFriend.getHeight() * 2 - 96), 12);

        this.sb_addFriend = new SpriteButton(IDs.PLUS_BUTTON, IDs.PLUS_BUTTON_DOWN) {
            @Override
            public void action() {
                if(DataManager.connected) {

                } else {
                    SuperSmashShoot.ms_message.update("You need to connect first!");
                    System.out.println("entra aqui");
                }
            }
        };
        this.sb_addFriend.setPosition(new Vector2(this.tb_addFriend.getPosition().x + this.tb_addFriend.getWidth() * 1.1f,
                this.tb_addFriend.getPosition().y + this.sb_addFriend.getSizes().width / 4f));
    }

    private void createBlock2(){
        this.lb_removeFriend = new Label("Remove Friend", new Vector2(new Vector2(this.lb_addFriend.getPosition().x,
                this.tb_addFriend.getPosition().y - this.lb_addFriend.getHeight())));

        this.tb_removeFriend = new TextBox(IDs.TEXT_BOX, 512, 96, new Vector2(this.lb_addFriend.getPosition().x,
                this.lb_removeFriend.getPosition().y - this.lb_removeFriend.getHeight() * 2 - 96), 12);

        this.sb_removeFriend = new SpriteButton(IDs.MINUS_BUTTON, IDs.MINUS_BUTTON_DOWN) {
            @Override
            public void action() {

            }
        };
        this.sb_removeFriend.setPosition(new Vector2(this.tb_removeFriend.getPosition().x + this.tb_removeFriend.getWidth() * 1.1f,
                this.tb_removeFriend.getPosition().y + this.sb_removeFriend.getSizes().width / 4f));
    }

    private void createBlock3(){
        this.lb_friendList = new Label("Friends list", new Vector2(new Vector2(this.lb_addFriend.getPosition().x,
                this.tb_removeFriend.getPosition().y - this.lb_addFriend.getHeight())));

        this.pl_friendsList = new PagedListWithButton(512, 512,
                new Vector2(this.lb_addFriend.getPosition().x, this.lb_friendList.getPosition().y
                        - this.lb_addFriend.getHeight() * 2 - 512),
                IDs.PAGED_LIST_BACK, IDs.NEXT, IDs.NEXT_DOWN, IDs.PREVIOUS, IDs.PREVIOUS_DOWN) {
            @Override
            public void buttonAction() {
                List<String> toSend = new ArrayList<>();
                toSend.add("SEND PARTY REQUEST");
                System.out.println(pl_friendsList.getSelectedItem());
                toSend.add(pl_friendsList.getSelectedItem());

                SuperSmashShoot.serverSpeaker.setToSend(toSend);
            }
        };

        if(DataManager.connected) {
            List<String> friends = SuperSmashShoot.serverSpeaker.getFriendsList();
            for(String friend : friends)
                pl_friendsList.addItem(friend);
        }
    }

    private void createBlock4(){
        this.lb_friendRequests = new Label("Friend requests", new Vector2(new Vector2(
                this.pl_friendsList.getPosition().x + this.pl_friendsList.getWidth() * 1.1f,
                this.lb_friendList.getPosition().y)));

        this.pl_friendRequests = new PagedListWithButton(512, 512,
                new Vector2(this.lb_friendRequests.getPosition().x, this.pl_friendsList.getPosition().y),
                IDs.PAGED_LIST_BACK, IDs.NEXT, IDs.NEXT_DOWN, IDs.PREVIOUS, IDs.PREVIOUS_DOWN) {
            @Override
            public void buttonAction() {

            }
        };

        if(DataManager.connected) {
            List<String> friendsRequest = SuperSmashShoot.serverSpeaker.getRequestsList();
            for(String request : friendsRequest)
                pl_friendRequests.addItem(request);
        }
    }

    private void createBlock5(){
        this.lb_partyRequests = new Label("Party requests", new Vector2(new Vector2(
                this.pl_friendRequests.getPosition().x + this.pl_friendsList.getWidth() * 1.1f,
                this.lb_friendList.getPosition().y)));

        this.pl_partyRequests = new PagedListWithButton(512, 512,
                new Vector2(this.lb_partyRequests.getPosition().x, this.pl_friendsList.getPosition().y),
                IDs.PAGED_LIST_BACK, IDs.NEXT, IDs.NEXT_DOWN, IDs.PREVIOUS, IDs.PREVIOUS_DOWN) {
            @Override
            public void buttonAction() {
                //sendPartyRequest();
            }
        };
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if(this.timePassed > this.UPDATE_LIST_TIME){
            List<String> friends = SuperSmashShoot.serverSpeaker.getFriendsList();
            for(String s : this.pl_friendsList.getItems()) {
                if (!friends.contains(s))
                    this.pl_friendsList.addItem(s);
            }

            List<String> request = SuperSmashShoot.serverSpeaker.getRequestsList();
            for(String s : this.pl_friendRequests.getItems()) {
                if (!request.contains(s))
                    this.pl_friendRequests.addItem(s);
            }

            List<String> party = SuperSmashShoot.serverSpeaker.getPartyList();
            for(String s : this.pl_partyRequests.getItems()) {
                if (!party.contains(s))
                    this.pl_partyRequests.addItem(s);
            }

            this.timePassed = 0;
        }else{
            this.timePassed += delta;
        }

        this.game.batch.setProjectionMatrix(this.camera.combined);
        this.game.debugger.setProjectionMatrix(this.camera.combined);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector3 mousePos = this.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        this.game.batch.begin();
        this.lb_addFriend.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.tb_addFriend.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_addFriend.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        this.lb_removeFriend.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.tb_removeFriend.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_removeFriend.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        this.lb_friendList.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.pl_friendsList.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        this.lb_friendRequests.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.pl_friendRequests.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        this.lb_partyRequests.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.pl_partyRequests.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        this.sb_back.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        SuperSmashShoot.ms_message.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        this.game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.pl_friendsList.dispose();
        this.tb_addFriend.dispose();
        this.tb_removeFriend.dispose();
        this.sb_addFriend.dispose();
        this.sb_removeFriend.dispose();
        this.lb_addFriend.dispose();
        this.lb_removeFriend.dispose();
        this.lb_friendList.dispose();
        this.lb_friendRequests.dispose();
        this.pl_friendRequests.dispose();
        this.lb_partyRequests.dispose();
        this.pl_partyRequests.dispose();
        this.sb_back.dispose();
    }

    /**
     * Cuando se detecta un click de mouse cada uno de los elementos de Ui comprueba si el click se ha hecho sobre si mismo
     * y se ejecuta su accion predefinida si corresponde.
    **/
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        Vector3 mousePos = this.camera.unproject(new Vector3(screenX, screenY, 0));
        this.tb_addFriend.execute((int)mousePos.x, (int)mousePos.y);
        this.tb_removeFriend.execute((int)mousePos.x, (int)mousePos.y);
        this.pl_friendsList.execute((int)mousePos.x, (int)mousePos.y);
        this.pl_friendRequests.execute((int)mousePos.x, (int)mousePos.y);
        this.pl_partyRequests.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_addFriend.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_removeFriend.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_back.execute((int)mousePos.x, (int)mousePos.y);
        return false;
    }

    /**
     * Cada vez que se pulse una tecla comprueba si es alguna de la lista del if, ya que som caracteres que en el metodo
     * keyTyped generan espacios en blanco y quiero evitar eso.
     *
     * La unica tecla que detecto como correcta es el BACKSPACE para poder borrar.
     *
     * En el caso de que se pulse una tecla que este en la lista, esta bloquea a los TextBox para que no reciban letras
     * y aumenten su texto, si se pulsa BACKSPACE va quitando las letras una a una si es que hay letras.
    **/
    @Override
    public boolean keyDown(int key){
        if(key == Input.Keys.DEL || key == Input.Keys.ENTER || key == Input.Keys.CONTROL_LEFT || key == Input.Keys.CONTROL_RIGHT
        || key == Input.Keys.SHIFT_LEFT || key == Input.Keys.SHIFT_RIGHT || key == Input.Keys.ESCAPE || key == Input.Keys.SPACE) {
            this.tb_addFriend.setCanGetLetter(false);
            this.tb_removeFriend.setCanGetLetter(false);

            if(key == (Input.Keys.DEL)) {
                this.tb_addFriend.removeLetter();
                this.tb_removeFriend.removeLetter();
            }
        } else {
            this.tb_addFriend.setCanGetLetter(true);
            this.tb_removeFriend.setCanGetLetter(true);
        }
        return false;
    }

    /**
     * Si el TextBox no esta bloqueado por haber pulsado una tecla de la lista y a su vez esta seleccionado por el
     * usuario (eso se maneja internamente en su clase), se va metiendo texto en el TextBox hasta un limite.
    **/
    @Override
    public boolean keyTyped(char key){
        if(this.tb_addFriend.isCanGetLetter())
            this.tb_addFriend.addLetter(String.valueOf(key));

        if(this.tb_removeFriend.isCanGetLetter())
            this.tb_removeFriend.addLetter(String.valueOf(key));
        return false;
    }
}
