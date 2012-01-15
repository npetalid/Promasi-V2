/*
 * @(#)WeatherData.java 8/22/2009
 *
 * 2002 - 2011 JIDE Software Inc. All rights reserved.
 * Copyright 2005 - 2011 Catalysoft Ltd. All rights reserved.
 */

import com.jidesoft.chart.model.DefaultChartModel;
import com.jidesoft.csv.CsvReader;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A data store for the Cambridge Weather data, which is used in more than one example.
 */
public class WeatherData {
    private static final int yearColumn = 0;
    private static final int monthColumn = 1;
    private static final int tempMaxColumn = 2;
    private static final int tempMinColumn = 3;
    private static final int rainfallColumn = 5;
    private static final int sunshineColumn = 6;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
    //private static final DateFormat yearFormat = new SimpleDateFormat("yyyy");
    private static WeatherData instance;
    // Record maximum temperatures on a per month basis 
    private DefaultChartModel[] maxTemps = new DefaultChartModel[12];
    // Record minimum temperatures on a per month basis
    private DefaultChartModel[] minTemps = new DefaultChartModel[12];
    // Record rainfall on a per month basis
    private DefaultChartModel[] rainfallModels = new DefaultChartModel[12];
    // Record sunshine on a per month basis over the years
    private DefaultChartModel[] sunshineModels = new DefaultChartModel[12];
    // Use this for recording sunshine on a per year basis. The key is a year as an Integer
    private Map<Integer, Double[]> byYearSunshine = new HashMap<Integer, Double[]>();

    private WeatherData() {
        try {
            load();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WeatherData getInstance() {
        if (instance == null) {
            instance = new WeatherData();
        }
        return instance;
    }

    public DefaultChartModel getTempMaxModel(Month month) {
        return maxTemps[month.ordinal()];
    }

    public DefaultChartModel getTempMinModel(Month month) {
        return minTemps[month.ordinal()];
    }

    public DefaultChartModel getRainfallModel(Month month) {
        return rainfallModels[month.ordinal()];
    }

    public DefaultChartModel getSunshineModel(Month month) {
        return sunshineModels[month.ordinal()];
    }
    
    public List<Integer> getSunshineModelKeys() {
        List<Integer> keys = new ArrayList<Integer>(byYearSunshine.keySet());
        Collections.sort(keys);
        return keys;
    }
    
    public Double[] getSunshineValues(Integer year) {
        return byYearSunshine.get(year);
    }

    protected void load() throws IOException, ParseException {
        CsvReader reader = new CsvReader('\t');
        // Load the data file from the classpath
        InputStream dataStream = getClass().getResourceAsStream("/cambridgedata.txt");
        assert dataStream != null : "Weather data not found on the classpath!";
        List<List<String>> csvValues = reader.parse(dataStream);

        final int headerLines = 2; // The number of header lines
        for (int line = 0; line < csvValues.size(); line++) {
            if (line >= headerLines) {
                List<String> lineValues = csvValues.get(line);
                String yearString = lineValues.get(yearColumn);
                Month month = Month.getMonth(new Integer(lineValues.get(monthColumn)));
                String maxTempString = lineValues.get(tempMaxColumn);
                Double maxTemp = Double.parseDouble(maxTempString);
                String minTempString = lineValues.get(tempMinColumn);
                Double minTemp = Double.parseDouble(minTempString);
                String rainfallString = lineValues.get(rainfallColumn);
                Double rainfall = null;
                try {
                    rainfall = Double.parseDouble(rainfallString);
                }
                catch (NumberFormatException nfe) {
                }
                String sunshineString = lineValues.get(sunshineColumn);
                Double sunshine = null;
                try {
                    sunshine = Double.parseDouble(sunshineString);
                }
                catch (NumberFormatException nfe) {
                }
                String dateString = String.format("15-%s-%s", month, yearString);
                long time = dateFormat.parse(dateString).getTime();
                long year = dateFormat.parse("15-Jun-" + yearString).getTime();
                // Used to check for month not being null in the following, but
                // it can't be null as it is an int
                if (yearString != null && maxTemp != null) {
                    addToModelByMonth("Max Temp", maxTemps, month, time, maxTemp);
                    addToModelByMonth("Min Temp", minTemps, month, time, minTemp);
                    addToModelByMonth(null, rainfallModels, month, time, rainfall);
                    addToModelByMonth(null, sunshineModels, month, year, sunshine);
                    addToSunshineModelByYear(yearString, month, sunshine);
                }
            }
        }
    }
    
    private void addToSunshineModelByYear(String yearString, Month month, Double value) {
        Integer year = new Integer(yearString);
        Double[] values = byYearSunshine.get(year);
        if (values == null) {
            values = new Double[12];
            byYearSunshine.put(year, values);
        }
        values[month.ordinal()] = value;
    }

     // Adds a value for a particular year to a chart model according to the month in which it was recorded
    private void addToModelByMonth(String modelName, DefaultChartModel[] array, Month month, long time, Double value) {
        if (value == null) {
            return;
        }
        DefaultChartModel m = array[month.ordinal()];
        if (m == null) {
            String name = modelName == null ? month.toString() : modelName+" ("+month+")";
            m = new DefaultChartModel(name);
            array[month.ordinal()] = m;
        }
        m.addPoint(time, value);
    }
}
