package no.fusiontd.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import no.fusiontd.FusionTD;
import no.fusiontd.Graphics;
import no.fusiontd.MPAlternative.MPClient;
import no.fusiontd.MPAlternative.MPServer;
import no.fusiontd.components.Geometry;
import no.fusiontd.components.Upgradeable;
import no.fusiontd.components.Value;
import no.fusiontd.screens.PlayScreen;

import java.util.LinkedList;

public class UI{

    private FusionTD game;
    private Player localPlayer, mulPlayer;
    private boolean showTowerSet = false;
    private float towerSettingX, towerSettingY;
    private EntityComponentManager engine;
    private MPClient mpClient;
    private MPServer mpServer;
    private boolean multiPlayer;
    private TextureAtlas.AtlasRegion play;
    private TextureAtlas uiAtlas, spriteAtlas;
    private Map map;

    public UI(FusionTD game, Player localPlayer, Player mulPlayer, EntityComponentManager engine, Map map) {
        this.game = game; this.localPlayer = localPlayer; this.mulPlayer = mulPlayer;
        this.showTowerSet = false;
        this.engine = engine;
        this.multiPlayer = false;
        uiAtlas = new TextureAtlas("ui.atlas"); spriteAtlas = new TextureAtlas("sprites.atlas");
        this.map = map;
    }

    public void render(SpriteBatch batch, PlayScreen.State state) {
        showLives(batch);
        showCash(batch);
        if(multiPlayer){
            showCashMultiPlayer(batch);
        }
        if (showTowerSet){
            towerSetMenu(towerSettingX, towerSettingY, batch);
        }
        switch (state){
            case PAUSE:
                batch.draw(uiAtlas.findRegion("pause1"), 1.0f , 0.0f, 1f, 1f);
                break;
            case RUN:
                batch.draw(uiAtlas.findRegion("pause0"), 1.0f , 0.0f, 1f, 1f);
                break;
        }
        batch.draw(uiAtlas.findRegion("back0"), 15.0f , 0.0f, 1f, 1f); // back button
        if (!multiPlayer || mpServer != null) {
            batch.draw(uiAtlas.findRegion("play0"), 0.0f, 0.0f, 1f, 1f);
        }

    }

    public void selectTower(float cameraX, float cameraY) {
        Entity e = engine.getTowerAt(cameraX, cameraY);
        if(engine.upgradeEntity(e)) {
            if (multiPlayer) {
                sendTowerUpgrade(cameraX, cameraY);
            }
            localPlayer.addCash(-e.getComponent(Value.class).cost);
        } else {
            System.out.println("You dont have enough money or your tower is at maximum rank");
        }
    }

    public void selectCreep(float cameraX, float cameraY) {
    }

    public void openTowerSet(float cameraX, float cameraY){
        showTowerSet = true; towerSettingX = cameraX; towerSettingY = cameraY;
    }

    public boolean isTowerSetting(){
        return showTowerSet;
    }

    public void closeTowerSet(){
        showTowerSet = false;
    }

    //NinePatch patch = new NinePatch(uiAtlas.findRegion("button0.9"));
    //NinePatchDrawable draw = new NinePatchDrawable(patch);

    public void towerSetMenu(float cameraX, float cameraY, SpriteBatch batch) {

        batch.draw(Graphics.getRegion("placeholder"), cameraX - 0.45f, cameraY - 0.35f, 0.79f, 0.76f);
        //draw.draw(batch, cameraX + 0.5f , cameraY - 1.5f, 2f, 4f);

        //batch.draw(uiAtlas.findRegion("button0"), cameraX + 0.5f , cameraY - 1.5f, 2f, 4f);
        batch.draw(uiAtlas.findRegion("button0"), cameraX + 0.5f , cameraY + 1.5f, 2f, 1f);
        batch.draw(spriteAtlas.findRegion("t"), cameraX + 0.5f, cameraY + 1.6f, 0.9f, 0.8f);

        batch.draw(uiAtlas.findRegion("button0"), cameraX + 0.5f , cameraY + 0.5f, 2f, 1f);
        batch.draw(spriteAtlas.findRegion("t_emil"), cameraX + 0.5f, cameraY + 0.5f, 0.9f, 1f);

        batch.draw(uiAtlas.findRegion("button0"), cameraX + 0.5f , cameraY - 0.5f, 2f, 1f);
        batch.draw(spriteAtlas.findRegion("t_hybrida"), cameraX + 0.5f, cameraY - 0.4f, 1f, 0.783f);

        batch.draw(uiAtlas.findRegion("button0"), cameraX + 0.5f , cameraY - 1.5f, 2f, 1f);
        batch.draw(spriteAtlas.findRegion("t_volvox"), cameraX + 0.5f, cameraY - 1.4f, 1f, 0.78f);
    }

