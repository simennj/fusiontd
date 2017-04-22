package no.fusiontd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import no.fusiontd.components.Geometry;
import no.fusiontd.components.PathFollow;

public class PathSystem extends IteratingSystem {

    private ComponentMapper<Geometry> mPos = ComponentMapper.getFor(Geometry.class);
    private ComponentMapper<PathFollow> mPath = ComponentMapper.getFor(PathFollow.class);

    public PathSystem() {
        super(Family.all(Geometry.class, PathFollow.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PathFollow path = mPath.get(entity);
        Geometry geometry = mPos.get(entity);
        path.time = path.time + deltaTime * path.speed / path.length;
        path.path.valueAt(geometry, path.time);
        Vector2 rotation = new Vector2();
        path.path.derivativeAt(rotation, path.time);
        if (path.time > 1) {
            getEngine().removeEntity(entity);
        }
    }
}
