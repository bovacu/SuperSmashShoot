package com.mygdx.game;

import characters.ServerListener;
import characters.ServerSpeaker;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import general.DataManager;
import general.IDs;
import maps.Map;
import screens.MainMenu;
import ui.Chat;
import ui.Message;
import ui.PartyList;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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

	public static List<String> partyMembers;
	public static int partyId;

	public static PartyList partyList;
	
	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.debugger = new ShapeRenderer();
		SuperSmashShoot.SCREEN_WIDTH = 1920;
		SuperSmashShoot.SCREEN_HEIGHT = 1080;

		SuperSmashShoot.partyId = -1;
		SuperSmashShoot.partyMembers = new ArrayList<>();

		DataManager.loadData();
		ms_message = new Message("", IDs.MESSAGE_BACKGROUND);
		SuperSmashShoot.pool = Executors.newFixedThreadPool(2);
		SuperSmashShoot.serverSpeaker = new ServerSpeaker();
		SuperSmashShoot.serverListener = new ServerListener();
		SuperSmashShoot.pool.execute(SuperSmashShoot.serverSpeaker);
		SuperSmashShoot.pool.execute(SuperSmashShoot.serverListener);

		SuperSmashShoot.partyList = new PartyList(512, 512, new Vector2(SCREEN_WIDTH - 512 - 15,
                SCREEN_HEIGHT / 2f - 256), IDs.PAGED_LIST_BACK);

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
		SuperSmashShoot.partyList.dispose();
		this.disconnectFromDataBase();
	}

	private void disconnectFromDataBase(){
        List<String> toSend = new ArrayList<>();
        toSend.add("CLOSE");

        SuperSmashShoot.serverSpeaker.setToSend(toSend);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pool.shutdown();
	}
}
