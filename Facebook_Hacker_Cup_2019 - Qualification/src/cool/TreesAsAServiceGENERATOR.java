package cool;

import java.io.*;


public class TreesAsAServiceGENERATOR
{

	public static void main(String[] args)
	{
		String filepath = "TreesAsAService_Input.txt";
		
		try
		{
			FileWriter fw = new FileWriter(filepath, false);
			BufferedWriter bw = new BufferedWriter(fw);
			
			int N, M, X, Y, Z;
			
			int T = 100000;	
			bw.write(T + "");
			bw.newLine();
		
			for(int i = 1; i <= T; i++)
			{
				N = (int)(Math.random()*59 + 1) + 1;
				M = (int)(Math.random()*120 + 1);
				bw.write(N + " " + M);
				bw.newLine();
				
				for (int j = 1; j <= M; j++)
				{
					X = (int)(Math.random()*N + 1);
					Y = (int)(Math.random()*N + 1);
					if (X == Y)
					{
						j--;
					}
					else
					{
						Z = (int)(Math.random()*N + 1);
						bw.write(X + " " + Y + " " + Z);
						bw.newLine();
					}				
				}
			}
			
			bw.close();
			fw.close();
		}
		catch (IOException e)
		{
			System.out.println("lol usuk");
		}
	}

}
