package ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.SuperSmashShoot;
import general.Converter;
import general.DataManager;

public class Message extends Sprite implements Ui {

    private final float LIFE_TIME = 2f;
    private final Color COLOR = Color.valueOf(DataManager.colorToHex(DataManager.textColor));

    private BitmapFont font;
    private GlyphLayout fontInfo;

    private String text;
    private float timePassed;

    public Message(String text, int id){
        super(Converter.idToSprite(id));
        this.text = text;
        this.timePassed = this.LIFE_TIME;
        this.font = new BitmapFont(Gdx.files.internal("fonts/flipps.fnt"));
        this.font.getData().setScale(0.75f);
        this.fontInfo = new GlyphLayout();
    }

    @Override
    public void render(SpriteBatch batch, int x, int y) {
        if(this.timePassed < this.LIFE_TIME){
            float delta = Gdx.graphics.getDeltaTime();
            super.draw(batch);
            if(this.font.getColor().a < 1) {
                this.font.getColor().a += delta;
                super.setAlpha(this.font.getColor().a);
            }

            this.font.draw(batch, this.text,super.getX() + this.fontInfo.height, super.getY() + this.fontInfo.height * 2);
            this.timePassed += delta;
        }
    }

    public void update(String text){
        float offset = 15;
        super.setAlpha(0);
        this.font.setColor(this.COLOR.r, this.COLOR.g, this.COLOR.b, 0);
        this.timePassed = 0;
        this.text = text;
        this.fontInfo.setText(this.font, this.text);
        super.setSize(this.fontInfo.width + 2 * this.fontInfo.height, 2 * this.fontInfo.height);
        super.setPosition(SuperSmashShoot.SCREEN_WIDTH / 2f - super.getWidth() / 2f,
                SuperSmashShoot.SCREEN_HEIGHT - super.getHeight() - offset);
    }

    @Override
    public void dispose() {
        super.getTexture().dispose();
        this.font.dispose();
    }

    @Override
    public void execute(int x, int y) {

    }
}
