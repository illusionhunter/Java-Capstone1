package de.openhpi.capstone1.game.builder;

import de.openhpi.capstone1.game.constants.Event;
import de.openhpi.capstone1.game.view.AbstractView;

public class Fire extends InteractiveComponent {
	
	boolean isAlive = true;
	int fireType = 1;
	int hitCount = 0;

	public boolean isAlive() {
		return isAlive;
	}
	
	public int getFireType() {
		return fireType;
	}

	@Override
	public void handleEvent(Event event) {
		if (event == Event.TOPEND) {
			if (this.positionY <=5 ) {
				this.isAlive = false;
			}
		}
		
		if (event == Event.FIREHIT) {
			if (this.fireType == 2 && this.hitCount < 2) {
				this.hitCount++;
			}else {
				this.isAlive = false;
			}
		}
		
		if (event == Event.ENDOFFRAME) {
			if (fireType != 3) {
				positionY -= 1;
			}else {
				positionY -= 2;
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
