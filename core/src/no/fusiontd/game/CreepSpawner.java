package no.fusiontd.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import no.fusiontd.components.*;

public class CreepSpawner {
    public int waveNumber = 0;
    private Path<Vector2> path;
    private Engine engine;
    private WaveReader waveReader;
    private CreepWave currentWave;
    private float timer;
    private Vector2 startPosition = new Vector2();
    private boolean waveActive;
    private TextureAtlas spriteAtlas = new TextureAtlas("sprites.atlas");

    public CreepSpawner(Path<Vector2> path, Engine engine) {
        this.path = path;
        path.valueAt(this.startPosition, 0);
        this.engine = engine;
        waveReader = new WaveReader("1");
    }

    public void update(float deltatime) {
        timer += deltatime;
        if (waveActive && timer > currentWave.currentDelayBetweenCreeps()) {
            waveActive = !currentWave.finished();
            timer = 0;
            CreepBluePrint creepBluePrint = currentWave.popCreep();
            spawnCreep(creepBluePrint.texture, creepBluePrint.life, creepBluePrint.speed, creepBluePrint.reward);
        }
    }

    public void startNextWave() {
        if (waveReader.hasNextWave()) {
            currentWave = waveReader.popWave();
            waveNumber++;
            waveActive = true;
        } else {
            System.out.println("You have won!");
        }
    }

    private void spawnCreep(String region, int life, float speed, int reward, Component... components) {
        Entity creep = new Entity()
                .add(new Geometry(startPosition, 0, .5f))
                .add(new Attackable(.1f))
                .add(new Durability(life))
                .add(new Value(reward))
                .add(new Render(spriteAtlas.findRegions(region)))
                .add(new PathFollow(path, speed));
        for (Component component : components) {
            creep.add(component);
        }
        engine.addEntity(creep);
    }

}
