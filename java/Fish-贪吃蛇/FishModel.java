package Fish;

import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

class FishModel extends Observable implements Runnable {
	boolean[][] matrix;
	LinkedList nodeArray = new LinkedList(); 
	Node food1;
	Node food2;
	int maxX;
	int maxY;
	int direction = 2; 
	boolean running = false; 

	int timeInterval = 200; 
	double speedChangeRate = 0.95; 
	boolean paused = false; 

	int score = 0; 
	int countMove = 0; 

	public static final int UP = 2;
	public static final int DOWN = 4;
	public static final int LEFT = 1;
	public static final int RIGHT = 3;

	public FishModel(int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;

		reset();
	}

	public void reset() {
		direction = FishModel.UP; 
		timeInterval = 200; 
		paused = false; 
		countMove = 0; 

		matrix = new boolean[maxX][];
		for (int i = 0; i < maxX; ++i) {
			matrix[i] = new boolean[maxY];
			Arrays.fill(matrix[i], false);
		}

		int initArrayLength = maxX > 20 ? 10 : maxX / 2;
		nodeArray.clear();
		for (int i = 0; i < initArrayLength; ++i) {
			int x = maxX / 2 + i;
			int y = maxY / 2; 
			nodeArray.addLast(new Node(x, y));
			matrix[x][y] = true;
		}

		food1 = createFood();
		food2 = createFood();
		matrix[food1.x][food1.y] = true;
		matrix[food2.x][food2.y] = true;
	}

	public void changeDirection(int newDirection) {
		if (direction % 2 != newDirection % 2) {
			direction = newDirection;
		}
	}

	public boolean moveOn() {
		Node n = (Node) nodeArray.getFirst();
		int x = n.x;
		int y = n.y;

		switch (direction) {
		case UP:
			y--;
			break;
		case DOWN:
			y++;
			break;
		case LEFT:
			x--;
			break;
		case RIGHT:
			x++;
			break;
		}

		if ((0 <= x && x < maxX) && (0 <= y && y < maxY)) {

			if (matrix[x][y]) { 
				if (x == food1.x && y == food1.y) { 
					nodeArray.addFirst(food1); 
                    speedUp();


					int scoreGet = (10000 - 200 * countMove) / timeInterval;
					score += scoreGet > 0 ? scoreGet : 10;
					countMove = 0;

					food1 = createFood(); 
					matrix[food1.x][food1.y] = true; 
					return true;
				} 
				if (x == food2.x && y == food2.y) { 
					nodeArray.addFirst(food2); 
                    speedUp();

					int scoreGet = (10000 - 200 * countMove) / timeInterval;
					score += scoreGet > 0 ? scoreGet : 10;
					countMove = 0;

					food2 = createFood(); 
					matrix[food2.x][food2.y] = true; 
					return true;
				}
				else
					return false;

			} else { 
				nodeArray.addFirst(new Node(x, y));
				matrix[x][y] = true;
				n = (Node) nodeArray.removeLast();
				matrix[n.x][n.y] = false;
				countMove++;
				return true;
			}
		}
		return false; 
	}

	public void run() {
		running = true;
		while (running) {
			try {
				Thread.sleep(timeInterval);
			} catch (Exception e) {
				break;
			}

				if (moveOn()) {
					setChanged(); 
					notifyObservers();
				} else {
					JOptionPane.showMessageDialog(null, "ÓãËÀÁË",
							"ÓÎÏ·½áÊø", JOptionPane.INFORMATION_MESSAGE);
					break;
				}
		}
		running = false;
	}

	private Node createFood() {
		int x = 0;
		int y = 0;
		do {
			Random r = new Random();
			x = r.nextInt(maxX);
			y = r.nextInt(maxY);
		} while (matrix[x][y]);

		return new Node(x, y);
	}

	public void speedUp() {
		if(timeInterval>=100){
		timeInterval *= speedChangeRate;
		}
	}

	public void speedDown() {
		timeInterval /= speedChangeRate;
	}

	public void changePauseState() {
		paused = !paused;
	}

	public String toString() {
		String result = "";
		for (int i = 0; i < nodeArray.size(); ++i) {
			Node n = (Node) nodeArray.get(i);
			result += "[" + n.x + "," + n.y + "]";
		}
		return result;
	}
}

class Node {
	int x;
	int y;

	Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
}