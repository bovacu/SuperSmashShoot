package tiles;

import characters.players.Player;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import general.Converter;
import general.GameObject;

public abstract class Tile extends GameObject {
    public static final int TILE_WH = 64;

    private Rectangle collider;

    public Tile(int id) {
        super(id);
        super.getSprite().setSize(Tile.TILE_WH, Tile.TILE_WH);
        this.collider = Converter.gameObjectToCollider(this);
    }

    public Tile(int id, Vector2 position, Vector2 size) {
        super(id, position, size);
        this.collider = Converter.gameObjectToCollider(this);
    }

    public Rectangle getCollider(){
        return this.collider;
    }

    @Override
    public void dispose() {
        super.getSprite().getTexture().dispose();
    }

    public abstract void interactWithPlayer(Player player);
}
