import java.awt.Color;
import java.awt.Graphics;

/**
 * A rectangle-shaped Shape
 * Defined by an upper-left corner (x1,y1) and a lower-right corner (x2,y2)
 * with x1<=x2 and y1<=y2
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, updated Fall 2016
 */
public class Rectangle implements Shape {
	// TODO: YOUR CODE HERE
	private int x1, x2, y1, y2; //points of a rectangle
	private Color color; //its color

	public Rectangle(int x1, int y1, Color color) {
		this.x1 = x1; this.x2 = x1;
		this.y1 = y1; this.y2 = y1;
		this.color = color;
	}

	public void setCorners(int x1, int y1, int x2, int y2) {
		//use Math.min and max to make sure the corners are set up correctly
		this.x1 = Math.min(x1, x2);
		this.y1 = Math.min(y1, y2);
		this.x2 = Math.max(x1, x2);
		this.y2 = Math.max(y1, y2);
	}

	public Rectangle(int x1, int y1, int x2, int y2, Color color) { //use setCorners to set up a rectangle given 4 coordinates
		setCorners(x1, y1, x2, y2);
		this.color = color;
	}

	@Override
	public void moveBy(int dx, int dy) {
		x1 += dx; y1 += dy;
		x2 += dx; y2 += dy;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}
		
	@Override
	public boolean contains(int x, int y) {
		return x1 <= x && x <= x2 && y1 <= y && y <= y2;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x1, y1, x2-x1, y2-y1);
	}

	public String toString() {
		return "rectangle "+x1+" "+y1+" "+x2+" "+y2+" " +  color.getRGB();
	}
}
