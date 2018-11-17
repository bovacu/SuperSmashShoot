package screens;

import characters.ServerSpeaker;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SuperSmashShoot;
import general.Converter;
import general.DataManager;
import general.IDs;
import maps.Map;
import ui.*;

public class MainMenu extends InputAdapter implements Screen {

    private SuperSmashShoot game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private SpriteButton sb_local, sb_multiplayer, sb_chat, sb_settings, sb_stats, sb_quit, sb_friends, sb_connect;
    private PartyList pl_friendsInParty;

    private Loggin loggin;
    private Chat chat;

    private Sprite background;

    public MainMenu(SuperSmashShoot game){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);
        this.viewport = new FitViewport(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT, this.camera);

        this.background = Converter.idToSprite(IDs.BACKGROUND1);
        this.background.setSize(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);

        this.constructButtons();
        this.setButtonsPositions();
        this.constructPartyFriends();

        this.chat = new Chat(this.sb_chat);
        this.loggin = new Loggin(IDs.PAGED_LIST_BACK, new Vector2(sb_chat.getPosition().x, sb_chat.getPosition().y +
                sb_chat.getSizes().height * 1.5f));

        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(this);
        im.addProcessor(this.chat);
        im.addProcessor(this.loggin);
        Gdx.input.setInputProcessor(im);
    }

    private void constructButtons(){
        this.sb_local = new SpriteTextButton(IDs.GRAY_BUTTON_UP, IDs.GRAY_BUTTON_DOWN,"LOCAL", 512, 128, 1f) {
            @Override
            public void action() {
                if(DataManager.partyID == -1){
                    this.dispose();
                    game.setScreen(new Map(game, "port.txt"));
                }else if(DataManager.partyID == Converter.userNameToPartyId())
                    SuperSmashShoot.ms_message.update("You cannot play local while in party");
                else
                    SuperSmashShoot.ms_message.update("You are not the host of the party");
            }
        };

        ((SpriteTextButton)this.sb_local).setTextColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));

        this.sb_multiplayer = new SpriteTextButton(IDs.GRAY_BUTTON_UP, IDs.GRAY_BUTTON_DOWN, "MULTIPLAYER", 512, 128, 1f) {
            @Override
            public void action() {
                if(DataManager.connected && (DataManager.partyID == -1 || DataManager.partyID == Converter.userNameToPartyId())){
                    this.dispose();
                    game.setScreen(new MultiplayerOptionMenu(game));
                }else if(!DataManager.connected)
                    SuperSmashShoot.ms_message.update("You have to connect first!");
                else
                    SuperSmashShoot.ms_message.update("You are not the host of the party");
            }
        };

        ((SpriteTextButton)this.sb_multiplayer).setTextColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));

        this.sb_chat = new SpriteButton(IDs.CHAT, IDs.CHAT_DOWN) {
            @Override
            public void action() {
                if(!chat.isVisible() && DataManager.connected){
                    loggin.setClosed(true);
                    chat.setVisible(true);
                }else
                    chat.setVisible(false);

                if(!DataManager.connected)
                    SuperSmashShoot.ms_message.update("You have to connect first");
            }
        };

        this.sb_settings = new SpriteButton(IDs.SETTINGS, IDs.SETTINGS_DOWN) {
            @Override
            public void action() {
                this.dispose();
                game.setScreen(new SettingsMenu(game));
            }
        };

        this.sb_stats = new SpriteButton(IDs.STATS, IDs.STATS_DOWN) {
            @Override
            public void action() {
                if(DataManager.connected){
                    this.dispose();
                    game.setScreen(new StatsMenu(game));
                }else
                    SuperSmashShoot.ms_message.update("You have to connect first");
            }
        };

        this.sb_quit = new SpriteButton(IDs.QUIT, IDs.QUIT_DOWN) {
            @Override
            public void action() {
                this.dispose();
                Gdx.app.exit();
            }
        };

        this.sb_friends = new SpriteButton(IDs.FRIENDS, IDs.FRIENDS_DOWN) {
            @Override
            public void action() {
                if(DataManager.connected){
                    this.dispose();
                    game.setScreen(new FriendsMenu(game));
                }else
                    SuperSmashShoot.ms_message.update("You have to connect first");
            }
        };

        this.sb_connect = new SpriteButton(IDs.CONNECT, IDs.CONNECT_DOWN) {
            @Override
            public void action() {
                if(!DataManager.connected && loggin.isClosed()){
                    loggin.updateColors();
                    chat.updateColors();
                    chat.setVisible(false);
                    loggin.setClosed(false);
                }else
                    SuperSmashShoot.ms_message.update("You are already connected");
            }
        };
    }

    private void setButtonsPositions(){
        this.sb_multiplayer.setCenterPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH / 2f,
                SuperSmashShoot.SCREEN_HEIGHT / 2f + this.sb_multiplayer.getSizes().height));

        this.sb_local.setCenterPosition(new Vector2(this.sb_multiplayer.getCenterPosition().x,
                this.sb_multiplayer.getCenterPosition().y - this.sb_local.getSizes().height * 2));

        this.sb_quit.setCenterPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH - this.sb_quit.getSizes().width,
                this.sb_quit.getSizes().height));

        this.sb_settings.setCenterPosition(new Vector2(this.sb_quit.getPosition().x - this.sb_settings.getSizes().width,
                this.sb_quit.getCenterPosition().y));

        this.sb_chat.setCenterPosition(new Vector2(this.sb_chat.getSizes().width, this.sb_quit.getCenterPosition().y));

        this.sb_connect.setCenterPosition(new Vector2(this.sb_chat.getPosition().x + this.sb_connect.getWidth() * 2,
                this.sb_chat.getCenterPosition().y));

        this.sb_stats.setCenterPosition(new Vector2(this.sb_quit.getCenterPosition().x,
                SuperSmashShoot.SCREEN_HEIGHT - this.sb_stats.getSizes().height));

        this.sb_friends.setCenterPosition(new Vector2(this.sb_settings.getCenterPosition().x, this.sb_stats.getCenterPosition().y));
    }

    private void constructPartyFriends(){
        this.pl_friendsInParty = new PartyList(512, 256,
                new Vector2(this.sb_chat.getPosition().x, SuperSmashShoot.SCREEN_HEIGHT - 512 / 1.75f),
                IDs.PAGED_LIST_BACK);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        this.game.batch.setProjectionMatrix(this.camera.combined);
        this.game.debugger.setProjectionMatrix(this.camera.combined);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector3 mousePos = this.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        this.game.batch.begin();
        this.background.draw(this.game.batch);
        this.sb_multiplayer.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_local.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_quit.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_settings.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_chat.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_stats.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_friends.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_connect.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        SuperSmashShoot.ms_message.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.pl_friendsInParty.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        SuperSmashShoot.partyList.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.chat.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.loggin.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.game.batch.end();

        if(SuperSmashShoot.serverListener.getLoadCharacterSelectorF()) {
            this.dispose();
            this.game.setScreen(new CharacterSelection(this.game));
        }
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
        this.background.getTexture().dispose();
        this.sb_local.dispose();
        this.sb_multiplayer.dispose();
        this.sb_quit.dispose();
        this.sb_settings.dispose();
        this.sb_chat.dispose();
        this.sb_stats.dispose();
        this.sb_friends.dispose();
        this.sb_connect.dispose();
        this.chat.dispose();
        this.loggin.dispose();
        this.pl_friendsInParty.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        Vector3 mousePos = this.camera.unproject(new Vector3(screenX, screenY, 0));
        this.sb_local.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_multiplayer.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_quit.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_settings.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_chat.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_stats.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_friends.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_connect.execute((int)mousePos.x, (int)mousePos.y);
        if(this.chat.isVisible())
            this.chat.execute((int)mousePos.x, (int)mousePos.y);
        if(!this.loggin.isClosed())
            this.loggin.execute((int)mousePos.x, (int)mousePos.y);
        if(SuperSmashShoot.partyList.getList().size() > 0)
            SuperSmashShoot.partyList.execute((int)mousePos.x, (int)mousePos.y);
        return false;
    }
}
