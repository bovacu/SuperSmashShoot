package characters.controllers;

import characters.players.Player;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class XboxController implements ControllerListener {

    private Player player;
    private double lastAngle;

    public XboxController(Player player){
        this.player = player;
    }

    @Override
    public void connected(Controller controller) {
        Controllers.addListener(new XboxController(this.player));
    }

    @Override
    public void disconnected(Controller controller) {
        Controllers.removeListener(this);
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        if((buttonCode == Xbox360Values.BUTTON_A) && this.player.isCanJump()) {
            this.player.setCanJump(false);
            this.player.setJumping(true);
        }

        if(buttonCode == Xbox360Values.BUTTON_RB)
            if(player.getLife() > 0 && (player.getTimePassedToShoot() >= player.getSHOOT_DELAY())) {
                this.player.setCanShoot(true);
                float _y = -controller.getAxis(0);
                float _x = controller.getAxis(1);
                player.setClickPosition(new Vector2(
                        this.player.getSprite().getX() + this.player.getSprite().getWidth() / 2f + _x * 100f,
                        this.player.getSprite().getY() + this.player.getSprite().getHeight() / 2f + _y * 100f));
            }
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        if(axisCode == Xbox360Values.AXIS_LEFT_X) {
            if(value > 0.1) {
                this.player.setMoveDirection(1);
                this.player.setCanMove(true);
            } else if(value < -0.1) {
                this.player.setMoveDirection(-1);
                this.player.setCanMove(true);
            } else
                this.player.setCanMove(false);
        }

        if(axisCode == Xbox360Values.AXIS_RIGHT_X){
            if(value > 0.1) {
                if(this.player.getAnimationController().isFlipX())
                    this.player.getAnimationController().flipAnimation();
                this.player.setAimDirection(1);
            } else if(value < -0.1) {
                if(!this.player.getAnimationController().isFlipX())
                    this.player.getAnimationController().flipAnimation();
                this.player.setAimDirection(-1);
            }
        }

        float _y = -controller.getAxis(0);
        float _x = controller.getAxis(1);

        double angle = Math.atan2(_y, _x);

        angle = (angle * 180) / Math.PI;

        if(angle < 0)
            angle = 360 + angle;

        if(Math.abs(_x) > 0.001f && Math.abs(_y) > 0.001f)
            lastAngle = angle;

        this.player.getWeapon().setRotation((float)lastAngle);

        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        System.out.println(value);
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}
