package cool1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//GAVE UP FROM HERE
public class LaddersAndSnakes
{
	public static boolean[][] InitMap(ArrayList<String> ladders, int H)
	{
		//Obtain length of map
		int lastX = 0;
		int temp = 0;

		for (String ladder : ladders)
		{
			String[] arr = ladder.split(" ");
			temp = Integer.parseInt(arr[0]);
			if (temp > lastX)
				lastX = temp;
		}

		boolean[][] map = new boolean[lastX + 1][H + 1];

		//Extract lengths and heights from ladders
		for (String ladder : ladders)
		{
			AddLadder(map, ladder);
		}
		
		return map;
	}

	public static void AddLadder(boolean[][] map, String ladder)
	{
		String[] arr = ladder.split(" ");
		int X = Integer.parseInt(arr[0]);
		int A = Integer.parseInt(arr[1]);
		int B = Integer.parseInt(arr[2]);

		//Coordinate (x, y) is true if ladder is present
		for (int y = A; y <= B; y++)
		{
			map[X][y] = true;
		}
	}

	public static boolean ImpossibleToStop(boolean[][] map)
	{
		int length = map.length;
		int height = map[0].length;

		//Will be impossible to stop Flynn if there's a ladder that takes her all the way from 0 to H
		for (int x = 0; x < length; x++)
		{
			boolean impossible = true;
			for (int y = 0; y < height; y++)
			{
				if (map[x][y] == false)
				{
					impossible = false;
					break;
				}
			}
			if (impossible)
				return true;
		}

		return false;
	}

	public static boolean ImpossibleToReach(boolean[][] map)
	{
		//Take first ladder and keep climbing. At top of ladder, there must be another one to continue on. Otherwise, impossible for Flynn to reach (0, H)
		int length = map.length;
		int height = map[0].length;
		int prevX = -1;
		int ladderX = -1;
		int ladderY = 0;

		while (ladderY < height)
		{
			//Check that new ladder exists at height ladderY
			for (int x = 0; x < length; x++)
			{
				if (x != prevX && map[x][ladderY])
				{
					ladderX = x;
					break;
				}
			}

			//Gap found. Cannot reach (0, H)
			if (ladderX == -1)
				return true;

			//Ladder found. Reach top of this ladder
			while (ladderY < height && map[ladderX][ladderY])
			{
				ladderY++;
			}
			ladderY--;
			prevX = ladderX;
			ladderX = -1;
		}

		return false;
	}

	public static int GetMinLength(boolean[][] map, ArrayList<String> ladders)
	{
		int length = map.length;
		int height = map[0].length;

		if (ImpossibleToStop(map))
			return -1;
		if (ImpossibleToReach(map))
			return 0;

		//Possible for Flynn to reach (0, H) and Sneider can stop Flynn---------------------------------
		
		//Obtain left and right most ladders (no need to block left/right of it)
		int rightMostLadder = length;
		int leftMostLadder = length;
		int temp = 0;
		for (String ladder : ladders)
		{
			String[] arr = ladder.split(" ");
			temp = Integer.parseInt(arr[0]);
			if (temp < leftMostLadder)
				leftMostLadder = temp;
		}
		
		//Only need to care about blocking ACCESS to some level, so only consider the 'overlaps' of ladders.
		//Endpoint-to-endpoints can be blocked by zero-length snakes
		
		
		
		//Calculate minGap?
		
	}

	//FOR UTILITY PURPOSES
	public static void DisplayMap(boolean[][] map)
	{
		int height = map[0].length;
		int length = map.length;
		for (int y = height - 1; y >= 0; y--)
		{
			for (int x = 0; x < length; x++)
			{
				if (map[x][y])
					System.out.print(" 1 ");
				else
					System.out.print("   ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		final String filepathIn = "LaddersAndSnakes_Sample.txt";
		final String filepathOut = "LaddersAndSnakes_Output.txt";

		//READ file
		try
		{
			FileReader fr = new FileReader(filepathIn);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(filepathOut, false);
			BufferedWriter bw = new BufferedWriter(fw);

			int T = Integer.parseInt(br.readLine());
			int minLength = 0;

			//Solve each case
			for (int i = 1; i <= T; i++)
			{
				String[] NH = br.readLine().split(" ");
				int N = Integer.parseInt(NH[0]);
				int H = Integer.parseInt(NH[1]);
				ArrayList<String> ladders = new ArrayList<String>();

				//Get ladder data and create map
				for (int j = 1; j <= N; j++)
				{
					ladders.add(br.readLine());
				}
				boolean[][] map = InitMap(ladders, H);

				//Solve problem
				//minLength = GetMinLength(map, ladders);
					
				//Display map
				System.out.println("[MAP #" + i + "]-------------------------------------------");
				DisplayMap(map);
				
				//Output
				//bw.write("Case #" + i + ": " + minLength);
				//bw.newLine();
			}

			br.close();
			fr.close();
			bw.close();
			fw.close();

			System.out.println("Finished outputting");
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ERROR: File not found!");
		}
		catch (IOException e)
		{
			System.out.println("ERROR: IOException!");
		}
	}
}