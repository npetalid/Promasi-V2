/*
 * @(#)DateChooserDemo.java 2/12/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.combobox.DateChooserPanel;
import com.jidesoft.combobox.DateExComboBox;
import com.jidesoft.combobox.DateFilter;
import com.jidesoft.combobox.DefaultDateModel;
import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideSwingUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Demoed Component: {@link DateExComboBox}, {@link DateChooserPanel} <br> Required jar files: jide-common.jar,
 * jide-grids.jar <br> Required L&F: Jide L&F extension required
 */
public class DateComboBoxDemo extends AbstractDemo {

    private static final long serialVersionUID = 1479449020424155823L;
    private DateExComboBox _dateComboBox;
    private SimpleDateFormat _dateFormat;
    public JLabel _valueLabel;

    public DateComboBoxDemo() {
    }

    public String getName() {
        return "DateExComboBox Demo";
    }

    public String getProduct() {
        return PRODUCT_NAME_GRIDS;
    }

    public Component getDemoPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 0, 2, 2));
        _dateComboBox = createDateComboBox();

        _valueLabel = new JLabel();
        _dateComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _valueLabel.setText("Date selected: " + ObjectConverterManager.toString(e.getItem()));
                }
            }
        });

        panel.add(_dateComboBox);
        panel.add(_valueLabel);
        return JideSwingUtilities.createTopPanel(panel);
    }

    @Override
    public Component getOptionsPanel() {
        return createOptionPanel();
    }

    @Override
    public String getDescription() {
        return "This is a demo of DateChooserPanel and DateExComboBox. Please choose different options in the options pane and click on the drop down button of DateExComboBox to see the difference.\n" +
                "\n" +
                "Demoed classes:\n" +
                "com.jidesoft.combobox.DateExComboBox\n" +
                "com.jidesoft.combobox.DateChooserPanel\n" +
                "com.jidesoft.combobox.DateFilter\n" +
                "com.jidesoft.combobox.DefaultDateModel";
    }

    @Override
    public String getDemoFolder() {
        return "G5.DateComboBoxes";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showAsFrame(new DateComboBoxDemo());
            }
        });

    }

    private DateExComboBox createDateComboBox() {
        DefaultDateModel model = new DefaultDateModel();

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        model.setMaxDate(calendar);

        calendar.set(Calendar.YEAR, 1980);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        model.setMinDate(calendar);

        DateExComboBox DateExComboBox = new DateExComboBox(model);

        Calendar prototypeValue = Calendar.getInstance();
        prototypeValue.set(Calendar.YEAR, 2000);
        prototypeValue.set(Calendar.MONDAY, 8);
        prototypeValue.set(Calendar.DAY_OF_MONTH, 30);
        DateExComboBox.setPrototypeDisplayValue(prototypeValue);
        Calendar currentDate = Calendar.getInstance();
        DateExComboBox.setCalendar(currentDate);

        DateExComboBox.setLocale(Locale.getDefault());
        return DateExComboBox;
    }

    protected Component createOptionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] formatters = new String[]{
                "(Default)",
                "MMM dd, yyyy",
                "MM/dd/yy",
                "yyyy.MM.dd",
                "EEE M/dd/yyyy",
                "EEE, MMM d, ''yy",
                "yyyyy.MMMMM.dd GGG",
                "EEE, d MMM yyyy",
                "yyMMdd"
        };

        JComboBox comboBox = new JComboBox(formatters);
        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED && e.getItem() instanceof String) {
                    if ((e.getItem()).equals("(Default)")) {
                        _dateFormat = null;
                        _dateComboBox.setFormat(null);
                    }
                    else {
                        _dateFormat = new SimpleDateFormat((String) e.getItem());
                        _dateComboBox.setFormat(_dateFormat);
                    }
                }
            }
        });

        final DateExComboBox minDateComboBox = new DateExComboBox();
        minDateComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _dateComboBox.getDateModel().setMinDate(minDateComboBox.getCalendar());
                }
            }
        });
        minDateComboBox.setCalendar(_dateComboBox.getDateModel().getMinDate());

        final DateExComboBox maxDateComboBox = new DateExComboBox();
        maxDateComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _dateComboBox.getDateModel().setMaxDate(maxDateComboBox.getCalendar());
                }
            }
        });
        maxDateComboBox.setCalendar(_dateComboBox.getDateModel().getMaxDate());

        final JComboBox dateValidatorComboBox = new JComboBox(new String[]{
                "<None>",
                "This week",
                "This month",
                "Later this month",
                "Weekday only",
                "Weekend only"
        });
        dateValidatorComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (_dateComboBox.getDateModel() instanceof DefaultDateModel) {
                        DateFilter dateFilter = null;
                        switch (dateValidatorComboBox.getSelectedIndex()) {
                            case 0:
                                dateFilter = null;
                                break;
                            case 1:
                                dateFilter = DefaultDateModel.THIS_WEEK;
                                break;
                            case 2:
                                dateFilter = DefaultDateModel.THIS_MONTH_ONLY;
                                break;
                            case 3:
                                dateFilter = DefaultDateModel.LATER_THIS_MONTH;
                                break;
                            case 4:
                                dateFilter = DefaultDateModel.WEEKDAY_ONLY;
                                break;
                            case 5:
                                dateFilter = DefaultDateModel.WEEKEND_ONLY;
                                break;

                        }
                        ((DefaultDateModel) _dateComboBox.getDateModel()).clearDateFilters();
                        if (dateFilter != null) {
                            ((DefaultDateModel) _dateComboBox.getDateModel()).addDateFilter(dateFilter);
                        }
                    }
                }
            }
        });

        final JComboBox focusLostComboBox = new JComboBox(new String[]{"Commit", "Commit or Revert", "Revert", "Persist", "Commit or Reset", "Reset"});
        focusLostComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    _dateComboBox.setFocusLostBehavior(focusLostComboBox.getSelectedIndex());
                }
            }
        });
        focusLostComboBox.setSelectedIndex(_dateComboBox.getFocusLostBehavior());

        panel.add(new JLabel("The date format"));
        panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);
        panel.add(comboBox);
        panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);
        panel.add(new JLabel("The minimum date"));
        panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);
        panel.add(minDateComboBox);
        panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);
        panel.add(new JLabel("The maximum date"));
        panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);
        panel.add(maxDateComboBox);
        panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);
        panel.add(new JLabel("Some date filter samples"));
        panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);
        panel.add(dateValidatorComboBox);
        panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);
        panel.add(new JLabel("The focus lost behavior"));
        panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);
        panel.add(focusLostComboBox);
        panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);
        final JCheckBox todayCheckBox = (JCheckBox) panel.add(new JCheckBox("Show the \"Today\" Button"));
        todayCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _dateComboBox.setShowTodayButton(todayCheckBox.isSelected());
            }
        });
        todayCheckBox.setSelected(_dateComboBox.isShowTodayButton());
        panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);
        final JCheckBox noneCheckBox = (JCheckBox) panel.add(new JCheckBox("Show the \"None\" button"));
        noneCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _dateComboBox.setShowNoneButton(noneCheckBox.isSelected());
            }
        });
        noneCheckBox.setSelected(_dateComboBox.isShowNoneButton());
        panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

        final JCheckBox weekNumbersCheckBox = (JCheckBox) panel.add(new JCheckBox("Show the week numbers"));
        weekNumbersCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _dateComboBox.setShowWeekNumbers(weekNumbersCheckBox.isSelected());
            }
        });
        weekNumbersCheckBox.setSelected(_dateComboBox.isShowWeekNumbers());
        panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

        final JCheckBox showTimeCheckBox = (JCheckBox) panel.add(new JCheckBox("Display time"));
        panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);
        final JCheckBox okCheckBox = (JCheckBox) panel.add(new JCheckBox("Show the \"OK\" button"));
        showTimeCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _dateComboBox.setTimeDisplayed(showTimeCheckBox.isSelected());
                okCheckBox.setEnabled(!showTimeCheckBox.isSelected());
                if (showTimeCheckBox.isSelected()) {
                    _dateComboBox.setFormat(SimpleDateFormat.getDateTimeInstance());
                    okCheckBox.setSelected(true);
                }
                else {
                    _dateComboBox.setFormat(_dateFormat);
                    okCheckBox.setSelected(false);
                }
            }
        });
        showTimeCheckBox.setSelected(_dateComboBox.isTimeDisplayed());

        okCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _dateComboBox.setShowOKButton(okCheckBox.isSelected());
            }
        });
        okCheckBox.setSelected(_dateComboBox.isShowOKButton());
        okCheckBox.setEnabled(!showTimeCheckBox.isSelected());
        panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

        final JCheckBox editableCheckBox = (JCheckBox) panel.add(new JCheckBox("Editable"));
        editableCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _dateComboBox.setEditable(editableCheckBox.isSelected());
            }
        });
        editableCheckBox.setSelected(_dateComboBox.isEditable());
        panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

        final JCheckBox invalidCheckBox = (JCheckBox) panel.add(new JCheckBox("Allow invalid value"));
        invalidCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                _dateComboBox.setInvalidValueAllowed(invalidCheckBox.isSelected());
            }
        });
        invalidCheckBox.setSelected(_dateComboBox.isInvalidValueAllowed());
        panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

        return panel;
    }
}
