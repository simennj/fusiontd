package no.fusiontd.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import no.fusiontd.Graphics;
import no.fusiontd.components.*;

public class CreepSpawner {
    private Path<Vector2> path;
    private Engine engine;
    private WaveReader waveReader;
    private CreepWave currentWave;
    private float timer;
    private Vector2 startPosition = new Vector2();
    private boolean waveActive;

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
            spawnCreep(creepBluePrint.texture, creepBluePrint.life, creepBluePrint.speed);
        }
    }

    public void startNextWave() {
        currentWave = waveReader.popWave();
        waveActive = true;
    }

    private void spawnCreep(String region, float life, float speed, Component... components) {
        Entity creep = new Entity()
                .add(new Geometry(startPosition, 0, .5f))
                .add(new Attackable(.1f))
                .add(new Durability(life))
                .add(new Render(Graphics.getRegion(region)))
                .add(new PathFollow(path, speed));
        for (Component component : components) {
            creep.add(component);
        }
        engine.addEntity(creep);
    }

}
