package no.fusiontd.game;

import com.badlogic.gdx.Gdx;

import java.util.LinkedList;
import java.util.List;

class CreepWave {
    private final List<WavePart> waveParts = new LinkedList<WavePart>();
    private int waveProgression, partProgression;

    float currentDelayBetweenCreeps() {
        return waveParts.get(waveProgression).delay;
    }

    void addPart(WavePart wavePart) {
        waveParts.add(wavePart);
    }

    boolean finished() {
        return waveProgression >= waveParts.size() - 1 && partProgression >= waveParts.get(waveProgression).amount - 1;
    }

    CreepBluePrint popCreep() {
        WavePart wavePart = waveParts.get(waveProgression);
        if (++partProgression >= wavePart.amount) {
            partProgression = 0;
            waveProgression++;
        }
        return wavePart.creepBluePrint;
    }

}
