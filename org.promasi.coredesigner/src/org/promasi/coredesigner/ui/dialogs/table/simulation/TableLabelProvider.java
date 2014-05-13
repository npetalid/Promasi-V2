package org.promasi.coredesigner.ui.dialogs.table.simulation;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.promasi.coredesigner.ui.dialogs.model.simulation.TreeTableSubNode;

public class TableLabelProvider implements ITableLabelProvider{
	 
    public Image getColumnImage(Object element, int columnIndex){
       return null;
    }

    public String getColumnText(Object element, int columnIndex){
       switch (columnIndex){
          case 0: return element.toString();
          case 1:
             if (element instanceof TreeTableSubNode)
                return ((TreeTableSubNode)element).getType();
             
          case 2: 
             if (element instanceof TreeTableSubNode)
                return ((TreeTableSubNode)element).getEquation();
          case 3: 
              if (element instanceof TreeTableSubNode)
                 return ((TreeTableSubNode)element).getValue();
       }
       return null;
    }

    public void addListener(ILabelProviderListener listener){
    }

    public void dispose(){
    }

    public boolean isLabelProperty(Object element, String property){
       return false;
    }

    public void removeListener(ILabelProviderListener listener){
    }
}