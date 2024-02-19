/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Singleton.java to edit this template
 */
package com.mycompany.tetris;

/**
 *
 * @author danielsanchez
 */
public class ConfigData {
    
    private static ConfigData configData = new ConfigData();
    private String playerName;
    private int level;
    private int score;
    private int deltaTime;
    private int completedRows;
    private ConfigData() {
        playerName = "";
        level = 1;
        score = 0;
        deltaTime = 500;
        completedRows = 0;
    }

    public int getCompletedRows() {
        return completedRows;
    }

    public void setCompletedRows(int completedRows) {
        this.completedRows = completedRows;
    }

    public int getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(int deltaTime) {
        this.deltaTime = deltaTime;
    }
    
    public static ConfigData getInstance() {
        
        return configData;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        deltaTime = 500 - this.level*10;
    }
    
    public void increaseLevelAndReduceDeltaTime(){
        level = level +1;
        deltaTime = deltaTime - 10;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
   
}
