package de.openhpi.capstone1.game.controller;


import de.openhpi.capstone1.game.builder.Ship;
import de.openhpi.capstone1.game.constants.Event;

public class ShipController implements Controller {
	
	private Ship ship;
	
	public ShipController(Ship Ship) {
		this.ship = Ship;
	}
	
	public void handleEvent(Event event) {
			ship.handleEvent(event);
	}

	@Override
	public void handleEvent(Event event, Object component) {
		if (event == Event.MOUSEMOVE) {
			ship.setPositionX((int) component);
		}
	}
}
