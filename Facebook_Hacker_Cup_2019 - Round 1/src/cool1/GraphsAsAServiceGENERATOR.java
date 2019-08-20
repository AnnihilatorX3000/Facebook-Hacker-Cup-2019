package cool1;

import java.io.*;
import java.util.ArrayList;

public class GraphsAsAServiceGENERATOR
{

	public static boolean hasDuplicates(ArrayList<String> XY, int X, int Y)
	{
		if (XY.contains(X + " " + Y) || XY.contains(Y + " " + X))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void main(String[] args)
	{
		String filepath = "GraphsAsAService_Input.txt";
		
		try
		{
			FileWriter fw = new FileWriter(filepath, false);
			BufferedWriter bw = new BufferedWriter(fw);
			
			int N, M, X, Y, Z;
			
			int T = 5000;	
			bw.write(T + "");
			bw.newLine();
		
			for(int i = 1; i <= T; i++)
			{
				System.out.println("Creating test case# " + i);
				N = (int)(Math.random()*49 + 1) + 1;
				int k = ((N - 1) * N) / 2; 		// #edges in complete graph
				M = (int)(Math.random()*k + 1);
				bw.write(N + " " + M);
				bw.newLine();
				
				ArrayList<String> XY = new ArrayList<>();
				
				for (int j = 1; j <= M; j++)
				{
					X = (int)(Math.random()*N + 1);
					Y = (int)(Math.random()*N + 1);
					if (X == Y || hasDuplicates(XY, X ,Y))
					{
						j--;
					}
					else
					{
						Z = (int)(Math.random()*1000000 + 1);
						bw.write(X + " " + Y + " " + Z);
						bw.newLine();
						XY.add(X + " " + Y);
					}				
				}
			}
			
			bw.close();
			fw.close();
			
			System.out.println("Input file generated");
		}
		catch (IOException e)
		{
			System.out.println("lol usuk");
		}
	}

}
