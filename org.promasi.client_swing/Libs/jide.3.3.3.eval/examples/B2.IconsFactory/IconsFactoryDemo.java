/*
 * @(#)SampleIconsFactory.java
 *
 * Copyright 2002-2003 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.icons.IconsFactory;

import javax.swing.*;

/**
 * Demoed Component: {@link IconsFactory} <br> Required jar files: jide-common.jar <br> Required L&F: any L&F
 */
public class IconsFactoryDemo {

    public static class CollapsiblePane {
        public static final String FOLDER = "icons/Software-Folder.png";

        public static final String PICTURE = "icons/Software-Picture.png";
        public static final String CALENDAR = "icons/Software-Calendar.png";
        public static final String CHART = "icons/Software-Chart.png";
        public static final String DATABASE = "icons/Software-Database.png";
        public static final String DOCUMENT = "icons/Software-Document.png";
        public static final String FILE = "icons/Software-File.png";

        public static final String OTHER = "icons/Hardware-Monitor.png";
        public static final String LOCALDISK = "icons/Hardware-HardDrive.png";
        public static final String MOUSE = "icons/Hardware-Mouse.png";
        public static final String COMPUTER = "icons/Hardware-Computer.png";
        public static final String NETWORK = "icons/Hardware-Network.png";
    }

    public static class OutlookShortcuts {
        public static final String TODAY = "icons/o_today.png";
        public static final String INBOX = "icons/o_inbox.png";
        public static final String CONTACTS = "icons/o_contacts.png";
        public static final String TASKS = "icons/o_tasks.png";
        public static final String CALENDAR = "icons/o_calendar.png";
        public static final String NOTES = "icons/o_notes.png";
        public static final String DELETED_ITEMS = "icons/o_deleted_items.png";

        public static final String DRAFTS = "icons/o_drafts.png";
        public static final String OUTBOX = "icons/o_outbox.png";
        public static final String SEND_ITEMS = "icons/o_send_items.png";
        public static final String JOURNAL = "icons/o_journal.png";

        public static final String COMPUTER = "icons/o_my_computer.png";
        public static final String NETWORK = "icons/o_my_network.png";
        public static final String DOCUMENTS = "icons/o_my_documents.png";
    }

    public static class PropertiesWindow {
        public static final String CATEGORIED = "icons/t_category.png";
        public static final String SORT = "icons/t_sort.png";
        public static final String DESCRIPTION = "icons/t_description.png";
    }

    public static ImageIcon getImageIcon(String name) {
        if (name != null)
            return IconsFactory.getImageIcon(IconsFactoryDemo.class, name);
        else
            return null;
    }

    public static void main(String[] argv) {
        IconsFactory.generateHTML(IconsFactoryDemo.class);
    }


}
