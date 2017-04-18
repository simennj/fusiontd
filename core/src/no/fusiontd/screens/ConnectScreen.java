package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

import no.fusiontd.FusionTD;
import no.fusiontd.MPAlternative.MPServer;
import no.fusiontd.MenuStage;
import no.fusiontd.networking.mpc.MPClient;

public class ConnectScreen implements Screen, Input.TextInputListener {

    private int width,height;
    private FusionTD game;
    private Table table;
    private TextureAtlas atlas;
    private Skin skin;
    private MPClient mpClient;
    private List<String> playerList;
    private String inviteString, serverIP, typedIPString;
    private boolean serverRunning = false;
    private TextField typedIPField;
    private TextButton btnFindGame, btnHostGame, btnAccept;
    private MenuStage stage;

    public ConnectScreen(FusionTD game) {
        serverIP = null;
        this.game = game;
        //mpClient = game.getMpc();
        //mpClient.sendPlayerListRequest();
        //inviteString = mpClient.getNL().getRequestString();
        atlas = new TextureAtlas(Gdx.files.internal("ui.atlas"));
        //skin = new Skin(Gdx.files.internal("ui/list_skin.json"), atlas);
        //playerList = new List<String>(skin);
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

        final TextureAtlas uiAtlas = new TextureAtlas("ui.atlas");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Kenney Blocks.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        final BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!


        final Table table = new Table();
        stage.addActor(table);
        table.setPosition(stage.getWidth()/2, stage.getHeight()/2);

        // table.align(Align.right | Align.bottom);

        /*final TextButton btnFindPlayer = new TextButton("Find Player", btnStyle);
        btnFindPlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String[] items2 = new String[mpClient.getNL().getPlayerList().size()];
                mpClient.getNL().getPlayerList().toArray(items2);
                System.out.println(mpClient.getNL().getPlayerList().toString());
                playerList.setItems(items2);
                Table playerTable = new Table();
                playerTable.setPosition(stage.getWidth()/2, stage.getHeight()/2);
                playerTable.setSize(400, 400);
                playerTable.setColor(Color.BLUE);
                TextButton btnSelect = new TextButton("Request assistance", btnStyle);
                btnSelect.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        mpClient.sendMPRequest(playerList.getSelectedIndex());
                    }
                });
                btnSelect.bottom();
                playerTable.add(playerList);
                playerTable.add(btnSelect);
                stage.addActor(playerTable);
                playerTable.debug();
            }
        });
        table.add(btnFindPlayer);*/
        // table.setTouchable(Touchable.disabled);

        final Table table3 = new Table();
        stage.addActor(table3);
        table3.setFillParent(true);
        table3.top();
        table.setPosition(stage.getWidth()/2, stage.getHeight()/2);

        // table.align(Align.right | Align.bottom);

        TextField.TextFieldStyle tfStyle = new TextField.TextFieldStyle();
        tfStyle.font = font12;
        tfStyle.fontColor = Color.GREEN;
        final TextField serverIPField = new TextField(serverIP, tfStyle);
        serverIPField.setSize(400, 400);
        serverIPField.debug();
        table3.add(serverIPField);

        //Displayed when you try you press HostGame a second time
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font12;
        TextureRegion windowBackground = uiAtlas.findRegion("yellow_button03");
        windowStyle.background = new TextureRegionDrawable(windowBackground);
        final Dialog popUp = new Dialog("Server is already running", windowStyle);

        btnHostGame = stage.createTextButton("Host Game", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(serverRunning){
                    popUp.show(stage);

                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run()
                        {
                            popUp.hide();
                        }
                    }, 2);
                }
                else{
                    final MPServer mpServer = new MPServer(game, "Haxor1337");
                    serverIP = mpServer.getIp();
                    serverIPField.setText("Server running on:\n " + serverIP);
                    serverRunning = true;
                }
            }
        });
        table3.add(btnHostGame);

        Table table4 = new Table();
        stage.addActor(table4);
        table4.setFillParent(true);
        table4.right();
        table.setPosition(stage.getWidth()/2, stage.getHeight()/2);

        // table.align(Align.right | Align.bottom);
        typedIPField = new TextField("no Ip entered yet", tfStyle);
        typedIPField.debug();

        btnFindGame = stage.createTextButton("Find Game", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("clicked button FindGame");

                if(btnFindGame.getText().equals("Play")){
                    game.startGame(game.getMpc().getMapName());
                }

                else if(typedIPString != null){
                    no.fusiontd.MPAlternative.MPClient mpClient = new no.fusiontd.MPAlternative.MPClient(typedIPString, game, "Saltminer");
                    mpClient.login();
                    btnFindGame.setText("Play");
                }

                else{
                    Gdx.input.getTextInput(ConnectScreen.this, "Enter Ip to Connect to", "", "xxx.xxx.x.x.x.x");
                }
            }
        });

        table4.add(typedIPField);
        table4.add(btnFindGame);

        final Table table2 = new Table();
        stage.addActor(table2);
        table2.setFillParent(true);
        table2.bottom();
        btnAccept = stage.createTextButton(inviteString,new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String currInviteString = mpClient.getNL().getRequestString();
                System.out.println(currInviteString);
                if(inviteString.equals("No requests yet :C")){
                    btnAccept.setText(currInviteString);
                    inviteString = currInviteString;
                }
                else{
                    /*new Dialog("Accept request", skin){
                        {
                            text(inviteString);
                            button("yes", "Alright!!");
                            button("no", "Ok :C");
                        }

                        @Override
                        protected void result(Object object){
                            mpClient.sendMPAnswer(true);
                        }
                    }.show(stage);*/
                    mpClient.sendMPAnswer(true);
                }
            }
        });
        table2.add(btnAccept);
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
