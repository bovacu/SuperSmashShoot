package ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import general.Converter;

import java.util.ArrayList;
import java.util.List;

public class PartyList extends Sprite implements Ui {

    private final float FONT_RESIZE = 0.75f;
    private final int ITEMS_PER_PAGE = 3;
    private BitmapFont font;
    private List<String> list;
    private Label lb_title;

    private int distanceFromBottomToButtons;
    private GlyphLayout layout;

    public PartyList(int width, int height, Vector2 position, int backId){
        super(Converter.idToSprite(backId));
        super.setSize(width, height);
        super.setPosition(position.x, position.y);
        this.distanceFromBottomToButtons = (int)(getPosition().y + 32 - this.getPosition().y);
        this.font = new BitmapFont(Gdx.files.internal("fonts/flipps.fnt"));
        this.font.getData().setScale(this.FONT_RESIZE);
        this.list = new ArrayList<>();
        this.layout = new GlyphLayout();
        this.lb_title = new Label("Friends Party", new Vector2(super.getX(),
                super.getY() + super.getHeight() + 1.5f * this.font.getCapHeight()));
    }

    public void addItem(String item){
        if(this.list.size() < this.ITEMS_PER_PAGE)
            this.list.add(item);
    }

    public Vector2 getPosition(){
        return new Vector2(super.getX(), super.getY());
    }


    @Override
    public void render(SpriteBatch batch, int x, int y) {
        if(this.list.size() > 0){
            super.draw(batch);
            for(int i = 0; i < this.list.size(); i++){
                float _x = this.getPosition().x + 32;
                float _y = (super.getY() + super.getHeight()) - this.distanceFromBottomToButtons - i * 64;
                if(x >= _x && x <= _x + (super.getWidth() - (_x - super.getX()))
                        &&
                        y >= _y - this.font.getCapHeight() * (1/this.FONT_RESIZE) && y <= _y) {
                    this.font.setColor(Color.RED);
                    layout.setText(this.font, this.list.get(i));

                } else
                    this.font.setColor(Color.WHITE);
                this.font.draw(batch, this.list.get(i), _x, _y);
                this.lb_title.render(batch, x, y);
            }
        }
    }

    @Override
    public void dispose() {
        super.getTexture().dispose();
        this.font.dispose();
        this.lb_title.dispose();
    }

    @Override
    public void execute(int x, int y) {

    }
}
