package no.fusiontd.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import no.fusiontd.Graphics;
import no.fusiontd.components.*;

public class TowerSpawner {
    private EntityComponentManager engine;
    private ImmutableArray<Entity> towers;
    private ComponentMapper<Placement> mPos = ComponentMapper.getFor(Placement.class);

    public TowerSpawner(EntityComponentManager engine) {
        this.engine = engine;
        this.towers = engine.getEntitiesFor(Family.all(Placement.class, Render.class, Targeting.class).get());
    }

    public void spawn(String region, float x, float y) {
        for (Entity tower : towers) {
            if (mPos.get(tower).dst(x, y) < .5f) return;
        }
        Entity tower = engine.spawn(
                new Placement(x, y, 0),
                new Render(Graphics.getRegion(region)),
                new Targeting(5, .5f,
                        new Render(Graphics.getRegion("missile")),
                        new Timer(1),
                        new Attack(.5f),
                        new Durability(12)
                )
        );
    }
}
