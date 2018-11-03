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
import ui.Label;
import ui.SpriteButton;

import java.util.List;

public class StatsMenu extends InputAdapter implements Screen {

    private SuperSmashShoot game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Label lb_matches, lb_wins, lb_looses, lb_doneDamage, lb_receivedDamage;

    private SpriteButton sb_back;

    private Sprite background;

    public StatsMenu(SuperSmashShoot game){
        this.game = game;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);
        this.viewport = new FitViewport(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT, this.camera);

        List<String> data = SuperSmashShoot.serverSpeaker.getStatsList();

        this.lb_matches = new Label("Number of match played:           " + data.get(0), new Vector2(SuperSmashShoot.SCREEN_WIDTH / 4f,
                SuperSmashShoot.SCREEN_HEIGHT * 0.95f));

        this.lb_wins = new Label("Total of wins:                             " + data.get(1), new Vector2(SuperSmashShoot.SCREEN_WIDTH / 4f,
                SuperSmashShoot.SCREEN_HEIGHT * 0.75f));

        this.lb_looses = new Label("Total of looses:                         " + data.get(2), new Vector2(SuperSmashShoot.SCREEN_WIDTH / 4f,
                SuperSmashShoot.SCREEN_HEIGHT * 0.55f));

        this.lb_doneDamage = new Label("Total damage done:                    " + data.get(3), new Vector2(SuperSmashShoot.SCREEN_WIDTH / 4f,
                SuperSmashShoot.SCREEN_HEIGHT * 0.35f));

        this.lb_receivedDamage = new Label("Total damage done:                    " + data.get(4), new Vector2(SuperSmashShoot.SCREEN_WIDTH / 4f,
                SuperSmashShoot.SCREEN_HEIGHT * 0.15f));

        this.sb_back = new SpriteButton(IDs.PREVIOUS, IDs.PREVIOUS_DOWN) {
            @Override
            public void action() {
                this.dispose();
                game.setScreen(new MainMenu(game));
            }
        };
        this.sb_back.setPosition(new Vector2(15, 15));

        this.background = Converter.idToSprite(IDs.BACKGROUND1);
        this.background.setSize(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);

        Gdx.input.setInputProcessor(this);
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
        this.lb_matches.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.lb_wins.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.lb_looses.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.lb_doneDamage.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
        this.lb_receivedDamage.render(this.game.batch, (int)mousePos.x, (int)mousePos.y);
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        Vector3 mousePos = this.camera.unproject(new Vector3(screenX, screenY, 0));
        this.sb_back.execute((int)mousePos.x, (int)mousePos.y);
        return false;
    }

    @Override
    public void dispose() {
        this.background.getTexture().dispose();
        this.lb_wins.dispose();
        this.lb_wins.dispose();
        this.lb_looses.dispose();
        this.lb_doneDamage.dispose();
        this.lb_receivedDamage.dispose();
        this.sb_back.dispose();
    }
}
