<div align="center">
  <br/><br/><br/><br/>
  <h1>Survive The Semester (학기 생존기)</h1>
  <br/>
  <h3>- 1. Conceptualization -</h3>
  <br/><br/><br/><br/><br/><br/>
</div>

- 1 -
Yeungnam University

--- 

**[ Revision history ]**

<table>
  <tr>
    <th width="150" align="center">Revision date</th>
    <th width="100" align="center">Version #</th>
    <th width="400" align="center">Description</th>
    <th width="150" align="center">Author</th>
  </tr>
  <tr>
    <td align="center">2026.03.25</td>
    <td align="center">1.0.0</td>
    <td align="center">초안 작성</td>
    <td align="center">KimSeungYoon</td>
  </tr>
</table>

<br/><br/><br/><br/><br/><br/><br/><br/><br/>

- II -
Yeungnam University

--- 

**[Contents]**

<pre>
1. Business purpose ........................................................ 4
2. System context diagram .................................................. 5
3. Use case list ........................................................... 6
4. Concept of operation .................................................... 8
5. Problem statement ...................................................... 12
6. Glossary ............................................................... 13
7. References ............................................................. 13
</pre>

<br/><br/><br/><br/><br/><br/><br/>

- III -
Yeungnam University

--- 

**1. Business purpose**

**1) Project background**
현대 대학생 및 수험생들은 매일 다수의 전공 과제와 개인적인 학습 목표를 부여받는다. 그러나 이러한 목표들은 즉각적인 보상 체계가 부재하여 지루함을 유발하며, 이는 필연적으로 할 일을 미루게 되는 현상으로 이어진다. 특히 우선순위를 명확히 산정하지 못해 마감일에 임박하여 일정을 처리하는 고질적인 문제가 존재한다. 단순한 체크리스트 형태의 기존 'To-do 앱'들은 사용자의 자발적인 참여와 성취를 이끌어내는 강력한 동기 부여 기제가 부족한 실정이다.

**2) Goal**
본 프로젝트는 사용자의 일상적인 '과제 수행'을 '몬스터 사냥 및 생존'이라는 RPG 게임의 메커니즘으로 치환한 안드로이드(Android) 기반의 애플리케이션을 개발하는 것을 목표로 한다. 사용자는 앱 내에서 하나의 아바타가 되며, 과제를 완수할 시 경험치(EXP)와 재화(Gold)를 획득하고 마감일을 초과할 경우 체력(HP)이 감소하는 페널티를 부여받음으로써 능동적인 일정 관리를 수행하게 된다.

**3) Target Market**
일정 관리에 어려움을 겪고 확실한 동기 부여가 필요한 대학생 및 일반 수험생이 주 타겟이다. 시스템 내 수집한 재화(Gold)를 통해 '야구장 직관 예매권', 'PC 하드웨어 업그레이드' 등 개인 맞춤형 현실 보상(Custom Reward)을 교환할 수 있도록 하여 실질적인 효용성을 추구하는 모든 사용자를 대상으로 한다.

<br/>

- 4 -
Yeungnam University

--- 

**2. System context diagram**

아래는 프로젝트의 system context diagram이다.

*(참고: 시스템 구조도 이미지 삽입 영역)*

[그림 2-1] System context diagram

* **User (사용자):** 앱을 통해 신규 퀘스트(과제)를 등록 및 조회하고, 완료 처리를 통해 보상을 획득하며 상점 시스템과 상호작용한다.
* **System (안드로이드 앱):** 사용자 인터페이스(UI)를 제공하고, 내부 알고리즘(Priority Queue)을 통해 퀘스트의 우선순위를 자동 정렬하며 보상/페널티 산정 엔진을 구동한다.
* **Local Data (SQLite DB):** 플레이어의 현재 스탯(HP, EXP, Gold)과 퀘스트 메타데이터, 상점 아이템 정보를 기기 내부에 저장한다.
* **Background Server (백그라운드 서비스):** 앱이 종료된 상태에서도 자정을 기준으로 일일 퀘스트를 갱신하고, 마감일 초과 퀘스트를 색인하여 HP 차감을 실행한다.

<br/><br/><br/><br/>

- 5 -
Yeungnam University

--- 

**3. Use case list**

**1) 메인 퀘스트 등록**
<table>
  <tr>
    <th width="150" align="left">Actor</th>
    <td width="650">User</td>
  </tr>
  <tr>
    <th align="left">Description</th>
    <td>사용자가 수행해야 할 중요 과제(퀘스트명, 마감일, 난이도)를 시스템에 입력하여 등록한다.</td>
  </tr>
</table>

