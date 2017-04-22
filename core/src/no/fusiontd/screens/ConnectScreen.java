package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import no.fusiontd.FusionTD;
import no.fusiontd.MPAlternative.MPClient;
import no.fusiontd.MPAlternative.MPServer;
import no.fusiontd.MenuStage;
import no.fusiontd.menu.DialogFactory;
import no.fusiontd.menu.ExitButton;
import no.fusiontd.menu.LabelFactory;

public class ConnectScreen implements Screen, Input.TextInputListener {

    private FusionTD game;
    private String serverIP, typedIPString;
    private boolean serverRunning = false;
    private Label labelIP, typedIPField;
    private TextButton btnFindGame, btnHostGame;
    private MenuStage stage;
    private LabelFactory labelFactory;
    private DialogFactory dialogFactory;
    private MPClient mpClient;
    private MPServer mpServer;
    private boolean pending;
    private Timer timer;
    private ExitButton exitButton;

    public ConnectScreen(FusionTD game) {
        serverIP = null;
        this.game = game;
        this.labelFactory = new LabelFactory();
        this.dialogFactory = new DialogFactory();
        pending = false;
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
        exitButton = ExitButton.create(game);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(mpClient != null){
                    mpClient.stopClient();
                }else if(serverRunning){
                    serverIP = null;
                    mpServer.stopServer();
                    serverRunning = false;
                }
                game.stopMP();
            }
        });

        /*Texture backgroundImage = new Texture(Gdx.files.internal("backgrounds/main_menu_with_creeps.png"));
        stage.setBackground(new Image(backgroundImage));*/

        labelIP = labelFactory.createLabel(serverIP);
        stage.addMenuContent(labelIP);

        final Dialog popUpDialog = dialogFactory.createDialog("", "No one has Connected yet :C");

        btnHostGame = stage.createTextButton("Host Game", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (serverRunning) {
                    if(mpServer.getConnection() == null){
                        popUpDialog.show(stage);

                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            popUpDialog.hide();
                        }
                    }, 2);
                    }else{
                        game.selectMap();
                    }
                } else {
                    mpServer = new MPServer(game, "Haxor1337");
                    serverIP = mpServer.getIp();
                    labelIP.setText("Server running on: " + serverIP);
                    serverRunning = true;
                    game.initMPServer(mpServer);
                    btnHostGame.setText("Start game");
                }
            }
        });

        typedIPField = labelFactory.createLabel("Click to enter IP");
        typedIPField.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.getTextInput(ConnectScreen.this, "Enter Ip to Connect to", "", "");
            }
        });
        stage.addMenuContent(typedIPField);

        btnFindGame = stage.createTextButton("Find Game", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(typedIPString != null && !pending) {
                    mpClient = new MPClient(typedIPString, game, "Saltminer");
                    mpClient.login();
                    game.initMPClient(mpClient);
                    btnFindGame.setText("Disconnect");
                    typedIPField.setText("Connected to: " + typedIPString);
                    pending = true;

                    timer = Timer.instance();
                    timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            System.out.println("Checking for map in mpClient");
                            if (!mpClient.getMapName().equals("")) {
                                game.startGame(mpClient.getMapName());
                                timer.clear();
                            }
                        }
                    }, 2, 2, 10);
                }
                else if(pending){
                    mpClient.close();
                    btnFindGame.setText("Connect");
                    timer.clear();
                    pending = false;
                    typedIPField.setText(typedIPString);
                }
                }
        });

        stage.addImageButton(exitButton);
    }


    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose () {
        stage.dispose();
        dialogFactory.dispose();
        exitButton.dispose();
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
