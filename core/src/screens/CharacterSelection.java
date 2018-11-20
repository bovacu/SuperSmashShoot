package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SuperSmashShoot;
import general.Converter;
import general.DataManager;
import general.IDs;
import ui.Label;
import ui.SpriteButton;
import ui.SpriteTextButton;

import java.util.ArrayList;
import java.util.List;

public class CharacterSelection extends InputAdapter implements Screen {

    public static IDs.CharacterType chosenCharacter = IDs.CharacterType.SOLDIER;
    public static int skin = 1;

    private SuperSmashShoot game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private SpriteButton sb_knight, sb_pirate, sb_clown, sb_soldier, sb_custom1, sb_custom2, sb_custom3, sb_ok;
    private BitmapFont timerFont;
    private GlyphLayout fontInfo;
    private Label lb_chooseFighter, lb_costumes;

    private Sprite background;

    private Animation<TextureRegion> selectedCharacter;
    private float timeBetweenFrames;

    private boolean blockSelection;

    public CharacterSelection(SuperSmashShoot game){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);
        this.viewport = new FitViewport(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT, this.camera);
        this.timeBetweenFrames = 0;
        CharacterSelection.skin = 1;
        this.blockSelection = false;

        this.createBlock1();
        this.createBlock2();
        this.createBlock3();

