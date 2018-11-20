package maps;

import characters.*;
import characters.bullets.Bullet;
import characters.bullets.BulletDataPackage;
import characters.bullets.GhostBullet;
import characters.bullets.GunBullet;
import characters.players.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SuperSmashShoot;
import general.Converter;
import general.DataManager;
import general.FrameRate;
import general.IDs;
import screens.CharacterSelection;
import tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class Map implements Screen {

    private SuperSmashShoot game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private List<Tile> tiles;
    private Player player;
    public static List<GhostPlayer> ghostPlayers = new ArrayList<>();
    public static List<Bullet> bullets = new ArrayList<>();
    private List<GhostBullet> ghostBullets = new ArrayList<>();

    private Sprite background;

    private float timeToStart;
    private BitmapFont countDownFont;
    private GlyphLayout fontInfo;

    private FrameRate fps;
    public static boolean showDebugging, showFps;

    private MapBorders mapBorders;
    private Sound music;

    public Map(SuperSmashShoot game, String mapName){
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);
        this.viewport = new FitViewport(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT, this.camera);

        SuperSmashShoot.mediaPlayer.pause();

        if(mapName.toLowerCase().contains("port"))
            this.music = Converter.idToSound(IDs.CITY_MUSIC);
        else if(mapName.toLowerCase().contains("sky"))
            this.music = Converter.idToSound(IDs.SKY_MUSIC);

        this.music.loop(DataManager.music / 100f);

        this.tiles = MapParser.getTilesFromFile(mapName);
        this.background = MapParser.getBackground(mapName);
        this.background.setSize(SuperSmashShoot.SCREEN_WIDTH, SuperSmashShoot.SCREEN_HEIGHT);

        this.timeToStart = 10;
        this.countDownFont = new BitmapFont(Gdx.files.internal(DataManager.font));
        this.countDownFont.getData().setScale(2f);
        this.countDownFont.setColor(Color.BLACK);
        this.fontInfo = new GlyphLayout();
        this.fontInfo.setText(this.countDownFont, "5");

        this.mapBorders = new MapBorders();

        if(CharacterSelection.chosenCharacter == IDs.CharacterType.SOLDIER)
            this.player = new Soldier(new Vector2(512, 256), CharacterSelection.skin);
        else if(CharacterSelection.chosenCharacter == IDs.CharacterType.KNIGHT)
            this.player = new Knight(new Vector2(512, 256), CharacterSelection.skin);
        else if(CharacterSelection.chosenCharacter == IDs.CharacterType.CLOWN)
            this.player = new Clown(new Vector2(512, 256), CharacterSelection.skin);
        else if(CharacterSelection.chosenCharacter == IDs.CharacterType.PIRATE)
            this.player = new Pirate(new Vector2(512, 256), CharacterSelection.skin);

        for(int i = 0; i < SuperSmashShoot.serverListener.pdpList.size(); i ++) {
            for(PlayerDataPackage pdp : SuperSmashShoot.serverListener.pdpList){
                if(SuperSmashShoot.serverListener.pdpList.get(i).getUsr().equals(pdp.getUsr())){
                    Map.ghostPlayers.add(new GhostPlayer(SuperSmashShoot.serverListener.pdpList.get(i).getCharacterType(),
                            pdp.getSkin(), SuperSmashShoot.serverListener.pdpList.get(i).getUsr()));
                }
            }
        }

        this.fps = new FrameRate();
        Map.showDebugging = false;
        Map.showFps = false;
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

        if(this.timeToStart <= 0)
            this.player.shoot(Map.bullets);

        this.player.applyPhysics();
        this.player.applyCollisions(this.tiles);
        this.player.interactWithGhostBullet(this.ghostBullets);

        for(GhostPlayer ghostPlayer : Map.ghostPlayers) {
            ghostPlayer.update();
        }

        for(Bullet b : Map.bullets) {
            b.move();
            b.interact(this.tiles);
        }

        for(Bullet b : Map.bullets) {
            for (GhostPlayer gp : Map.ghostPlayers)
                b.interact(gp);
        }

        this.mapBorders.interactWithBullets(Map.bullets);
        this.mapBorders.interactWithPlayer(this.player);

        /**-------------------------    UPDATE      -------------------------**/

        this.fps.update();
        this.player.update();

        this.updateGhostBullets();

        for(Bullet b : Map.bullets) {
            b.update();
        }

        for(Tile t : this.tiles)
            t.update();

        this.camera.update();

        /**-------------------------     RENDERING      -------------------------**/

        this.game.batch.setProjectionMatrix(this.camera.combined);
        this.game.debugger.setProjectionMatrix(this.camera.combined);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.game.batch.begin();
        this.background.draw(this.game.batch);
        for(Tile t : this.tiles)
            t.render(this.game.batch);

        for(Bullet b : Map.bullets)
            b.render(this.game.batch);

        for(GhostBullet gb : this.ghostBullets)
            gb.render(this.game.batch);

        for(GhostPlayer ghostPlayer : Map.ghostPlayers)
            ghostPlayer.render(this.game.batch);

        this.player.render(this.game.batch);

        if(Map.showFps)
            this.fps.render(this.game.batch);

        if(this.timeToStart >= 0) {
            this.countDownFont.draw(this.game.batch, String.valueOf((int) this.timeToStart), SuperSmashShoot.SCREEN_WIDTH / 2f - this.fontInfo.width / 2f,
                    SuperSmashShoot.SCREEN_HEIGHT / 2f);
            this.timeToStart -= Gdx.graphics.getDeltaTime();
        }

        this.game.batch.end();

        /**-------------------------    DEBUGGING       --------------------------**/

        if(Map.showDebugging){
            this.game.debugger.begin(ShapeRenderer.ShapeType.Line);
            for(Tile t : this.tiles)
                t.debug(this.game.debugger);

            for(Bullet b : Map.bullets)
                b.debug(this.game.debugger);

            for(GhostBullet b : this.ghostBullets)
                b.debug(this.game.debugger);

            for(GhostPlayer ghostPlayer : Map.ghostPlayers)
                ghostPlayer.debug(this.game.debugger);

            this.player.debug(this.game.debugger);
            this.game.debugger.end();
        }

        this.destroyBullets();
    }


    private void updateGhostBullets(){
        List<BulletDataPackage> bdpCopy = new ArrayList<>(ServerListener.bdpList);
        for(BulletDataPackage bdp : bdpCopy){
            if(this.ghostBullets.isEmpty() && !bdp.getPlayer().equals(DataManager.userName)){
                this.ghostBullets.add(new GhostBullet(bdp.getCharacterType(), bdp.getPlayer(), bdp.getId(), bdp.getPosition()));
            }else{
                boolean isInList = false;
                List<GhostBullet> toDestroy = new ArrayList<>(this.ghostBullets);
                for(GhostBullet gb : this.ghostBullets){
                    if(gb.getPlayer().equals(bdp.getPlayer()) && gb.getId() == bdp.getId()){
                        gb.update(bdp.getPosition());
                        toDestroy.remove(gb);
                        isInList = true;
                    }
                }

                if(!isInList && !bdp.getPlayer().equals(DataManager.userName)){
                    this.ghostBullets.add(new GhostBullet(bdp.getCharacterType(), bdp.getPlayer(), bdp.getId(), bdp.getPosition()));
                }

                for (GhostBullet gb : toDestroy)
                    gb.dispose();

                this.ghostBullets.removeAll(toDestroy);
            }
        }
    }

    private void destroyBullets(){
        List<Bullet> toDestroy = new ArrayList<>();
        for(Bullet b : Map.bullets) {
            if (b.isReadyToDestroy()) {
                b.dispose();
                toDestroy.add(b);
            }
        }

        for (Bullet b : toDestroy)
            b.dispose();

        Map.bullets.removeAll(toDestroy);
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
        this.fps.resize(width, height);
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

        for(Bullet b : Map.bullets)
            b.dispose();

        for(GhostPlayer gp : Map.ghostPlayers)
            gp.dispose();

        for(GhostBullet gb : this.ghostBullets)
            gb.dispose();

        this.background.getTexture().dispose();
        this.countDownFont.dispose();
        this.music.dispose();
    }
}
