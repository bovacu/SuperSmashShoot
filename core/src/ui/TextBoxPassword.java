package ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TextBoxPassword extends TextBox {

    private String password;

    public TextBoxPassword(int id, int width, int height, Vector2 position, final int MAX_LENGTH) {
        super(id, width, height, position, MAX_LENGTH);
        this.password = "";
    }

    @Override
    public void render(SpriteBatch batch, int x, int y) {
        super.draw(batch);
        super.font.draw(batch, this.password, super.getPosition().x + super.getWidth() * 0.1f,
                super.getPosition().y + super.font.getCapHeight() * 2.5f);
    }

    private void updatePass(){
        if(this.info.length() > this.MAX_LENGTH){
            int toCut = this.info.length() - this.MAX_LENGTH;
            this.password = this.info.substring(toCut).replaceAll(".", "*");
        }else
            this.password = this.info.replaceAll(".", "*");
    }

    public void addLetter(String letter){
        if(super.selected) {
            super.info += letter;
            this.updatePass();
        }
    }

    public void removeLetter(){
        if(super.info.length() > 0 && super.selected) {
            super.info = this.info.substring(0, super.info.length() - 1);
            this.updatePass();
        }
    }
}
