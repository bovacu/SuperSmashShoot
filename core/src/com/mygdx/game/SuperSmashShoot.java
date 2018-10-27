package com.mygdx.game;

import characters.ServerListener;
import characters.ServerSpeaker;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import general.DataManager;
import general.IDs;
import maps.Map;
import screens.MainMenu;
import ui.Chat;
import ui.Message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SuperSmashShoot extends Game {
	public static final String HOST_NAME = "supersmashshoot.ddns.net";

	public SpriteBatch batch;
	public ShapeRenderer debugger;
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	public static final int PORT = 6868;
	private Map map;
	private MainMenu mainMenu;

    public static Message ms_message;
	public static ServerSpeaker serverSpeaker;
	public static ServerListener serverListener;
	public static ExecutorService pool;
	
	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.debugger = new ShapeRenderer();
		SuperSmashShoot.SCREEN_WIDTH = 1920;
		SuperSmashShoot.SCREEN_HEIGHT = 1080;
		DataManager.loadData();
		ms_message = new Message("", IDs.PAGED_LIST_BACK);
		SuperSmashShoot.pool = Executors.newFixedThreadPool(2);
		SuperSmashShoot.serverSpeaker = new ServerSpeaker();
		SuperSmashShoot.serverListener = new ServerListener();
		SuperSmashShoot.pool.execute(SuperSmashShoot.serverSpeaker);
		SuperSmashShoot.pool.execute(SuperSmashShoot.serverListener);

        this.mainMenu = new MainMenu(this);
		super.setScreen(this.mainMenu);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		//this.map.dispose();
		this.mainMenu.dispose();
		this.batch.dispose();
		this.debugger.dispose();
		ms_message.dispose();
		this.disconnectFromDataBase();
	}

	private void disconnectFromDataBase(){
		Socket s = null;
		SuperSmashShoot.serverSpeaker.closeSpeaker();
        pool.shutdown();
		try {
			s = new Socket("localhost", 6868);
			DataOutputStream os = new DataOutputStream(s.getOutputStream());
			os.writeBytes("DISCONNECT" + "\r\n");
			os.writeBytes(DataManager.userName + "\r\n");
			os.flush();
			os.close();
			s.close();
		} catch (ConnectException e){

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
