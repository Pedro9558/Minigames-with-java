package com.pedro.minigames.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.pedro.minigames.games.TicTacToeGame;
import com.pedro.minigames.games.TicTacToeGame.CellType;
import com.pedro.minigames.player.TicTacToePlayer;
import com.pedro.minigames.player.TicTacToePlayer.AILevel;
import com.pedro.minigames.sound.AudioClipPlayer;

/**
 * The Tic Tac Toe Selector screen using Swing library, used to choose the 2 players that will <br>
 * participate on the game
 * @author Pedro9558
 *
 */
public class TicTacToeSelectorScreen extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	private JLabel title = new JLabel("Select 2 players:");
	
	private JLabel lPlayer1 = new JLabel("Player 1 (O):");
	private JComboBox<String> player1Type = new JComboBox<String>(new String[] {"Normal","AI - Easy", "AI - Normal","AI - Hard","AI - Impossible"});
	private JTextField player1Name = new JTextField();
	
	private JLabel lPlayer2 = new JLabel("Player 2 (X):");
	private JComboBox<String> player2Type = new JComboBox<String>(new String[] {"Normal","AI - Easy", "AI - Normal","AI - Hard","AI - Impossible"});
	private JTextField player2Name = new JTextField();
	
	private String[] names = {"Robert","Pedro","Patricia","Patrick","Alicia","Steve","Matthew","Marina","Alexia","Barbara","George","Paola"};
	
	private JButton startButton = new JButton("Start Game");
	
	public TicTacToeSelectorScreen() {
		this.setSize(600, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addWindowListener(this);
		this.setTitle("Tic Tac Toe Game - Player Selector");
		this.getContentPane().setLayout(null);
		
		
		Font f = new Font("Arial", Font.BOLD, 36);
		title.setFont(f);
		this.getContentPane().add(title);
		title.setBounds(150, 0, 300, 50);
		
		this.getContentPane().add(lPlayer1);
		lPlayer1.setFont(f.deriveFont(24.0f));
		lPlayer1.setBounds(10, 100, 300, 50);
		
		this.getContentPane().add(player1Type);
		player1Type.setFont(f.deriveFont(28.0f));
		player1Type.setToolTipText("<html>Select the player type:<br><b>Normal: </b> Player controlled player<br><b>AI - &lt;AI Level&gt;:</b>: Computer controlled player</html>");
		player1Type.setBounds(160, 110, 200, 30);
		player1Type.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getItem().toString().equals("Normal")) {
					if(e.getStateChange() == ItemEvent.DESELECTED) {
						player1Name.setEditable(false);
						player1Name.setForeground(Color.LIGHT_GRAY);
						player1Name.setText("Randomly generated");
					}else {
						player1Name.setEditable(true);
						player1Name.setForeground(Color.LIGHT_GRAY);
						player1Name.setText("Player 1 name here...");
					}
				}			
			}
		});
		
		this.getContentPane().add(player1Name);
		player1Name.setFont(f.deriveFont(16.0f));
		player1Name.setToolTipText("<html>Player 1 name. Must be between 2 to 13 characters (If player is <b>AI</b>, name will be generated automatically)</html>");
		player1Name.setBounds(380, 110, 180, 30);
		player1Name.addFocusListener(new FocusListener() {
			private Color originalForeground = player1Name.getForeground();
			@Override
			public void focusGained(FocusEvent e) {
				if(!player1Name.getText().equals("") && player1Name.getText().equalsIgnoreCase("Player 1 name here...")) {
					player1Name.setForeground(originalForeground);
					player1Name.setText("");
				}				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(player1Name.getText().equals("")) {
					player1Name.setForeground(Color.LIGHT_GRAY);
					player1Name.setText("Player 1 name here...");
				}
				
			}
			
		});
		player1Name.setForeground(Color.LIGHT_GRAY);
		player1Name.setText("Player 1 name here...");
		
		this.getContentPane().add(lPlayer2);
		lPlayer2.setFont(f.deriveFont(24.0f));
		lPlayer2.setBounds(10, 200, 300, 50);
		
		this.getContentPane().add(player2Type);
		player2Type.setFont(f.deriveFont(28.0f));
		player2Type.setToolTipText("<html>Select the player type:<br><b>Normal: </b> Player controlled player<br><b>AI - &lt;AI Level&gt;:</b>: Computer controlled player</html>");
		player2Type.setBounds(160, 210, 200, 30);
		player2Type.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getItem().toString().equals("Normal")) {
					if(e.getStateChange() == ItemEvent.DESELECTED) {
						player2Name.setEditable(false);
						player2Name.setForeground(Color.LIGHT_GRAY);
						player2Name.setText("Randomly generated");
					}else {
						player2Name.setEditable(true);
						player2Name.setForeground(Color.LIGHT_GRAY);
						player2Name.setText("Player 2 name here...");
					}
				}			
			}
		});
		
		
		this.getContentPane().add(player2Name);
		player2Name.setFont(f.deriveFont(16.0f));
		player2Name.setToolTipText("<html>Player 2 name. Must be between 2 to 13 characters (If player is <b>AI</b>, name will be generated automatically)</html>");
		player2Name.setBounds(380, 210, 180, 30);
		player2Name.addFocusListener(new FocusListener() {
			private Color originalForeground = player2Name.getForeground();
			@Override
			public void focusGained(FocusEvent e) {
				if(!player2Name.getText().equals("") && player2Name.getText().equalsIgnoreCase("Player 2 name here...")) {
					player2Name.setForeground(originalForeground);
					player2Name.setText("");
				}				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(player2Name.getText().equals("")) {
					player2Name.setForeground(Color.LIGHT_GRAY);
					player2Name.setText("Player 2 name here...");
				}
				
			}
			
		});
		player2Name.setForeground(Color.LIGHT_GRAY);
		player2Name.setText("Player 2 name here...");
		
		this.getContentPane().add(startButton);
		startButton.setBounds(180, 300, 200, 60);
		startButton.setForeground(new Color(0, 100, 0));
		startButton.setFont(f.deriveFont(28.0f));
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TicTacToePlayer player1 = null;
				TicTacToePlayer player2 = null;
				
				String item1Selected = player1Type.getSelectedItem().toString();
				String item2Selected = player2Type.getSelectedItem().toString();
				String aiName = names[(int)(Math.random() * names.length)];
				if(item1Selected.equals("Normal")) {
					player1 = new TicTacToePlayer(player1Name.getText(), false, CellType.WITH_O);
				}else if(item1Selected.equals("AI - Easy")) {
					player1 = new TicTacToePlayer(aiName, true, CellType.WITH_O, AILevel.EASY);
				}else if(item1Selected.equals("AI - Normal")) {
					player1 = new TicTacToePlayer(aiName, true, CellType.WITH_O, AILevel.MEDIUM);
				}else if(item1Selected.equals("AI - Hard")) {
					player1 = new TicTacToePlayer(aiName, true, CellType.WITH_O, AILevel.HARD);
				}else if(item1Selected.equals("AI - Impossible")) {
					player1 = new TicTacToePlayer(aiName, true, CellType.WITH_O, AILevel.IMPOSSIBLE);
				}
				aiName = names[(int)(Math.random() * names.length)];
				if(item2Selected.equals("Normal")) {
					player2 = new TicTacToePlayer(player2Name.getText(), false, CellType.WITH_X);
				}else if(item2Selected.equals("AI - Easy")) {
					player2 = new TicTacToePlayer(aiName, true, CellType.WITH_X, AILevel.EASY);
				}else if(item2Selected.equals("AI - Normal")) {
					player2 = new TicTacToePlayer(aiName, true, CellType.WITH_X, AILevel.MEDIUM);
				}else if(item2Selected.equals("AI - Hard")) {
					player2 = new TicTacToePlayer(aiName, true, CellType.WITH_X, AILevel.HARD);
				}else if(item2Selected.equals("AI - Impossible")) {
					player2 = new TicTacToePlayer(aiName, true, CellType.WITH_X, AILevel.IMPOSSIBLE);
				}
				TicTacToeGame game = new TicTacToeGame(player1, player2);
				ScreenManager.getManager().getTicScreen().setGame(game);
				ScreenManager.getManager().getTicSelectorScreen().setVisible(false);
				ScreenManager.getManager().getTicScreen().setVisible(true);
				ScreenManager.getManager().getTicScreen().startGame();
				AudioClipPlayer.getPlayer().stopAllSounds();
			}
		});
		
		new Thread(new NameChecker()).start();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		ScreenManager.getManager().getTicSelectorScreen().setVisible(false);
		ScreenManager.getManager().getMainScreen().setVisible(true);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
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
	
	class NameChecker implements Runnable {
		private boolean name1OK;
		private boolean name2OK;
		@Override
		public void run() {
			while(true) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(player1Name.getForeground() == Color.LIGHT_GRAY) {
					if(!player1Type.getSelectedItem().toString().equals("Normal")) {
						name1OK = true;
					}else {
						name1OK = false;
					}
				}else {
					if(player1Name.getText().equals("") || player1Name.getText().length() < 3 || player1Name.getText().length() > 13) {
						name1OK = false;
					}else {
						name1OK = true;
					}
				}
				
				if(player2Name.getForeground() == Color.LIGHT_GRAY) {
					if(!player2Type.getSelectedItem().toString().equals("Normal")) {
						name2OK = true;
					}else {
						name2OK = false;
					}
				}else {
					if(player2Name.getText().equals("") || player2Name.getText().length() < 3 || player2Name.getText().length() > 13) {
						name2OK = false;
					}else {
						name2OK = true;
					}
				}
				if(name1OK && name2OK) {
					startButton.setForeground(new Color(0, 100, 0));
					startButton.setEnabled(true);
				}else {
					startButton.setForeground(Color.DARK_GRAY);
					startButton.setEnabled(false);
				}
				
			}
		}
	}

}
