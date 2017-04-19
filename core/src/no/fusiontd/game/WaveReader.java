package no.fusiontd.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

class WaveReader {
    private LinkedList<CreepWave> waves;

    WaveReader(String wave) {
        waves = new LinkedList<CreepWave>();
        waves.add(new CreepWave());
        try {
            Scanner scanner = new Scanner(new FileInputStream("waves/" + wave));
            scanner.useLocale(Locale.ENGLISH);
            parseWavesFromFile(scanner);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void parseWavesFromFile(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            if (line.length == 5) {
                waves.getLast().addPart(parsePart(line));
            } else {
                waves.add(new CreepWave());
            }
        }
    }

    private WavePart parsePart(String[] line) {
        return new WavePart(
                Integer.parseInt(line[0]),
                line[1],
                Float.parseFloat(line[2]),
                Float.parseFloat(line[3]),
                Float.parseFloat(line[4]) / 1000f
        );
    }

    CreepWave popWave() {
        return waves.pop();
    }
}
