import java.awt.Color;

public class Paddle extends Sprite {
	private static final int WIDTH = 10;
	private static final int HEIGHT = 100;
	private static final Color PADDLE_COLOUR = Color.BLACK;
	private static final int DISTANCE_FROM_EDGE = 40;
	
	
	//class constructor
	public Paddle(int panelWidth, int panelHeight, Player player) {
		setColour(PADDLE_COLOUR);
		setWidth(WIDTH);
		setHeight(HEIGHT);
		int xPos;
		
		if(player == Player.ONE) {
			xPos = DISTANCE_FROM_EDGE;
		}else {
			xPos = panelWidth - DISTANCE_FROM_EDGE - getWidth();
		}
		
		setInitialPosition(xPos, panelHeight / 2 - (getHeight() / 2));
	
		resetToInitialPosition();
		
	}
	
}
