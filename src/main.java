
public class main {

	public static void perceptron() {
		Perceptron p1 = new Perceptron(1);
        Dataset d1 = new Dataset("data/gaussian.txt", true);
        PerceptronPlotter pp1 = new PerceptronPlotter("1", "-1");

        p1.updateWeights(d1);

        pp1.plotData(d1, p1);
        // Equation for decision line is y = -8x
	}

	public static void perceptronDigits() {
		Dataset d2 = new Dataset("data/train_digits.txt", true);
        DigitFrame df1 = new DigitFrame("train_digits 1");
        DigitFrame df2 = new DigitFrame("train_digits 0");

//        df1.showImage(d2.get(50), 8, 8);
//        df2.showImage(d2.get(49), 8, 8);
        // Amount of zeros = 50, Amount of ones 50

        Perceptron p2 = new Perceptron(1);
        p2.updateWeights(d2);
        p2.updateWeights(d2);

        DigitFrame df3 = new DigitFrame("weights");
//        df3.showImage(p2.getWeights(), 8, 8);

        Dataset d3 = new Dataset("data/test_digits.txt", true);
        double error = 0.0;
        for (int i = 0; i < d3.size(); i++) {
            if (d3.get(i).getLabel() != p2.predict(d3.get(i)))
                error++;
        }
        error = error/d3.size();
        System.out.println(error);
    }

	public static void nearestNeighbour() {
		// add code here
	}
	
	public static void nearestNeighbourDigits() {
		// add code here
	}

	public static void main(String[] args) {
//		perceptron();
		perceptronDigits();
		//nearestNeighbour();
		//nearestNeighbourDigits();
	}

}
