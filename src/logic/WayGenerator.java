package logic;

import javafx.util.Pair;
import sun.awt.util.IdentityLinkedList;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class WayGenerator {
    private Queue<Pair<Double, Double>> way = new IdentityLinkedList<>();
    private static final Random RND = new Random();

    public Pair<Double, Double> getNextPoint(){
        return way.remove();
    }

    public void addHighway(){
        double friction = RND.nextDouble() * 0.25 + 0.75;
        for(int i = 0; i < 15; ++i)
            way.add(new Pair<>(RND.nextDouble() * 40.0 - 20.0, friction));
    }

    public void addRural(){
        double friction = RND.nextDouble() * 0.25 + 0.4;
        for(int i = 0; i < 10; ++i)
            way.add(new Pair<>(RND.nextDouble() * 50.0 - 25.0, friction));
    }

    public void addDangerousWay(){
        double friction = RND.nextDouble() * 0.2 + 0.1;
        for(int i = 0; i < 10; ++i)
            way.add(new Pair<>(RND.nextDouble() * 50.0 - 25.0, friction));
    }

    public void addCorner(String surface, boolean narrow){
        double friction = 0.0;
        switch(surface){
            case "ice": friction = RND.nextDouble() * 0.25; break;
            case "sand": friction = RND.nextDouble() * 0.15 + 0.4; break;
            case "ground": friction = RND.nextDouble() * 0.15 + 0.55; break;
            case "asphalt": friction = RND.nextDouble() * 0.25 + 0.75; break;
            default: break;
        }
        double angle = narrow ? RND.nextDouble() * 40 + 60 : RND.nextDouble() * 40 + 20;
        for(int i = 0; i < 5; ++i)
            way.add(new Pair<>(angle, friction));
    }

    public boolean isEmpty(){
        return way.isEmpty();
    }
}
