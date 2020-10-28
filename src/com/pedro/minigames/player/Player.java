package com.pedro.minigames.player;

/**
 * A simple player instance
 * @author Pedro9558
 *
 */
public class Player {
	private String name;
	private boolean npc;
	public Player(String name, boolean npc) {
		this.setName(name);
		this.npc = npc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isNpc() {
		return npc;
	}
}
