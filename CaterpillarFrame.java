
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 3647751031335543569L;
	private static final Color MAIN_SCREEN_COLOR = new Color(0x50C878);
	private static final int IMAGE_SCALE = 80;
	private static final int MAIN_FRAME_SIZE = 800;
	private static final int FONT_SIZE = 20;
	private static final Image FOOD_IMAGE = new ImageIcon("Apple.png").getImage()
			.getScaledInstance(IMAGE_SCALE, IMAGE_SCALE, Image.SCALE_SMOOTH);
	private static final Image HIGH_SCORE_IMAGE = new ImageIcon("Trophy.png").getImage()
			.getScaledInstance(IMAGE_SCALE, IMAGE_SCALE, Image.SCALE_SMOOTH);
	private CaterpillarPanel caterpillarPanel;
	private static final int CATERPILLAR_IMAGE_SIZE = 110;
	private static final Image CATERPILLAR = new ImageIcon("Caterpillar.png").getImage()
			.getScaledInstance(CATERPILLAR_IMAGE_SIZE, CATERPILLAR_IMAGE_SIZE, Image.SCALE_SMOOTH);

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
		setupMainScoreLabels();
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
	private void setupMainScoreLabels() {
		final int maxScoreLabel_X = 120;
		final int currScoreLabel_Y = 40;
		final int label_Y = 20;
		final int LABEL_SIZE = 100;

		// finds highest score in file to keep track of
		maxScoreLabel.setText(caterpillarPanel.getMaxScore());
		maxScoreLabel.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));
		maxScoreLabel.setIcon(new ImageIcon(HIGH_SCORE_IMAGE));
		maxScoreLabel.setBackground(MAIN_SCREEN_COLOR);
		maxScoreLabel.setBounds(maxScoreLabel_X, label_Y, LABEL_SIZE, LABEL_SIZE);

		currScoreLabel.setText(0 + "");
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
		maxScoreSetup();
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
		frame.getContentPane().setBackground(MAIN_SCREEN_COLOR);
		frame.setIconImage(CATERPILLAR);
		frame.setSize(LAUNCH_FRAME_WIDTH, LAUNCH_FRAME_HEIGHT);
		frame.setTitle("The Caterpillar Game");
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		label.setBounds(CATERPILLAR_X, CATERPILLAR_Y, CATERPILLAR_IMAGE_SIZE,
				CATERPILLAR_IMAGE_SIZE);
		label.setBackground(new Color(0x50C878));
		label.setIcon(new ImageIcon(CATERPILLAR));
		label.setOpaque(true);
		frame.add(label);
		currentScoreSetup();
		playButtonSetup();
	}

	/**
	 * creates a new Huff Compressor
	 * 
	 * @param in           input stream
	 * @param headerFormat the constant to see what header format we should use
	 * @throws IOException
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
	 * creates a new Huff Compressor
	 * 
	 * @param in           input stream
	 * @param headerFormat the constant to see what header format we should use
	 * @throws IOException
	 */
	private void currentScoreSetup() {
		currScoreLabel.setBounds(40, FONT_SIZE, 100, 100);
		currScoreLabel.setText(0 + "");
		currScoreLabel.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));
		currScoreLabel.setVerticalTextPosition(JLabel.BOTTOM);
		currScoreLabel.setHorizontalTextPosition(JLabel.CENTER);
		currScoreLabel.setIcon(new ImageIcon(FOOD_IMAGE));
		currScoreLabel.setBackground(new Color(0x50C878));
		currScoreLabel.setOpaque(true);
		frame.add(currScoreLabel);
	}

	/**
	 * creates a new Huff Compressor
	 * 
	 * @param in           input stream
	 * @param headerFormat the constant to see what header format we should use
	 * @throws IOException
	 */
	private void maxScoreSetupAgain() throws IOException {
		maxScoreLabel.setBounds(210, FONT_SIZE, 100, 100);
		maxScoreLabel.setText(caterpillarPanel.getMaxScore());
		maxScoreLabel.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));
		maxScoreLabel.setVerticalTextPosition(JLabel.BOTTOM);
		maxScoreLabel.setHorizontalTextPosition(JLabel.CENTER);
		maxScoreLabel.setIcon(new ImageIcon(HIGH_SCORE_IMAGE));
		maxScoreLabel.setBackground(new Color(0x50C878));
		maxScoreLabel.setOpaque(true);
		frame.add(maxScoreLabel);
	}

	/**
	 * creates a new Huff Compressor
	 * 
	 * @param in           input stream
	 * @param headerFormat the constant to see what header format we should use
	 * @throws IOException
	 */
	private void maxScoreSetup() throws IOException {
		maxScoreLabel.setBounds(210, FONT_SIZE, 100, 100);
		maxScoreLabel.setText("0");
		maxScoreLabel.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));
		maxScoreLabel.setVerticalTextPosition(JLabel.BOTTOM);
		maxScoreLabel.setHorizontalTextPosition(JLabel.CENTER);
		maxScoreLabel.setIcon(new ImageIcon(HIGH_SCORE_IMAGE));
		maxScoreLabel.setBackground(new Color(0x50C878));
		maxScoreLabel.setOpaque(true);
		frame.add(maxScoreLabel);

	}

	/**
	 * creates a new Huff Compressor
	 * 
	 * @param in           input stream
	 * @param headerFormat the constant to see what header format we should use
	 * @throws IOException
	 */
	private void playButtonSetup() {
		button.setBounds(70, 300, 200, 50);
		button.setText("Play");
		button.setFont(new Font("Calibri", Font.BOLD, FONT_SIZE));
		Image playLogo = new ImageIcon("Play button.png").getImage().getScaledInstance(35, 35,
				Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(playLogo));
		button.setFocusable(false);
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(0x0000FF));
		frame.add(button);
		button.addActionListener(this);
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
	 * creates a new Huff Compressor
	 * 
	 * @param in           input stream
	 * @param headerFormat the constant to see what header format we should use
	 * @throws IOException
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			frame.dispose();
			playMainWindow();
		}

	}
}