        Gdx.input.setInputProcessor(this);
        SuperSmashShoot.serverListener.resetLoadCharacterSelectorF();
    }

    private void createBlock1(){
        this.timerFont = new BitmapFont(Gdx.files.internal(DataManager.font));
        this.timerFont.setColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));
        this.fontInfo = new GlyphLayout();
        this.fontInfo.setText(this.timerFont, "20");

        this.background = Converter.idToSprite(IDs.BACKGROUND1);
        this.background.setSize(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);

        this.lb_chooseFighter = new Label("CHOOSE YOUR FIGHTER", 2f);
        this.lb_chooseFighter.setPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH / 2f - this.lb_chooseFighter.getWidth() / 2f,
                SuperSmashShoot.SCREEN_HEIGHT - this.lb_chooseFighter.getHeight() / 2f));
    }

    private void createBlock2(){
        this.sb_knight = new SpriteButton(IDs.CC_KNIGHT_PICTURE, IDs.CC_KNIGHT_PICTURE_DOWN) {
            @Override
            public void action() {
                if(!blockSelection)
                    createAnimation(IDs.CC_KNIGHT_1_WALKING);
            }
        };

        this.sb_pirate = new SpriteButton(IDs.CC_PIRATE_PICTURE, IDs.CC_PIRATE_PICTURE_DOWN) {
            @Override
            public void action() {
                if(!blockSelection)
                    createAnimation(IDs.CC_PIRATE_1_WALKING);
            }
        };

        this.sb_clown = new SpriteButton(IDs.CC_CLOWN_PICTURE, IDs.CC_CLOWN_PICTURE_DOWN) {
            @Override
            public void action() {
               if(!blockSelection)
                   createAnimation(IDs.CC_CLOWN_1_WALKING);
            }
        };

        this.sb_soldier = new SpriteButton(IDs.CC_SOLDIER_PICTURE, IDs.CC_SOLDIER_PICTURE_DOWN) {
            @Override
            public void action() {
                if(!blockSelection)
                    createAnimation(IDs.CC_SOLDIER_1_WALKING);
            }
        };

        this.sb_knight.setButtonSize(128, 128);
        this.sb_pirate.setButtonSize(128, 128);
        this.sb_clown.setButtonSize(128, 128);
        this.sb_soldier.setButtonSize(128, 128);

        this.sb_knight.setCenterPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH * 0.35f, SuperSmashShoot.SCREEN_HEIGHT / 2f));
        this.sb_pirate.setCenterPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH * 0.45f, SuperSmashShoot.SCREEN_HEIGHT / 2f));
        this.sb_clown.setCenterPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH * 0.55f, SuperSmashShoot.SCREEN_HEIGHT / 2f));
        this.sb_soldier.setCenterPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH * 0.65f, SuperSmashShoot.SCREEN_HEIGHT / 2f));
    }

    private void createBlock3(){

        final float offset = 15;

        this.sb_custom1 = new SpriteTextButton(IDs.GRAY_BUTTON_UP, IDs.GRAY_BUTTON_DOWN, "1", 64, 64, 1f) {
            @Override
            public void action() {
                if(!blockSelection){
                    if(chosenCharacter == IDs.CharacterType.KNIGHT)
                        createAnimation(IDs.CC_KNIGHT_1_WALKING);
                    else if(chosenCharacter == IDs.CharacterType.PIRATE)
                        createAnimation(IDs.CC_PIRATE_1_WALKING);
                    else if(chosenCharacter == IDs.CharacterType.CLOWN)
                        createAnimation(IDs.CC_CLOWN_1_WALKING);
                    else if(chosenCharacter == IDs.CharacterType.SOLDIER)
                        createAnimation(IDs.CC_SOLDIER_1_WALKING);

                    skin = 1;
                }
            }
        };

        this.sb_custom2 = new SpriteTextButton(IDs.GRAY_BUTTON_UP, IDs.GRAY_BUTTON_DOWN, "2", 64, 64, 1f) {
            @Override
            public void action() {
                if(!blockSelection){
                    if(chosenCharacter == IDs.CharacterType.KNIGHT)
                        createAnimation(IDs.CC_KNIGHT_2_WALKING);
                    else if(chosenCharacter == IDs.CharacterType.PIRATE)
                        createAnimation(IDs.CC_PIRATE_2_WALKING);
                    else if(chosenCharacter == IDs.CharacterType.CLOWN)
                        createAnimation(IDs.CC_CLOWN_2_WALKING);
                    else if(chosenCharacter == IDs.CharacterType.SOLDIER)
                        createAnimation(IDs.CC_SOLDIER_2_WALKING);

                    skin = 2;
                }
            }
        };

        this.sb_custom3 = new SpriteTextButton(IDs.GRAY_BUTTON_UP, IDs.GRAY_BUTTON_DOWN, "3", 64, 64, 1f) {
            @Override
            public void action() {
                if(!blockSelection){
                    if(chosenCharacter == IDs.CharacterType.KNIGHT)
                        createAnimation(IDs.CC_KNIGHT_3_WALKING);
                    else if(chosenCharacter == IDs.CharacterType.PIRATE)
                        createAnimation(IDs.CC_PIRATE_3_WALKING);
                    else if(chosenCharacter == IDs.CharacterType.CLOWN)
                        createAnimation(IDs.CC_CLOWN_3_WALKING);
                    else if(chosenCharacter == IDs.CharacterType.SOLDIER)
                        createAnimation(IDs.CC_SOLDIER_3_WALKING);

                    skin = 3;
                }
            }
        };

        this.sb_custom1.setPosition(new Vector2(offset, offset));
        this.sb_custom2.setPosition(new Vector2(this.sb_custom1.getPosition().x + this.sb_custom1.getSizes().width + offset,
                this.sb_custom1.getPosition().y));
        this.sb_custom3.setPosition(new Vector2(this.sb_custom2.getPosition().x + this.sb_custom2.getSizes().width + offset,
                this.sb_custom2.getPosition().y));

        this.lb_costumes = new Label("Customs", new Vector2(this.sb_custom1.getPosition().x,
                this.sb_custom1.getPosition().y + this.sb_custom1.getSizes().height * 2 + offset), 1f);

        this.sb_ok = new SpriteTextButton(IDs.GRAY_BUTTON_UP, IDs.GRAY_BUTTON_DOWN, "FIGHT", 172, 64, 1f) {
            @Override
            public void action() {
                if(!blockSelection){
                    if(chosenCharacter != null){
                        blockSelection = true;
                        List<String> toSend = new ArrayList<>();
                        toSend.add("LOAD MAP SELECTOR");
                        SuperSmashShoot.serverSpeaker.setToSend(toSend);
                    }else
                        SuperSmashShoot.ms_message.update("You have to select a character first");
                }
            }
        };

        this.sb_ok.setPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH - offset - this.sb_ok.getSizes().width, offset));
    }

    private void createAnimation(int id){
        final int frames = 4;
        final float time = 0.15f;
        Texture sheet = Converter.idToSprite(id).getTexture();
        TextureRegion [][] tmp = TextureRegion.split(sheet, sheet.getWidth()/frames, sheet.getHeight());
        TextureRegion animationFrames[] = new TextureRegion[frames];
        for (int i = 0; i < animationFrames.length; i++) animationFrames[i] = tmp[0][i];
        selectedCharacter = new Animation<>(time, animationFrames);
        selectedCharacter.setPlayMode(Animation.PlayMode.LOOP);
        timeBetweenFrames = 0;

        switch (id){
            case IDs.CC_KNIGHT_1_WALKING    :     chosenCharacter = IDs.CharacterType.KNIGHT;     break;
            case IDs.CC_PIRATE_1_WALKING    :     chosenCharacter = IDs.CharacterType.PIRATE;     break;
            case IDs.CC_CLOWN_1_WALKING     :     chosenCharacter = IDs.CharacterType.CLOWN;      break;
            case IDs.CC_SOLDIER_1_WALKING   :     chosenCharacter = IDs.CharacterType.SOLDIER;    break;
        }
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
        this.lb_chooseFighter.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_knight.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_pirate.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_clown.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_soldier.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);


        if(this.selectedCharacter != null) {
            this.timeBetweenFrames += delta;
            this.game.batch.draw(this.selectedCharacter.getKeyFrame(this.timeBetweenFrames),
                    SuperSmashShoot.SCREEN_WIDTH / 2f - 64,
                    this.sb_pirate.getPosition().y + this.sb_pirate.getSizes().height * 1.5f, 128, 128);
            if(this.timeBetweenFrames > this.selectedCharacter.getAnimationDuration())
                this.timeBetweenFrames = 0;
        }

        this.lb_costumes.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_custom1.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_custom2.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_custom3.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_ok.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        SuperSmashShoot.ms_message.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        this.game.batch.end();

        if(SuperSmashShoot.serverListener.getLoadMapSelectorF()){
            this.dispose();
            this.game.setScreen(new MapSelector(this.game));
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
        if(this.selectedCharacter != null)
            for(TextureRegion t : this.selectedCharacter.getKeyFrames())
                t.getTexture().dispose();


        this.sb_clown.dispose();
        this.sb_custom1.dispose();
        this.sb_custom2.dispose();
        this.sb_custom3.dispose();
        this.sb_knight.dispose();
        this.sb_pirate.dispose();
        this.sb_soldier.dispose();
        this.sb_ok.dispose();
        this.background.getTexture().dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        Vector3 mousePos = this.camera.unproject(new Vector3(screenX, screenY, 0));
        this.sb_pirate.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_soldier.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_clown.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_knight.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_ok.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_custom1.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_custom2.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_custom3.execute((int)mousePos.x, (int)mousePos.y);
        return false;
    }
}
