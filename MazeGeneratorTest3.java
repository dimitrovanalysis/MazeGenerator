import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Checks if the number of cells visited during the maze generation process is equal to the total of cells in the maze.
 * @author Dale Christian Seen and Konstantin Dimitrov
 *
 */
class MazeGeneratorTest3 {

	@Test
	void test()
	{
		final int DIMENSION = 4;
		
		MazeGenerator maze = new MazeGenerator(DIMENSION);
		
		int result = maze.generateMaze();
		
		int expected = DIMENSION * DIMENSION;
		
		assertEquals(result, expected);
	}

}
