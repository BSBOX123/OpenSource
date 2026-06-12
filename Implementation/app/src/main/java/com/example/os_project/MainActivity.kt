package com.example.os_project

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList
import java.util.Date
import java.util.PriorityQueue

class MainActivity : androidx.activity.ComponentActivity() {
    private lateinit var eventController: EventController

    private lateinit var tvPlayerLevel: TextView
    private lateinit var tvPlayerGold: TextView
    private lateinit var barHp: ProgressBar
    private lateinit var barExp: ProgressBar
    private lateinit var lvQuests: ListView
    private lateinit var btnRegisterQuest: Button
    private lateinit var btnGoToShop: Button

    private val renderedQuestList = ArrayList<Quest>()
    private lateinit var listAdapter: ArrayAdapter<String>
    private val listDisplayStrings = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eventController = EventController(this)

        tvPlayerLevel = findViewById(R.id.tvPlayerLevel)
        tvPlayerGold = findViewById(R.id.tvPlayerGold)
        barHp = findViewById(R.id.barHp)
        barExp = findViewById(R.id.barExp)
        lvQuests = findViewById(R.id.lvQuests)
        btnRegisterQuest = findViewById(R.id.btnRegisterQuest)
        btnGoToShop = findViewById(R.id.btnGoToShop)

        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listDisplayStrings)
        lvQuests.adapter = listAdapter

        btnRegisterQuest.setOnClickListener {
            val context = this
            val builder = android.app.AlertDialog.Builder(context)
            builder.setTitle("새 퀘스트(과제) 등록")

            val dialogLayout = android.widget.LinearLayout(context).apply {
                orientation = android.widget.LinearLayout.VERTICAL
                setPadding(60, 40, 60, 40)
            }

            val etTitle = android.widget.EditText(context).apply {
                hint = "과제명을 입력하세요 (예: 팀 프로젝트 소스코드 제출)"
            }
            dialogLayout.addView(etTitle)

            val etHours = android.widget.EditText(context).apply {
                hint = "마감까지 남은 시간 (숫자만 입력, 예: 12)"
                inputType = android.text.InputType.TYPE_CLASS_NUMBER
            }
            dialogLayout.addView(etHours)

            val tvDiff = android.widget.TextView(context).apply {
                text = "\n과제 난이도 선택"
                textSize = 14f
                setTextColor(android.graphics.Color.GRAY)
            }
            dialogLayout.addView(tvDiff)

            val rgDiff = android.widget.RadioGroup(context).apply {
                orientation = android.widget.RadioGroup.HORIZONTAL
                layoutParams = android.widget.LinearLayout.LayoutParams(
                    android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { topMargin = 10 }
            }

            val rb1 = android.widget.RadioButton(context).apply { text = "하 (1)"; id = 1; isChecked = true }
            val rb2 = android.widget.RadioButton(context).apply { text = "중 (2)"; id = 2 }
            val rb3 = android.widget.RadioButton(context).apply { text = "상 (3)"; id = 3 }
            rgDiff.addView(rb1)
            rgDiff.addView(rb2)
            rgDiff.addView(rb3)
            dialogLayout.addView(rgDiff)

            builder.setView(dialogLayout)

            builder.setPositiveButton("등록") { dialog, _ ->
                val title = etTitle.text.toString().trim()
                val hoursStr = etHours.text.toString().trim()

                if (title.isEmpty() || hoursStr.isEmpty()) {
                    Toast.makeText(context, "빈칸 없이 모두 입력해야 퀘스트가 생성됩니다", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val hours = hoursStr.toLong()
                val difficulty = when (rgDiff.checkedRadioButtonId) {
                    1 -> 1
                    2 -> 2
                    3 -> 3
                    else -> 1
                }

                val calculatedDueDate = Date(System.currentTimeMillis() + (hours * 60 * 60 * 1000L))

                eventController.onQuestAdded(title, calculatedDueDate, difficulty)

                Toast.makeText(context, "⚔️ 새 퀘스트가 리스트에 등록되었습니다!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            builder.setNegativeButton("취소") { dialog, _ -> dialog.cancel() }
            builder.show()
        }

        lvQuests.setOnItemClickListener { _, _, position, _ ->
            if (position < renderedQuestList.size) {
                val targetQuest = renderedQuestList[position]
                eventController.onQuestCompleted(targetQuest.id)

                Toast.makeText(this, "과제 달성 성공! 보상이 지급되었습니다.", Toast.LENGTH_SHORT).show()
                refreshUI()
            }
        }

        btnGoToShop.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshUI()
    }

    private fun refreshUI() {
        val player = eventController.playerStatus
        if (player != null) {
            tvPlayerLevel.text = "PLAYER LEVEL : LV.${player.level}"
            tvPlayerGold.text = "보유 Gold : ${player.gold} G"
            barHp.progress = player.hp
            barExp.progress = player.exp
        }

        val sortedQueue: PriorityQueue<Quest> = eventController.requestSortedQuests()

        renderedQuestList.clear()
        listDisplayStrings.clear()

        while (!sortedQueue.isEmpty()) {
            val q = sortedQueue.poll()
            if (q != null) {
                renderedQuestList.add(q)
                listDisplayStrings.add(q.details + "  [터치 시 완료]")
            }
        }

        listAdapter.notifyDataSetChanged()
    }
}