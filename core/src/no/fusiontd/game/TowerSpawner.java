package no.fusiontd.game;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import no.fusiontd.Graphics;
import no.fusiontd.components.Position;
import no.fusiontd.components.Render;
import no.fusiontd.components.Targeting;

public class TowerSpawner {
    private Engine engine;
    private ImmutableArray<Entity> towers;
    private ComponentMapper<Position> mPos = ComponentMapper.getFor(Position.class);

    public TowerSpawner(Engine engine) {
        this.engine = engine;
        this.towers = engine.getEntitiesFor(Family.all(Position.class, Render.class, Targeting.class).get());
    }

    public void spawn(String region, float x, float y, Component... components) {
        for (Entity tower : towers) {
            if (mPos.get(tower).dst(x, y) < .5f) return;
        }
        Entity tower = new Entity()
                .add(new Position(x, y, 0))
                .add(new Render(Graphics.getRegion(region)))
                .add(new Targeting(5, .5f));
        for (Component component : components) {
            tower.add(component);
        }
        engine.addEntity(tower);
    }
}
