package com.example.os_project;

public class Player {
    private int level;
    private int hp;
    private int exp;
    private int gold;

    public Player(int level, int hp, int exp, int gold) {
        this.level = level;
        this.hp = hp;
        this.exp = exp;
        this.gold = gold;
    }

    public void updateStats() {
    }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public int getExp() { return exp; }
    public void setExp(int exp) { this.exp = exp; }

    public int getGold() { return gold; }
    public void setGold(int gold) { this.gold = gold; }
}