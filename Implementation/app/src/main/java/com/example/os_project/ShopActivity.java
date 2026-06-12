package com.example.os_project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends androidx.activity.ComponentActivity {
    private EventController eventController;
    private TextView tvShopPlayerGold;
    private ListView lvShopItems;
    private Button btnBackToMain;

    private String[] itemNames = {
            "달콤한 야식 치킨 한 마리 쿠폰",
            "시원한 아이스 아메리카노 테이크아웃",
            "합법적 넷플릭스/게임 2시간 자유 이용권",
            "다음날 아침 짱짱한 늦잠 허용권"
    };
    private int[] itemPrices = {300, 100, 150, 200};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        eventController = new EventController(this);

        tvShopPlayerGold = findViewById(R.id.tvShopPlayerGold);
        lvShopItems = findViewById(R.id.lvShopItems);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        List<String> shopListStrings = new ArrayList<>();
        for (int i = 0; i < itemNames.length; i++) {
            shopListStrings.add(itemNames[i] + " \n 가격: " + itemPrices[i] + " G");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shopListStrings);
        lvShopItems.setAdapter(adapter);

        displayGold();

        lvShopItems.setOnItemClickListener((parent, view, position, id) -> {
            int price = itemPrices[position];

            boolean isSuccess = eventController.onItemPurchased(position, price);

            if (isSuccess) {
                Toast.makeText(this, "구매 성공! 현실 보상을 획득하세요.", Toast.LENGTH_SHORT).show();
                displayGold(); // 차감된 골드 UI 동기화
            } else {
                Toast.makeText(this, "골드가 부족합니다! 과제를 더 해결해 오세요.", Toast.LENGTH_SHORT).show();
            }
        });


        btnBackToMain.setOnClickListener(v -> finish());
    }

    private void displayGold() {
        Player player = eventController.getPlayerStatus();
        if (player != null) {
            tvShopPlayerGold.setText("내 보유 Gold: " + player.getGold() + " G");
        }
    }
}