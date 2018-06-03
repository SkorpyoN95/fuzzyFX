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

    public Variable step(){
//        fis.setVariable("poziom_natezenia", poziomNatezenia);
//        fis.setVariable("pora_dnia", poraDnia);
        //fis.evaluate();
        return fis.getVariable("zmiana_natezenia");
    }
}
