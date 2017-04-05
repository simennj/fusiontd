package no.fusiontd.game;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import no.fusiontd.CloneableComponent;
import no.fusiontd.Graphics;
import no.fusiontd.components.*;

import java.util.LinkedList;
import java.util.List;

public class TowerSpawner {
    private Engine engine;
    private ImmutableArray<Entity> towers;
    private ComponentMapper<Placement> mPos = ComponentMapper.getFor(Placement.class);

    public TowerSpawner(Engine engine) {
        this.engine = engine;
        this.towers = engine.getEntitiesFor(Family.all(Placement.class, Render.class, Targeting.class).get());
    }

    public void spawn(String region, float x, float y, Component... components) {
        for (Entity tower : towers) {
            if (mPos.get(tower).dst(x, y) < .5f) return;
        }
        List<CloneableComponent> missileComponents = new LinkedList<CloneableComponent>();
        missileComponents.add(new Render(Graphics.getRegion("missile")));
        missileComponents.add(new Timer(1));
        missileComponents.add(new Attack(.5f));
        missileComponents.add(new Durability(12));
        Entity tower = new Entity()
                .add(new Placement(x, y, 0))
                .add(new Render(Graphics.getRegion(region)))
                .add(new Targeting(5, .5f, missileComponents));
        for (Component component : components) {
            tower.add(component);
        }
        engine.addEntity(tower);
    }
}
