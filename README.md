# 🎮 All Worlds For Her

**All Worlds For Her** is a console-based RPG game developed in **Java**, where the player travels through an interdimensional labyrinth to find a cure for the person they love.  
Every decision matters: combat, risk events, resource management, and strategic choices determine whether the hero survives… or not.

---

## 📖 Story

The cure exists — just not in this world.

To save the person you love, you must enter a **labyrinth between worlds**, where each room belongs to a different universe.  
Along the way, you will meet powerful allies, face dangerous enemies, and cross extremely risky paths.

Your body carries a **weak point**, directly linked to the illness of the person you are trying to save.  
If an enemy strikes that weak point, the damage will be devastating.

Only those who reach the **Core of the Labyrinth** can win.

---

## 🧍 Playable Characters

The player can choose between **three characters**, each with a unique special attack:

- 🏥 **Nurse**  
  Special attack inspired by hospital irony and endless waiting lines.

- 📦 **Courier**  
  Special attack using an “urgent delivery” — violently delivered.

- 📚 **Teacher**  
  Special attack based on linear algebra concepts capable of confusing any enemy.

---

## 🧩 Core Mechanics

### ⚔️ Combat System
- Normal attacks
- Special attacks (once per combat)
- Combat consumables
- **Weak point system** (double damage when hit)
- Health is **not restored automatically** after combat

---

### 💊 Items
- **Potions** (heal health and/or increase strength)
- **Combat consumables** (instant damage)
- **Weapons** (normal and special attack bonuses)

Items may have **character restrictions**.

---

### 🛒 Shop
- Dynamic shop with random offers
- Purchase items using gold earned in combat
- Weapons are equipped automatically
- Consumables are stored in the inventory

---

### 🧨 Risk Events
Special rooms where the player must choose:

- 🐢 Go slowly → lower risk
- ⚡ Go fast → higher risk

A single mistake can result in **instant death** and game over.

---

### 🗺️ Labyrinth (Graph-Based)
- Rooms connected by **names** (graph structure)
- Multiple possible paths
- Each room is cleared only once
- Room types:
  - Combat rooms
  - Shop rooms
  - Risk event rooms
  - Final boss room

---

## 🔊 Audio & Effects

The game includes sound effects for:
- Game start
- Combat
- Normal attacks
- Special attacks
- Weak point hits
- Shop interaction
- Room transitions
- Victory and defeat

Audio files should be placed in:
  src/resources/audio

---

## 🧱 Project Structure


src/
├── audio/
│   └── Audio.java
├── entidades/
│   ├── Entity.java
│   ├── Hero.java
│   ├── Nurse.java
│   ├── Courier.java
│   ├── Teacher.java
│   ├── NPC.java
│   └── Vendor.java
├── itens/
│   ├── Item.java
│   ├── Weapon.java
│   ├── Consumable.java
│   ├── Potion.java
│   └── CombatConsumable.java
├── jogo/
│   ├── Main.java
│   ├── Game.java
│   ├── Room.java
│   ├── RiskEvent.java
│   ├── WeakPoint.java
│   └── ConsoleFX.java
└── resources/
    └── audio/

  ▶️ How to Run
	1.	Open the project in a Java IDE (IntelliJ IDEA, Eclipse, VS Code)
	2.	Make sure the resources/audio folder contains the .wav files
	3.	Run the following class:
      jogo.Main

  📚 Object-Oriented Concepts Used
	•	Encapsulation
	•	Inheritance
	•	Polymorphism
	•	Abstract classes
	•	Enumerations
	•	Composition
	•	Graph-based navigation
	•	Package separation
	•	Full Javadoc documentation


👤 Author

Concept, design, and development by José Pinho 
Java Object-Oriented Programming Project


This project was developed with a strong focus on:
	•	Clean object-oriented design
	•	Code quality and readability
	•	Console user experience
	•	Emotional storytelling

Feel free to fork, modify, or expand the game 🚀
