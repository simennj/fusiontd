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

import no.fusiontd.MPAlternative.MPClient;
import no.fusiontd.MPAlternative.MPServer;
import no.fusiontd.screens.*;

public class FusionTD extends Game {
    private Screen menuScreen;
	private Screen optionScreen;
	private Screen highscoreScreen;
	private MapEditorScreen mapEditorScreen;
	private PlayScreen playScreen;
	private Screen connectScreen;
	private Screen mapSelectScreen;
	private MPServer mpServer;
	private MPClient mpc;
	private boolean multiplayer;

	@Override
	public void create () {
		menuScreen = new MenuScreen(this);
		optionScreen = new OptionScreen(this);
		highscoreScreen = new HighscoreScreen(this);
		mapEditorScreen = new MapEditorScreen(this);
		mapSelectScreen = new MapSelectScreen(this);
		connectScreen = new ConnectScreen(this);
		multiplayer = false; // not yet chosen mp
		setScreen(menuScreen);
	}

	public void startGame(String mapName){
		playScreen = new PlayScreen(this, multiplayer);
		playScreen.setMap(mapName);
		setScreen(playScreen);
		//System.out.println("Runs startgame");

		if(multiplayer){
			//passes client or server to playscreen based on which one has been instantiated, does nothing if neither is instantiated
			if(mpc == null){
				System.out.println("setting mpServer in playscreen");
				playScreen.setMpServer(mpServer);
				mpServer.sendMetaData(mapName);
			}
			else if(mpServer == null){
				//Sends metadata to the client and starts game
				System.out.println("setting mpClient in playscreen");
				playScreen.setMpClient(mpc);
			}
		}
	}

	public void returnToMenu(){ setScreen(menuScreen); }

	public void selectMap(){
		//pass client to playscreen or smth
		setScreen(mapSelectScreen);
	}

	public void openMapEditor(){
		setScreen(mapEditorScreen);
	}

	public void openOptions(){ setScreen(optionScreen); }

	public void openMultiplayer(){
		setScreen(connectScreen);
	}

	public void initMPServer(MPServer mpServer){
		multiplayer = true;
		this.mpServer = mpServer;
		//Passes the new game object to mapselectscreen
		mapSelectScreen = new MapSelectScreen(this);
	}

	public void initMPClient(MPClient mpClient){
		mpc = mpClient;
		multiplayer = true;
	}

	// Don't use this for now
	public void stopMPC(){
		//mpc.stop();
		mpc = null;
	}

	public MPClient getMpc(){
		return mpc;
	}

}