    public boolean towerSet(float cameraX, float cameraY){


        if(cameraX > towerSettingX + 0.5f && cameraX < towerSettingX + 2.5f && cameraY > towerSettingY + 1.5f && cameraY < towerSettingY + 2.5f){
            if (localPlayer.getCash() >= engine.getCost("t_basic")) {
                showTowerSet = false;
                engine.spawnTower("t_basic", new Geometry(towerSettingX, towerSettingY, 0, .5f));

                //Sends tower to the other player
                if(multiPlayer) {
                    sendTower("t_basic", towerSettingX, towerSettingY);
                }

                localPlayer.addCash(-engine.getCost("t_basic"));
                return true;
            } return false;
        } else if(cameraX > towerSettingX + 0.5f && cameraX < towerSettingX + 2.5f && cameraY > towerSettingY + 0.5f && cameraY < towerSettingY + 1.5f){
            if (localPlayer.getCash() >= engine.getCost("t_emil")) {
                showTowerSet = false;
                engine.spawnTower("t_emil", new Geometry(towerSettingX, towerSettingY, 0, .5f));
                if(multiPlayer) {
                    sendTower("t_emil", towerSettingX, towerSettingY);
                }

                localPlayer.addCash(-engine.getCost("t_emil"));
                return true;
            } return false;
        } else if (cameraX > towerSettingX + 0.5f && cameraX < towerSettingX + 2.5f && cameraY > towerSettingY - 0.5f && cameraY < towerSettingY + 0.5f){
            if (localPlayer.getCash() >= engine.getCost("t_hybrida")) {
                showTowerSet = false;
                engine.spawnTower("t_hybrida", new Geometry(towerSettingX, towerSettingY, 0, .5f));
                if(multiPlayer) {
                    sendTower("t_hybrida", towerSettingX, towerSettingY);
                }

                localPlayer.addCash(-engine.getCost("t_hybrida"));
                return true;
            } return false;
        } else if (cameraX > towerSettingX + 0.5f && cameraX < towerSettingX + 2.5f && cameraY > towerSettingY - 1.5f && cameraY < towerSettingY - 0.5f){
            if (localPlayer.getCash() >= engine.getCost("t_volvox")) {
                showTowerSet = false;
                engine.spawnTower("t_volvox", new Geometry(towerSettingX, towerSettingY, 0, .5f));
                if(multiPlayer) {
                    sendTower("t_volvox", towerSettingX, towerSettingY);
                }

                localPlayer.addCash(-engine.getCost("t_volvox"));
                return true;
            } return false;
        } else if(cameraX < towerSettingX + 0.5f && cameraX > towerSettingX - 0.5f && cameraY < towerSettingY + 1.5f && cameraY > towerSettingY - 1.5f) {
            closeTowerSet();
        } else {
            if (map.getTile(cameraX, cameraY) == 1 || map.getTile(cameraX, cameraY) == 2 || map.getTile(cameraX, cameraY) == 3) {
                closeTowerSet();
            } else {
                closeTowerSet();
                openTowerSet(cameraX, cameraY);
            }
        }
        return false;
    }

    public void representNumber(int number, float x, float y, SpriteBatch batch){

        float distanceBetweenNumbers = 0.5f;
        float width = 0.55f, height = 0.55f;

        if (number == 0){
            batch.draw(uiAtlas.findRegion("0"), x , y, width, height);
        }

        LinkedList<Integer> stack = new LinkedList<Integer>();
        while (number > 0) {
            stack.push( number % 10 );
            number = number / 10;
        }

        float i = - distanceBetweenNumbers;
        while (!stack.isEmpty()) {
            i += distanceBetweenNumbers;
            switch (stack.pop()){
                case 0:
                    batch.draw(uiAtlas.findRegion("0"), x + i, y, width, height); break;
                case 1:
                    batch.draw(uiAtlas.findRegion("1"), x + i, y, width, height); break;
                case 2:
                    batch.draw(uiAtlas.findRegion("2"), x + i, y, width, height); break;
                case 3:
                    batch.draw(uiAtlas.findRegion("3"), x + i, y, width, height); break;
                case 4:
                    batch.draw(uiAtlas.findRegion("4"), x + i, y, width, height); break;
                case 5:
                    batch.draw(uiAtlas.findRegion("5"), x + i, y, width, height); break;
                case 6:
                    batch.draw(uiAtlas.findRegion("6"), x + i, y, width, height); break;
                case 7:
                    batch.draw(uiAtlas.findRegion("7"), x + i, y, width, height); break;
                case 8:
                    batch.draw(uiAtlas.findRegion("8"), x + i, y, width, height); break;
                case 9:
                    batch.draw(uiAtlas.findRegion("9"), x + i, y, width, height); break;
                default:
                    System.out.println("Chaos ensues");
            }
        }
    }

    public void showLives(SpriteBatch batch){
        int lives = localPlayer.getLives();
        representNumber(lives,12.0f,0.0f,batch);
    }



    public void showCash(SpriteBatch batch) {
        int cash = localPlayer.getCash();
        representNumber(cash,9.0f,0.0f,batch);
    }

    public void showCashMultiPlayer(SpriteBatch batch) {

        mulPlayer = getMulPlayerFromNetwork();
        int cash = mulPlayer.getCash();
        representNumber(cash, 6.0f,0.0f,batch);
    }

    public void initMPClient(MPClient mpClient){
        this.mpClient = mpClient;
        mpClient.initEngine(engine);
        mpClient.setMulPlayer(mulPlayer);
        multiPlayer = true;
    }

    public void initMPServer(MPServer mpServer){
        this.mpServer = mpServer;
        mpServer.initEngine(engine);
        mpServer.setMulPlayer(mulPlayer);
        multiPlayer = true;
    }

    //Sends tower to the other player

    private void sendTower(String towerType, float x, float y) {
        if (mpClient == null) {
            mpServer.sendTower(towerType, x, y);
        } else if (mpServer == null) {
            mpClient.sendTower(towerType, x, y);
        }
    }

    private void sendTowerUpgrade(float x, float y){
        if (mpClient == null) {
            mpServer.sendUpgradeTower(x, y);
        } else if (mpServer == null) {
            mpClient.sendUpgradeTower(x, y);
        }
    }

    private Player getMulPlayerFromNetwork(){
        if (mpClient == null) {
            return mpServer.getMulPlayer();
        } else if (mpServer == null) {
            return mpClient.getMulPlayer();
        }
        return mulPlayer;
    }
}
