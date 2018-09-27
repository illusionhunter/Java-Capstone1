package de.openhpi.capstone1.game.builder;

import java.util.ArrayList;

import de.openhpi.capstone1.game.constants.Event;
import de.openhpi.capstone1.game.view.AbstractView;

public class Ship extends InteractiveComponent {
	
	int fireType = 1;
	int coolingCount = 0;
	
	private ArrayList<Fire> fireList;
	
	public Ship (ArrayList<Fire> fireList) {
		this.fireList = fireList;
	}
	

	
	@Override
	public void handleEvent(Event event) {
		
		if (event == Event.SUPERMODEON) {
			fireType = 3;
		}
		
		if (event == Event.SUPERMODEOFF) {
			fireType = 1;
		}
		
		
		if (event == Event.MOUSECLICKLEFT) {
			if (fireType == 2) {
				if (coolingCount >= 60) {
					createFire();
					coolingCount = 0;
				}
			} else if (fireType == 3){
				createSuperFire();
			}else {
				createFire();
			}
		}
		
		if (event == Event.MOUSECLICKRIGHT) {
			switchFire();
		}
		
		if (event == Event.ENDOFFRAME) {
			coolingCount++;
			if (coolingCount > 120) {
				coolingCount = 60;
			}
		}

		
	}

	private void switchFire() {
		if (fireType == 3) {
			return;
		}
		if (fireType == 1) {
			fireType = 2;
		} else {
			fireType = 1;
		}
	}

	private void createFire() {
		Fire SingleFire = (Fire) InteractiveComponentBuilder.create("Fire");
		SingleFire.positionX = this.positionX + 10;
		SingleFire.positionY = this.positionY;
		SingleFire.fireType = fireType;
		fireList.add(SingleFire);		
	}
	
	private void createSuperFire() {
		for (int i=0; i<3; i++) {
			Fire SingleFire = (Fire) InteractiveComponentBuilder.create("Fire");
			SingleFire.positionX = this.positionX + 5 + i*3;
			SingleFire.positionY = this.positionY;
			SingleFire.fireType = 3;
			fireList.add(SingleFire);		
		}

	}
		
	protected AbstractView[] views; 
	
	public void update() {
		for (AbstractView view : views) {
			view.update(); 
		}
	}
	
}
