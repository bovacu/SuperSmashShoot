package ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import general.DataManager;

public abstract class SpriteTextButton extends SpriteButton {

    private String text;
    private BitmapFont font;

    private GlyphLayout fontInfo;

    public SpriteTextButton(int id, int downId, String text, int width, int height, float fontSize) {
        super(id, downId);
        super.setButtonSize(width, height);
        this.text = text;
        this.font = new BitmapFont(Gdx.files.internal(DataManager.font));
        this.font.getData().setScale(fontSize);
        this.fontInfo = new GlyphLayout();
        this.fontInfo.setText(this.font, this.text);
    }

    @Override
    public void render(SpriteBatch batch, int x, int y){
        super.render(batch, x, y);
        float offset = 0;
        if(super.isMouseOver(x, y))
            offset = 5;
        float _x = super.getPosition().x + super.toRender.getWidth() / 2f - this.fontInfo.width / 2f - offset;
        float _y = super.getPosition().y + super.toRender.getHeight() / 2f + this.fontInfo.height - offset;
        this.font.draw(batch, this.text, _x, _y);
    }

    public void dispose(){
        super.dispose();
        this.font.dispose();
    }
}
