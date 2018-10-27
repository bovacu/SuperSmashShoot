package ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import general.Converter;

import java.awt.*;

public abstract class SpriteButton extends Sprite implements Ui{

    private Sprite downButton;
    private Sprite toRender;

    public SpriteButton(int id, int downId){
        super(Converter.idToSprite(id));
        this.downButton = Converter.idToSprite(downId);
        this.toRender = this;
    }

    public void execute(int x, int y){
        if(this.isMouseOver(x, y))
            this.action();
    }

    public abstract void action();

    public Vector2 getPosition(){
        return new Vector2(super.getX(), super.getY());
    }

    public void setButtonSize(float width, float height){
        super.setSize(width, height);
        this.downButton.setSize(width, height);
        this.toRender.setSize(width, height);
    }

    public void setPosition(Vector2 position){
        super.setPosition(position.x, position.y);
        this.downButton.setPosition(super.getX(), super.getY());
        this.toRender.setPosition(super.getX(), super.getY());
    }

    public Dimension getSizes(){
        return new Dimension((int)super.getWidth(), (int)super.getHeight());
    }

    public void setCenterPosition(Vector2 position){
        super.setPosition(position.x - super.getWidth() / 2, position.y - super.getHeight() / 2);
        this.downButton.setPosition(super.getX(), super.getY());
        this.toRender.setPosition(super.getX(), super.getY());
    }

    public Vector2 getCenterPosition(){
        return new Vector2(super.getX() + super.getWidth() / 2, super.getY() + super.getHeight() / 2);
    }

    public boolean isMouseOver(int x, int y){
        return (x >= super.getX() && x <= super.getX() + super.getWidth()
                                        &&
                y >= super.getY() && y < super.getY() + super.getHeight());
    }

    private void update(int x, int y){
        if(!this.isMouseOver(x, y) && toRender != this)
            toRender = this;
        else if(this.isMouseOver(x, y) && toRender != this.downButton)
            toRender = downButton;
    }

    public void render(SpriteBatch batch, int x, int y){
        this.update(x, y);
        this.toRender.draw(batch);
    }

    public void dispose(){
        super.getTexture().dispose();
        this.toRender.getTexture().dispose();
        this.downButton.getTexture().dispose();
    }
}
