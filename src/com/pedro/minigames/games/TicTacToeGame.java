package com.pedro.minigames.games;

import com.pedro.minigames.player.TicTacToePlayer;

public class TicTacToeGame {
	private CellType[][] field = new CellType[][] {{CellType.EMPTY, CellType.EMPTY, CellType.EMPTY},
		{CellType.EMPTY, CellType.EMPTY, CellType.EMPTY},
		{CellType.EMPTY, CellType.EMPTY, CellType.EMPTY}};
	private TicTacToePlayer player1;
	private TicTacToePlayer player2;
	
	private GameSituation situation;
	
	private TicTacToePlayer winner;
	
	public TicTacToeGame(TicTacToePlayer p1, TicTacToePlayer p2) {
		this.player1 = p1;
		this.player2 = p2;
	}
	
	public boolean doMoviment(TicTacToePlayer player, int[] position) {
		if(checkEmpty(position) && situation == GameSituation.IN_GAME) {
			field[position[0]][position[1]] = player.getCellType();
			this.checkGame();
			return true;
		}
		return false;
	}
	public void startGame() {
		this.situation = GameSituation.IN_GAME;
	}
	public void forceFinishGame() {
		this.situation = GameSituation.FINISHED;
	}
	private void checkGame() {
		// Check for victory
		
		// Store player relatives
		TicTacToePlayer player_O;
		TicTacToePlayer player_X;
		
		if(player1.getCellType() == CellType.WITH_O) {
			player_O = player1;
			player_X = player2;
		}else {
			player_O = player2;
			player_X = player1;
		}
		
		// Check the field lines
		for(int i = 0; i < 3; i++) {
			if((field[i][0] == CellType.WITH_O) && (field[i][1] == CellType.WITH_O) && (field[i][2] == CellType.WITH_O)) {
				winner = player_O;				
			} else if((field[i][0] == CellType.WITH_X) && (field[i][1] == CellType.WITH_X) && (field[i][2] == CellType.WITH_X)) {
				winner = player_X;
			}
		}
		
		// Check the field columns
		for(int i = 0; i < 3; i++) {
			if((field[0][i] == CellType.WITH_O) && (field[1][i] == CellType.WITH_O) && (field[2][i] == CellType.WITH_O)) {
				winner = player_O;				
			} else if((field[0][i] == CellType.WITH_X) && (field[1][i] == CellType.WITH_X) && (field[2][i] == CellType.WITH_X)) {
				winner = player_X;
			}
		}
		
		// Check diagonals
		// Main diagonal
		if((field[0][0] == CellType.WITH_O) && (field[1][1] == CellType.WITH_O) && (field[2][2] == CellType.WITH_O)) {
			winner = player_O;				
		} else if((field[0][0] == CellType.WITH_X) && (field[1][1] == CellType.WITH_X) && (field[2][2] == CellType.WITH_X)) {
			winner = player_X;
		}
		// Secondary diagonal
		if((field[0][2] == CellType.WITH_O) && (field[1][1] == CellType.WITH_O) && (field[2][0] == CellType.WITH_O)) {
			winner = player_O;				
		} else if((field[0][2] == CellType.WITH_X) && (field[1][1] == CellType.WITH_X) && (field[2][0] == CellType.WITH_X)) {
			winner = player_X;
		}
		
		// If there is a winner or there is no more space left, game over
		if(winner != null || !hasSpaceLeft()) {
			situation = GameSituation.FINISHED;
		}
	}
	
	public boolean checkEmpty(int[] position) {
		return field[position[0]][position[1]] == CellType.EMPTY;
	}
	private boolean hasSpaceLeft() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(field[i][j] == CellType.EMPTY) {
					return true;
				}
			}
		}
		return false;
	}
	public TicTacToePlayer getWinner() {
		return winner;
	}

	public GameSituation getSituation() {
		return situation;
	}


	public TicTacToePlayer getPlayer1() {
		return player1;
	}

	public void setPlayer1(TicTacToePlayer player1) {
		this.player1 = player1;
	}

	public TicTacToePlayer getPlayer2() {
		return player2;
	}

	public void setPlayer2(TicTacToePlayer player2) {
		this.player2 = player2;
	}


	public CellType[][] getField() {
		return field;
	}


	public enum CellType {
		EMPTY(0), WITH_O(1), WITH_X(2);
		private int weight;
		CellType(int i) {
			this.weight = i;
			
		}
		public int getWeight() {
			return this.weight;
		}
		
		
	}
	public enum GameSituation {
		WAITING, IN_GAME, FINISHED
	}
}

