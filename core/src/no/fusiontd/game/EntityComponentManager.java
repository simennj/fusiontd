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
    private ComponentMapper<Geometry> mPos = ComponentMapper.getFor(Geometry.class);
    private ImmutableArray<Entity> creeps;

    public EntityComponentManager(PlayScreen view) {
        super();
        addSystem(new VelocitySystem());
        addSystem(new RenderSystem(view.batch));
        addSystem(new PathSystem());
        addSystem(new TargetingSystem());
        addSystem(new TimerSystem());
        addSystem(new CollisionSystem());
        addEntityListener(Family.all(AddOnRemove.class, Geometry.class).get(), new EntityListener() {
            ComponentMapper<AddOnRemove> removeActionMapper = ComponentMapper.getFor(AddOnRemove.class);
            ComponentMapper<Geometry> positionMapper = ComponentMapper.getFor(Geometry.class);

            @Override
            public void entityAdded(Entity entity) {

            }

            @Override
            public void entityRemoved(Entity entity) {
                AddOnRemove addOnRemove = removeActionMapper.get(entity);
                Entity newEntity = spawn(addOnRemove.newEntity);
                Geometry geometry = positionMapper.get(newEntity);
                Geometry entityGeometry = positionMapper.get(entity);
                if (geometry == null) {
                    newEntity.add(entityGeometry.cloneComponent());
                    geometry = positionMapper.get(newEntity);
                }
                geometry.add(addOnRemove.displacement.cpy().rotate(entityGeometry.rotation));
            }
        });
        towers = getEntitiesFor(Family.all(Geometry.class, Render.class, Targeting.class).get());
        creeps = getEntitiesFor(Family.all(Geometry.class, Attackable.class, Durability.class).get());

        blueprints.put("missileTower", Arrays.<CloneableComponent>asList(
                new Render("missileTower"),
                new Targeting(5, .5f,
                        new Render(Graphics.getRegion("missile")),
                        new Timer(1),
                        new Attack(.5f),
                        new Durability(12),
                        new Velocity(new Vector2(10, 0)),
                        new AddOnRemove(new Vector2(0, .5f),
                                new Render("explosion"),
                                new Timer(2)
                        )
                )
        ));

        blueprints.put("flameTower", Arrays.<CloneableComponent>asList(
                new Render("flameTower"),
                new Targeting(1, .05f, new Vector2(0, .5f),
                        new Render(Graphics.getRegion("flame")),
                        new Timer(1),
                        new Attack(.05f),
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

    public void spawnTower(String name, Geometry geometry) {
        for (Entity tower : towers) {
            if (mPos.get(tower).dst(geometry) < geometry.radius) return;
        }
        Entity tower = spawn(blueprints.get(name));
        tower.add(geometry);
    }

    public boolean checkTower(Geometry geometry){
        for (Entity tower : towers) {
            if (mPos.get(tower).dst(geometry) < geometry.radius) return true;
        }
        return false;
    }

    public boolean checkCreep(Geometry geometry){
        for (Entity creep : creeps) {
            if (mPos.get(creep).dst(geometry) < geometry.radius) return true;
        }
        return false;
    }


}
