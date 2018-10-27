package general;

import characters.bullets.GunBullet;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Converter {

    private static final String MAP_1_TILES     = "graphics/map1Tiles/";
    private static final String SOLDIER         = "graphics/soldier/";
    private static final String CLOWN1          = "graphics/clownHammer1/";
    private static final String KNIGHT1         = "graphics/knightAxe/";
    private static final String PIRATE1         = "graphics/gunPirate/";
    private static final String UI              = "graphics/ui/";

    public static Sprite idToSprite(int id){
        switch (id){
            case IDs.BASIC1 :                   return new Sprite(new Texture(MAP_1_TILES + "Basic.png"));
            case IDs.BASIC2 :                   return new Sprite(new Texture(MAP_1_TILES + "Basic2.png"));
            case IDs.BASIC3 :                   return new Sprite(new Texture(MAP_1_TILES + "Basic3.png"));
            case IDs.BASIC4 :                   return new Sprite(new Texture(MAP_1_TILES + "Basic4.png"));
            case IDs.DOWN1 :                    return new Sprite(new Texture(MAP_1_TILES + "Down1.png"));
            case IDs.LEFT1 :                    return new Sprite(new Texture(MAP_1_TILES + "Left1.png"));
            case IDs.LEFT2 :                    return new Sprite(new Texture(MAP_1_TILES + "Left2.png"));
            case IDs.LEFT3 :                    return new Sprite(new Texture(MAP_1_TILES + "Left3.png"));
            case IDs.RIGHT1 :                   return new Sprite(new Texture(MAP_1_TILES + "Right1.png"));
            case IDs.RIGHT2 :                   return new Sprite(new Texture(MAP_1_TILES + "Right2.png"));
            case IDs.RIGHT3 :                   return new Sprite(new Texture(MAP_1_TILES + "Right3.png"));
            case IDs.RIGHT4 :                   return new Sprite(new Texture(MAP_1_TILES + "Right4.png"));
            case IDs.TOP1 :                     return new Sprite(new Texture(MAP_1_TILES + "Top1.png"));
            case IDs.TOP2 :                     return new Sprite(new Texture(MAP_1_TILES + "Top2.png"));
            case IDs.TOP3 :                     return new Sprite(new Texture(MAP_1_TILES + "Top3.png"));
            case IDs.TOP4 :                     return new Sprite(new Texture(MAP_1_TILES + "Top4.png"));
            case IDs.TOP5 :                     return new Sprite(new Texture(MAP_1_TILES + "Top5.png"));
            case IDs.TOP6 :                     return new Sprite(new Texture(MAP_1_TILES + "Top6.png"));
            case IDs.TOP7 :                     return new Sprite(new Texture(MAP_1_TILES + "Top7.png"));
            case IDs.TOP8 :                     return new Sprite(new Texture(MAP_1_TILES + "Top8.png"));
            case IDs.TOP9 :                     return new Sprite(new Texture(MAP_1_TILES + "Top9.png"));
            case IDs.TOP10 :                    return new Sprite(new Texture(MAP_1_TILES + "Top10.png"));
            case IDs.TOP11 :                    return new Sprite(new Texture(MAP_1_TILES + "Top11.png"));

            case IDs.SOLDIER_IDLE :             return new Sprite(new Texture(SOLDIER + "idle/idle.png"));
            case IDs.SOLDIER_WALKING :          return new Sprite(new Texture(SOLDIER + "walking/walking.png"));
            case IDs.SOLDIER_SHOOTING :         return new Sprite(new Texture(SOLDIER + "shooting/shooting.png"));
            case IDs.SOLDIER_WALKING_SHOOTING : return new Sprite(new Texture(SOLDIER + "walkingShooting/walkingShooting"));
            case IDs.SOLDIER_DYING :            return new Sprite(new Texture(SOLDIER + "dying/dying.png"));

            case IDs.CLOWN_1_IDLE :             return new Sprite(new Texture(CLOWN1 + "idle/idle.png"));
            case IDs.CLOWN_1_WALKING :          return new Sprite(new Texture(CLOWN1 + "walking/walking.png"));
            case IDs.CLOWN_1_SHOOTING :         return new Sprite(new Texture(CLOWN1 + "shooting/shooting.png"));
            case IDs.CLOWN_1_DYING :            return new Sprite(new Texture(CLOWN1 + "dying/dying.png"));

            case IDs.KNIGHT_1_IDLE :            return new Sprite(new Texture(KNIGHT1 + "idle/idle.png"));
            case IDs.KNIGHT_1_WALKING :         return new Sprite(new Texture(KNIGHT1 + "walking/walking.png"));
            case IDs.KNIGHT_1_SHOOTING :        return new Sprite(new Texture(KNIGHT1 + "shooting/shooting.png"));
            case IDs.KNIGHT_1_DYING :           return new Sprite(new Texture(KNIGHT1 + "dying/dying.png"));

            case IDs.PIRATE_1_IDLE :            return new Sprite(new Texture(PIRATE1 + "idle/idle.png"));
            case IDs.PIRATE_1_WALKING :         return new Sprite(new Texture(PIRATE1 + "walking/walking.png"));
            case IDs.PIRATE_1_SHOOTING :        return new Sprite(new Texture(PIRATE1 + "shooting/shooting.png"));
            case IDs.PIRATE_1_DYING :           return new Sprite(new Texture(PIRATE1 + "dying/dying.png"));

            case IDs.GUN_BULLET :               return new Sprite(new Texture(SOLDIER + "bullet/bullet.png"));
            case IDs.GUN_BULLET_DESTROYED :     return new Sprite(new Texture(SOLDIER + "bullet/bulletDestroyed.png"));
            case IDs.HAMMER :                   return new Sprite(new Texture(CLOWN1 + "hammer/hammer.png"));
            case IDs.AXE :                      return new Sprite(new Texture(KNIGHT1 + "axe/axe.png"));

            case IDs.LOCAL :                    return new Sprite(new Texture(UI + "local.png"));
            case IDs.MULTIPLAYER :              return new Sprite(new Texture(UI + "multiplayer.png"));
            case IDs.ACHIEVEMENTS :             return new Sprite(new Texture(UI + "achievements.png"));
            case IDs.CHAT :                     return new Sprite(new Texture(UI + "chat.png"));
            case IDs.MINUS_BUTTON :             return new Sprite(new Texture(UI + "minus.png"));
            case IDs.MUSIC_OFF :                return new Sprite(new Texture(UI + "musicOff.png"));
            case IDs.MUSIC_ON :                 return new Sprite(new Texture(UI + "musicOn.png"));
            case IDs.PLUS_BUTTON :              return new Sprite(new Texture(UI + "plus.png"));
            case IDs.QUIT :                     return new Sprite(new Texture(UI + "quit.png"));
            case IDs.SETTINGS :                 return new Sprite(new Texture(UI + "settings.png"));
            case IDs.STATS :                    return new Sprite(new Texture(UI + "stats.png"));
            case IDs.FRIENDS :                  return new Sprite(new Texture(UI + "friends.png"));

            case IDs.LOCAL_DOWN :               return new Sprite(new Texture(UI + "localDown.png"));
            case IDs.MULTIPLAYER_DOWN :         return new Sprite(new Texture(UI + "multiplayerDown.png"));
            case IDs.ACHIEVEMENTS_DOWN :        return new Sprite(new Texture(UI + "achievementsDown.png"));
            case IDs.CHAT_DOWN :                return new Sprite(new Texture(UI + "chatDown.png"));
            case IDs.MINUS_BUTTON_DOWN :        return new Sprite(new Texture(UI + "minusDown.png"));
            case IDs.MUSIC_OFF_DOWN :           return new Sprite(new Texture(UI + "musicOffDown.png"));
            case IDs.MUSIC_ON_DOWN :            return new Sprite(new Texture(UI + "musicOnDown.png"));
            case IDs.PLUS_BUTTON_DOWN :         return new Sprite(new Texture(UI + "plusDown.png"));
            case IDs.QUIT_DOWN :                return new Sprite(new Texture(UI + "quitDown.png"));
            case IDs.SETTINGS_DOWN :            return new Sprite(new Texture(UI + "settingsDown.png"));
            case IDs.STATS_DOWN :               return new Sprite(new Texture(UI + "statsDown.png"));
            case IDs.FRIENDS_DOWN :             return new Sprite(new Texture(UI + "friendsDown.png"));
            case IDs.NEXT :                     return new Sprite(new Texture(UI + "next.png"));
            case IDs.NEXT_DOWN :                return new Sprite(new Texture(UI + "nextDown.png"));
            case IDs.PREVIOUS :                 return new Sprite(new Texture(UI + "previous.png"));
            case IDs.PREVIOUS_DOWN :            return new Sprite(new Texture(UI + "previousDown.png"));
            case IDs.PAGED_LIST_BACK :          return new Sprite(new Texture(UI + "pagedListBack.png"));
            case IDs.TEXT_BOX :                 return new Sprite(new Texture(UI + "textBox.png"));
            case IDs.CHECK :                    return new Sprite(new Texture(UI + "check.png"));
            case IDs.CHECK_DOWN :               return new Sprite(new Texture(UI + "checkDown.png"));
            case IDs.CONNECT :                  return new Sprite(new Texture(UI + "connect.png"));
            case IDs.CONNECT_DOWN :             return new Sprite(new Texture(UI + "connectDown.png"));
            case IDs.CREATE_MATCH :             return new Sprite(new Texture(UI + "createMatch.png"));
            case IDs.CREATE_MATCH_DOWN :        return new Sprite(new Texture(UI + "createMatchDown.png"));
            case IDs.JOIN_MATCH :               return new Sprite(new Texture(UI + "joinMatch.png"));
            case IDs.JOIN_MATCH_DOWN :          return new Sprite(new Texture(UI + "joinMatchDown.png"));
            case IDs.REGISTER :                 return new Sprite(new Texture(UI + "register.png"));
            case IDs.REGISTER_DOWN :            return new Sprite(new Texture(UI + "registerDown.png"));
            case IDs.SEND :                     return new Sprite(new Texture(UI + "send.png"));
            case IDs.SEND_DOWN :                return new Sprite(new Texture(UI + "sendDown.png"));
            case IDs.GUN :                      return new Sprite(new Texture(SOLDIER + "gun.png"));

            case IDs.DEFAULT :                  return new Sprite();

            default :                           System.err.println("Sprite not found in Converter.idToSprite()"); return null;
        }
    }

    public static Rectangle gameObjectToCollider(GameObject gameObject){
        if(gameObject.getId() >= IDs.BASIC1 && gameObject.getId() <= IDs.TOP11)
            return new Rectangle((int)gameObject.getPosition().x, (int)gameObject.getPosition().y,
                    (int)gameObject.getSprite().getWidth(), (int)gameObject.getSprite().getHeight());

        else if(gameObject.getId() == IDs.SOLDIER_IDLE)
            return new Rectangle((int)gameObject.getPosition().x, (int)gameObject.getPosition().y,
                    (int)gameObject.getSprite().getWidth(), (int)gameObject.getSprite().getHeight());

        else if(gameObject.getId() == IDs.GUN_BULLET)
            return new Rectangle((int)gameObject.getPosition().x + GunBullet.WH / 2f,
                    (int)gameObject.getPosition().y + GunBullet.WH / 2f,
                    (int)gameObject.getSprite().getWidth() / 2f,
                    (int)gameObject.getSprite().getHeight() / 2f);

        else
            return null;
    }

    public static Rectangle[] gameObjectToPlayerCollider(GameObject gameObject){
        Rectangle colliders[];
        if(gameObject.getId() == IDs.SOLDIER_IDLE) {
            Rectangle c0 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 4f,
                    (int) gameObject.getPosition().y,
                    (int) gameObject.getSprite().getWidth() / 2f, (int) gameObject.getSprite().getHeight() / 5f);

            Rectangle c1 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 4f,
                    (int) gameObject.getPosition().y + gameObject.getSprite().getHeight(),
                    (int) gameObject.getSprite().getWidth() / 2f, (int) gameObject.getSprite().getHeight() / 5f);

            Rectangle c2 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 3.5f,
                    (int) gameObject.getPosition().y,
                    (int) gameObject.getSprite().getWidth() / 5f, (int) gameObject.getSprite().getHeight() / 2f);

            Rectangle c3 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 3.5f +
                    gameObject.getSprite().getWidth(), (int) gameObject.getPosition().y,
                    (int) gameObject.getSprite().getWidth() / 5f, (int) gameObject.getSprite().getHeight() / 2f);

            colliders = new Rectangle[4];
            colliders[0] = c0;
            colliders[1] = c1;
            colliders[2] = c2;
            colliders[3] = c3;
        }

        else if(gameObject.getId() == IDs.CLOWN_1_IDLE) {
            Rectangle c0 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 4f,
                    (int) gameObject.getPosition().y,
                    (int) gameObject.getSprite().getWidth() / 2f, (int) gameObject.getSprite().getHeight() / 5f);

            Rectangle c1 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 4f,
                    (int) gameObject.getPosition().y + gameObject.getSprite().getHeight(),
                    (int) gameObject.getSprite().getWidth() / 2f, (int) gameObject.getSprite().getHeight() / 5f);

            Rectangle c2 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 3.5f,
                    (int) gameObject.getPosition().y,
                    (int) gameObject.getSprite().getWidth() / 5f, (int) gameObject.getSprite().getHeight() / 2f);

            Rectangle c3 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 3.5f +
                    gameObject.getSprite().getWidth(), (int) gameObject.getPosition().y,
                    (int) gameObject.getSprite().getWidth() / 5f, (int) gameObject.getSprite().getHeight() / 2f);

            colliders = new Rectangle[4];
            colliders[0] = c0;
            colliders[1] = c1;
            colliders[2] = c2;
            colliders[3] = c3;
        }

        else if(gameObject.getId() == IDs.KNIGHT_1_IDLE) {
            Rectangle c0 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 4f,
                    (int) gameObject.getPosition().y,
                    (int) gameObject.getSprite().getWidth() / 2f, (int) gameObject.getSprite().getHeight() / 5f);

            Rectangle c1 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 4f,
                    (int) gameObject.getPosition().y + gameObject.getSprite().getHeight(),
                    (int) gameObject.getSprite().getWidth() / 2f, (int) gameObject.getSprite().getHeight() / 5f);

            Rectangle c2 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 3.5f,
                    (int) gameObject.getPosition().y,
                    (int) gameObject.getSprite().getWidth() / 5f, (int) gameObject.getSprite().getHeight() / 2f);

            Rectangle c3 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 3.5f +
                    gameObject.getSprite().getWidth(), (int) gameObject.getPosition().y,
                    (int) gameObject.getSprite().getWidth() / 5f, (int) gameObject.getSprite().getHeight() / 2f);

            colliders = new Rectangle[4];
            colliders[0] = c0;
            colliders[1] = c1;
            colliders[2] = c2;
            colliders[3] = c3;
        }

        else if(gameObject.getId() == IDs.PIRATE_1_IDLE) {
            Rectangle c0 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 4f,
                    (int) gameObject.getPosition().y,
                    (int) gameObject.getSprite().getWidth() / 2f, (int) gameObject.getSprite().getHeight() / 5f);

            Rectangle c1 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 4f,
                    (int) gameObject.getPosition().y + gameObject.getSprite().getHeight(),
                    (int) gameObject.getSprite().getWidth() / 2f, (int) gameObject.getSprite().getHeight() / 5f);

            Rectangle c2 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 3.5f,
                    (int) gameObject.getPosition().y,
                    (int) gameObject.getSprite().getWidth() / 5f, (int) gameObject.getSprite().getHeight() / 2f);

            Rectangle c3 = new Rectangle((int) gameObject.getPosition().x + gameObject.getSprite().getWidth() / 3.5f +
                    gameObject.getSprite().getWidth(), (int) gameObject.getPosition().y,
                    (int) gameObject.getSprite().getWidth() / 5f, (int) gameObject.getSprite().getHeight() / 2f);

            colliders = new Rectangle[4];
            colliders[0] = c0;
            colliders[1] = c1;
            colliders[2] = c2;
            colliders[3] = c3;
        }

        else
            colliders = null;
        return colliders;
    }
}
