package com.ggasoftware.jdi_ui_tests.core.reporting;

import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;

import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.reporting.ActionsType.JDI_ACTION;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.CalculationUtils.average;

/**
 * Created by Roman_Iovlev on 7/26/2015.
 */
public class PerformanceStatistic {
    private static MapArray<ActionsType, List<Long>> statistic = new MapArray<>();

    public static void addStatistic(long time) {
        addStatistic(JDI_ACTION, time);
    }
    public static void addStatistic(ActionsType actionType, long time) {
        if (statistic.keys().contains(actionType))
            statistic.get(actionType).add(time);
    }

    public static String printStatistic() {
        return "Average Actions Time: " + statistic.toMapArray(value -> average(value) + "");
    }

}
