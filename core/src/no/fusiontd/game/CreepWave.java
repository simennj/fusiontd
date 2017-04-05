package no.fusiontd.game;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CreepWave {
    final List<WavePart> waveParts;
    private int wavePartsCount;
    private int waveProgression, partProgression;

    public CreepWave(String wave) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("waves/" + wave));
        scanner.useLocale(Locale.ENGLISH);
        waveParts = new LinkedList<WavePart>();
        while (scanner.hasNextLine() && scanner.hasNext()) {
            wavePartsCount++;
            waveParts.add(new WavePart(scanner.nextInt(), scanner.next(), scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat() / 1000f));
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        CreepWave creepWave = new CreepWave("1");
        for (WavePart part : creepWave.waveParts) {
            System.out.println(part);
        }
    }

    public float currentDelayBetweenCreeps() {
        if (waveProgression >= wavePartsCount) {
            waveProgression = 0;
        }
        return waveParts.get(waveProgression).delay;
    }

    public CreepBluePrint popCreep() {
        WavePart wavePart = waveParts.get(waveProgression);
        if (++partProgression >= wavePart.amount) {
            partProgression = 0;
            waveProgression++;
        }
        return wavePart.creepBluePrint;
    }

    private class WavePart {
        private final int amount;
        private final CreepBluePrint creepBluePrint;
        private final float delay;

        WavePart(int amount, String texture, float life, float speed, float delay) {
            this.amount = amount;
            this.delay = delay;
            creepBluePrint = new CreepBluePrint(texture, life, speed);
        }

        @Override
        public String toString() {
            return String.format(Locale.ENGLISH, "%d %s %s %s", amount, creepBluePrint.texture, creepBluePrint.life, creepBluePrint.speed, delay * 1000);
        }
    }

    public class CreepBluePrint {
        public final String texture;
        public final float life;
        public final float speed;

        CreepBluePrint(String texture, float life, float speed) {
            this.texture = texture;
            this.life = life;
            this.speed = speed;
        }
    }
}
