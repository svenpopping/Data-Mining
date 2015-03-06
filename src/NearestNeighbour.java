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

		// make HashMap to store the other objects
        Map<Double, FeatureVector> distances = new TreeMap<Double, FeatureVector>(); // Sort them by distance, smallest distances first.

        // store every object from the dataset in the TreeMap (sorted by distance)
        for (int i = 0; i < this.getDataset().size(); i++) { //foreach element in the dataset.
            double distance = this.getDataset().get(i).distance(features); // Calculate the distance
            distances.put(distance, this.getDataset().get(i));
        }


        // get the k nearest object from the TreeMap
        Collection<FeatureVector> values = distances.values();
        Collection<FeatureVector> nearestNeighbours = new ArrayList<FeatureVector>();
        Iterator<FeatureVector> valueIterator = values.iterator();
        for (int i = 0; i < k; i++) {
            nearestNeighbours.add(valueIterator.next());
        }

        // count the number of different labels
        ArrayList<Integer> labelList = new ArrayList<Integer>();
        Iterator<FeatureVector> nearestNeighboursIterator = nearestNeighbours.iterator();
        while (nearestNeighboursIterator.hasNext()) {
            FeatureVector next = nearestNeighboursIterator.next();
            labelList.add(next.getLabel());
        }

        int[] counter = new int[labelList.size() + 2];
        for (int i = 0; i < labelList.size(); i++) {
            counter[labelList.get(i)+1]++;
        }

        label = 0;
        for (int i = 0; i < counter.length; i++) {
            if (counter[i] > counter[label])
                label = i;
        }
        // return the predicted label
        return label - 1;
	}
}
