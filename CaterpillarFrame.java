
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CaterpillarFrame extends JFrame implements ActionListener {

	// had to include this to remove warning from Java
	private static final long serialVersionUID = 3647751031335543569L;
	private static final Color MAIN_SCREEN_COLOR = new Color(0x50C878);
	private static final int IMAGE_SCALE = 80;
	private static final int MAIN_FRAME_SIZE = 800;
	private static final int FONT_SIZE = 20;
	private static final int CATERPILLAR_IMAGE_SIZE = 110;
	private static final Image FOOD_IMAGE = new ImageIcon("Apple.png").getImage()
			.getScaledInstance(IMAGE_SCALE, IMAGE_SCALE, Image.SCALE_SMOOTH);
	private static final Image HIGH_SCORE_IMAGE = new ImageIcon("Trophy.png").getImage()
			.getScaledInstance(IMAGE_SCALE, IMAGE_SCALE, Image.SCALE_SMOOTH);
	private static final Image CATERPILLAR = new ImageIcon("Caterpillar.png").getImage()
			.getScaledInstance(CATERPILLAR_IMAGE_SIZE, CATERPILLAR_IMAGE_SIZE, Image.SCALE_SMOOTH);
	private static final int LABEL_SIZE = 100;
	private CaterpillarPanel caterpillarPanel;
	private static JLabel currScoreLabel;
	private static JLabel maxScoreLabel;
	private JLabel label;
	private static JFrame frame;
	private JButton button;

	public CaterpillarFrame() throws FileNotFoundException {
		frame = new JFrame();
		button = new JButton();
		label = new JLabel();
		currScoreLabel = new JLabel();
		maxScoreLabel = new JLabel();
		caterpillarPanel = new CaterpillarPanel();
	}

	/**
	 * Creates the main screen (frame) where the user plays the game on
	 * 
	 * @return void
	 */
	private void playMainWindow() {
		this.setTitle("The Caterpillar Game");
		this.setSize(MAIN_FRAME_SIZE, MAIN_FRAME_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(MAIN_SCREEN_COLOR);
		// allows components on the frame to be moved anywhere
		this.setLayout(null);
		this.setResizable(false);
		// sets the frame to center of the screen when the user first launches the game
		this.setLocationRelativeTo(null);
		// sets up the labels that will go on the main game screen
		setupMainGameScreenScores();
		this.add(currScoreLabel);
		this.add(maxScoreLabel);
		this.add(caterpillarPanel);
		this.setVisible(true);
	}

	/**
	 * Sets up the current score and high score labels on the main game frame
	 * 
	 * @return void
	 */
	private void setupMainGameScreenScores() {
		final int maxScoreLabel_X = 120;
		final int currScoreLabel_Y = 40;
		final int label_Y = 20;
		final int CURR_SCORE_AT_START_OF_ROUND = 0;

		// finds highest score in file to keep track of
		maxScoreLabel.setText(caterpillarPanel.getMaxScore());
		maxScoreLabel.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));
		maxScoreLabel.setIcon(new ImageIcon(HIGH_SCORE_IMAGE));
		maxScoreLabel.setBackground(MAIN_SCREEN_COLOR);
		maxScoreLabel.setBounds(maxScoreLabel_X, label_Y, LABEL_SIZE, LABEL_SIZE);

		currScoreLabel.setText(CURR_SCORE_AT_START_OF_ROUND + "");
		currScoreLabel.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));
		currScoreLabel.setIcon(new ImageIcon(FOOD_IMAGE));
		currScoreLabel.setBackground(MAIN_SCREEN_COLOR);
		currScoreLabel.setBounds(currScoreLabel_Y, label_Y, LABEL_SIZE, LABEL_SIZE);
	}

	/**
	 * Launches the game if a player wants to achieve a greater high score and/or wants to try
	 * again at the game in one sitting
	 * 
	 * @return void
	 * @throws IOException
	 */
	public void launchGameAgain() throws IOException {
		setupFrameLaunchGame();
		// takes in current high score from file so user knows what score it needs to beat
		maxScoreSetupAgain();
		frame.setVisible(true);

	}

	/**
	 * Launches the main game screen the first time the user wants to play the game
	 * 
	 * @return void
	 * @throws IOException
	 */
	public void launchGame() throws IOException {
		// resets the whole file so that a high score is not carried over to another sitting of
		// the gamw
		resetMaxScoreAtStart();
		setupFrameLaunchGame();
		maxScoreForLaunchScreen();
		frame.setVisible(true);
	}

	/**
	 * Sets up the components of the frame for the launch game screen
	 * 
	 * @return void
	 */
	private void setupFrameLaunchGame() {
		final int CATERPILLAR_X = 110;
		final int CATERPILLAR_Y = 150;
		final int LAUNCH_FRAME_WIDTH = 350;
		final int LAUNCH_FRAME_HEIGHT = 450;
		frame.setTitle("The Caterpillar Game");
		frame.getContentPane().setBackground(MAIN_SCREEN_COLOR);
		frame.setIconImage(CATERPILLAR);
		frame.setSize(LAUNCH_FRAME_WIDTH, LAUNCH_FRAME_HEIGHT);
		// allows components to be placed anywhere on the frame
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// when user launches screen, frame is automatically placed center in the screen
		frame.setLocationRelativeTo(null);
		label.setBounds(CATERPILLAR_X, CATERPILLAR_Y, CATERPILLAR_IMAGE_SIZE,
				CATERPILLAR_IMAGE_SIZE);
		label.setBackground(MAIN_SCREEN_COLOR);
		label.setIcon(new ImageIcon(CATERPILLAR));
		label.setOpaque(true);
		frame.add(label);
		currentScoreForLaunchScreen();
		playButtonSetup();
	}

	/**
	 * flushes output stream to make sure max score does not carry over between different
	 * sittings of user playing the game
	 * 
	 * @return void
	 */
	private void resetMaxScoreAtStart() {
		FileWriter fw;
		try {
			fw = new FileWriter("maxScore.txt");
			fw.flush();
			fw.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Sets up the current score label for the launch and main game screens
	 * 
	 * @return void
	 */
	private void currentScoreForLaunchScreen() {
		final int LABEL_X = 40;
		final int LABEL_Y = 20;
		final int CURR_SCORE_AT_START = 0;
		currScoreLabel.setText(CURR_SCORE_AT_START + "");
		currScoreLabel.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));
		currScoreLabel.setVerticalTextPosition(JLabel.BOTTOM);
		currScoreLabel.setHorizontalTextPosition(JLabel.CENTER);
		currScoreLabel.setIcon(new ImageIcon(FOOD_IMAGE));
		currScoreLabel.setBackground(MAIN_SCREEN_COLOR);
		currScoreLabel.setBounds(LABEL_X, LABEL_Y, LABEL_SIZE, LABEL_SIZE);
		currScoreLabel.setOpaque(true);
		frame.add(currScoreLabel);
	}

	/**
	 * Sets up the high score label for the main game screen
	 * 
	 * @throws IOException
	 * @return void
	 */
	private void maxScoreSetupAgain() throws IOException {
		final int LABEL_X = 210;
		final int LABEL_Y = 20;
		maxScoreLabel.setText(caterpillarPanel.getMaxScore());
		maxScoreLabel.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));
		maxScoreLabel.setVerticalTextPosition(JLabel.BOTTOM);
		maxScoreLabel.setHorizontalTextPosition(JLabel.CENTER);
		maxScoreLabel.setIcon(new ImageIcon(HIGH_SCORE_IMAGE));
		maxScoreLabel.setBackground(MAIN_SCREEN_COLOR);
		maxScoreLabel.setBounds(LABEL_X, LABEL_Y, LABEL_SIZE, LABEL_SIZE);
		maxScoreLabel.setOpaque(true);
		frame.add(maxScoreLabel);
	}

	/**
	 * Sets up the high score label for the launch screen
	 * 
	 * @return void
	 */
	private void maxScoreForLaunchScreen() throws IOException {
		final int LABEL_X = 210;
		final int LABEL_Y = 20;
		final int MAX_SCORE_AT_LAUNCH = 0;
		maxScoreLabel.setText(MAX_SCORE_AT_LAUNCH + "");
		maxScoreLabel.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));
		maxScoreLabel.setVerticalTextPosition(JLabel.BOTTOM);
		maxScoreLabel.setHorizontalTextPosition(JLabel.CENTER);
		maxScoreLabel.setBackground(MAIN_SCREEN_COLOR);
		maxScoreLabel.setIcon(new ImageIcon(HIGH_SCORE_IMAGE));
		maxScoreLabel.setBounds(LABEL_X, LABEL_Y, LABEL_SIZE, LABEL_SIZE);
		maxScoreLabel.setOpaque(true);
		frame.add(maxScoreLabel);

	}

	/**
	 * Sets up the current score label for the launch and main game screens
	 * 
	 * @return void
	 */
	private void playButtonSetup() {
		final int IMAGE_SIZE = 35;
		final int BUTTON_X = 70;
		final int BUTTON_Y = 300;
		final int BUTTON_WIDTH = 200;
		final int BUTTON_HEIGHT = 50;
		final Color BLUE = new Color(0x0000FF);
		button.setText("Play");
		button.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));
		Image playLogo = new ImageIcon("Play button.png").getImage().getScaledInstance(IMAGE_SIZE,
				IMAGE_SIZE, Image.SCALE_SMOOTH);
		button.setForeground(Color.WHITE);
		button.setBackground(BLUE);
		button.setIcon(new ImageIcon(playLogo));
		button.setBounds(BUTTON_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		button.setFocusable(false);
		button.addActionListener(this);
		frame.add(button);
	}

	/**
	 * updates the high score of the user by resetting the text on the high score label
	 * 
	 * @param int maxScore
	 * @return void
	 */
	public static void updateMaxScore(int maxScore) {
		maxScoreLabel.setText(maxScore + "");
	}

	/**
	 * updates the current score of the user by resetting the text on the current score label
	 * 
	 * @param int currentScore
	 * @return void
	 */
	public static void updateCurrScore(int currScore) {
		currScoreLabel.setText(currScore + "");
	}

	/**
	 * If user wants to play the game, the launch screen closes and the main game screen
	 * window opens
	 * 
	 * @param ActionEvent e
	 * @return void
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			frame.dispose();
			playMainWindow();
		}

	}
}