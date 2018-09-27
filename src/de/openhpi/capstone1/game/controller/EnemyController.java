package de.openhpi.capstone1.game.controller;

import java.util.ArrayList;

import de.openhpi.capstone1.game.builder.Enemy;
import de.openhpi.capstone1.game.constants.Event;

public class EnemyController implements Controller {

	private ArrayList<Enemy> enemyList;

	public EnemyController(ArrayList<Enemy> EnemyList) {
		this.enemyList = EnemyList;
	}

	public void handleEvent(Event event) {

		ArrayList<Enemy> deadList = new ArrayList<Enemy>();

		for (Enemy e : enemyList) {
			if (!e.isAlive()) {
				deadList.add(e);
			}
			e.handleEvent(event);
		}
		enemyList.removeAll(deadList);

	}

	public void handleEvent(Event event, Object component) {
		Enemy enemy = (Enemy) component;
		enemy.handleEvent(event);

		// if any enemy got hit, the whole army will speed up

		if (event == Event.FIREHIT) {
			for (Enemy e : enemyList) {
				e.handleEvent(Event.SPEEDUP);
			}
		}
	}
}
