import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.System.arraycopy;
import static java.lang.System.exit;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.fill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class TreesAsAServiceSOLUTION {

	// Discuss this round on Codeforces: https://codeforces.com/blog/entry/67641

	static class IntList {

		int data[] = new int[3];
		int size = 0;

		boolean isEmpty() {
			return size == 0;
		}

		int size() {
			return size;
		}

		int get(int index) {
			if (index < 0 || index >= size) {
				throw new IndexOutOfBoundsException();
			}
			return data[index];
		}

		void clear() {
			size = 0;
		}

		void set(int index, int value) {
			if (index < 0 || index >= size) {
				throw new IndexOutOfBoundsException();
			}
			data[index] = value;
		}

		void expand() {
			if (size >= data.length) {
				data = copyOf(data, (data.length << 1) + 1);
			}
		}

		void insert(int index, int value) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}
			expand();
			arraycopy(data, index, data, index + 1, size++ - index);
			data[index] = value;
		}

		int delete(int index) {
			if (index < 0 || index >= size) {
				throw new IndexOutOfBoundsException();
			}
			int value = data[index];
			arraycopy(data, index + 1, data, index, --size - index);
			return value;
		}

		void push(int value) {
			expand();
			data[size++] = value;
		}

		int pop() {
			if (size == 0) {
				throw new NoSuchElementException();
			}
			return data[--size];
		}

		void unshift(int value) {
			expand();
			arraycopy(data, 0, data, 1, size++);
			data[0] = value;
		}

		int shift() {
			if (size == 0) {
				throw new NoSuchElementException();
			}
			int value = data[0];
			arraycopy(data, 1, data, 0, --size);
			return value;
		}
	}

	static class Req {
		final int l, r, lca;

		Req(int l, int r, int lca) {
			this.l = l;
			this.r = r;
			this.lca = lca;
		}
	}

	static IntList edges[];
	static int group[], ans[];

	static void solve() throws Exception {
		int n = scanInt(), m = scanInt();
		IntList nodes = new IntList();
		edges = new IntList[n];
		for (int i = 0; i < n; i++) {
			nodes.push(i);
			edges[i] = new IntList();
		}
		List<Req> reqs = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			reqs.add(new Req(scanInt() - 1, scanInt() - 1, scanInt() - 1));
		}
		group = new int[n];
		ans = new int[n];
		if (solve(nodes, reqs, -1)) {
			printCase();
			for (int i = 0; i < n; i++) {
				out.print((ans[i] + 1) + " ");
			}
			out.println();
		} else {
			printCase();
			out.println("Impossible");
		}
	}

	static boolean solve(IntList nodes, List<Req> reqs, int parent) {
		root: for (int iRoot = 0; iRoot < nodes.size; iRoot++) {
			int root = nodes.data[iRoot];
			for (int i = 0; i < nodes.size; i++) {
				if (i != iRoot) {
					edges[nodes.data[i]].clear();
				}
			}
			for (int i = 0; i < reqs.size(); i++) {
				Req r = reqs.get(i);
				if (r.lca != root) {
					if (r.l == root || r.r == root) {
						continue root;
					} else {
						edges[r.l].push(r.r);
						edges[r.l].push(r.lca);
						edges[r.r].push(r.l);
						edges[r.r].push(r.lca);
						edges[r.lca].push(r.l);
						edges[r.lca].push(r.r);
					}
				}
			}
			fill(group, -1);
			int ngroups = 0;
			for (int i = 0; i < nodes.size; i++) {
				if (i != iRoot && group[nodes.data[i]] < 0) {
					dfs(nodes.data[i], ngroups++);
				}
			}
			for (int i = 0; i < reqs.size(); i++) {
				Req r = reqs.get(i);
				if (r.lca == root && group[r.l] == group[r.r]) {
					continue root;
				}
			}
			IntList groups[] = new IntList[ngroups];
			List<Req> groupReqs[] = new List[ngroups];
			for (int i = 0; i < ngroups; i++) {
				groups[i] = new IntList();
				groupReqs[i] = new ArrayList<>();
			}
			for (int i = 0; i < nodes.size; i++) {
				if (i != iRoot) {
					int ii = nodes.data[i];
					groups[group[ii]].push(ii);
				}
			}
			for (int i = 0; i < reqs.size(); i++) {
				Req r = reqs.get(i);
				int g = group[r.lca];
				if (g >= 0) {
					groupReqs[g].add(r);
				}
			}
			for (int i = 0; i < ngroups; i++) {
				if (!solve(groups[i], groupReqs[i], root)) {
					return false;
				}
			}
			ans[root] = parent;
			return true;
		}
		return false;
	}

	static void dfs(int cur, int cgroup) {
		if (group[cur] >= 0) {
			return;
		}
		group[cur] = cgroup;
		IntList e = edges[cur];
		for (int i = 0; i < e.size; i++) {
			dfs(e.data[i], cgroup);
		}
	}

	static int scanInt() throws IOException {
		return parseInt(scanString());
	}

	static long scanLong() throws IOException {
		return parseLong(scanString());
	}

	static String scanString() throws IOException {
		while (tok == null || !tok.hasMoreTokens()) {
			tok = new StringTokenizer(in.readLine());
		}
		return tok.nextToken();
	}

	static void printCase() {
		out.print("Case #" + test + ": ");
	}

	static void printlnCase() {
		out.println("Case #" + test + ":");
	}

	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok;
	static int test;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			int tests = scanInt();
			for (test = 1; test <= tests; test++) {
				solve();
			}
			in.close();
			out.close();
		} catch (Throwable e) {
			e.printStackTrace();
			exit(1);
		}
	}
}