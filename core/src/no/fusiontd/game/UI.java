package no.fusiontd.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

import no.fusiontd.FusionTD;
import no.fusiontd.Graphics;
import no.fusiontd.MPAlternative.MPClient;
import no.fusiontd.MPAlternative.MPServer;
import no.fusiontd.components.Buyable;
import no.fusiontd.components.Geometry;

public class UI{

    private FusionTD game;
    private Player localPlayer, mulPlayer;
    private boolean showTowerSet = false;
    private float towerSettingX, towerSettingY;
    private EntityComponentManager engine;
    private MPClient mpClient;
    private MPServer mpServer;

    public UI(FusionTD game, Player localPlayer, Player mulPlayer, EntityComponentManager engine) {
        this.game = game; this.localPlayer = localPlayer; this.mulPlayer = mulPlayer;
        this.showTowerSet = false;
        this.engine = engine;
    }

    public void render(SpriteBatch batch) {
        showLives(batch);
        showCash(batch);
        if (showTowerSet){
            towerSetMenu(towerSettingX, towerSettingY, batch);
        }
    }

    public void selectTower(float cameraX, float cameraY) {
        Entity e = engine.getTowerAt(cameraX, cameraY);
        engine.upgradeEntity(e);
        localPlayer.addCash(-e.getComponent(Buyable.class).cost);
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

    public void towerSetMenu(float cameraX, float cameraY, SpriteBatch batch) {
        batch.draw(Graphics.getRegion("missileTower"), cameraX - 0.5f, cameraY - 1.5f, 1f, 1f);
        batch.draw(Graphics.getRegion("flameTower"), cameraX - 0.5f , cameraY - 0.75f, 1f, 1f);
        batch.draw(Graphics.getRegion("sniperTower"), cameraX - 0.5f , cameraY, 1f, 1f);
        batch.draw(Graphics.getRegion("red_button"), 15.0f , 0.2f, 0.7f, 0.7f);
    }

    public boolean towerSet(float cameraX, float cameraY){
        System.out.println(cameraX + "," + cameraY + ";" + towerSettingX + "," + towerSettingY);
        if(cameraX > towerSettingX - 0.35f && cameraX < towerSettingX + 0.35f && cameraY > towerSettingY - 0.5f && cameraY < towerSettingY + 0.5f){
            if (localPlayer.getCash() >= 5) {
                showTowerSet = false;
                engine.spawnTower("flameTower", new Geometry(towerSettingX, towerSettingY, 0, .5f));

                //Sends tower to the other player
                if(mpClient == null){
                    mpServer.sendTower("flameTower", engine.getTowerAt(cameraX, cameraY));
                    }
                else if( mpServer == null){
                    mpClient.sendTower("flameTower", engine.getTowerAt(cameraX, cameraY));
                    }

                localPlayer.addCash(-engine.getCost("flameTower"));
                return true;
            } return false;
        } else if(cameraX > towerSettingX - 0.35f && cameraX < towerSettingX + 0.35f && cameraY > towerSettingY - 1.5f && cameraY < towerSettingY - 0.5f){
            if (localPlayer.getCash() >= 2) {
                showTowerSet = false;
                engine.spawnTower("cannonTower", new Geometry(towerSettingX, towerSettingY, 0, .5f));
                localPlayer.addCash(-engine.getCost("cannonTower"));
                return true;
            } return false;
        } else if(cameraX > towerSettingX - 0.35f && cameraX < towerSettingX + 0.35f && cameraY > towerSettingY - 0.5f && cameraY < towerSettingY + 0.5f){
            if (localPlayer.getCash() >= 5) {
                showTowerSet = false;
                engine.spawnTower("flameTower", new Geometry(towerSettingX, towerSettingY, 0, .5f));
                localPlayer.addCash(-engine.getCost("flameTower"));
                return true;
            } return false;
        } else if (cameraX > towerSettingX - 0.35f && cameraX < towerSettingX + 0.35f && cameraY > towerSettingY + 0.5f && cameraY < towerSettingY + 1.5f){
            if (localPlayer.getCash() >= 20) {
                showTowerSet = false;
                engine.spawnTower("sniperTower", new Geometry(towerSettingX, towerSettingY, 0, .5f));
                localPlayer.addCash(-engine.getCost("sniperTower"));
                return true;
            } return false;
        } else if (cameraX > towerSettingX - 0.35f && cameraX < towerSettingX + 0.35f && cameraY > towerSettingY + 1.5f && cameraY < towerSettingY + 2.5f){
            if (localPlayer.getCash() >= 20) {
                showTowerSet = false;
                engine.spawnTower("missileTower", new Geometry(towerSettingX, towerSettingY, 0, .5f));
                localPlayer.addCash(-engine.getCost("missileTower"));
                return true;
            } return false;
        } else if (cameraX > 15.0f && cameraX < 15.7f && cameraY > 0.2f & cameraY < 0.9f){
            game.returnToMenu();
            return false;
        }
        return false;
    }

    public void showLives(SpriteBatch batch){

        int lives = localPlayer.getLives();

        if (lives == 0){
            batch.draw(Graphics.getRegion("zeros"), 13.0f , 0.2f, 1f, 1f);
        }

        LinkedList<Integer> stack = new LinkedList<Integer>();
        while (lives > 0) {
            stack.push( lives % 10 );
            lives = lives / 10;
        }

        float i = -0.4f;
        while (!stack.isEmpty()) {
            i += 0.4f;
            switch (stack.pop()){
                case 0:
                    batch.draw(Graphics.getRegion("zeros"), 13.0f + i, 0.2f, 1f, 1f); break;
                case 1:
                    batch.draw(Graphics.getRegion("one"), 13.0f + i, 0.2f, 1f, 1f); break;
                case 2:
                    batch.draw(Graphics.getRegion("two"), 13.0f + i, 0.2f, 1f, 1f); break;
                case 3:
                    batch.draw(Graphics.getRegion("three"), 13.0f + i, 0.2f, 1f, 1f); break;
                case 4:
                    batch.draw(Graphics.getRegion("four"), 13.0f + i, 0.2f, 1f, 1f); break;
                case 5:
                    batch.draw(Graphics.getRegion("five"), 13.0f + i, 0.2f, 1f, 1f); break;
                case 6:
                    batch.draw(Graphics.getRegion("six"), 13.0f + i, 0.2f, 1f, 1f); break;
                case 7:
                    batch.draw(Graphics.getRegion("seven"), 13.0f + i, 0.2f, 1f, 1f); break;
                case 8:
                    batch.draw(Graphics.getRegion("eight"), 13.0f + i, 0.2f, 1f, 1f); break;
                case 9:
                    batch.draw(Graphics.getRegion("nine"), 13.0f + i, 0.2f, 1f, 1f); break;
                default:
                    System.out.println("lives:" + localPlayer.getLives());
            }
        }
    }



    public void showCash(SpriteBatch batch) {

        int cash = localPlayer.getCash();

        if (cash == 0){
            batch.draw(Graphics.getRegion("zeros"), 11.0f, 0.2f, 1f, 1f);
        }

        LinkedList<Integer> stack = new LinkedList<Integer>();
        while (cash > 0) {
            stack.push( cash % 10 );
            cash = cash / 10;
        }

        float i = -0.4f;
        while (!stack.isEmpty()) {
            i += 0.4f;
            switch (stack.pop()){
                case 0:
                    batch.draw(Graphics.getRegion("zeros"), 11.0f + i, 0.2f, 1f, 1f); break;
                case 1:
                    batch.draw(Graphics.getRegion("one"), 11.0f + i, 0.2f, 1f, 1f); break;
                case 2:
                    batch.draw(Graphics.getRegion("two"), 11.0f + i, 0.2f, 1f, 1f); break;
                case 3:
                    batch.draw(Graphics.getRegion("three"), 11.0f + i, 0.2f, 1f, 1f); break;
                case 4:
                    batch.draw(Graphics.getRegion("four"), 11.0f + i, 0.2f, 1f, 1f); break;
                case 5:
                    batch.draw(Graphics.getRegion("five"), 11.0f + i, 0.2f, 1f, 1f); break;
                case 6:
                    batch.draw(Graphics.getRegion("six"), 11.0f + i, 0.2f, 1f, 1f); break;
                case 7:
                    batch.draw(Graphics.getRegion("seven"), 11.0f + i, 0.2f, 1f, 1f); break;
                case 8:
                    batch.draw(Graphics.getRegion("eight"), 11.0f + i, 0.2f, 1f, 1f); break;
                case 9:
                    batch.draw(Graphics.getRegion("nine"), 11.0f + i, 0.2f, 1f, 1f); break;
                default:
                    System.out.println("cash:" + localPlayer.getCash());
            }
        }
    }

    public void initMPClient(MPClient mpClient){
        this.mpClient = mpClient;
        mpClient.initEngine(engine);
    }

    public void initMPServer(MPServer mpServer){
        this.mpServer = mpServer;
        mpServer.initEngine(engine);
    }
}
