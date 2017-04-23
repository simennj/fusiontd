package no.fusiontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

class WaveReader {
    LinkedList<CreepWave> waves;

    WaveReader(String wave) {
        waves = new LinkedList<CreepWave>();
        waves.add(new CreepWave());
            FileHandle file = Gdx.files.internal("waves/" + wave);
            String text = file.readString();
            Scanner scanner = new Scanner(text);
            scanner.useLocale(Locale.ENGLISH);
            parseWavesFromFile(scanner);
    }

    private void parseWavesFromFile(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            if (line.length == 6) {
                waves.getLast().addPart(parsePart(line));
            } else if(line.length == 1){
                waves.add(new CreepWave());

            } else {
                System.out.println("Wave is not correctly implemented");
            }
        }
    }

    private WavePart parsePart(String[] line) {
        return new WavePart(
                Integer.parseInt(line[0]),
                line[1],
                Integer.parseInt(line[2]),
                Float.parseFloat(line[3]),
                Float.parseFloat(line[4]) / 1000f,
                Integer.parseInt(line[5])
        );
    }

    public boolean hasNextWave() {
        return (waves.size() > 0);
    }

    CreepWave popWave() {
        return waves.pop();
    }
}
