package no.fusiontd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
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
		mapSelectScreen = new MapSelectScreen(this);
		multiplayer = false; // not yet chosen mp
		setScreen(menuScreen);
	}

	public void startGame(String mapName){
		playScreen = new PlayScreen(this, multiplayer);
		playScreen.setMap(mapName);
		setScreen(playScreen);
	}

	public void returnToMenu(){ setScreen(menuScreen); }

	public void connectMP(String playerName){
		initMP(playerName);
		connectScreen = new ConnectScreen(this);
		setScreen(connectScreen);
		multiplayer = true;
	}

	public void selectMap(){
		//pass client to playscreen or smth
		setScreen(mapSelectScreen);
	}

	public void openOptions(){ setScreen(optionScreen); }

	public void initMP(String playerName){
		if( mpc == null){
			mpc = new MPClient("localhost", this, playerName);
		}
	}

	// Don't use this for now
	public void stopMPC(){
		mpc.stop();
		mpc = null;
	}

	public MPClient getMpc(){
		return mpc;
	}
}
