package ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import general.Converter;
import general.DataManager;

public class TextBox extends Sprite implements Ui {
    protected final float FONT_RESIZE = 0.75f;
    protected final int MAX_LENGTH;

    protected boolean selected;
    protected boolean canGetLetter;

    protected BitmapFont font;

    protected String info, shown;

    public TextBox(int id, int width, int height, Vector2 position, final int MAX_LENGTH){
        super(Converter.idToSprite(id));
        super.setSize(width, height);
        super.setPosition(position.x, position.y);
        this.font = new BitmapFont(Gdx.files.internal(DataManager.font));
        this.font.getData().setScale(this.FONT_RESIZE);
        this.selected = false;
        this.canGetLetter = true;
        this.info = "";
        this.shown = "";
        this.MAX_LENGTH = MAX_LENGTH;
    }

    public boolean isMouseOver(int x, int y){
        return (x >= super.getX() && x <= super.getX() + super.getWidth()
                &&
                y >= super.getY() && y < super.getY() + super.getHeight());
    }

    public void addLetter(String letter){
        if(this.selected){
            this.info += letter;
            this.updateShown();
        }
    }

    public void removeLetter(){
        if(this.selected && this.info.length() > 0) {
            this.info = this.info.substring(0, this.info.length() - 1);
            this.updateShown();
        }
    }

    public String getInfo(){
        return this.info;
    }

    public boolean isCanGetLetter(){
        return this.canGetLetter;
    }

    public void setCanGetLetter(boolean letter){
        this.canGetLetter = letter;
    }

    public Vector2 getPosition(){
        return new Vector2(super.getX(), super.getY());
    }

    public boolean isSelected(){
        return this.selected;
    }

    public void resetInfo(){
        this.info = "";
        this.shown = "";
    }

    public void execute(int x, int y){
        if(this.isMouseOver(x, y))
            this.selected = true;
        else
            this.selected = false;
    }

    private void updateShown(){
        if(this.info.length() > this.MAX_LENGTH){
            int toCut = this.info.length() - this.MAX_LENGTH;
            this.shown = this.info.substring(toCut);
        }else
            this.shown = this.info;
    }

    @Override
    public void render(SpriteBatch batch, int x, int y) {
        super.draw(batch);
        this.font.draw(batch, this.shown, this.getPosition().x + super.getWidth() * 0.1f,
                this.getPosition().y + super.getHeight() / 2f + this.font.getCapHeight());
    }

    @Override
    public void dispose() {
        super.getTexture().dispose();
        this.font.dispose();
    }
}
