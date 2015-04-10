package ti2736c;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class User {

    int index, age, profession;
    boolean male;
    
	public User(int _index, boolean _male, int _age, int _profession) {
        this.index      = _index;
        this.male       = _male;
        this.age        = _age;
        this.profession = _profession;
    }
    
    public int getIndex() {
        return index;
    }
    
    public boolean isMale() {
        return male;
    }
    
    public int getAge() {
        return age;
    }
    
    public int getProfession() {
        return profession;
    }

    public double equality(User other, HashMap<Integer,HashMap<Integer,Double>> ratedMovies) {
        double equality = 0.0;

        if (this.isMale() == other.isMale())
            equality += 20;

        if (this.getAge() == other.getAge())
            equality += 50;
        else if (Math.abs(this.getAge() - other.getAge()) <= 5)
            equality += 50 - 10 * Math.abs(this.getAge() - other.getAge());

        if (this.getProfession() == other.getProfession())
            equality += 40;

        HashMap<Integer,Double> thisRatings = ratedMovies.get(getIndex());
        HashMap<Integer,Double> otherRatings = ratedMovies.get(other.getIndex());
        Iterator<Map.Entry<Integer,Double>> thisIterator = thisRatings.entrySet().iterator();
        while (thisIterator.hasNext()){
            Map.Entry<Integer,Double> current = thisIterator.next();
            if (otherRatings.get(current.getKey()) != null) {
                double difference = current.getValue() - otherRatings.get(current.getKey());
                if (difference == 0)
                    equality += 25;
                else if (difference == 1)
                    equality += 5;
            }
        }

        return equality;
    }

    public HashMap<Integer,Double> equalUsers(UserList users, HashMap<Integer,HashMap<Integer,Double>> ratedMovies) {
        HashMap<Integer,Double> result = new HashMap<Integer, Double>();
        for (int i = 0; i < users.size(); i++) {
            double currentEquality = this.equality(users.get(i),ratedMovies);
            if (currentEquality > 200)
                result.put(users.get(i).getIndex(),currentEquality);
        }
        return result;
    }
}