**2) 퀘스트 완료 처리 (공격)**
<table>
  <tr>
    <th width="150" align="left">Actor</th>
    <td width="650">User</td>
  </tr>
  <tr>
    <th align="left">Description</th>
    <td>사용자가 실제 현실에서 과제를 마친 직후 앱에서 완료 버튼을 눌러 경험치와 재화를 획득한다.</td>
  </tr>
</table>

**3) 아바타 스탯 및 진화 확인**
<table>
  <tr>
    <th width="150" align="left">Actor</th>
    <td width="650">User</td>
  </tr>
  <tr>
    <th align="left">Description</th>
    <td>사용자가 자신의 현재 HP, EXP 레벨을 확인하고, 레벨업에 따른 아바타의 진화 상태를 조회한다.</td>
  </tr>
</table>

**4) 상점 아이템 구매**
<table>
  <tr>
    <th width="150" align="left">Actor</th>
    <td width="650">User</td>
  </tr>
  <tr>
    <th align="left">Description</th>
    <td>사용자가 모은 Gold를 소모하여 미리 등록해 둔 현실 보상 아이템을 구매하고 인벤토리에 기록한다.</td>
  </tr>
</table>

**5) 일일 퀘스트 자동 생성**
<table>
  <tr>
    <th width="150" align="left">Actor</th>
    <td width="650">System</td>
  </tr>
  <tr>
    <th align="left">Description</th>
    <td>자정(00:00)이 되면 시스템이 자동으로 가벼운 일일 퀘스트(영단어 암기 등)를 생성하여 리스트에 추가한다.</td>
  </tr>
</table>

<br/>

- 6 -
Yeungnam University

--- 

**6) 퀘스트 우선순위 자동 정렬**
<table>
  <tr>
    <th width="150" align="left">Actor</th>
    <td width="650">System</td>
  </tr>
  <tr>
    <th align="left">Description</th>
    <td>시스템이 알고리즘을 통해 마감일이 임박하고 난이도가 높은 과제를 리스트 최상단에 자동 정렬한다.</td>
  </tr>
</table>

**7) 마감일 초과 페널티 부여**
<table>
  <tr>
    <th width="150" align="left">Actor</th>
    <td width="650">System</td>
  </tr>
  <tr>
    <th align="left">Description</th>
    <td>백그라운드에서 마감일이 지난 미완료 퀘스트를 탐지하여 플레이어의 체력(HP)을 차감시킨다.</td>
  </tr>
</table>

<br/><br/><br/><br/><br/><br/><br/><br/>

- 7 -
Yeungnam University

--- 

**4. Concept of operation**

**1) 메인 퀘스트 등록**
<table>
  <tr>
    <th width="150" align="left">Purpose</th>
    <td width="650">사용자가 수행해야 할 중요 과제 및 일정을 시스템에 등록한다.</td>
  </tr>
  <tr>
    <th align="left">Approach</th>
    <td>과제명, 마감일(Deadline), 난이도(Difficulty) 정보를 입력받아 <code>MainQuest</code> 객체 인스턴스를 생성한 후, 로컬 데이터베이스에 저장한다.</td>
  </tr>
  <tr>
    <th align="left">Dynamics</th>
    <td>새로운 학업 일정이나 개인 과제가 발생했을 때 사용자가 능동적으로 입력 이벤트를 발생시킨다.</td>
  </tr>
  <tr>
    <th align="left">Goals</th>
    <td>불규칙한 사용자의 일정을 시스템이 계산 및 통제할 수 있는 규격화된 데이터로 변환한다.</td>
  </tr>
</table>

**2) 퀘스트 완료 처리 (공격)**
<table>
  <tr>
    <th width="150" align="left">Purpose</th>
    <td width="650">수행을 마친 과제를 시스템 상에서 완료 처리하고 그에 상응하는 보상을 획득한다.</td>
  </tr>
  <tr>
    <th align="left">Approach</th>
    <td>사용자가 퀘스트 완료 버튼을 트리거하면, 시스템의 보상 엔진이 난이도 가중치를 계산하여 플레이어의 EXP와 Gold를 증가시킨다.</td>
  </tr>
  <tr>
    <th align="left">Dynamics</th>
    <td>사용자가 목표한 과제를 성공적으로 마친 직후 앱을 실행하여 상호작용하는 경우.</td>
  </tr>
  <tr>
    <th align="left">Goals</th>
    <td>사용자에게 즉각적이고 시각적인 긍정적 강화를 제공하여 지속적인 행동 유발을 이끌어낸다.</td>
  </tr>
</table>

