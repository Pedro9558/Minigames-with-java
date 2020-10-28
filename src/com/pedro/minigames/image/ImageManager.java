package com.pedro.minigames.image;

import javax.swing.ImageIcon;

public class ImageManager {
	private static ImageManager MANAGER;
	
	public static ImageManager getManager() {
		if(MANAGER == null)
			MANAGER = new ImageManager();
		return MANAGER;
	}
	
	public ImageIcon getImageIcon(String image) {
		// System.out.println(this.getClass().getResource(image).getPath());
		return new ImageIcon(this.getClass().getResource(image));
	}
	
	private ImageManager() {}
}
