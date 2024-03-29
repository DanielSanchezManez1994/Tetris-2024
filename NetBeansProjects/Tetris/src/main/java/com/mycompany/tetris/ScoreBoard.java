/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.tetris;

import java.nio.file.SecureDirectoryStream;

/**
 *
 * @author danielsanchez
 */
public class ScoreBoard extends javax.swing.JPanel implements ScoreInterface{

    private int score;
    
    /**
     * Creates new form ScoreBoard
     */
    public ScoreBoard() {
        initComponents();
        score = 0;
        
    }

    public void incrementScore(){
        int currentScore = ConfigData.getInstance().getScore() + 1;
        ConfigData.getInstance().setScore(currentScore);
        updateScoreBoard(currentScore);
    }
    
    public void resetScore(){
        score = 0;
        updateScoreBoard(score);
    }
    
    private void updateScoreBoard(int score){
        jScore.setText("Score: " + ConfigData.getInstance().getScore());
        jLevel.setText("Level: " + ConfigData.getInstance().getLevel());
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScore = new javax.swing.JLabel();
        jLevel = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jScore.setText("0");
        add(jScore, java.awt.BorderLayout.CENTER);

        jLevel.setText("1");
        add(jLevel, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLevel;
    private javax.swing.JLabel jScore;
    // End of variables declaration//GEN-END:variables
}
