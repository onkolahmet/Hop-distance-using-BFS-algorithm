
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Part1_CSE2046 {
	public Queue<Node> queue;
	static List<Node> neighbours1 = new ArrayList<Node>();
	static List<Node> neighbours2 = new ArrayList<Node>();
	static class Node {
		double x;
		double y;
		int data;
		double range;
		int hopDistance;
		boolean visited;
		List<Node> neighbours;
		Node(double x, double y, double range, int hopDistance, int data) {
			this.data = data;
			this.x = x;
			this.y = y;
			this.range = range;
			this.hopDistance = hopDistance;
			this.neighbours = new ArrayList<>();
		}
		public void addneighbours(Node neighbourNode) {
			this.neighbours.add(neighbourNode);
		}
		public List<Node> getNeighbours() {
			return neighbours;
		}
		public void setNeighbours(List<Node> neighbours) {
			this.neighbours = neighbours;
		}
		public void setHopDistance(int distance) {
			this.hopDistance = distance;
		}
	}
	public Part1_CSE2046() {
		queue = new LinkedList<Node>();
	}
	public void bfs(Node node, FileWriter writer) throws IOException { // prints hopDistance values from root laptop depending on BFS search algorithm
		queue.add(node);
		node.visited = true;
		while (!queue.isEmpty()) {

			Node element = queue.remove();
			try {
				 writer.write(element.hopDistance + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<Node> neighbours = element.getNeighbours();
			for (int i = 0; i < neighbours.size(); i++) {
				Node n = neighbours.get(i);
				if (n != null && !n.visited) {
					queue.add(n);
					n.visited = true;
				}
			}

		}
	writer.close();
	}
	public static void main(String args[]) {
		String FileName = "" ;
		if(args.length > 0) {
		FileName += args[0] ;
		}
		else {
		System.out.println("Wrong file name.");
		System.exit(0);
		}
		Node root = null;
		try {
			
			File file = new File(FileName);  // it can be test1.txt, test5.txt, test6.txt etc.
			FileReader fr = new FileReader(file);
			FileReader reader = new FileReader(FileName); 
			BufferedReader br = new BufferedReader(fr);  // corresponding(File,FileReader,BufferedReader,FileWriter) ->
			FileWriter writer = new FileWriter("output.txt", false); //library functions for reading and writing text files
			String line = "";
			int i = 0, n = 0, numOfVar;
			while ((line = br.readLine()) != null) { // takes whole line from text file
				if (!line.contains("#") && !line.equals("")) { //skips comment lines 
					if (i == 0) {
						numOfVar = Integer.parseInt(line); //assigning number of variables from input
						i++;
					} else if (i == 1) {
						String splitArr[] = line.split("\t");
						double x = Double.parseDouble(splitArr[0]);
						double y = Double.parseDouble(splitArr[1]);
						double range = Double.parseDouble(splitArr[2]);
						int hopDistance = 0;
						int data = 0;
						root = new Node(x, y, range, hopDistance, data);  //creating zeroth element so we can search the graph using BFS
						i++;
					} else if (i != 0 && i != 1) { //takes ith element after first laptop and works on them whether they are connected with root laptop or not
						String splitArr[] = line.split("\t");
						double x = Double.parseDouble(splitArr[0]);
						double y = Double.parseDouble(splitArr[1]);
						double range = Double.parseDouble(splitArr[2]);
						int hopDistance = 0;
						int data = i - 1;
						double dis = Math.sqrt((x - root.x) * (x - root.x) + (y - root.y) * (y - root.y)); // calculates the distance between ith laptop and the root laptop
						if ((range + root.range) >= dis) { // checks if the total range of corresponding laptops is enough for communicate depending on comparison between total range and distance
							hopDistance = 1; // assign 1 to hopDistance since they are connected directly
							Node newNode = new Node(x, y, range, hopDistance, data);
							root.addneighbours(newNode); //adds new node to root laptop with corresponding data
						} else if ((range + root.range) < dis) { // if it is not enough assign zero to hopDistance for initialization
							Node newNode = new Node(x, y, range, hopDistance, data);
							root.addneighbours(newNode); //adds new node to root laptop with corresponding data
						}
						i++;
					}

				}

			}
			reader.close();
			br.close();
			File file2 = new File(FileName); // it can be test3.txt, test4.txt, test6.txt etc.
			FileReader fr2 = new FileReader(file2); // opening the file for the second time that provides the ability of looking for the undirected connections with root laptop (cases for hopDistance != 1 & hopDistance > 1)  
			FileReader reader2 = new FileReader(FileName);
			BufferedReader br2 = new BufferedReader(fr2);
			while ((line = br2.readLine()) != null) {
				if (!line.contains("#") && !line.equals("")) {
					if (n == 0) {
						n++;
					} else if (n == 1) {
						n++;
					} else if (n != 0 && n != 1) {
						neighbours1 = root.getNeighbours(); // getting root's directed(hopDistance = 1) neighbour laptops so be able to look for other connections
						neighbours2 = root.getNeighbours();
						for (int f = 0; f < neighbours1.size(); f++) {
							String splitArr[] = line.split("\t");
							double x = Double.parseDouble(splitArr[0]);
							double y = Double.parseDouble(splitArr[1]);
							double range = Double.parseDouble(splitArr[2]);
							int hopDistance = 0;
							int data = n - 1;
							double dis = Math.sqrt((x - neighbours1.get(f).x) * (x - neighbours1.get(f).x)
									+ (y - neighbours1.get(f).y) * (y - neighbours1.get(f).y));
							if ((range + neighbours1.get(f).range) >= dis && dis != 0) {
								if (neighbours1.get(f).hopDistance != 0) { //checks if the corresponding laptop and ith neighbour laptop is connected with the (total range ~ distance) comparison
									hopDistance = neighbours1.get(f).hopDistance + 1;
									for (int m = 0; m < neighbours2.size(); m++) { 
										if (x == neighbours2.get(m).x && y == neighbours2.get(m).y
												&& range == neighbours2.get(m).range
												&& neighbours2.get(m).hopDistance != 1) { // looks for that item in the neighbors list so if x,y and range values are matches ; updates hopDistance value for it
											neighbours2.get(m).setHopDistance(hopDistance); //updates hopDistance for corresponding laptop
											break;
										}
									}
								break ;
								}
							}
						}
						n++;
					}
				}

			}
			String values = "";
			for (int e = 0; e < neighbours2.size(); e++) {
				values += neighbours2.get(e).hopDistance;
			}

			if (values.contains("1")) {
				for (int a = 0; a < neighbours2.size(); a++) { // this nested for loops part checks whether there are  laptops that have a path from root laptop and still has the value of zero for hopDistance					
					if (neighbours2.get(a).hopDistance == 0) {  // if so update those hopDistances for corresponding laptops with their valid values
						for (int b = 0; b < neighbours2.size(); b++) {
							double dis = Math.sqrt((neighbours2.get(a).x - neighbours2.get(b).x)
									* (neighbours2.get(a).x - neighbours2.get(b).x)
									+ (neighbours2.get(a).y - neighbours2.get(b).y)
											* (neighbours2.get(a).y - neighbours2.get(b).y));
							if ((neighbours2.get(a).range + neighbours2.get(b).range) >= dis && dis != 0 
									&& neighbours2.get(b).hopDistance != 0) {
								int hopDistance1 = neighbours2.get(b).hopDistance + 1;
								neighbours2.get(a).setHopDistance(hopDistance1);  // changes hopDistance value with the updated one
							}
						}
					}
				}
			}
			Part1_CSE2046 bfsExample = new Part1_CSE2046();
			bfsExample.bfs(root,writer);
			writer.close();
			reader2.close();
			
			br2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
}

