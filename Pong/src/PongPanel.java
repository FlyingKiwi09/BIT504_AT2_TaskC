import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;


public class PongPanel extends JPanel implements ActionListener, KeyListener{
	
	private final static Color BACKGROUND_COLOUR = Color.YELLOW;
	private final static int TIMER_DELAY = 5;
	private final static int BALL_MOVEMENT_SPEED = 2;
	Ball ball;
	Paddle paddle1;
	Paddle paddle2;
	
	//variables for scores and wins
	private final static int POINTS_TO_WIN = 3;
	int player1Score = 0, player2Score = 0;
	Player gameWinner;
	
	//variables for painting scores
	private final static int SCORE_TEXT_X = 100;
	private final static int SCORE_TEXT_Y = 100;
	private final static int SCORE_FONT_SIZE = 50;
	private final static String SCORE_FONT_FAMILY = "Serif";
	
	GameState gameState = GameState.INITIALISING;
	
	//class constructor
		public PongPanel () {
			setBackground(BACKGROUND_COLOUR);
			Timer timer = new Timer(TIMER_DELAY, this);
				timer.start();
				addKeyListener(this);
				setFocusable(true);
		}
		
	public void createObjects() {
		ball = new Ball(getWidth(), getHeight());
		paddle1 = new Paddle(getWidth(), getHeight(), Player.ONE);
		paddle2 = new Paddle(getWidth(), getHeight(), Player.TWO);
	}
		
		
	private void update () {
		
		switch(gameState) {
			case INITIALISING: {
				createObjects();
				gameState = GameState.PLAYING;
				ball.setYVelocity(BALL_MOVEMENT_SPEED);
				ball.setXVelocity(BALL_MOVEMENT_SPEED);
				break;
			}
			case PLAYING: {
				moveObject(paddle1);
				moveObject(paddle2);
				moveObject(ball);
				checkWallBounce();
				checkPaddleBounce();
				checkWin();
				break;
			}
			case GAMEOVER: {
				
				break;
			}
		}
		
	}
	
	private void paintSprite(Graphics g, Sprite sprite) {
		g.setColor(sprite.getColour());
		g.fillRect(sprite.getXPosition(), sprite.getYPosition(), sprite.getWidth(), sprite.getHeight());
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		update();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintDottedLine(g);
		if(gameState != GameState.INITIALISING) {
			paintSprite(g, ball);
			paintSprite(g, paddle1);
			paintSprite(g, paddle2);
			paintScores(g);
			paintWin(g);
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		//up & down movement for player 2
		if(event.getKeyCode()== KeyEvent.VK_UP) {
			paddle2.setYVelocity(-2);
		}else if(event.getKeyCode() == KeyEvent.VK_DOWN) {
			paddle2.setYVelocity(2);
		}
		
		//up & down movement for player 1
		if(event.getKeyCode() == KeyEvent.VK_W) {
			paddle1.setYVelocity(-2);
		}else if(event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setYVelocity(2);
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// stop movement for player 2
		if(event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
			paddle2.setYVelocity(0);
		}
		
		//stop movement for player 1
		if(event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setYVelocity(0);
		}
		
	}
	
	private void paintDottedLine(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {9}, 0);
		g2d.setStroke(dashed);
		g2d.setPaint(Color.BLACK);
		g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		g2d.dispose();
	}
	
	private void moveObject(Sprite sprite) {
		int newXPosition, newYPosition;
		
		newXPosition = sprite.getXPosition() + sprite.getXVelocity();
		sprite.setXPosition(newXPosition, getWidth());
		
		newYPosition = sprite.getYPosition() + sprite.getYVelocity();
		sprite.setYPosition(newYPosition, getHeight());
	
	}
	
	
	private void checkWallBounce() {
		int currentYPosition = ball.getYPosition();
		int currentXPosition = ball.getXPosition();
		
		   if ((currentYPosition  >= getHeight() - ball.getHeight()) || (currentYPosition <= 0)) {
				 ball.setYVelocity(-ball.getYVelocity());
			 }
		   
		   if ((currentXPosition  >= getWidth() - ball.getWidth()) ){
			   addScore(Player.TWO);	
			   ball.setXVelocity(-ball.getXVelocity());
			   resetBall();
		   } else if (currentXPosition <= 0) {
			   addScore(Player.ONE);
			   ball.setXVelocity(-ball.getXVelocity());
			   resetBall();
		   }
	}
	
	
	private void checkPaddleBounce() {
		if(ball.getXVelocity() <0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
			ball.setXVelocity(BALL_MOVEMENT_SPEED);
		}
		if(ball.getXVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
			ball.setXVelocity(-BALL_MOVEMENT_SPEED);
		}
	}
	
	
	private void addScore(Player player) {
		if (player == Player.ONE) {
			player1Score++;
		}else if (player == Player.TWO) {
			player2Score++;
		}
	}
	
	
	private void checkWin() {
		if (player1Score >= POINTS_TO_WIN) {
			gameWinner = Player.ONE;
			gameState = GameState.GAMEOVER;	
		} else if (player2Score >= POINTS_TO_WIN) {
			gameWinner = Player.TWO;
			gameState = GameState.GAMEOVER;	
		}
	}
	
	private void paintScores(Graphics g) {
		Font scoreFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
		String leftScore = Integer.toString(player1Score);
		String rightScore = Integer.toString(player2Score);
		g.setFont(scoreFont);
		g.drawString(leftScore, SCORE_TEXT_X, SCORE_TEXT_Y);
		g.drawString(rightScore, getWidth()-SCORE_TEXT_X, SCORE_TEXT_Y);
	}
	
	private void paintWin(Graphics g) {
		Font winFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
		String winMessage = "WIN!";
		g.setFont(winFont);
		if(gameWinner == Player.ONE) {
			g.drawString(winMessage, (SCORE_TEXT_X*2), SCORE_TEXT_Y*2);
		} else if (gameWinner == Player.TWO) {
			g.drawString(winMessage, getWidth()-(SCORE_TEXT_X*2), SCORE_TEXT_Y*2);
		}
	}
	
	private void resetBall() {
		ball.resetToInitialPosition();
	}
	
}
