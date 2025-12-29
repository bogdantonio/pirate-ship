<img width="1240" height="150" alt="🍻Booze_ _Guns🔫" src="https://github.com/user-attachments/assets/be6a474d-00ce-4254-b1cd-297cc8cc0ab8" />

**Booze & Guns** is a text-based pirate adventure game where you assemble a crew, face dangerous encounters, and attempt to survive a perilous journey in search of legendary treasure.

![Static Badge](https://img.shields.io/badge/4y3l-oop_project-%23E34F1E)
![GitHub last commit](https://img.shields.io/github/last-commit/bogdantonio/pirate-ship)
![GitHub commit activity](https://img.shields.io/github/commit-activity/w/bogdantonio/pirate-ship)
![GitHub repo size](https://img.shields.io/github/repo-size/bogdantonio/pirate-ship)

---

## 📑 Table of Contents
- [About](#about)
- [Running the App](#running-the-app)
- [How to Play](#how-to-play)
- [Project Structure](#project-structure)
- [User Interface](#user-interface)

---

## 📖 About

To build the game the **Java programming language** was used.  
It uses **PostgreSQL** for database implementation, while **HTML, CSS, and JavaScript** are used to create a clean and interactive user interface.

### 🏴‍☠️ What is the premise of the game?

Your aim is to become the captain of a great pirate crew that seeks the greatest treasure of them all. To do this, you have to find crewmates worthy enough to sail alongside you and suited to your needs. Be wary-some of them are more skilled than others. 

Once the crew has been assembled, the adventure begins. Throughout the journey, your crew will face various encounters. These take one of two forms:

- ⚔️ Battles against enemies that require the strength of the entire crew  
- 🎯 Challenges that test the skills of individual crew members  

Some encounters will be manageable, while others may prove too difficult.

To reach your objective, you must successfully pass enough encounters. Failing more than **three** will bring your adventure to an end.

---

## ▶️ Running the App

1. Navigate to the `JavaAPIServer` class and run the program.
2. Open the `welcome.html` file in a web browser.
   - If you are using **IntelliJ IDEA**, the HTML file can be opened directly in the browser from within the IDE.

---

## 🎮 How to Play

1. On the welcome screen, press **“Start a New Tale”**.
2. You will be taken to the **Ship’s Manifest**, where you must enter:
   - Your name
   - Your nickname
   - Your pirate crew’s name
3. Press **“Sign & Set Sail”**.

### 🧑‍🤝‍🧑 Crew Selection
- There are **9 roles** that must be filled (e.g. second, navigator, sniper, cook).
- Each candidate has:
  - General statistics
  - Role-specific statistics
- To maximize your chances of success, try to find the best combination of attributes.

Once your crew is assembled, press **“Set Sail”** to begin the adventure.

### ⚠️ Encounters
- The **left side** of the screen displays the event type (enemy or crew member) and its requirements.
- The **right side** displays the event prompt and the **“Attempt”** button.
- Press **“Continue”** to move to the next event.

### 🏆 Winning & Losing
- There are **10 total events**
- Pass **at least 7** to win
- Fail **more than 3** to lose
- Press **“Play Again”** to start a new adventure

---

## 🧱 Project Structure

### 📦 adventure
- `Adventure` – Runs the adventure using a `Crew` and an `EventSet`
- `AdventureDemo` – Demo for the adventure logic

### 📦 crew
- `Crew` – Handles crew building and crew-wide information
- `CrewDemo` – Demo for crew construction
- `ExistingRoleException` – Thrown when adding a pirate with a duplicate role
- `FullCrewException` – Thrown when exceeding crew size limit

### 📦 database
- `DataBaseConnection` – Database connection test
- `DataBaseCredentials` – Database credentials (**gitignored**)
- `InsertQuery` – Insert query methods
- `SelectQuery` – Select query methods

### 📦 events
#### enemy
- `Enemy` – Enemy information
- `Faction` – Enum for enemy factions
- `EnemyEvent` – Enemy-type event implementation

#### general
- `Event` – Wrapper for event types
- `EventDemo` – Event set demo
- `EventInterface` – Common interface for all event types
- `EventSet` – Holds all events
- `EventType` – Enum (`Enemy` / `PirateSubclass`)
- `PirateSubclassEvent` – Pirate subclass event implementation

### 📦 pirateSubclasses
#### pirate
- `Pirate` – Pirate information
- `PirateStatSet` – General pirate statistics
- `Role` – Enum of pirate roles
- `InvalidDataException` – Thrown on invalid object data

#### subclasses
- `Archeologist`
- `Cook`
- `Doctor`
- `Helmsman`
- `Musician`
- `Navigator`
- `Second`
- `Shipwright`
- `Sniper`

### ⚙️ Other
- `JavaAPIServer` – Handles communication between Java backend and browser requests
- `Main` – Console entry point for the application

---

## 🖥️ User Interface

The user interface was built using **HTML**, **CSS**, and **JavaScript**, with a strong focus on maintaining a consistent pirate theme.

A pirate-themed font (**Pirate One**, provided by Google Fonts) and a background image sourced from the internet were used to enhance immersion. All other visual assets were AI-generated using the **Nano Banana Pro** model to match the game’s aesthetic and ensure visual consistency across screens.

The application is divided into multiple screens such as:
- Welcome
- Ship’s Manifest
- Crew Members

This structure ensures smooth navigation and keeps the gameplay flow intuitive and easy to follow.

---
