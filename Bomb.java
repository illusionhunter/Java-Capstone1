package de.openhpi.capstone1.game.builder;

import de.openhpi.capstone1.game.constants.Event;
import de.openhpi.capstone1.game.view.AbstractView;

public class Bomb extends InteractiveComponent {
	
	boolean isAlive = true;
	int bombType = 1;
	
	public boolean isAlive() {
		return isAlive;
	}
	
	@Override
	public void handleEvent(Event event) {
		if (event == Event.BOTTOMEND) {
			if (this.positionY >= 380 ) {
				this.isAlive = false;
			}
		}
				
		if (event == Event.ENDOFFRAME) {
			positionY += bombType;

		}
		
	}
	
	protected AbstractView[] views; 
	
	public void update() {
		for (AbstractView view : views) {
			view.update(); 
		}
	}
}
