package no.fusiontd.game;

import com.badlogic.ashley.core.Engine;
import no.fusiontd.screens.PlayScreen;
import no.fusiontd.systems.PathSystem;
import no.fusiontd.systems.RenderSystem;
import no.fusiontd.systems.TargetingSystem;
import no.fusiontd.systems.VelocitySystem;

public class EntityComponentManager extends Engine {

    public EntityComponentManager(PlayScreen view) {
        super();
        addSystem(new VelocitySystem());
        addSystem(new RenderSystem(view.batch));
        addSystem(new PathSystem());
        addSystem(new TargetingSystem());
    }
}
