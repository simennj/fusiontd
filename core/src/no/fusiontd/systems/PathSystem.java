package no.fusiontd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import no.fusiontd.components.PathFollow;
import no.fusiontd.components.Placement;

public class PathSystem extends IteratingSystem {

    private ComponentMapper<Placement> mPos = ComponentMapper.getFor(Placement.class);
    private ComponentMapper<PathFollow> mPath = ComponentMapper.getFor(PathFollow.class);

    public PathSystem() {
        super(Family.all(Placement.class, PathFollow.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PathFollow path = mPath.get(entity);
        Vector2 position = mPos.get(entity);
        path.time = path.time + deltaTime * path.speed / path.length;
        path.path.valueAt(position, path.time);
        if (path.time > 1) {
            getEngine().removeEntity(entity);
        }
    }
}
