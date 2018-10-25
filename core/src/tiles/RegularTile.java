package tiles;

import characters.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class RegularTile extends Tile {

    public RegularTile(int id) {
        super(id);
    }

    public RegularTile(int id, Vector2 position, Vector2 size) {
        super(id, position, size);
    }

    @Override
    public void interactWithPlayer(Player player) {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {
        super.getSprite().draw(batch);
    }

    @Override
    public void debug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(super.getCollider().x, super.getCollider().y, super.getCollider().width, super.getCollider().height);
    }
}
