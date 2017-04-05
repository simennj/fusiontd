package no.fusiontd.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import no.fusiontd.Graphics;
import no.fusiontd.components.*;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class CreepSpawner {
    private Path<Vector2> path;
    private Engine engine;
    private LinkedList<CreepWave> creepWaves = new LinkedList<CreepWave>();
    private CreepWave currentWave;
    private float timer;
    private Vector2 startPosition = new Vector2();

    public CreepSpawner(Path<Vector2> path, Engine engine) {
        this.path = path;
        path.valueAt(this.startPosition, 0);
        this.engine = engine;
        try {
            creepWaves.add(new CreepWave("1"));
            currentWave = creepWaves.pop();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(float deltatime) {
        timer += deltatime;
        if (timer > currentWave.currentDelayBetweenCreeps()) {
            timer = 0;
            CreepWave.CreepBluePrint creepBluePrint = currentWave.popCreep();
            spawnCreep(creepBluePrint.texture, creepBluePrint.life, creepBluePrint.speed);
        }
    }

    public void spawnCreep(String region, float life, float speed, Component... components) {
        Entity creep = new Entity()
                .add(new Position(startPosition, 0))
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
