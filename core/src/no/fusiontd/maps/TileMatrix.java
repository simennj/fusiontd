package no.fusiontd.maps;

class TileMatrix {
    private int[][] tiles;
    private int tileRows, tileCols;

    TileMatrix(int[][] tileTypes) {
        tileRows = tileTypes.length;
        tileCols = tileTypes[0].length;
        generateTiles(tileTypes);
    }

    private void generateTiles(int[][] tileTypes) {
        tiles = new int[tileRows][tileCols];
        for (int row = 0; row < tileRows; row++) {
            tiles[row] = new int[tileCols];
            System.arraycopy(tileTypes[row], 0, tiles[row], 0, tileCols);
        }
    }

    int[][] getTileTypes() {
        int[][] tileTypes = new int[tileRows][tileCols];
        for (int row = 0; row < tileRows; row++) {
            for (int col = 0; col < tileCols; col++) {
                tileTypes[row][col] = getType(row, col);
            }
        }
        return tileTypes;
    }

    int getType(int row, int col) {
        return tiles[row][col];
    }

    void setType(int row, int col, int tileType) {
        tiles[row][col] = tileType;
    }
}
