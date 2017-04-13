package no.fusiontd.components;

import com.badlogic.gdx.math.Vector2;
import no.fusiontd.CloneableComponent;

import java.util.Arrays;
import java.util.List;

public class AddOnRemove implements CloneableComponent<AddOnRemove> {
    public List<CloneableComponent> newEntity;
    public Vector2 displacement;

    public AddOnRemove(Vector2 displacement, CloneableComponent... cloneableComponents) {
        this(displacement, Arrays.asList(cloneableComponents));
    }

    public AddOnRemove(Vector2 displacement, List<CloneableComponent> newEntity) {
        this.newEntity = newEntity;
        this.displacement = displacement;
    }

    @Override
    public AddOnRemove cloneComponent() {
        return new AddOnRemove(displacement, newEntity);
    }
}
