package characters;

public class PlayerDataPackage{

    private String usr;
    private int[] position;
    private int id;
    private int aimDirection;
    private boolean flipAnim;
    private String anim;

    public PlayerDataPackage(int id, String usr){
        this.position = new int[2];
        this.id = id;
        this.usr = usr;
        this.anim = "IDLE";
        this.flipAnim = false;
        this.aimDirection = 1;
    }

    public void update(int[] position, String anim){
        this.position = position;
        this.anim = anim;
    }

    public int[] getPosition(){
        return this.position;
    }

    public int getId(){
        return this.id;
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

    public int getAimDirection(){
        return this.aimDirection;
    }

    public void setAimDirection(int aimDirection){
        this.aimDirection = aimDirection;
    }
}
