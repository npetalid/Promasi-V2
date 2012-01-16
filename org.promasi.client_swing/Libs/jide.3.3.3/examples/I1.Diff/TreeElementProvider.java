/*
 * @(#)TreeElementProvider.java 7/8/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;

public class TreeElementProvider {

    public static Document createDocument(InputSource is) throws Exception {
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        saxFactory.setNamespaceAware(true);
        SAXParser parser = saxFactory.newSAXParser();
        XMLReader reader = new XMLTrimFilter(parser.getXMLReader());

        TransformerFactory factory = TransformerFactory.newInstance();

        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        DOMResult result = new DOMResult();
        transformer.transform(new SAXSource(reader, is), result);


        return (Document) result.getNode();
    }


}