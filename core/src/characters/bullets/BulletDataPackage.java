package characters.bullets;

import com.badlogic.gdx.math.Vector2;
import general.IDs;

public class BulletDataPackage {
    private String player;
    private int id;
    private IDs.CharacterType characterType;
    private Vector2 position;

    public BulletDataPackage(IDs.CharacterType characterType, String player, int id, Vector2 position){
        this.characterType = characterType;
        this.player = player;
        this.id = id;
        this.position = position;
    }

    public IDs.CharacterType getCharacterType() {
        return characterType;
    }

    public int getId() {
        return id;
    }

    public String getPlayer() {
        return player;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(Vector2 position){
        this.position.set(position.x, position.y);
    }
}