**3) 아바타 스탯 및 진화 확인**
<table>
  <tr>
    <th width="150" align="left">Purpose</th>
    <td width="650">사용자의 현재 상태와 성장 진행도를 직관적으로 파악하게 한다.</td>
  </tr>
  <tr>
    <th align="left">Approach</th>
    <td>메인 화면에 생성형 AI로 제작된 레벨별 아바타 이미지를 렌더링하고 HP/EXP 게이지 바를 표시한다.</td>
  </tr>
  <tr>
    <th align="left">Dynamics</th>
    <td>사용자가 앱을 실행하거나 특정 퀘스트를 완료하여 스탯의 변화가 생겼을 경우.</td>
  </tr>
  <tr>
    <th align="left">Goals</th>
    <td>RPG 게임의 감성을 극대화하여 시각적 성취감과 몰입도를 높인다.</td>
  </tr>
</table>

<br/>

- 8 -
Yeungnam University

--- 

**4) 상점 아이템 구매**
<table>
  <tr>
    <th width="150" align="left">Purpose</th>
    <td width="650">앱 내에서 수집한 재화를 소모하여 사용자 개인에게 유의미한 현실 보상을 획득한다.</td>
  </tr>
  <tr>
    <th align="left">Approach</th>
    <td>사용자가 사전에 가치를 설정해 둔 커스텀 아이템 리스트에서 물품을 선택하면, 시스템은 보유 Gold를 차감하고 구매 내역을 기록한다.</td>
  </tr>
  <tr>
    <th align="left">Dynamics</th>
    <td>사용자의 보유 Gold가 목표 아이템의 요구 수치를 충족하여 구매를 희망하는 경우.</td>
  </tr>
  <tr>
    <th align="left">Goals</th>
    <td>가상의 재화를 현실의 보상으로 치환하여 시스템 사용의 궁극적인 동력을 확보한다.</td>
  </tr>
</table>

**5) 일일 퀘스트 자동 생성**
<table>
  <tr>
    <th width="150" align="left">Purpose</th>
    <td width="650">앱 접속 빈도를 높이고 사용자에게 매일 가벼운 성취 목표를 제공한다.</td>
  </tr>
  <tr>
    <th align="left">Approach</th>
    <td><code>DailyQuest</code> 객체를 안드로이드 백그라운드 스케줄러를 활용하여 매일 특정 시간에 DB에 자동으로 Insert 한다.</td>
  </tr>
  <tr>
    <th align="left">Dynamics</th>
    <td>기기 시간이 자정(00:00)을 지나는 시점.</td>
  </tr>
  <tr>
    <th align="left">Goals</th>
    <td>무거운 전공 과제 외에도 일상적인 목표 달성을 유도하여 지속적인 앱 사용(Retention)을 유지한다.</td>
  </tr>
</table>

<br/><br/><br/>

- 9 -
Yeungnam University

--- 

**6) 퀘스트 우선순위 자동 정렬**
<table>
  <tr>
    <th width="150" align="left">Purpose</th>
    <td width="650">다수의 과제 중 가장 먼저 처리해야 할 과제를 최상단에 노출한다.</td>
  </tr>
  <tr>
    <th align="left">Approach</th>
    <td><code>Priority Queue</code>(우선순위 큐) 자료구조를 활용하여, 마감일 임박도와 난이도를 합산한 가중치를 기준으로 리스트를 동적 정렬한다.</td>
  </tr>
  <tr>
    <th align="left">Dynamics</th>
    <td>애플리케이션 초기 실행 시, 혹은 새로운 퀘스트 객체가 추가되거나 완료 처리될 경우.</td>
  </tr>
  <tr>
    <th align="left">Goals</th>
    <td>사용자의 스케줄링 및 의사결정에 소모되는 인지적 에너지를 최소화한다.</td>
  </tr>
</table>

**7) 마감일 초과 페널티 부여**
<table>
  <tr>
    <th width="150" align="left">Purpose</th>
    <td width="650">마감일을 지키지 못한 사용자에게 손실 회피 심리를 자극하여 동기를 부여한다.</td>
  </tr>
  <tr>
    <th align="left">Approach</th>
    <td>백그라운드 시스템이 현재 시간과 퀘스트의 마감일을 비교하여, 초과된 미완료 퀘스트가 있을 시 플레이어의 HP를 즉각 차감한다.</td>
  </tr>
  <tr>
    <th align="left">Dynamics</th>
    <td>등록된 과제의 마감 시간이 경과하였음에도 사용자가 완료 처리를 하지 않은 경우.</td>
  </tr>
  <tr>
    <th align="left">Goals</th>
    <td>HP가 0이 될 시의 강력한 페널티(골드 초기화 등)를 피하기 위해 사용자가 미루는 습관을 고치도록 유도한다.</td>
  </tr>
