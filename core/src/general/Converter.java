package general;

import characters.Player;
import characters.bullets.GunBullet;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Converter {

    private static final String MAP_1_TILES     = "graphics/maps/map1Tiles/";
    private static final String MAP_2_TILES     = "graphics/maps/map2Tiles/";
    private static final String MAP_3_TILES     = "graphics/maps/map3Tiles/";
    private static final String SOLDIER         = "graphics/characters/soldier/";
    private static final String CLOWN1          = "graphics/characters/clown/";
    private static final String KNIGHT1         = "graphics/characters/knight/";
    private static final String PIRATE1         = "graphics/characters/pirate/";
    private static final String UI              = "graphics/ui/";
    private static final String CC              = "graphics/screens/characterSelector/";
    private static final String CM              = "graphics/screens/mapSelector/";
    private static final String HEALTH_BAR      = "graphics/healthBar/";

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

            case IDs.SOLDIER_1_IDLE:             return new Sprite(new Texture(SOLDIER + "idle/idle.png"));
            case IDs.SOLDIER_1_WALKING:          return new Sprite(new Texture(SOLDIER + "walking/walking.png"));
            case IDs.SOLDIER_1_DYING:            return new Sprite(new Texture(SOLDIER + "dying/dying.png"));

            case IDs.SOLDIER_2_IDLE:             return new Sprite(new Texture(SOLDIER + "idle/idle2.png"));
            case IDs.SOLDIER_2_WALKING:          return new Sprite(new Texture(SOLDIER + "walking/walking2.png"));
            case IDs.SOLDIER_2_DYING:            return new Sprite(new Texture(SOLDIER + "dying/dying2.png"));

            case IDs.SOLDIER_3_IDLE:             return new Sprite(new Texture(SOLDIER + "idle/idle3.png"));
            case IDs.SOLDIER_3_WALKING:          return new Sprite(new Texture(SOLDIER + "walking/walking3.png"));
            case IDs.SOLDIER_3_DYING:            return new Sprite(new Texture(SOLDIER + "dying/dying3.png"));

            case IDs.CLOWN_1_IDLE :             return new Sprite(new Texture(CLOWN1 + "idle/idle.png"));
            case IDs.CLOWN_1_WALKING :          return new Sprite(new Texture(CLOWN1 + "walking/walking.png"));
            case IDs.CLOWN_1_DYING :            return new Sprite(new Texture(CLOWN1 + "dying/dying.png"));

            case IDs.CLOWN_2_IDLE :             return new Sprite(new Texture(CLOWN1 + "idle/idle2.png"));
            case IDs.CLOWN_2_WALKING :          return new Sprite(new Texture(CLOWN1 + "walking/walking2.png"));
            case IDs.CLOWN_2_DYING :            return new Sprite(new Texture(CLOWN1 + "dying/dying2.png"));

            case IDs.CLOWN_3_IDLE :             return new Sprite(new Texture(CLOWN1 + "idle/idle3.png"));
            case IDs.CLOWN_3_WALKING :          return new Sprite(new Texture(CLOWN1 + "walking/walking3.png"));
            case IDs.CLOWN_3_DYING :            return new Sprite(new Texture(CLOWN1 + "dying/dying3.png"));

            case IDs.KNIGHT_1_IDLE :            return new Sprite(new Texture(KNIGHT1 + "idle/idle.png"));
            case IDs.KNIGHT_1_WALKING :         return new Sprite(new Texture(KNIGHT1 + "walking/walking.png"));
            case IDs.KNIGHT_1_DYING :           return new Sprite(new Texture(KNIGHT1 + "dying/dying.png"));

            case IDs.KNIGHT_2_IDLE :            return new Sprite(new Texture(KNIGHT1 + "idle/idle2.png"));
            case IDs.KNIGHT_2_WALKING :         return new Sprite(new Texture(KNIGHT1 + "walking/walking2.png"));
            case IDs.KNIGHT_2_DYING :           return new Sprite(new Texture(KNIGHT1 + "dying/dying2.png"));

            case IDs.KNIGHT_3_IDLE :            return new Sprite(new Texture(KNIGHT1 + "idle/idle3.png"));
            case IDs.KNIGHT_3_WALKING :         return new Sprite(new Texture(KNIGHT1 + "walking/walking3.png"));
            case IDs.KNIGHT_3_DYING :           return new Sprite(new Texture(KNIGHT1 + "dying/dying3.png"));

            case IDs.PIRATE_1_IDLE :            return new Sprite(new Texture(PIRATE1 + "idle/idle.png"));
            case IDs.PIRATE_1_WALKING :         return new Sprite(new Texture(PIRATE1 + "walking/walking.png"));
            case IDs.PIRATE_1_DYING :           return new Sprite(new Texture(PIRATE1 + "dying/dying.png"));

            case IDs.GUN_BULLET :               return new Sprite(new Texture(SOLDIER + "bullet/bullet.png"));
            case IDs.GUN_BULLET_DESTROYED :     return new Sprite(new Texture(SOLDIER + "bullet/bulletDestroyed.png"));
            case IDs.HAMMER :                   return new Sprite(new Texture(CLOWN1 + "hammer/hammer.png"));
            case IDs.AXE :                      return new Sprite(new Texture(KNIGHT1 + "axe/axe.png"));

            case IDs.LOCAL :                    return new Sprite(new Texture(UI + "local.png"));
            case IDs.GRAY_BUTTON_UP:              return new Sprite(new Texture(UI + "grey_11.png"));
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
            case IDs.GRAY_BUTTON_DOWN:         return new Sprite(new Texture(UI + "pressed_11.png"));
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
            case IDs.LEAVE :                    return new Sprite(new Texture(UI + "leave.png"));
            case IDs.LEAVE_DOWN :               return new Sprite(new Texture(UI + "leaveDown.png"));
            case IDs.CLOSE :                    return new Sprite(new Texture(UI + "close.png"));
            case IDs.CLOSE_DOWN :               return new Sprite(new Texture(UI + "closeDown.png"));
            case IDs.WIFI :                     return new Sprite(new Texture(UI + "wifi.png"));
            case IDs.WIFI_DOWN :                return new Sprite(new Texture(UI + "wifiDown.png"));
            case IDs.MESSAGE_BACKGROUND :       return new Sprite(new Texture(UI + "messageBackground.png"));
            case IDs.CHAT_BACKGROUND :          return new Sprite(new Texture(UI + "pagedListBack.png"));
            case IDs.SAVE :                     return new Sprite(new Texture(UI + "save.png"));
            case IDs.SAVE_DOWN :                return new Sprite(new Texture(UI + "saveDown.png"));
            case IDs.BACKGROUND1 :              return new Sprite(new Texture(UI + "background1.png"));

            case IDs.PORT_BACKGROUND :          return new Sprite(new Texture(MAP_2_TILES + "background.png"));
            case IDs.PORT_FLOOR_LEFT :          return new Sprite(new Texture(MAP_2_TILES + "leftCornerFloor.png"));
            case IDs.PORT_FLOOR_MIDDLE :        return new Sprite(new Texture(MAP_2_TILES + "middleFloor.png"));
            case IDs.PORT_FLOOR_RIGHT :         return new Sprite(new Texture(MAP_2_TILES + "rightCornerFloor.png"));
            case IDs.PORT_WOODEN_BOX :          return new Sprite(new Texture(MAP_2_TILES + "woodenBox.png"));
            case IDs.PORT_CARDBOARD_PACK_1 :    return new Sprite(new Texture(MAP_2_TILES + "cardboardPack1.png"));
            case IDs.PORT_CARDBOARD_PACK_2 :    return new Sprite(new Texture(MAP_2_TILES + "cardboardPack2.png"));
            case IDs.PORT_CARDBOARD_PACK_3 :    return new Sprite(new Texture(MAP_2_TILES + "cardboardPack3.png"));
            case IDs.PORT_CARDBOARD_PACK_4 :    return new Sprite(new Texture(MAP_2_TILES + "cardboardPack4.png"));
            case IDs.PORT_FENCE_1 :             return new Sprite(new Texture(MAP_2_TILES + "fence1.png"));
            case IDs.PORT_FENCE_2 :             return new Sprite(new Texture(MAP_2_TILES + "fence2.png"));
            case IDs.PORT_FENCE_3 :             return new Sprite(new Texture(MAP_2_TILES + "fence3.png"));
            case IDs.PORT_FENCE_4 :             return new Sprite(new Texture(MAP_2_TILES + "fence4.png"));
            case IDs.PORT_FENCE_5 :             return new Sprite(new Texture(MAP_2_TILES + "fence5.png"));
            case IDs.PORT_FENCE_6 :             return new Sprite(new Texture(MAP_2_TILES + "fence6.png"));
            case IDs.PORT_FENCE_7 :             return new Sprite(new Texture(MAP_2_TILES + "fence7.png"));
            case IDs.PORT_FENCE_8 :             return new Sprite(new Texture(MAP_2_TILES + "fence8.png"));
            case IDs.PORT_FENCE_9 :             return new Sprite(new Texture(MAP_2_TILES + "fence9.png"));
            case IDs.PORT_FENCE_10 :            return new Sprite(new Texture(MAP_2_TILES + "fence10.png"));
            case IDs.PORT_FENCE_11 :            return new Sprite(new Texture(MAP_2_TILES + "fence11.png"));
            case IDs.PORT_VCB_1 :               return new Sprite(new Texture(MAP_2_TILES + "vcb1.png"));
            case IDs.PORT_VCB_2 :               return new Sprite(new Texture(MAP_2_TILES + "vcb2.png"));
            case IDs.PORT_VCB_3 :               return new Sprite(new Texture(MAP_2_TILES + "vcb3.png"));
            case IDs.PORT_VCB_4 :               return new Sprite(new Texture(MAP_2_TILES + "vcb4.png"));
            case IDs.PORT_VCB_5 :               return new Sprite(new Texture(MAP_2_TILES + "vcb5.png"));
            case IDs.PORT_VCB_6 :               return new Sprite(new Texture(MAP_2_TILES + "vcb6.png"));
            case IDs.PORT_VCB_7 :               return new Sprite(new Texture(MAP_2_TILES + "vcb7.png"));
            case IDs.PORT_VCB_8 :               return new Sprite(new Texture(MAP_2_TILES + "vcb8.png"));
            case IDs.PORT_VCB_9 :               return new Sprite(new Texture(MAP_2_TILES + "vcb9.png"));
            case IDs.PORT_VCBR_1 :              return new Sprite(new Texture(MAP_2_TILES + "vcbr1.png"));
            case IDs.PORT_VCBR_2 :              return new Sprite(new Texture(MAP_2_TILES + "vcbr2.png"));
            case IDs.PORT_VCBR_3 :              return new Sprite(new Texture(MAP_2_TILES + "vcbr3.png"));
            case IDs.PORT_VCBR_4 :              return new Sprite(new Texture(MAP_2_TILES + "vcbr4.png"));
            case IDs.PORT_VCBR_5 :              return new Sprite(new Texture(MAP_2_TILES + "vcbr5.png"));
            case IDs.PORT_VCBR_6 :              return new Sprite(new Texture(MAP_2_TILES + "vcbr6.png"));
            case IDs.PORT_VCBR_7 :              return new Sprite(new Texture(MAP_2_TILES + "vcbr7.png"));
            case IDs.PORT_VCBR_8 :              return new Sprite(new Texture(MAP_2_TILES + "vcbr8.png"));
            case IDs.PORT_VCBR_9 :              return new Sprite(new Texture(MAP_2_TILES + "vcbr9.png"));
            case IDs.PORT_VCG_1 :               return new Sprite(new Texture(MAP_2_TILES + "vcg1.png"));
            case IDs.PORT_VCG_2 :               return new Sprite(new Texture(MAP_2_TILES + "vcg2.png"));
            case IDs.PORT_VCG_3 :               return new Sprite(new Texture(MAP_2_TILES + "vcg3.png"));
            case IDs.PORT_VCG_4 :               return new Sprite(new Texture(MAP_2_TILES + "vcg4.png"));
            case IDs.PORT_VCG_5 :               return new Sprite(new Texture(MAP_2_TILES + "vcg5.png"));
            case IDs.PORT_VCG_6 :               return new Sprite(new Texture(MAP_2_TILES + "vcg6.png"));
            case IDs.PORT_VCG_7 :               return new Sprite(new Texture(MAP_2_TILES + "vcg7.png"));
            case IDs.PORT_VCG_8 :               return new Sprite(new Texture(MAP_2_TILES + "vcg8.png"));
            case IDs.PORT_VCG_9 :               return new Sprite(new Texture(MAP_2_TILES + "vcg9.png"));
            case IDs.PORT_VCR_1 :               return new Sprite(new Texture(MAP_2_TILES + "hcr1.png"));
            case IDs.PORT_VCR_2 :               return new Sprite(new Texture(MAP_2_TILES + "hcr2.png"));
            case IDs.PORT_VCR_3 :               return new Sprite(new Texture(MAP_2_TILES + "hcr3.png"));
            case IDs.PORT_VCR_4 :               return new Sprite(new Texture(MAP_2_TILES + "hcr4.png"));
            case IDs.PORT_VCR_5 :               return new Sprite(new Texture(MAP_2_TILES + "hcr5.png"));
            case IDs.PORT_VCR_6 :               return new Sprite(new Texture(MAP_2_TILES + "hcr6.png"));
            case IDs.PORT_VCR_7 :               return new Sprite(new Texture(MAP_2_TILES + "hcr7.png"));
            case IDs.PORT_VCR_8 :               return new Sprite(new Texture(MAP_2_TILES + "hcr8.png"));
            case IDs.PORT_HCR_1 :               return new Sprite(new Texture(MAP_2_TILES + "vcr1.png"));
            case IDs.PORT_HCR_2 :               return new Sprite(new Texture(MAP_2_TILES + "vcr2.png"));
            case IDs.PORT_HCR_3 :               return new Sprite(new Texture(MAP_2_TILES + "vcr3.png"));
            case IDs.PORT_HCR_4 :               return new Sprite(new Texture(MAP_2_TILES + "vcr4.png"));
            case IDs.PORT_HCR_5 :               return new Sprite(new Texture(MAP_2_TILES + "vcr5.png"));
            case IDs.PORT_HCR_6 :               return new Sprite(new Texture(MAP_2_TILES + "vcr6.png"));
            case IDs.PORT_HCR_7 :               return new Sprite(new Texture(MAP_2_TILES + "vcr7.png"));
            case IDs.PORT_HCR_8 :               return new Sprite(new Texture(MAP_2_TILES + "vcr8.png"));
            case IDs.PORT_HCR_9 :               return new Sprite(new Texture(MAP_2_TILES + "vcr9.png"));
            case IDs.PORT_HCR_10 :              return new Sprite(new Texture(MAP_2_TILES + "vcr10.png"));
            case IDs.PORT_HCR_11 :              return new Sprite(new Texture(MAP_2_TILES + "vcr11.png"));
            case IDs.PORT_HCR_12 :              return new Sprite(new Texture(MAP_2_TILES + "vcr12.png"));
            case IDs.PORT_HCR_13 :              return new Sprite(new Texture(MAP_2_TILES + "vcr13.png"));
            case IDs.PORT_HCR_14 :              return new Sprite(new Texture(MAP_2_TILES + "vcr14.png"));
            case IDs.PORT_HCR_15 :              return new Sprite(new Texture(MAP_2_TILES + "vcr15.png"));
            case IDs.PORT_HCR_16 :              return new Sprite(new Texture(MAP_2_TILES + "vcr16.png"));
            case IDs.PORT_HCB_1 :               return new Sprite(new Texture(MAP_2_TILES + "hcb1.png"));
            case IDs.PORT_HCB_2 :               return new Sprite(new Texture(MAP_2_TILES + "hcb2.png"));
            case IDs.PORT_HCB_3 :               return new Sprite(new Texture(MAP_2_TILES + "hcb3.png"));
            case IDs.PORT_HCB_4 :               return new Sprite(new Texture(MAP_2_TILES + "hcb4.png"));
            case IDs.PORT_HCB_5 :               return new Sprite(new Texture(MAP_2_TILES + "hcb5.png"));
            case IDs.PORT_HCB_6 :               return new Sprite(new Texture(MAP_2_TILES + "hcb6.png"));
            case IDs.PORT_HCB_7 :               return new Sprite(new Texture(MAP_2_TILES + "hcb7.png"));
            case IDs.PORT_HCB_8 :               return new Sprite(new Texture(MAP_2_TILES + "hcb8.png"));
            case IDs.PORT_HCB_9 :               return new Sprite(new Texture(MAP_2_TILES + "hcb9.png"));
            case IDs.PORT_HCB_10 :              return new Sprite(new Texture(MAP_2_TILES + "hcb10.png"));
            case IDs.PORT_HCB_11 :              return new Sprite(new Texture(MAP_2_TILES + "hcb11.png"));
            case IDs.PORT_HCB_12 :              return new Sprite(new Texture(MAP_2_TILES + "hcb12.png"));
            case IDs.PORT_HCB_13 :              return new Sprite(new Texture(MAP_2_TILES + "hcb13.png"));
            case IDs.PORT_HCB_14 :              return new Sprite(new Texture(MAP_2_TILES + "hcb14.png"));
            case IDs.PORT_HCB_15 :              return new Sprite(new Texture(MAP_2_TILES + "hcb15.png"));
            case IDs.PORT_HCB_16 :              return new Sprite(new Texture(MAP_2_TILES + "hcb16.png"));
            case IDs.PORT_HCB_17 :              return new Sprite(new Texture(MAP_2_TILES + "hcb17.png"));
            case IDs.PORT_HCB_18 :              return new Sprite(new Texture(MAP_2_TILES + "hcb18.png"));
            case IDs.PORT_HCB_19 :              return new Sprite(new Texture(MAP_2_TILES + "hcb19.png"));
            case IDs.PORT_HCB_20 :              return new Sprite(new Texture(MAP_2_TILES + "hcb20.png"));
            case IDs.PORT_HCB_21 :              return new Sprite(new Texture(MAP_2_TILES + "hcb21.png"));
            case IDs.PORT_HCB_22 :              return new Sprite(new Texture(MAP_2_TILES + "hcb22.png"));
            case IDs.PORT_HCB_23 :              return new Sprite(new Texture(MAP_2_TILES + "hcb23.png"));
            case IDs.PORT_HCB_24 :              return new Sprite(new Texture(MAP_2_TILES + "hcb24.png"));

            case IDs.SKY_FLOOR_1 :              return new Sprite(new Texture(MAP_3_TILES + "floor1.png"));
            case IDs.SKY_FLOOR_2 :              return new Sprite(new Texture(MAP_3_TILES + "floor2.png"));
            case IDs.SKY_FLOOR_3 :              return new Sprite(new Texture(MAP_3_TILES + "floor3.png"));
            case IDs.SKY_FLOOR_4 :              return new Sprite(new Texture(MAP_3_TILES + "floor4.png"));
            case IDs.SKY_LOW_FLOOR_1 :          return new Sprite(new Texture(MAP_3_TILES + "lowFloor1.png"));
            case IDs.SKY_LOW_FLOOR_2 :          return new Sprite(new Texture(MAP_3_TILES + "lowFloor2.png"));
            case IDs.SKY_CORNER_1 :             return new Sprite(new Texture(MAP_3_TILES + "rightCorner.png"));
            case IDs.SKY_CORNER_2 :             return new Sprite(new Texture(MAP_3_TILES + "leftCorner.png"));
            case IDs.SKY_LOW_CORNER_1 :         return new Sprite(new Texture(MAP_3_TILES + "rightLowCorner.png"));
            case IDs.SKY_LOW_CORNER_2 :         return new Sprite(new Texture(MAP_3_TILES + "leftLowCorner.png"));
            case IDs.SKY_ROPE_1 :               return new Sprite(new Texture(MAP_3_TILES + "rope1.png"));
            case IDs.SKY_ROPE_2 :               return new Sprite(new Texture(MAP_3_TILES + "rope2.png"));
            case IDs.SKY_ROPE_3 :               return new Sprite(new Texture(MAP_3_TILES + "rope3.png"));
            case IDs.SKY_ROPE_4 :               return new Sprite(new Texture(MAP_3_TILES + "rope4.png"));
            case IDs.SKY_BIG_WOOD_1 :           return new Sprite(new Texture(MAP_3_TILES + "bigWood1.png"));
            case IDs.SKY_BIG_WOOD_2 :           return new Sprite(new Texture(MAP_3_TILES + "bigWood2.png"));
            case IDs.SKY_GRASS_1 :              return new Sprite(new Texture(MAP_3_TILES + "grass1.png"));
            case IDs.SKY_GRASS_2 :              return new Sprite(new Texture(MAP_3_TILES + "grass2.png"));
            case IDs.SKY_BRIDGE_FLOOR :         return new Sprite(new Texture(MAP_3_TILES + "bridgeFloor.png"));
            case IDs.SKY_WOOD_1 :               return new Sprite(new Texture(MAP_3_TILES + "wood1.png"));
            case IDs.SKY_WOOD_2 :               return new Sprite(new Texture(MAP_3_TILES + "wood2.png"));
            case IDs.SKY_WOOD_AND_ROPE_1 :      return new Sprite(new Texture(MAP_3_TILES + "woodAndRope1.png"));
            case IDs.SKY_WOOD_AND_ROPE_2 :      return new Sprite(new Texture(MAP_3_TILES + "woodAndRope2.png"));
            case IDs.SKY_BUSH_1 :               return new Sprite(new Texture(MAP_3_TILES + "bush1.png"));
            case IDs.SKY_BUSH_2 :               return new Sprite(new Texture(MAP_3_TILES + "bush2.png"));
            case IDs.SKY_BUSH_3 :               return new Sprite(new Texture(MAP_3_TILES + "bush3.png"));
            case IDs.SKY_BUSH_4 :               return new Sprite(new Texture(MAP_3_TILES + "bush4.png"));
            case IDs.SKY_TREE_1 :               return new Sprite(new Texture(MAP_3_TILES + "tree1.png"));
            case IDs.SKY_TREE_2 :               return new Sprite(new Texture(MAP_3_TILES + "tree2.png"));
            case IDs.SKY_TREE_3 :               return new Sprite(new Texture(MAP_3_TILES + "tree3.png"));
            case IDs.SKY_TREE_4 :               return new Sprite(new Texture(MAP_3_TILES + "tree4.png"));
            case IDs.SKY_TREE_5 :               return new Sprite(new Texture(MAP_3_TILES + "tree5.png"));
            case IDs.SKY_TREE_6 :               return new Sprite(new Texture(MAP_3_TILES + "tree6.png"));
            case IDs.SKY_TREE_7 :               return new Sprite(new Texture(MAP_3_TILES + "tree7.png"));
            case IDs.SKY_TREE_8 :               return new Sprite(new Texture(MAP_3_TILES + "tree8.png"));
            case IDs.SKY_TREE_9 :               return new Sprite(new Texture(MAP_3_TILES + "tree9.png"));
            case IDs.SKY_TREE_10 :              return new Sprite(new Texture(MAP_3_TILES + "tree10.png"));
            case IDs.SKY_TREE_11 :              return new Sprite(new Texture(MAP_3_TILES + "tree11.png"));
            case IDs.SKY_TREE_12 :              return new Sprite(new Texture(MAP_3_TILES + "tree12.png"));
            case IDs.SKY_TREE_13 :              return new Sprite(new Texture(MAP_3_TILES + "tree13.png"));
            case IDs.SKY_TREE_14 :              return new Sprite(new Texture(MAP_3_TILES + "tree14.png"));
            case IDs.SKY_BACKGROUND :           return new Sprite(new Texture(MAP_3_TILES + "background.png"));

            case IDs.CC_BACKGROUND :            return new Sprite(new Texture(CC + "background.png"));
            case IDs.CC_DECORATION :            return new Sprite(new Texture(CC + "decoration1.png"));
            case IDs.CC_CLOWN_PICTURE :         return new Sprite(new Texture(CC + "clownPicture.png"));
            case IDs.CC_PIRATE_PICTURE :        return new Sprite(new Texture(CC + "piratePicture.png"));
            case IDs.CC_KNIGHT_PICTURE :        return new Sprite(new Texture(CC + "knightPicture.png"));
            case IDs.CC_SOLDIER_PICTURE :       return new Sprite(new Texture(CC + "soldierPicture.png"));
            case IDs.CC_CLOWN_PICTURE_DOWN :    return new Sprite(new Texture(CC + "clownPictureDown.png"));
            case IDs.CC_PIRATE_PICTURE_DOWN :   return new Sprite(new Texture(CC + "piratePictureDown.png"));
            case IDs.CC_KNIGHT_PICTURE_DOWN :   return new Sprite(new Texture(CC + "knightPictureDown.png"));
            case IDs.CC_SOLDIER_PICTURE_DOWN :  return new Sprite(new Texture(CC + "soldierPictureDown.png"));
            case IDs.CC_PIRATE_1_WALKING :      return new Sprite(new Texture(CC + "pirate1Walking.png"));
            case IDs.CC_PIRATE_2_WALKING :      return new Sprite(new Texture(CC + "pirate2Walking.png"));
            case IDs.CC_PIRATE_3_WALKING :      return new Sprite(new Texture(CC + "pirate3Walking.png"));
            case IDs.CC_SOLDIER_1_WALKING :     return new Sprite(new Texture(CC + "soldier1Walking.png"));
            case IDs.CC_SOLDIER_2_WALKING :     return new Sprite(new Texture(CC + "soldier2Walking.png"));
            case IDs.CC_SOLDIER_3_WALKING :     return new Sprite(new Texture(CC + "soldier3Walking.png"));
            case IDs.CC_CLOWN_1_WALKING :       return new Sprite(new Texture(CC + "clown1Walking.png"));
            case IDs.CC_CLOWN_2_WALKING :       return new Sprite(new Texture(CC + "clown2Walking.png"));
            case IDs.CC_CLOWN_3_WALKING :       return new Sprite(new Texture(CC + "clown3Walking.png"));
            case IDs.CC_KNIGHT_1_WALKING:       return new Sprite(new Texture(CC + "knight1Walking.png"));
            case IDs.CC_KNIGHT_2_WALKING:       return new Sprite(new Texture(CC + "knight2Walking.png"));
            case IDs.CC_KNIGHT_3_WALKING:       return new Sprite(new Texture(CC + "knight3Walking.png"));

            case IDs.CM_PORT :                  return new Sprite(new Texture(CM + "portMap.png"));
            case IDs.CM_PORT_DOWN :             return new Sprite(new Texture(CM + "portMapDown.png"));
            case IDs.CM_SKY :                   return new Sprite(new Texture(CM + "skyMap.png"));
            case IDs.CM_SKY_DOWN :              return new Sprite(new Texture(CM + "skyMapDown.png"));

            case IDs.GUN :                      return new Sprite(new Texture(SOLDIER + "gun.png"));

            case IDs.HEALTH_BAR_1 :             return new Sprite(new Texture(HEALTH_BAR + "healthbar1.png"));
            case IDs.HEALTH_BAR_2 :             return new Sprite(new Texture(HEALTH_BAR + "healthbar2.png"));
            case IDs.HEALTH_BAR_3 :             return new Sprite(new Texture(HEALTH_BAR + "healthbar3.png"));
            case IDs.HEALTH_BAR_4 :             return new Sprite(new Texture(HEALTH_BAR + "healthbar4.png"));
            case IDs.HEALTH_BAR_5 :             return new Sprite(new Texture(HEALTH_BAR + "healthbar5.png"));
            case IDs.HEALTH_BAR_6 :             return new Sprite(new Texture(HEALTH_BAR + "healthbar6.png"));

            case IDs.DEFAULT :                  return new Sprite();

            default :                           System.err.println("Sprite not found in Converter.idToSprite()"); return null;
        }
    }

    public static Rectangle gameObjectToCollider(GameObject gameObject){
        if(gameObject.getId() >= IDs.BASIC1 && gameObject.getId() <= IDs.TOP11)
            return new Rectangle((int)gameObject.getPosition().x, (int)gameObject.getPosition().y,
                    (int)gameObject.getSprite().getWidth(), (int)gameObject.getSprite().getHeight());

        else if(gameObject.getId() == IDs.SOLDIER_1_IDLE) {
            return new Rectangle((int) gameObject.getPosition().x, (int) gameObject.getPosition().y,
                    (int) gameObject.getSprite().getWidth(), (int) gameObject.getSprite().getHeight());
        }

        else if(gameObject.getId() == IDs.GUN_BULLET)
            return new Rectangle((int)gameObject.getPosition().x + GunBullet.WH / 2f,
                    (int)gameObject.getPosition().y + GunBullet.WH / 2f,
                    (int)gameObject.getSprite().getWidth() / 2f,
                    (int)gameObject.getSprite().getHeight() / 2f);

        else if(gameObject.getId() >= IDs.PORT_FLOOR_LEFT && gameObject.getId() <= IDs.PORT_FLOOR_RIGHT ||
                gameObject.getId() >= IDs.PORT_VCB_1 && gameObject.getId() <= IDs.PORT_VCB_9 ||
                gameObject.getId() >= IDs.PORT_VCBR_1 && gameObject.getId() <= IDs.PORT_VCBR_9 ||
                gameObject.getId() >= IDs.PORT_VCG_1 && gameObject.getId() <= IDs.PORT_VCG_9 ||
                gameObject.getId() >= IDs.PORT_HCB_1 && gameObject.getId() <= IDs.PORT_HCB_24){

            return new Rectangle((int)gameObject.getPosition().x, (int)gameObject.getPosition().y,
                    (int)gameObject.getSprite().getWidth(), (int)gameObject.getSprite().getHeight());
        }

        else if(gameObject.getId() >= IDs.SKY_FLOOR_1 && gameObject.getId() <= IDs.SKY_LOW_FLOOR_2 ||
                gameObject.getId() == IDs.SKY_BRIDGE_FLOOR){

            return new Rectangle((int)gameObject.getPosition().x, (int)gameObject.getPosition().y,
                    (int)gameObject.getSprite().getWidth(), (int)gameObject.getSprite().getHeight());
        }

        else
            return null;
    }

    public static Rectangle ghostCollider(GameObject gameObject){
        return new Rectangle((int)gameObject.getPosition().x, (int)gameObject.getPosition().y,
                55, 55);
    }

    public static Sprite characterTypeToGhostBulletSprite(IDs.CharacterType characterType){
        switch (characterType){
            case CLOWN:         return new Sprite(new Texture(SOLDIER + "bullet/bullet.png"));
            case KNIGHT:        return new Sprite(new Texture(SOLDIER + "bullet/bullet.png"));
            case PIRATE:        return new Sprite(new Texture(SOLDIER + "bullet/bullet.png"));
            case SOLDIER:       return new Sprite(new Texture(SOLDIER + "bullet/bullet.png"));
        }

        return new Sprite();
    }

    public static Rectangle getGhostBulletCollider(Sprite gameObject){
        return new Rectangle((int)gameObject.getX() + GunBullet.WH / 2f,
                (int)gameObject.getY() + GunBullet.WH / 2f,
                (int)gameObject.getWidth() / 2f,
                (int)gameObject.getHeight() / 2f);
    }

    public static Rectangle[] gameObjectToPlayerCollider(GameObject gameObject){
        Rectangle colliders[];
        if(gameObject.getId() == IDs.SOLDIER_1_IDLE || gameObject.getId() == IDs.SOLDIER_2_IDLE || gameObject.getId() == IDs.SOLDIER_3_IDLE) {
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

        else if(gameObject.getId() == IDs.CLOWN_1_IDLE || gameObject.getId() == IDs.CLOWN_2_IDLE || gameObject.getId() == IDs.CLOWN_3_IDLE) {
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

        else if(gameObject.getId() == IDs.KNIGHT_1_IDLE || gameObject.getId() == IDs.KNIGHT_2_IDLE || gameObject.getId() == IDs.KNIGHT_3_IDLE) {
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

        else if(gameObject.getId() == IDs.PIRATE_1_IDLE || gameObject.getId() == IDs.PIRATE_2_IDLE || gameObject.getId() == IDs.PIRATE_3_IDLE) {
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

    public static int userNameToPartyId(){
        int id = 0;

        for(int i = 0; i < DataManager.userName.length(); i++){
            id += DataManager.userName.charAt(i) * (i + 1);
        }

        return id;
    }

    public static int characterTypeToInt(IDs.CharacterType characterType){
        switch (characterType){
            case SOLDIER :      return IDs.SOLDIER_1_IDLE;
            case CLOWN   :      return IDs.CLOWN_1_IDLE;
            case KNIGHT  :      return IDs.KNIGHT_1_IDLE;
            case PIRATE  :      return IDs.PIRATE_1_IDLE;
            default      :      return -1;
        }
    }

    public static int soldierSkinToIdle(int skin){
        switch (skin){
            case 1 : return IDs.SOLDIER_1_IDLE;
            case 2 : return IDs.SOLDIER_2_IDLE;
            case 3 : return IDs.SOLDIER_3_IDLE;
            default: return -1;
        }
    }

    public static int[] soldierSkinToAnimationsIds(int skin) {
        switch (skin) {
            case 1: return new int[]{IDs.SOLDIER_1_IDLE, IDs.SOLDIER_1_WALKING, IDs.SOLDIER_1_DYING};
            case 2: return new int[]{IDs.SOLDIER_2_IDLE, IDs.SOLDIER_2_WALKING, IDs.SOLDIER_2_DYING};
            case 3: return new int[]{IDs.SOLDIER_3_IDLE, IDs.SOLDIER_3_WALKING, IDs.SOLDIER_3_DYING};
            default: return new int[]{-1, -1, -1};
        }
    }
}
