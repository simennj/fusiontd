package no.fusiontd.game;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import no.fusiontd.CloneableComponent;
import no.fusiontd.Graphics;
import no.fusiontd.components.*;
import no.fusiontd.screens.PlayScreen;
import no.fusiontd.systems.*;

import java.util.Arrays;
import java.util.Collection;

public class EntityComponentManager extends Engine {

    private final ObjectMap<String, Collection<CloneableComponent>> blueprints = new ObjectMap<String, Collection<CloneableComponent>>();
    private ImmutableArray<Entity> towers;
    private ComponentMapper<Placement> mPos = ComponentMapper.getFor(Placement.class);

    public EntityComponentManager(PlayScreen view) {
        super();
        addSystem(new VelocitySystem());
        addSystem(new RenderSystem(view.batch));
        addSystem(new PathSystem());
        addSystem(new TargetingSystem());
        addSystem(new TimerSystem());
        addSystem(new CollisionSystem());
        addEntityListener(Family.all(AddOnRemove.class, Placement.class).get(), new EntityListener() {
            ComponentMapper<AddOnRemove> removeActionMapper = ComponentMapper.getFor(AddOnRemove.class);
            ComponentMapper<Placement> positionMapper = ComponentMapper.getFor(Placement.class);

            @Override
            public void entityAdded(Entity entity) {

            }

            @Override
            public void entityRemoved(Entity entity) {

            }
        });
        towers = getEntitiesFor(Family.all(Placement.class, Render.class, Targeting.class).get());

        blueprints.put("missileTower", Arrays.<CloneableComponent>asList(
                new Render("missileTower"),
                new Targeting(5, .5f,
                        new Render(Graphics.getRegion("missile")),
                        new Timer(1),
                        new Attack(.5f),
                        new Durability(12),
                        new Velocity(new Vector2(10, 0))
                )
        ));

        blueprints.put("flameTower", Arrays.<CloneableComponent>asList(
                new Render("flameTower"),
                new Targeting(1, .5f, new Vector2(0, .5f),
                        new Render(Graphics.getRegion("flame")),
                        new Timer(1),
                        new Attack(.5f),
                        new Durability(60)
                )
        ));

    }

    public Entity spawn(CloneableComponent... components) {
        return spawn(Arrays.asList(components));
    }

    public Entity spawn(Iterable<CloneableComponent> components) {
        Entity entity = new Entity();
        for (CloneableComponent component : components) {
            entity.add(component.cloneComponent());
        }
        addEntity(entity);
        return entity;
    }

    public void spawnTower(String name, float x, float y) {
        for (Entity tower : towers) {
            if (mPos.get(tower).dst(x, y) < .5f) return;
        }
        Entity tower = spawn(blueprints.get(name));
        tower.add(new Placement(x, y, 0));
    }

}
