import static org.junit.Assert.assertEquals;

import java.io.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * Checks if the MazeGenerator generates the same maze after it is run the second time.
 * @author Dale Christian Seen and Konstantin Dimitrov
 *
 */
class MazeGeneratorTest2 {

	@Test
	void test()
	{
		try
		{
			final String ATTEMPT1 = "Attempt1.txt";			//	Name of .txt file containing the generated solution for the first attempt
			final String ATTEMPT2 = "Attempt2.txt";			//	Name of .txt file containing the generated solution for the second attempt
			
			final int DIMENSION = 4;						// 	The dimension of the maze that will be generated.
			
			// Define two BufferedWriters to write the results of each attempt
			BufferedWriter out1 = new BufferedWriter(new FileWriter(ATTEMPT1));
			BufferedWriter out2 = new BufferedWriter(new FileWriter(ATTEMPT2));
			
			// Create two MazeGenerator objects that will generate results
			MazeGenerator maze1 = new MazeGenerator(DIMENSION);
			MazeGenerator maze2 = new MazeGenerator(DIMENSION);
			
			// Generate results of each attempt and write them into two seperate .txt files.
			generateAndWriteResult(maze1, out1);
			generateAndWriteResult(maze2, out2);
			
			out1.close();
			out2.close();
			
			// Compare if both attempts generated the same mazes and solutions.
			BufferedReader reader1 = new BufferedReader(new FileReader(ATTEMPT1));
			BufferedReader reader2 = new BufferedReader(new FileReader(ATTEMPT2));
			
			String expectedLine = "";
			while ((expectedLine = reader1.readLine()) != null)
			{
				String actualLine = reader2.readLine();
				assertEquals(expectedLine, actualLine);
			}
		}
		catch (Exception fail)
		{
			Assert.fail();
		}
	}
	
	/**
	 * Writes the results of a MazeGenerator into a .txt file
	 * @param aMaze Maze that will generate result
	 * @param out BufferedWriter object that will write result into .txt file
	 */
	void generateAndWriteResult(MazeGenerator aMaze, BufferedWriter out)
	{
		try
		{
			out.write("Graph Size: " + aMaze.getDimension());
			out.newLine();
			out.write("MAZE:");
			out.newLine();
			aMaze.generateMaze();
			String initialMaze = aMaze.toStringInitialMaze() + "\r\n";
			out.write(initialMaze);
			aMaze.solveMazeBFS();
			String bfs = aMaze.toStringOrderMaze() + "\r\n";
			out.write("BFS:\r\n");
			out.write(bfs);
			String bfsSolution = aMaze.toStringSolvedMaze() + "\r\n";
			out.write(bfsSolution);
			aMaze.reset();
			aMaze.solveMazeDFS();
			String dfs = aMaze.toStringOrderMaze() + "\r\n";
			out.write("DFS:\r\n");
			out.write(dfs);
			String dfsSolution = aMaze.toStringSolvedMaze();
			out.write(dfsSolution);
			out.write("======================\r\n");
			out.write("  Program Completed!  \r\n");
			out.write("======================\r\n");
		}
		catch (Exception event)
		{
			Assert.fail();
		}
	}
}
