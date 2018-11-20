package maps;

import characters.bullets.Bullet;
import characters.players.Player;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.SuperSmashShoot;

import java.util.List;

public class MapBorders {
    private Rectangle limits[];

    public MapBorders(){
        this.limits = new Rectangle[4];
        this.limits[0] = new Rectangle(0, -50, SuperSmashShoot.SCREEN_WIDTH, 50);
        this.limits[1] = new Rectangle(0, SuperSmashShoot.SCREEN_HEIGHT, SuperSmashShoot.SCREEN_WIDTH, 50);
        this.limits[2] = new Rectangle(-50, 0, 50, SuperSmashShoot.SCREEN_HEIGHT);
        this.limits[3] = new Rectangle(SuperSmashShoot.SCREEN_WIDTH, 0, 50, SuperSmashShoot.SCREEN_HEIGHT);
    }

    public void interactWithBullets(List<Bullet> bullets){
        for(Bullet b : bullets){
            for(Rectangle limit : this.limits){
                if(b.getCollider().overlaps(limit))
                    b.setReadyToDestroy(true);
            }
        }
    }

    public void interactWithPlayer(Player player){
        for(int i = 0; i < 4; i++){
            for(Rectangle playerCollider : player.getColliders()){
                if (playerCollider.overlaps(this.limits[i])){
                    if(i == 0){
                        player.getSprite().setPosition(player.getSprite().getX(), SuperSmashShoot.SCREEN_HEIGHT);
                        player.update();
                    } else if(i == 2){
                        player.getSprite().setPosition(SuperSmashShoot.SCREEN_WIDTH - Player.PLAYER_WH - 10, player.getSprite().getY());
                        player.update();
                    } else if(i == 3){
                        player.getSprite().setPosition(0 , player.getSprite().getY());
                        player.update();
                    }
                }
            }
        }
    }
}
