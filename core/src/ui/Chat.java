package ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import general.Converter;
import general.IDs;

public class Chat extends InputAdapter implements Ui {

    private final int WIDTH, HEIGHT;

    private String log;
    private Sprite background;

    private SpriteButton sb_sendMessage;
    private TextBox tb_messages;
    private BitmapFont textToRender;

    private boolean visible;

    public Chat(SpriteButton chatButton){
        this.log = "";
        this.visible = false;

        this.WIDTH = 650;
        this.HEIGHT = 512;
        this.background = Converter.idToSprite(IDs.PAGED_LIST_BACK);
        this.background.setSize(WIDTH, HEIGHT);
        this.background.setPosition(chatButton.getPosition().x, chatButton.getPosition().y + chatButton.getSizes().height + 15);

        this.textToRender = new BitmapFont(Gdx.files.internal("fonts/flipps.fnt"));

        this.tb_messages = new TextBox(IDs.TEXT_BOX, (int)(this.WIDTH * 0.905f), (int)(this.HEIGHT * 0.15f),
                new Vector2(this.background.getX(), this.background.getY()), 27);


        this.sb_sendMessage = new SpriteButton(IDs.SEND, IDs.SEND_DOWN) {
            @Override
            public void action() {
                if(!tb_messages.getInfo().equals("\\s+")){

                }
            }
        };

        this.sb_sendMessage.setButtonSize(this.WIDTH * 0.1f, this.HEIGHT * 0.15f);
        this.sb_sendMessage.setPosition(new Vector2(this.background.getX() + this.tb_messages.getWidth() * 0.89f + this.WIDTH * 0.1f,
                this.background.getY()));
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    public boolean isVisible(){
        return this.visible;
    }

    @Override
    public void render(SpriteBatch batch, int x, int y) {
        if(this.visible){
            this.background.draw(batch);
            this.tb_messages.render(batch, x, y);
            this.sb_sendMessage.render(batch, x, y);
            // this.textToRender.draw();
        }
    }

    @Override
    public void dispose() {
        this.background.getTexture().dispose();
        this.sb_sendMessage.dispose();
        this.tb_messages.dispose();
        this.textToRender.dispose();
    }

    @Override
    public void execute(int x, int y) {
        if(this.visible){
            this.tb_messages.execute(x, y);
            this.sb_sendMessage.execute(x, y);
        }
    }

    @Override
    public boolean keyDown(int key){
        if(this.visible)
            if(key == Input.Keys.DEL || key == Input.Keys.ENTER || key == Input.Keys.CONTROL_LEFT || key == Input.Keys.CONTROL_RIGHT
                    || key == Input.Keys.SHIFT_LEFT || key == Input.Keys.SHIFT_RIGHT || key == Input.Keys.ESCAPE
                    || key == Input.Keys.TAB) {
                this.tb_messages.setCanGetLetter(false);

                if(key == (Input.Keys.DEL)) {
                    this.tb_messages.removeLetter();
                }
            } else {
                this.tb_messages.setCanGetLetter(true);
                this.tb_messages.setCanGetLetter(true);
            }
        return false;
    }

    /**
     * Si el TextBox no esta bloqueado por haber pulsado una tecla de la lista y a su vez esta seleccionado por el
     * usuario (eso se maneja internamente en su clase), se va metiendo texto en el TextBox hasta un limite.
     **/
    @Override
    public boolean keyTyped(char key){
        if(this.visible)
            if(this.tb_messages.isCanGetLetter())
                this.tb_messages.addLetter(String.valueOf(key));
        return false;
    }
}
