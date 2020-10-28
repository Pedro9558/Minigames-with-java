package com.pedro.minigames.sound;

/**
 * 
 * @author Pedro9558
 *
 */
public abstract class SoundPlayer {
	protected static SoundPlayer PLAYER;
	
	private boolean enabled;
	
	private String musicPlaying;
	
	/**
	 * Play a sound given a URL or sound file name
	 * or resume a sound that was paused
	 * @param sound - The sound file name or the URL the file is in
	 * @param loop - If the audio must be playign continously
	 * @param async - If the audio can overlap any existing playing audio
	 * @param volume - The volume of the audio [0.0(muted) - 1.0(full)]
	 */
	public abstract void playSound(String sound, boolean loop, boolean async, double volume);
	
	/**
	 * Stop a sound that is currently playing, it must be a sound that already went to playSound()
	 * @param sound - The sound file name or the URL the file is in
	 */
	public abstract void stopSound(String sound);
	
	/**
	 * Stop and dispose of all playing sounds
	 */
	public abstract void stopAllSounds();
	
	/**
	 * Pause a sound that was playing
	 * @param sound
	 */
	public abstract void pauseSound(String sound);
	
	public abstract boolean isPlaying(String sound);
	
	public String getMusicPlaying() {
		return musicPlaying;
	}
	public void setMusicPlaying(String musicPlaying) {
		this.musicPlaying = musicPlaying;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
