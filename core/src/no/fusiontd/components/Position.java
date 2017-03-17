package no.fusiontd.components;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

public class Position extends Component{
    public Vector2 vec = new Vector2();

    Position(float x,float y) {
        vec.set(x,y);
    }
}
