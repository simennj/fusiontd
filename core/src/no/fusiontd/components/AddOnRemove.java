package no.fusiontd.components;

import com.badlogic.gdx.math.Vector2;
import no.fusiontd.CloneableComponent;
import no.fusiontd.game.CloneableComponentList;

public class AddOnRemove implements CloneableComponent<AddOnRemove> {
    public CloneableComponentList newEntity;
    public Vector2 displacement;

    public AddOnRemove(Vector2 displacement, CloneableComponent... cloneableComponents) {
        this(displacement, new CloneableComponentList(cloneableComponents));
    }

    public AddOnRemove(Vector2 displacement, CloneableComponentList newEntity) {
        this.newEntity = newEntity;
        this.displacement = displacement;
    }

    @Override
    public AddOnRemove cloneComponent() {
        return new AddOnRemove(displacement, newEntity);
    }
}
