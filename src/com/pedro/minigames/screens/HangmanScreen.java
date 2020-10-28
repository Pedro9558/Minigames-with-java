package com.pedro.minigames.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.pedro.minigames.games.HangmanGame;
import com.pedro.minigames.games.HangmanGame.HangmanPhase;
import com.pedro.minigames.games.HangmanGame.Themes;
import com.pedro.minigames.image.ImageManager;
import com.pedro.minigames.player.Hangman;
import com.pedro.minigames.player.Player;
import com.pedro.minigames.sound.AudioClipPlayer;

/**
 * The hangman screen of the Hangman Game using Swing library
 * @author Pedro9558
 *
 */
public class HangmanScreen extends JFrame implements WindowListener {
	private static final long serialVersionUID = -4933099118150309793L;
	
	private HangmanGame game;
	
	private boolean waitForGuess = true;
	private boolean readInput = true;
	
	private char userGuess;
	
	private Hangman hangman;
	
	private String formation;
	
	private JLabel title = new JLabel("Hangman");
	
	private JLabel word = new JLabel();
	private JLabel theme =  new JLabel("Theme: %theme%");
	
	private JLabel gameSituation = new JLabel();
	
	private JPanel keyboard = new JPanel();
	private char[] qwertOrder = new char[] {81, 87, 69, 82, 84, 89, 85, 73, 79,
			80, 65, 83, 68, 70, 71, 72, 74, 75, 
			76, 90, 88, 67, 86, 66, 78, 77};
	
	private char[] guessed = new char[26];
	
	private JButton[] inputs =  new JButton[26];
	
	private ImageManager iManager;
	private AudioClipPlayer audioPlayer;
	
	private HangmanWorker mainWorker;
	
	private Thread hangmanFix;
	
	private JButton restartGame = new JButton("Restart");
	
	private JButton leaveGame = new JButton("Leave Game");
	
	private ColorBlinkAnimation blinkAnimation;
	/*
	 * Hangman phase instances
	 * JLabel final bounds:
	 * Head -> (172, 105, headImage.getWidth(), headImage.getHeight())
	 * Body -> (194, 180, 32, 100)
	 * Left Arm -> (215, 185, 15, 80)
	 * Right Arm -> (190, 180, 15, 80)
	 * Left Leg -> (211, 280, 15, 70)
	 * Right Leg -> (194, 280, 15, 70)
	 */
	private HashMap<HangmanPhase, JLabel> hangmanParts = new HashMap<HangmanPhase, JLabel>();
	
