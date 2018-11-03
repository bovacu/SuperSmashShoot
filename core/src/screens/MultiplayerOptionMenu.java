package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SuperSmashShoot;
import general.Converter;
import general.IDs;
import ui.SpriteButton;
import ui.SpriteTextButton;

public class MultiplayerOptionMenu extends InputAdapter implements Screen {

    private SuperSmashShoot game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private SpriteButton sb_createMatch, sb_joinMatch, sb_back;

    private Sprite background;

    public MultiplayerOptionMenu(SuperSmashShoot game){
        this.game = game;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);
        this.viewport = new FitViewport(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT, this.camera);

        this.createButtons();
        this.setButtons();

        this.background = Converter.idToSprite(IDs.BACKGROUND1);
        this.background.setSize(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);

        Gdx.input.setInputProcessor(this);
    }

    private void createButtons(){
        this.sb_createMatch = new SpriteTextButton(IDs.GRAY_BUTTON_UP, IDs.GRAY_BUTTON_DOWN, "CREATE MATCH", 512, 128, 1f) {
            @Override
            public void action() {

            }
        };

        this.sb_joinMatch = new SpriteTextButton(IDs.GRAY_BUTTON_UP, IDs.GRAY_BUTTON_DOWN, "JOIN MATCH", 512, 128, 1f) {
            @Override
            public void action() {

            }
        };

        this.sb_back = new SpriteButton(IDs.PREVIOUS, IDs.PREVIOUS_DOWN) {
            @Override
            public void action() {
                this.dispose();
                game.setScreen(new MainMenu(game));
            }
        };
    }

    private void setButtons(){
        float offset = 15f;
        this.sb_createMatch.setCenterPosition(new Vector2(SuperSmashShoot.SCREEN_WIDTH / 2f,
                SuperSmashShoot.SCREEN_HEIGHT / 2f + this.sb_createMatch.getHeight()));

        this.sb_joinMatch.setCenterPosition(new Vector2(this.sb_createMatch.getCenterPosition().x,
                SuperSmashShoot.SCREEN_HEIGHT / 2f - this.sb_joinMatch.getHeight()));

        this.sb_back.setPosition(new Vector2(offset, offset));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        this.game.batch.setProjectionMatrix(this.camera.combined);
        this.game.debugger.setProjectionMatrix(this.camera.combined);

        Vector3 mousePos = this.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.game.batch.begin();
        this.background.draw(this.game.batch);
        this.sb_createMatch.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_joinMatch.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.sb_back.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
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
        this.sb_createMatch.dispose();
        this.sb_joinMatch.dispose();
        this.sb_back.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        Vector3 mousePos = this.camera.unproject(new Vector3(screenX, screenY, 0));
        this.sb_createMatch.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_joinMatch.execute((int)mousePos.x, (int)mousePos.y);
        this.sb_back.execute((int)mousePos.x, (int)mousePos.y);
        return false;
    }
}
