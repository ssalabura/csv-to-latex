import javafx.util.Pair;

import java.util.ArrayList;

public class Coordinates {
    private ArrayList<Pair<Double, Double>> list;

    Coordinates() {
        this.list = new ArrayList<>();
    }

    void add(Double x, Double y) {
        list.add(new Pair<>(x,y));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Pair<Double,Double> pair : list) {
            builder.append("(").append(pair.getKey()).append(",").append(pair.getValue()).append(")");
        }
        return builder.toString();
    }
}
