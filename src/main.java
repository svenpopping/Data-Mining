
public class main {
	
	private static void hierarchical() {
//		add code here
//        HierarchicalClusteringPlotter plotter = new HierarchicalClusteringPlotter(3, "data/cluster.txt");

        // 4.1: The leaves, all data points. The root is cluster of all data points. (Dendrogram)

        // 4.2: K is the number of roots in the final tree.

        HierarchicalClusteringPlotter plotter = new HierarchicalClusteringPlotter(3, "data/cluster_lines.txt");

        // 5.1: Because the distances between the lines are smaller than the distances between the clusters in the same line.
        // Clusters are made between the lines.

        // 7.1: Yes its better, because it now checks for the closest distance between two clusters. The lines are now correctly
        // clustered.
	}
	
	private static void hierarchicalDigits() {
		// add code here
	}
	
	private static void kmeans() {
		// add code here
	}
	
	private static void kmeansTuneK() {
		// add code here
	}
	
	private static void kmeansDigits() {
		// add code here
	}

	public static void main(String[] args) {
		hierarchical();
		//hierarchicalDigits();
		//kmeans();
		//kmeansTuneK();
		//kmeansDigits();
	}

}
