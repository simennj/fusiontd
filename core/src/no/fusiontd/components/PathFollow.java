package no.fusiontd.components;

import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import no.fusiontd.CloneableComponent;

public class PathFollow implements CloneableComponent<PathFollow> {
    public float time;
    public Path<Vector2> path;
    public float speed;
    public float length;

    public PathFollow(Path<Vector2> path, float speed) {
        this.path = path;
        this.speed = speed;
        this.length = path.approxLength(100);
    }

    @Override
    public PathFollow cloneComponent() {
        return new PathFollow(path, speed);
    }
}
