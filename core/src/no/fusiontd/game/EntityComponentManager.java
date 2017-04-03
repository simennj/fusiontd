package no.fusiontd.game;

import com.badlogic.ashley.core.Engine;
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
    }
}
