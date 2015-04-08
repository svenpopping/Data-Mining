package ti2736c;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RatingList extends ArrayList<Rating> {

	// Reads in a file with rating data
	public void readFile(String filename, UserList userList, MovieList movieList) {
		BufferedReader br = null;
		String line;
		try {
			br = new BufferedReader(new FileReader(filename));
			while ((line = br.readLine()) != null) {
				String[] ratingData = line.split(";");
				if (ratingData.length == 3) {
					add(new Rating(
							userList.get(Integer.parseInt(ratingData[0]) - 1),
							movieList.get(Integer.parseInt(ratingData[1]) - 1),
							Double.parseDouble(ratingData[2])));
				} else {
					add(new Rating(
							userList.get(Integer.parseInt(ratingData[0]) - 1),
							movieList.get(Integer.parseInt(ratingData[1]) - 1),
							0.0));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Writes a result file
	public void writeResultsFile(String filename) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(filename);
			pw.println("Id,Rating");
			for (int i = 0; i < size(); i++) {
				pw.println((i + 1) + "," + get(i).getRating());
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    /**
     * Returns all ratings for movie m.
     */
    public RatingList movieRatings(Movie m) {
        RatingList result = new RatingList();
        Iterator<Rating> ratingIterator = this.iterator();
        while (ratingIterator.hasNext()) {
            Rating current = ratingIterator.next();
            if (current.getMovie().equals(m))
                result.add(current);
        }
        return result;
    }

    /**
     * Returns all ratings by user u.
     */
    public RatingList userRatings(User u) {
        RatingList result = new RatingList();
        Iterator<Rating> ratingIterator = this.iterator();
        while (ratingIterator.hasNext()) {
            Rating current = ratingIterator.next();
            if (current.getUser().equals(u))
                result.add(current);
        }
        return result;
    }

    /**
     * Returns the mean rating of a RatingList
     */
    public double meanRating() {
        if (this.size() > 0) {
            double result = 0;
            for (int i = 0; i < this.size(); i++) {
                result += this.get(i).getRating();
            }
            return result / this.size();
        } else
            return 3.0;
    }

    /**
     * Returns weighted rating
     */
    public double expectedRating(Rating currentRating) {
        if (this.size() > 0) {
            double result = 0;
            double size = 0;
            User current = currentRating.getUser();
            for (int i = 0; i < this.size(); i++) {
                double currentWeight = 0.0;
                User other = this.get(i).getUser();

                if (current.isMale() == other.isMale())
                    currentWeight += 15;

                if (current.getAge() == other.getAge())
                    currentWeight += 25;
                else
                    currentWeight += 20 / Math.abs(current.getAge() - other.getAge());

                if (current.getProfession() == other.getProfession())
                    currentWeight += 25;

                size += currentWeight;
                result += currentWeight * this.get(i).getRating();
            }
            result = result / size;
            if (!Double.isNaN(result))
                return result;
            else
                return 3.0;
        } else {
            RatingList userRatings = this.userRatings(currentRating.getUser());
            double result = 0.0;
            double size = 0.0;
            for (int i = 0; i < userRatings.size(); i++) {
                double yearDiff = Math.abs(currentRating.getMovie().getYear() - userRatings.get(i).getMovie().getYear());
                if (yearDiff == 0.0) {
                    result += 110 + userRatings.get(i).getRating();
                    size += 110;
                } else {
                    result += 100 / yearDiff * userRatings.get(i).getRating();
                    size += 100 / yearDiff;
                }
            }
            result = result / size;
            if (!Double.isNaN(result))
                return result;
            else
                return 3.0;
        }
    }


}
