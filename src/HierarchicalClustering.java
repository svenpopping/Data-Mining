import java.util.ArrayList;
import java.util.List;

public class HierarchicalClustering {
	/**
	 * The number of clusters to detect.
	 */
	private int k;
	
	/**
	 * The original unclustered data.
	 */
	private Cluster data;
	
	/**
	 * A collection of clusters.
	 */
	private List<Cluster> clusters;
	
	/**
	 * Constructor.
	 * @param k The number of clusters to detect.
	 * @param fileName The filename at which the data is stored.
	 */
	public HierarchicalClustering(int k, String fileName) {
		this.k = k;
		clusters = new ArrayList<Cluster>();
		
		data = new Cluster();
		
		readData(fileName);
	}
	
	/**
	 * Reads in data from a filename.
	 * @param fileName The filename that needs to be read.
	 */
	public void readData(String fileName) {
		data.readData(fileName);
		
		for (FeatureVector fv: data) {
			Cluster c = new Cluster();
			c.add(fv);
			clusters.add(c);
		}
	}
	
	/**
	 * @return The unclustered data.
	 */
	public Cluster getData() {
		return data;
	}
	
	/**
	 * @return The clusters that have been computed so far.
	 */
	public List<Cluster> getClusters() {
		return clusters;
	}
	
	/**
	 * 
	 * @return Returns the number of clusters in the list of clusters.
	 */
	public int getClusterSize() {
		return clusters.size();
	}
	
	/**
	 * 
	 * @param i Index of the cluster that is to be retrieved.
	 * @return Returns the cluster belonging to index i.
	 */
	public Cluster getCluster(int i) {
		return clusters.get(i);
	}
	
	/**
	 * Performs one update step of the algorithm.
	 */
	public void update() {
        if (this.clusters.size() <= k) return;

        double minDistance = Double.MAX_VALUE;
        int minI = -1, minJ = -1;
        for (int i = 0; i < clusters.size(); i++) {
            for (int j = i + 1; j < clusters.size(); j++) {
//                double distance = this.clusters.get(i).meanDistanceTo(this.clusters.get(j));
                double distance = this.clusters.get(i).minDistanceTo(this.clusters.get(j));
                System.out.println(distance);
                if (distance < minDistance) {
                    minDistance = distance;
                    minI = i;
                    minJ = j;
                }
            }
        }
        this.clusters.get(minJ).addAll(this.clusters.get(minI));
        this.clusters.remove(minI);
    }
}
