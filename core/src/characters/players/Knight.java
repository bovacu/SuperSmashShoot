package characters.players;

import characters.HealthBar;
import characters.ServerSpeaker;
import characters.bullets.Bullet;
import characters.bullets.GhostBullet;
import characters.bullets.SpinningBullet;
import characters.controllers.AnimationController;
import characters.controllers.KeyboardController;
import characters.controllers.XboxController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import general.Converter;
import general.DataManager;
import general.IDs;
import maps.Items;
import tiles.Tile;

import java.util.List;

public class Knight extends Player {

    private final String ANIMATION_NAMES[] = {"IDLE", "WALK", "DYING"};
    private BitmapFont font;
    private GlyphLayout fontInfo;
    private HealthBar healthBar;
    private int skin;

    private Sound shot;

    public Knight(Vector2 position, int skin) {
        super(Converter.knightSkinToIdle(skin), position, new Vector2(Player.PLAYER_WH, Player.PLAYER_WH));
        this.skin = skin;
        super.weapon = Converter.idToSprite(IDs.AXE);
        super.weapon.setCenter(super.weapon.getX(), super.weapon.getY() + super.weapon.getHeight() / 2f);
        super.weapon.setPosition(super.getSprite().getX() + super.weapon.getWidth() / 2f, super.getSprite().getY() + super.weapon.getHeight() / 2f);
        this.addController();
        this.createAnimationController();
        this.font = new BitmapFont(Gdx.files.internal(DataManager.font));
        this.font.getData().setScale(0.55f);
        this.fontInfo = new GlyphLayout();
        this.fontInfo.setText(this.font, DataManager.userName);
        this.healthBar = new HealthBar(this);
        super.SHOOT_DELAY = 0.9f;
        super.timePassedToShoot = super.SHOOT_DELAY;

        this.shot = Converter.idToSound(IDs.THROW_EFFECT);
    }

    private void addController(){
        if(Controllers.getControllers().size > 0){
            Controllers.addListener(new XboxController(this));
        }else{
            Gdx.input.setInputProcessor(new KeyboardController(this));
        }
    }

    private void createAnimationController(){
        int ids[]                   = Converter.knightSkinToAnimationsIds(this.skin);
        int frames[]                = {5, 4, 5};
        float times[]               = {0.15f, 0.2f, 0.15f};
        Animation.PlayMode modes[]  = {Animation.PlayMode.LOOP, Animation.PlayMode.LOOP, Animation.PlayMode.NORMAL};

        super.animationController = new AnimationController(this, this.ANIMATION_NAMES, ids, frames, times, modes);
    }

    private void manageAnimationStates(){
        if(super.isCanMove() && !super.animationController.getCurrentAnimationName().equals(this.ANIMATION_NAMES[1]) && super.getLife() > 0)
            super.animationController.changeAnimationTo(this.ANIMATION_NAMES[1]);
        else if(!super.isCanMove() && !super.animationController.getCurrentAnimationName().equals(this.ANIMATION_NAMES[0]) && super.getLife() > 0)
            super.animationController.changeAnimationTo(this.ANIMATION_NAMES[0]);
        else if(!super.animationController.getCurrentAnimationName().equals(this.ANIMATION_NAMES[2]) && super.getLife() <= 0){
            super.animationController.changeAnimationTo(this.ANIMATION_NAMES[2]);
        }
    }

    @Override
    public void applyPhysics() {
        if(super.isApplyGravity()){
            if(this.yVelocity > Player.MAX_FALL_SPEED)
                this.yVelocity += Player.GRAVITY * Gdx.graphics.getDeltaTime() + 0.5 * Player.GRAVITY * Gdx.graphics.getDeltaTime();
            super.getSprite().setPosition(super.getSprite().getX(), super.getSprite().getY() + this.yVelocity);
            this.update();
        }
    }

    @Override
    public void applyCollisions(List<Tile> tileList) {
        boolean attached = false;
        for(Tile tile : tileList) {
            if(tile.getCollider() != null){
                // Feet collider
                if(super.getColliders()[0].overlaps(tile.getCollider())) {
                    super.yVelocity = 0;
                    super.getSprite().setY(tile.getCollider().y + tile.getCollider().height);
                    this.update();
                    super.setCanJump(true);
                    attached = false;
                }

                // Head collider
                if(super.getColliders()[1].overlaps(tile.getCollider())){
                    this.yVelocity = 0;
                    super.getSprite().setY(tile.getCollider().y - super.getSprite().getHeight());
                    this.update();
                    super.setCanJump(false);
                    super.setJumping(false);
                }

                // Left collider
                if(super.getColliders()[2].overlaps(tile.getCollider())){
                    super.setCanJump(true);
                    float diff = super.getColliders()[2].x - super.getSprite().getX();
                    super.getSprite().setX(tile.getCollider().x + tile.getCollider().width - diff);
                    this.update();
                    if(!attached)
                        attached = true;
                }

                // Right collider
                if(super.getColliders()[3].overlaps(tile.getCollider())){
                    super.setCanJump(true);
                    float right = super.getSprite().getX() + super.getSprite().getWidth();
                    float diff = right - (super.getColliders()[3].x + super.getColliders()[3].width);
                    super.getSprite().setX(tile.getCollider().x - super.getSprite().getWidth() + diff);
                    this.update();
                    if(!attached)
                        attached = true;
                }

                if(attached) {
                    super.setApplyGravity(false);
                    super.setJumping(false);
                }else{
                    super.setApplyGravity(true);
                }
            }
        }
    }

