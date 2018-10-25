package general;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    private Sprite sprite;
    private int id;

    public GameObject(int id){
        this.id = id;
        this.sprite = Converter.idToSprite(id);
    }

    public GameObject(int id, Vector2 position, Vector2 size){
        this.id = id;
        this.sprite = Converter.idToSprite(id);
        this.sprite.setSize(size.x, size.y);
        this.sprite.setPosition(position.x, position.y);
    }

    public int getId(){
        return this.id;
    }

    public Sprite getSprite(){
        return this.sprite;
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public void setPosition(Vector2 position){
        this.sprite.setPosition(position.x, position.y);
    }

    public Vector2 getPosition(){
        return new Vector2(this.sprite.getX(), this.sprite.getY());
    }

    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public abstract void debug(ShapeRenderer shapeRenderer);
    public abstract void dispose();
}
