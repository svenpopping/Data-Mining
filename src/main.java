import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;


public class main {

    // 0.0: M * v = lambda * v
	/**
	 * Computes nrVectors eigen vectors of m where e is the
	 * stopping criterion for the norm of the difference for an
	 * eigenvector in between two rounds.
	 * @param m The matrix of which eigenvectors should be computed.
	 * @param nrVectors The number of eigenvectors to compute.
	 * @param e The threshold for the stopping criterion.
	 * @return A list of eigenvectors in m.
	 */
	public static List<Matrix> powerIteration(Matrix m, int nrVectors, double e) {
		assert(m.cols() == m.rows() && m.cols() >= nrVectors);
        List<Matrix> eigenvectors = new ArrayList<Matrix>();

        for (int i = 0; i < nrVectors; i++) {
            Matrix v = new Matrix(m.rows(), 1, 1);
            double stop;
            double diff = Double.MAX_VALUE;

            do {
                Matrix oldV = v;
                Matrix current = m.multWithVector(v);
                v = current.divide(current.norm());

                double oldDiff = diff;
                diff = v.subtractRow(oldV).norm();
                stop = Math.abs(oldDiff-diff);
            } while (stop >= e);

            eigenvectors.add(v);

            double lambda = v.transpose().multWithVector(m.multWithVector(v)).get(0, 0);
            m = m.subtract(v.multVectors(v.transpose()).multiply(lambda));
        }
        return eigenvectors;
	}
	
	/**
	 * Computes two eigenvectors of a small matrix example.
	 */
	public static void powerIterationTest() {
		Matrix test = Matrix.readData("data/matrix.txt");
        System.out.println(powerIteration(test,2,0.0001));

        // 3.1: There can be at most D eigenvectors
        // 3.2: They are decreasing. They indicate the importance of the eigenvector.
        // 3.3: In assignment 3, we used it for PageRank.
    }

	/**
	 * Computes the principal components from a Gaussian
	 * distributed dataset.
	 */
	public static void pca() {
		Matrix X = Matrix.readData("data/gaussian.txt");
        PCAPlotter plotter1 = new PCAPlotter();
        plotter1.plotData(X);


        Matrix mX = X.meanRow();
        Matrix Y = X.subtractRow(mX);
        Matrix Yt = Y.transpose();
        Matrix covariance = Yt.multiply(1.0/X.rows()).dot(Y);
        List<Matrix> eigenVectors = powerIteration(covariance,covariance.cols(),0.0001);
        plotter1.plotEigenvectors(eigenVectors);

        // 2.1: The "x"-axis This was the first pc.
    }
	
	/**
	 * Computes some principal components from a dataset
	 * of face images.
	 */
	public static void pcaFaces() {
		Matrix X = Matrix.readData("data/faces.txt");

        Matrix mX = X.meanRow();
        Matrix Y = X.subtractRow(mX);
        Matrix Yt = Y.transpose();
        Matrix covariance = Yt.multiply(1.0/X.rows()).dot(Y);
        List<Matrix> eigenVectors = powerIteration(covariance,10,0.0001);

        for (int i = 0; i < 10; i++) {
            ImageFrame faces = new ImageFrame("Faces " + i,eigenVectors.get(i),32,32);
        }

        // 4.1: The importance of the different parts of a face during face recognition.
    }

	public static void main(String[] args) {
//		powerIterationTest();
//		pca();
//		pcaFaces();
	}

}