    @Override
    public void shoot(List<Bullet> bulletList) {
        if(super.timePassedToShoot >= super.SHOOT_DELAY) {
            if (super.isCanShoot()) {
                this.shot.play(DataManager.effects / 100f);
                Bullet gb = new SpinningBullet(IDs.AXE, this);
                bulletList.add(gb);
                super.setCanShoot(false);
                this.timePassedToShoot = 0;
            }
        } else
            timePassedToShoot += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void jump() {
        //y = y0 + g*t + 1/2 * g * t^2
        if(super.isJumping()){
            if(this.yVelocity < Player.MAX_JUMP_SPEED)
                this.yVelocity += jumpSpeed * Gdx.graphics.getDeltaTime() + 0.5f * jumpSpeed * Math.pow(Gdx.graphics.getDeltaTime(), 2);
            else
                super.setJumping(false);
            super.getSprite().setY(super.getSprite().getY() + this.yVelocity * Gdx.graphics.getDeltaTime());
            this.update();
        }
    }

    @Override
    public void move() {
        if(super.isCanMove()){
            float movement = super.getSprite().getX() + (super.getWalkSpeed() * Gdx.graphics.getDeltaTime()) * super.getMoveDirection();
            super.getSprite().setX(movement);
            super.getSprite().setY((int)super.getSprite().getY());
            super.getSprite().setPosition(movement, super.getSprite().getY());
            this.update();
        }
    }

    @Override
    public void interactWithBullet(List<Bullet> bulletList) {

    }

    @Override
    public void interactWithGhostBullet(List<GhostBullet> bulletList) {

    }

    @Override
    public void interactWithItems(List<Items> itemsList) {

    }

    @Override
    public void update() {
        super.getColliders()[0].x = super.getSprite().getX() + super.getSprite().getWidth() / 4f;
        super.getColliders()[0].y = super.getSprite().getY();

        super.getColliders()[1].x = super.getSprite().getX() + super.getSprite().getWidth() / 4f;
        super.getColliders()[1].y = super.getSprite().getY() + super.getSprite().getHeight() - super.getColliders()[1].height * 2;

        super.getColliders()[2].x = super.getSprite().getX() + super.getSprite().getWidth()/ 7f;
        super.getColliders()[2].y = super.getSprite().getY() + super.getSprite().getHeight()/ 7f;

        super.getColliders()[3].x = super.getSprite().getX() +super.getSprite().getWidth() - super.getSprite().getWidth() / 3f;
        super.getColliders()[3].y = super.getSprite().getY() + super.getSprite().getHeight() / 7f;

        super.getWeapon().setPosition(super.getSprite().getX(), super.getSprite().getY());

        this.manageAnimationStates();

        ServerSpeaker.pdp.update(new int[]{(int)super.getSprite().getX(), (int)super.getSprite().getY()},
                super.animationController.getCurrentAnimationName());

        super.setLife(ServerSpeaker.pdp.getLife());
    }

    @Override
    public void render(SpriteBatch batch) {
        super.animationController.play(batch, Player.PLAYER_WH);
        if(super.getLife() > 0)
            super.getWeapon().draw(batch);
        this.healthBar.render(batch);
        this.font.draw(batch, DataManager.userName, super.getSprite().getX() + super.getSprite().getWidth() / 2f - this.fontInfo.width / 2f,
                this.healthBar.getPosition().y + this.healthBar.getSize().height * 3f);
    }

    @Override
    public void debug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        for(Rectangle r : super.getColliders())
            shapeRenderer.rect(r.x, r.y, r.width, r.height);
        shapeRenderer.line(new Vector2(super.getSprite().getX() + super.getSprite().getWidth() / 2f,
                super.getSprite().getY() + super.getSprite().getHeight() / 2f), super.getClickPosition());

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.line(new Vector2(super.getSprite().getX() + super.getSprite().getWidth() / 2f,
                        super.getSprite().getY() + super.getSprite().getHeight() / 2f),
                new Vector2(super.getSprite().getX() + super.getSprite().getWidth() * 2, super.getSprite().getY() + super.getSprite().getHeight() / 2f));

        shapeRenderer.line(new Vector2(super.getSprite().getX() + super.getSprite().getWidth() / 2f,
                        super.getSprite().getY() + super.getSprite().getHeight() / 2f),
                new Vector2(super.getSprite().getX() + super.getSprite().getWidth() / 2f, super.getSprite().getY() + super.getSprite().getHeight() * 2));

        shapeRenderer.line(new Vector2(super.getSprite().getX() + super.getSprite().getWidth() / 2f,
                        super.getSprite().getY() + super.getSprite().getHeight() / 2f),
                new Vector2(super.getSprite().getX() + super.getSprite().getWidth() * -1, super.getSprite().getY() + super.getSprite().getHeight() / 2f));

        shapeRenderer.line(new Vector2(super.getSprite().getX() + super.getSprite().getWidth() / 2f,
                        super.getSprite().getY() + super.getSprite().getHeight() / 2f),
                new Vector2(super.getSprite().getX() + super.getSprite().getWidth() / 2f, super.getSprite().getY() + super.getSprite().getHeight() * -1));
    }

    @Override
    public void dispose() {
        super.getSprite().getTexture().dispose();
        this.animationController.dispose();
        super.getWeapon().getTexture().dispose();
        this.shot.dispose();
    }
}
