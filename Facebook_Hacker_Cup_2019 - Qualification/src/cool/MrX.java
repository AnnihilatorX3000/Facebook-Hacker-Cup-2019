package cool;

import java.io.*;
import java.util.Stack;

public class MrX
{

	public static String[] SplitExp(String line)
	{
		Stack<Character> brackets = new Stack<>();
		Character chr;
		int opPos = 0;

		//Locate outermost operator position
		for (int i = 1; i < line.length(); i++)
		{
			chr = line.charAt(i);

			//Keep searching
			if (chr == '(')
			{
				brackets.push(chr);
			}
			else if (chr == ')') //Assumes incoming string is of valid format
			{
				brackets.pop();
			}

			//Outermost operator located
			if (brackets.isEmpty())
			{
				opPos = i + 1;
				break;
			}
		}

		//Split expression based on operator location
		String[] exp = new String[3];
		exp[0] = line.substring(1, opPos);
		exp[1] = String.valueOf(line.charAt(opPos));
		exp[2] = line.substring(opPos + 1, line.length() - 1);
		return exp;
	}

	public static boolean NeedsToChange(String line)
	{
		//Compare results if we set x = 0 and then x = 1. 
		String[] exp = SplitExp(line);
		String expL = exp[0];
		char op = exp[1].charAt(0);
		String expR = exp[2];

		//Change xs into 0s or 1s before evaluating
		String tempL, tempR = "";

		tempL = SubXVal(expL, 0);
		tempR = SubXVal(expR, 0);
		boolean xIs0 = EvalExp(tempL, op, tempR);

		tempL = SubXVal(expL, 1);
		tempR = SubXVal(expR, 1);
		boolean xIs1 = EvalExp(tempL, op, tempR);

		//If results are equal, then no need to change.
		return (!(xIs0 == xIs1));
	}

	public static String SubXVal(String exp, int valOfX)
	{
		if (valOfX == 0)
			exp = exp.replace('x', '0').replace('X', '1');
		else
			exp = exp.replace('x', '1').replace('X', '0');
		return exp;
	}

	public static boolean EvalExp(String expL, char op, String expR)
	{
		String[] splitExp = new String[3];
		boolean L, R;

		//Base case	- ExpL
		if (expL.equals("0"))
			L = false;
		else if (expL.equals("1"))
			L = true;
		else
		{
			//Recursive
			splitExp = SplitExp(expL);
			L = EvalExp(splitExp[0], splitExp[1].charAt(0), splitExp[2]);
		}

		//Base case - ExpR
		if (expR.equals("0"))
			R = false;
		else if (expR.equals("1"))
			R = true;
		else
		{
			//Recursive
			splitExp = SplitExp(expR);
			R = EvalExp(splitExp[0], splitExp[1].charAt(0), splitExp[2]);
		}

		//Perform binary operation
		boolean result = true;
		switch (op)
		{
		case '|':
			result = OR(L, R);
			break;
		case '&':
			result = AND(L, R);
			break;
		case '^':
			result = XOR(L, R);
			break;
		}

		return result;
	}

	public static boolean OR(boolean A, boolean B)
	{
		return A || B;
	}

	public static boolean AND(boolean A, boolean B)
	{
		return A && B;
	}

	public static boolean XOR(boolean A, boolean B)
	{
		return A ^ B;
	}

	public static void WriteToFile(BufferedWriter bw, int i, int changes) throws IOException
	{
		bw.write("Case #" + i + ": " + changes);
		bw.newLine();
	}

	public static void main(String[] args)
	{
		//Read input
		final String filepathIn = "MrX_Input.txt";
		final String filepathOut = "MrX_Output.txt";

		int T = 0;
		int changes = 0;
		String line;

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
				line = br.readLine();
				
				if (line.length() == 1)
				{
					if (line.equals("X") || line.equals("x"))
					{
						//Just change a single-character line to a constant
						line = "1";
						changes = 1;
					}
				}
				//By considering all cases, we can check that every binary expression here requires either one change or none.
				else if (NeedsToChange(line))
				{
					changes = 1;
				}

				WriteToFile(bw, i, changes);
				
				changes = 0;
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