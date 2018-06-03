package logic;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class FuzzyController {
    private FIS fis;

    public FuzzyController(String fileName) {
        fis = FIS.load(fileName, false);
    }

    public void drawIOCharts(){
        JFuzzyChart.get().chart(fis);
    }

    public Variable step(double speed, double friction, double angle, int exp){
        fis.setVariable("car_speed", speed);
        fis.setVariable("surface", friction);
        fis.setVariable("line", angle);
        fis.setVariable("experience", exp);
        fis.evaluate();
        return fis.getVariable("acceleration");
    }
}
