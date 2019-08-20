package cool;

import java.util.Stack;

public class BracketCheck
{

	public static void main(String[] args)
	{
		String fag = "(((X|X)&(X&1))&((X&X)^((X^0)|(X^1))))";
		Stack<Character> fagStack = new Stack<>();
		Character chr;
		
		for (int i = 0; i < fag.length(); i++)
		{
			chr = fag.charAt(i);
			
			if (chr == '(')
			{
				fagStack.push('(');
			}
			else if (chr == ')')
			{
				fagStack.pop();
			}
		}
		
		if (fagStack.isEmpty())
		{
			System.out.println("Balanced");
		}
		else
		{
			System.out.println("NOT Balanced");
		}
	}

}
