package no.fusiontd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import no.fusiontd.components.Timer;

public class TimerSystem extends IteratingSystem {
    ComponentMapper<Timer> mTim = ComponentMapper.getFor(Timer.class);

    public TimerSystem() {
        super(Family.all(Timer.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Timer timer = mTim.get(entity);
        timer.time -= deltaTime;
        if (timer.time <= 0) {
            getEngine().removeEntity(entity);
        }
    }
}
