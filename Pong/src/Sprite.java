import java.awt.Color;
import java.awt.Rectangle;

public class Sprite {

	private int xPosition, yPosition, xVelocity, yVelocity, width, height;
	private int initialXPosition, initialYPosition;
	private Color colour;
	
	//getter methods
	public int getXPosition() {
		return xPosition;
	}
	
	public int getYPosition() {
		return yPosition;
	}
	
	public int getXVelocity() {
		return xVelocity;
	}
	
	public int getYVelocity() {
		return yVelocity;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Color getColour() {
		return colour;
	}
	
	//setter methods
	public void setXPosition(int newXPosition) {
		this.xPosition = newXPosition;
	}
	
	public void setYPosition(int newYPosition) {
		this.yPosition = newYPosition;
	}
	
	
	
	
	 public void setXPosition(int newX, int panelWidth) {
		xPosition = newX;
		 if (xPosition + width > panelWidth) {
			 xPosition = panelWidth - width;
		 }else if (newX < 0) {
			 xPosition = 0;
		 }
	
	 }
	 
	 
	 
	 
	 public void setYPosition(int newY, int panelHeight) {
	     yPosition = newY;
	     if (yPosition + height > panelHeight) {
			 yPosition = panelHeight - height;
		 }else if (yPosition < 0) {
			 yPosition = 0;
		 }	
	
	 }
	
	
	
	
	public void setXVelocity(int newXVelocity) {
		this.xVelocity = newXVelocity;
	}
	
	public void setYVelocity(int newYVelocity) {
		this.yVelocity = newYVelocity;
	}
	
	public void setWidth(int newWidth) {
		this.width = newWidth;
	}
	
	public void setHeight(int newHeight) {
		this.height = newHeight;
	}
	
	public void setColour(Color newColour) {
		this.colour = newColour;
	}
	
	
	
	public void setInitialPosition(int initialX, int initialY) 	{
		initialXPosition = initialX;
		initialYPosition = initialY;
	}
	
	public void resetToInitialPosition() {
		setXPosition(initialXPosition);
		setYPosition(initialYPosition);
	}
	
	
	public Rectangle getRectangle() {
		return new Rectangle(getXPosition(), getYPosition(), getWidth(), getHeight());
	}
	
}
