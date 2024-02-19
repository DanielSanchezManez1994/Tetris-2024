/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.tetris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

/**
 *
 * @author danielsanchez
 */
public class Board extends javax.swing.JPanel {

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (canMove(currentShape, currentRow, currentCol - 1) && timer.isRunning()) {
                        currentCol--;
                    }
// whatever
                    break;
                case KeyEvent.VK_RIGHT:
                    if (canMove(currentShape, currentRow, currentCol + 1) && timer.isRunning()) {
                        currentCol++;

                    }
// whatever
                    break;
                case KeyEvent.VK_UP:
                    Shape newShape = currentShape.getCopy();
                    newShape.rotateRight();
                    if (!shapeHitsMatrix(newShape, currentRow, currentCol) && canMove(newShape, currentRow, currentCol) && timer.isRunning()) {
                        currentShape = newShape;
                    }
// whatever
                    break;
                case KeyEvent.VK_DOWN:
                    if (canMove(currentShape, currentRow, currentCol) && timer.isRunning()) {
                        currentRow = getFinalRow();
                    }
// whatever
                    break;
                case KeyEvent.VK_R:
                    if (timer.isRunning()) {
                        restartGame();

                    }
                    break;
                case KeyEvent.VK_P:
                    pauseGame();
                default:
                    break;
            }
            repaint();
        }
    }

    public static final int NUM_COLS = 10;
    public static final int NUM_ROWS = 22;
    public static final int MIN_DELTA_TIME = 200;

    private Tetrominoes[][] matrix;
    private Shape currentShape;
    private int currentRow;
    private int currentCol;
    private Timer timer;
    private MyKeyAdapter keyAdapter;
    private ScoreInterface score;

    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        initMatrix();
        keyAdapter = new MyKeyAdapter();
       
        
    }

    public void initGame(){
        initMatrix();
        setFocusable(true);
        requestFocusInWindow();
        createNewCurrentShape();
        addKeyListener(keyAdapter);
        int deltaTime = ConfigData.getInstance().getDeltaTime();
        if(timer != null && timer.isRunning()){
            timer.stop();
        }
        timer = new Timer(deltaTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tick();
                repaint();
            }
        });
        timer.start();
    }
    public void setScore(ScoreInterface score) {
        this.score = score;
    }

    
    public void initMatrix() {
        matrix = new Tetrominoes[NUM_ROWS][NUM_COLS];
        for (int i = 0; i < matrix.length; i++) {
            for (int ii = 0; ii < matrix[0].length; ii++) {
                matrix[i][ii] = Tetrominoes.NoShape;
            }
        }
    }

    public boolean canMove(Shape shape, int row, int col) {
        if (col + shape.getMinX() < 0 || col + shape.getMaxX() >= NUM_COLS) {
            return false;
        }
        if (row + shape.getMaxY() >= NUM_ROWS) {
            return false;
        }
        if (shapeHitsMatrix(shape, row, col)) {
            return false;
        }
        return true;
    }

    private void tick() {
        if (canMove(currentShape, currentRow + 1, currentCol)) {
            currentRow++;
            System.out.println(currentRow);
            System.out.println(currentCol);
        } else {
            copyCurrentShapeToMatrix();
            checkCompletedRows();
            createNewCurrentShape();
            checkGameOver();
        }

    }
    
    public void restartGame() {
        initMatrix();
        createNewCurrentShape();
        timer.start();
        ConfigData.getInstance().setDeltaTime(500);
    }

    public void pauseGame() {
        if (timer.isRunning()) {
            timer.stop();
        } else {
            timer.start();
        }
    }

    public int getFinalRow() {
        for (int row = 0; row < NUM_ROWS; row++) {
            if (shapeHitsMatrix(currentShape, row, currentCol)) {
                return row - 1;
            }
        }
        return NUM_ROWS - 1 - currentShape.getMaxY();
    }

    private boolean shapeHitsMatrix(Shape shape, int row, int col) {
        for (int i = 0; i < 4; i++) {

            int rr = row + shape.getY(i);
            int cc = col + shape.getX(i);
            if (cc < 0 || cc >= NUM_COLS) {
                return true;
            }
            if (rr >= 0 && rr < matrix.length) {
                if (matrix[rr][cc] != Tetrominoes.NoShape) {
                    return true;
                }
            }
        }
        return false;
    }

    public void checkCompletedRows() {
        for (int row = 0; row < NUM_ROWS; row++) {
            if (isRowCompleted(row)) {
                deleteRow(row);
                pushAllRows(row);
                score.incrementScore();
                int completedRows = ConfigData.getInstance().getCompletedRows() + 1;
                ConfigData.getInstance().setCompletedRows(completedRows);
                if(completedRows >= 10 && ConfigData.getInstance().getDeltaTime() > MIN_DELTA_TIME){
                   ConfigData.getInstance().increaseLevelAndReduceDeltaTime();
                }
            }
        }
    }

    public void pushAllRows(int row) {

        for (int rr = row; rr > 0; rr--) {
            for (int col = 0; col < NUM_COLS; col++) {
                matrix[rr][col] = matrix[rr - 1][col];
            }
        }
        deleteRow(0);
    }

    public void checkGameOver() {
        for (int i = 0; i < 4; i++) {
            int rr = currentRow + currentShape.getY(i);
            int cc = currentCol + currentShape.getX(i);

            if (matrix[rr][cc] != Tetrominoes.NoShape){
                processGameOver();
                break;
            }
        }
    }
    private void processGameOver(){
        currentShape = null;
        timer.stop();
        removeKeyListener(keyAdapter);
        fillMatrixWithGameOVer();
        
    }

    public void deleteRow(int row) {

        for (int col = 0; col < NUM_COLS; col++) {
            matrix[row][col] = Tetrominoes.NoShape;
        }

    }

    public boolean isRowCompleted(int row) {
        int count = 0;
        for (int col = 0; col < NUM_COLS; col++) {
            if (matrix[row][col] != Tetrominoes.NoShape) {
                count++;
            }
        }
        if (count == 10) {
            return true;
        } else {
            return false;
        }
    }

    public void copyCurrentShapeToMatrix() {
        for (int i = 0; i < 4; i++) {
            int row = currentRow + currentShape.getY(i);
            int col = currentCol + currentShape.getX(i);
            matrix[row][col] = currentShape.getShape();
        }
    }

    private void createNewCurrentShape() {
        currentShape = new Shape();
        currentRow = 0;
        currentCol = NUM_COLS / 2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBorder(g);
        paintMatrix(g);
        if(currentShape != null){
          paintCurrentShape(g);
        }
 
    }

    public void paintMatrix(Graphics g) {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                drawSquare(g, row, col, matrix[row][col]);
            }

        }
    }

    public void paintBorder(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        BasicStroke bs1 = new BasicStroke(1);
        g2d.setStroke(bs1);
        g2d.drawRect(0, 0, NUM_COLS * getSquareWidth() - 2, NUM_ROWS * getSquareHeigth());
    }

    private void paintCurrentShape(Graphics g) {
        for (int i = 0; i < 4; i++) {
            drawSquare(g, currentRow + currentShape.getY(i), currentCol + currentShape.getX(i), currentShape.getShape());
        }
    }

    private void drawSquare(Graphics g, int row, int col, Tetrominoes shape) {
        Color colors[] = {new Color(0, 0, 0),
            new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
            new Color(204, 204, 102), new Color(204, 102, 204),
            new Color(102, 204, 204), new Color(218, 170, 0)
        };
        int x = col * getSquareWidth();
        int y = row * getSquareHeigth();
        Color color = colors[shape.ordinal()];
        g.setColor(color);
        g.fillRect(x + 1, y + 1, getSquareWidth() - 2, getSquareHeigth() - 2);
        if (shape == Tetrominoes.NoShape) {
            g.setColor(color.gray);
        } else {
            g.setColor(color.brighter());
        }

        g.drawLine(x, y + getSquareHeigth() - 1, x, y);
        g.drawLine(x, y, x + getSquareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + getSquareHeigth() - 1, x + getSquareWidth() - 1, y + getSquareHeigth() - 1);
        g.drawLine(x + getSquareWidth() - 1, y + getSquareHeigth() - 1, x + getSquareWidth() - 1, y + 1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 151, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 411, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private int getSquareWidth() {
        return getWidth() / NUM_COLS;
    }

    private int getSquareHeigth() {
        return getHeight() / NUM_ROWS;
    }

    private static int rowGO;
    private static int colGO;
    private static Timer timerGO;
    
    private void fillMatrixWithGameOVer(){
         rowGO = 0;
         colGO = 0;
        timerGO = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                matrix[rowGO][colGO] = Tetrominoes.LineShape;
                colGO++;
                if(colGO >= NUM_COLS){
                    colGO = 0;
                    rowGO++;
                }
                if(rowGO >= NUM_ROWS){
                    timerGO.stop();
                    addKeyListener(keyAdapter);
                }
                repaint();
            }
        });
       timerGO.start();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
