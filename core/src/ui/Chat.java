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
import general.IDs;

import java.util.ArrayList;
import java.util.List;

public class Chat extends InputAdapter implements Ui {

    private static final int WIDTH = 650, HEIGHT = 512, TB_LENGTH = 23, CHAT_LENGTH = 30;

    private static String log;
    private Sprite background;

    private SpriteButton sb_sendMessage;
    private TextBox tb_messages;
    private BitmapFont textToRender;

    private boolean visible;

    public Chat(SpriteButton chatButton){
        Chat.log = "";
        this.visible = false;

        this.background = Converter.idToSprite(IDs.PAGED_LIST_BACK);
        this.background.setSize(WIDTH, HEIGHT);
        this.background.setPosition(chatButton.getPosition().x, chatButton.getPosition().y + chatButton.getSizes().height + 15);

        this.textToRender = new BitmapFont(Gdx.files.internal("fonts/flipps.fnt"));
        this.textToRender.getData().markupEnabled = true;
        this.textToRender.getData().setScale(0.6f);

        this.tb_messages = new TextBox(IDs.TEXT_BOX, (int)(Chat.WIDTH * 0.905f), (int)(Chat.HEIGHT * 0.15f),
                new Vector2(this.background.getX(), this.background.getY()), Chat.TB_LENGTH);


        this.sb_sendMessage = new SpriteButton(IDs.SEND, IDs.SEND_DOWN) {
            @Override
            public void action() {
                if(!tb_messages.getInfo().equals("\\s+")){
                    List<String> toSend = new ArrayList<>();
                    toSend.add("SEND MESSAGE");
                    toSend.add(tb_messages.info);

                    SuperSmashShoot.serverSpeaker.setToSend(toSend);
                }
            }
        };

        this.sb_sendMessage.setButtonSize(Chat.WIDTH * 0.1f, Chat.HEIGHT * 0.15f);
        this.sb_sendMessage.setPosition(new Vector2(this.background.getX() + this.tb_messages.getWidth() * 0.89f + Chat.WIDTH * 0.1f,
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
            this.textToRender.draw(batch, Chat.log, this.background.getX() + Chat.WIDTH * 0.07f, this.background.getY() + this.background.getHeight() -
                    Chat.HEIGHT * 0.07f);
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

    public static void addNewMessage(String user, String message){
        String aux = user + ": " + message;

        if(aux.length() >= Chat.CHAT_LENGTH){
            int splits = aux.length() / Chat.CHAT_LENGTH;
            int lastI = 0;
            String newMessage = "";
            for(int i = 0; i < splits; i++){
                newMessage += aux.substring(i * Chat.CHAT_LENGTH, i * Chat.CHAT_LENGTH + Chat.CHAT_LENGTH) + "\n";
                lastI = i;
            }

            if(aux.length() % Chat.CHAT_LENGTH != 0) {
                newMessage += aux.substring(lastI * Chat.CHAT_LENGTH + Chat.CHAT_LENGTH) + "\n\n";
            } else
                newMessage += "\n";

            Chat.log += newMessage;
        }else{
            Chat.log += aux + "\n\n";
        }
    }

    @Override
    public boolean keyDown(int key){
        if(this.visible)
            if(key == Input.Keys.DEL || key == Input.Keys.CONTROL_LEFT || key == Input.Keys.CONTROL_RIGHT
                    || key == Input.Keys.SHIFT_LEFT || key == Input.Keys.SHIFT_RIGHT || key == Input.Keys.ESCAPE
                    || key == Input.Keys.TAB) {
                this.tb_messages.setCanGetLetter(false);

                if(key == (Input.Keys.DEL)) {
                    this.tb_messages.removeLetter();
                }
            } else if(key == Input.Keys.ENTER){
                if(this.tb_messages.isSelected()){
                    this.sb_sendMessage.action();
                    this.tb_messages.resetInfo();
                }
            }else {
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
