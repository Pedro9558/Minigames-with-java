package com.pedro.minigames.games;

import java.util.Random;

import com.pedro.minigames.player.Player;

public class HangmanGame {
	private Player player;
	private Themes currentTheme;
	private String currentWord;
	private String currentFormation;
	
	private boolean gameOver;
	private boolean victory;
	
	private char[] guessedLetters = new char[26];
	private int pointer = 0;
	
	private HangmanPhase hangmanPhase = HangmanPhase.NOTHING;
	
	public HangmanGame(Player player, Themes theme) {
		this.player = player;
		this.currentTheme = theme;
		this.currentWord = theme.getRandomWord().toUpperCase();
		this.currentFormation = "";
		for(char c: currentWord.toCharArray()) {
			if(c == ' ') {
				this.currentFormation += " ";
			}else {
				this.currentFormation += "_";
			}
		}
	}
	
	public String getCurrentFormation() {
		return this.currentFormation;
	}
	
	public boolean makeGuess(char guess) {
		boolean correctGuess = false;
		guessedLetters[pointer] = guess;
		for(int i = 0; i < currentWord.length(); i++) {
			if(currentWord.charAt(i) == guess) {
				this.currentFormation = currentFormation.substring(0, i) + guess + currentFormation.substring(i + 1);
				correctGuess = true;
			}
		}
		if(!correctGuess) {
			switch(hangmanPhase) {
			case NOTHING:
				hangmanPhase = HangmanPhase.HEAD;
				break;
			case HEAD:
				hangmanPhase = HangmanPhase.BODY;
				break;
			case BODY:
				hangmanPhase = HangmanPhase.LEFT_ARM;
				break;
			case LEFT_ARM:
				hangmanPhase = HangmanPhase.RIGHT_ARM;
				break;
			case RIGHT_ARM:
				hangmanPhase = HangmanPhase.LEFT_LEG;
				break;
			case LEFT_LEG:
				hangmanPhase = HangmanPhase.RIGHT_LEG_FULL;
				this.currentFormation = this.currentWord.toUpperCase();
			case RIGHT_LEG_FULL:
				break;
			default:
				break;
			
			}
		}
		this.checkGame();
		return correctGuess;
	}
	
	private void checkGame() {
		this.gameOver = true;
		for(char c : currentFormation.toCharArray()) {
			if(c == '_') {
				this.gameOver = false;
			}
		}
		if(isGameOver()) {
			this.victory = !(this.getHangmanPhase() == HangmanPhase.RIGHT_LEG_FULL);
		}
	}
	
	
	public Player getPlayer() {
		return player;
	}
	

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Themes getCurrentTheme() {
		return currentTheme;
	}

	public void setCurrentTheme(Themes currentTheme) {
		this.currentTheme = currentTheme;
	}

	public String getCurrentWord() {
		return currentWord;
	}

	public HangmanPhase getHangmanPhase() {
		return hangmanPhase;
	}







	public boolean isGameOver() {
		return gameOver;
	}







	public boolean isVictory() {
		return victory;
	}







	public enum Themes {
		FOOD(new String[] {"Banana","Apple","Rice","Beans","Grapes","Cheeseburger","Nuggets","Meatball","Steak","Fries","Yogurt","Cheese","Cake","Pie","Bread","Noodles","Fish","Salad","Pasta","Pancakes","Potato","Onion","Cabbage","Carrot","Tuna","Shrimp","Mutton","Liver","Sausage"}), 
		PERSON(new String[] {"Bill Gates","Steve Jobs","Xuxa","Barack Obama","Tyler James","Terry Crews","PSY","Kanye West","Nicki Minaj","Kim Kardashian","Kourtney Kardashian","Nicolas Cage","Steve Carell","Tom Holland","","Andrew Garfield","Jenna Fischer","Donald Trump","Daniel Radcliffe","Emma Watson","Rupert Grint","Tom Felton","Will Smith","Michelle Obama","Joe Biden","Jair Bolsonaro","Carlos Bolsonaro","Lula","Dilma Rousseff","Fernando Haddad","Avril Lavigne","Anitta","Fausto Silva","Vladimir Putin"}),
		ANIMAL(new String[] {"Dog","Cat","Crab","Hamster","Lion","Tiger","Ladybug","Whale","Dolphin","Monkey","Giraffe","Rabbit","Cow","Chicken","Pig","Butterfly","Cricket","Cockroach","Grasshopper","Moth","Elephant","Llama","Cheetah","Ocelot","Shark","Horse","Deer","Wolf","Ant","Groundhog"});
		private String[] words;
		Themes(String[] words) {
			this.words = words;
		}
		public String[] getWords() {
			return this.words;
		}
		public String getRandomWord() {
			int i = new Random().nextInt(words.length);
			return this.words[i];
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < this.name().length(); i++) {
				if(i == 0) {
					builder.append(("" + this.name().charAt(0)).toUpperCase());
				}else {
					builder.append(("" + this.name().charAt(i)).toLowerCase());
				}
			}
			return builder.toString();
		}
	}
	public enum HangmanPhase {
		NOTHING, HEAD, BODY, LEFT_ARM, RIGHT_ARM, LEFT_LEG, RIGHT_LEG_FULL;
	}
}
