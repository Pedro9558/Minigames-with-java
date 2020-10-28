package com.pedro.minigames.player;

import java.awt.Color;

import javax.swing.ImageIcon;

/**
 * The Hangman
 * @author Pedro9558
 *
 */
public class Hangman {
	private ImageIcon headIcon;
	private Color shirtColor;
	private Color pantsColor;
	private Color skinColor;
	
	public Hangman(ImageIcon head, Color shirtColor, Color pantsColor, Color skinColor) {
		this.setHeadIcon(head);
		this.setPantsColor(pantsColor);
		this.setShirtColor(shirtColor);
		this.setSkinColor(skinColor);
	}

	public ImageIcon getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(ImageIcon headIcon) {
		this.headIcon = headIcon;
	}

	public Color getShirtColor() {
		return shirtColor;
	}

	public void setShirtColor(Color shirtColor) {
		this.shirtColor = shirtColor;
	}

	public Color getPantsColor() {
		return pantsColor;
	}

	public void setPantsColor(Color pantsColor) {
		this.pantsColor = pantsColor;
	}

	public Color getSkinColor() {
		return skinColor;
	}

	public void setSkinColor(Color skinColor) {
		this.skinColor = skinColor;
	}
}
