package no.fusiontd.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import no.fusiontd.components.Position;
import no.fusiontd.components.Render;
import no.fusiontd.components.Rotation;
import no.fusiontd.components.Targeting;

public class TowerSpawner {
    Engine engine;

    public TowerSpawner(Engine engine) {
        this.engine = engine;
    }

    public void spawn(TextureAtlas.AtlasRegion texture, float x, float y, Component... components) {
        Entity tower = new Entity()
                .add(new Position(x, y))
                .add(new Rotation())
                .add(new Render(texture))
                .add(new Targeting());
        for (Component component : components) {
            tower.add(component);
        }
        engine.addEntity(tower);
    }
}
