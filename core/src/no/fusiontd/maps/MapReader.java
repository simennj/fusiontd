package no.fusiontd.maps;

import com.badlogic.gdx.Gdx;

public class MapReader {

    public int[][] loadMap(String mapName, int rows, int cols) {

        int[][] map = new int[rows][cols];

        String[] level = Gdx.files.internal("maps/" + mapName).readString().split("\\R");

        for (int y = 0; y < rows; y++) {
            char[] row = level[y].toCharArray();
            for (int x = 0; x < cols; x++) {
                switch (row[x]) {
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