package no.fusiontd.components;

import no.fusiontd.CloneableComponent;

public class Timer implements CloneableComponent<Timer> {
    public float time;

    public Timer(float time) {
        this.time = time;
    }

    @Override
    public Timer cloneComponent() {
        return new Timer(time);
    }
}
