package ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.SuperSmashShoot;
import general.Converter;
import general.DataManager;
import general.IDs;

import java.util.ArrayList;
import java.util.List;

public class Chat extends InputAdapter implements Ui {

    private final int WIDTH, HEIGHT, TB_LENGTH, CHAT_LENGTH;

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
        this.TB_LENGTH = 27;
        this.CHAT_LENGTH = 30;

        this.background = Converter.idToSprite(IDs.PAGED_LIST_BACK);
        this.background.setSize(WIDTH, HEIGHT);
        this.background.setPosition(chatButton.getPosition().x, chatButton.getPosition().y + chatButton.getSizes().height + 15);

        this.textToRender = new BitmapFont(Gdx.files.internal("fonts/flipps.fnt"));

        this.tb_messages = new TextBox(IDs.TEXT_BOX, (int)(this.WIDTH * 0.905f), (int)(this.HEIGHT * 0.15f),
                new Vector2(this.background.getX(), this.background.getY()), this.TB_LENGTH);


        this.sb_sendMessage = new SpriteButton(IDs.SEND, IDs.SEND_DOWN) {
            @Override
            public void action() {
                if(!tb_messages.getInfo().equals("\\s+")){
                    List<String> toSend = new ArrayList<>();
                    toSend.add("SEND MESSAGE");
                    toSend.add(DataManager.userName);
                    toSend.add(tb_messages.info);

                    SuperSmashShoot.serverSpeaker.setToSend(toSend);
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

    public void addNewMessage(String user, String message){
        String aux = user + ": " + message;

        if(aux.length() >= this.CHAT_LENGTH){
            int splits = aux.length() / this.CHAT_LENGTH;
            int lastI = 0;
            String newMessage = "";
            for(int i = 0; i < splits; i++){
                newMessage += aux.substring(i * this.CHAT_LENGTH, i * this.CHAT_LENGTH + this.CHAT_LENGTH) + "\n";
                lastI = i;
            }

            if(aux.length() % this.CHAT_LENGTH != 0) {
                newMessage += aux.substring(lastI * this.CHAT_LENGTH + this.CHAT_LENGTH) + "\n\n";
            } else
                newMessage += "\n";

            this.log += newMessage;
        }else{
            this.log += aux;
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
