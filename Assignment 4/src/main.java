import java.io.File;
import java.util.Scanner;
import java.util.Set;

public class main {

	public static void main(String[] args) {
		APriori ap = new APriori(3);
        try {
            Scanner scanner = new Scanner(new File("input_example/basket.txt"));
            while (scanner.hasNextLine())
                ap.addBasket(scanner.nextLine());
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Frequent sets AP k=1 = " +  ap.getFrequentSets(1));
        System.out.println("Frequent sets AP k=2 = " +  ap.getFrequentSets(2));
        System.out.println("Candidate doubletons AP = " + ap.constructCandidates(ap.getFrequentSets(1), 2));

        // 5.1 Frequent doubletons:
        Set<StringSet> doubletons = ap.getFrequentSets(2);
        doubletons.removeAll(ap.getFrequentSets(1));
        System.out.println("Frequent doubletons AP = " + doubletons + "\n");

        // 5.2 k+1, once for every countCandidates and once to initialize in constructCandidates

        // 5.3 Then you need to check all the possible combinations of subsets, if you have a large dataset
        // the amount of subsets gets to big.

        PCY pcy = new PCY(3,1024);
        try {
            Scanner scanner = new Scanner(new File("input_example/basket.txt"));
            while (scanner.hasNextLine())
                pcy.addBasket(scanner.nextLine());
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Frequent sets PCY k=1 = " + pcy.getFrequentSets(1));
        System.out.println("Frequent sets PCY k=2 = " + pcy.getFrequentSets(2));

        // 3.1 A 10% decrease in candidates.
        System.out.println("Candidate doubletons PCY = " + pcy.constructCandidates(pcy.getFrequentSets(1), 2));

        // 3.2 It has less candidates to count in countCandidates, so it's faster.

        // 3.3 If the bucketSize is to low, then two pairs can hash in the same bucket and their supports
        // are added together. In this scenario, you would still have more candidates then needed.
    }

}
