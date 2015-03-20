import java.util.*;


public class PCY extends APriori {

	private int bucketSize;

	private List<Integer> buckets = null;

	/**
	 * Constructor of the PCY algorithm class.
	 * @param s The support threshold.
	 * @param bs The bucket size.
	 */
	public PCY(int s, int bs) {
		super(s);

		bucketSize = bs;
	}

	/**
	 * Constructs candidates based on the previous set of frequent itemsets (L_k-1)
	 * @param filteredCandidates The set of frequent k-1 itemsets
	 * @return A set of candidate itemsets of size k
	 */
	@Override
	public Set<StringSet> constructCandidates(Set<StringSet> filteredCandidates, int k) {
		// PCY only acts on the frequent pairs
		if (k != 2)
			return super.constructCandidates(filteredCandidates, k);
		
		// the result
		Set<StringSet> candidates = new HashSet<StringSet>();

		if (filteredCandidates == null) {
			// add all initial words to the items set
			for (Set<String> basket: baskets) {
				for (String s: basket) {
					StringSet sl = new StringSet();
					sl.add(s);
					candidates.add(sl);
				}
			}
		} else {
            Object[] p = filteredCandidates.toArray();
            for (int i = 0; i < p.length; i++) {
                for (int j = i + 1; j < p.length; j++) {
                    StringSet current = new StringSet((StringSet) p[i]);
                    current.addAll((StringSet) p[j]);
                    if (current.size() == k && buckets.get(Math.abs(current.hashCode()) % bucketSize) >= supportThreshold)
                        candidates.add(current);
                }
            }
		}

		return candidates;
	}

	/**
	 * Calculates the support value of each set in candidates.
	 * @param candidates The set of candidate sets.
	 * @param k The size of the candidates.
	 * @return A mapping of sets to support value.
	 */
	@Override
	public Map<StringSet, Integer> countCandidates(Set<StringSet> candidates, int k) {
		// PCY only acts on the frequent pairs
		if (k != 1)
			return super.countCandidates(candidates, k);
		
		// initialize the buckets
		buckets = new ArrayList<Integer>(bucketSize);
		for (int i = 0; i < bucketSize; i++)
			buckets.add(0);

		// the result
		Map<StringSet, Integer> candidatesCount = new HashMap<StringSet, Integer>();

        Set<StringSet> singletons = new HashSet<StringSet>();
        for (Set<String> basket: baskets) {
            for (String s: basket) {
                StringSet sl = new StringSet();
                sl.add(s);
                singletons.add(sl);
            }
        }

        for (int i = 0; i < baskets.size(); i++) {
            Set<StringSet> subSets = getSubsets(baskets.get(i),k);
            Iterator<StringSet> subSetIterator = subSets.iterator();
            while (subSetIterator.hasNext()){
                StringSet currentSet = subSetIterator.next();
                if (candidates.contains(currentSet)) {
                    if (candidatesCount.containsKey(currentSet))
                        candidatesCount.put(currentSet, candidatesCount.get(currentSet) + 1);
                    else
                        candidatesCount.put(currentSet, 1);
                }
            }
        }

        StringSet[] singletonArray = singletons.toArray(new StringSet[singletons.size()]);
        for (int i = 0; i < singletonArray.length; i++) {
            StringSet s1 = singletonArray[i];
            for (int j = i + 1; j < singletonArray.length; j++) {
                StringSet s2 = singletonArray[j];
                StringSet current = new StringSet();
                current.add(s1.toString().replaceAll("[\\[\\]]",""));
                current.add(s2.toString().replaceAll("[\\[\\]]",""));
                int hashCode = Math.abs(current.hashCode()) % bucketSize;
                buckets.set(hashCode,buckets.get(hashCode) + 1);
            }
        }

        return candidatesCount;
	}

}
