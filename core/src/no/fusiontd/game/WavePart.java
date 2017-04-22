package no.fusiontd.game;

import java.util.Locale;

class WavePart {
    final int amount;
    final CreepBluePrint creepBluePrint;
    final float delay;

    WavePart(int amount, String texture, int life, float speed, float delay, int reward) {
        this.amount = amount;
        this.delay = delay;
        creepBluePrint = new CreepBluePrint(texture, life, speed, reward);
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%d %s %s %s",
                amount,
                creepBluePrint.texture,
                creepBluePrint.life,
                creepBluePrint.speed, delay * 1000
        );
    }
}
