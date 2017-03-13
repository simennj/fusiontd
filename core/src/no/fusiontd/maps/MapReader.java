package no.fusiontd.maps;

import com.badlogic.gdx.Gdx;

public class MapReader {

    public int[][] loadMap(String mapName, int rows, int cols) {

        int[][] map = new int[rows][cols];

        String level = Gdx.files.internal("maps/" + mapName).readString().replace("\r\n", "\n").replace("\r", "\n").replace("\n", "");
        char[] oneline = level.toCharArray();
        System.out.println(oneline);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                switch (oneline[x + y*cols]) {
                    case '0': map[y][x] = 0;
                        break;
                    case '1': map[y][x] = 1;
                        break;
                    case '2': map[y][x] = 2;
                        break;
                    case '3': map[y][x] = 3;
                        break;
                    case '4': map[y][x] = 4;
                        break;
                    case '5': map[y][x] = 5;
                        break;
                    default: map[y][x] = 0;
                }
            }
        }
        return map;
    }
}