package gui;
import eu.hansolo.medusa.Gauge;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Pair;
import logic.FuzzyController;
import logic.WayGenerator;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static final Random RND = new Random();
    private double speed = 30.0;
    private int exp = 20;
    private FuzzyController fuzzy = new FuzzyController("src/logic/car.fcl");

    @FXML private Gauge speed_gauge;
    @FXML private Text friction;
    @FXML private Text angle;
    @FXML private Text experience;

    private long           lastTimerCall;
    private AnimationTimer timer;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        fuzzy.drawIOCharts();
        WayGenerator gen = new WayGenerator();
        gen.addRural();
        gen.addCorner("ground", true);
        gen.addCorner("asphalt", false);
        gen.addHighway();
        gen.addHighway();
        gen.addCorner("asphalt", true);
        gen.addRural();
        gen.addCorner("ice", false);
        gen.addDangerousWay();
        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override public void handle(final long now) {
                if (now > lastTimerCall + 1_000_000_000L && !gen.isEmpty()) {
                    Pair<Double, Double> waypoint = gen.getNextPoint();
                    Variable delta = fuzzy.step(speed, waypoint.getValue(), waypoint.getKey(), exp);
                    //System.out.println(delta.toString());
                    speed += delta.getValue();
                    speed_gauge.setValue(speed);
                    friction.textProperty().setValue("Friction: " + String.format("%.2f",waypoint.getValue()));
                    angle.textProperty().setValue("Angle: " + String.format("%.2f", waypoint.getKey()));
                    experience.textProperty().setValue("Experience: " + exp);
                    if(exp < 100) exp += 1;
                    lastTimerCall = now;
                }
            }
        };
        timer.start();
    }
}