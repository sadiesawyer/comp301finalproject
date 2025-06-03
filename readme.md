# Dungeon Crawler

Dungeon Crawler is a 2D turn-based adventure game I built in Java, complete with level progression, enemy encounters, and collectible treasure. The backend is fully custom — I designed everything from the board logic to collision detection, and then connected it to a JavaFX user interface that supports both light/dark mode and easy/hard difficulty settings.

## What It Does

You control a hero navigating through randomly generated dungeon levels:

- Collect treasure to boost your score.
- Avoid enemies, or it's game over.
- Find the exit to reach the next level — each one harder than the last.
- Play in **easy** mode for fewer enemies, or try **hard** mode for a real challenge.
- Switch between **light** and **dark** mode to match your preference.

## What I Built

- A full game engine in Java with its own rules for movement, collisions, and scoring.
- Clean **Model-View-Controller (MVC)** architecture and the **Observer** pattern to keep the UI and logic decoupled.
- A modular system of game pieces (`Hero`, `Enemy`, `Treasure`, `Wall`, `Exit`) that all follow consistent interfaces.
- Smart game state management: levels, scores, status updates, and transitions are all tracked under the hood.
- A JavaFX frontend that reacts to the model in real time — when something changes, the display updates automatically.

## Favorite Features

- **Difficulty levels**: Easy mode has fewer enemies; hard mode ramps things up with every level.
- **Theming support**: Toggle between light and dark mode.
- **Collision system**: Every interaction (hero hitting a wall, enemy eating treasure, etc.) returns a custom result that drives the game forward.
- **Clean code**: The backend is fully testable, extendable, and ready to hook into other UIs or platforms.

## Coming Soon

Once the UI is fully polished, I’ll add screenshots and a short gameplay video.

---

**Built by Sadie Sawyer**  
Computer science student at UNC-Chapel Hill who enjoys building clever, clean, and fun-to-use software.
