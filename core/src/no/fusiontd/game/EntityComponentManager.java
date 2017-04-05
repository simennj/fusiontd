package no.fusiontd.game;

import com.badlogic.ashley.core.*;
import no.fusiontd.CloneableComponent;
import no.fusiontd.components.AddOnRemove;
import no.fusiontd.components.Placement;
import no.fusiontd.screens.PlayScreen;
import no.fusiontd.systems.*;

import java.util.Arrays;

public class EntityComponentManager extends Engine {

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

}
