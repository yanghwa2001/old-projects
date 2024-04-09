import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Handles communication between the server and one client, for SketchServer
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate SketchServerCommunicator
 */
public class SketchServerCommunicator extends Thread {
	private Socket sock;					// to talk with client
	private BufferedReader in;				// from client
	private PrintWriter out;				// to client
	private SketchServer server;			// handling communication for

	public SketchServerCommunicator(Socket sock, SketchServer server) {
		this.sock = sock;
		this.server = server;
	}

	/**
	 * Sends a message to the client
	 * @param msg
	 */
	public void send(String msg) {
		out.println(msg);
	}
	
	/**
	 * Keeps listening for and handling (your code) messages from the client
	 */
	public void run() {
		try {
			System.out.println("someone connected");
			
			// Communication channel
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);

			// Tell the client the current state of the world
			send(server.getSketch().toString());

			// Keep getting and handling messages from the client
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println("Received request to " + line);
				readLineHelper(line);
			}

			// Clean up -- note that also remove self from server's list so it doesn't broadcast here
			server.removeCommunicator(this);
			out.close();
			in.close();
			sock.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readLineHelper(String str) {
		String[] split = str.split(","); // finds what the command is
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

	synchronized public void handleAddingShape(String[] strings) {
		System.out.println("DRAW mode!");
		Shape shape = null;
		String s = strings[1];
		if (s.equals("ellipse") || s.equals("rectangle") || s.equals("segment")) {
			int x1 = Integer.parseInt(strings[2]);
			int y1 = Integer.parseInt(strings[3]);
			int x2 = Integer.parseInt(strings[4]);
			int y2 = Integer.parseInt(strings[5]);
			int rgb = Integer.parseInt(strings[6]);
			if (s.equals("ellipse")) {
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
		} else {
			int n = (strings.length - 3) / 2;
			int[] xPoints = new int[n];
			int[] yPoints = new int[n];
			int j = 3;
			while (j < strings.length - 1) {
				int x = Integer.parseInt(strings[j]);
				xPoints[(j - 3) / 2] = x;
				int y = Integer.parseInt(strings[j + 1]);
				yPoints[(j - 3) / 2] = y;
				j += 2;
			}
			int rgb = Integer.parseInt((strings[(strings.length) - 1]));
			Color c = new Color(rgb);
			shape = new Polyline(xPoints, yPoints, c);
		}

		if (shape != null) {
			server.getSketch().setShapeID(SketchServer.id, shape);
			server.broadcast("add," + SketchServer.id + "," + server.getSketch().getShapeMap().get(SketchServer.id).toString());
			SketchServer.id++;
		}
		
	}

	synchronized public void handleDeletingShape(String[] strings) {
		System.out.println("delete mode");
		int id = Integer.parseInt(strings[1]);
		Shape shape = server.getSketch().getShapeMap().get(id);
		if (shape != null) {
			server.getSketch().getShapeMap().remove(id);
			server.broadcast("delete," + id);
		}
	}

	public void handleRecoloringShape(String[] strings) {
		System.out.println("recoloring");
		int id = Integer.parseInt(strings[1]);
		int rgb = Integer.parseInt(strings[2]);
		Shape shape = server.getSketch().getShapeMap().get(id);
		if (shape != null) {
			shape.setColor(new Color(rgb));
			server.getSketch().setShapeID(id, shape);
			server.broadcast("recolor," + id + "," + shape.getColor().getRGB());
		}
	}

	synchronized public void handleMovingShape(String[] strings) {
		System.out.println("move mode");
		int id = Integer.parseInt(strings[1]);
		int dx = Integer.parseInt(strings[2]);
		int dy = Integer.parseInt(strings[3]);
		Shape shape = server.getSketch().getShapeMap().get(id);
		if (shape != null) {
			shape.moveBy(dx, dy);
			server.getSketch().setShapeID(id, shape);
			server.broadcast("move," + id + "," + dx + "," + dy);
		}
	}
}
