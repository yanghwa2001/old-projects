import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Handles communication to/from the server for the editor
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Chris Bailey-Kellogg; overall structure substantially revised Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 */
public class EditorCommunicator extends Thread {
	private PrintWriter out;		// to server
	private BufferedReader in;		// from server
	protected Editor editor;		// handling communication for

	/**
	 * Establishes connection and in/out pair
	 */
	public EditorCommunicator(String serverIP, Editor editor) {
		this.editor = editor;
		System.out.println("connecting to " + serverIP + "...");
		try {
			Socket sock = new Socket(serverIP, 4242);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("...connected");
		}
		catch (IOException e) {
			System.err.println("couldn't connect");
			System.exit(-1);
		}
	}

	/**
	 * Sends message to the server
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 */
	public void run() {
		try {
			// Handle messages
			String input = in.readLine();
			String line;				
			syncDraw(input);
			while ((line = in.readLine()) != null) {
				System.out.println("received " + line);
				lineToList(line);
				editor.repaint();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("server hung up");
		}
	}	

	// Send editor requests to the server
	public void lineToList(String s) {
		if (!s.equals("")) {
			String[] split = s.split(",");
			if (split[0].equals("add")) {
				handleAddingShape(split);
			}
			if (split[0].equals("delete")) {
				handleDeletingShape(split);
			}
			if (split[0].equals("recolor")) {
				handleRecoloringShape(split);
			}
			if (split[0].equals("move")) {
				handleMovingShape(split);
			}
		}
		editor.repaint();
	}

	synchronized public void handleAddingShape(String[] stringList) {
		int id = Integer.parseInt(stringList[1]);
		String shapeType = stringList[2];
		Shape shape = null;
		if (shapeType.equals("ellipse") || shapeType.equals("rectangle") || shapeType.equals("segment")) {
			shape = shapesHelper(stringList, shapeType, 1);
		}
		else if (shapeType.equals("freehand")) {
			shape = polylineHelper(stringList, 1);
		}
		if (shape != null) {
			editor.getSketch().getShapeMap().put(id, shape);
		}
	}

	synchronized public void handleDeletingShape(String[] stringList) {
		int id = Integer.parseInt(stringList[1]);
		editor.getSketch().getShapeMap().remove(id);
		editor.repaint();
	}

	synchronized public void handleRecoloringShape(String[] stringList) {
		int id = Integer.parseInt(stringList[1]);
		int rgb = Integer.parseInt(stringList[2]);
		Shape s = editor.getSketch().getShapeMap().get(id);
		if (s != null) {
			s.setColor(new Color(rgb));
			editor.getSketch().setShapeID(id, s);
		}
	}

	synchronized public void handleMovingShape(String[] stringList) {
		int id = Integer.parseInt(stringList[1]);
		int dx = Integer.parseInt(stringList[2]);
		int dy = Integer.parseInt(stringList[3]);
		editor.getSketch().getShapeMap().get(id).moveBy(dx, dy);
	}

	/**
	 *
	 * Helper for drawing polylines
	 */
	private Shape polylineHelper(String[] strings, int i){
		int n = (strings.length-3-i)/2;
		int[] pointsX = new int[n];
		int[] pointsY = new int[n];
		int j = 2;
		while (j < strings.length-1) {
			int x = Integer.parseInt(strings[j]);
			pointsX[(j-2-i)/2] = x;
			int y = Integer.parseInt(strings[j+1]);
			pointsY[(j-2-i)/2] = y;
			j += 2;
		}
		int rgb = Integer.parseInt((strings[(strings.length) - 1]));
		Color c = new Color(rgb);
		return new Polyline(pointsX, pointsY, c);
	}

	/**
	 * Helper for drawing shapes
	 */
	private Shape shapesHelper(String[] strings, String s, int i) {
		Shape shape = null;
		int x1 = Integer.parseInt(strings[2 + i]);
		int y1 = Integer.parseInt(strings[3 + i]);
		int x2 = Integer.parseInt(strings[4 + i]);
		int y2 = Integer.parseInt(strings[5 + i]);
		int rgb = Integer.parseInt(strings[6 + i]);
		if (s.equals("ellipse")) { //creates ellipse, rectangle, or segment depending on the given string
			Color c = new Color(rgb);
			shape = new Ellipse(x1, y1, x2, y2, c);
		}
		if (s.equals("rectangle")) {
			Color c = new Color(rgb);
			shape = new Rectangle(x1, y1, x2, y2, c);
		}
		if (s.equals("segment")) {
			Color c = new Color(rgb);
			shape = new Segment(x1, y1, x2, y2, c);
		}
		return shape;
	}

	/**
	 * Syncing function
	 */
	private void syncDraw(String s) {
		if (!s.equals("")) {
			System.out.println("syncing");
			String[] split = s.split(" ");
			for (String str : split) {
				String[] strings = str.split(",");
				int id = Integer.parseInt(strings[0]);
				String shape = strings[1];
				if (shape.equals("polyline")) { //polyline requires a different approach from other shapes
					Shape s1 = polylineHelper(strings, 0);
					editor.getSketch().setShapeID(id, s1);
				}
				else if (shape.equals("ellipse") || shape.equals("rectangle") || shape.equals("segment")) {//other shapes
					Shape s1 = shapesHelper(strings, shape, 0);
					editor.getSketch().setShapeID(id, s1);
				}
			}
		}
	}
	
}
