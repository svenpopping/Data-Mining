package ti2736c;

import java.util.*;

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
        HashMap<Integer,HashMap<Integer,Double>> ratedMovies = ratingList.ratedMovies();
        HashMap<Integer,HashMap<Integer,Double>> equalUsers = new HashMap<Integer,HashMap<Integer,Double>>();
        UserList predictionUsers = predRatings.getUserList();
        for (int i = 0; i < predictionUsers.size(); i++) {
            equalUsers.put(predictionUsers.get(i).getIndex(),predictionUsers.get(i).equalUsers(userList,ratedMovies));
            System.out.println(i);
        }
        for (int i = 0; i < predRatings.size(); i++) {
            User currentUser = predRatings.get(i).getUser();
            Movie currentMovie = predRatings.get(i).getMovie();

            Iterator<Map.Entry<Integer,Double>> equalsIterator = equalUsers.get(currentUser.getIndex()).entrySet().iterator();
            double totalScore = 0.0;
            double size = 0.0;
            while (equalsIterator.hasNext()){
                Map.Entry<Integer,Double> current = equalsIterator.next();
                if (ratedMovies.get(current.getKey()).get(currentMovie.getIndex()) != null) {
                    totalScore += current.getValue() * ratedMovies.get(current.getKey()).get(currentMovie.getIndex());
                    size += current.getValue();
                }
            }

            System.out.println(i);
            double result = totalScore / size;
            if (Double.isFinite(result) && !Double.isNaN(result))
                predRatings.get(i).setRating(result);
            else
                predRatings.get(i).setRating(3.0);
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
