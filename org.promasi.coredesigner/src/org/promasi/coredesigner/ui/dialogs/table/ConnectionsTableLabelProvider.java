package org.promasi.coredesigner.ui.dialogs.table;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

import org.eclipse.swt.graphics.Image;

import org.promasi.coredesigner.ui.dialogs.model.ConnectionTableModel;
/**
 * 
 * @author antoxron
 *
 */
public class ConnectionsTableLabelProvider extends LabelProvider 
implements ITableLabelProvider { 
	
	
	   public Image getColumnImage(Object element, int index) { 
	      return null; 
	   } 
	   public String getColumnText(Object element, int index) { 
		   ConnectionTableModel model = (ConnectionTableModel) element; 
	      switch (index) { 
	         case 0 : 
	        	 String id = String.valueOf( model.getId() );
	            return id;
	         case 1 : 
	            return model.getName();
	         case 2 : 
	        	 return model.getTask();
	         default : 
	            return "unknown " + index; 
	      } 
	   } 
	} 
