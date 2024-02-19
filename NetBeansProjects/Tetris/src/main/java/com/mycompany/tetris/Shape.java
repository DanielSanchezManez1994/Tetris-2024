/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetris;

/**
 *
 * @author danielsanchez
 */
public class Shape {

    private Tetrominoes pieceShape;
    private int coords[][];
    private static int[][][] coordsTable = new int[][][]{
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
        {{-1, 0}, {0, 0}, {0, 1}, {1, 1}},
        {{1, 0}, {0, 0}, {0, 1}, {-1, 1}},
        {{-2, 0}, {-1, 0}, {0, 0}, {1, 0}},
        {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
        {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
        {{-1, 1}, {-1, 0}, {0, 0}, {1, 0}},
        {{1, 1}, {1, 0}, {0, 0}, {-1, 0}
        }
    };

    public Shape() {

        int randomShape = (int) (Math.random() * 7) + 1;

        pieceShape = Tetrominoes.values()[randomShape];

        //coords = coordsTable[randomShape];
        coords = new int[4][2];

        for (int i = 0; i < coords.length; i++) {

            for (int ii = 0; ii < coords[0].length; ii++) {

                coords[i][ii] = coordsTable[randomShape][i][ii];
            }
        }
    }

    public int getX(int index) {
        return coords[index][0];
    }

    public int getY(int index) {
        return coords[index][1];
    }

    public Tetrominoes getShape() {
        return pieceShape;
    }

    public Shape getCopy() {
        Shape newShape = new Shape();
        newShape.pieceShape = pieceShape;
        for (int i = 0; i < 4; i++) {
            newShape.coords[i][0] = coords[i][0];
            newShape.coords[i][1] = coords[i][1];

        }
        return newShape;
    }

    public void rotateRight() {
        if(pieceShape == Tetrominoes.SquareShape){
            return;
        }else{
        for (int i = 0; i < 4; i++) {
            int temp = coords[i][0];
            coords[i][0] = -coords[i][1];
            coords[i][1] = temp;
        }
  
        }
    }
    public int getMinX() {
        int min = coords[0][0];
        for (int i = 1; i < coords.length; i++) {
            if (coords[i][0] < min) {
                min = coords[i][0];

            }
        }
        return min;
    }

    public int getMaxX() {
        int max = coords[0][0];
        for (int i = 1; i < coords.length; i++) {
            if (coords[i][0] > max) {
                max = coords[i][0];
            }
        }
        return max;
    }

    public int getMinY() {
        int min = coords[0][1];
        for (int i = 0; i < coords.length; i++) {
            if (coords[i][1] < min) {
                min = coords[i][1];
            }
        }
        return min;
    }

    public int getMaxY() {
        int max = coords[0][1];
        for (int i = 0; i < coords.length; i++) {
            if (coords[i][1] > max) {
                max = coords[i][1];
            }
        }
        return max;
    }
}
