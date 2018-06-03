package gui;
import eu.hansolo.medusa.Gauge;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import logic.FuzzyController;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static final Random RND = new Random();
    private double speed = 0.0;
    private int exp = 0;
    private FuzzyController fuzzy = new FuzzyController("src/logic/car.fcl");

    @FXML private Gauge speed_gauge;
    @FXML private Text friction;
    @FXML private Text angle;
    @FXML private Text experience;

    private long           lastTimerCall;
    private AnimationTimer timer;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        fuzzy.drawIOCharts();
        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override public void handle(final long now) {
                if (now > lastTimerCall + 1_000_000_000L) {
                    double _u = RND.nextDouble(), _a = RND.nextDouble()*180.0 - 90.0;
                    Variable delta = fuzzy.step(speed, _u, _a, exp);
                    //System.out.println(delta.toString());
                    speed += delta.getValue();
                    speed_gauge.setValue(speed);
                    friction.textProperty().setValue("Friction: " + String.format("%.2f",_u));
                    angle.textProperty().setValue("Angle: " + String.format("%.2f",_a));
                    experience.textProperty().setValue("Experience: " + exp);
                    if(exp < 100) exp += 1;
                    lastTimerCall = now;
                }
            }
        };
        timer.start();
    }
}