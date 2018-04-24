import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MazeGeneratorTest {

	@Test
	public void test() 
	{
		try
		{
			final String EXPECTED_FILE_OUTPUT = "maze_s468.txt";
			final String ACTUAL_FILE_OUTPUT = "output.txt";
			
			BufferedReader Out = new BufferedReader(new FileReader(ACTUAL_FILE_OUTPUT));
			BufferedReader In = new BufferedReader(new FileReader(EXPECTED_FILE_OUTPUT));
			
			String expectedLine = "";
			while ((expectedLine = In.readLine()) != null)
			{
				String actualLine = Out.readLine();
				assertEquals(expectedLine, actualLine);
			}
		}
		catch (Exception fail)
		{
			Assert.fail();
		}
		
		/**
		final String EXPECTED_FILE_OUTPUT = "maze_s468.txt";
		final String ACTUAL_FILE_OUTPUT = "output.txt";
		
		try
		{
			FileInputStream file1 = new FileInputStream(EXPECTED_FILE_OUTPUT);
			InputStreamReader expIn = new InputStreamReader(file1, "UTF-8");
			BufferedReader expected = new BufferedReader(expIn);
			
			FileInputStream file2 = new FileInputStream(ACTUAL_FILE_OUTPUT);
			InputStreamReader resIn = new InputStreamReader(file2, "UTF-8");
			BufferedReader result = new BufferedReader(resIn);
			
			String expectedLine;
			while ((expectedLine = expected.readLine()) != null)
			{
				String actualLine = result.readLine();
				assertEquals(expectedLine, actualLine);
			}
			expected.close();
			result.close();
		}
		catch (Exception fail)
		{
			System.out.println("ERROR: FILE(S) NOT FOUND");
		}
		**/
	}

}
