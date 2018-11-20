package characters.players;

import characters.controllers.AnimationController;
import characters.bullets.Bullet;
import characters.bullets.GhostBullet;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import general.Converter;
import general.GameObject;
import maps.Items;
import tiles.Tile;

import java.util.List;

public abstract class Player extends GameObject{

    public static final int PLAYER_WH = 64;
    public static float GRAVITY = -15f;
    public static final float MAX_FALL_SPEED = -15;
    public static final int MAX_JUMP_SPEED = 10;

    private Rectangle collider[];

    private int life;

    private float walkSpeed;
    float jumpSpeed;
    float yVelocity;
    private boolean canJump;
    private boolean canMove;
    private boolean canShoot;
    private boolean jumping;
    private int moveDirection;
    private int aimDirection;
    private boolean applyGravity;

    protected AnimationController animationController;

    private Vector2 clickPosition;

    protected Sprite weapon;

    protected float SHOOT_DELAY;
    protected float timePassedToShoot;

    public Player(int id, Vector2 position, Vector2 size) {
        super(id, position, size);
        this.collider = Converter.gameObjectToPlayerCollider(this);
        this.life = 5;
        this.canJump = true;
        this.jumping = false;
        this.yVelocity = 0;
        this.walkSpeed = 200;
        this.jumpSpeed = 150;
        this.moveDirection = 0;
        this.aimDirection = 1;
        this.canMove = false;
        this.canShoot = false;
        this.applyGravity = true;
        this.clickPosition = new Vector2();
    }

    public float getSHOOT_DELAY() {
        return SHOOT_DELAY;
    }

    public float getTimePassedToShoot() {
        return timePassedToShoot;
    }

    public AnimationController getAnimationController(){
        return this.animationController;
    }

    public int getLife() {
        return life;
    }

    void setLife(int life) {
        this.life = life;
    }

    public Rectangle[] getColliders(){
        return this.collider;
    }

    public void setCanMove(boolean canMove){
        this.canMove = canMove;
    }

    int getMoveDirection(){
        return this.moveDirection;
    }

    public int getAimDirection(){
        return this.aimDirection;
    }

    float getWalkSpeed(){
        return this.walkSpeed;
    }

    public void setAimDirection(int aimDirection){
        this.aimDirection = aimDirection;
    }

    public void setClickPosition(Vector2 clickPosition){
        this.clickPosition = clickPosition;
    }

    public Vector2 getClickPosition(){
        return this.clickPosition;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    boolean isCanMove() {
        return canMove;
    }

    boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setMoveDirection(int moveDirection) {
        this.moveDirection = moveDirection;
    }

    boolean isApplyGravity(){
        return this.applyGravity;
    }

    void setApplyGravity(boolean apply){
        this.applyGravity = apply;
    }

    public Sprite getWeapon(){
        return this.weapon;
    }

    public abstract void applyPhysics();
    public abstract void applyCollisions(List<Tile> tileList);
    public abstract void shoot(List<Bullet> bulletList);
    public abstract void jump();
    public abstract void move();
    public abstract void interactWithBullet(List<Bullet> bulletList);
    public abstract void interactWithGhostBullet(List<GhostBullet> ghostBulletList);
    public abstract void interactWithItems(List<Items> itemsList);
}
