package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import no.fusiontd.FusionTD;
import no.fusiontd.MPAlternative.MPServer;
import no.fusiontd.MenuStage;
import no.fusiontd.menu.DialogFactory;
import no.fusiontd.menu.LabelFactory;

public class ConnectScreen implements Screen, Input.TextInputListener {

    private FusionTD game;
    private TextureAtlas atlas;
    private String serverIP, typedIPString;
    private boolean serverRunning = false;
    private Label labelIP, typedIPField;
    private TextButton btnFindGame, btnHostGame, btnAccept;
    private MenuStage stage;
    private LabelFactory labelFactory;
    private DialogFactory dialogFactory;

    public ConnectScreen(FusionTD game) {
        serverIP = null;
        this.game = game;
        this.labelFactory = new LabelFactory();
        this.dialogFactory = new DialogFactory();
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

        stage = new MenuStage();
        Gdx.input.setInputProcessor(stage);

        Texture backgroundImage = new Texture(Gdx.files.internal("backgrounds/main_menu_with_creeps.png"));
        stage.setBackground(new Image(backgroundImage));

        labelIP = labelFactory.createLabel(serverIP);
        stage.addMenuContent(labelIP);


        final Dialog popUpDialog = dialogFactory.createDialog("", "Server is already running");

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

        typedIPField = labelFactory.createLabel("no Ip entered yet");
        stage.addMenuContent(typedIPField);


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
