package no.fusiontd.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;

import no.fusiontd.components.Position;
import no.fusiontd.components.Velocity;


public class VelocitySystem extends IteratingSystem {
    ComponentMapper<Position> positionMapper;
    ComponentMapper<Velocity> velocityMapper;
    float delta = 1/60;
    public VelocitySystem(Aspect.Builder aspect) {
        super(Aspect.all(Position.class, Velocity.class));
    }

    @Override
    protected void process(int entityId) {
        Position position = positionMapper.get(entityId);
        position.vec.add(velocityMapper.get(entityId).vec.scl(delta));
    }
}
