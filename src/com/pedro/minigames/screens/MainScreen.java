package com.pedro.minigames.screens;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.pedro.minigames.image.ImageManager;
import com.pedro.minigames.sound.AudioClipPlayer;
import com.pedro.minigames.sound.SoundPlayer;

/**
 * The main title screen using the Swing library
 * @author Pedro9558
 *
 */
public class MainScreen extends JFrame {
	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;
	
	private ImageIcon audioIcon;
	private JButton audioButton = new JButton();
	private boolean audioOn = true;
	private BackgroundOpacityAnimation audioBAnimation;
	
	private SoundPlayer player;
	
	private JLabel musicPlaying = new JLabel("Playing: %music%");
	private SlidingLeftAnimation slidingAnimation;

	private JLabel title = new JLabel("MINIGAMES");
	
	private JFrame screenReference = this;
	
	private JPanel menuContainer = new JPanel();
	
	private JLabel select = new JLabel("Select a minigame: ");
	
	private JLabel option1 = new JLabel("  Hangman");
	private BackgroundOpacityAnimation option1Animation;
	private JLabel option2 = new JLabel("Tic Tac Toe");
	private BackgroundOpacityAnimation option2Animation;
	
	private JLabel exitOption = new JLabel("   Exit");
	private BackgroundOpacityAnimation exitAnimation;
	
	// Don't you dare remove or change that!
	private JLabel copyright = new JLabel("by Pedro9558");
	
	public MainScreen() {
		// Screen setup
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.screenReference.setSize(800, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Classic Minigames");
		this.getContentPane().setBackground(Color.CYAN);
		this.getContentPane().setLayout(null);
		
		
		player = AudioClipPlayer.getPlayer();
		
		// Title component setup
		Font f = new Font("Arial",Font.BOLD, 56);
		title.setFont(f);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(100, 0, 600, 80);
		this.getContentPane().add(title);
		
		
		// select
		this.getContentPane().add(select);
		select.setFont(f.deriveFont(36.0f));
		select.setBounds(250, 0, 350, 300);
		
		// Menu Container
		menuContainer.setLayout(null);
		menuContainer.add(option1);
		option1.setFont(new Font("Lucida Console",Font.BOLD, 40));
		option1.setBackground(Color.BLUE);
		option1.setOpaque(true);
		option1.setBounds(10, 0, 300, 50);
		option1.addMouseListener(new MouseListener() {
			
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
				option1Animation.setMode((byte)0);
				screenReference.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				option1Animation.setMode((byte)1);
				player.playSound("mouseover.wav", false, true, 0.2);
				screenReference.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				screenReference.setVisible(false);
				ScreenManager.getManager().getHangmanSelectorScreen().setVisible(true);
			}
		});
		menuContainer.add(option2);
		option2.setFont(new Font("Lucida Console",Font.BOLD, 40));
		option2.setBounds(10, 100, 300, 50);
		option2.setBackground(Color.BLUE);
		option2.setOpaque(true);
		option2.addMouseListener(new MouseListener() {
			
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
				option2Animation.setMode((byte)0);
				screenReference.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				option2Animation.setMode((byte)1);
				player.playSound("mouseover.wav", false, true, 0.2);
				screenReference.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				screenReference.setVisible(false);
				ScreenManager.getManager().getTicSelectorScreen().setVisible(true);
			}
		});
		this.getContentPane().add(menuContainer);
		menuContainer.setBounds(250, 170, 330, 300);
		menuContainer.setBackground(new Color(0f,0f,0f,0.3f));
		
