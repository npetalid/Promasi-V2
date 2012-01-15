import com.jidesoft.chart.Chart;
import com.jidesoft.chart.Legend;
import com.jidesoft.chart.Orientation;
import com.jidesoft.chart.axis.*;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.model.SummingChartModel;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.csv.CsvReader;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.Category;
import com.jidesoft.range.CategoryRange;
import com.jidesoft.range.Range;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Copyright (c) Catalysoft Ltd, 2005-2011 All Rights Reserved
 * Created: 22/12/2011
 */
public class PopulationPyramidDemo extends AbstractDemo {
    private static final Logger logger = Logger.getLogger(PopulationPyramidDemo.class.getName());
    private List<PopulationSlice> slices = new ArrayList<PopulationSlice>();
    private static final String DATA_FILE = "/population.csv";
    private final int COLUMN_BOTH = 1;
    private final int COLUMN_MALE = 2;
    private final int COLUMN_FEMALE = 3;
    private CategoryRange<String> ages = new CategoryRange<String>();

    @Override
    public Component getDemoPanel() {
        readFile();
        for (PopulationSlice slice : slices) {
            ages.add(new Category<String>(slice.getName()));
        }
        JPanel panel = new JPanel(new BorderLayout());
        Chart chart = new Chart();
        chart.setAnimateOnShow(false);
        chart.setTitle("Demographic Pyramid, USA 1990");
        chart.setChartBorder(new LineBorder(Color.gray));
        chart.setHorizontalGridLinesVisible(false);
        chart.setAutoRanging(true);
        panel.add(chart, BorderLayout.CENTER);
        CategoryAxis<String> yAxis = new CategoryAxis<String>(ages);
        yAxis.setLabel("Age");
        chart.setYAxis(yAxis);
        NumericAxis xAxis = new NumericAxis((String) null);
        // Modify tick labels so that negative values are shown as positive ones
        xAxis.setTickCalculator(new DefaultNumericTickCalculator() {
            @Override
            public Tick[] calculateTicks(Range<Double> r) {
                Tick[] ticks = super.calculateTicks(r);
                NumberFormat numberFormat = getNumberFormat();
                for (Tick t : ticks) {
                    String label = t.getLabel();
                    if (label != null && t.getPosition() < 0) {
                        t.setLabel(numberFormat.format(Math.abs(t.getPosition())));
                    }
                }
                return ticks;
            }
        });
        xAxis.setPlacement(AxisPlacement.TRAILING);
        chart.setXAxis(xAxis);
        // Need to add the negative values before the positive ones as the sizes of the
        // sub-bars are calculated left to right
        chart.addModel(createModel("Male", COLUMN_MALE), createStyle(Color.blue));
        chart.addModel(createModel("Female", COLUMN_FEMALE), createStyle(Color.red));
        panel.setPreferredSize(new Dimension(600, 500));
        JPanel legendPanel = new JPanel();
        legendPanel.add(new Legend(chart, 0));
        panel.add(legendPanel, BorderLayout.SOUTH);
        return panel;
    }

    private ChartStyle createStyle(Color color) {
        ChartStyle style = new ChartStyle(color).withBars();
        style.setBarOrientation(Orientation.horizontal);
        style.setBarWidthProportion(0.8);
        return style;
    }

    private ChartModel createModel(String modelName, int index) {
        DefaultChartModel model = new DefaultChartModel(modelName);
        for (int i=0; i<slices.size(); i++) {
            PopulationSlice slice = slices.get(i);
            Category<String> age = ages.getCategory(i+1);
            switch (index) {
                case COLUMN_BOTH: model.addPoint(slice.getBoth(), age); break;
                case COLUMN_MALE: model.addPoint(-slice.getMale(), age); break;
                case COLUMN_FEMALE: model.addPoint(slice.getFemale(), age); break;
            }
        }
        return model;
    }

    /**
     * Read the data from the text file into a list of PopulationSlice instances
     */
    private void readFile() {
        try {
            CsvReader csvReader = new CsvReader();
            InputStream dataStream = getClass().getResourceAsStream(DATA_FILE);
            assert dataStream != null : "Population data not found on the classpath!";
            List<List<String>> data = csvReader.parse(dataStream);
            for (List<String> row : data) {
                String name = row.get(0);
                try {
                    Double both = Double.parseDouble(row.get(COLUMN_BOTH));
                    Double male = Double.parseDouble(row.get(COLUMN_MALE));
                    Double female = Double.parseDouble(row.get(COLUMN_FEMALE));
                    slices.add(new PopulationSlice(name, both, male, female));
                } catch (NumberFormatException nfe) {
                    // skip this invalid row
                }
            }
        } catch (IOException ioe) {
            logger.warning("Error while loading data file");
            ioe.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "Population Pyramid Demo";
    }

    @Override
    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    @Override
    public int getAttributes() {
        return ATTRIBUTE_NEW;
    }

    private static class PopulationSlice {
        private String name;
        private double both, male, female;

        public PopulationSlice(String name, double both, double male, double female) {
            this.name = name;
            this.both = both;
            this.male = male;
            this.female = female;
        }

        public double getBoth() {
            return both;
        }

        public double getFemale() {
            return female;
        }

        public double getMale() {
            return male;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new PopulationPyramidDemo());
    }
}
