package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import no.fusiontd.FusionTD;
import no.fusiontd.MPAlternative.MPServer;
import no.fusiontd.MenuStage;

public class ConnectScreen implements Screen, Input.TextInputListener {

    private int width,height;
    private FusionTD game;
    private TextureAtlas atlas;
    private String serverIP, typedIPString;
    private boolean serverRunning = false;
    private Label labelIP, typedIPField;
    private TextButton btnFindGame, btnHostGame, btnAccept;
    private MenuStage stage;

    public ConnectScreen(FusionTD game) {
        serverIP = null;
        this.game = game;
        atlas = new TextureAtlas(Gdx.files.internal("ui.atlas"));
    }

    @Override
    public void show(){
        create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void create () {

        stage = new MenuStage(game);
        Gdx.input.setInputProcessor(stage);

        labelIP = stage.createLabel(serverIP);
        //labelIP.debug();


        final Dialog popUpDialog = stage.createDialog("", "Server is already running");

        btnHostGame = stage.createTextButton("Host Game", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(serverRunning){
                    popUpDialog.show(stage);

                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run()
                        {
                            popUpDialog.hide();
                        }
                    }, 2);
                }
                else{
                    MPServer mpServer = new MPServer(game, "Haxor1337");
                    serverIP = mpServer.getIp();
                    labelIP.setText("Server running on: " + serverIP);
                    serverRunning = true;
                    game.initMPServer(mpServer);
                }
            }
        });

        // table.align(Align.right | Align.bottom);
        typedIPField = stage.createLabel("no Ip entered yet");
        //typedIPField.debug();

        btnFindGame = stage.createTextButton("Find Game", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //System.out.println("clicked button FindGame");

                if(btnFindGame.getText().equals("Play")){
                    game.startGame(game.getMpc().getMapName());
                }

                else if(typedIPString != null){
                    no.fusiontd.MPAlternative.MPClient mpClient = new no.fusiontd.MPAlternative.MPClient(typedIPString, game, "Saltminer");
                    mpClient.login();
                    btnFindGame.setText("Play");
                }

                else{
                    Gdx.input.getTextInput(ConnectScreen.this, "Enter Ip to Connect to", "", "");
                }
            }
        });

    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose () {
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void input(String inputIP) {
        typedIPString = inputIP;
        typedIPField.setText(inputIP);
        btnFindGame.setText("Connect");
    }

    @Override
    public void canceled() {

    }
}