</table>

<br/><br/><br/>

- 10 -
Yeungnam University

--- 

**5. Problem statement**

**Overview**
이 시스템은 외부 서버에 의존하지 않고 안드로이드 기기 내부의 환경에서 단독으로 완벽히 동작해야 한다. 따라서 제한된 모바일 환경 리소스 내에서 알고리즘의 효율성을 확보하고, 다음과 같은 목적과 문제점들을 달성 및 해결해야 한다.

**1) Problem #1 Data Sorting Efficiency**
사용자가 등록한 수많은 퀘스트 데이터가 변경될 때마다 정렬 알고리즘이 동작해야 한다. 이 과정에서 O(N^2) 이상의 비효율적인 정렬을 사용할 경우 모바일 기기의 배터리 소모와 UI 렌더링 지연이 발생할 수 있다. 우선순위 큐(Priority Queue)를 활용한 효율적인 객체지향 설계 최적화가 필수적이다.

**2) Problem #2 Background Processing**
사용자가 앱을 완전히 종료한 상태에서도 자정이 되면 일일 퀘스트가 리셋되고, 마감일이 지난 퀘스트를 색인하여 플레이어의 HP를 차감해야 한다. 이를 위해 안드로이드의 `WorkManager`를 활용하여 시스템 자원 소모를 최소화하면서도 정확한 시점에 스케줄링 작업을 수행하는 기술적 안정성이 확보되어야 한다.

**3) Problem #3 Graphic Resource Limitation**
RPG 컨셉의 몰입도를 극대화하기 위해서는 레벨 구간별로 변화하는 고품질의 아바타 일러스트가 다수 필요하다. 1인 개발 환경에서 이를 직접 디자인하는 것은 불가능하므로, 생성형 AI 도구를 활용하여 일관된 톤앤매너를 가진 스프라이트 이미지를 확보하는 방안이 요구된다.

**NFRS**
① 본 시스템은 안드로이드 OS 기반의 모바일 기기에서 정상적으로 동작해야 한다.
② 코어 로직 및 UI 제어는 Java 언어를 기반으로 구현한다.
③ 퀘스트 조회 및 우선순위 정렬 처리에 따른 UI 업데이트는 1초 이내에 완료되어야 한다.
④ 모든 데이터는 기기 내부의 SQLite에 저장되며, 불필요한 네트워크 통신은 배제한다.

<br/>

- 11 -
Yeungnam University

--- 

**6. Glossary**

<table>
  <tr>
    <th width="200" align="center">Terms</th>
    <th width="600" align="center">Description</th>
  </tr>
  <tr>
    <td align="center"><b>HP (Health Point)</b></td>
    <td>시스템 내 플레이어의 생존력을 나타내는 수치. 마감일 초과 시 차감되며, 0에 도달할 경우 심각한 시스템 페널티(골드 삭감 등)가 부여된다.</td>
  </tr>
  <tr>
    <td align="center"><b>EXP (Experience Point)</b></td>
    <td>과제 수행을 통해 획득하는 경험치. 특정 임계치 도달 시 플레이어의 레벨이 상승하고 아바타가 상위 단계로 진화한다.</td>
  </tr>
  <tr>
    <td align="center"><b>Gold</b></td>
    <td>앱 내 통용되는 가상의 재화 단위. 퀘스트 완료 시 획득하며, 상점(Shop) 시스템에서 현실 보상 아이템을 교환하는 매개체로 사용된다.</td>
  </tr>
  <tr>
    <td align="center"><b>MainQuest</b></td>
    <td>사용자가 직접 마감일과 난이도를 입력하여 생성하는 메인 과제 객체.</td>
  </tr>
  <tr>
    <td align="center"><b>DailyQuest</b></td>
    <td>자정마다 시스템이 자동으로 부여하는 가벼운 일일 목표(예: 영단어 암기 등)를 정의하는 객체.</td>
  </tr>
  <tr>
    <td align="center"><b>Priority Queue</b></td>
    <td>퀘스트 객체가 가진 데이터(마감일, 난이도)를 연산하여 높은 우선순위를 가진 원소를 먼저 반환하는 자료구조.</td>
  </tr>
</table>

<br/><br/><br/><br/>

- 12 -
Yeungnam University

--- 

**7. References**

1) Android Developers Official Documentation
   https://developer.android.com
2) Java Platform, Standard Edition Documentation (Oracle)
   https://docs.oracle.com/en/java/
3) "Introduction to Algorithms, 3rd Edition" (Thomas H. Cormen) - Priority Queue 구현 참조

<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

- 13 -
Yeungnam University
