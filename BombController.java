package de.openhpi.capstone1.game.controller;

import java.util.ArrayList;

import de.openhpi.capstone1.game.builder.Bomb;
import de.openhpi.capstone1.game.constants.Event;

public class BombController implements Controller {

	private ArrayList<Bomb> bombList;

	public BombController(ArrayList<Bomb> bombList) {
		this.bombList = bombList;
	}

	public void handleEvent(Event event) {
		ArrayList<Bomb> deadList = new ArrayList<Bomb>();

		for (Bomb b : bombList) {
			if (!b.isAlive()) {
				deadList.add(b);
			}
			b.handleEvent(event);
		}
		bombList.removeAll(deadList);
	}

	@Override
	public void handleEvent(Event event, Object component) {
		Bomb bomb = (Bomb) component;
		bomb.handleEvent(event);
	}
}
