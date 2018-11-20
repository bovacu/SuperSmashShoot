package characters.bullets;

import characters.controllers.AnimationController;
import characters.players.GhostPlayer;
import characters.players.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import general.IDs;
import tiles.Tile;

import java.util.List;

public class SpinningBullet extends Bullet {

    public static int bulletID = 0;

    public static final int WH = 32;
    private final float SPEED = 600;
    private final int DIRECTION;
    private float delta;

    private AnimationController controller;

    private Vector2 initialPosition, clickPosition, vector;

    public SpinningBullet(int id, Player player) {
        super(id, player);
        this.DIRECTION = super.getPlayer().getAimDirection() != 0 ? super.getPlayer().getAimDirection() : 1;

        this.setAnimationController();
        this.initialPosition = new Vector2(player.getSprite().getX() + player.getSprite().getWidth() / 2f,
                player.getSprite().getY() + player.getSprite().getHeight() / 2f);
        this.clickPosition = player.getClickPosition();
        this.vector = new Vector2((this.clickPosition.x - this.initialPosition.x), (this.clickPosition.y - this.initialPosition.y));
        this.delta = Gdx.graphics.getDeltaTime();
        super.getSprite().setPosition(this.initialPosition.x - super.getSprite().getWidth() / 2f,
                this.initialPosition.y - super.getSprite().getHeight() / 2f);

        GunBullet.bulletID++;
    }

    private void setAnimationController(){
        String name[]               = {"DESTROYED"};
        int id[]                    = {IDs.GUN_BULLET_DESTROYED};
        int frames[]                = {4};
        float times[]               = {0.05f};
        Animation.PlayMode modes[]  = {Animation.PlayMode.NORMAL};
        this.controller = new AnimationController(this, name,id, frames, times, modes);
    }

    private void moveInLine(){
        float y = 0,x = 0;
        float m = (vector.y / vector.x);

        // Primer cuadrante
        if(m < 1 && m >= 0 && this.DIRECTION > 0){
            x = super.getSprite().getX() + this.SPEED * delta;
            y = (m * (x - this.initialPosition.x)) + this.initialPosition.y;
        }else if((m >= 1 || Double.isInfinite(m)) && this.DIRECTION > 0){
            y = super.getSprite().getY() + this.SPEED * delta;
            x = (y - initialPosition.y) / m + this.initialPosition.x;
        }

        // Segundo cuadrante
        else if((m <= -1 || Double.isInfinite(m))  && this.DIRECTION < 0){
            y = super.getSprite().getY() + this.SPEED * delta;
            x = (y - initialPosition.y) / m + this.initialPosition.x;
        }else if(m > -1 && m <= 0 && this.DIRECTION < 0){
            x = super.getSprite().getX() - this.SPEED * delta;
            y = (m * (x - this.initialPosition.x)) + this.initialPosition.y;
        }

        //Tercer cuadrante
        else if(m >= 0 && m < 1 && this.DIRECTION < 0){
            x = super.getSprite().getX() - this.SPEED * delta;
            y = (m * (x - this.initialPosition.x)) + this.initialPosition.y;
        }else if((m >= 1 || Double.isInfinite(m)) && this.DIRECTION < 0){
            y = super.getSprite().getY() - this.SPEED * delta;
            x = (y - initialPosition.y) / m + this.initialPosition.x;
        }

        // Cuarto cuadrante
        else if((m <= -1 || Double.isInfinite(m)) && this.DIRECTION > 0){
            y = super.getSprite().getY() - this.SPEED * delta;
            x = (y - initialPosition.y) / m + this.initialPosition.x;
        }else if(m > - 1 && m <= 0 && this.DIRECTION > 0){
            x = super.getSprite().getX() + this.SPEED * delta;
            y = (m * (x - this.initialPosition.x)) + this.initialPosition.y;
        }

        super.getSprite().setY(y);
        super.getSprite().setX(x);
        this.update();
    }


    @Override
    public void move() {
        if(!this.isStartDestroyAnimation())
            this.moveInLine();
    }

    @Override
    public void interact(Player player) {

    }

    @Override
    public void interact(GhostPlayer ghostPlayer) {
        if(super.getCollider().overlaps(ghostPlayer.getCollider())){
            if(ghostPlayer.getLife() > 0) {
                super.setReadyToDestroy(true);
                ghostPlayer.setLife(ghostPlayer.getLife() - 1);
            }
        }
    }

    @Override
    public void interact(Bullet bullet) {

    }

    @Override
    public void interact(List<Tile> tile) {
        for(Tile t : tile)
            if(t.getCollider() != null)
                if(super.getCollider().overlaps(t.getCollider()))
                    this.setStartDestroyAnimation(true);
    }

    @Override
    public void update() {
        super.getCollider().x = super.getSprite().getX() + GunBullet.WH / 4f;
        super.getCollider().y = super.getSprite().getY() + GunBullet.WH / 4f;
    }

    @Override
    public void render(SpriteBatch batch) {
        if(!this.isStartDestroyAnimation()) {
            super.getSprite().rotate(20f);
            super.getSprite().draw(batch);
        } else{
            this.controller.play(batch, GunBullet.WH);
            if(this.controller.isAnimationFinished())
                super.setReadyToDestroy(true);
        }
    }

    @Override
    public void debug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(super.getCollider().x, super.getCollider().y, super.getCollider().width, super.getCollider().height);
    }

    @Override
    public void dispose() {
        super.getSprite().getTexture().dispose();
        this.controller.dispose();
    }
}
