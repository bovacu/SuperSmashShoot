package characters;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class XboxController implements ControllerListener {

    private Player player;

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
        if(buttonCode == Xbox360Values.BUTTON_A && this.player.isCanJump()) {
            this.player.setCanJump(false);
            this.player.setJumping(true);
        }

        if(buttonCode == Xbox360Values.BUTTON_RB)
            this.player.setCanShoot(true);
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
                if(this.player.animationController.isFlipX())
                    this.player.animationController.flipAnimation();
                this.player.setAimDirection(1);
            } else if(value < -0.1) {
                if(!this.player.animationController.isFlipX())
                    this.player.animationController.flipAnimation();
                this.player.setAimDirection(-1);
            }
        }

        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
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
