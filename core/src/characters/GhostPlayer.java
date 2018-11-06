package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.SuperSmashShoot;
import general.DataManager;
import general.GameObject;
import general.IDs;

public class GhostPlayer extends GameObject {
    private final String ANIMATION_NAMES[] = {"IDLE", "WALK"};

    private String usr;
    private AnimationController animationController;
    private BitmapFont font;
    private GlyphLayout fontInfo;

    public GhostPlayer(int id, String usr){
        super(id);
        this.usr = usr;
        this.createAnimationController();
        this.font = new BitmapFont(Gdx.files.internal(DataManager.font));
        this.font.getData().setScale(0.75f);
        this.fontInfo = new GlyphLayout();
        this.fontInfo.setText(this.font, usr);
    }

    private void createAnimationController(){
        int ids[]                   = {IDs.SOLDIER_IDLE, IDs.SOLDIER_WALKING};
        int frames[]                = {2, 4};
        float times[]               = {0.3f, 0.2f};
        Animation.PlayMode modes[]  = {Animation.PlayMode.LOOP, Animation.PlayMode.LOOP};

        this.animationController = new AnimationController(this, this.ANIMATION_NAMES, ids, frames, times, modes);
    }

    @Override
    public void update() {
        for(PlayerDataPackage pdp : SuperSmashShoot.serverListener.pdpList)
            if(pdp.getUsr().equals(this.usr)){
                super.getSprite().setPosition(pdp.getPosition()[0], pdp.getPosition()[1]);
                if(!pdp.getAnim().equals(this.animationController.getCurrentAnimationName()))
                    this.animationController.changeAnimationTo(pdp.getAnim());

                if(pdp.isFlipAnim() && !this.animationController.isFlipX()){
                    this.animationController.flipAnimation();
                }else if(!pdp.isFlipAnim() && this.animationController.isFlipX())
                    this.animationController.flipAnimation();
                break;
            }
    }

    @Override
    public void render(SpriteBatch batch) {
        this.animationController.play(batch, Player.PLAYER_WH);
        this.font.draw(batch, this.usr, super.getSprite().getX() + Player.PLAYER_WH / 2f - this.fontInfo.width / 2f,
                super.getSprite().getY() + Player.PLAYER_WH * 1.5f);
    }

    @Override
    public void debug(ShapeRenderer shapeRenderer) {

    }

    @Override
    public void dispose() {
        super.getSprite().getTexture().dispose();
        this.animationController.dispose();
        this.font.dispose();
    }
}
