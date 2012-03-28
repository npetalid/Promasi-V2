/*
 * @(#)JIDEXMLTTRunner.java 7/8/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.grid.TreeTable;
import org.xml.sax.InputSource;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class XmlTableDemo {

    /**
     * @param args
     * @throws java.io.FileNotFoundException
     * @throws java.io.UnsupportedEncodingException
     *
     */

    public XmlTableDemo() throws UnsupportedEncodingException {

        InputStream inputStream = this.getClass().getResourceAsStream("/file1.xml");

        Reader reader = new InputStreamReader(inputStream, "UTF-8");

        InputSource is = new InputSource(reader);

        XmlTreeTableModel jxttm = null;

        try {

            XmlNode xmlNode = new XmlNode(TreeElementProvider.createDocument(is).getDocumentElement());
            java.util.List<XmlNode> list = new ArrayList();
            list.add(xmlNode);
            jxttm = new XmlTreeTableModel(list);
            TreeTable jxtt = new TreeTable(jxttm);

            JFrame frame = new JFrame("XML TREE TABLE");

            JScrollPane jsp = new JScrollPane(jxtt);

            frame.getContentPane().add(jsp);

            frame.setPreferredSize(new Dimension(400, 400));

            frame.pack();

            frame.setVisible(true);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {


        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                try {
                    new XmlTableDemo();
                }
                catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });


    }

}
