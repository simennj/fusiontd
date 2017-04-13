package no.fusiontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

import no.fusiontd.FusionTD;
import no.fusiontd.Graphics;

public class UI{

    private FusionTD game;
    private Player localPlayer, mulPlayer;

    public UI(FusionTD game, Player localPlayer, Player mulPlayer) {
        this.game = game; this.localPlayer = localPlayer; this.mulPlayer = mulPlayer;
    }

    public void render(float delta, SpriteBatch batch, float tilesize) {
        showLives(batch);
        showCash(batch);
    }

    public void selectTower(float cameraX, float cameraY) {
    }

    public void selectCreep(float cameraX, float cameraY) {
    }

    public void towerSet(float cameraX, float cameraY) {
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
