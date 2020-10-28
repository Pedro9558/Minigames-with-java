package com.pedro.minigames.player;

import com.pedro.minigames.games.TicTacToeGame;

/**
 * Special TicTacToePlayer with more attributes
 * @author Pedro9558
 *
 */
public class TicTacToePlayer extends Player {
	private TicTacToeGame.CellType cellType;
	private AILevel aiLevel;
	
	
	public TicTacToePlayer(String name, boolean npc, TicTacToeGame.CellType cellType) {
		super(name, npc);
		this.cellType = cellType;
		if(npc)
			this.setAiLevel(AILevel.EASY);
	}
	public TicTacToePlayer(String name, boolean npc, TicTacToeGame.CellType cellType, AILevel aiLevel) {
		super(name, npc);
		this.cellType = cellType;
		this.setAiLevel(aiLevel);
	}

	public TicTacToeGame.CellType getCellType() {
		return cellType;
	}

	public void setCellType(TicTacToeGame.CellType cellType) {
		this.cellType = cellType;
	}
	
	public AILevel getAiLevel() {
		return aiLevel;
	}
	public void setAiLevel(AILevel aiLevel) {
		this.aiLevel = aiLevel;
	}

	public enum AILevel {
		EASY(100.0f, 0.0f), MEDIUM(70.0f, 30.0f), HARD(40.0f, 60.0f), IMPOSSIBLE(0.0f, 100.0f);
		
		private float randomPlayChance;
		private float strategyPlayChance;
		AILevel(float randomPlayChance, float strategyPlayChance) {
			this.randomPlayChance = randomPlayChance;
			this.strategyPlayChance = strategyPlayChance;
		}
		public float getRandomPlayChance() {
			return randomPlayChance;
		}
		public float getStrategyPlayChance() {
			return strategyPlayChance;
		}
	}

}
