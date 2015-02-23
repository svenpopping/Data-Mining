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
		
		//exercise1_3(mh);
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
