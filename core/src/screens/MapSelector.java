package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
import ui.Label;
import ui.SpriteButton;
import ui.SpriteTextButton;

import java.util.ArrayList;
import java.util.List;

public class MapSelector extends InputAdapter implements Screen {

    private SuperSmashShoot game;
    private OrthographicCamera camera;
    private Viewport viewport;

    private SpriteButton sb_portMap, sb_skyMap, sb_fight;
    private BitmapFont timerFont;
    private GlyphLayout fontInfo;
    private Label lb_chooseMap;

    private Sprite background;

    private String selectedMap;

    public MapSelector(SuperSmashShoot game){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);
        this.viewport = new FitViewport(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT, this.camera);
        SuperSmashShoot.serverListener.resetLoadMapSelectorF();

        this.createBlock1();
        this.createBlock2();

        Gdx.input.setInputProcessor(this);
    }

    private void createBlock1(){
        this.timerFont = new BitmapFont(Gdx.files.internal(DataManager.font));
        this.timerFont.setColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));
        this.fontInfo = new GlyphLayout();
        this.fontInfo.setText(this.timerFont, "20");

        this.background = Converter.idToSprite(IDs.BACKGROUND1);
        this.background.setSize(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);

        this.lb_chooseMap = new Label("CHOOSE THE MAP", 2f);
        this.lb_chooseMap.setPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH / 2f - this.lb_chooseMap.getWidth() / 2f,
                SuperSmashShoot.SCREEN_HEIGHT - this.lb_chooseMap.getHeight() / 2f));
    }

    private void createBlock2(){

        final float offset = 15;

        this.sb_portMap = new SpriteButton(IDs.CM_PORT, IDs.CM_PORT_DOWN) {
            @Override
            public void action() {
                if(DataManager.partyID == Converter.userNameToPartyId()){
                    selectedMap = "port.txt";
                }else
                    SuperSmashShoot.ms_message.update("Only the host can select a map");
            }
        };

        this.sb_skyMap = new SpriteButton(IDs.CM_SKY, IDs.CM_SKY_DOWN) {
            @Override
            public void action() {
                if(DataManager.partyID == Converter.userNameToPartyId()){
                    selectedMap = "sky.txt";
                }else
                    SuperSmashShoot.ms_message.update("Only the host can select a map");
            }
        };

        this.sb_fight = new SpriteTextButton(IDs.GRAY_BUTTON_UP, IDs.GRAY_BUTTON_DOWN,"FIGHT", 172, 64, 1f) {
            @Override
            public void action() {
                if(DataManager.partyID == Converter.userNameToPartyId() && selectedMap != null){
                    List<String> toSend = new ArrayList<>();
                    toSend.add("START FIGHT");
                    toSend.add(selectedMap);
                    SuperSmashShoot.serverSpeaker.setToSend(toSend);
                }else
                    SuperSmashShoot.ms_message.update("The host starts the match");
            }
        };

        this.sb_portMap.setButtonSize(400, 288);
        this.sb_skyMap.setButtonSize(400, 288);

        this.sb_portMap.setCenterPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH / 2f - this.sb_portMap.getSizes().width,
                SuperSmashShoot.SCREEN_HEIGHT / 2f));
        this.sb_skyMap.setCenterPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH / 2f + this.sb_skyMap.getSizes().width,
                SuperSmashShoot.SCREEN_HEIGHT / 2f));

        this.sb_fight.setPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH - offset - this.sb_fight.getSizes().width, offset));
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
        this.timerFont.draw(this.game.batch, "20", 15, SuperSmashShoot.SCREEN_HEIGHT - this.fontInfo.height);
        this.lb_chooseMap.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_portMap.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_skyMap.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_fight.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        SuperSmashShoot.ms_message.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        this.game.batch.end();

        if(SuperSmashShoot.serverListener.getLoadMapF()){
            this.dispose();
            this.game.setScreen(new Map(this.game, SuperSmashShoot.serverListener.getMapF()));
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
        this.sb_fight.dispose();
        this.sb_skyMap.dispose();
        this.sb_portMap.dispose();
        this.background.getTexture().dispose();
        this.lb_chooseMap.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        Vector3 mousePos = this.camera.unproject(new Vector3(screenX, screenY, 0));
        this.sb_skyMap.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_portMap.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_fight.execute((int)mousePos.x, (int)mousePos.y);
        return false;
    }
}
