package no.fusiontd.game;

import com.badlogic.ashley.core.*;
import no.fusiontd.components.AddOnRemove;
import no.fusiontd.components.Placement;
import no.fusiontd.screens.PlayScreen;
import no.fusiontd.systems.*;

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
}
