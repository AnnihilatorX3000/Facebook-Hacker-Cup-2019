package cool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.HashSet;

public class TreesAsAService
{
	
	public static boolean PathExists(int A, int B, int[] parent)
	{
		//Go through A's parent-line via depth-first search till we reach B or a dead-end
		if (A == B)
		{
			return true;
		}	
		else if (parent[A] == 0)
		{
			return false;
		}
			
		return PathExists(parent[A], B, parent);
	}
	
	public static boolean HasCycle(int source, int A, int[] parent)
	{
		//Sees if there is a cycle in the tree via depth-first search starting from source
		if (A == source)
		{
			return true;
		}
		else if (parent[A] == 0)
		{
			//No cycle if we can reach a dead-end
			return false;
		}
			
		return HasCycle(source, parent[A], parent);	
	}
	
	public static boolean Relocate(int A, int B, int[] parent)
	{
		//Goes through parent-line of A via depth-first search, testing candidate paths, to find suitable spot to place the edge to B
		//Returns FALSE if Relocate detects a cycle	caused due to relocating
		if (A == B)
		{
			//Note: This cannot happen on the first Relocate call, and can only happen if we have created a cycle
			return false;
		}	
		else if (parent[A] == B)
		{
			//Merge path if relocated to an existing one
			return true;	
		}
		else if (parent[A] == 0)
		{
			//If reached end of parent-line of A, create new direct path to B
			parent[A] = B;
			return true;
		}
		
		//Otherwise, go up next node on A's parent-line
		return Relocate (parent[A], B, parent);				
	
	}
	
	public static int GetLCA(int A, int B, int[] parent)
	{
		//Assumptions: No cycle exists, A != B, parents[A], parents[B] != 0 to begin with (relocation should still maintain some path from A and B to LCA)
		//Goes through A's whole parent-line
		HashSet<Integer> parentsA = new HashSet<>();
		while (A != 0)
		{
			parentsA.add(A);
			A = parent[A];
		}
		
		//Then, goes through B's parent-line, checking for intersection with A's parent-line
		//(Intersection is guaranteed to exist since  a successful relocation either merges or creates a path to the LCA. But just in case, return 0 if no path)
		while (B != 0)
		{
			if (parentsA.contains(B))
				return B;
			else
				B = parent[B];
		}
		return 0;
	}
	
	public static boolean Connect(int A, int B, int[] parent, LinkedList<int[]> metRequirements)
	{
		//Returns FALSE if a new connection between A and B has created a cycle
		if (!PathExists(A, B, parent))
		{
			if (parent[A] == 0)
			{
				//Create direct path from A to B
				parent[A] = B;
				//Checks if new connection has created a cycle and fails test if it has
				return !HasCycle(A, parent[A], parent);
			}
			else
			{
				//If there's already a connection from A to oldB, it needs to be relocated
				int oldB = parent[A];
				parent[A] = B;
				//Checks if new connection has created a cycle and fails test if it has
				if (HasCycle(A, parent[A], parent))
				{
					return false;
				}
				
				//Relocation
				boolean relocationSuccess = Relocate(B, oldB, parent);
				
				if (!relocationSuccess)
					return false;
				else
				{
					//Check that relocation has not created a cycle
					if (HasCycle(oldB, parent[oldB], parent))
					{
						return false;
					}
					
					//Check that relocation didn't destroy met requirements
					for (int[] req : metRequirements)
					{
						if (GetLCA(req[0], req[1], parent) != req[2])
						{
							relocationSuccess = false;
							break;
						}
					}
					
					return relocationSuccess;
				}
			}
		}
		
		//If path already exists, then nothing is done and no cycle can be created
		return true;
	}

	public static boolean MeetRequirement(int X, int Y, int Z, int[] parent, LinkedList<int[]> metRequirements)
	{
		//Tries to create the connections XZ and YZ
		//Returns FALSE if this is results in an invalid tree or if current requirement has not been met
		boolean connectionSuccess = true;	
		connectionSuccess = (Connect(X, Z, parent, metRequirements) && Connect(Y, Z, parent, metRequirements));
			
		if (!connectionSuccess)
			return false;
		else if (GetLCA(X, Y, parent) != Z)
			//Verifies that current requirement is being met
			return false;
		else
			return true;
	}
	
	public static void main(String[] args)
	{
		final String filepathIn = "TreesAsAService_Input.txt";
		final String filepathOut = "TreesAsAService_Output.txt";
		final int maxNoOfNodes = 60;
		
		int T, N, M, X, Y, Z;	
		String line = "";
		String[] arr = null;
		boolean success = true;
		
		//Set to maximum size to avoid resizing / creating new arrays
		int[] nodes = new int [maxNoOfNodes + 1];
		int[] parent = new int[maxNoOfNodes + 1];
		LinkedList<int[]> metRequirements = new LinkedList<>(); //For storing current requirements met (need to check they're being met after relocation)
		
		//Populating node array with node numbers - will leave 0th index untouched to avoid off-by-one error
		for (int i = 1; i <= maxNoOfNodes; i++)
		{
			nodes[i] = i; 
		}
		
		//READ file
		try
		{		
			FileReader fr = new FileReader(filepathIn);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(filepathOut, false);
			BufferedWriter bw = new BufferedWriter(fw);
			
			T = Integer.parseInt(br.readLine());
						
			//Solving each case
			for (int i = 1; i <= T; i++)
			{
				//GET N, M
				line = br.readLine();
				arr = line.split(" ");
				N = Integer.parseInt(arr[0]);
				M = Integer.parseInt(arr[1]);
				
				//RESET edge data for nodes, success flag, and requirements for next case
				for (int j = 1; j <= N; j++)
				{
					//[Note: Since each node is numbered from 1 onwards, we will let 0 represent null]
					parent[j] = 0;
				}
				success = true;
				metRequirements.clear();
				
				//TRY TO MEET REQUIREMENTS
				for (int j = 1; j <= M; j++)
				{	
					//Obtaining requirement info
					line = br.readLine();
					
					arr = line.split(" ");
					X = Integer.parseInt(arr[0]);
					Y = Integer.parseInt(arr[1]);
					Z = Integer.parseInt(arr[2]);
					
					success = MeetRequirement(X, Y, Z, parent, metRequirements);
				
					if (!success)
					{		
						//Skips to appropriate line on the input file
						for (int k = j + 1; k <= M; k++)
						{
							br.readLine();
						}
						
						break;
					}
					else
					{
						metRequirements.add(new int[] {X, Y, Z});
					}		
				}
				
				//VERDICT for this case
				bw.write("Case #" + i + ": ");
				if (success)
				{
					//Output parent data
					for (int j = 1; j <= N; j++)
					{
						bw.write(parent[j] + " ");
					}
				}
				else
				{
					bw.write("Impossible");		
				}
				bw.newLine();
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