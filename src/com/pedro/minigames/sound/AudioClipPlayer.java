package com.pedro.minigames.sound;

import javafx.scene.media.AudioClip;

/**
 * An simple audio player using {@link javafx.scene.media.AudioClip}
 * 
 * @author Pedro9558
 *
 */
public class AudioClipPlayer extends SoundPlayer {

	private AudioClip[] audioMemory = new AudioClip[100000];

	/**
	 * Gets the audio player
	 * 
	 * @return A audio player
	 */
	public static AudioClipPlayer getPlayer() {
		if (PLAYER == null) {
			PLAYER = new AudioClipPlayer();
		}
		return (AudioClipPlayer) PLAYER;
	}

	private AudioClipPlayer() {
		this.setEnabled(true);
	}

	@Override
	public void playSound(String sound, boolean loop, boolean async, double volume) {
		this.playSound(sound, loop, async, volume, 0.0, 1.0);
	}

	/**
	 * Same as
	 * <code>playSound(String sound, boolean loop, boolean async, double volume)</code>
	 * except with special effects, like balance and rate
	 * 
	 * @param sound   - The sound file name or the URL the file is in
	 * @param loop    - If the audio must be playign continously
	 * @param async   - If the audio can overlap any existing playing audio
	 * @param volume  - The volume of the audio [0.0(muted) - 1.0(full)]
	 * @param balance - The balance of the audio (-1.0 audio only on left side, 0.0
	 *                normal, 1.0 audio only on the right side)
	 * @param rate    - Audio speed rate from 0.125(slowest) to 8.0(fastest) -
	 *                default value: 1.0
	 */
	public void playSound(String sound, boolean loop, boolean async, double volume, double balance, double rate) {
		if (this.isEnabled()) {
			try {
				AudioClip temp = new AudioClip(this.getClass().getResource(sound).toString());
				System.out.println(this.getClass().getResource(sound).getPath());
				temp.setVolume(volume);
				if (!async && anyAudioPlaying()) {
					this.stopAllSounds();
					this.setMusicPlaying(sound);
				}
				if (this.getMusicPlaying() == null) {
					this.setMusicPlaying(sound);
				}
				if (loop) {
					temp.setCycleCount(AudioClip.INDEFINITE);
				}
				temp.setBalance(balance);
				temp.setRate(rate);
				this.addAudioToMemory(temp);
				temp.play();
				//System.out.println("Playing: " + this.getEnhancedMusicName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void addAudioToMemory(AudioClip audio) {
		for (int i = 0; i < audioMemory.length; i++) {
			if (audioMemory[i] == null || !audioMemory[i].isPlaying()) {
				audioMemory[i] = audio;
				break;
			}
		}
	}

	private String enhanceMusicName(String URL) {
		String[] split = URL.split("\\.");
		if (split.length == 0) {
			return URL;
		}
		return split[0];
	}
	
	public String getEnhancedMusicName() {
		return enhanceMusicName(this.getMusicPlaying());
	}

	private boolean anyAudioPlaying() {
		for (AudioClip a : audioMemory) {
			if (a != null && a.isPlaying()) {
				return true;
			}
			if (a == null)
				break;
		}
		return false;
	}

	@Override
	public void stopSound(String sound) {
		for (int i = 0; i < audioMemory.length; i++) {
			if (audioMemory[i] == null) break;
			if (audioMemory[i].getSource().replace("%20", " ").contains(sound)) {
				if (audioMemory[i].isPlaying()) {
					//System.out.println("Stopping: " + this.getEnhancedMusicName());
					audioMemory[i].stop();
				}
			}
		}

	}

	@Override
	public void stopAllSounds() {
		for (int i = 0; i < audioMemory.length; i++) {
			if (audioMemory[i] == null)
				break;
			if (audioMemory[i].isPlaying()) {
				audioMemory[i].stop();
			}
			audioMemory[i] = null;
		}

	}

	@Override
	public void pauseSound(String sound) {
		this.stopSound(sound);
	}

	@Override
	public boolean isPlaying(String sound) {
		for (int i = 0; i < audioMemory.length; i++) {
			if (audioMemory[i] == null)
				break;
			if (audioMemory[i].getSource().replace("%20", " ").contains(sound)) {
				if (audioMemory[i].isPlaying()) {
					return true;
				}
			}
		}
		return false;
	}

}
