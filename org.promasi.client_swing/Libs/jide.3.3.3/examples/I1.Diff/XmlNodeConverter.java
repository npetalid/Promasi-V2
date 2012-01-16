/*
 * @(#)XmlNodeConverter.java 7/29/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.DefaultObjectConverter;

public class XmlNodeConverter extends DefaultObjectConverter {
    public static final ConverterContext CONTEXT = new ConverterContext("XmlNode");

    @Override
    public String toString(Object object, ConverterContext context) {
        if (object instanceof XmlNode) {
            return ((XmlNode) object).getNode().getNodeName();
        }
        return super.toString(object, context);
    }
}
