package no.fusiontd.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import no.fusiontd.Graphics;
import no.fusiontd.components.*;

import java.util.ArrayList;

public class CreepSpawner {
    private Path<Vector2> path;
    private Engine engine;
    private ArrayList<ArrayList<Entity>> creeps = new ArrayList<ArrayList<Entity>>();
    private int wave = 0;
    private float timer;

    public CreepSpawner(Path<Vector2> path, Engine engine) {
        this.path = path;
        this.engine = engine;
    }

    private void waveMaker() {
        wave++;
        for (Entity creep : creeps.get(wave)) {
            spawnCreep("plane", creep.getComponent(Durability.class).life);
        }

    }

    public void update(float deltatime) {
        timer += deltatime;
        if (timer > .5f) {
            timer = 0;
            spawnCreep("plane", 24);
        }
    }

    public void spawnCreep(String region, float life, Component... components) {
        Entity creep = new Entity()
                .add(new Position())
                .add(new Rotation())
                .add(new Attackable(.1f))
                .add(new Durability(12))
                .add(new Render(Graphics.getRegion(region)))
                .add(new PathFollow(path));
        for (Component component : components) {
            creep.add(component);
        }
        engine.addEntity(creep);
    }


    private void initWave() {
        for (int i = 0; i < 30; i++) {
            creeps.add(new ArrayList<Entity>());
            for (int j = 0; j < 30; j++) {
                creeps.get(i).set(j, new Entity().add(new Durability(j)));
            }
        }
    }
}
