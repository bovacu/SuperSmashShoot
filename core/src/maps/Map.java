package maps;

import characters.GhostPlayer;
import characters.Player;
import characters.Soldier;
import characters.bullets.Bullet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SuperSmashShoot;
import general.FrameRate;
import tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class Map implements Screen {

    private SuperSmashShoot game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private List<Tile> tiles;
    private Player player;
    private List<GhostPlayer> ghostPlayers;
    private List<Bullet> bullets;

    private Sprite background;

    private FrameRate fps;

    public Map(SuperSmashShoot game, String mapName){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);
        this.viewport = new FitViewport(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT, this.camera);

        this.tiles = MapParser.getTilesFromFile(mapName);
        this.background = MapParser.getBackground(mapName);
        this.background.setSize(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);
        this.bullets = new ArrayList<>();
        this.player = new Soldier(new Vector2(512, 256));

        this.ghostPlayers = new ArrayList<>();

        for(int i = 0; i < SuperSmashShoot.serverListener.pdpList.size(); i ++)
            this.ghostPlayers.add(new GhostPlayer(SuperSmashShoot.serverListener.pdpList.get(i).getId(),
                    SuperSmashShoot.serverListener.pdpList.get(i).getUsr()));

        this.fps = new FrameRate(this.player);
        SuperSmashShoot.serverListener.resetLoadMapF();
        SuperSmashShoot.serverListener.resetMapF();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        /**-------------------------    INPUT       -------------------------**/
        this.player.jump();
        this.player.move();
        this.player.shoot(this.bullets);

        this.player.applyPhysics();
        this.player.applyCollisions(this.tiles);

        for(GhostPlayer ghostPlayer : this.ghostPlayers)
            ghostPlayer.update();

        for(Bullet b : this.bullets) {
            b.move();
            b.interact(this.tiles);
        }

        /**-------------------------    UPDATE      -------------------------**/

        this.fps.update();
        this.player.update();

        for(Bullet b : this.bullets)
            b.update();

        for(Tile t : this.tiles)
            t.update();

        this.camera.update();

        /**-------------------------     RENDERING      -------------------------**/

        this.game.batch.setProjectionMatrix(this.camera.combined);
        this.game.debugger.setProjectionMatrix(this.camera.combined);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.fps.render();
        this.game.batch.begin();
        this.background.draw(this.game.batch);
        for(Tile t : this.tiles)
            t.render(this.game.batch);

        for(Bullet b : this.bullets)
            b.render(this.game.batch);

        for(GhostPlayer ghostPlayer : this.ghostPlayers)
            ghostPlayer.render(this.game.batch);

        this.player.render(this.game.batch);
        this.game.batch.end();

        /**-------------------------    DEBUGGING       --------------------------**/

        this.game.debugger.begin(ShapeRenderer.ShapeType.Line);
        for(Tile t : this.tiles)
            t.debug(this.game.debugger);

        for(Bullet b : this.bullets)
            b.debug(this.game.debugger);

        this.player.debug(this.game.debugger);
        this.game.debugger.end();

        this.destroyBullets();
    }

    private void destroyBullets(){
        List<Bullet> toDestroy = new ArrayList<>();
        for(Bullet b : this.bullets) {
            if (b.isReadyToDestroy()) {
                b.dispose();
                toDestroy.add(b);
            }
        }
        this.bullets.removeAll(toDestroy);
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
        for(Tile t : this.tiles)
            t.dispose();
        this.fps.dispose();

        this.player.dispose();

        for(Bullet b : this.bullets)
            b.dispose();

        this.background.getTexture().dispose();
    }
}
