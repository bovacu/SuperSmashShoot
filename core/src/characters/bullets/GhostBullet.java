package characters.bullets;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import general.Converter;
import general.DataManager;
import general.IDs;

public class GhostBullet extends Sprite {

    private String player;
    private int id;
    private IDs.CharacterType characterType;

    private Vector2 lastPosition;
    private int timesSamePosition;

    private Rectangle collider;
    private Sound shot;

    public GhostBullet(IDs.CharacterType characterType, String player, int id, Vector2 position){
        super(Converter.characterTypeToGhostBulletSprite(characterType));
        if(characterType == IDs.CharacterType.SOLDIER)
            super.setSize(GunBullet.WH, GunBullet.WH);

        if(characterType == IDs.CharacterType.KNIGHT || characterType == IDs.CharacterType.CLOWN)
            this.shot = Converter.idToSound(IDs.THROW_EFFECT);
        else if(characterType == IDs.CharacterType.PIRATE)
            this.shot = Converter.idToSound(IDs.PISTOL_EFFECT);
        else if(characterType == IDs.CharacterType.SOLDIER)
            this.shot = Converter.idToSound(IDs.SHOT_EFFECT);

        this.shot.play(DataManager.effects / 100f);

        this.collider = Converter.getGhostBulletCollider(this);
        this.player = player;
        this.id = id;
        super.setPosition(position.x, position.y);
        this.characterType = characterType;
        this.lastPosition = new Vector2(0, 0);
        this.timesSamePosition = 0;
    }

    public String getPlayer(){
        return this.player;
    }

    public int getId(){
        return this.id;
    }

    public void update(Vector2 position){
        lastPosition.set(super.getX(), super.getY());
        super.setPosition(position.x, position.y);
        this.collider.x = position.x;
        this.collider.y = position.y;

        if(lastPosition.x == super.getX() && lastPosition.y == super.getY())
            this.timesSamePosition++;
        else
            this.timesSamePosition = 0;
    }

    public void render(SpriteBatch batch){
        if(this.timesSamePosition < 6){
            if(characterType == IDs.CharacterType.CLOWN || characterType == IDs.CharacterType.KNIGHT)
                super.rotate(20f);
            super.draw(batch);
        }
    }

    public void debug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(this.collider.x, this.collider.y, this.collider.width, this.collider.height);
    }

    public void dispose(){
        super.getTexture().dispose();
        this.shot.dispose();
    }
}
