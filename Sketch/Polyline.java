import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, updated Fall 2016
 */
public class Polyline implements Shape {
	// TODO: YOUR CODE HERE
	private int[] pointsX, pointsY; //contains the x and y points
	private int points;
	private Color color;

	public Polyline(int[] pointsX, int[] pointsY, Color color) {
		this.pointsX = pointsX;
		this.pointsY = pointsY;
		this.points = pointsX.length;
		this.color = color;
	}

	public void addPoints(int x, int y){ //function to add points to the polyline
		points++;
		int[] tempPoints = pointsX;
		pointsX = new int[points];

		for (int i=0; i < tempPoints.length; i++){
			pointsX[i] = tempPoints[i];
		}
		pointsX[points-1] = x;

		tempPoints = pointsY;
		pointsY = new int[points];
		for (int i=0; i < tempPoints.length; i++){
			pointsY[i] = tempPoints [i];
		}
		pointsY[points-1] = y;
	}

	@Override
	public void moveBy(int dx, int dy) { //move by the given displacement
		for (int i=0; i < points; i++) {
			pointsX[i] += dx;
			pointsY[i] += dy;
		}
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
		for (int i = 0; i < this.points; i++) {
			if (Segment.pointToSegmentDistance(x, y, pointsX[i], pointsY[i], pointsX[i+1], pointsY[i+1]) <= 3) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawPolyline(pointsX, pointsY, points);
	}

	@Override
	public String toString() {
		String string = "polyline ";
		for (int i=0; i < points; i++) {
			string += pointsX[i] + " " + pointsY[i] + " ";
		}
		string += color.getRGB();

		return string;
	}
}
