package characters;

import general.IDs;

public class PlayerDataPackage{

    private String usr;
    private int[] position;
    private IDs.CharacterType characterType;
    private int skin;
    private int aimDirection;
    private boolean flipAnim;
    private String anim;
    private int life;

    public PlayerDataPackage(IDs.CharacterType characterType, int skin, String usr){
        this.position = new int[2];
        this.characterType = characterType;
        this.skin = skin;
        this.usr = usr;
        this.anim = "IDLE";
        this.flipAnim = false;
        this.aimDirection = 1;
        this.life = 5;
    }

    public void update(int[] position, String anim){
        this.position = position;
        this.anim = anim;
    }

    public int getSkin() {
        return skin;
    }

    public int[] getPosition(){
        return this.position;
    }

    public IDs.CharacterType getCharacterType(){
        return this.characterType;
    }

    public String getUsr(){
        return this.usr;
    }

    public String getAnim(){
        return this.anim;
    }

    public boolean isFlipAnim(){
        return this.flipAnim;
    }

    public void setFlipAnim(boolean flipAnim){
        this.flipAnim = flipAnim;
    }

    public void setAimDirection(int aimDirection){
        this.aimDirection = aimDirection;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

}
