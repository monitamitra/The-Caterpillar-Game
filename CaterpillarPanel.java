import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class CaterpillarPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1211678552423540494L;
	private static final int GRID_SQUARE = 20;
	private static final int CATERPILLAR_HEAD = 0;
	private static final int GAME_PANEL_SIZE = 560;
	// the number of elements in the array (used for the graphics draw line function to draw
	// the caterpillar on the game panel)
	private static final int MOVE_CHANGE = (GAME_PANEL_SIZE * GAME_PANEL_SIZE) / GRID_SQUARE;
	// the x and y coordinates of the apple on the game grid panel
	private int appleX;
	private int appleY;
	// used to implement moving action of caterpillar
	private Timer timer;
	private int x[];
	private int y[];
	// # of sections of the caterpillar that line should be drawn on the game grid panel
	private int bodyParts;
	private char direction;
	private boolean stopGame;
	private int currScore;
	private String maxScore;
	// file used to store high score of user between the rounds of the game
	private File scoreFile;

	public CaterpillarPanel() throws FileNotFoundException {
		final int PANEL_X = 110;
		final int PANEL_Y = 140;
		final int RATE_OF_TIMER = 150;
		scoreFile = new File("maxScore.txt");
		maxScore = getMaxScore();
		stopGame = false;
		direction = 'R';
		bodyParts = 4;
		currScore = 0;
		appleX = 100;
		appleY = 0;
		x = new int[MOVE_CHANGE];
		y = new int[MOVE_CHANGE];
		timer = new Timer(RATE_OF_TIMER, this);
		this.setBackground(new Color(0x98FB98));
		this.setBounds(PANEL_X, PANEL_Y, GAME_PANEL_SIZE, GAME_PANEL_SIZE);
		this.setOpaque(true);
		this.setFocusable(true);
		// allows user to move the caterpillar with up, down, left, and right, keys of the
		// keyboard
		this.addKeyListener(new MyKeyAdapter());
	}

	/**
	 * draws grid lines and caterpillar, also enables caterpillar slithering motion across the
	 * grid initiated by the user pressing keys
	 * 
	 * @param Graphics g
	 * @return void
	 * 
	 */
	public void paintComponent(Graphics g) {
		timer.start();
		super.paintComponent(g);
		final int caterpillar_ARC_LENGTH = 10;
		final Color APPLE_RED = new Color(0xD30000);
		final Color COBALT_BLUE = new Color(0x1338BE);
		final Color DARK_BLUE_HEAD = new Color(0x0A1172);

		// if game is still running
		if (!stopGame) {
			drawGridLines(g);
			g.setColor(APPLE_RED);
			// draws apple
			g.fillOval(appleX, appleY, GRID_SQUARE, GRID_SQUARE);

			for (int i = CATERPILLAR_HEAD; i < bodyParts; i++) {
				// head should be a different color
				if (i == CATERPILLAR_HEAD) {
					g.setColor(DARK_BLUE_HEAD);
					g.fillRoundRect(x[i], y[i], GRID_SQUARE, GRID_SQUARE, caterpillar_ARC_LENGTH,
							caterpillar_ARC_LENGTH);
				} else {
					g.setColor(COBALT_BLUE);
					g.fillRoundRect(x[i], y[i], GRID_SQUARE, GRID_SQUARE, caterpillar_ARC_LENGTH,
							caterpillar_ARC_LENGTH);
				}
			}

			// checks for new high score set by the user
			checkScore();
			// reassigns high score to new high score set by user
			maxScore = this.getMaxScore();
		}
	}

	/**
	 * draws grid lines on game panel for the user to easily move caterpillar around and eat
	 * the apple
	 * 
	 * @param Graphics g
	 * @return void
	 * 
	 */
	private void drawGridLines(Graphics g) {
		final Color DARK_GREEN = new Color(0x2E8B57);
		final float GRID_LINE_STROKE = (float) 3.0;
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(DARK_GREEN);
		g2.setStroke(new BasicStroke(GRID_LINE_STROKE));
		for (int i = CATERPILLAR_HEAD; i < GAME_PANEL_SIZE / GRID_SQUARE; i++) {
			g2.drawLine(i * GRID_SQUARE, 0, i * GRID_SQUARE, GAME_PANEL_SIZE);
			g2.drawLine(0, i * GRID_SQUARE, GAME_PANEL_SIZE, i * GRID_SQUARE);
		}
	}

	/**
	 * Checks for a new high score set by the user and if there is a new high score, it is
	 * overwritten in the file
	 * 
	 * @return void
	 * 
	 */
	private void checkScore() {
		if (currScore > Integer.parseInt(maxScore)) {
			maxScore = currScore + "";
			FileWriter fileWriter = null;
			// overwrites new high score to the file
			try {
				fileWriter = new FileWriter(scoreFile, false);
				fileWriter.write(this.maxScore);
				// updates high score label in main game grid panel
				CaterpillarFrame.updateMaxScore(Integer.parseInt(maxScore));
			} catch (Exception e) {

			} finally {
				if (fileWriter != null) {
					try {
						fileWriter.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	/**
	 * Moves the caterpillar in a slithering motion, triggered by ActionEvent of user pressing
	 * specified direction keys
	 * 
	 * @return void
	 * 
	 */
	public void moveCaterpillar() {

		// handles the movement of the caterpillar's tail to curve move along the lines of the
		// main game grid panel
		for (int i = bodyParts; i > CATERPILLAR_HEAD; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		// up
		if (direction == 'U') {
			y[CATERPILLAR_HEAD] -= GRID_SQUARE;
			// down
		} else if (direction == 'D') {
			y[CATERPILLAR_HEAD] += GRID_SQUARE;
			// left
		} else if (direction == 'L') {
			x[CATERPILLAR_HEAD] -= GRID_SQUARE;
			// right
		} else if (direction == 'R') {
			x[CATERPILLAR_HEAD] += GRID_SQUARE;
		}
	}

	/**
	 * Moves the apple randomly in the game grid panel
	 * 
	 * @return void
	 * 
	 */
	public void moveApple() {
		Random ran = new Random();
		// calculation is used to fit apple into a random square on the grid
		appleX = ran.nextInt((GAME_PANEL_SIZE / GRID_SQUARE)) * GRID_SQUARE;
		appleY = ran.nextInt((GAME_PANEL_SIZE / GRID_SQUARE)) * GRID_SQUARE;
	}

	/**
	 * Initiates the caterpillar and apple to move when the user presses a direction key If
	 * collision occurred, ends game and launches a new round
	 * 
	 * @Override
	 * @param ActionEvent e
	 * @return void
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		// if game is still running
		if (!stopGame) {
			try {
				// moves apple and handles effects of eating the apple
				caterpillarFood();
			} catch (IOException e1) {
			}
			// draw line (move caterpillar) in direction of key user pressed
			moveCaterpillar();
			try {
				// checks for collisions
				checkCollisions();
			} catch (IOException e1) {
			}
		} else {
			// checks for new high score and ends game if caterpillar had a collision
			checkScore();
			endGame();
			try {
				// brings up launch screen to start a new round of the game
				new CaterpillarFrame().launchGameAgain();

			} catch (IOException e1) {
			}
		}
		this.repaint();
	}

	/**
	 * Ends the game when the caterpillar has a collision
	 * 
	 * @return void
	 * 
	 */
	private void endGame() {
		JFrame f = (JFrame) SwingUtilities.getWindowAncestor(this);
		// destroys main game frame and stops caterpillar from moving further
		f.dispose();
		timer.stop();
	}

	/**
	 * Checks if the caterpillar touches the border of the game panel or collides with its own
	 * body
	 * 
	 * @throws IOException
	 * @return void
	 * 
	 */
	private void checkCollisions() throws IOException {

		// caterpillar collides with its own body
		for (int i = bodyParts; i > CATERPILLAR_HEAD; i--) {
			if (x[CATERPILLAR_HEAD] == x[i] && y[CATERPILLAR_HEAD] == y[i]) {
				// game ends, checks if user set new high score to carry over to next game round
				stopGame = true;
				checkScore();
			}
		}
		// caterpillar collides with border of the game panel
		if (x[CATERPILLAR_HEAD] < 0 || y[CATERPILLAR_HEAD] < 0
				|| x[CATERPILLAR_HEAD] > (GAME_PANEL_SIZE - GRID_SQUARE)
				|| y[CATERPILLAR_HEAD] > (GAME_PANEL_SIZE - GRID_SQUARE)) {
			// game ends, checks if user set new high score to carry over to next game round
			stopGame = true;
			checkScore();
		}
	}

	/**
	 * Has the caterpillar eaten the apple?
	 * 
	 * @throws IOException
	 * @return void
	 * 
	 */
	private void caterpillarFood() throws IOException {
		// did caterpillar eat the apple?
		if (x[CATERPILLAR_HEAD] == appleX && y[CATERPILLAR_HEAD] == appleY) {
			// caterpillar grows longer
			bodyParts++;
			currScore++;
			// did user set a new high score?
			checkScore();
			// updates current score label on main game panel
			CaterpillarFrame.updateCurrScore(currScore);
			// moves apple to new random spot on game grid panel
			moveApple();
		}
	}

	/**
	 * Finds the new high score set by the user
	 * 
	 * @return String maxScore
	 * 
	 */
	public String getMaxScore() {
		String max = "0";
		try {
			// uses Scanner to find new high score set by user
			Scanner findMax = new Scanner(scoreFile);
			if (findMax.hasNext()) {
				max = findMax.next();
			}
			findMax.close();
		} catch (FileNotFoundException e) {
		}
		return max;
	}

	/**
	 * Class to handle the movement of the caterpillar using the direction keys
	 * 
	 * @extends KeyAdapter
	 * 
	 */
	private class MyKeyAdapter extends KeyAdapter {
		/**
		 * Handles the movement of caterpillar upon keys being pressed
		 * 
		 * @param KeyEvent e
		 * @Override
		 * @extends KeyAdapter
		 */
		public void keyPressed(KeyEvent e) {
			// gets the key that the user pressed
			int userChoice = e.getKeyCode();
			// prevents the caterpillar from turning in on itself
			if (userChoice == KeyEvent.VK_LEFT) {
				if (direction != 'R') {
					direction = 'L';
				}
			} else if (userChoice == KeyEvent.VK_RIGHT) {
				if (direction != 'L') {
					direction = 'R';
				}
			} else if (userChoice == KeyEvent.VK_DOWN) {
				if (direction != 'U') {
					direction = 'D';
				}
			} else if (userChoice == KeyEvent.VK_UP) {
				if (direction != 'D') {
					direction = 'U';
				}
			}
		}
	}
}
