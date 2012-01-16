/*
 * @(#)SparklinesDemo.java 22-AUG-2010
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */
import com.jidesoft.chart.Chart;
import com.jidesoft.chart.DifferenceMarker;
import com.jidesoft.chart.LineMarker;
import com.jidesoft.chart.Orientation;
import com.jidesoft.chart.PointShape;
import com.jidesoft.chart.axis.DefaultNumericTickCalculator;
import com.jidesoft.chart.axis.NumericAxis;
import com.jidesoft.chart.axis.Tick;
import com.jidesoft.chart.axis.TimeAxis;
import com.jidesoft.chart.model.ChartModel;
import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.chart.render.SmoothLineRenderer;
import com.jidesoft.chart.style.ChartStyle;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.range.CombinedNumericRange;
import com.jidesoft.range.Range;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class SparklinesDemo extends AbstractDemo {
    private static final int rowHeight = 50;
    private JPanel demoPanel;
    private TableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;

    public Component getDemoPanel() {
        if (demoPanel == null) {
            demoPanel = new JPanel(new BorderLayout());
            JLabel titleLabel = new JLabel("Weather in Cambridge, 1959-2006", JLabel.CENTER);
            titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));
            titleLabel.setBorder(new EmptyBorder(20,20,20,20));
            demoPanel.add(titleLabel, BorderLayout.NORTH);
            Dimension size = new Dimension(600, 500);
            demoPanel.setSize(size);
            demoPanel.setPreferredSize(size);
            tableModel = new SparklinesTableModel();
            table = new JTable(tableModel);
            table.setDefaultRenderer(ChartModel.class, new ChartTableCellRenderer());
            table.setRowHeight(rowHeight);
            scrollPane = new JScrollPane(table);
            demoPanel.add(scrollPane);
            for (int col=0; col<4; col++) {
                TableColumn monthColumn = table.getColumnModel().getColumn(col);
                monthColumn.setPreferredWidth(col == 0 ? 50 : 150);
            }
            DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
            headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        }
        return demoPanel;
    }

    class SparklinesTableModel extends AbstractTableModel {
        private String[] columns = {"Month", "Sunshine (hrs)", "Temp. Range (\u2103)", "Rainfall (mm)"};
        private WeatherData weatherData;

        SparklinesTableModel() {
            weatherData = WeatherData.getInstance();
        }

        public int getRowCount() {
            return Month.values().length;
        }

        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 0 ? String.class : ChartModel.class;
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Month month = Month.getMonth(1+rowIndex);
            if (columnIndex == 0) {
                return month;
            } else {
                List<DefaultChartModel> models = new ArrayList<DefaultChartModel>();
                if (columnIndex == 1) {
                    models.add(weatherData.getSunshineModel(month));
                } else if (columnIndex == 2) {
                    models.add(weatherData.getTempMinModel(month));
                    models.add(weatherData.getTempMaxModel(month));
                } else { // Rainfall
                    models.add(weatherData.getRainfallModel(month));
                }
                return models;
            }
        }
    }

    class ChartTableCellRenderer extends DefaultTableCellRenderer {
        private Chart chart = new Chart();
        private NumericAxis yAxis;
        private ChartStyle sunshineStyle = new ChartStyle().withPoints();
        private ChartStyle temperatureStyle = new ChartStyle().withLines();
        private ChartStyle rainfallStyle = new ChartStyle().withBars();
        private DecimalFormat format = new DecimalFormat("##.#");
        private Color intervalColor = new Color(200, 200, 200);
        private DifferenceMarker differenceMarker = new DifferenceMarker(chart);

        ChartTableCellRenderer() {
            differenceMarker.setSmooth(true);
            // It is important to switch off animation when using the chart as a renderer
            chart.setAnimateOnShow(false);
            chart.setPanelBackground(Color.white);
            chart.setChartBackground(Color.white);
            chart.setVerticalGridLinesVisible(false);
            chart.setHorizontalGridLinesVisible(false);
            chart.setLineRenderer(new SmoothLineRenderer(chart));
            TimeAxis xAxis = new TimeAxis();
            xAxis.setVisible(false);
            chart.setXAxis(xAxis);
            yAxis = new NumericAxis();
            yAxis.setAxisColor(Color.white);
            yAxis.setLabelWidth(30);
            yAxis.setTickFont(chart.getFont().deriveFont(8.5f));
            chart.setTickLength(0);
            chart.setYAxis(yAxis);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            List<DefaultChartModel> models = (List<DefaultChartModel>) value;
            final CombinedNumericRange yRange = new CombinedNumericRange();
            Range<?> xRange = null;
            for (DefaultChartModel model : models) {
                yRange.add(model.getYRange());
                xRange = model.getXRange();
            }
            //DefaultChartModel model = (DefaultChartModel) value;
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            chart.setSize(tableColumn.getWidth(), rowHeight);
            chart.setChartBackground(label.getBackground());
            chart.setPanelBackground(label.getBackground());
            chart.setBorder(label.getBorder());
            chart.getXAxis().setRange(xRange);
            //final Range<Double> yRange = model.getYRange();
            //yAxis.setRange(model.getYRange(0.20, 0.25));
            yAxis.setRange(yRange.getRange(0.2, 0.25));
            yAxis.setTickCalculator(new DefaultNumericTickCalculator() {
                @Override
                public Tick[] calculateTicks(Range<Double> r) {
                    return new Tick[] {
                            new Tick(yRange.minimum(), format.format(yRange.minimum())),
                            new Tick(yRange.maximum(), format.format(yRange.maximum()))
                    };
                }
            });
            chart.removeDrawables();
            chart.removeModels();
            BasicStroke markerStroke = new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 5f, new float[] {1f, 4f}, 0f);
            LineMarker minimumMarker = new LineMarker(chart, Orientation.horizontal, yRange.minimum(), intervalColor);
            minimumMarker.setStroke(markerStroke);
            chart.addDrawable(minimumMarker);
            LineMarker maximumMarker = new LineMarker(chart, Orientation.horizontal, yRange.maximum(), intervalColor);
            maximumMarker.setStroke(markerStroke);
            chart.addDrawable(maximumMarker);
            Month month = Month.getMonth(row+1);

            ChartStyle style = new ChartStyle(sunshineStyle);
            int columnIndex = table.convertColumnIndexToModel(column);
            if (columnIndex == 1) {
                style = sunshineStyle;
                style.setPointColor(month.getColor().darker());
                style.setLineColor(new Color(150, 150, 150));
                style.setPointShape(PointShape.HORIZONTAL_LINE);
                style.setPointSize(5);
            } else if (columnIndex == 2) {
                style = temperatureStyle;
                style.setLineColor(month.getColor().darker());
            } else if (columnIndex == 3) {
                style = rainfallStyle;
                style.setBarColor(month.getColor().darker());
                style.setBarWidth(1);
            }
            if (isSelected) {
                Color foreground = label.getForeground();
                style.setBarColor(foreground);
                style.setLineColor(foreground);
                style.setPointColor(foreground);
            }
            chart.setLabelColor(isSelected ? Color.white : Color.black);
            if (models.size() == 1) {
                chart.addModel(models.get(0), style);
            } else { // must be two models
                differenceMarker.setModel1(models.get(0));
                differenceMarker.setModel2(models.get(1));
                Color c = month.getColor();
                Color fillColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), 100);
                differenceMarker.setFill(isSelected ? label.getForeground() : fillColor);
                chart.addModel(models.get(0), style);
                chart.addModel(models.get(1), style);
                chart.addDrawable(differenceMarker);
            }

            return chart;
        }
    }

    public String getName() {
        return "Sparklines Demo";
    }

    @Override
    public String getDescription() {
        return "This demo shows how you can use a chart to display "+
                "a rich data value as the content of a cell in a table. ";
    }

    public String getProduct() {
        return PRODUCT_NAME_CHARTS;
    }

    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        showAsFrame(new SparklinesDemo());
    }
}
