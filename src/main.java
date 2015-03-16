import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Scanner;
import java.util.Set;

public class main {

	public static void main(String[] args) {
		APriori ap1 = new APriori(3);
        try {
            Scanner scanner = new Scanner(new File("input_example/basket.txt"));
            while (scanner.hasNextLine())
                ap1.addBasket(scanner.nextLine());
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(ap1.getFrequentSets(1));
        System.out.println(ap1.constructCandidates(ap1.getFrequentSets(1), 2));

        // 5.1 Frequent doubletons:
        Set<StringSet> doubletons = ap1.getFrequentSets(2);
        doubletons.removeAll(ap1.getFrequentSets(1));
        System.out.println(doubletons);

        // 5.2 k+1, once for every countCandidates and once to initialize in constructCandidates

        // 5.3 Then you need to check all the possible combinations of subsets, if you have a large dataset
        // the amount of subsets gets to big.
    }

}
