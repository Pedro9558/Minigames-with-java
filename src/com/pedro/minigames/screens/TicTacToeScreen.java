package com.pedro.minigames.screens;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.pedro.minigames.games.TicTacToeGame;
import com.pedro.minigames.games.TicTacToeGame.CellType;
import com.pedro.minigames.games.TicTacToeGame.GameSituation;
import com.pedro.minigames.player.TicTacToePlayer;
import com.pedro.minigames.player.TicTacToePlayer.AILevel;

/**
 * The screen of the game Tic Tac Toe using Swing library
 *
 * @author Pedro9558
 *
 */
public class TicTacToeScreen extends JFrame implements WindowListener, MouseListener {

	/**
	 * 
	 */

	private TicTacToeGame oldInstance;
	private TicTacToeGame game;

	private TicTacToePlayer turn;

	private TicTacToePlayer p1;
	private TicTacToePlayer p2;

	private boolean started;

	private static final long serialVersionUID = 3L;

	private JLabel title = new JLabel("Tic Tac Toe");

	private JLabel[][] cells = new JLabel[3][3];

	private GameWorker gWorker;

	private boolean waitNextMove;

	private JButton leaveGame = new JButton("Leave game");
	private JButton restartGame = new JButton("Restart");

	private String[] states = { "Starting game...", "%player%'s turn!", "%player% is thinking...", "Game over!" };
	private JLabel gameStateLabel = new JLabel(states[0]);
	private String[] situations = { "", "Victory to %player%!", "Tie!" };
	private JLabel situationLabel = new JLabel(situations[0]);

