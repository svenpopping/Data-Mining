import java.util.List;
import java.util.Set;


public class main {
	
	public static void exercise1_1() {
		// ADD CODE HERE
        ShingleSet s1 = new ShingleSet(5);
        ShingleSet s2 = new ShingleSet(5);

        s1.shingleString("The plane was ready for touch down");
        s2.shingleString("The quarterback scored a touchdown");

        System.out.println(s1.jaccardDistance(s2));


        ShingleSet s4 = new ShingleSet(5);
        ShingleSet s5 = new ShingleSet(5);

        s4.shingleStrippedString("The plane was ready for touch down");
        s5.shingleStrippedString("The quarterback scored a touchdown");

        System.out.println(s4.jaccardDistance(s5));
	}
	
	public static void exercise1_2() {
		// ADD CODE HERE
		ShingleSet s1 = new ShingleSet(1), s2 = new ShingleSet(1), s3 = new ShingleSet(1), s4 = new ShingleSet(1);

        s1.shingleString("ad");
        s2.shingleString("c");
        s3.shingleString("bde");
        s4.shingleString("acd");

        MinHash mh = new MinHash();
        mh.addHashFunction(new HashFunction(1, 1));
        mh.addHashFunction(new HashFunction(3, 1));

        mh.addSet(s1);
        mh.addSet(s2);
        mh.addSet(s3);
        mh.addSet(s4);

        System.out.println(mh.computeSignature());

        MinHash mh2 = new MinHash();
        mh2.addRandomHashFunctions(100);

        mh2.addSet(s1);
        mh2.addSet(s2);
        mh2.addSet(s3);
        mh2.addSet(s4);

        System.out.println(LSH.computeCandidates(mh2.computeSignature(), 1000, 5));
	    exercise1_3(mh);
	}
	
	public static void exercise1_3(MinHash mh) {
		// ADD CODE HERE
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// exercise 1.1
		exercise1_1();
		
		// exercise 1.2
		exercise1_2();
	}

}
