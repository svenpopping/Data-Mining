import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class APriori {
	/**
	 * The dataset. Each basket could be thought of as sentences, each entry as words.
	 */
	protected List<Set<String>> baskets;

	/**
	 * The threshold for when an item is frequent.
	 */
	protected int supportThreshold;

	/**
	 * Constructor for the A-Priori class.
	 * @param st The support threshold value.
	 */
	public APriori(int st) {
		supportThreshold = st;
		baskets = new ArrayList<Set<String>>();
	}

	/**
	 * Adds a basket (sentence) to the list of baskets.
	 * @param basket The basket to add.
	 */
	public void addBasket(String basket) {
		baskets.add(new HashSet<String>(Arrays.asList(basket.toLowerCase().split(" "))));
	}

	/**
	 * Computes all subsets of size k from set.
	 * @param set The set of which the subsets should be computed.
	 * @param k The size of the computed subsets.
	 * @return A set of subsets.
	 */
	public static Set<StringSet> getSubsets(Set<String> set, int k) {
		Set<StringSet> result = new HashSet<StringSet>();
		StringSet setList = new StringSet(set);

		StringSet subset = new StringSet();
		getSubsets_(setList, subset, k, result);
		return result;
	}

	/**
	 * Recursive method for getSubsets.
	 */
	private static void getSubsets_(StringSet set, StringSet subset, int subsetSize, Set<StringSet> candidates) {
		if (subsetSize == subset.size()) {
			candidates.add((StringSet)subset.clone());
		} else {
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String s = it.next();
				subset.add(s);
				StringSet clone = new StringSet(set);
				clone.remove(s);
				getSubsets_(clone, subset, subsetSize, candidates);
				subset.remove(s);
			}
		}
	}

	/**
	 * Constructs candidates based on the previous set of frequent itemsets (L_k-1)
	 * @param filteredCandidates The set of frequent k-1 itemsets
	 * @return A set of candidate itemsets of size k
	 */
	public Set<StringSet> constructCandidates(Set<StringSet> filteredCandidates, int k) {
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
            StringSet[] p = filteredCandidates.toArray(new StringSet[filteredCandidates.size()]);
            for (int i = 0; i < p.length; i++) {
                for (int j = i + 1; j < p.length; j++) {
                    StringSet current = new StringSet(p[i]);
                    current.addAll(p[j]);
                    if (current.size() == k)
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
	public Map<StringSet, Integer> countCandidates(Set<StringSet> candidates, int k) {
		// the result
		Map<StringSet, Integer> candidatesCount = new HashMap<StringSet, Integer>();

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

		return candidatesCount;
	}


	/**
	 * Removes those candidates that have a support value lower than supportThreshold.
	 * @param candidatesCount The map of sets to support value.
	 * @return A set of filtered candidates.
	 */
	public Set<StringSet> filterCandidates(Map<StringSet, Integer> candidatesCount) {
		// the result
		Set<StringSet> filteredCandidates = new HashSet<StringSet>();
        Iterator<Entry<StringSet,Integer>> entryIterator = candidatesCount.entrySet().iterator();

        while (entryIterator.hasNext()){
            Entry<StringSet,Integer> current = entryIterator.next();
            if (current.getValue() >= supportThreshold)
                filteredCandidates.add(current.getKey());
        }

		return filteredCandidates;
	}

	/**
	 * Calculates frequent sets of size k from the baskets.
	 * @param k The size of the frequent sets.
	 * @return Set of frequent itemsets with size k
	 */
	public Set<StringSet> getFrequentSets(int k) {
		// the result
		Set<StringSet> filteredCandidates = null;
        for (int i = 1; i <= k; i++) {
            Set<StringSet> construct = constructCandidates(filteredCandidates,i);
            Map<StringSet,Integer> count = countCandidates(construct,i);
            Set<StringSet> filter = filterCandidates(count);
            try {
                filteredCandidates.addAll(filter);
            } catch (Exception e) {
                filteredCandidates = filter;
            }

        }

		return filteredCandidates;
	}


}