	public TicTacToeScreen() {
		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setResizable(false);
		this.addWindowListener(this);
		this.setLocationRelativeTo(null);
		this.setTitle("Tic Tac Toe");
		this.getContentPane().setBackground(new Color(255, 255, 190));
		this.getContentPane().setLayout(null);

		Font f = new Font("Arial", Font.BOLD, 56);
		title.setFont(f);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(100, 0, 600, 60);
		this.getContentPane().add(title);

		this.getContentPane().add(gameStateLabel);
		gameStateLabel.setFont(f.deriveFont(30.0f));
		gameStateLabel.setBounds(10, 530, 300, 40);

		this.getContentPane().add(situationLabel);
		situationLabel.setFont(f.deriveFont(30.0f));
		situationLabel.setForeground(new Color(0, 100, 0));
		situationLabel.setBounds(500, 530, 300, 40);

		this.getContentPane().add(restartGame);
		restartGame.setFont(f.deriveFont(30.0f));
		restartGame.setForeground(new Color(0, 100, 0));
		restartGame.setBackground(Color.LIGHT_GRAY);
		restartGame.setBorder(null);
		restartGame.setEnabled(false);
		restartGame.setBounds(280, 530, 200, 40);
		restartGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to restart the game") == JOptionPane.YES_OPTION) {
					restartGame();
					restartGame.setEnabled(false);
					restartGame.setBackground(Color.LIGHT_GRAY);
				}

			}
		});

		this.getContentPane().add(leaveGame);
		leaveGame.setFont(f.deriveFont(30.0f));
		leaveGame.setForeground(new Color(100, 0, 0));
		leaveGame.setBackground(new Color(200, 0, 0));
		leaveGame.setBorder(null);
		leaveGame.setBounds(20, 20, 200, 40);
		leaveGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to leave the game?") == JOptionPane.YES_OPTION) {
					finishGame();
					ScreenManager.getManager().getTicScreen().setVisible(false);
					ScreenManager.getManager().getMainScreen().setVisible(true);
				}
			}

		});

		gWorker = new GameWorker();

		this.buildField(this.getContentPane());
		new Thread(gWorker).start();
		new Thread(new ColorSwitchAnimation(title, Color.CYAN, Color.GREEN, 4L)).start();
		new Thread(new BlinkAnimation(situationLabel, this.getContentPane().getBackground(), 500L)).start();
	}

	private void restartGame() {
		this.finishGame();
		oldInstance = game;
		game = new TicTacToeGame(p1, p2);
		this.startGame();
	}

	private void buildField(Container c) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				cells[i][j] = new JLabel();
				cells[i][j].setOpaque(true);
				cells[i][j].setBackground(new Color(255, 255, 190));
				c.add(cells[i][j]);
				cells[i][j].setFont(new Font("Open Sans", Font.BOLD, 92));
				cells[i][j].setForeground(Color.BLACK);
				cells[i][j].addMouseListener(this);
			}
		}
		cells[0][0].setBounds(120, 60, 100, 100);
		cells[0][1].setBounds(320, 60, 100, 100);
		cells[0][2].setBounds(520, 60, 100, 100);

		cells[1][0].setBounds(120, 220, 100, 100);
		cells[1][1].setBounds(320, 220, 100, 100);
		cells[1][2].setBounds(520, 220, 100, 100);

		cells[2][0].setBounds(120, 400, 100, 100);
		cells[2][1].setBounds(320, 400, 100, 100);
		cells[2][2].setBounds(520, 400, 100, 100);

	}

	public void startGame() {
		if (getGame() != null && !started) {
			started = true;
			p1 = getGame().getPlayer1();
			p2 = getGame().getPlayer2();
			getGame().startGame();
		}
	}

	public void finishGame() {
		if (getGame() != null && started) {
			started = false;
			getGame().forceFinishGame();
			game = null;
			turn = null;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					cells[i][j].setText("");
					cells[i][j].setBackground(new Color(255, 255, 190));
					cells[i][j].setForeground(Color.BLACK);
				}
			}
			gameStateLabel.setText(states[0]);
			situationLabel.setText(situations[0]);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);

		g2.fillRect(100, 200, 600, 10);
		g2.fillRect(100, 400, 600, 10);

		g2.fillRect(270, 80, 10, 470);
		g2.fillRect(470, 80, 10, 470);
	}

	public TicTacToeGame getGame() {
		return game;
	}

	public CellType[][] getField() {
		TicTacToeGame g = getGame();
		return g == null ? null : g.getField();
	}

	public void setGame(TicTacToeGame game) {
		if (this.game == null) {
			this.oldInstance = game;
		} else {
			this.oldInstance = this.game;
		}
		this.game = game;

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {
		finishGame();
		ScreenManager.getManager().getTicScreen().setVisible(false);
		ScreenManager.getManager().getMainScreen().setVisible(true);
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public TicTacToePlayer getTurn() {
		return turn;
	}

	public void setTurn(TicTacToePlayer turn) {
		this.turn = turn;
	}

	class ColorSwitchAnimation implements Runnable {
		private long animationDelay = 10L;
		private JComponent component;

		private Color from;
		private Color to;
		private Color current;

		public ColorSwitchAnimation(JComponent component, Color from, Color to) {
			this(component, from, to, 10L);
		}

		public ColorSwitchAnimation(JComponent component, Color from, Color to, long animationDelay) {
			this.component = component;
			component.setForeground(from);
			this.setFrom(from);
			this.setTo(to);
			this.setAnimationDelay(animationDelay);
		}

		@Override
		public void run() {
			while (true) {
				current = from;
				while (current.getRGB() < to.getRGB()) {
					try {
						Thread.sleep(animationDelay);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					current = new Color(current.getRGB() + 1);
					component.setForeground(current);
				}
				while (current.getRGB() > to.getRGB()) {
					try {
						Thread.sleep(animationDelay);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					current = new Color(current.getRGB() - 1);
					component.setForeground(current);
				}
				to = from;
				from = current;
			}
		}

		public JComponent getComponent() {
			return component;
		}

		public long getAnimationDelay() {
			return animationDelay;
		}

		public void setAnimationDelay(long animationDelay) {
			this.animationDelay = animationDelay > 0 ? animationDelay : 10L;
		}

		public Color getFrom() {
			return from;
		}

		public void setFrom(Color from) {
			this.from = from;
		}

		public Color getTo() {
			return to;
		}

		public void setTo(Color to) {
			this.to = to;
		}
	}

	class BlinkAnimation implements Runnable {
		private long animationDelay = 10L;
		private JComponent component;

		private Color backgroundColor;
		private Color originalColor;

		public BlinkAnimation(JComponent component, Color backgroundColor) {
			this(component, backgroundColor, 10L);
		}

		public BlinkAnimation(JComponent component, Color backgroundColor, long animationDelay) {
			this.setAnimationDelay(animationDelay);
			this.setComponent(component);
			this.setBackgroundColor(backgroundColor);
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(animationDelay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (component.getForeground() == originalColor) {
					component.setForeground(backgroundColor);
				} else {
					component.setForeground(originalColor);
				}
			}
		}

		public long getAnimationDelay() {
			return animationDelay;
		}

		public void setAnimationDelay(long animationDelay) {
			this.animationDelay = animationDelay > 0 ? animationDelay : 10L;
		}

		public JComponent getComponent() {
			return component;
		}

		public void setComponent(JComponent component) {
			this.component = component;
			this.originalColor = component.getForeground();
		}

		public Color getBackgroundColor() {
			return backgroundColor;
		}

		public void setBackgroundColor(Color backgroundColor) {
			this.backgroundColor = backgroundColor;
		}

		public Color getOriginalColor() {
			return originalColor;
		}

		public void setOriginalColor(Color originalColor) {
			this.originalColor = originalColor;
		}
	}
	/**
	 * The worker responsible for the whole game processing, <br>
	 * like who's turn next to play, if the game is finished <br>
	 * and it controls the AIs, if there's any in the game
	 * @author Pedro9558
	 *
	 */
	class GameWorker implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
				}
				// Check game
				if (game != null) {
					if (game.getSituation() == GameSituation.FINISHED) {
						gameStateLabel.setText(states[3]);
						if (game.getWinner() != null) {
							this.highlightWin();
							situationLabel.setText(situations[1].replace("%player%", game.getWinner().getName()));
						} else {
							situationLabel.setText(situations[2]);
						}
						restartGame.setEnabled(true);
						restartGame.setBackground(new Color(0, 200, 0));
					} else if (game.getSituation() == GameSituation.IN_GAME) {
						if (turn == null || turn == game.getPlayer2()) {
							turn = game.getPlayer1();
						} else {
							turn = game.getPlayer2();
						}
						gameStateLabel.setText(states[1].replace("%player%", turn.getName()));
						waitNextMove = true;
						try {
							Thread.sleep(2000);
						} catch (Exception e) {
						}
						gameStateLabel.setText(states[2].replace("%player%", turn.getName()));
						while (waitNextMove) {
							try {
								Thread.sleep(1);
							} catch (Exception e) {
							}
							try {
								if (turn.isNpc()) {
									try {
										Thread.sleep(2000);
									} catch (Exception e) {
									}
									AILevel level = turn.getAiLevel();
									Random r = new Random();
									double move = r.nextDouble() * 101;
									int[] moviment;
									if (move <= level.getRandomPlayChance()) {
										moviment = getRandomMove();
									} else {
										moviment = getStrategyMove(turn);
									}
									game.doMoviment(turn, moviment);
									waitNextMove = false;
								}
							} catch (Exception e) {
								waitNextMove = false;
							}
						}
						gameStateLabel.setText("");
						updateScreen();
					}
				}
			}

		}
		/**
		 * Update the screen
		 */
		private void updateScreen() {
			CellType[][] f = getField();
			if (f != null) {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (f[i][j] == CellType.WITH_O) {
							cells[i][j].setText("O");
						} else if (f[i][j] == CellType.WITH_X) {
							cells[i][j].setText("X");
						} else {
							cells[i][j].setText("");
						}
					}
				}
			}
		}
		/**
		 * Highlight the winner section
		 */
		private void highlightWin() {
			CellType[][] f = getField();
			for (int i = 0; i < 3; i++) {
				// Lines
				if ((f[i][0] == f[i][1]) && (f[i][1] == f[i][2]) && (f[i][0] != CellType.EMPTY)) {
					for (int j = 0; j < 3; j++) {
						cells[i][j].setForeground(Color.BLUE);
					}
				}
				// Columns
				if ((f[0][i] == f[1][i]) && (f[1][i] == f[2][i]) && (f[0][i] != CellType.EMPTY)) {
					for (int j = 0; j < 3; j++) {
						cells[j][i].setForeground(Color.BLUE);
					}
				}
			}
			// Main diagonal
			if ((f[0][0] == f[1][1]) && (f[1][1] == f[2][2]) && (f[0][0] != CellType.EMPTY)) {
				for (int j = 0; j < 3; j++) {
					cells[j][j].setForeground(Color.BLUE);
				}
			}
			// Secondary diagonal
			if ((f[0][2] == f[1][1]) && (f[1][1] == f[2][0]) && (f[0][2] != CellType.EMPTY)) {
				for (int i = 0, j = 2; i < 3; i++) {
					cells[i][j].setForeground(Color.BLUE);
					j--;
				}
			}
		}
		/**
		 * Get random position based if the position is empty on the field
		 * @return - A position of a random empty field
		 */
		private int[] getRandomMove() {
			Random r = new Random();
			int[] choosen = new int[] { r.nextInt(3), r.nextInt(3) };
			CellType cellChoosen = getField()[choosen[0]][choosen[1]];
			int attempts = 0; // Avoid infinity loops
			while (cellChoosen != CellType.EMPTY || attempts < 100) {
				choosen = new int[] { r.nextInt(3), r.nextInt(3) };
				cellChoosen = getField()[choosen[0]][choosen[1]];
				attempts++;
			}
			return choosen;
		}
		/**
		 * Calculates and return the best move, based if the position is empty on the field
		 * @param player - The Player (MUST be an AI)
		 * @return A strategy position based on the AI calculation
		 */
		private int[] getStrategyMove(TicTacToePlayer player) {
			CellType[][] f = getField();
			CellType me = player.getCellType();
			CellType rival = (me == CellType.WITH_O) ? CellType.WITH_X : CellType.WITH_O;
			Random r = new Random();
			// Look for rival possible winning moves
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (j != 2) {
						// Line checking
						CellType v1 = f[i][j];
						CellType v2 = f[i][j + 1];
						if ((v1 == rival) && (v2 == rival)) {
							if (j == 0) {
								if (f[i][2] == CellType.EMPTY) {
									return new int[] { i, 2 };
								}
							} else {
								if (f[i][0] == CellType.EMPTY) {
									return new int[] { i, 0 };
								}
							}
						}
						// Column checking
						CellType v3 = f[j][i];
						CellType v4 = f[j + 1][i];
						if ((v3 == rival) && (v4 == rival)) {
							if (j == 0) {
								if (f[2][i] == CellType.EMPTY) {
									return new int[] { 2, i };
								}
							} else {
								if (f[0][i] == CellType.EMPTY) {
									return new int[] { 0, i };
								}
							}
						}
					}
				}
			}
			// Main Diagonal checking
			for (int i = 0; i < 3; i++) {
				if (i != 2) {
					CellType v1 = f[i][i];
					CellType v2 = f[i + 1][i + 1];
					if ((v1 == rival) && (v2 == rival)) {
						if (i == 0) {
							if (f[2][2] == CellType.EMPTY) {
								return new int[] { 2, 2 };
							}
						} else {
							if (f[0][0] == CellType.EMPTY) {
								return new int[] { 0, 0 };
							}
						}
					}
				}
			}
			// Secondary diagonal
			for (int i = 0, j = 2; i < 3; i++) {
				CellType v1 = f[i][j];
				if (j == 0)
					break;
				CellType v2 = f[i + 1][j - 1];
				//System.out.println("[" + i + "] " + "[" + j + "] = " + v1);
				//System.out.println("[" + (i + 1) + "] " + "[" + (j - 1) + "] = " + v2);
				if ((v1 == rival) && (v2 == rival)) {
					if (j == 1) {
						if (f[0][2] == CellType.EMPTY) {
							return new int[] { 0, 2 };
						}
					} else {
						if (f[2][0] == CellType.EMPTY) {
							return new int[] { 2, 0 };
						}
					}
				}
				j--;
			}
			// Corner checking
			CellType c1 = f[0][0];
			CellType c2 = f[0][2];
			CellType c3 = f[2][0];
			CellType c4 = f[2][2];
			if (c1 == rival) {
				if (c2 == rival) {
					if (f[0][1] == CellType.EMPTY) {
						return new int[] { 0, 1 };
					}
				} else if (c3 == rival) {
					if (f[1][0] == CellType.EMPTY) {
						return new int[] { 1, 0 };
					}
				} else if (c4 == rival) {
					if (f[1][1] == CellType.EMPTY) {
						return new int[] { 1, 1 };
					}
				}
			}
			if (c2 == rival) {
				if (c3 == rival) {
					if (f[1][1] == CellType.EMPTY) {
						return new int[] { 1, 1 };
					}
				} else if (c4 == rival) {
					if (f[1][2] == CellType.EMPTY) {
						return new int[] { 1, 2 };
					}
				}
			}
			if ((c3 == rival) && (c4 == rival)) {
				if (f[2][1] == CellType.EMPTY) {
					return new int[] { 2, 1 };
				}
			}
			// No blocking, find another strategy
			int piecesAmount = 0; // Count the AI pieces
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (f[i][j] == me) {
						piecesAmount++;
					}
				}
			}
			// If it is zero, it means it's the first AI move
			if (piecesAmount == 0) {
				// Aim for the middle
				if (f[1][1] == CellType.EMPTY) {
					return new int[] { 1, 1 };
				}
				// Otherwise, aim for a corner
				int corner = (int) (Math.random() * 4);

				CellType cCompare = null;

				switch (corner) {
				case 0:
					cCompare = c1;
					break;
				case 1:
					cCompare = c2;
					break;
				case 2:
					cCompare = c3;
					break;
				case 3:
					cCompare = c4;
					break;
				}
				while (cCompare != CellType.EMPTY) {
					corner = (int) (Math.random() * 4);
					switch (corner) {
					case 0:
						cCompare = c1;
						break;
					case 1:
						cCompare = c2;
						break;
					case 2:
						cCompare = c3;
						break;
					case 3:
						cCompare = c4;
						break;
					}
				}
				switch (corner) {
				case 0:
					return new int[] { 0, 0 };
				case 1:
					return new int[] { 0, 2 };
				case 2:
					return new int[] { 2, 0 };
				case 3:
					return new int[] { 2, 2 };
				}

			} else {
				// Check for possible wins
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (j != 2) {
							// Line checking
							CellType v1 = f[i][j];
							CellType v2 = f[i][j + 1];
							if ((v1 == me) && (v2 == me)) {
								if (j == 0) {
									if (f[i][2] == CellType.EMPTY) {
										return new int[] { i, 2 };
									}
								} else {
									if (f[i][0] == CellType.EMPTY) {
										return new int[] { i, 0 };
									}
								}
							}
							// Column checking
							CellType v3 = f[j][i];
							CellType v4 = f[j + 1][i];
							if ((v3 == me) && (v4 == me)) {
								if (j == 0) {
									if (f[2][i] == CellType.EMPTY) {
										return new int[] { 2, i };
									}
								} else {
									if (f[0][i] == CellType.EMPTY) {
										return new int[] { 0, i };
									}
								}
							}
						}
					}
				}

				// Main Diagonal checking
				for (int i = 0; i < 3; i++) {
					if (i != 2) {
						CellType v1 = f[i][i];
						CellType v2 = f[i + 1][i + 1];
						if ((v1 == me) && (v2 == me)) {
							if (i == 0) {
								if (f[2][2] == CellType.EMPTY) {
									return new int[] { 2, 2 };
								}
							} else {
								if (f[0][0] == CellType.EMPTY) {
									return new int[] { 0, 0 };
								}
							}
						}
					}
				}

				// Secondary diagonal
				for (int i = 0, j = 2; i < 3; i++) {
					CellType v1 = f[i][j];
					if (j == 0)
						break;
					CellType v2 = f[i + 1][j - 1];
					if ((v1 == me) && (v2 == me)) {
						if (j == 1) {
							if (f[0][2] == CellType.EMPTY) {
								return new int[] { 0, 2 };
							}
						} else {
							if (f[2][0] == CellType.EMPTY) {
								return new int[] { 2, 0 };
							}
						}
					}
					j--;
				}

				// Corner checking
				if (c1 == me) {
					if (c2 == me) {
						if (f[0][1] == CellType.EMPTY) {
							return new int[] { 0, 1 };
						}
					} else if (c3 == me) {
						if (f[1][0] == CellType.EMPTY) {
							return new int[] { 1, 0 };
						}
					} else if (c4 == me) {
						if (f[1][1] == CellType.EMPTY) {
							return new int[] { 1, 1 };
						}
					}
				}
				if (c2 == me) {
					if (c3 == me) {
						if (f[1][1] == CellType.EMPTY) {
							return new int[] { 1, 1 };
						}
					} else if (c4 == me) {
						if (f[1][2] == CellType.EMPTY) {
							return new int[] { 1, 2 };
						}
					}
				}
				if ((c3 == me) && (c4 == me)) {
					if (f[2][1] == CellType.EMPTY) {
						return new int[] { 2, 1 };
					}
				}

				// No chance of winning? check to see if there are other
				// pieces that needs a pair
				CellType ce1, ce2, ce3, ce4, ce5, ce6, ce7, ce8, ce9;
				ce1 = f[0][0];
				ce2 = f[0][1];
				ce3 = f[0][2];
				ce4 = f[1][0];
				ce5 = f[1][1];
				ce6 = f[1][2];
				ce7 = f[2][0];
				ce8 = f[2][1];
				ce9 = f[2][2];
				if (ce1 == me) {
					if (ce2 == CellType.EMPTY) {
						return new int[] { 0, 1 };
					} else if (ce4 == CellType.EMPTY) {
						return new int[] { 1, 0 };
					} else if (ce5 == CellType.EMPTY) {
						return new int[] { 1, 1 };
					}
				}
				if (ce2 == me) {
					if (ce1 == CellType.EMPTY) {
						return new int[] { 0, 0 };
					} else if (ce3 == CellType.EMPTY) {
						return new int[] { 0, 2 };
					} else if (ce5 == CellType.EMPTY) {
						return new int[] { 1, 1 };
					}
				}
				if (ce3 == me) {
					if (ce2 == CellType.EMPTY) {
						return new int[] { 0, 1 };
					} else if (ce5 == CellType.EMPTY) {
						return new int[] { 1, 1 };
					} else if (ce6 == CellType.EMPTY) {
						return new int[] { 1, 2 };
					}
				}
				if (ce4 == me) {
					if (ce1 == CellType.EMPTY) {
						return new int[] { 0, 0 };
					} else if (ce5 == CellType.EMPTY) {
						return new int[] { 1, 1 };
					} else if (ce7 == CellType.EMPTY) {
						return new int[] { 2, 0 };
					}
				}
				if (ce5 == me) {
					if (ce1 == CellType.EMPTY) {
						return new int[] { 0, 0 };
					} else if (ce2 == CellType.EMPTY) {
						return new int[] { 0, 1 };
					} else if (ce3 == CellType.EMPTY) {
						return new int[] { 0, 2 };
					} else if (ce4 == CellType.EMPTY) {
						return new int[] { 1, 0 };
					} else if (ce6 == CellType.EMPTY) {
						return new int[] { 1, 2 };
					} else if (ce7 == CellType.EMPTY) {
						return new int[] { 2, 0 };
					} else if (ce8 == CellType.EMPTY) {
						return new int[] { 2, 1 };
					} else if (ce9 == CellType.EMPTY) {
						return new int[] { 2, 2 };
					}
				}
				if (ce6 == me) {
					if (ce9 == CellType.EMPTY) {
						return new int[] { 2, 2 };
					} else if (ce3 == CellType.EMPTY) {
						return new int[] { 0, 2 };
					} else if (ce5 == CellType.EMPTY) {
						return new int[] { 1, 1 };
					}
				}
				if (ce7 == me) {
					if (ce4 == CellType.EMPTY) {
						return new int[] { 1, 0 };
					} else if (ce8 == CellType.EMPTY) {
						return new int[] { 2, 1 };
					} else if (ce5 == CellType.EMPTY) {
						return new int[] { 1, 1 };
					}
				}
				if (ce8 == me) {
					if (ce7 == CellType.EMPTY) {
						return new int[] { 2, 0 };
					} else if (ce9 == CellType.EMPTY) {
						return new int[] { 2, 2 };
					} else if (ce5 == CellType.EMPTY) {
						return new int[] { 1, 1 };
					}
				}
				if (ce9 == me) {
					if (ce6 == CellType.EMPTY) {
						return new int[] { 1, 2 };
					} else if (ce8 == CellType.EMPTY) {
						return new int[] { 2, 1 };
					} else if (ce5 == CellType.EMPTY) {
						return new int[] { 1, 1 };
					}
				}
			}
			return this.getRandomMove();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!turn.isNpc() && started && waitNextMove) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (cells[i][j] == e.getSource()) {
						if (cells[i][j].getText().equals("")) {
							game.doMoviment(turn, new int[] { i, j });
							waitNextMove = false;
							break;
						}
					}
				}
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (e.getSource() == cells[i][j]) {
					String text = cells[i][j].getText();
					if (text.equals("")) {
						cells[i][j].setBackground(Color.WHITE);
						this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					} else {
						cells[i][j].setBackground(Color.LIGHT_GRAY);
					}
					break;
				}
			}
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (e.getSource() == cells[i][j]) {
					cells[i][j].setBackground(new Color(255, 255, 190));
					this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					break;
				}
			}
		}

	}

	public boolean hasStarted() {
		return started;
	}
}
