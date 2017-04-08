package no.fusiontd.components;

import no.fusiontd.CloneableComponent;

import java.util.Arrays;
import java.util.List;

public class AddOnRemove implements CloneableComponent<AddOnRemove> {
    public List<CloneableComponent> newEntity;

    public AddOnRemove(CloneableComponent... cloneableComponents) {
        this(Arrays.asList(cloneableComponents));
    }

    public AddOnRemove(List<CloneableComponent> newEntity) {
        this.newEntity = newEntity;
    }

    @Override
    public AddOnRemove cloneComponent() {
        return new AddOnRemove(newEntity);
    }
}
