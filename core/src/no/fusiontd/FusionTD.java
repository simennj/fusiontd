package no.fusiontd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Connection;

import no.fusiontd.networking.mpc.MPClient;
import no.fusiontd.screens.*;

public class FusionTD extends Game {
    private Screen menuScreen;
	private Screen optionScreen;
	private Screen highscoreScreen;
	private Screen mapEditorScreen;
	private PlayScreen playScreen;
	private Screen connectScreen;
	private Screen mapSelectScreen;
	private MPClient mpc;
	private boolean multiplayer;

	@Override
	public void create () {
		menuScreen = new MenuScreen(this);
		optionScreen = new OptionScreen(this);
		highscoreScreen = new HighscoreScreen(this);
		mapEditorScreen = new MapEditorScreen(this);
		playScreen = new PlayScreen(this);
		connectScreen = new ConnectScreen(this);
		mapSelectScreen = new MapSelectScreen(this);
		mpc = new MPClient("localhost", this, "Odd er dum");
		multiplayer = false; // not yet chosen mp
		setScreen(menuScreen);
	}

	public void startGame(String mapName){
		playScreen.setMap(mapName);
		setScreen(playScreen);
	}

	public void connectMP(){ setScreen(connectScreen); }

	public void selectMap(){ setScreen(mapSelectScreen);}

	public void openOptions(){ setScreen(optionScreen); }
}
