import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author hansgaiser
 *
 */
public class LSH {
	
	/**
	 * Computes the candidate pairs using the LSH technique.
	 * @param mhs The minhash signature object.
	 * @param bs The number of buckets to be used in the LSH table.
	 * @param r The number of rows per band to be used.
	 * @return Returns a set of indices pairs that are candidate to being similar.
	 */
	public static Set<List<Integer>> computeCandidates(MinHashSignature mhs, int bs, int r) {
		// assert that the number of rows can be evenly divided by r
		assert(mhs.rows() % r == 0);
		
		// the number of bands
		int b = mhs.rows() / r;
		
		// the result
		Set<List<Integer>> candidates = new HashSet<List<Integer>>();
		
		// ADD CODE HERE
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < bs; i++) {
            buckets.add(new ArrayList<Integer>());
        }
        ArrayList<Integer> hashes = new ArrayList<Integer>();

        for (int i = 0; i < b; i = i + r) {
            for (int j = 0; j < mhs.cols(); j++) {
                String s = mhs.colSegment(j, i, i + r);

                int hash = s.hashCode();
                int indexHash = hashes.indexOf(hash);

                if (indexHash != -1) {
                    buckets.get(indexHash).add(j);
                } else {
                    hashes.add(hash);
                    buckets.get(hashes.indexOf(hash)).add(j);
                }
            }
        }

        for (int i = 0; i < b; i++) {
            for (int j = 0; j < mhs.cols(); j++) {
                String s = mhs.colSegment(j, i, i + r);

                int hash = s.hashCode();
                int indexHash = hashes.indexOf(hash);

                if (indexHash != -1 && buckets.get(indexHash).size() > 1) {
                    candidates.add(buckets.get(indexHash));
                }
            }
        }
		return candidates;
	}
	
}
