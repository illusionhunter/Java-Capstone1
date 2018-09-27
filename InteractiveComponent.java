package de.openhpi.capstone1.game.builder;

import de.openhpi.capstone1.game.constants.Event;
import de.openhpi.capstone1.game.view.AbstractView;

public abstract class InteractiveComponent {

	float positionX;


	float positionY;
	
	public abstract void handleEvent(Event event);
	
	public float getPositionX() {
		return positionX;
	}

	public float getPositionY() {
		return positionY;
	}
	
	
	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}
	
	protected AbstractView[] views; 
	
	public void update() {
		for (AbstractView view : views) {
			view.update(); 
		}
	}

}
