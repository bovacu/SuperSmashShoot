package ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Ui {
    void render(SpriteBatch batch, int x, int y);
    void dispose();
    void execute(int x, int y);
}
