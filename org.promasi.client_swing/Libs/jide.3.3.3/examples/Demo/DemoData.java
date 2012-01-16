/*
 * @(#)DemoData.java 9/8/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.CurrencyConverter;
import com.jidesoft.converter.DefaultObjectConverter;
import com.jidesoft.grid.ContextSensitiveTableModel;
import com.jidesoft.grid.DefaultContextSensitiveTableModel;
import com.jidesoft.grid.EditorContext;
import com.jidesoft.grid.GroupableTableModel;
import com.jidesoft.grouper.DefaultObjectGrouper;
import com.jidesoft.grouper.GrouperContext;
import com.jidesoft.grouper.date.DateYearGrouper;
import com.jidesoft.icons.IconsFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

public class DemoData {
    public static final String[] TITLES = new String[]{
            "Mail",
            "Calendar",
            "Contacts",
            "Tasks",
            "Notes",
            "Folder List",
            "Shortcuts",
            "Journal"
    };
    public static final int[] MNEMONICS = new int[]{
            KeyEvent.VK_M,
            KeyEvent.VK_C,
            KeyEvent.VK_O,
            KeyEvent.VK_T,
            KeyEvent.VK_N,
            KeyEvent.VK_F,
            KeyEvent.VK_S,
            KeyEvent.VK_J
    };
    public static final ImageIcon[] ICONS = new ImageIcon[]{
            IconsFactory.getImageIcon(DemoData.class, "icons/email.gif"),
            IconsFactory.getImageIcon(DemoData.class, "icons/calendar.gif"),
            IconsFactory.getImageIcon(DemoData.class, "icons/contacts.gif"),
            IconsFactory.getImageIcon(DemoData.class, "icons/tasks.gif"),
            IconsFactory.getImageIcon(DemoData.class, "icons/notes.gif"),
            IconsFactory.getImageIcon(DemoData.class, "icons/folder.gif"),
            IconsFactory.getImageIcon(DemoData.class, "icons/shortcut.gif"),
            IconsFactory.getImageIcon(DemoData.class, "icons/journal.gif")
    };
    public static final ImageIcon[] ICONS_LARGE = new ImageIcon[]{
            IconsFactory.getImageIcon(DemoData.class, "icons/email_24x24.png"),
            IconsFactory.getImageIcon(DemoData.class, "icons/calendar_24x24.png"),
            IconsFactory.getImageIcon(DemoData.class, "icons/contacts_24x24.png"),
            IconsFactory.getImageIcon(DemoData.class, "icons/tasks_24x24.png"),
            IconsFactory.getImageIcon(DemoData.class, "icons/notes_24x24.png"),
            IconsFactory.getImageIcon(DemoData.class, "icons/folder_24x24.png"),
            IconsFactory.getImageIcon(DemoData.class, "icons/shortcut_24x24.png"),
            IconsFactory.getImageIcon(DemoData.class, "icons/journal_24x24.png")
    };

    public static DefaultListModel createCountryListModel() {
        final String[] names = getCountryNames();
        final DefaultListModel model = new DefaultListModel();
        for (String name : names) {
            model.addElement(name);
        }
        return model;
    }

    public static DefaultComboBoxModel createCountryComboBoxModel() {
        final String[] names = getCountryNames();
        final DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (String name : names) {
            model.addElement(name);
        }
        return model;
    }

    public static DefaultListModel createFontListModel() {
        final String[] names = getFontNames();
        final DefaultListModel model = new DefaultListModel();
        for (String name : names) {
            model.addElement(name);
        }
        return model;
    }

    public static DefaultComboBoxModel createFontComboBoxModel() {
        final String[] names = getFontNames();
        final DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (String name : names) {
            model.addElement(name);
        }
        return model;
    }

    public static String[] getFontNames() {
        return new String[]{
                "Agency FB",
                "Aharoni",
                "Algerian",
                "Andalus",
                "Angsana New",
                "AngsanaUPC",
                "Aparajita",
                "Arabic Typesetting",
                "Arial",
                "Arial Black",
                "Arial Narrow",
                "Arial Rounded MT Bold",
                "Arial Unicode MS",
                "Baskerville Old Face",
                "Batang",
                "BatangChe",
                "Bauhaus 93",
                "Bell MT",
                "Berlin Sans FB",
                "Berlin Sans FB Demi",
                "Bernard MT Condensed",
                "Blackadder ITC",
                "Bodoni MT",
                "Bodoni MT Black",
                "Bodoni MT Condensed",
                "Bodoni MT Poster Compressed",
                "Book Antiqua",
                "Bookman Old Style",
                "Bookshelf Symbol 7",
                "Bradley Hand ITC",
                "Britannic Bold",
                "Broadway",
                "Browallia New",
                "BrowalliaUPC",
                "Brush Script MT",
                "Calibri",
                "Californian FB",
                "Calisto MT",
                "Cambria",
                "Cambria Math",
                "Candara",
                "Castellar",
                "Centaur",
                "Century",
                "Century Gothic",
                "Century Schoolbook",
                "Chiller",
                "Colonna MT",
                "Comic Sans MS",
                "Consolas",
                "Constantia",
                "Cooper Black",
                "Copperplate Gothic Bold",
                "Copperplate Gothic Light",
                "Corbel",
                "Cordia New",
                "CordiaUPC",
                "Courier New",
                "Curlz MT",
                "DaunPenh",
                "David",
                "DFKai-SB",
                "Dialog",
                "DialogInput",
                "DilleniaUPC",
                "DokChampa",
                "Dotum",
                "DotumChe",
                "Ebrima",
                "Edwardian Script ITC",
                "Elephant",
                "Engravers MT",
                "Estrangelo Edessa",
                "EucrosiaUPC",
                "Euphemia",
                "Euro Sign",
                "FangSong",
                "Felix Titling",
                "Footlight MT Light",
                "Forte",
                "Franklin Gothic Book",
                "Franklin Gothic Demi",
                "Franklin Gothic Demi Cond",
                "Franklin Gothic Heavy",
                "Franklin Gothic Medium",
                "Franklin Gothic Medium Cond",
                "FrankRuehl",
                "FreesiaUPC",
                "Freestyle Script",
                "French Script MT",
                "Gabriola",
                "Garamond",
                "Gautami",
                "Georgia",
                "Gigi",
                "Gill Sans MT",
                "Gill Sans MT Condensed",
                "Gill Sans MT Ext Condensed Bold",
                "Gill Sans Ultra Bold",
                "Gill Sans Ultra Bold Condensed",
                "Gisha",
                "Gloucester MT Extra Condensed",
                "Goudy Old Style",
                "Goudy Stout",
                "Gulim",
                "GulimChe",
                "Gungsuh",
                "GungsuhChe",
                "Haettenschweiler",
                "Harlow Solid Italic",
                "Harrington",
                "High Tower Text",
                "Impact",
                "Imprint MT Shadow",
                "Informal Roman",
                "IrisUPC",
                "Iskoola Pota",
                "JasmineUPC",
                "Jokerman",
                "Juice ITC",
                "KaiTi",
                "Kalinga",
                "Kartika",
                "Khmer UI",
                "KodchiangUPC",
                "Kokila",
                "Kristen ITC",
                "Kunstler Script",
                "Lao UI",
                "Latha",
                "Leelawadee",
                "Levenim MT",
                "LilyUPC",
                "Lucida Bright",
                "Lucida Calligraphy",
                "Lucida Console",
                "Lucida Fax",
                "Lucida Handwriting",
                "Lucida Sans",
                "Lucida Sans Typewriter",
                "Lucida Sans Unicode",
                "Magneto",
                "Maiandra GD",
                "Malgun Gothic",
                "Mangal",
                "Marlett",
                "Matura MT Script Capitals",
                "Meiryo",
                "Meiryo UI",
                "Microsoft Himalaya",
                "Microsoft JhengHei",
                "Microsoft New Tai Lue",
                "Microsoft PhagsPa",
                "Microsoft Sans Serif",
                "Microsoft Tai Le",
                "Microsoft Uighur",
                "Microsoft YaHei",
                "Microsoft Yi Baiti",
                "MingLiU",
                "MingLiU-ExtB",
                "MingLiU_HKSCS",
                "MingLiU_HKSCS-ExtB",
                "Miriam",
                "Miriam Fixed",
                "Mistral",
                "Modern No. 20",
                "Mongolian Baiti",
                "Monospaced",
                "Monotype Corsiva",
                "MoolBoran",
                "MS Gothic",
                "MS Mincho",
                "MS Outlook",
                "MS PGothic",
                "MS PMincho",
                "MS Reference Sans Serif",
                "MS Reference Specialty",
                "MS UI Gothic",
                "MT Extra",
                "MV Boli",
                "Narkisim",
                "Niagara Engraved",
                "Niagara Solid",
                "Niamey",
                "NSimSun",
                "Nyala",
                "OCR A Extended",
                "OCR B MT",
                "OCR-A II",
                "Old English Text MT",
                "Onyx",
                "Palace Script MT",
                "Palatino Linotype",
                "Papyrus",
                "Parchment",
                "Perpetua",
                "Perpetua Titling MT",
                "Plantagenet Cherokee",
                "Playbill",
                "PMingLiU",
                "PMingLiU-ExtB",
                "Poor Richard",
                "Pristina",
                "QuickType",
                "QuickType Condensed",
                "QuickType II",
                "QuickType II Condensed",
                "QuickType II Mono",
                "QuickType II Pi",
                "QuickType Mono",
                "QuickType Pi",
                "Raavi",
                "Rage Italic",
                "Ravie",
                "Rockwell",
                "Rockwell Condensed",
                "Rockwell Extra Bold",
                "Rod",
                "Sakkal Majalla",
                "SansSerif",
                "Script MT Bold",
                "Segoe Print",
                "Segoe Script",
                "Segoe UI",
                "Segoe UI Light",
                "Segoe UI Semibold",
                "Segoe UI Symbol",
                "Serif",
                "Shonar Bangla",
                "Showcard Gothic",
                "Shruti",
                "SimHei",
                "Simplified Arabic",
                "Simplified Arabic Fixed",
                "SimSun",
                "SimSun-ExtB",
                "Snap ITC",
                "Stencil",
                "SWGamekeys MT",
                "Sylfaen",
                "Symbol",
                "Tahoma",
                "Tempus Sans ITC",
                "Times New Roman",
                "Traditional Arabic",
                "Trebuchet MS",
                "Tunga",
                "Tw Cen MT",
                "Tw Cen MT Condensed",
                "Tw Cen MT Condensed Extra Bold",
                "Untitled",
                "Utsaah",
                "Vani",
                "Verdana",
                "Vijaya",
                "Viner Hand ITC",
                "Vivaldi",
                "Vladimir Script",
                "Vodafone Rg",
                "Vrinda",
                "Webdings",
                "Wide Latin",
                "Wingdings",
                "Wingdings 2",
                "Wingdings 3",
                "ZWAdobeF",
        };
    }

    public static String[] getCountryNames() {
        return new String[]{
                "Andorra",
                "United Arab Emirates",
                "Afghanistan",
                "Antigua And Barbuda",
                "Anguilla",
                "Albania",
                "Armenia",
                "Netherlands Antilles",
                "Angola",
                "Antarctica",
                "Argentina",
                "American Samoa",
                "Austria",
                "Australia",
                "Aruba",
                "Azerbaijan",
                "Bosnia And Herzegovina",
                "Barbados",
                "Bangladesh",
                "Belgium",
                "Burkina Faso",
                "Bulgaria",
                "Bahrain",
                "Burundi",
                "Benin",
                "Bermuda",
                "Brunei Darussalam",
                "Bolivia",
                "Brazil",
                "Bahamas",
                "Bhutan",
                "Bouvet Island",
                "Botswana",
                "Belarus",
                "Belize",
                "Canada",
                "Cocos (Keeling) Islands",
                "Congo, The Democratic Republic Of The",
                "Central African Republic",
                "Congo",
                "Switzerland",
                "Cook Islands",
                "Chile",
                "Cameroon",
                "China",
                "Colombia",
                "Costa Rica",
                "Cuba",
                "Cape Verde",
                "Christmas Island",
                "Cyprus",
                "Czech Republic",
                "Germany",
                "Djibouti",
                "Denmark",
                "Dominica",
                "Dominican Republic",
                "Algeria",
                "Ecuador",
                "Estonia",
                "Egypt",
                "Western Sarara",
                "Eritrea",
                "Spain",
                "Ethiopia",
                "Finland",
                "Fiji",
                "Falkland Islands (Malvinas)",
                "Micronesia, Federated States Of",
                "Faroe Islands",
                "France",
                "Gabon",
                "United Kingdom",
                "Grenada",
                "Georgia",
                "French Guiana",
                "Ghana",
                "Gibraltar",
                "Greenland",
                "Gambia",
                "Guinea",
                "Guadeloupe",
                "Equatorial Guinea",
                "Greece",
                "South Georgia And The South Sandwich Islands",
                "Guatemala",
                "Guam",
                "Guinea-bissau",
                "Guyana",
                "Hong Kong",
                "Heard Island And Mcdonald Islands",
                "Honduras",
                "Croatia",
                "Haiti",
                "Hungary",
                "Indonesia",
                "Ireland",
                "Israel",
                "India",
                "British Indian Ocean Territory",
                "Iraq",
                "Iran, Islamic Republic Of",
                "Iceland",
                "Italy",
                "Jamaica",
                "Jordan",
                "Japan",
                "Kenya",
                "Kyrgyzstan",
                "Cambodia",
                "Kiribati",
                "Comoros",
                "Saint Kitts And Nevis",
                "Korea, Democratic People'S Republic Of",
                "Korea, Republic Of",
                "Kuwait",
                "Cayman Islands",
                "Kazakhstan",
                "Lao People'S Democratic Republic",
                "Lebanon",
                "Saint Lucia",
                "Liechtenstein",
                "Sri Lanka",
                "Liberia",
                "Lesotho",
                "Lithuania",
                "Luxembourg",
                "Latvia",
                "Libyan Arab Jamabiriya",
                "Morocco",
                "Monaco",
                "Moldova, Republic Of",
                "Madagascar",
                "Marshall Islands",
                "Macedonia, The Former Yugoslav Repu8lic Of",
                "Mali",
                "Myanmar",
                "Mongolia",
                "Macau",
                "Northern Mariana Islands",
                "Martinique",
                "Mauritania",
                "Montserrat",
                "Malta",
                "Mauritius",
                "Maldives",
                "Malawi",
                "Mexico",
                "Malaysia",
                "Mozambique",
                "Namibia",
                "New Caledonia",
                "Niger",
                "Norfolk Island",
                "Nigeria",
                "Nicaragua",
                "Netherlands",
                "Norway",
                "Nepal",
                "Niue",
                "New Zealand",
                "Oman",
                "Panama",
                "Peru",
                "French Polynesia",
                "Papua New Guinea",
                "Philippines",
                "Pakistan",
                "Poland",
                "Saint Pierre And Miquelon",
                "Pitcairn",
                "Puerto Rico",
                "Portugal",
                "Palau",
                "Paraguay",
                "Qatar",
                "Romania",
                "Russian Federation",
                "Rwanda",
                "Saudi Arabia",
                "Solomon Islands",
                "Seychelles",
                "Sudan",
                "Sweden",
                "Singapore",
                "Saint Helena",
                "Slovenia",
                "Svalbard And Jan Mayen",
                "Slovakia",
                "Sierra Leone",
                "San Marino",
                "Senegal",
                "Somalia",
                "Suriname",
                "Sao Tome And Principe",
                "El Salvador",
                "Syrian Arab Republic",
                "Swaziland",
                "Turks And Caicos Islands",
                "Chad",
                "French Southern Territories",
                "Togo",
                "Thailand",
                "Tajikistan",
                "Tokelau",
                "Turkmenistan",
                "Tunisia",
                "Tonga",
                "East Timor",
                "Turkey",
                "Trinidad And Tobago",
                "Tuvalu",
                "Taiwan, Province Of China",
                "Tanzania, United Republic Of",
                "Ukraine",
                "Uganda",
                "United States Minor Outlying Islands",
                "United States",
                "Uruguay",
                "Uzbekistan",
                "Venezuela",
                "Virgin Islands, British",
                "Virgin Islands, U.S.",
                "Viet Nam",
                "Vanuatu",
                "Wallis And Futuna",
                "Samoa",
                "Yemen",
                "Mayotte",
                "Yugoslavia",
                "South Africa",
                "Zambia",
                "Zimbabwe"
        };
    }

    static String[] QUOTE_COLUMNS = new String[]{"Symbol", "Name", "Last", "Change", "Volume"};

    static Object[][] QUOTES = new Object[][]{
            new Object[]{"AA", "ALCOA INC", "32.88", "+0.53 (1.64%)", "4156200"},
            new Object[]{"AIG", "AMER INTL GROUP", "69.53", "-0.58 (0.83%)", "4369200"},
            new Object[]{"AXP", "AMER EXPRESS CO", "48.90", "-0.35 (0.71%)", "4103600"},
            new Object[]{"BA", "BOEING CO", "49.14", "-0.18 (0.36%)", "3573700"},
            new Object[]{"C", "CITIGROUP", "44.21", "-0.89 (1.97%)", "28594900"},
            new Object[]{"CAT", "CATERPILLAR INC", "79.40", "+0.62 (0.79%)", "1458200"},
            new Object[]{"DD", "DU PONT CO", "42.62", "-0.14 (0.33%)", "1832700"},
            new Object[]{"DIS", "WALT DISNEY CO", "23.87", "-0.32 (1.32%)", "4443600"},
            new Object[]{"GE", "GENERAL ELEC CO", "33.37", "+0.24 (0.72%)", "31429500"},
            new Object[]{"GM", "GENERAL MOTORS", "43.94", "-0.20 (0.45%)", "3722100"},
            new Object[]{"HD", "HOME DEPOT INC", "34.33", "-0.18 (0.52%)", "5367900"},
            new Object[]{"HON", "HONEYWELL INTL", "35.70", "+0.23 (0.65%)", "4092100"},
            new Object[]{"HPQ", "HEWLETT-PACKARD", "19.65", "-0.25 (1.26%)", "11003000"},
            new Object[]{"IBM", "INTL BUS MACHINE", "84.02", "-0.11 (0.13%)", "6880500"},
            new Object[]{"INTC", "INTEL CORP", "23.15", "-0.23 (0.98%)", "95177008"},
            new Object[]{"JNJ", "JOHNSON&JOHNSON", "55.35", "-0.57 (1.02%)", "5428000"},
            new Object[]{"JPM", "JP MORGAN CHASE", "36.00", "-0.45 (1.23%)", "12135300"},
            new Object[]{"KO", "COCA COLA CO", "50.84", "-0.32 (0.63%)", "4143600"},
            new Object[]{"MCD", "MCDONALDS CORP", "27.91", "+0.12 (0.43%)", "6110800"},
            new Object[]{"MMM", "3M COMPANY", "88.62", "+0.43 (0.49%)", "2073800"},
            new Object[]{"MO", "ALTRIA GROUP", "48.20", "-0.80 (1.63%)", "6005500"},
            new Object[]{"MRK", "MERCK & CO", "44.71", "-0.97 (2.12%)", "5472100"},
            new Object[]{"MSFT", "MICROSOFT CP", "27.87", "-0.26 (0.92%)", "46717716"},
            new Object[]{"PFE", "PFIZER INC", "32.58", "-1.43 (4.20%)", "28783200"},
            new Object[]{"PG", "PROCTER & GAMBLE", "55.01", "-0.07 (0.13%)", "5538400"},
            new Object[]{"SBC", "SBC COMMS", "23.00", "-0.54 (2.29%)", "6423400"},
            new Object[]{"UTX", "UNITED TECH CP", "91.00", "+1.16 (1.29%)", "1868600"},
            new Object[]{"VZ", "VERIZON COMMS", "34.81", "-0.35 (1.00%)", "4182600"},
            new Object[]{"WMT", "WAL-MART STORES", "52.33", "-0.25 (0.48%)", "6776700"},
            new Object[]{"XOM", "EXXON MOBIL", "45.32", "-0.14 (0.31%)", "7838100"}
    };

    static class QuoteTableModel extends DefaultTableModel implements ContextSensitiveTableModel {
        private static final long serialVersionUID = 7487309284053329041L;

        public QuoteTableModel() {
            super(QUOTES, QUOTE_COLUMNS);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public ConverterContext getConverterContextAt(int rowIndex, int columnIndex) {
            return null;
        }

        public EditorContext getEditorContextAt(int rowIndex, int columnIndex) {
            return null;
        }

        public Class<?> getCellClassAt(int rowIndex, int columnIndex) {
            return getColumnClass(columnIndex);
        }
    }

    public static TableModel createQuoteTableModel() {
        return new QuoteTableModel();
    }

    public static Vector[] getProductReportsData(int repeats, int maxRows) {
        try {
            InputStream resource = DemoData.class.getClassLoader().getResourceAsStream("ProductReports.txt.gz");
            if (resource == null) {
                return null;
            }
            InputStream in = new GZIPInputStream(resource);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            Vector<String> columnNames = new Vector<String>();

            String columnsLine = reader.readLine(); // skip first line
            String[] columnValues = columnsLine.split("\t");
            columnNames.addAll(Arrays.asList(columnValues));

            int count = 0;
            do {
                String line = reader.readLine();
                if (line == null || line.length() == 0) {
                    break;
                }
                String[] values = line.split("\t");
                Vector<Object> lineData = new Vector<Object>();
                lineData.add(values[0]); // category  name
                lineData.add(values[1]); // product name
                {
                    String value = values[2];
                    if (value.startsWith("$")) {
                        float f = Float.parseFloat(value.substring(1));
                        lineData.add(f); // product amount
                    }
                }
                {
                    String value = values[3];
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
                        Date date = format.parse(value);
                        lineData.add(date); // order date
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < repeats; i++) {
                    data.add(lineData);
                }
                count++;
                if (maxRows > 0 && count > maxRows) {
                    break;
                }
            }
            while (true);
            return new Vector[]{data, columnNames};
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TableModel createProductReportsTableModel(final boolean editable, int maxRows) {
        Vector[] data = DemoData.getProductReportsData(1, maxRows);
        return data == null ? null : new ProductTableModel(data[0], data[1]) {
            private static final long serialVersionUID = -1938173557449999961L;

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                    case 1:
                        return String.class;
                    case 2:
                        return Float.class;
                    case 3:
                        return Date.class;
                }
                if (columnIndex < 0) {
                    throw new IllegalArgumentException("Incorrect column index");
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return editable;
            }

            @Override
            public Class<?> getCellClassAt(int row, int column) {
                return getColumnClass(column);
            }

            @Override
            public ConverterContext getConverterContextAt(int row, int column) {
                if (column == 2) {
                    return CurrencyConverter.CONTEXT;
                }
                return super.getConverterContextAt(row, column);
            }
        };
    }

    public static class ProductTableModel extends DefaultContextSensitiveTableModel implements GroupableTableModel {
        private static final long serialVersionUID = 6219782090215635528L;

        public ProductTableModel() {
        }

        public ProductTableModel(int rowCount, int columnCount) {
            super(rowCount, columnCount);
        }

        public ProductTableModel(Vector columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        public ProductTableModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        public ProductTableModel(Vector data, Vector columnNames) {
            super(data, columnNames);
        }

        public ProductTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        public ConverterContext getConverterContextAt(int row, int column) {
            return null;
        }

        public EditorContext getEditorContextAt(int row, int column) {
            return null;
        }

        public Class<?> getCellClassAt(int row, int column) {
            return getColumnClass(column);
        }

        public GrouperContext getGrouperContext(int columnIndex) {
            switch (columnIndex) {
                case 2:
                    return SalesObjectGrouper.CONTEXT;
                case 3:
                    return DateYearGrouper.CONTEXT;
            }
            return null;
        }
    }

    public static class SalesConverter extends DefaultObjectConverter {
        public static ConverterContext CONTEXT = new ConverterContext("Sales");

        @Override
        public String toString(Object object, ConverterContext context) {
            if (object instanceof Integer) {
                int value = (Integer) object;
                switch (value) {
                    case 0:
                        return "From 0 to 100";
                    case 1:
                        return "From 100 to 1000";
                    case 2:
                        return "From 1000 to 10000";
                    case 3:
                        return "Greater than 10000";
                }
            }
            return null;
        }

        @Override
        public boolean supportFromString(String string, ConverterContext context) {
            return false;
        }
    }

    public static class SalesObjectGrouper extends DefaultObjectGrouper {
        public static final GrouperContext CONTEXT = new GrouperContext("Sales");

        @Override
        public Object getValue(Object value) {
            if (value instanceof Number) {
                double v = ((Number) value).doubleValue();
                if (v < 100) {
                    return 0;
                }
                else if (v < 1000) {
                    return 1;
                }
                else if (v < 10000) {
                    return 2;
                }
                else {
                    return 3;
                }
            }
            return null;
        }

        @Override
        public Class<?> getType() {
            return Integer.class;
        }

        @Override
        public ConverterContext getConverterContext() {
            return SalesConverter.CONTEXT;
        }
    }

    public static TreeModel createSongTreeModel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Songs");
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        HashMap<String, DefaultMutableTreeNode> albums = new HashMap();

        try {
            InputStream resource = DemoData.class.getClassLoader().getResourceAsStream("Library.txt.gz");
            if (resource == null) {
                return null;
            }
            InputStream in = new GZIPInputStream(resource);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            reader.readLine(); // skip first line
            do {
                String line = reader.readLine();
                if (line == null || line.length() == 0) {
                    break;
                }
                String[] values = line.split("\t");
                String songName = "";
                String albumName = "";
                if (values.length >= 1) {
                    songName = values[0];
                }
                if (values.length >= 2) {
                    songName += " - " + values[1];
                }
                if (values.length >= 3) {
                    albumName = values[2]; // artist
                }

                DefaultMutableTreeNode treeNode = albums.get(albumName);
                if (treeNode == null) {
                    treeNode = new DefaultMutableTreeNode(albumName);
                    albums.put(albumName, treeNode);
                    root.add(treeNode);
                }
                treeNode.add(new DefaultMutableTreeNode(songName));
            }
            while (true);
            return treeModel;
        }
        catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        return null;
    }

    public static TableModel createSongTableModel() {
        try {
            InputStream resource = DemoData.class.getClassLoader().getResourceAsStream("Library.txt.gz");
            if (resource == null) {
                return null;
            }
            InputStream in = new GZIPInputStream(resource);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            Vector<String> columnNames = new Vector<String>();

            String columnsLine = reader.readLine(); // skip first line
            String[] columnValues = columnsLine.split("\t");
            columnNames.addAll(Arrays.asList(columnValues));
            do {
                String line = reader.readLine();
                if (line == null || line.length() == 0) {
                    break;
                }
                String[] values = line.split("\t");
                Vector<Object> lineData = new Vector<Object>();
                if (values.length < 1)
                    lineData.add(null); // song name
                else
                    lineData.add(values[0]); // song name
                if (values.length < 2)
                    lineData.add(null); // artist
                else
                    lineData.add(values[1]); // artist
                if (values.length < 3)
                    lineData.add(null); // album
                else
                    lineData.add(values[2]); // album
                if (values.length < 4)
                    lineData.add(null); // genre
                else
                    lineData.add(values[3]); // genre
                if (values.length < 5)
                    lineData.add(null); // time
                else
                    lineData.add(values[4]); // time
                if (values.length < 6)
                    lineData.add(null); // year
                else
                    lineData.add(values[5]); // year
                data.add(lineData);
            }
            while (true);
            return new DefaultTableModel(data, columnNames);
        }
        catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        return null;
    }
}
