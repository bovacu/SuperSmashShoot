package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.SuperSmashShoot;
import general.Converter;
import general.DataManager;
import general.GameObject;
import general.IDs;

public class GhostPlayer extends GameObject {
    private final String ANIMATION_NAMES[] = {"IDLE", "WALK", "DYING"};

    private String usr;
    private AnimationController animationController;
    private IDs.CharacterType characterType;
    private BitmapFont font;
    private GlyphLayout fontInfo;
    private Rectangle collider;
    private HealthBar healthBar;

    private int life;
    private int skin;

    public GhostPlayer(IDs.CharacterType characterType, int skin, String usr){
        super(Converter.characterTypeToInt(characterType));
        this.skin = skin;
        this.characterType = characterType;
        this.life = 5;
        this.usr = usr;
        this.createAnimationController();
        this.font = new BitmapFont(Gdx.files.internal(DataManager.font));
        this.font.getData().setScale(0.55f);
        this.fontInfo = new GlyphLayout();
        this.fontInfo.setText(this.font, usr);
        this.collider = Converter.ghostCollider(this);
        this.healthBar = new HealthBar(this);

    }

    private void createAnimationController(){
        int animationsIds[] = null;

        if(this.characterType == IDs.CharacterType.SOLDIER)
            animationsIds = Converter.soldierSkinToAnimationsIds(this.skin);

        int ids[]                   = animationsIds;
        int frames[]                = {2, 4, 5};
        float times[]               = {0.3f, 0.2f, 0.15f};
        Animation.PlayMode modes[]  = {Animation.PlayMode.LOOP, Animation.PlayMode.LOOP, Animation.PlayMode.NORMAL};

        this.animationController = new AnimationController(this, this.ANIMATION_NAMES, ids, frames, times, modes);
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Rectangle getCollider(){
        return this.collider;
    }

    public String getUsr() {
        return usr;
    }

    @Override
    public void update() {
        for(PlayerDataPackage pdp : SuperSmashShoot.serverListener.pdpList) {
            if (pdp.getUsr().equals(this.usr)) {
                super.getSprite().setPosition(pdp.getPosition()[0], pdp.getPosition()[1]);
                this.updateCollider(pdp.getPosition());
                if (!pdp.getAnim().equals(this.animationController.getCurrentAnimationName())) {
                    this.animationController.changeAnimationTo(pdp.getAnim());
                }

                if (pdp.isFlipAnim() && !this.animationController.isFlipX()) {
                    this.animationController.flipAnimation();
                } else if (!pdp.isFlipAnim() && this.animationController.isFlipX())
                    this.animationController.flipAnimation();
                break;
            }
        }
    }

    private void updateCollider(int position[]){
        this.collider.x = position[0];
        this.collider.y = position[1];
    }

    @Override
    public void render(SpriteBatch batch) {
        this.animationController.play(batch, Player.PLAYER_WH);
        this.healthBar.render(batch);
        this.font.draw(batch, this.usr, super.getSprite().getX() + Player.PLAYER_WH / 2f - this.fontInfo.width / 2f,
                this.healthBar.getPosition().y + this.healthBar.getSize().height * 3f);
    }

    @Override
    public void debug(ShapeRenderer shapeRenderer) {
        if(this.collider != null){
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(this.collider.x, this.collider.y, this.collider.width, this.collider.height);
        }
    }

    @Override
    public void dispose() {
        super.getSprite().getTexture().dispose();
        this.animationController.dispose();
        this.font.dispose();
    }
}
