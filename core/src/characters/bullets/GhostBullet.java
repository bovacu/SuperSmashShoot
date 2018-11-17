package characters.bullets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import general.Converter;
import general.IDs;

public class GhostBullet extends Sprite {

    private String player;
    private int id;
    private IDs.CharacterType characterType;
    private boolean readyToDestroy;

    private Vector2 position;

    private Rectangle collider;

    public GhostBullet(IDs.CharacterType characterType, String player, int id, Vector2 position){
        super(Converter.characterTypeToGhostBulletSprite(characterType));
        super.setSize(GunBullet.WH, GunBullet.WH);
        this.collider = Converter.getGhostBulletCollider(this);
        this.player = player;
        this.id = id;
        this.position = position;
        this.characterType = characterType;
        this.readyToDestroy = false;
    }

    public String getPlayer(){
        return this.player;
    }

    public int getId(){
        return this.id;
    }

    public void update(Vector2 position){
        super.setPosition(position.x, position.y);
        this.collider.x = position.x;
        this.collider.y = position.y;
    }

    public void render(SpriteBatch batch){
        super.draw(batch);
    }

    public void debug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(this.collider.x, this.collider.y, this.collider.width, this.collider.height);
    }

    public void dispose(){
        super.getTexture().dispose();
    }
}
