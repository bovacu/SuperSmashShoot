package characters;

import characters.bullets.Bullet;
import characters.bullets.GunBullet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import general.IDs;
import maps.Items;
import tiles.Tile;

import java.util.List;

public class Soldier extends Player {

    private final String ANIMATION_NAMES[] = {"IDLE", "WALK"};

    public Soldier(Vector2 position) {
        super(IDs.SOLDIER_IDLE, position, new Vector2(Player.PLAYER_WH, Player.PLAYER_WH));
        this.addController();
        this.createAnimationController();
    }

    private void addController(){
        if(Controllers.getControllers().size > 0){
            Controllers.addListener(new XboxController(this));
            System.out.println("con mando");
        }else{
            Gdx.input.setInputProcessor(new KeyboardController(this));
            System.out.println("sin mando");
        }
    }

    private void createAnimationController(){
        int ids[]                   = {IDs.SOLDIER_IDLE, IDs.SOLDIER_WALKING};
        int frames[]                = {2, 4};
        float times[]               = {0.3f, 0.2f};
        Animation.PlayMode modes[]  = {Animation.PlayMode.LOOP, Animation.PlayMode.LOOP};

        super.animationController = new AnimationController(this, this.ANIMATION_NAMES, ids, frames, times, modes);
    }

    private void manageAnimationStates(){
        if(super.isCanMove() && !super.animationController.getCurrentAnimationName().equals(this.ANIMATION_NAMES[1]))
            super.animationController.changeAnimationTo(this.ANIMATION_NAMES[1]);
        else if(!super.isCanMove() && !super.animationController.getCurrentAnimationName().equals(this.ANIMATION_NAMES[0]))
            super.animationController.changeAnimationTo(this.ANIMATION_NAMES[0]);

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
        if(super.isCanShoot()){
            Bullet gb = new GunBullet(IDs.GUN_BULLET, this);
            bulletList.add(gb);
            super.setCanShoot(false);
        }
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
    }

    @Override
    public void render(SpriteBatch batch) {
        super.animationController.play(batch, Player.PLAYER_WH);
        super.getWeapon().draw(batch);
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
    }
}
