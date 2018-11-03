package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
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
import ui.Label;
import ui.SpriteButton;

import java.util.Arrays;

public class SettingsMenu extends InputAdapter implements Screen {
    private final int DISTANCE_BETWEEN_BUTTONS = SuperSmashShoot.SCREEN_WIDTH / 4;
    private final Color TEXT_COLORS[] = {Color.WHITE, Color.GREEN, Color.BLACK, Color.YELLOW, Color.GRAY};
    private final Color UI_COLORS[] = {Color.GRAY, Color.BROWN, Color.VIOLET};

    private SuperSmashShoot game;

    private SpriteButton sb_plusMusic, sb_minusMusic, sb_plusEffects, sb_minusEffects, sb_prevTextColor, sb_nextTextColor,
    sb_prevUi, sb_nextUi, sb_back, sb_apply;

    private Label lb_settings, lb_music, lb_effects, lb_textColor, lb_ui;
    private Label lb_musicValue, lb_effectsValue, lb_textColorValue, lb_uiValue;

    private OrthographicCamera camera;
    private Viewport viewport;

    private int colorIndex;
    private int uiIndex;

    private Sprite background;

    public SettingsMenu(SuperSmashShoot game){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);
        this.viewport = new FitViewport(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT, this.camera);

        DataManager.loadData();
        this.colorIndex = Arrays.asList(this.TEXT_COLORS).indexOf(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));
        this.uiIndex = Arrays.asList(this.UI_COLORS).indexOf(Color.valueOf(DataManager.colorToHex(DataManager.uiColor)));

        this.lb_settings = new Label("SETTINGS", new Vector2(0,0), 1.0f);
        this.lb_settings.setColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));
        this.lb_settings.setPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH / 2f - this.lb_settings.getWidth() / 2f,
                SuperSmashShoot.SCREEN_HEIGHT - this.lb_settings.getHeight()));

        this.createBlock1();
        this.createBlock2();
        this.createBlock3();
        this.createBlock4();
        this.createBlock5();

        this.background = Converter.idToSprite(IDs.BACKGROUND1);
        this.background.setSize(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);

        Gdx.input.setInputProcessor(this);
    }

    private void createBlock1(){
        float offset = 15f;
        this.lb_music = new Label(String.valueOf("Music"), new Vector2(0, 0));
        this.lb_music.setColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));
        this.lb_music.setPosition(new Vector2(offset + SuperSmashShoot.SCREEN_WIDTH / 4f,
                this.lb_settings.getPosition().y - this.lb_music.getHeight() * 5));

        this.sb_minusMusic = new SpriteButton(IDs.PREVIOUS, IDs.PREVIOUS_DOWN) {
            @Override
            public void action() {
                if(DataManager.music - 10 >= 0) {
                    DataManager.music -= 10;
                    lb_musicValue.setText(String.valueOf(DataManager.music));
                }
            }
        };
        float distance = SuperSmashShoot.SCREEN_WIDTH / 3f - this.lb_music.getPosition().x + this.lb_music.getWidth();
        this.sb_minusMusic.setPosition(new Vector2(this.lb_music.getPosition().x + this.lb_music.getWidth() + distance,
                this.lb_music.getPosition().y - this.lb_music.getHeight() * 2));

        this.lb_musicValue = new Label(String.valueOf(DataManager.music), new Vector2(), 1f);
        this.lb_musicValue.setColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));

        this.sb_plusMusic = new SpriteButton(IDs.NEXT, IDs.NEXT_DOWN) {
            @Override
            public void action() {
                if(DataManager.music + 10 <= 100) {
                    DataManager.music += 10;
                    lb_musicValue.setText(String.valueOf(DataManager.music));
                }
            }
        };
        this.sb_plusMusic.setPosition(new Vector2(this.sb_minusMusic.getPosition().x + this.DISTANCE_BETWEEN_BUTTONS,
                this.sb_minusMusic.getPosition().y));

        float middle = (this.sb_plusMusic.getPosition().x - this.sb_minusMusic.getPosition().x + this.sb_minusMusic.getWidth()) / 2f;
        this.lb_musicValue.setPosition(new Vector2(this.sb_minusMusic.getPosition().x + this.sb_minusMusic.getWidth() +
                middle - this.lb_musicValue.getWidth(),
                this.sb_minusMusic.getPosition().y + this.lb_musicValue.getHeight() * 1.75f));
    }

    private void createBlock2(){
        float offset = 15f;
        this.lb_effects = new Label(String.valueOf("Effects"), new Vector2(0, 0));
        this.lb_effects.setColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));
        this.lb_effects.setPosition(new Vector2(offset + SuperSmashShoot.SCREEN_WIDTH / 4f,
                this.lb_music.getPosition().y - this.lb_music.getHeight() * 3));

        this.sb_minusEffects = new SpriteButton(IDs.PREVIOUS, IDs.PREVIOUS_DOWN) {
            @Override
            public void action() {
                if(DataManager.effects - 10 >= 0) {
                    DataManager.effects -= 10;
                    lb_effectsValue.setText(String.valueOf(DataManager.effects));
                }
            }
        };

        this.sb_minusEffects.setPosition(new Vector2(this.sb_minusMusic.getPosition().x,
                this.lb_effects.getPosition().y - this.lb_effects.getHeight() * 2));

        this.lb_effectsValue = new Label(String.valueOf(DataManager.effects), new Vector2(), 1f);
        this.lb_effectsValue.setColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));
        this.sb_plusEffects = new SpriteButton(IDs.NEXT, IDs.NEXT_DOWN) {
            @Override
            public void action() {
                if(DataManager.effects + 10 <= 100) {
                    DataManager.effects += 10;
                    lb_effectsValue.setText(String.valueOf(DataManager.effects));
                }
            }
        };
        this.sb_plusEffects.setPosition(new Vector2(this.sb_minusEffects.getPosition().x + this.DISTANCE_BETWEEN_BUTTONS,
                this.sb_minusEffects.getPosition().y));

        float middle = (this.sb_plusEffects.getPosition().x - this.sb_minusEffects.getPosition().x + this.sb_minusEffects.getWidth()) / 2f;
        this.lb_effectsValue.setPosition(new Vector2(this.sb_minusEffects.getPosition().x + this.sb_minusEffects.getWidth() +
                middle - this.lb_effectsValue.getWidth(),
                this.sb_minusEffects.getPosition().y + this.lb_effectsValue.getHeight() * 1.75f));
    }

    private void createBlock3(){
        float offset = 15f;
        this.lb_textColor = new Label(String.valueOf("Text Color"), new Vector2(0, 0));
        this.lb_textColor.setColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));
        this.lb_textColor.setPosition(new Vector2(offset + SuperSmashShoot.SCREEN_WIDTH / 4f,
                this.lb_effects.getPosition().y - this.lb_effects.getHeight() * 3));

        this.sb_prevTextColor = new SpriteButton(IDs.PREVIOUS, IDs.PREVIOUS_DOWN) {
            @Override
            public void action() {
                if(colorIndex - 1 >= 0) {
                    colorIndex--;
                    lb_textColorValue.setText(DataManager.hexToColor(TEXT_COLORS[colorIndex].toString()));
                    DataManager.textColor = DataManager.hexToColor(TEXT_COLORS[colorIndex].toString());
                }else{
                    colorIndex = TEXT_COLORS.length - 1;
                    lb_textColorValue.setText(DataManager.hexToColor(TEXT_COLORS[colorIndex].toString()));
                    DataManager.textColor = DataManager.hexToColor(TEXT_COLORS[colorIndex].toString());
                }
            }
        };

        this.sb_prevTextColor.setPosition(new Vector2(this.sb_minusEffects.getPosition().x,
                this.lb_textColor.getPosition().y - this.lb_textColor.getHeight() * 2));

        this.lb_textColorValue = new Label(DataManager.hexToColor(this.TEXT_COLORS[colorIndex].toString()), new Vector2(), 1f);
        this.lb_textColorValue.setColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));

        this.sb_nextTextColor = new SpriteButton(IDs.NEXT, IDs.NEXT_DOWN) {
            @Override
            public void action() {
                if(colorIndex + 1 < TEXT_COLORS.length) {
                    colorIndex++;
                    lb_textColorValue.setText(DataManager.hexToColor(TEXT_COLORS[colorIndex].toString()));
                    DataManager.textColor = DataManager.hexToColor(TEXT_COLORS[colorIndex].toString());
                }else{
                    colorIndex = 0;
                    lb_textColorValue.setText(DataManager.hexToColor(TEXT_COLORS[colorIndex].toString()));
                    DataManager.textColor = DataManager.hexToColor(TEXT_COLORS[colorIndex].toString());
                }
            }
        };
        this.sb_nextTextColor.setPosition(new Vector2(this.sb_prevTextColor.getPosition().x + this.DISTANCE_BETWEEN_BUTTONS,
                this.sb_prevTextColor.getPosition().y));

        float middle = (this.sb_nextTextColor.getPosition().x - this.sb_prevTextColor.getPosition().x + this.sb_prevTextColor.getWidth()) / 2f;
        this.lb_textColorValue.setPosition(new Vector2(this.sb_prevTextColor.getPosition().x + this.sb_prevTextColor.getWidth() +
                middle - this.lb_textColorValue.getWidth(),
                this.sb_prevTextColor.getPosition().y + this.lb_textColorValue.getHeight() * 1.75f));
    }

    private void createBlock4(){
        float offset = 15f;
        this.lb_ui = new Label(String.valueOf("UI Color"), new Vector2(0, 0));
        this.lb_ui.setColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));
        this.lb_ui.setPosition(new Vector2(offset + SuperSmashShoot.SCREEN_WIDTH / 4f,
                this.lb_textColor.getPosition().y - this.lb_textColor.getHeight() * 3));

        this.sb_prevUi = new SpriteButton(IDs.PREVIOUS, IDs.PREVIOUS_DOWN) {
            @Override
            public void action() {
                if(uiIndex - 1 >= 0) {
                    uiIndex--;
                    lb_uiValue.setText(DataManager.hexToColor(UI_COLORS[uiIndex].toString()));
                    DataManager.uiColor = DataManager.hexToColor(UI_COLORS[uiIndex].toString());
                }else{
                    uiIndex = UI_COLORS.length - 1;
                    lb_uiValue.setText(DataManager.hexToColor(UI_COLORS[uiIndex].toString()));
                    DataManager.uiColor = DataManager.hexToColor(UI_COLORS[uiIndex].toString());
                }
            }
        };

        this.sb_prevUi.setPosition(new Vector2(this.sb_prevTextColor.getPosition().x,
                this.lb_ui.getPosition().y - this.lb_ui.getHeight() * 2));

        this.lb_uiValue = new Label(DataManager.hexToColor(this.UI_COLORS[uiIndex].toString()), new Vector2(), 1f);
        this.lb_uiValue.setColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));

        this.sb_nextUi = new SpriteButton(IDs.NEXT, IDs.NEXT_DOWN) {
            @Override
            public void action() {
                if(uiIndex + 1 < UI_COLORS.length) {
                    uiIndex++;
                    lb_uiValue.setText(DataManager.hexToColor(UI_COLORS[uiIndex].toString()));
                    DataManager.uiColor = DataManager.hexToColor(UI_COLORS[uiIndex].toString());
                }else{
                    uiIndex = 0;
                    lb_uiValue.setText(DataManager.hexToColor(UI_COLORS[uiIndex].toString()));
                    DataManager.uiColor = DataManager.hexToColor(UI_COLORS[uiIndex].toString());
                }
            }
        };
        this.sb_nextUi.setPosition(new Vector2(this.sb_prevUi.getPosition().x + this.DISTANCE_BETWEEN_BUTTONS,
                this.sb_prevUi.getPosition().y));

        float middle = (this.sb_nextUi.getPosition().x - this.sb_prevUi.getPosition().x + this.sb_prevUi.getWidth()) / 2f;
        this.lb_uiValue.setPosition(new Vector2(this.sb_prevUi.getPosition().x + this.sb_prevUi.getWidth() +
                middle - this.lb_uiValue.getWidth(),
                this.sb_prevUi.getPosition().y + this.lb_uiValue.getHeight() * 1.75f));
    }

    private void createBlock5(){
        float offset = 15;
        this.sb_back = new SpriteButton(IDs.PREVIOUS, IDs.PREVIOUS_DOWN) {
            @Override
            public void action() {
                this.dispose();
                game.setScreen(new MainMenu(game));
            }
        };
        this.sb_back.setPosition(new Vector2(offset, offset));

        this.sb_apply = new SpriteButton(IDs.SAVE, IDs.SAVE_DOWN) {
            @Override
            public void action() {
                DataManager.writeData();
            }
        };
        this.sb_apply.setPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH - this.sb_apply.getWidth() - offset,
                this.sb_back.getPosition().y));
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
        this.lb_settings.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        this.lb_music.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_minusMusic.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.lb_musicValue.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_plusMusic.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        this.lb_effects.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_minusEffects.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.lb_effectsValue.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_plusEffects.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        this.lb_textColor.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_prevTextColor.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.lb_textColorValue.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_nextTextColor.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        this.lb_ui.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_prevUi.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.lb_uiValue.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_nextUi.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

        this.sb_back.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_apply.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);

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
        this.background.getTexture().dispose();
        this.sb_apply.dispose();
        this.sb_back.dispose();
        this.sb_minusEffects.dispose();
        this.sb_nextTextColor.dispose();
        this.sb_minusMusic.dispose();
        this.sb_nextUi.dispose();
        this.sb_plusEffects.dispose();
        this.sb_plusMusic.dispose();
        this.sb_prevTextColor.dispose();
        this.sb_prevUi.dispose();

        this.lb_effects.dispose();
        this.lb_effectsValue.dispose();
        this.lb_music.dispose();
        this.lb_musicValue.dispose();
        this.lb_ui.dispose();
        this.lb_uiValue.dispose();
        this.lb_textColor.dispose();
        this.lb_textColorValue.dispose();
        this.lb_settings.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        Vector3 mousePos = this.camera.unproject(new Vector3(screenX, screenY, 0));
        this.sb_minusMusic.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_plusMusic.execute((int)mousePos.x, (int)mousePos.y);

        this.sb_minusEffects.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_plusEffects.execute((int)mousePos.x, (int)mousePos.y);

        this.sb_prevTextColor.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_nextTextColor.execute((int)mousePos.x, (int)mousePos.y);

        this.sb_prevUi.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_nextUi.execute((int)mousePos.x, (int)mousePos.y);

        this.sb_back.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_apply.execute((int)mousePos.x, (int)mousePos.y);
        return false;
    }
}
