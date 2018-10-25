package characters.bullets;

import characters.Player;
import com.badlogic.gdx.math.Rectangle;
import general.Converter;
import general.GameObject;
import tiles.Tile;

import java.util.List;

public abstract class Bullet extends GameObject {
    private Rectangle collider;
    private Player player;
    private boolean readyToDestroy;
    private boolean startDestroyAnimation;

    public Bullet(int id, Player player) {
        super(id);
        this.player = player;
        this.collider = Converter.gameObjectToCollider(this);
        this.readyToDestroy = false;
        this.startDestroyAnimation = false;
    }

    public Player getPlayer(){
        return this.player;
    }

    public void setReadyToDestroy(boolean destroy){
        this.readyToDestroy = destroy;
    }

    public boolean isReadyToDestroy(){
        return this.readyToDestroy;
    }

    public void setStartDestroyAnimation(boolean start){
        this.startDestroyAnimation = start;
    }

    public boolean isStartDestroyAnimation(){
        return this.startDestroyAnimation;
    }

    public Rectangle getCollider(){
        return this.collider;
    }

    public abstract void move();
    public abstract void interact(Player player);
    public abstract void interact(Bullet bullet);
    public abstract void interact(List<Tile> tile);
}
