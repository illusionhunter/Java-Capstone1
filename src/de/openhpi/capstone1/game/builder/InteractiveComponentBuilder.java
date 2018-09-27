package de.openhpi.capstone1.game.builder;

import processing.core.PApplet;
import de.openhpi.capstone1.game.starter.TheApp;


public class InteractiveComponentBuilder {
	
	public static InteractiveComponent create(PApplet applet, String type) {
		
		InteractiveComponent component = null;
		TheApp app = (TheApp)applet;
		
		switch (type) {
		case "Enemy":
			component = new Enemy(app.getBombList());
			Enemy e = (Enemy)component;
			e.setShip(app.getShip());
			break;
		case "Ship":
			component = new Ship(app.getFireList());
			component.positionX = 200;
			component.positionY = 360;
			break;


		}

		return component;
	}
	
	public static InteractiveComponent create(String type) {
		
		InteractiveComponent component = null;
		
		switch (type) {
		case "Fire":
			component = new Fire();
			break;
		case "Bomb":
			component = new Bomb();
			break;

		}

		return component;
	}
}
