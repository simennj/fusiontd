package no.fusiontd.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import no.fusiontd.components.Attackable;
import no.fusiontd.components.Durability;
import no.fusiontd.components.PathFollow;
import no.fusiontd.components.Position;
import no.fusiontd.components.Render;
import no.fusiontd.components.Rotation;

public class CreepSpawner {
    Path<Vector2> path;
    Engine engine;
    ArrayList<ArrayList<Entity>> creeps = new ArrayList<ArrayList<Entity>>();
    TextureAtlas creepAtlas = new TextureAtlas("tiles.atlas");
    TextureAtlas.AtlasRegion testCreep;
    int wave = 0;

    private void initializeTextures() {
        testCreep = creepAtlas.findRegion("271");
    }

    private void waveMaker() {
        wave++;
        for (Entity creep : creeps.get(wave)) {
            spawnCreep(testCreep, creep.getComponent(Durability.class).life);
        }

    }

    public CreepSpawner(Path<Vector2> path, Engine engine) {
        this.path = path;
        this.engine = engine;
    }



    public void spawnCreep(TextureAtlas.AtlasRegion texture, int life,  Component... components) {
        Entity creep = new Entity()
                .add(new Position())
                .add(new Rotation())
                .add(new Attackable(life))
                .add(new Render(texture))
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
