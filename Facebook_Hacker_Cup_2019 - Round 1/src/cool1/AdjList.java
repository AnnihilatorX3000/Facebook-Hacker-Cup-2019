package cool1;

import java.util.LinkedList;

//Adjacency List - Note: Index # = Node #, so index 0 is left empty
public class AdjList
{
	private int size;
	private LinkedList<Edge>[] arr;
		
	@SuppressWarnings("unchecked")
	public AdjList(int size)
	{
		this.size = size;
		arr = new LinkedList[size + 1];
		for (int i = 1; i <= size; i++)
		{
			arr[i] = new LinkedList<Edge>();
		}
	}
	
	public int getSize()
	{
		return size;
	}

	public LinkedList<Edge> getEdgeList(int n)
	{
		return arr[n];
	}
	
	public void AddEdge(int source, Edge e)
	{
		//X -> Y
		arr[source].add(e);
		//X <- Y
		Edge reverse = new Edge(source, e.weight);
		arr[e.dest].add(reverse);
	}
}