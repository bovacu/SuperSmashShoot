package characters;

import characters.bullets.Bullet;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import general.Converter;
import general.GameObject;
import general.IDs;
import maps.Items;
import tiles.Tile;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

public abstract class Player extends GameObject{

    public static final int PLAYER_WH = 64;
    public static float GRAVITY = -15f;
    public static final float MAX_FALL_SPEED = -15;
    public static final int MAX_JUMP_SPEED = 10;

    private Rectangle collider[];

    private int damagePercentage;
    private boolean alive;

    private float walkSpeed;
    protected float jumpSpeed;
    protected float yVelocity;
    private boolean canJump;
    private boolean canMove;
    private boolean canShoot;
    private boolean jumping;
    private int moveDirection;
    private int aimDirection;
    private boolean applyGravity;

    protected AnimationController animationController;

    private Vector2 clickPosition;

    private Sprite weapon;

    public Player(int id, Vector2 position, Vector2 size) {
        super(id, position, size);
        this.collider = Converter.gameObjectToPlayerCollider(this);
        this.damagePercentage = 0;
        this.alive = true;
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
        this.weapon = Converter.idToSprite(IDs.GUN);
        this.weapon.setCenter(this.weapon.getX(), this.weapon.getY() + this.weapon.getHeight() / 2f);
        this.weapon.setPosition(super.getSprite().getX() + this.weapon.getWidth() / 2f, super.getSprite().getY() + this.weapon.getHeight() / 2f);
        this.clickPosition = new Vector2();
    }

    Rectangle[] getColliders(){
        return this.collider;
    }

    public int getDamagePercentage(){
        return this.damagePercentage;
    }

    public boolean isAlive(){
        return this.alive;
    }

    public void increaseDamagePercentage(int amount){
        this.damagePercentage += amount;
    }

    public void resetDamagePercentage(){
        this.damagePercentage = 0;
    }

    void setCanMove(boolean canMove){
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

    void setAimDirection(int aimDirection){
        this.aimDirection = aimDirection;
    }

    void setClickPosition(Vector2 clickPosition){
        this.clickPosition = clickPosition;
    }

    public Vector2 getClickPosition(){
        return this.clickPosition;
    }

    boolean isCanJump() {
        return canJump;
    }

    void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    boolean isCanMove() {
        return canMove;
    }

    boolean isCanShoot() {
        return canShoot;
    }

    void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    boolean isJumping() {
        return jumping;
    }

    void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    void setMoveDirection(int moveDirection) {
        this.moveDirection = moveDirection;
    }

    boolean isApplyGravity(){
        return this.applyGravity;
    }

    void setApplyGravity(boolean apply){
        this.applyGravity = apply;
    }

    Sprite getWeapon(){
        return this.weapon;
    }

    public abstract void applyPhysics();
    public abstract void applyCollisions(List<Tile> tileList);
    public abstract void shoot(List<Bullet> bulletList);
    public abstract void jump();
    public abstract void move();
    public abstract void interactWithBullet(List<Bullet> bulletList);
    public abstract void interactWithItems(List<Items> itemsList);
}
