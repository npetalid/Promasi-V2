/*
 * @(#)XmlNode.java 7/8/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */


import com.jidesoft.converter.ConverterContext;
import com.jidesoft.grid.DefaultExpandableRow;
import com.jidesoft.grid.EditorContext;
import com.jidesoft.grid.Expandable;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class XmlNode extends DefaultExpandableRow implements Comparable {
    private Node _node;
    private java.util.List<XmlNode> _children;

    public XmlNode(Node node) {
        _node = node;
    }

    public Node getNode() {
        return _node;
    }

    @Override
    public List<?> getChildren() {
        if (_children == null || _children.size() == 0) {
            createChildren();
        }
        return _children;
    }

    private void createChildren() {
        _children = new ArrayList<XmlNode>();
        NamedNodeMap attrs = _node.getAttributes();
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                _children.add(new XmlNode(attrs.item(i)));
            }
        }
        NodeList children = _node.getChildNodes();
        int childCount = children != null ? children.getLength() : 0;
        if (childCount != 1 || children.item(0).getNodeType() != Node.TEXT_NODE) {
            if (children != null) {
                for (int i = 0; i < children.getLength(); i++) {
                    _children.add(new XmlNode(children.item(i)));
                }
            }
        }
        for (XmlNode child : _children) {
            child.setParent(this);
        }
    }

    @Override
    public Class<?> getCellClassAt(int columnIndex) {
        return columnIndex == 0 ? XmlNode.class : String.class;
    }

    @Override
    public EditorContext getEditorContextAt(int columnIndex) {
        return columnIndex == 0 ? XmlNodeCellRenderer.CONTEXT : super.getEditorContextAt(columnIndex);
    }

    @Override
    public ConverterContext getConverterContextAt(int columnIndex) {
        return columnIndex == 0 ? XmlNodeConverter.CONTEXT : super.getConverterContextAt(columnIndex);
    }

    public Object getValueAt(int column) {
        if (column == 0)
            return this;
        else {
            if (_node.getNodeType() == Node.ELEMENT_NODE) {
                NodeList children = _node.getChildNodes();
                int childCount = children != null ? children.getLength() : 0;
                if (childCount == 1 && children.item(0).getNodeType() == Node.TEXT_NODE)
                    return children.item(0).getNodeValue();
            }
            return _node.getNodeValue();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    private boolean isVisible() {
        Expandable parent = this;
        while (parent != null) {
            if (!parent.isExpanded()) return false;
            parent = parent.getParent();
        }
        return true;
    }

    public int compareTo(Object o) {
        if (o instanceof XmlNode) {
            Object n1 = getNode().getNodeName();
            Object n2 = ((XmlNode) o).getNode().getNodeName();
            int result = 0;
            if (n1 == null && n2 == null) {
                result = 0;
            }
            else if (n1 == null && n2 != null) {
                result = -1;
            }
            else if (n1 != null && n2 == null) {
                result = 1;
            }
            else {
                result = n1.toString().compareTo(n2.toString());
            }
            if (result == 0) {
                Object o1 = getValueAt(1);
                Object o2 = ((XmlNode) o).getValueAt(1);
                int result2 = 0;
                if (o1 == null && o2 == null) {
                    result2 = 0;
                }
                else if (o1 == null && o2 != null) {
                    result2 = -1;
                }
                else if (o1 != null && o2 == null) {
                    result2 = 1;
                }
                else {
                    result2 = o1.toString().compareTo(o2.toString());
                }
                if (result2 == 0 && !isVisible() && !((XmlNode) o).isVisible()) { // compare children
                    List<?> children1 = getChildren();
                    List<?> children2 = ((XmlNode) o).getChildren();
                    if (children1.size() < children2.size()) {
                        return -1;
                    }
                    else if (children1.size() > children2.size()) {
                        return 1;
                    }
                    else {
                        if (children1.size() != 0 && children2.size() != 0) {
                            for (int i = 0; i < children1.size(); i++) {
                                Object co1 = children1.get(i);
                                Object co2 = children2.get(i);
                                int result3 = ((XmlNode) co1).compareTo(co2);
                                if (result3 != 0) {
                                    return result3;
                                }
                            }
                        }
                        return 0;
                    }
                }
                return result2;
            }
            return result;
        }
        return 0;
    }
}
