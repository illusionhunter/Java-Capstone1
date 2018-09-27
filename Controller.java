package de.openhpi.capstone1.game.controller;

import de.openhpi.capstone1.game.constants.Event;

public interface Controller {
	void handleEvent(Event event, Object component);
	void handleEvent(Event event);
}
