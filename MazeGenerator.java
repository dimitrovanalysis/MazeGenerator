import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator{
	
class Vertex {
		Vertex[] surroundings; //adjacency list - nearby vertexes
		int[] walls; //array of walls, -100 edge of maze, 0 broken wall, 1 intact wall, 100 entry/exit
		int colValue; // white 0, grey 1, black 2
		Vertex previous; // the previous node
		int shortestPath; // the shortest path between two points,(distance for DFS)
		boolean checkIfInWay; //true if path is in way of solution
		int keepTrackOfOrder; //keeps order of traversal 

		
		public Vertex() {
			surroundings = new Vertex[100];
			walls = new int[100];
			closeAllWall();
			previous = null;
			shortestPath = 0; 
			checkIfInWay = false;
			keepTrackOfOrder = 0;
		}

		public int getVertexStatus(Vertex v) 
		{
			if (getUp() != null && getUp().equals(v)) 
				return 0;
			else if (getRight() != null && getRight().equals(v)) 
				return 1;
			else if (getDown() != null && getDown().equals(v)) 
				return 2;
			else  
				return 3;	
		}
		
		public boolean checkIntactWalls() {
			for (int i = 0; i < walls.length; i++) {
				if (walls[i] == 0) {
					return false;
				}
			}
			return true;
		}
		
		public void closeAllWall() {
			for (int i = 0; i < walls.length; i++) {
				walls[i] = 1;
			}
		}

		public void breakNorthWall() {
			if (walls[0] != -100)
				walls[0] = 0;
		}

		public void breakEastWall() {
			if (walls[1] != -100)
				walls[1] = 0;
		}

		public void breakSouthWall() {
			if (walls[2] != -100)
				walls[2] = 0;
		}

		public void breakWestWall() {
			if (walls[3] != -100)
				walls[3] = 0;
		}

		public void setLeft(Vertex v) {
			surroundings[3] = v;
		}

		public void setRight(Vertex v) {
			surroundings[1] = v;
		}

		public void setUp(Vertex v) {
			surroundings[0] = v;
		}

		public void setDown(Vertex v) {
			surroundings[2] = v;
		}

		public Vertex getLeft() {
			return surroundings[3];
		}

		public Vertex getRight() {
			return surroundings[1];
		}

		public Vertex getUp() {
			return surroundings[0];
		}

		public Vertex getDown() {
			return surroundings[2];
		}

		public void reset()
		{
			colValue = 0;
			previous = null;
			shortestPath = 0; 
			checkIfInWay = false;
			keepTrackOfOrder = 0;
		}

	}//vertex ends here


Vertex mazeElementList[][];  //2dList of elements in maze
int eleCount; //total elements
int Size; // Size of maze
private Random myRandGen; //random
private Vertex start; //start of maze
private Vertex end; //end of maze
int time; // keep track for dfs
int[] pathHistory; // 0 = false, 1 = true

double myRandom() {
	return myRandGen.nextDouble();
}

public void setPath(){
	Vertex cur = mazeElementList[Size-1][Size-1];
	while (cur != null)
	{
		cur.checkIfInWay = true;
		cur = cur.previous;
	}
}


public MazeGenerator(int mazeSize) {
	mazeElementList = new Vertex[mazeSize][mazeSize];
	for (int i = 0; i <mazeSize; i++) 
	{
		for (int j = 0; j <mazeSize; j++) 
		{
			mazeElementList[i][j] = new Vertex();
		}
	}

	Size = mazeSize;
	eleCount = Size * Size;
	myRandGen = new java.util.Random(0);
	start = mazeElementList[0][0];
	end = mazeElementList[Size - 1][Size - 1]; 
	pathHistory = new int[Size*Size]; 
}


public void DFS(Vertex s) {
	int traverseO = 1;  
	Stack<Vertex> q = new Stack<>();
	q.push(s);
	while (!q.isEmpty() && !q.peek().equals(end)) { 
		Vertex u = q.pop();
		for (int i = 0; i < u.surroundings.length; i++) {
			Vertex v = u.surroundings[i];
			int direction = u.getVertexStatus(v);
			if ((u.walls[direction] == 0) && v != null && v.colValue == 0) { 
				v.colValue = 1; 
				if (v.keepTrackOfOrder == 0){  
    				if (pathHistory[traverseO] == 0){ 
    					v.keepTrackOfOrder = traverseO;
    					pathHistory[traverseO] = 1;	
    				} else {	
    					traverseO++;
    					v.keepTrackOfOrder = traverseO;
    					pathHistory[traverseO] = 1; //set it to used 
    				}
    			}
				v.shortestPath = u.shortestPath + 1;
				v.previous = u;
				q.push(v);
			}
		}
		u.colValue = 2; //colValue = black
	}
}

public void BFS(Vertex s)
{
	int traverseO = 1;
	LinkedList<Vertex> queue = new LinkedList<Vertex>();
	Vertex u = s;
	queue.addFirst(s);
	int inc = 0;
	while (!queue.isEmpty() && !u.equals(end))
	{
		u = queue.removeLast();
		Vertex[] adjNodes = u.surroundings;
		for (int i = 0; i < adjNodes.length; i++)
		{
			Vertex v = adjNodes[i];
			int direction = u.getVertexStatus(v);
			if ((u.walls[direction] == 0) && v != null && v.colValue == 0) { 
				v.colValue = 1; 
				if (v.keepTrackOfOrder == 0){  
    				if (pathHistory[traverseO] == 0){ 
    					v.keepTrackOfOrder = traverseO;
    					pathHistory[traverseO] = 1;	
    				} else {	
    					traverseO++;
    					v.keepTrackOfOrder = traverseO;
    					pathHistory[traverseO] = 1; //set it to used 
    				}
    			}
				v.shortestPath = u.shortestPath + 1;
				v.previous = u;
				queue.addFirst(v);
			}
		}
		u.colValue = 2;
	}
}

public void reset() {
	for (int i = 0; i < Size; i++)
	{
		for (int j = 0; j < Size; j++) 
		{
			mazeElementList[i][j].reset();
		}
	}
	pathHistory = new int[Size*Size]; 
	start.walls[0] = 100;	//north wall is entry
	end.walls[2] = 100; 	//south wall is exit
}

public void populateGraph() {
	for (int i = 0; i < Size; i++) {
		for (int j = 0; j < Size; j++) {
			Vertex curEle = mazeElementList[i][j];

			if (i == 0) {
				curEle.setUp(null);
				curEle.walls[0] = -100; 
			}

			else {
				curEle.setUp(mazeElementList[i - 1][j]); 
				curEle.surroundings[0] = mazeElementList[i - 1][j]; 
			}

			if (j == 0) {
				curEle.setLeft(null);
				curEle.walls[3] = -100;
			}

			else {
				curEle.setLeft(mazeElementList[i][j - 1]);
				curEle.surroundings[3] = mazeElementList[i][j - 1];
			}

			if (i == Size - 1) {
				curEle.setDown(null);
				curEle.walls[2] = -100;
			}

			else {
				curEle.setDown(mazeElementList[i + 1][j]);
				curEle.surroundings[2] = mazeElementList[i + 1][j];
			}

			if (j == Size - 1) {
				curEle.setRight(null);
				curEle.walls[1] = -100;
			}

			else {
				curEle.setRight(mazeElementList[i][j + 1]);
				curEle.surroundings[1] = mazeElementList[i][j + 1];
			}
		}
	}
}


void generateMaze() {
	reset();
	populateGraph(); 
	start.walls[0] = 100; 
	end.walls[2] = 100;
	Stack<Vertex> elementStack = new Stack<>();
	int totalElements = eleCount;
	Vertex curEle = mazeElementList[0][0];
	int visitedEle = 1;
	while (visitedEle < totalElements) {
		ArrayList<Vertex> neighborsIntact = new ArrayList<Vertex>();
		for (int i = 0; i < curEle.surroundings.length; i++) {
			Vertex neighbor = curEle.surroundings[i];
			if (neighbor != null) 
			{
				if (neighbor.checkIntactWalls()) 
				{
					neighborsIntact.add(neighbor);
				}
			}
		}
		
		if (neighborsIntact.size() != 0) 
		{
			int rand = (int) (myRandom() * neighborsIntact.size());
			Vertex knockDown = neighborsIntact.get(rand);
			int relationship = curEle.getVertexStatus(knockDown); 
			if (relationship == 0) {
				curEle.breakNorthWall();
				knockDown.breakSouthWall();
			} 
			
			else if (relationship == 1) 
			{
				curEle.breakEastWall();
				knockDown.breakWestWall();
			} 
			
			else if (relationship == 2) 
			{ 
				curEle.breakSouthWall();
				knockDown.breakNorthWall();
			} 
			
			else 
			{ 
				curEle.breakWestWall();
				knockDown.breakEastWall();
			}
			
			elementStack.push(curEle);
			curEle = knockDown;
			visitedEle++;
			
		} 
		
		else 
		{
			curEle = elementStack.pop();
		}
	}

}

public String printInitialGrid() {
	String maze = "";
	int n = 2;
	for (int i = 0; i < Size; i++) {
		if (i == Size - 1)
			n = 3;
		for (int k = 1; k <= n; k++) {
			if (k == 1) 
				maze += "+"; 
			
			if (k == 2) 
				maze += "|"; 
			
			if ((k == 3) && (i == Size - 1)) 
				maze += "+";

			for (int j = 0; j < Size; j++) {
				Vertex v = mazeElementList[i][j];
				if (k == 1) 
				{
					if ((v.walls[0] != 0) && (v.walls[0] != 100)) 
						maze += "-";
					else
						maze += " ";

					maze += "+";
				}

				else if (k == 2) {
					maze += " ";

					if (v.walls[1] != 0) 
						maze += "|";
					else
						maze += " ";
				}

				else if ((k == 3) && (i == Size - 1)) 
				{
					if ((v.walls[2] != 0) && (v.walls[2] != 100)) 
						maze += "-";
					else
						maze += " ";

					maze += "+";
				}
			}
			maze += "\n";
		}
	}
	return maze;
}

public String printBFSDFSGrid() {
	String maze = "";
	int n = 2;
	for (int i = 0; i < Size; i++) {
		if (i == Size - 1)
			n = 3;
		for (int k = 1; k <= n; k++) {

			if (k == 1) {
				maze += "+";
			}

			if (k == 2) {
				maze += "|";
			}

			if ((k == 3) && (i == Size - 1)) {
				maze += "+";
			}

			for (int j = 0; j < Size; j++) {
				Vertex v = mazeElementList[i][j];
				if (k == 1) {
					if ((v.walls[0] != 0) && (v.walls[0] != 100))
						maze += "-";
					else
						maze += " ";

					maze += "+";
				}
				else if (k == 2) {
					if ((v != null) && v == (mazeElementList[0][0])){
						maze += "0";
					} else if (v.previous == null) { // don't print label
						maze += " ";
					} else {
						maze += ((v.keepTrackOfOrder)%10);
					}
					
					if (v.walls[1] != 0) // right wall is 1
						maze += "|";
					else
						maze += " ";
				}

				else if ((k == 3) && (i == Size - 1)) {
					if ((v.walls[2] != 0) && (v.walls[2] != 100))
						maze += "-";
					else
						maze += " ";

					maze += "+";
				}
		}
			maze += "\n";
		}
	}

	return maze;
}

public String printSolutionGrid() {
	String maze = "";
	int n = 2;
	for (int i = 0; i < Size; i++) {
		if (i == Size - 1)
			n = 3;
		for (int k = 1; k <= n; k++) {
			
			if (k == 1) 
				maze += "+";
			if (k == 2) 
				maze += "|";
			if ((k == 3) && (i == Size - 1)) 
				maze += "+";
			
			for (int j = 0; j < Size; j++) {
				Vertex v = mazeElementList[i][j];
				if (k == 1) {
					if ((v.walls[0] != 0) && (v.walls[0] != 100)) 
						maze += "-";
					else if (v.equals(start)){
						maze += "#";
					}
					else if (v.checkIfInWay && v.getUp() != null && v.getUp().checkIfInWay)
						maze += "#";
					else {
						maze += " ";
					}

					maze += "+";
				}
				else if (k == 2) {
					if (v == null) { 
						maze += " ";
					} else if (v.checkIfInWay){
						maze += (("#"));
					} else {
						maze += " ";
					}
			
					if (v.walls[1] != 0) 
						maze += "|";
					else
						maze += " ";
				}
				else if ((k == 3) && (i == Size - 1)) {
					if ((v.walls[2] != 0) && (v.walls[2] != 100))
						maze += "-";
					else if (v.checkIfInWay) 
						maze += "#";
					else
						maze += " ";

					maze += "+";

				}
			}
			maze += "\n";
		}
	}

	return maze;
}


public static void main(String[] args) {
	MazeGenerator g1 = new MazeGenerator(5);
	g1.generateMaze();
	System.out.println("Blank Generated Grid: ");
	String maze1 = g1.printInitialGrid();
	System.out.println(maze1);
	g1.DFS(g1.mazeElementList[0][0]);
	String aGrid1 = g1.printBFSDFSGrid();
	System.out.println();
	System.out.println("DFS Attempt");
	System.out.println(aGrid1);
	g1.setPath();
	System.out.println("Solution");
	String dGrid1 = g1.printSolutionGrid();
	System.out.println();
	System.out.println(dGrid1);
	
	g1.reset();
	System.out.println("Blank Generated Grid: ");
	maze1 = g1.printInitialGrid();
	System.out.println(maze1);
	g1.BFS(g1.mazeElementList[0][0]);
	aGrid1 = g1.printBFSDFSGrid();
	System.out.println();
	System.out.println("BFS Attempt");
	System.out.println(aGrid1);
	g1.setPath();
	System.out.println("Solution");
	dGrid1 = g1.printSolutionGrid();
	System.out.println();
	System.out.println(dGrid1);
}
}//MazeGenerator Ends here