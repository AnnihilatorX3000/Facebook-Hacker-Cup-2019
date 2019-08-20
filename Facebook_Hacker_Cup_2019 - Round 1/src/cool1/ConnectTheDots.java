package cool1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConnectTheDots
{	
	public static void main(String[] args)
	{
		final String filepathIn = "ConnectTheDots_Input.txt";
		final String filepathOut = "ConnectTheDots_Output.txt";

		//READ file
		try
		{
			FileReader fr = new FileReader(filepathIn);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(filepathOut, false);
			BufferedWriter bw = new BufferedWriter(fw);

			
			
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