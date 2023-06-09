import java.io.IOException;

public class CaterpillarGame {

	public CaterpillarGame() throws IOException {
		// launches the Caterpillar game from the beginning
		new CaterpillarFrame().launchGame();

	}

	public static void main(String[] args) throws IOException {
		// calls on constructor (Java executes the main method before a class' constructor)
		new CaterpillarGame();
	}

}
