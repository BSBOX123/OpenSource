package com.example.os_project;

import java.util.Date;

public class RewardEngine {

    public static class RewardData {
        public int exp;
        public int gold;

        public RewardData(int exp, int gold) {
            this.exp = exp;
            this.gold = gold;
        }
    }

    public RewardData calculateReward(int difficulty) {
        int exp = difficulty * 30;
        int gold = difficulty * 50;
        return new RewardData(exp, gold);
    }

    public int calculatePenalty(Date overdueDate) {
        long overdueTime = System.currentTimeMillis() - overdueDate.getTime();
        if (overdueTime <= 0) return 0;

        int overdueHours = (int) (overdueTime / (1000 * 60 * 60));

        return 10 + (overdueHours * 5);
    }

    public boolean checkLevelUp(int currentExp, int addedExp) {
        int totalExp = currentExp + addedExp;

        return totalExp >= 100;
    }
}