package ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import general.Converter;
import general.IDs;

import java.util.ArrayList;
import java.util.List;

public abstract class PagedListWithButton extends Sprite implements Ui{
    private final float FONT_RESIZE = 0.75f;
    private final int ITEMS_PER_PAGE = 6;
    private BitmapFont font;
    private List<String> list;
    private SpriteButton next, previous;
    private SpriteButton sb_itemButtons[];
    private String selectedItem;

    private boolean blockButtons;

    private int selectedPage;
    private int itemHeight;
    private int maxPages;

    private int distanceFromBottomToButtons;

    private GlyphLayout layout;

    public PagedListWithButton(int width, int height, Vector2 position, int backId, int nextId, int nextDownId, int prevId, int prevDownId){
        super(Converter.idToSprite(backId));
        super.setSize(width, height);
        super.setPosition(position.x, position.y);
        this.createNextButton(nextId, nextDownId);
        this.createPreviousButton(prevId, prevDownId);
        this.distanceFromBottomToButtons = (int)(this.previous.getPosition().y - this.getPosition().y);
        this.font = new BitmapFont(Gdx.files.internal("fonts/flipps.fnt"));
        this.font.getData().setScale(this.FONT_RESIZE);
        this.itemHeight = (int)(this.previous.getHeight());
        this.list = new ArrayList<>();
        this.layout = new GlyphLayout();
        this.selectedPage = 0;
        this.sb_itemButtons = new SpriteButton[this.ITEMS_PER_PAGE];

        this.blockButtons = false;
    }

    private void createNextButton(int id, int downId){
        this.next = new SpriteButton(id, downId) {
            @Override
            public void action() {
                if(selectedPage + 1 < maxPages) {
                    selectedPage++;
                    updateButtons(itemsToDraw(), IDs.CHECK, IDs.CHECK_DOWN);
                }
            }
        };

        this.next.setCenterPosition(new Vector2(this.getPosition().x + super.getWidth() - this.next.getWidth(), getPosition().y + this.next.getHeight()));
    }

    private void createPreviousButton(int id, int downId){
        this.previous = new SpriteButton(id, downId) {
            @Override
            public void action() {
                if(selectedPage - 1 >= 0) {
                    selectedPage--;
                    updateButtons(itemsToDraw(), IDs.CHECK, IDs.CHECK_DOWN);
                }
            }
        };

        this.previous.setCenterPosition(new Vector2(this.getPosition().x + this.previous.getWidth(), this.next.getCenterPosition().y));
    }

    public void setBlockButtons(boolean block){
        this.blockButtons = block;
    }

    private void updateButtons(int size, int id, int idDown){
        for(int i = 0; i < size; i++){
            this.sb_itemButtons[i] = new SpriteButton(id, idDown) {
                @Override
                public void action() {
                    buttonAction();
                }
            };
        }
    }

    public void setItems(List<String> items){
        this.list = new ArrayList<>(items);
        this.maxPages = (this.list.size() % this.ITEMS_PER_PAGE == 0) ? this.list.size() / this.ITEMS_PER_PAGE :
                (this.list.size() / this.ITEMS_PER_PAGE) + 1;
        this.updateButtons(this.itemsToDraw(), IDs.CHECK, IDs.CHECK_DOWN);
    }

    public void addItem(String item){
        this.list.add(item);
        this.maxPages = (this.list.size() % this.ITEMS_PER_PAGE == 0) ? this.list.size() / this.ITEMS_PER_PAGE :
                (this.list.size() / this.ITEMS_PER_PAGE) + 1;
        this.updateButtons(this.itemsToDraw(), IDs.CHECK, IDs.CHECK_DOWN);
    }

    public Vector2 getPosition(){
        return new Vector2(super.getX(), super.getY());
    }

    public String getSelectedItem(){
        return this.selectedItem;
    }

    public List<String> getItems(){
        return this.list;
    }

    public void execute(int x, int y){
        if(this.next.isMouseOver(x, y))
            this.next.execute(x, y);
        if(this.previous.isMouseOver(x, y))
            this.previous.execute(x, y);
        for(SpriteButton sb : this.sb_itemButtons)
            if(sb != null)
                if(sb.isMouseOver(x, y) && !this.blockButtons)
                    sb.execute(x, y);
    }

    private int itemsToDraw(){
        if(this.list.size() <= this.ITEMS_PER_PAGE)
            return this.list.size();
        else if(this.list.size() % this.ITEMS_PER_PAGE == 0){
            return this.ITEMS_PER_PAGE;
        }else{
            if(this.selectedPage < this.maxPages - 1){
                return this.ITEMS_PER_PAGE;
            }else{
                return this.list.size() % this.ITEMS_PER_PAGE;
            }
        }
    }

    public abstract void buttonAction();

    @Override
    public void render(SpriteBatch batch, int x, int y) {
        super.draw(batch);
        int maxItems = this.itemsToDraw();
        for(int i = 0; i < maxItems; i++){
            float _x = this.getPosition().x + this.previous.getWidth() / 2f;
            float _y = (super.getY() + super.getHeight()) - this.distanceFromBottomToButtons - i * this.itemHeight;
            if(x >= _x && x <= _x + (super.getWidth() - (_x - super.getX()))
                &&
               y >= _y - this.font.getCapHeight() * (1/this.FONT_RESIZE) && y <= _y) {
                this.font.setColor(Color.RED);
                this.selectedItem = this.list.get(i + (this.ITEMS_PER_PAGE * this.selectedPage));
                layout.setText(this.font, this.list.get(i + (this.ITEMS_PER_PAGE * this.selectedPage)));

            } else
                this.font.setColor(Color.WHITE);
            this.font.draw(batch, this.list.get(i + (this.ITEMS_PER_PAGE * this.selectedPage)), _x, _y);
            if(this.sb_itemButtons[i] != null) {
                this.sb_itemButtons[i].setPosition(new Vector2(super.getX() + super.getWidth() - this.sb_itemButtons[i].getSizes().width - 25
                        , _y - this.font.getCapHeight() - this.sb_itemButtons[i].getSizes().height / 2f));
                this.sb_itemButtons[i].render(batch,x, y);
            }
        }
        this.previous.render(batch, x, y);
        this.next.render(batch, x, y);
    }

    @Override
    public void dispose() {
        super.getTexture().dispose();
        this.next.dispose();
        this.previous.dispose();
        this.font.dispose();
        for(SpriteButton sb : this.sb_itemButtons)
            sb.dispose();
    }
}