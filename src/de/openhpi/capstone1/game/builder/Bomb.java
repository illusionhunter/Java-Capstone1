package de.openhpi.capstone1.game.builder;

import de.openhpi.capstone1.game.constants.Event;
import de.openhpi.capstone1.game.view.AbstractView;

public class Bomb extends InteractiveComponent {

	boolean isAlive = true;
	int bombType = 1;
	int missleCount = 0;

	private Ship ship;

	public boolean isAlive() {
		return isAlive;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	@Override
	public void handleEvent(Event event) {
		if (event == Event.BOTTOMEND) {
			if (this.positionY >= 380) {
				this.isAlive = false;
			}
		}

		if (event == Event.ENDOFFRAME) {

			if (bombType == 3 && missleCount++ < 120) {
				float x = ship.getPositionX() + 10;
				if (positionX < x && (x - positionX) < 100) {
					positionX++;
				}
				if (positionX > x && positionX - x < 100) {
					positionX--;
				}
				positionY++;
			} else {
				positionY += bombType;
			}

		}

	}

	protected AbstractView[] views;

	public void update() {
		for (AbstractView view : views) {
			view.update();
		}
	}
}
