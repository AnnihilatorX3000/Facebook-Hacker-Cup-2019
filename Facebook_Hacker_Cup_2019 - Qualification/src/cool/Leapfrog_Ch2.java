package cool;

import java.io.*;

public class Leapfrog_Ch2
{

	public static Boolean SolveCase(BufferedReader br, String strCurrLine, char[] arrCurrLine, int i, int B, int N) throws IOException
	{
		//Get data
		strCurrLine = br.readLine();
		arrCurrLine = strCurrLine.toCharArray();		
		N = arrCurrLine.length;
		
		//Compute number of Bs
		for (char c : arrCurrLine)
		{
			if (c == 'B')
				B++;
		}
		
		//At min, we must have floor(N/2) Bs and at max, we must have N-2 Bs. Anywhere in between is solvable.
		if (N >= 4)
			return (B >= 2 && B <= N - 2);
		else	
			return (B >= N/2 && B <= N - 2);
	}
	
	public static void WriteToFile(BufferedWriter bw, Boolean result, int i, int B, int N) throws IOException
	{
		String line = "Case #" + i + ": ";
			
		if (result)
			line += "Y";
		else
			line += "N";
	
		bw.write(line);
		bw.newLine();
	}
	
	public static void main(String[] args)
	{		
		//Read input
		final String filepathIn = "Leapfrog_Ch2_Input.txt";
		final String filepathOut = "Leapfrog_Ch2_Output.txt";
		
		int T = 0;
		String strCurrLine = "";
		char[] arrCurrLine = null;
		int N = 0;
		int B = 0;
		Boolean result;
		
		try
		{
			FileReader fr = new FileReader(filepathIn);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(filepathOut, false);
			BufferedWriter bw = new BufferedWriter(fw);
		
			T = Integer.parseInt(br.readLine());
			
			//Solving for each case
			for (int i = 1; i <= T; i++)
			{	
				result = SolveCase(br, strCurrLine, arrCurrLine, i, B, N);			
				WriteToFile(bw, result, i, B, N);
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