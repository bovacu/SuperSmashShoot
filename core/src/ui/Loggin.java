package ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.SuperSmashShoot;
import general.Converter;
import general.IDs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Loggin extends InputAdapter implements Ui {

    private Sprite background;
    private TextBox tb_user, tb_password;
    private Label lb_user, lb_password;
    private SpriteButton sb_loggIn, sb_close, sb_register;

    private boolean closed;

    public Loggin(int id, Vector2 position){
        this.background = Converter.idToSprite(id);
        this.background.setSize(512, 512);
        this.background.setPosition(position.x, position.y);
        this.closed = false;
        this.createBlock1();
        this.createBlock2();
        this.createBlock3();
    }

    private void createBlock1(){
        final int TB_W = 360;
        final int TB_H = 96;
        this.lb_user = new Label("USER", 1f);
        this.lb_user.setPosition(new Vector2(this.background.getX() + this.background.getWidth() / 2f - this.lb_user.getWidth() / 2f,
                this.background.getY() + this.background.getHeight() - this.lb_user.getHeight() / 1.5f));
        this.tb_user = new TextBox(IDs.TEXT_BOX, TB_W, TB_H, new Vector2(this.background.getX() + this.background.getWidth() / 2f - TB_W / 2f,
                this.lb_user.getPosition().y - this.lb_user.getHeight() * 4f), 12);
    }

    private void createBlock2(){
        final int TB_W = 360;
        final int TB_H = 96;
        this.lb_password = new Label("PASSWORD", 1f);
        this.lb_password.setPosition(new Vector2(this.background.getX() + this.background.getWidth() / 2f - this.lb_password.getWidth() / 2f,
                this.tb_user.getPosition().y - this.lb_user.getHeight() / 1.5f));
        this.tb_password = new TextBoxPassword(IDs.TEXT_BOX, TB_W, TB_H, new Vector2(this.tb_user.getPosition().x,
                this.lb_password.getPosition().y - this.lb_password.getHeight() * 4f), 12);
    }

    private void createBlock3(){
        float offset = 40;
        this.sb_loggIn = new SpriteButton(IDs.CHECK, IDs.CHECK_DOWN) {
            @Override
            public void action() {
                List<String> toSend = new ArrayList<>();
                toSend.add("CONNECT");
                toSend.add(tb_user.info);
                toSend.add(tb_password.info);

                SuperSmashShoot.serverSpeaker.setToSend(toSend);
            }
        };

        this.sb_loggIn.setPosition(new Vector2(this.background.getX() + this.background.getWidth() - this.sb_loggIn.getWidth() - offset,
                this.background.getY() + offset));

        //-------------------------------------------------------

        this.sb_close = new SpriteButton(IDs.QUIT, IDs.QUIT_DOWN) {
            @Override
            public void action() {
                this.dispose();
                closed = true;
            }
        };

        this.sb_close.setPosition(new Vector2(this.sb_loggIn.getPosition().x - this.sb_loggIn.getWidth() - offset / 2f,
                this.sb_loggIn.getPosition().y));

        //-------------------------------------------------------

        this.sb_register = new SpriteButton(IDs.REGISTER, IDs.REGISTER_DOWN) {
            @Override
            public void action() {
                List<String> toSend = new ArrayList<>();
                toSend.add("REGISTER");
                toSend.add(tb_user.info);
                toSend.add(tb_password.info);

                SuperSmashShoot.serverSpeaker.setToSend(toSend);
            }
        };

        this.sb_register.setPosition(new Vector2(this.background.getX() + offset,
                this.sb_loggIn.getPosition().y));
    }

    public boolean isClosed(){
        return this.closed;
    }

    @Override
    public void render(SpriteBatch batch, int x, int y) {
        this.background.draw(batch);
        this.lb_user.render(batch, x, y);
        this.tb_user.render(batch, x, y);
        this.lb_password.render(batch, x, y);
        this.tb_password.render(batch, x, y);
        this.sb_loggIn.render(batch, x, y);
        this.sb_close.render(batch, x, y);
        this.sb_register.render(batch, x, y);
    }

    @Override
    public void dispose() {
        this.background.getTexture().dispose();
        this.lb_user.dispose();
        this.tb_user.dispose();
        this.lb_password.dispose();
        this.tb_password.dispose();
        this.sb_close.dispose();
        this.sb_loggIn.dispose();
        this.sb_register.dispose();
    }

    @Override
    public void execute(int x, int y) {
        this.tb_user.execute(x, y);
        this.tb_password.execute(x, y);
        this.sb_loggIn.execute(x, y);
        this.sb_close.execute(x, y);
        this.sb_register.execute(x, y);
    }

    @Override
    public boolean keyDown(int key){
        if(key == Input.Keys.DEL || key == Input.Keys.ENTER || key == Input.Keys.CONTROL_LEFT || key == Input.Keys.CONTROL_RIGHT
                || key == Input.Keys.SHIFT_LEFT || key == Input.Keys.SHIFT_RIGHT || key == Input.Keys.ESCAPE || key == Input.Keys.SPACE) {
            this.tb_user.setCanGetLetter(false);
            this.tb_password.setCanGetLetter(false);

            if(key == (Input.Keys.DEL)) {
                this.tb_user.removeLetter();
                this.tb_password.removeLetter();
            }
        } else {
            this.tb_user.setCanGetLetter(true);
            this.tb_password.setCanGetLetter(true);
        }
        return false;
    }

    /**
     * Si el TextBox no esta bloqueado por haber pulsado una tecla de la lista y a su vez esta seleccionado por el
     * usuario (eso se maneja internamente en su clase), se va metiendo texto en el TextBox hasta un limite.
     **/
    @Override
    public boolean keyTyped(char key){
        if(this.tb_user.isCanGetLetter())
            this.tb_user.addLetter(String.valueOf(key));

        if(this.tb_password.isCanGetLetter())
            this.tb_password.addLetter(String.valueOf(key));
        return false;
    }
}
