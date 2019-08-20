package cool1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ArrayList;

public class GraphsAsAService
{

	//Dijkstra
	public static int shortestDist(AdjList graph, int source, int dest)
	{
		int size = graph.getSize();
		LinkedList<Integer> Q = new LinkedList<>();
		int[] dist = new int[size + 1];
		int[] prev = new int[size + 1];

		//Initialise
		for (int i = 1; i <= size; i++)
		{
			dist[i] = Integer.MAX_VALUE;
			prev[i] = -1;
			Q.add(i);
		}
		dist[source] = 0;

		//Perform Dijkstra
		while (!Q.isEmpty())
		{
			//Get element v from Q with min dist[v]
			int min = Q.get(0);
			for (int v : Q)
			{
				if (dist[v] < dist[min])
					min = v;
			}
			//Terminate search if reached destination
			if (min == dest)
			{
				Q.clear();
				break;
			}

			Q.remove((Integer) min);

			//Look at each neighbour of min
			for (Edge e : graph.getEdgeList(min))
			{
				//Skip if neighbour is not in Q
				if (!Q.contains(e.dest))
				{
					continue;
				}

				//Replace longer path with already existing shorter one
				int temp;
				if (dist[min] == Integer.MAX_VALUE)

				{
					temp = Integer.MAX_VALUE;
				}
				else
				{
					temp = dist[min] + e.weight;
					if (temp < dist[e.dest])
					{
						dist[e.dest] = temp;
						prev[e.dest] = min;
					}
				}
			}
		}

		//Returns -1 if no path found
		if (prev[dest] != -1)
		{
			return dist[dest];
		}
		else
		{
			return -1;
		}
	}

	public static boolean MeetReq(AdjList graph, ArrayList<String> E, int X, int Y, int Z)
	{
		int dist = shortestDist(graph, X, Y);		
		
		//If no path exists or if current path is too long, make new path
		if (dist == -1 || dist > Z)
		{
			//Create direct connection between X and Y
			Edge e = new Edge(Y, Z);
			graph.AddEdge(X, e);
			E.add(X + " " + Y + " " + Z);
			
			return true;
		}
		else if (dist < Z)
		{
			return false;
		}
		else
		{
			//dist == Z here. Don't add new path if one already exists
			return true;
		}
	}
	
	public static boolean AllReqMet(AdjList graph, ArrayList<String> req)
	{
		for (String line : req)
		{
			String[] temp = line.split(" ");
			int X = Integer.parseInt(temp[0]);
			int Y = Integer.parseInt(temp[1]);
			int Z = Integer.parseInt(temp[2]);
			
			int dist = shortestDist(graph, X, Y);
			if (dist != Z)
			{
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args)
	{
		final String filepathIn = "GraphsAsAService_Input.txt";
		final String filepathOut = "GraphsAsAService_Output.txt";

		//READ file
		try
		{
			FileReader fr = new FileReader(filepathIn);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(filepathOut, false);
			BufferedWriter bw = new BufferedWriter(fw);

			//Solve each case
			int T = Integer.parseInt(br.readLine());
			for (int i = 1; i <= T; i++)
			{				
				String[] arr = br.readLine().split(" ");
				int N = Integer.parseInt(arr[0]);
				int M = Integer.parseInt(arr[1]);
				ArrayList<String> E = new ArrayList<>();				
				AdjList graph = new AdjList(N);
				boolean metReq = true;	
				
				ArrayList<String> req = new ArrayList<>();
				
				//Attempt to meet each requirement
				for (int j = 1; j <= M; j++)
				{
					String line = br.readLine();
					req.add(line);
					
					arr = line.split(" ");		
					int X = Integer.parseInt(arr[0]);
					int Y = Integer.parseInt(arr[1]);
					int Z = Integer.parseInt(arr[2]);

					if (!MeetReq(graph, E, X, Y, Z))
					{
						metReq = false;			
						//Advance to next case
						for (int k = j + 1; k <= M; k++)
						{
							br.readLine();
						}				
						break;
					}
				}

				//Check all requirements are met
				if (metReq && !AllReqMet(graph, req))
				{
					metReq = false;
				}
				
				//Verdict
				bw.write("Case #" + i + ": ");
				if (metReq)
				{
					bw.write(Integer.toString(E.size()));
					bw.newLine();

					//Output each edge info
					for (int j = 0; j < E.size(); j++)
					{
						bw.write(E.get(j));
						bw.newLine();
					}
				}
				else
				{
					bw.write("Impossible");
					bw.newLine();
				}
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