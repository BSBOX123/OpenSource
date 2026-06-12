package com.example.os_project;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import java.util.Date;
import java.util.List;

public class BackgroundScheduler extends BroadcastReceiver {
    private DataController dataController;
    private RewardEngine rewardEngine;
    private static final String CHANNEL_ID = "semester_survive_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        dataController = new DataController(context);
        rewardEngine = new RewardEngine();

        String action = intent.getAction();
        if ("ACTION_MIDNIGHT_CHECK".equals(action)) {
            generateDailyQuest(context);
        } else {
            checkOverdueQuests(context);
        }
    }

    public void checkOverdueQuests(Context context) {
        List<Quest> pendingQuests = dataController.fetchPendingQuests();
        Player player = dataController.getPlayer();
        if (player == null) return;

        long now = System.currentTimeMillis();

        for (Quest quest : pendingQuests) {
            long dueDateMillis = quest.getDueDate().getTime();

            if (now > dueDateMillis) {
                int damage = rewardEngine.calculatePenalty(quest.getDueDate());
                int newHp = player.getHp() - damage;

                dataController.updateQuestStatus(quest.getId(), "FAILED");

                if (newHp <= 0) {
                    newHp = 100;
                    int penalizedGold = (int) (player.getGold() * 0.7);
                    dataController.updatePlayerStat(newHp, player.getExp(), penalizedGold);
                    sendNotification(context, "게임 오버", "학업 스트레스로 아바타가 쓰러졌습니다. 골드가 일부 몰수됩니다.");
                } else {
                    dataController.updatePlayerStat(newHp, player.getExp(), player.getGold());
                    sendNotification(context, "퀘스트 마감 실패 페널티", "과제 '" + quest.getTitle() + "' 마감 초과로 HP가 " + damage + " 차감되었습니다.");
                }
            }
            else if (dueDateMillis - now < 24 * 60 * 60 * 1000) {
                sendNotification(context, "퀘스트 마감 임박 경고!", "'" + quest.getTitle() + "' 마감까지 24시간 미만 남았습니다! 서두르세요.");
            }
        }
    }

    public void generateDailyQuest(Context context) {
        Quest dailyQuest = new Quest(0, "오늘의 생존 루틴: 전공 서적 30페이지 읽기",
                new Date(System.currentTimeMillis() + 16 * 60 * 60 * 1000),
                1, "PENDING");

        dataController.insertQuest(dailyQuest);
        sendNotification(context, "일일 퀘스트 도착!", "새로운 교수님의 습격! 일일 퀘스트가 리스트에 추가되었습니다.");
    }

    @SuppressLint("MissingPermission")
    public void sendNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "학기생존기 알림 채널", NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        if (notificationManager != null) {
            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }
}