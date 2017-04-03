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
    Path<Vector2> path;
    Engine engine;
    ArrayList<ArrayList<Entity>> creeps = new ArrayList<ArrayList<Entity>>();
    int wave = 0;

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

    public void spawnCreep(String region, int life, Component... components) {
        Entity creep = new Entity()
                .add(new Position())
                .add(new Rotation())
                .add(new Attackable(life))
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
