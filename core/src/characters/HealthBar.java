package characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import general.Converter;
import general.IDs;

import java.awt.*;

public class HealthBar {

    private Sprite states[];
    private Player player;
    private GhostPlayer ghostPlayer;

    private HealthBar(){
        this.states = new Sprite[6];
        for(int i = 0; i < this.states.length; i++){
            this.states[i] = Converter.idToSprite(IDs.HEALTH_BAR_1 + i);
            this.states[i].setSize(64, 16);
        }
    }

    public HealthBar(Player player){
        this();
        this.player = player;
        this.ghostPlayer = null;
    }

    public HealthBar(GhostPlayer ghostPlayer){
        this();
        this.ghostPlayer = ghostPlayer;
        this.player = null;
    }

    public Vector2 getPosition(){
        return new Vector2(this.states[0].getX(), this.states[0].getY());
    }

    public Dimension getSize(){
        return new Dimension((int)this.states[0].getWidth(), (int)this.states[0].getHeight());
    }

    public void render(SpriteBatch batch){
        if(this.player != null){
            this.states[(this.states.length - 1) - this.player.getLife()].setPosition(player.getPosition().x,
                    player.getPosition().y + Player.PLAYER_WH - 15);
            this.states[(this.states.length - 1) - this.player.getLife()].draw(batch);
        }else{
            this.states[(this.states.length - 1) - this.ghostPlayer.getLife()].setPosition(ghostPlayer.getPosition().x,
                    ghostPlayer.getPosition().y + Player.PLAYER_WH - 15);
            this.states[(this.states.length - 1) - this.ghostPlayer.getLife()].draw(batch);
        }
    }
}
