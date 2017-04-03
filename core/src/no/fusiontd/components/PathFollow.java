package no.fusiontd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;

public class PathFollow implements Component {
    public float time;
    public Path<Vector2> path;

    public PathFollow(Path<Vector2> path) {
        this.path = path;
    }
}
