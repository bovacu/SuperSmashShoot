package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.SuperSmashShoot;
import maps.Map;

public class KeyboardController extends InputAdapter {

    private Player player;

    public KeyboardController(Player player){
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(player.getLife() > 0){
            if(keycode == Input.Keys.SPACE && this.player.isCanJump()) {
                this.player.setCanJump(false);
                this.player.setJumping(true);
            } else if(keycode == Input.Keys.D) {
                this.player.setMoveDirection(1);
                this.player.setCanMove(true);

            }else if(keycode == Input.Keys.A) {
                this.player.setMoveDirection(-1);
                this.player.setCanMove(true);

            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(player.getLife() > 0){
            if(keycode == Input.Keys.SPACE && this.player.isJumping())
                this.player.setJumping(false);
            else if(keycode == Input.Keys.D && !Gdx.input.isKeyPressed(Input.Keys.A))
                this.player.setCanMove(false);
            else if(keycode == Input.Keys.A && !Gdx.input.isKeyPressed(Input.Keys.D))
                this.player.setCanMove(false);
        }

        if(keycode == Input.Keys.F9 && !Map.showDebugging)
            Map.showDebugging = true;
        else if(keycode == Input.Keys.F9)
            Map.showDebugging = false;

        if(keycode == Input.Keys.F10 && !Map.showFps)
            Map.showFps = true;
        else if(keycode == Input.Keys.F10)
            Map.showFps = false;

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(player.getLife() > 0){
            this.player.setCanShoot(true);
            this.player.setClickPosition(new Vector2(screenX, SuperSmashShoot.SCREEN_HEIGHT - screenY));
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if(screenX >= this.player.getPosition().x + Player.PLAYER_WH / 2f) {
            if (this.player.animationController.isFlipX()) {
                this.player.animationController.flipAnimation();

                this.player.getWeapon().flip(false, true);
            }
            this.player.setAimDirection(1);

            if(ServerSpeaker.pdp != null)
                ServerSpeaker.pdp.setAimDirection(1);
        }else {
            if (!this.player.animationController.isFlipX()) {
                this.player.animationController.flipAnimation();
                this.player.getWeapon().flip(false, true);
            }
            this.player.setAimDirection(-1);

            if(ServerSpeaker.pdp != null)
                ServerSpeaker.pdp.setAimDirection(-1);
        }

        if(ServerSpeaker.pdp != null)
            ServerSpeaker.pdp.setFlipAnim(this.player.animationController.isFlipX());

        int mouseX = screenX;
        int mouseY = SuperSmashShoot.SCREEN_HEIGHT - screenY;
        float rot = MathUtils.radiansToDegrees * MathUtils.atan2(mouseY - player.getSprite().getY(), mouseX - player.getSprite().getX());
        player.getWeapon().setRotation(rot);

        return false;
    }
}
