package ti2736c;

import java.util.HashMap;
import java.util.Locale;

public class main {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		// Create user list, movie list, and list of ratings
		UserList userList = new UserList();
		userList.readFile("data/users.csv");
		MovieList movieList = new MovieList();
		movieList.readFile("data/movies.csv");
		RatingList ratings = new RatingList();
		ratings.readFile("data/ratings.csv", userList, movieList);

		// Read list of ratings we need to predict
		RatingList predRatings = new RatingList();
		predRatings.readFile("data/predictions.csv", userList, movieList);

		// Perform rating predictions
		predictRatings(userList, movieList, ratings, predRatings);

		// Write result file
		predRatings.writeResultsFile("submission.csv");
	}

    public static RatingList predictRatings(UserList userList,
            MovieList movieList, RatingList ratingList, RatingList predRatings) {

        for (int i = 0; i < predRatings.size(); i++) {
            Movie currentMovie = predRatings.get(i).getMovie();
            User currentUser = predRatings.get(i).getUser();
            RatingList eqMovieRatings = ratingList.movieRatings(currentMovie);
            double result = eqMovieRatings.expectedRating(predRatings.get(i));
            predRatings.get(i).setRating(result);

            System.out.println(i);
        }

        // Return predictions
        return predRatings;
    }

//	public static RatingList predictRatings(UserList userList,
//			MovieList movieList, RatingList ratingList, RatingList predRatings) {
//
//        HashMap<String,Double> movieMeans = new HashMap<String, Double>();
//        for (int i = 0; i < predRatings.size(); i++) {
//            if (movieMeans.get(predRatings.get(i).getMovie().getTitle()) == null) {
//                Movie currentMovie = predRatings.get(i).getMovie();
//                RatingList eqMovieRatings = ratingList.movieRatings(currentMovie);
//                double mean = eqMovieRatings.meanRating();
//                movieMeans.put(predRatings.get(i).getMovie().getTitle(),mean);
//            }
//
//
//            predRatings.get(i).setRating(movieMeans.get(predRatings.get(i).getMovie().getTitle()));
//            System.out.println(i);
//        }
//
//		// Return predictions
//		return predRatings;
//	}
}
