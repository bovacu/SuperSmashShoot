package ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.SuperSmashShoot;
import general.Converter;
import general.DataManager;
import general.IDs;

import java.util.ArrayList;
import java.util.List;

public class PartyList extends Sprite implements Ui {

    private final float FONT_RESIZE = 0.75f;
    private final int ITEMS_PER_PAGE = 3;
    private BitmapFont font;
    private List<String> list;
    private Label lb_title;

    private SpriteButton sb_leave;

    private int distanceFromBottomToButtons;
    private GlyphLayout layout;

    public PartyList(int width, int height, Vector2 position, int backId){
        super(Converter.idToSprite(backId));
        super.setSize(width, height);
        super.setPosition(position.x, position.y);
        this.distanceFromBottomToButtons = (int)(getPosition().y + 32 - this.getPosition().y);
        this.font = new BitmapFont(Gdx.files.internal(DataManager.font));
        this.font.getData().setScale(this.FONT_RESIZE);
        this.list = new ArrayList<>();
        this.layout = new GlyphLayout();
        this.lb_title = new Label("Friends Party", new Vector2(super.getX(),
                super.getY() + super.getHeight() + 1.5f * this.font.getCapHeight()));
        this.sb_leave = new SpriteTextButton(IDs.GRAY_BUTTON_UP, IDs.GRAY_BUTTON_DOWN, "LEAVE", 256, 64, 1f) {
            @Override
            public void action() {
                List<String> toSend = new ArrayList<>();
                toSend.add("LEAVE PARTY");
                SuperSmashShoot.serverSpeaker.setToSend(toSend);
            }
        };

        this.sb_leave.setPosition(new Vector2(super.getX() + this.sb_leave.getSizes().width / 2f,
                super.getY() - this.sb_leave.getSizes().height * 1.5f));
    }

    public void updateColors(){
        this.lb_title.setColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));
        this.font.setColor(Color.valueOf(DataManager.colorToHex(DataManager.textColor)));
    }

    public void addItem(String item){
        if(this.list.size() < this.ITEMS_PER_PAGE)
            this.list.add(item);
    }

    public void resetList(){
        this.list = new ArrayList<>();
    }

    public List<String> getList(){
        return this.list;
    }

    public Vector2 getPosition(){
        return new Vector2(super.getX(), super.getY());
    }


    @Override
    public void render(SpriteBatch batch, int x, int y) {
        if(this.list.size() > 0){
            super.draw(batch);
            this.sb_leave.render(batch, x, y);
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
        this.sb_leave.dispose();
    }

    @Override
    public void execute(int x, int y) {
        this.sb_leave.execute(x, y);
    }
}
