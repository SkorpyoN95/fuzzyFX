package gui;
import eu.hansolo.medusa.Gauge;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import logic.FuzzyController;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static final Random RND = new Random();
    private double speed = 50.0;
    private int exp = 0;
    private FuzzyController fuzzy = new FuzzyController("src/logic/car.fcl");

    @FXML private Gauge speed_gauge;

    private long           lastTimerCall;
    private AnimationTimer timer;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        fuzzy.drawIOCharts();
        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override public void handle(final long now) {
                if (now > lastTimerCall + 1_000_000_000L) {
                    Variable delta = fuzzy.step(speed, RND.nextDouble(), RND.nextDouble()*180.0 - 90.0, exp);
                    //System.out.println(delta.toString());
                    speed += delta.getValue();
                    speed_gauge.setValue(speed);
                    if(exp < 100) exp += 1;
                    lastTimerCall = now;
                }
            }
        };
        timer.start();
    }
}