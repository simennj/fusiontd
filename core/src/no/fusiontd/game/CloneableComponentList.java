package no.fusiontd.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import no.fusiontd.CloneableComponent;

import java.util.HashMap;

public class CloneableComponentList extends HashMap<Class<? extends CloneableComponent>, CloneableComponent> {

    public CloneableComponentList(CloneableComponent... components) {
        for (CloneableComponent component : components) {
            put(component.getClass(), component);
        }
    }

    public CloneableComponentList cloneList() {
        CloneableComponentList clonedList = new CloneableComponentList();
        for (CloneableComponent component : this.values()) {
            clonedList.put(component.getClass(), component.cloneComponent());
        }
        return clonedList;
    }

    public Entity toEntity() {
        Entity entity = new Entity();
        for (Component component : cloneList().values()) {
            entity.add(component);
        }
        return entity;
    }
}
