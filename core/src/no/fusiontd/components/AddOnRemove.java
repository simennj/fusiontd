package no.fusiontd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class AddOnRemove implements Component {
    public Entity newEntity;

    public AddOnRemove(Entity newEntity) {
        this.newEntity = newEntity;
    }

}