	public HangmanScreen() {
		this.setSize(800, 600);
		this.setResizable(false);
		this.addWindowListener(this);
		this.setLocationRelativeTo(null);
		this.setTitle("Hangman");
		this.getContentPane().setBackground(new Color(255, 255, 190));
		this.getContentPane().setLayout(null);
		iManager = ImageManager.getManager();
		this.buildHangman();
		
		audioPlayer = AudioClipPlayer.getPlayer();
		
		this.getContentPane().add(title);
		Font f = new Font("Arial",Font.BOLD, 48);
		title.setFont(f);
		title.setBounds(300, 0, 400, 60);
		
		this.getContentPane().add(word);
		word.setFont(f);
		word.setBounds(30, 400, 600, 200);
		
		this.getContentPane().add(theme);
		theme.setFont(f.deriveFont(28.0f));
		theme.setBounds(30, 300, 600, 200);
		
		this.getContentPane().add(keyboard);
		keyboard.setLayout(new GridLayout(3, 9));
		keyboard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		for(int i = 0; i < inputs.length; i++) {
			inputs[i] = new JButton("" + qwertOrder[i]);
			inputs[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			inputs[i].setBackground(Color.WHITE);
			inputs[i].setFont(f.deriveFont(24.0f));
			inputs[i].addMouseListener(new MouseListener() {
				
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
					JButton self = (JButton)e.getSource();
					if(self.isEnabled()) {
						self.setBackground(Color.WHITE);
					}
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					JButton self = (JButton)e.getSource();
					if(self.isEnabled()) {
						self.setBackground(Color.GREEN);
					}
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					JButton self = (JButton)e.getSource();
					if(self.isEnabled() && readInput) {
						userGuess = self.getText().charAt(0);
						waitForGuess = false;
						disableGuessing();
					}
					
				}
			});
			keyboard.add(inputs[i]);
		}
		keyboard.setBounds(300, 100, 400, 200);
		
		this.getContentPane().add(gameSituation);
		gameSituation.setFont(f);
		gameSituation.setBounds(360, 320, 400, 60);
		
		this.getContentPane().add(restartGame);
		restartGame.setFont(f.deriveFont(30.0f));
		restartGame.setForeground(new Color(0, 100, 0));
		restartGame.setBackground(Color.LIGHT_GRAY);
		restartGame.setBorder(null);
		restartGame.setEnabled(false);
		restartGame.setBounds(550, 20, 200, 40);
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
		leaveGame.setBounds(10, 10, 200, 40);
		leaveGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to leave the game?") == JOptionPane.YES_OPTION) {
					ScreenManager.getManager().getHangmanGameScreen().setVisible(false);
					ScreenManager.getManager().getMainScreen().setVisible(true);
				}
			}

		});

		
		blinkAnimation = new ColorBlinkAnimation(gameSituation, this.getContentPane().getBackground(), Color.RED, ColorBlinkAnimation.FOREGROUND, 500L);
		mainWorker = new HangmanWorker();
		new Thread(blinkAnimation).start();
		new Thread(mainWorker).start();
		// Invisible hangman fix
		hangmanFix = new Thread(new Runnable() {
			int attempts = 0;
			public void run() {
				while(attempts < 5) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					repaintHang();
					attempts++;
				}
			}
		});
	}
	
	private void enableGuessing() {
		readInput = true;
		for(int i = 0; i < inputs.length; i++) {
			inputs[i].setEnabled(true);
			inputs[i].setBackground(Color.WHITE);
			for(int j = 0; j < guessed.length; j++) {
				if(inputs[i].getText().charAt(0) == guessed[j]) {
					inputs[i].setEnabled(false);
					inputs[i].setBackground(Color.LIGHT_GRAY);
					break;
				}
			}
		}
	}
	private void disableGuessing() {
		readInput = false;
		for(int i = 0; i < inputs.length; i++) {
			inputs[i].setEnabled(false);
			inputs[i].setBackground(Color.LIGHT_GRAY);
		}
	}
	
	private void generateHangman() throws URISyntaxException, IOException {
		if(hangmanParts.size() != 0) {
			for(Entry<HangmanPhase, JLabel> entry : hangmanParts.entrySet()) {
				this.getContentPane().remove(entry.getValue());
			}
		}
		hangmanParts = new HashMap<HangmanPhase, JLabel>();
		Random r = new Random();
		// Read the images related to the hangman head
		URI uri = iManager.getClass().getResource("/com/pedro/minigames/image").toURI();
		Path myPath;
		int heads = 0;
		String regex = "\\\\";
		if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = null;
            try {
            	fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
            }catch(FileSystemAlreadyExistsException exception) {
            	fileSystem = FileSystems.getFileSystem(uri);
            }
            myPath = fileSystem.getPath("/com/pedro/minigames/image");
            regex = "/";
        } else {
            myPath = Paths.get(uri);
            regex = "\\\\";
        }
        Stream<Path> walk = Files.walk(myPath, 1);
        for (Iterator<Path> it = walk.iterator(); it.hasNext();){
            String[] split = it.next().toString().split(regex);
            if(split.length != 0) {
            	System.out.println(split[split.length - 1]);
				if(split[split.length - 1].contains("head")) {
					if(Character.isDigit(split[split.length - 1].charAt(4))) {
						heads++;
					}
				}
            }else {
            	System.out.println("Split returned nothing");
            }
        }
        walk.close();
        int headSelected = r.nextInt(heads) + 1;
        // Select a head
        ImageIcon head = iManager.getImageIcon("head" + headSelected + ".png");
        // Determine it's skin color by converting the icon into bufferedimage
        BufferedImage image = new BufferedImage(head.getIconWidth(), head.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        head.paintIcon(null, g2d, 0, 0);
        g2d.dispose();
        int rgb = image.getRGB(25, 25);
        Color skinColor = new Color(rgb, true);
        Color shirtColor = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        Color pantsColor = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        hangman = new Hangman(head, shirtColor, pantsColor, skinColor);
        //
        JLabel lHead = new JLabel(head);
        lHead.setSize(image.getWidth(), image.getHeight());
        hangmanParts.put(HangmanPhase.HEAD, lHead);
        //
        JLabel lLeftArm = new JLabel() {
        	@Override
        	protected void paintComponent(Graphics g) {
        		Graphics2D g2 = (Graphics2D) g;
        		AffineTransform aT = g2.getTransform();
        		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        		Shape shape = g2.getClip();
        		aT.rotate(Math.toRadians(-20));
        		g2.setTransform(aT);
        		g2.setClip(shape);
        		super.paintComponent(g);
        	}
        };
        lLeftArm.setBackground(skinColor);
        lLeftArm.setOpaque(true);
        hangmanParts.put(HangmanPhase.LEFT_ARM, lLeftArm);
        //
        JLabel lRightArm = new JLabel() {
        	@Override
        	protected void paintComponent(Graphics g) {
        		Graphics2D g2 = (Graphics2D) g;
        		AffineTransform aT = g2.getTransform();
        		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        		Shape shape = g2.getClip();
        		aT.rotate(Math.toRadians(20));
        		g2.setTransform(aT);
        		g2.setClip(shape);
        		super.paintComponent(g);
        	}
        };
        lRightArm.setBackground(skinColor);
        lRightArm.setOpaque(true);
        hangmanParts.put(HangmanPhase.RIGHT_ARM, lRightArm);
        //
        JLabel lLeftLeg = new JLabel();
        lLeftLeg.setBackground(pantsColor);
        lLeftLeg.setOpaque(true);
        hangmanParts.put(HangmanPhase.LEFT_LEG, lLeftLeg);
        //
        JLabel lRightLeg = new JLabel();
        lRightLeg.setBackground(pantsColor);
        lRightLeg.setOpaque(true);
        hangmanParts.put(HangmanPhase.RIGHT_LEG_FULL, lRightLeg);
        //
        JLabel lShirt = new JLabel();
        lShirt.setBackground(shirtColor);
        lShirt.setOpaque(true);
        hangmanParts.put(HangmanPhase.BODY, lShirt);
        //
        //
	}
	
	private void buildHangman() {
		try {
			this.generateHangman();
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        TreeMap<HangmanPhase, JLabel> sortedMap = new TreeMap<HangmanPhase, JLabel>(hangmanParts);
        for(Entry<HangmanPhase, JLabel> entry : sortedMap.entrySet()) {
        	this.getContentPane().add(entry.getValue());
        	switch(entry.getKey()) {
			case HEAD:
				entry.getValue().setBounds(172, 105, entry.getValue().getWidth(), entry.getValue().getHeight());
				break;
			case BODY:
				entry.getValue().setBounds(194, 180, 32, 100);
				break;
			case LEFT_ARM:
				entry.getValue().setBounds(215, 185, 15, 80);				
				break;
			case LEFT_LEG:
				entry.getValue().setBounds(211, 280, 15, 70);
				break;
			case RIGHT_ARM:
				entry.getValue().setBounds(190, 180, 15, 80);
				break;
			case RIGHT_LEG_FULL:
				entry.getValue().setBounds(194, 280, 15, 70);
				break;
			case NOTHING:
			default:
				break;        	
        	}
        }
        
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// Setup hangman base
		// Wooden base
		g.setColor(new Color(132, 86, 61));
		g.fill3DRect(50, 350, 100, 20, true);
		g.fill3DRect(90, 80, 20, 270, true);
		g.fill3DRect(60, 80, 170, 20, true);
		// Rope
		g.setColor(Color.BLACK);
		g.fillRoundRect(210, 95, 5, 50, 10, 10);
	}
	
	private void repaintHang() {
		Graphics g = this.getContentPane().getGraphics();
		if(g != null) {
			g.setColor(new Color(132, 86, 61));
			g.fill3DRect(48, 325, 100, 20, true);
			g.fill3DRect(88, 55, 20, 270, true);
			g.fill3DRect(58, 55, 170, 20, true);
			// Rope
			g.setColor(Color.BLACK);
			g.fillRoundRect(208, 70, 5, 50, 10, 10);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		ScreenManager.getManager().getHangmanGameScreen().setVisible(false);
		ScreenManager.getManager().getMainScreen().setVisible(true);
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		ScreenManager.getManager().getHangmanGameScreen().setVisible(false);
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

	public HangmanGame getGame() {
		return game;
	}
	
	private void restartGame() {
		Player p = game.getPlayer();
		Themes t = game.getCurrentTheme();
		this.setGame(new HangmanGame(p, t));
	}

	public void setGame(HangmanGame game) {
		this.game = game;
		mainWorker.resetGame();
		theme.setText("Theme: " + game.getCurrentTheme().toString());
	}
	
	public class ColorBlinkAnimation implements Runnable {
		public final static int BACKGROUND = 0;
		public final static int FOREGROUND = 1;
		private JComponent component;
		private Color color1;
		private Color color2;
		private int layer;
		private long delay;
		
		/**
		 * Constructor for the blinking animation
		 * @param component - The component that will blink
		 * @param color1 - The first color
		 * @param color2 - The second color
		 * @param layer - The affected layer, can be either {@code ColorBlinkAnimation.BACKGROUND} or {@code ColorBlinkAnimation.FOREGROUND}
		 * @param delay - The delay time in ms of the blinking animation
		 * @throws IllegalArgumentException - If layer is not either {@code ColorBlinkAnimation.BACKGROUND} or {@code ColorBlinkAnimation.FOREGROUND}
		 */
		public ColorBlinkAnimation(JComponent component, Color color1, Color color2, int layer, long delay) {
			if(layer != BACKGROUND && layer != FOREGROUND) {
				throw new IllegalArgumentException("Layer must be either "+BACKGROUND+" - Background or " + FOREGROUND+" - Foreground");
			}
			this.setColor1(color1);
			this.setColor2(color2);
			this.setLayer(layer);
			this.setDelay(delay);
			this.setComponent(component);
			if(layer == BACKGROUND) {
				this.getComponent().setBackground(color1);
			}else {
				this.getComponent().setForeground(color1);
			}
		}
		
		public JComponent getComponent() {
			return component;
		}

		public void setComponent(JComponent component) {
			this.component = component;
		}

		public Color getColor1() {
			return color1;
		}

		public void setColor1(Color color1) {
			this.color1 = color1;
		}

		public Color getColor2() {
			return color2;
		}

		public void setColor2(Color color2) {
			this.color2 = color2;
		}

		public int getLayer() {
			return layer;
		}

		public void setLayer(int layer) {
			this.layer = layer;
		}

		public long getDelay() {
			return delay;
		}

		public void setDelay(long delay) {
			this.delay = delay;
		}

		@Override
		public void run() {
			while(true) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				switch(layer) {
				case BACKGROUND:
					if(color2 == this.getComponent().getBackground()) {
						this.getComponent().setBackground(color1);
					}else {
						this.getComponent().setBackground(color2);
					}
					break;
				case FOREGROUND:
					if(color2 == this.getComponent().getForeground()) {
						this.getComponent().setForeground(color1);
					}else {
						this.getComponent().setForeground(color2);
					}
					break;
				}
			}
		}
	}
	
	class HangmanWorker implements Runnable {
		@Override
		public void run() {
			resetGame();
			while(true) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!waitForGuess) {
					boolean play = game.makeGuess(userGuess);
					waitForGuess = true;
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					formation = game.getCurrentFormation();
					word.setText("<html>");
					for(char c : formation.toCharArray()) {
						if(c == ' ') {
							word.setText(word.getText() + "&nbsp;");
						}else {
							word.setText(word.getText() + c + " ");
						}
					}
					word.setText(word.getText() + "</html>");
					HangmanPhase currentPhase = game.getHangmanPhase();
					switch(currentPhase) {
					case HEAD:
						hangmanParts.get(HangmanPhase.HEAD).setBounds(172, 105, hangmanParts.get(HangmanPhase.HEAD).getWidth(), hangmanParts.get(HangmanPhase.HEAD).getHeight());
						break;
					case BODY:
						hangmanParts.get(HangmanPhase.BODY).setBounds(194, 180, 32, 100);
						break;
					case LEFT_ARM:
						hangmanParts.get(HangmanPhase.LEFT_ARM).setBounds(215, 185, 15, 80);
						break;
					case RIGHT_ARM:
						hangmanParts.get(HangmanPhase.RIGHT_ARM).setBounds(190, 180, 15, 80);
						break;
					case LEFT_LEG:
						hangmanParts.get(HangmanPhase.LEFT_LEG).setBounds(211, 280, 15, 70);
						break;
					case RIGHT_LEG_FULL:
						hangmanParts.get(HangmanPhase.RIGHT_LEG_FULL).setBounds(194, 280, 15, 70);
						break;
					case NOTHING:
						for(Entry<HangmanPhase, JLabel> entry : hangmanParts.entrySet()) {
							entry.getValue().setBounds(-500, -500, entry.getValue().getWidth(), entry.getValue().getHeight());
						}
						break;
					default:
						break;				
					}
					if(game.isGameOver()) {
						if(game.isVictory()) {
							blinkAnimation.setColor2(new Color(0, 100, 0));
							gameSituation.setText("You Won!");
						}else {
							blinkAnimation.setColor2(new Color(100, 0, 0));
							gameSituation.setText("Game Over!");
						}
						restartGame.setEnabled(true);
						restartGame.setBackground(new Color(0, 200, 0));
					} else {
						readInput = true;
						for(int i = 0; i < guessed.length; i++) {
							if(guessed[i] == 0) {
								guessed[i] = userGuess;
								break;
							}
						}
						enableGuessing();
					}
					hangmanFix = new Thread(new Runnable() {
						int attempts = 0;
						public void run() {
							while(attempts < 50) {
								try {
									Thread.sleep(1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								repaintHang();
								attempts++;
							}
						}
					});
					hangmanFix.start();
				}
			}
		}
		public void resetGame() {
			hangmanFix = new Thread(new Runnable() {
				int attempts = 0;
				public void run() {
					while(attempts < 50) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						repaintHang();
						attempts++;
					}
				}
			});
			buildHangman();
			for(Entry<HangmanPhase, JLabel> entry : hangmanParts.entrySet()) {
				entry.getValue().setBounds(-500, -500, entry.getValue().getWidth(), entry.getValue().getHeight());
			}
			gameSituation.setText("");
			hangmanFix.start();
			if(game == null) {
				word.setText("");
				return;
			}
			guessed = new char[26];
			restartGame.setEnabled(false);
			restartGame.setBackground(Color.LIGHT_GRAY);
			enableGuessing();
			formation = game.getCurrentFormation();
			word.setText("<html>");
			for(char c : formation.toCharArray()) {
				if(c == ' ') {
					word.setText(word.getText() + "&nbsp;");
				}else {
					word.setText(word.getText() + c + " ");
				}
			}
			word.setText(word.getText() + "</html>");
			
		}
	}
}
