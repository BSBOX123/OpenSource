package com.example.os_project;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

public class QueueManager {

    public PriorityQueue<Quest> sortQuests(List<Quest> questList) {
        PriorityQueue<Quest> priorityQueue = new PriorityQueue<>(new Comparator<Quest>() {
            @Override
            public int compare(Quest q1, Quest q2) {
                float score1 = calculatePriorityScore(q1.getDueDate(), q1.getDifficulty());
                float score2 = calculatePriorityScore(q2.getDueDate(), q2.getDifficulty());
                return Float.compare(score2, score1);
            }
        });

        priorityQueue.addAll(questList);
        return priorityQueue;
    }

    private float calculatePriorityScore(java.util.Date dueDate, int difficulty) {
        long timeRemaining = dueDate.getTime() - System.currentTimeMillis();

        float hoursRemaining = (float) timeRemaining / (1000 * 60 * 60);

        if (hoursRemaining <= 0) {
            hoursRemaining = 0.1f;
        }

        return (float) difficulty / hoursRemaining;
    }
}