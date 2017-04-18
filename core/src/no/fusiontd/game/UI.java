package no.fusiontd.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

import no.fusiontd.FusionTD;
import no.fusiontd.Graphics;
import no.fusiontd.components.Geometry;

public class UI{

    private FusionTD game;
    private Player localPlayer, mulPlayer;
    private boolean showTowerSet = false;
    private float towerSettingX, towerSettingY;
    private EntityComponentManager engine;

    public UI(FusionTD game, Player localPlayer, Player mulPlayer, EntityComponentManager engine) {
        this.game = game; this.localPlayer = localPlayer; this.mulPlayer = mulPlayer;
        this.showTowerSet = false;
        this.engine = engine;
    }

    public void render(float delta, SpriteBatch batch, float tilesize) {
        showLives(batch);
        showCash(batch);
        if (showTowerSet){
            towerSetMenu(towerSettingX, towerSettingY, batch);
        }
    }

    public void selectTower(float cameraX, float cameraY) {
    }

    public void selectCreep(float cameraX, float cameraY) {
    }

    public void openTowerSet(float cameraX, float cameraY){
        showTowerSet = true; towerSettingX = cameraX; towerSettingY = cameraY;
    }

    public boolean isTowerSetting(){
        System.out.println(showTowerSet);
        return showTowerSet;
    }

    public void closeTowerSet(){
        showTowerSet = false;
    }

    public void towerSetMenu(float cameraX, float cameraY, SpriteBatch batch) {
        batch.draw(Graphics.getRegion("missileTower"), cameraX - 0.5f, cameraY - 1.5f, 1f, 1f);
        batch.draw(Graphics.getRegion("flameTower"), cameraX - 0.5f , cameraY - 0.75f, 1f, 1f);
        batch.draw(Graphics.getRegion("sniperTower"), cameraX - 0.5f , cameraY, 1f, 1f);
    }

    public void towerSet(float cameraX, float cameraY){
        System.out.println(cameraX + "," + cameraY + ";" + towerSettingX + "," + towerSettingY);
        if(cameraX > towerSettingX - 0.35f && cameraX < towerSettingX + 0.35f && cameraY > towerSettingY - 0.5f && cameraY < towerSettingY + 0.5f){
            showTowerSet = false;
            engine.spawnTower("flameTower", new Geometry(towerSettingX, towerSettingY, 0, .5f));
        } else if(cameraX > towerSettingX - 0.35f && cameraX < towerSettingX + 0.35f && cameraY > towerSettingY - 1.5f && cameraY < towerSettingY - 0.5f){
            showTowerSet = false;
            engine.spawnTower("missileTower2", new Geometry(towerSettingX, towerSettingY, 0, .5f));
        } else if (cameraX > towerSettingX - 0.35f && cameraX < towerSettingX + 0.35f && cameraY > towerSettingY + 0.5f && cameraY < towerSettingY + 1.5f){
            showTowerSet = false;
            engine.spawnTower("sniperTower", new Geometry(towerSettingX, towerSettingY, 0, .5f));
        }
    }

    public void showLives(SpriteBatch batch){

        int lives = localPlayer.getLives();
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
                    batch.draw(Graphics.getRegion("zeros"), 0f + i, 1.5f, 1f, 1f); break;
                case 1:
                    batch.draw(Graphics.getRegion("one"), 0f + i, 1.5f, 1f, 1f); break;
                case 2:
                    batch.draw(Graphics.getRegion("two"), 0f + i, 1.5f, 1f, 1f); break;
                case 3:
                    batch.draw(Graphics.getRegion("three"), 0f + i, 1.5f, 1f, 1f); break;
                case 4:
                    batch.draw(Graphics.getRegion("four"), 0f + i, 1.5f, 1f, 1f); break;
                case 5:
                    batch.draw(Graphics.getRegion("five"), 0f + i, 1.5f, 1f, 1f); break;
                case 6:
                    batch.draw(Graphics.getRegion("six"), 0f + i, 1.5f, 1f, 1f); break;
                case 7:
                    batch.draw(Graphics.getRegion("seven"), 0f + i, 1.5f, 1f, 1f); break;
                case 8:
                    batch.draw(Graphics.getRegion("eight"), 0f + i, 1.5f, 1f, 1f); break;
                case 9:
                    batch.draw(Graphics.getRegion("nine"), 0f + i, 1.5f, 1f, 1f); break;
                default:
                    System.out.println("lives:" + localPlayer.getLives());
            }
        }
    }

    public void showCash(SpriteBatch batch) {
        int cash = localPlayer.getCash();
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
                    batch.draw(Graphics.getRegion("zeros"), 5f + i, 1.5f, 1f, 1f); break;
                case 1:
                    batch.draw(Graphics.getRegion("one"), 5f + i, 1.5f, 1f, 1f); break;
                case 2:
                    batch.draw(Graphics.getRegion("two"), 5f + i, 1.5f, 1f, 1f); break;
                case 3:
                    batch.draw(Graphics.getRegion("three"), 5f + i, 1.5f, 1f, 1f); break;
                case 4:
                    batch.draw(Graphics.getRegion("four"), 5f + i, 1.5f, 1f, 1f); break;
                case 5:
                    batch.draw(Graphics.getRegion("five"), 5f + i, 1.5f, 1f, 1f); break;
                case 6:
                    batch.draw(Graphics.getRegion("six"), 5f + i, 1.5f, 1f, 1f); break;
                case 7:
                    batch.draw(Graphics.getRegion("seven"), 5f + i, 1.5f, 1f, 1f); break;
                case 8:
                    batch.draw(Graphics.getRegion("eight"), 5f + i, 1.5f, 1f, 1f); break;
                case 9:
                    batch.draw(Graphics.getRegion("nine"), 5f + i, 1.5f, 1f, 1f); break;
                default:
                    System.out.println("cash:" + localPlayer.getCash());
            }
        }
    }
}
