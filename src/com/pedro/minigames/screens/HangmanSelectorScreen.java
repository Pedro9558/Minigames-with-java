package com.pedro.minigames.screens;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.pedro.minigames.games.HangmanGame;
import com.pedro.minigames.games.HangmanGame.Themes;
import com.pedro.minigames.sound.AudioClipPlayer;

/**
 * The hangman selector screen of the Hangman Game using Swing library. <br>
 * Used to pick the theme
 * @author Pedro9558
 *
 */
public class HangmanSelectorScreen extends JFrame implements WindowListener {
	private static final long serialVersionUID = 5596915496820199550L;
	
	private JLabel title = new JLabel("Select a theme");
	
	private JComboBox<String> themes = new JComboBox<String>();
	
	private JButton startButton = new JButton("Start");
	
	public HangmanSelectorScreen() {
		this.setSize(400, 200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addWindowListener(this);
		this.setTitle("Hangman - Theme Selector");
		this.getContentPane().setLayout(null);
		
		for(Themes t : Themes.values()) {
			themes.addItem(t.toString());
		}
		
		this.getContentPane().add(title);
		Font f = new Font("Arial",Font.BOLD,36);
		title.setFont(f);
		title.setBounds(60, 0, 400, 60);
		
		this.getContentPane().add(themes);
		themes.setFont(f.deriveFont(20.0f));
		themes.setBounds(60, 80, 200, 30);
		
		this.getContentPane().add(startButton);
		startButton.setFont(f.deriveFont(30.0f));
		startButton.setBounds(100, 120, 150, 45);
		startButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				HangmanGame game = new HangmanGame(null, HangmanGame.Themes.valueOf(themes.getSelectedItem().toString().toUpperCase()));
				ScreenManager.getManager().getHangmanGameScreen().setGame(game);
				ScreenManager.getManager().getHangmanSelectorScreen().setVisible(false);
				ScreenManager.getManager().getHangmanGameScreen().setVisible(true);
				AudioClipPlayer.getPlayer().stopAllSounds();
			}
		});
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		ScreenManager.getManager().getHangmanSelectorScreen().setVisible(false);
		ScreenManager.getManager().getMainScreen().setVisible(true);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
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
}
