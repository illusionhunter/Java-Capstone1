package de.openhpi.capstone1.game.starter;

import java.util.ArrayList;

import de.openhpi.capstone1.game.builder.*;
import de.openhpi.capstone1.game.controller.*;
import de.openhpi.capstone1.game.constants.*;
import processing.core.PApplet;
import processing.core.PFont;

public class TheApp extends PApplet {

	Ship ship;
	ArrayList<Enemy> enemyList;
	ArrayList<Fire> fireList;
	ArrayList<Bomb> bombList;

	EnemyController enemyController;
	FireController fireController;
	BombController bombController;
	ShipController shipController;

	PFont font;

	float enemyBottomY;
	float enemyLeftX;
	float enemyRightX;
	int enemyCount;

	int score = 0;
	boolean superMode = false;

	public ArrayList<Fire> getFireList() {
		return fireList;
	}

	public ArrayList<Bomb> getBombList() {
		return bombList;
	}

	@Override
	public void settings() {
		size(400, 420);

	}

	@Override
	public void setup() { // setup() runs once

		font = createFont("Arial", 16, true); // Arial, 16 point, anti-aliasing on

		enemyList = new ArrayList<Enemy>();
		fireList = new ArrayList<Fire>();
		bombList = new ArrayList<Bomb>();

		enemyController = new EnemyController(enemyList);
		fireController = new FireController(fireList);
		bombController = new BombController(bombList);

		ship = (Ship) InteractiveComponentBuilder.create(this, "Ship");
		shipController = new ShipController(ship);

		// initialize the enemy matrix

		for (int i = 0; i < 48; i++) {
			Enemy singleEnemy = (Enemy) InteractiveComponentBuilder.create(this, "Enemy");
			singleEnemy.setPositionX(singleEnemy.getPositionX() + (i % 8) * 40);
			singleEnemy.setPositionY(singleEnemy.getPositionY() + (i / 8) * 15);
			enemyList.add(singleEnemy);
		}

		// randomly choose some enemies to be super enemy

		for (int i = 0; i < 5; i++) {
			int k = (int) random(47);
			enemyList.get(k).handleEvent(Event.SETSUPER);
		}

	}

	@Override
	public void draw() { // draw() loops forever, until stopped

		// this is the view for MVC, automatically updated every frame, no need for
		// additional views

		background(255);
		fill(0);
		drawEnemy();
		drawShip();
		drawFire();
		drawBomb();
		drawScore();
		drawSuperMode();

	}

	private void drawEnemy() {

		enemyCount = 0;
		enemyLeftX = 400;
		enemyRightX = 0;
		enemyBottomY = 0;

		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				if (e.isHurt()) {
					fill(255);
					rect(e.getPositionX(), e.getPositionY(), 20, 10);

				} else if (e.isSuper()) {
					fill(255, 0, 0);
					rect(e.getPositionX(), e.getPositionY(), 20, 10);
				} else {
					fill(0);
					rect(e.getPositionX(), e.getPositionY(), 20, 10);

				}

				if (e.getPositionX() > enemyRightX) {
					enemyRightX = e.getPositionX();
				}
				if (e.getPositionY() > enemyBottomY) {
					enemyBottomY = e.getPositionY();
				}

				if (e.getPositionX() < enemyLeftX) {
					enemyLeftX = e.getPositionX();
				}
				enemyCount++;
			}
		}

		if (enemyRightX >= 380) {
			enemyController.handleEvent(Event.RIGHTEND);
		} else if (enemyLeftX <= 0) {
			enemyController.handleEvent(Event.LEFTEND);
		}

		if (enemyBottomY + 10 >= ship.getPositionY() - 5) { // reached spaceship
			gameOver("Lost");
		}

		if (enemyCount == 0) { // cleared enemies
			gameOver("Won");
		}

		enemyController.handleEvent(Event.ENDOFFRAME);

	}

	private void drawShip() {
		fill(0);
		rect(ship.getPositionX(), ship.getPositionY(), 20, 20);
		shipController.handleEvent(Event.ENDOFFRAME);

	}

	private void drawFire() {
		for (Fire f : fireList) {
			if (f.isAlive()) {
				fill(0);
				if (f.getFireType() == 1 || f.getFireType() == 3) {
					rect(f.getPositionX(), f.getPositionY(), 1, 5);
				} else {
					rect(f.getPositionX(), f.getPositionY(), 1, 50);

				}
				for (Enemy e : enemyList) {
					if (detectFireHit(f, e)) {
						if (e.isSuper()) {
							score += 50;
						} else {
							score += 10;
						}
						fireController.handleEvent(Event.FIREHIT, f);
						enemyController.handleEvent(Event.FIREHIT, e);

					}
				}
			}

			if (f.getPositionY() == 5) {
				fireController.handleEvent(Event.TOPEND, f);
			}
		}
		fireController.handleEvent(Event.ENDOFFRAME);

	}

	private void drawBomb() {
		for (Bomb b : bombList) {
			if (b.isAlive()) {
				rect(b.getPositionX(), b.getPositionY(), 1, 5);
				if (detectBombHit(b)) { // bomb hit spaceship
					gameOver("Lost");
				}
				if (b.getPositionY() >= 380) {
					bombController.handleEvent(Event.BOTTOMEND, b);
				}
			}

		}

		bombController.handleEvent(Event.ENDOFFRAME);
	}

	private void drawScore() {
		textFont(font, 14);
		fill(128);
		text("SCORE: " + score, 20, 400);

	}
	
	private void drawSuperMode() {
		if (superMode) {
			fill(128);
		}else {
			fill(255);
		}
		textFont(font, 14);
		text("SUPER MODE", 280, 400);
	}

	private boolean detectFireHit(Fire f, Enemy e) {

		if (!e.isAlive() || !f.isAlive()) {
			return false;
		}

		float fx = f.getPositionX();
		float fy = f.getPositionY();
		float ex = e.getPositionX();
		float ey = e.getPositionY();

		int height = 5;
		if (f.getFireType() == 2) {
			height = 50;
		}

		if (fx > ex && fx < ex + 20) {
			if ((fy > ey && fy < ey + 10) || (fy + height > ey && fy + height < ey + 10)
					|| (fy < ey && fy + height > ey + 10)) {
				return true;
			}

		}
		return false;
	}

	private boolean detectBombHit(Bomb b) {

		float bx = b.getPositionX();
		float by = b.getPositionY();
		float sx = ship.getPositionX();
		float sy = ship.getPositionY();

		return b.isAlive() && bx > sx && bx < sx + 20 && by > sy && by < sy + 20;
	}

	private void gameOver(String result) {

		textFont(font, 36);
		background(255);
		fill(128);
		if ("Lost".equals(result)) {
			text("Game Over!", 100, 200);
		}

		if ("Won".equals(result)) {
			text("You Win!", 120, 200);
		}

		noLoop();

	}

	// Add further user interaction as necessary
	@Override
	public void mouseClicked() {

		if (mouseButton == LEFT) {
			ship.handleEvent(Event.MOUSECLICKLEFT);
		}

		if (mouseButton == RIGHT) {
			ship.handleEvent(Event.MOUSECLICKRIGHT);
		}

	}

	@Override
	public void mouseMoved() {
		shipController.handleEvent(Event.MOUSEMOVE, mouseX);

	}

	@Override
	public void keyPressed() {
		if (key == 's' || key == 'S') {
			if (superMode) {
				shipController.handleEvent(Event.SUPERMODEOFF);
				superMode = false;
			} else {
				shipController.handleEvent(Event.SUPERMODEON);
				superMode = true;
			}
		}

	}


}
