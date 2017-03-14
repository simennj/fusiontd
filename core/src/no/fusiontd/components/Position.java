package no.fusiontd.components;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

public class Position extends Component{
    public float x,y;

    Position(float x,float y) {
        this.x = x;
        this.y = y;
    }
}
