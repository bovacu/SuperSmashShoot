package general;

import characters.Player;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.Disposable;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.SuperSmashShoot;


/**
 * A nicer class for showing framerate that doesn't spam the console
 * <p>
 * like Logger.log()
 *
 * @author William Hartman
 */

public class FrameRate implements Disposable {

    long lastTimeCounted;

    private float sinceChange;

    private float frameRate;

    private BitmapFont font;

//    private SpriteBatch batch;
//    private OrthographicCamera cam;


    public FrameRate() {
        lastTimeCounted = TimeUtils.millis();
        sinceChange = 0;
        frameRate = Gdx.graphics.getFramesPerSecond();
        font = new BitmapFont(Gdx.files.internal(DataManager.font));
        font.getData().setScale(0.5f);
//        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void resize(int screenWidth, int screenHeight) {
//        cam = new OrthographicCamera(screenWidth, screenHeight);
//        cam.translate(screenWidth / 2f, screenHeight / 2f);
//        cam.update();
//        batch.setProjectionMatrix(cam.combined);
    }

    public void update() {
        long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
        lastTimeCounted = TimeUtils.millis();
        sinceChange += delta;
        if (sinceChange >= 1000) {
            sinceChange = 0;
            frameRate = Gdx.graphics.getFramesPerSecond();
        }
    }


    public void render(SpriteBatch batch) {
        font.draw(batch, (int) frameRate + " fps", SuperSmashShoot.SCREEN_WIDTH * 0.05f, SuperSmashShoot.SCREEN_HEIGHT * 0.95f);
    }


    public void dispose() {
        font.dispose();
    }

}
