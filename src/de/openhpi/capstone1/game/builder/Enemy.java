package de.openhpi.capstone1.game.builder;

import java.util.ArrayList;
import java.util.Random;

import de.openhpi.capstone1.game.constants.*;

public class Enemy extends InteractiveComponent {

	boolean isAlive = true;
	boolean isHurt = false;
	boolean isSuper = false;

	int hitCount = 0;
	int dropCount = 0;
	int coolingCount = 0;
	float speed = 0.1f;

	Random random = new Random();

	Direction direction = Direction.RIGHT;

	private Ship ship;
	private ArrayList<Bomb> bombList;

	public Enemy(ArrayList<Bomb> bombList) {
		this.bombList = bombList;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public boolean isHurt() {
		return isHurt;
	}

	public boolean isSuper() {
		return isSuper;
	}
	
	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	@Override
	public void handleEvent(Event event) {
		if (event == Event.RIGHTEND) {
			this.positionX -= 1;
			this.positionY += 5;
			direction = Direction.LEFT;
		}

		if (event == Event.LEFTEND) {
			this.positionX += 1;
			this.positionY += 5;
			direction = Direction.RIGHT;
		}

		if (event == Event.FIREHIT) {

			// normal enemy drops 1 type 2 bomb when it's hit
			// super enemy drops 5 type 2 bombs when it's hit

			if (coolingCount >= 60) {
				if (this.isSuper) {
					for (int i = 0; i < 5; i++) {
						Bomb singleBomb = createBomb(this, 2);
						singleBomb.positionX += i * 5;
						bombList.add(singleBomb);
					}
				} else {
					bombList.add(createBomb(this, 2));
				}
				coolingCount = 0;
			}
			// normal enemy can bear two hits, after one hit isHurt is true.
			// super enemy can bear additional 5 hits, counted by hitCount.

			if (!this.isHurt) {
				if (this.isSuper && this.hitCount < 5) {
					this.hitCount++;
				} else {
					this.isHurt = true;
				}
			} else if (this.isAlive) {
				this.isAlive = false;
			}
		}

		if (event == Event.SPEEDUP) {
			if (speed < 1) {
				speed += 0.1f;
			}
		}
		
		if (event == Event.SETSUPER) {
			this.isSuper = true;
		}

		if (event == Event.ENDOFFRAME) {

			if (this.isAlive) {

				if (direction == Direction.RIGHT) {
					positionX += speed;
				} else {
					positionX -= speed;
				}

				coolingCount++;

				if (coolingCount > 120) { // prevent infinite coolingCount increase for bomb type 2
					coolingCount = 60;
				}

				if (dropCount < 120) { // prevent too many bombing, used for bomb type 1
					dropCount++;
				} else { // reset dropCount and drop bomb randomly
					dropCount = 0;
					if ((int) random.nextInt(100) % 5 == 0) {
						bombList.add(createBomb(this, 1));
					}
				}
			}

		}
	}

	private Bomb createBomb(Enemy e, int type) {
		Bomb singleBomb = (Bomb) InteractiveComponentBuilder.create("Bomb");
		singleBomb.positionX = e.positionX + 14;
		singleBomb.positionY = e.positionY + 10;
		if (e.isSuper() && type == 1) {
			singleBomb.bombType = 3;
		}else {
			singleBomb.bombType = type;
		}
		singleBomb.setShip(this.getShip());
		return singleBomb;
	}


}
