package no.fusiontd.game;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by Even on 18.04.2017.
 */

public class MapEditorLogic {

    private Map map;

    public boolean checkForValidPlacement(int x, int y){
        //Method for checking a valid placement of a road tile with respect to other road tiles.
        //A false return value means that the placement is not valid.

        //check if the original tile is 'empty' (grass)
        int originalTile=map.getTile(x,y);
        if (originalTile==1 || originalTile==4 || originalTile==5){
            return false;
        }

        //check if the tile has more than 2 neighbours (which would be invalid).
        int neighbourTilesCounter = 0;

        if (map.getTile(x,y-1)==1){
            neighbourTilesCounter=neighbourTilesCounter+1;
            if (checkNeighbour(x, y-1)==false){
                return false;
            }
        }
        if (map.getTile(x,y+1)==1){
            neighbourTilesCounter=neighbourTilesCounter+1;
            if (checkNeighbour(x, y+1)==false){
                return false;
            }
        }
        if (map.getTile(x-1,y)==1){
            neighbourTilesCounter=neighbourTilesCounter+1;
            if (checkNeighbour(x-1, y)==false){
                return false;
            }
        }
        if (map.getTile(x+1,y)==1){
            neighbourTilesCounter=neighbourTilesCounter+1;
            if (checkNeighbour(x+1, y)==false){
                return false;
            }
        }

        if (neighbourTilesCounter>2){
            return false;
        }

        return true;
    }

    public boolean checkNeighbour(int x, int y){

        //check if the given tile has more than 2 neighbours (which would be invalid).
        //Companion to the checkForValidPlacement method, 
        int neighbourTilesCounter = 0;

        if (map.getTile(x,y-1)==1){
            neighbourTilesCounter=neighbourTilesCounter+1;
        }
        if (map.getTile(x,y+1)==1){
            neighbourTilesCounter=neighbourTilesCounter+1;
        }
        if (map.getTile(x-1,y)==1){
            neighbourTilesCounter=neighbourTilesCounter+1;
        }
        if (map.getTile(x+1,y)==1){
            neighbourTilesCounter=neighbourTilesCounter+1;
        }

        if (neighbourTilesCounter>2){
            return false;
        }

        return true;
    }

    public boolean checkIfMapIsValid(int[][] adj){

        int endCounter = 0;
        int startCounter = 0;

        List<Point2D> path = map.findPath(adj);

        for (int i = 0; i < map.TILEROWS; i++) {
            for (int j = 0; j < map.TILECOLS; j++) {

                if (adj[i][j]==4){
                    startCounter=startCounter+1;
                }
                if (adj[i][j]==5){
                    endCounter=endCounter+1;
                }

                if (adj[i][j]==1 && checkIfPointIsInPath(path, i, j)==false){
                    return false;
                }

            }
        }
        if (startCounter!=1 || endCounter!=1){
            return false;
        }

        return true;
    }

    public boolean checkIfPointIsInPath(List<Point2D> path, int i, int j){

        for (int k=0; k<path.size(); k++){
            Point2D point = path.get(k);
            if (point.getX() == i && point.getY() == j){
                return true;
            }
        }

        return false;
    }
}
