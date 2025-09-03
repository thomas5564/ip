package avo.graphs;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;

/**
 * A generic PieChart that builds slices from a HashMap<String, Number>
 */
public class TaskPieChart extends PieChart {
    /**
     * Constructor for this class
     * @param dataMap hashmap of values
     */
    public TaskPieChart(Map<String, ? extends Number> dataMap) {
        super();
        updateChart(dataMap);
    }
    /**
     * Updates the chart slices from a map.
     * Keys become labels, values become slice sizes.
     */
    public void updateChart(Map<String, ? extends Number> dataMap) {
        var pieData = FXCollections.<PieChart.Data>observableArrayList();

        for (Map.Entry<String, ? extends Number> entry : dataMap.entrySet()) {
            String label = entry.getKey();
            Number value = entry.getValue();
            pieData.add(new PieChart.Data(label, value.doubleValue()));
        }

        setData(pieData);
    }
}
