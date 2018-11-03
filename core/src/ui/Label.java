package ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import general.DataManager;

public class Label implements Ui{
    private final float FONT_RESIZE = 0.75f;

    private String text;
    private BitmapFont font;
    private Vector2 position;

    private GlyphLayout fontInfo;

    public Label(String text, Vector2 position){
        this.text = text;
        this.position = position;
        this.font = new BitmapFont(Gdx.files.internal(DataManager.font));
        this.font.getData().setScale(this.FONT_RESIZE);
        this.fontInfo = new GlyphLayout();
    }

    public Label(String text, Vector2 position, float size){
        this.text = text;
        this.position = position;
        this.font = new BitmapFont(Gdx.files.internal(DataManager.font));
        this.font.getData().setScale(size);
        this.fontInfo = new GlyphLayout();
    }

    public Label(String text, float size){
        this.text = text;
        this.position = new Vector2();
        this.font = new BitmapFont(Gdx.files.internal(DataManager.font));
        this.font.getData().setScale(size);
        this.fontInfo = new GlyphLayout();
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setPosition(Vector2 position){
        this.position = position;
    }

    public float getHeight(){
        this.fontInfo.setText(this.font, this.text);
        return this.fontInfo.height;
    }

    public void setColor(Color color){
        this.font.setColor(color);
    }

    public float getWidth(){
        this.fontInfo.setText(this.font, this.text);
        return this.fontInfo.width;
    }

    @Override
    public void render(SpriteBatch batch, int x, int y) {
        this.font.draw(batch, this.text, this.position.x, this.position.y);
    }

    @Override
    public void dispose() {
        this.font.dispose();
    }

    @Override
    public void execute(int x, int y) {

    }
}