		// Exit option
		this.getContentPane().add(exitOption);
		this.exitOption.setBounds(350, 500, 170, 50);
		exitOption.setBackground(Color.BLUE);
		exitOption.setFont(f.deriveFont(48.0f));
		exitOption.setForeground(Color.RED);
		exitOption.setOpaque(true);
		exitOption.addMouseListener(new MouseListener() {
			
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
				exitAnimation.setMode((byte)0);
				screenReference.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				exitAnimation.setMode((byte)1);
				player.playSound("mouseover.wav", false, true, 0.2);
				screenReference.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you wanna exit?") == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				
			}
		});
		
		this.getContentPane().add(musicPlaying);
		musicPlaying.setFont(f.deriveFont(16.0f));
		musicPlaying.setBounds(0, 550, 400, 22);
		player.playSound("Kevin Macleod - Killing Time.wav", true, false, 0.3);
		musicPlaying.setText(musicPlaying.getText().replace("%music%", ((AudioClipPlayer) player).getEnhancedMusicName()));
		
		
		if(audioOn)
			audioIcon = ImageManager.getManager().getImageIcon("audioon.png");
		else
			audioIcon = ImageManager.getManager().getImageIcon("audiooff.png");
		
		this.getContentPane().add(audioButton);
		audioButton.setBounds(10, 10, 55, 55);
		audioButton.setBorder(null);
		audioButton.setIcon(audioIcon);
		audioButton.setToolTipText("<html>Turn <b>on</b> or <b>off</b> all the application audio<br>Audio is current "+(audioOn ? "<b style=\"color:green;\">ON</b>" : "<b style=\"color:red;\">OFF</b>" )+"</html>");
		audioButton.setBackground(new Color(0, 0, 80));
		audioButton.addMouseListener(new MouseListener() {
			
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
				audioBAnimation.setMode((byte)0);
				screenReference.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				audioBAnimation.setMode((byte)1);
				player.playSound("mouseover.wav", false, true, 0.2);
				screenReference.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(audioOn) {
					audioIcon = ImageManager.getManager().getImageIcon("audiooff.png");
					player.setEnabled(false);
					player.pauseSound(player.getMusicPlaying());
					musicPlaying.setText("Playing: <Nothing, Audio Disabled>");
				}else {
					audioIcon = ImageManager.getManager().getImageIcon("audioon.png");
					player.setEnabled(true);
					player.playSound("Kevin Macleod - Killing Time.wav", true, false, 0.3);
					musicPlaying.setText("Playing: %music%");
					musicPlaying.setText(musicPlaying.getText().replace("%music%", ((AudioClipPlayer) player).getEnhancedMusicName()));
				}
				audioOn = !audioOn;
				audioButton.setIcon(audioIcon);
				audioButton.setToolTipText("<html>Turn <b>on</b> or <b>off</b> all the application audio<br>Audio is current "+(audioOn ? "<b style=\"color:green;\">ON</b>" : "<b style=\"color:red;\">OFF</b>" )+"</html>");
			}
		});
		
		this.getContentPane().add(copyright);
		copyright.setFont(f.deriveFont(12.0f));
		copyright.setBounds(500, 30, 600, 80);
		
		option1Animation = new BackgroundOpacityAnimation(option1, 5L);
		option2Animation = new BackgroundOpacityAnimation(option2, 5L);
		exitAnimation = new BackgroundOpacityAnimation(exitOption, 5L);
		audioBAnimation = new BackgroundOpacityAnimation(audioButton, 5L);
		slidingAnimation = new SlidingLeftAnimation(musicPlaying, 10L, this.getWidth());
		// Animation threads
		new Thread(new TitleAnimation()).start();
		new Thread(option1Animation).start();
		new Thread(option2Animation).start();
		new Thread(audioBAnimation).start();
		new Thread(exitAnimation).start();
		new Thread(slidingAnimation).start();
	}
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if(player.isEnabled() && !player.isPlaying("Kevin Macleod - Killing Time.wav")) {
			player.playSound("Kevin Macleod - Killing Time.wav", true, false, 0.3);
		}
	}
	class TitleAnimation implements Runnable {

		private long animationDelay = 20L;
		
		private int yellowLevel = 0;
		
		@Override
		public void run() {
			while(true) {
				// Yellow fade in
				while(yellowLevel < 255) {
					try {
						Thread.sleep(animationDelay);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					title.setForeground(new Color(yellowLevel, yellowLevel, 0));
					yellowLevel = yellowLevel + 5;
				}
				// Yellow fade out
				while(yellowLevel > 0) {
					try {
						Thread.sleep(animationDelay);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					title.setForeground(new Color(yellowLevel, yellowLevel, 0));
					yellowLevel = yellowLevel - 5;
				}
			}
			
		}
		
	}
	class SlidingLeftAnimation implements Runnable {
		private JComponent component;
		private int screenWidth;
		private long animationDelay = 20L;
		public SlidingLeftAnimation(JComponent component, long animationDelay ,int screenWidth) {
			this.component = component;
			this.animationDelay = animationDelay;
			this.screenWidth = screenWidth;
		}
		
		@Override
		public void run() {
			while(true) {
				this.getComponent().setBounds(this.screenWidth + 200, this.getComponent().getY(), this.getComponent().getWidth(), this.getComponent().getHeight());
				try {
					Thread.sleep(animationDelay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(this.getComponent().getX() >= (-50 - component.getWidth())) {
					try {
						Thread.sleep(animationDelay);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.getComponent().setBounds(this.getComponent().getX() - 1, this.getComponent().getY(), this.getComponent().getWidth(), this.getComponent().getHeight());
				}
			}
		}

		public JComponent getComponent() {
			return component;
		}

		public void setComponent(JComponent component) {
			this.component = component;
		}

		public int getScreenWidth() {
			return screenWidth;
		}

		public void setScreenWidth(int screenWidth) {
			this.screenWidth = screenWidth;
		}

		public long getAnimationDelay() {
			return animationDelay;
		}

		public void setAnimationDelay(long animationDelay) {
			this.animationDelay = animationDelay;
		}
	}
	class BackgroundOpacityAnimation implements Runnable {
		private long animationDelay = 20L;
		private JComponent component;
		private int backgroundOpacity = 0;
		
		// 0 = fade out 1 = fade in
		private byte mode = 0;
		
		public BackgroundOpacityAnimation(JComponent component, long animationDelay) {
			this.component = component;
			this.animationDelay = animationDelay > 0 ? animationDelay : 10L;
		}
		@Override
		public void run() {
			while(true) {
				try {
					Thread.sleep(animationDelay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(mode == 1) {
					backgroundOpacity = backgroundOpacity < 255 ? backgroundOpacity + 5 : backgroundOpacity;
				}else {
					backgroundOpacity = backgroundOpacity > 0 ? backgroundOpacity - 5 : backgroundOpacity;
				}
				Color current = component.getBackground();
				Color color = new Color(backgroundOpacity, backgroundOpacity, current.getBlue());
				component.setBackground(color);
			}
		}
		public byte getMode() {
			return mode;
		}
		public void setMode(byte mode) {
			this.mode = mode;
		}
		
	}
}
