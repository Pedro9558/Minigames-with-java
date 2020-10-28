package com.pedro.minigames.screens;

/**
 * A manager to manage all screen instances
 * @author Pedro9558
 *
 */
public class ScreenManager {
	private MainScreen mainScreen;

	private TicTacToeSelectorScreen ticSelectorScreen;
	private TicTacToeScreen ticScreen;
	
	private HangmanSelectorScreen hangmanScreen;
	private HangmanScreen hangmanGameScreen;
	
	private static ScreenManager MANAGER;
	
	public static ScreenManager getManager() {
		if (MANAGER == null)
			MANAGER = new ScreenManager();
		return MANAGER;
	}
	
	private ScreenManager() {
		mainScreen = new MainScreen();
		ticSelectorScreen = new TicTacToeSelectorScreen();
		ticScreen = new TicTacToeScreen();
		hangmanScreen = new HangmanSelectorScreen();
		hangmanGameScreen = new HangmanScreen();
	}
	public MainScreen getMainScreen() {
		return mainScreen;
	}

	public TicTacToeSelectorScreen getTicSelectorScreen() {
		return ticSelectorScreen;
	}

	public TicTacToeScreen getTicScreen() {
		return ticScreen;
	}

	public HangmanSelectorScreen getHangmanSelectorScreen() {
		return hangmanScreen;
	}

	public HangmanScreen getHangmanGameScreen() {
		return hangmanGameScreen;
	}
}
