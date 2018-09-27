package de.openhpi.capstone1.game.controller;

import java.util.ArrayList;

import de.openhpi.capstone1.game.builder.Fire;
import de.openhpi.capstone1.game.constants.Event;

public class FireController implements Controller {

	private ArrayList<Fire> fireList;

	public FireController(ArrayList<Fire> fireList) {
		this.fireList = fireList;
	}

	public void handleEvent(Event event) {
		ArrayList<Fire> deadList = new ArrayList<Fire>();
		for (Fire f : fireList) {
			if (!f.isAlive()) {
				deadList.add(f);
			}
			f.handleEvent(event);
		}
		fireList.removeAll(deadList);
	}

	@Override
	public void handleEvent(Event event, Object component) {
		Fire fire = (Fire) component;
		fire.handleEvent(event);
	}
}
