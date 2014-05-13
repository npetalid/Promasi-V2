package org.promasi.coredesigner.ui.dialogs.table;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import org.promasi.coredesigner.ui.dialogs.model.DependencyTableModel;
/**
 * 
 * @author antoxron
 *
 */
public class DependenciesTableLabelProvider extends LabelProvider 
implements ITableLabelProvider { 
	
	
	   public Image getColumnImage(Object element, int index) { 
	      return null; 
	   } 
	   public String getColumnText(Object element, int index) { 
		   DependencyTableModel model = (DependencyTableModel) element; 
	      switch (index) { 
	         case 0 : 
	        	 String id = String.valueOf( model.getId() );
	            return id;
	         case 1 : 
	            return model.getName();
	         case 2 : 
	        	 return model.getType();
	         default : 
	            return "unknown " + index; 
	      } 
	   } 
	} 
