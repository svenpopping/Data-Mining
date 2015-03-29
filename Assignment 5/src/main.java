
public class main {
	
	private static void hierarchical() {
//		add code here
        HierarchicalClusteringPlotter plotter2 = new HierarchicalClusteringPlotter(3, "data/cluster.txt");

        // 4.1: The leaves, all data points. The root is cluster of all data points. (Dendrogram)

        // 4.2: K is the number of roots in the final tree.

//        HierarchicalClusteringPlotter plotter1 = new HierarchicalClusteringPlotter(3, "data/cluster_lines.txt");

        // 5.1: Because the distances between the lines are smaller than the distances between the clusters in the same line.
        // Clusters are made between the lines.

        // 7.1: Yes its better, because it now checks for the closest distance between two clusters. The lines are now correctly
        // clustered.
	}
	
	private static void hierarchicalDigits() {
		HierarchicalClustering clustering = new HierarchicalClustering(10, "data/train_digits.txt");
        while (clustering.getClusterSize() > 10) {
            clustering.update();
        }

        for (int i = 0; i < 10; i++) {
            DigitFrame digitFrame = new DigitFrame("test", clustering.getCluster(i).centroid(), 8, 8);
        }
	}
	
	private static void kmeans() {
        // 1.1: Select the k-points that have largest underlying distances between each other.
        KMeansPlotter plotter2 = new KMeansPlotter(3, "data/cluster.txt");
	}
	
	private static void kmeansTuneK() {
        for (int i = 1; i <= 10; i++) {
            KMeans kmeans = new KMeans(i, "data/cluster.txt");

            for (int j = 0; j < 10; j++) {
                kmeans.update();
            }

            System.out.println("-------------------- /|{k = " + i + "}|\\ --------------------");
            for (Cluster featureVectors : kmeans.getClusters()) {
                System.out.print(featureVectors.calculateAverageRSS() + " | ");
            }
            System.out.println();
        }
    }
	
	private static void kmeansDigits() {
        KMeans clustering = new KMeans(10, "data/train_digits.txt");
        for (int i = 0; i < 1000; i++) {
            clustering.update();
        }

        for (int i = 0; i < 10; i++) {
            DigitFrame digitFrame = new DigitFrame("test", clustering.getCluster(i).centroid(), 8, 8);
        }
	}

	public static void main(String[] args) {
//		hierarchical();
//		hierarchicalDigits();
		kmeans();
//		kmeansTuneK();
//		kmeansDigits();
	}

}
