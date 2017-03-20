package no.fusiontd.components;

import com.badlogic.ashley.core.Component;

public class StatusEffect implements Component {
    int status;

    StatusEffect(int newStatus) {
        this.status = newStatus;
    }
}
