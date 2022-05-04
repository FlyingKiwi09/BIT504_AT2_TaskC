import java.awt.Color;

public class Ball extends Sprite{

	private final static Color BALL_COLOUR = Color.BLACK;
	private final static int WIDTH = 25;
	private final static int HEIGHT = 25;
	
	//constructor
	
	public Ball(int panelWidth, int panelHeight) {
		setColour(BALL_COLOUR);
		setWidth(HEIGHT);
		setHeight(WIDTH);
		setInitialPosition(panelWidth / 2 - (getWidth() / 2), panelHeight / 2 - (getHeight() / 2));
		
		resetToInitialPosition();
		
	}
}
