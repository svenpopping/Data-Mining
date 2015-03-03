import java.util.*;
import java.util.Map.Entry;


public class NearestNeighbour {
	/**
	 * List of training vectors.
	 */
	Dataset dataset;

	/**
	 * Constructor.
	 */
	public NearestNeighbour() {
		dataset = new Dataset();
	}

	/**
	 * Reads in the data from a text file.
	 * @param fileName
	 */
	public void readData(String fileName) {
		dataset.readData(fileName, false);
	}

	/**
	 * 
	 * @return
	 */
	public Dataset getDataset() {
		return dataset;
	}

	/**
	 * Classifies a query. Finds the k nearest neighbours and scales them if necessary.
	 * @param features The query features.
	 * @param k The number of neighbours to select.
	 * @return Returns the label assigned to the query.
	 */
	public int predict(List<Double> features, int k) {
		// the result
		int label = -1;

		// add code here
        Map<Double, FeatureVector> distances = new HashMap<Double, FeatureVector>(); // Sort them by distance, smallest distances first.

        for (int i = 0; i < this.getDataset().size(); i++) { //foreach element in the dataset.
            double distance = this.getDataset().get(i).distance(features); // Calculate the distance

            distances.put(distance, this.getDataset().get(i));
        }

        System.out.println(distances.keySet());

        FeatureVector[] values = (FeatureVector[]) distances.values().toArray();
        FeatureVector[] nearestNeighbours = new FeatureVector[k];
        for (int i = 0; i < k; i++) {
            nearestNeighbours[i] = values[i];
        }

        int one = 0;
        int two = 0;
        for (int i = 0; i < nearestNeighbours.length; i++) {
            if (nearestNeighbours[i].getLabel() == 1)
                one ++;
            else if (nearestNeighbours[i].getLabel() == -1)
                two++;
        }

        label = (one > two) ? 1 : -1;
        // Select k of the nearest objects.
        // Calculate which label has the highest majority vote.
        // Assign the label to that class.
        return label;
	}
}
