package no.fusiontd.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import no.fusiontd.CloneableComponent;

import java.util.Arrays;
import java.util.LinkedList;

public class CloneableComponentList extends LinkedList<CloneableComponent> {

    public CloneableComponentList(CloneableComponent... components) {
        super(Arrays.asList(components));
    }

    public CloneableComponentList cloneList() {
        CloneableComponentList clonedList = new CloneableComponentList();
        for (CloneableComponent component : this) {
            clonedList.add(component.cloneComponent());
        }
        return clonedList;
    }

    public Entity toEntity() {
        Entity entity = new Entity();
        for (Component component : cloneList()) {
            entity.add(component);
        }
        return entity;
    }
}
