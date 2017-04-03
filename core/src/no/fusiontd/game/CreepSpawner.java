package no.fusiontd.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import no.fusiontd.components.PathFollow;
import no.fusiontd.components.Position;
import no.fusiontd.components.Render;
import no.fusiontd.components.Rotation;

public class CreepSpawner {
    Path<Vector2> path;
    Engine engine;

    public CreepSpawner(Path<Vector2> path, Engine engine) {
        this.path = path;
        this.engine = engine;
    }

    public void spawnCreep(TextureAtlas.AtlasRegion texture, Component... components) {
        Entity creep = new Entity()
                .add(new Position())
                .add(new Rotation())
                .add(new Render(texture))
                .add(new PathFollow(path));
        for (Component component : components) {
            creep.add(component);
        }
        engine.addEntity(creep);
    }
}
