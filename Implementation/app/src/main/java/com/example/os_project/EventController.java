package com.example.os_project;

import android.content.Context;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

public class EventController {
    private DataController dataController;
    private QueueManager queueManager;
    private RewardEngine rewardEngine;
    private Context context;

    public EventController(Context context) {
        this.context = context;
        this.dataController = new DataController(context);
        this.queueManager = new QueueManager();
        this.rewardEngine = new RewardEngine();
    }

    public void onQuestAdded(String title, Date dueDate, int difficulty) {
        Quest newQuest = new Quest(0, title, dueDate, difficulty, "PENDING");
        dataController.insertQuest(newQuest);
    }

    public void onQuestCompleted(int questId) {
        List<Quest> pendingQuests = dataController.fetchPendingQuests();
        int difficulty = 1;
        for (Quest q : pendingQuests) {
            if (q.getId() == questId) {
                difficulty = q.getDifficulty();
                break;
            }
        }

        // 2) 보상 연산 및 플레이어 스탯 조회
        RewardEngine.RewardData reward = rewardEngine.calculateReward(difficulty);
        Player player = dataController.getPlayer();

        if (player != null) {
            int newExp = player.getExp() + reward.exp;
            int newLevel = player.getLevel();
            int newHp = player.getHp();

            if (rewardEngine.checkLevelUp(player.getExp(), reward.exp)) {
                newLevel += 1;
                newExp = (player.getExp() + reward.exp) - 100;
                newHp = 100;
            }

            int newGold = player.getGold() + reward.gold;

            dataController.updateQuestStatus(questId, "DONE");
            dataController.updatePlayerStat(newHp, newExp, newGold);

        }
    }

    public boolean onItemPurchased(int itemId, int price) {
        Player player = dataController.getPlayer();
        if (player != null && player.getGold() >= price) {
            int newGold = player.getGold() - price;
            dataController.updatePlayerStat(player.getHp(), player.getExp(), newGold);
            return true;
        }
        return false;
    }

    public PriorityQueue<Quest> requestSortedQuests() {
        List<String> userSummaryCheck;
        List<Quest> pendingList = dataController.fetchPendingQuests();
        return queueManager.sortQuests(pendingList);
    }

    public Player getPlayerStatus() {
        return dataController.getPlayer();
    }
}