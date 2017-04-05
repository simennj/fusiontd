package no.fusiontd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;

public class PathFollow implements Component {
    public float time;
    public Path<Vector2> path;
    public float speed;
    public float length;

    public PathFollow(Path<Vector2> path, float speed) {
        this.path = path;
        this.speed = speed;
        this.length = path.approxLength(100);
    }

}
