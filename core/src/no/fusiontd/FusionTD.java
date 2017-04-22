package no.fusiontd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import no.fusiontd.MPAlternative.MPClient;
import no.fusiontd.MPAlternative.MPServer;
import no.fusiontd.maps.MapReader;
import no.fusiontd.maps.MapWriter;
import no.fusiontd.screens.*;

public class FusionTD extends Game {
    private Screen menuScreen;
	private Screen optionScreen;
	private Screen highscoreScreen;
	private MapEditorScreen mapEditorScreen;
	private PlayScreen playScreen;
	private Screen connectScreen;
	private Screen mapSelectScreen;
	private Screen mapDeleteScreen;
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
		boolean mapExists = false;
		FileHandle[] maps =  Gdx.files.internal("maps/").list();
		for (int i = 0; i < maps.length; i++) {
			System.out.println(maps[i].toString());
			String[] map = maps[i].toString().split("[/.]");
			System.out.println(map[1]);
			if(map[1].equals(mapName)){
				mapExists = true;
			}
		}
		if(!mapExists){
			String mapAsString = mpc.getMapAsString();
			int [][] mapArray = new int[9][16];
			int k = 0;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 16; j++) {
					mapArray[i][j] = mapAsString.charAt(k);
					k++;
				}
			}
			MapWriter mapWriter = new MapWriter();
			mapWriter.saveMap(mapArray,mapName);
		}

		if(multiplayer){
			//passes client or server to playscreen based on which one has been instantiated, does nothing if neither is instantiated
			if(mpc == null){
				MapReader mapReader = new MapReader();
				int [][] mapArray = mapReader.loadMap(mapName +".txt", 9, 16);
				MapWriter mapWriter = new MapWriter();
				String mapAsString = mapWriter.mapToString(mapArray);
				System.out.println("setting mpServer in playscreen");
				playScreen.setMpServer(mpServer);
				mpServer.sendMetaData(mapName, mapAsString);
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

	public void openDeleteScreen(){
		mapDeleteScreen = new MapDeleteScreen(this);
		setScreen(mapDeleteScreen);
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
	public void stopMP(){
		mpServer = null;
		mpc = null;
	}

	public MPClient getMpc(){
		return mpc;
	}

}
