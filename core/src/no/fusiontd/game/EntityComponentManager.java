package no.fusiontd.game;

import com.badlogic.ashley.core.*;
import no.fusiontd.components.AddOnRemove;
import no.fusiontd.components.Position;
import no.fusiontd.components.Rotation;
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
        addEntityListener(Family.all(AddOnRemove.class, Position.class).get(), new EntityListener() {
            ComponentMapper<AddOnRemove> removeActionMapper = ComponentMapper.getFor(AddOnRemove.class);
            ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
            ComponentMapper<Rotation> rotationMapper = ComponentMapper.getFor(Rotation.class);

            @Override
            public void entityAdded(Entity entity) {

            }

            @Override
            public void entityRemoved(Entity entity) {

            }
        });
    }
}
