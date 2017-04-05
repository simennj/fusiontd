package no.fusiontd.components;

import com.badlogic.ashley.core.Entity;
import no.fusiontd.CloneableComponent;

public class AddOnRemove implements CloneableComponent<AddOnRemove> {
    public Entity newEntity;

    public AddOnRemove(Entity newEntity) {
        this.newEntity = newEntity;
    }

    @Override
    public AddOnRemove cloneComponent() {
        return new AddOnRemove(newEntity);
    }
}
