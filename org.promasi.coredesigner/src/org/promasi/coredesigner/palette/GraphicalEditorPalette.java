package org.promasi.coredesigner.palette;


import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.SelectionToolEntry;

import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.promasi.coredesigner.command.connection.ConnectionCreationFactory;
import org.promasi.coredesigner.model.SdCalculate;
import org.promasi.coredesigner.model.SdConnection;
import org.promasi.coredesigner.model.SdFlow;
import org.promasi.coredesigner.model.SdInput;
import org.promasi.coredesigner.model.SdLookup;
import org.promasi.coredesigner.model.SdOutput;
import org.promasi.coredesigner.model.SdStock;
import org.promasi.coredesigner.model.SdVariable;
import org.promasi.coredesigner.resources.IImageKeys;
import org.promasi.coredesigner.ui.editor.SdObjectCreationFactory;
/**
 * 
 * @author antoxron
 *
 */
public class GraphicalEditorPalette  { 
  
	
  public static PaletteRoot getPaletteRoot() { 
	  
	  	PaletteRoot root = new PaletteRoot();
	  
		PaletteGroup basicGroup = new PaletteGroup("Basic tools");
		root.add( basicGroup );
	
		SelectionToolEntry selectionToolEntry = new SelectionToolEntry();
		basicGroup.add( selectionToolEntry );
		basicGroup.add( new MarqueeToolEntry() );
		PaletteSeparator connectionsSeparator = new PaletteSeparator();
		root.add(connectionsSeparator);
		PaletteGroup  connectionsGroup = new PaletteGroup ( "Connections" );
		connectionsGroup.add( new ConnectionCreationToolEntry( "connection","create connection",
				new ConnectionCreationFactory(SdConnection.class ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.CONNECTION_ICON ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.CONNECTION_ICON ) ) );
		root.add( connectionsGroup );
		PaletteSeparator objectsSeparator = new PaletteSeparator();
		root.add( objectsSeparator );
		PaletteDrawer  objectGroup = new PaletteDrawer ( "Core Objects" );
		root.add(objectGroup);
		objectGroup.add(new CombinedTemplateCreationEntry( "Flow","Create a Flow",
				SdFlow.class,new SdObjectCreationFactory( SdFlow.class ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.OBJECT_ICON ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.OBJECT_ICON ) ) );
		objectGroup.add(new CombinedTemplateCreationEntry( "Stock","Create a Stock",
				SdStock.class,new SdObjectCreationFactory( SdStock.class ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.OBJECT_ICON ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.OBJECT_ICON ) ) );
		objectGroup.add(new CombinedTemplateCreationEntry( "Input","Create a Input",
				SdInput.class,new SdObjectCreationFactory( SdInput.class ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.OBJECT_ICON ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.OBJECT_ICON ) ) );
		objectGroup.add(new CombinedTemplateCreationEntry( "Output","Create a Output",
				SdOutput.class,new SdObjectCreationFactory( SdOutput.class ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.OBJECT_ICON ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.OBJECT_ICON ) ) );
		objectGroup.add(new CombinedTemplateCreationEntry( "Variable","Create a Variable",
				SdVariable.class,new SdObjectCreationFactory( SdVariable.class ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.OBJECT_ICON ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.OBJECT_ICON ) ) );
		root.setDefaultEntry( selectionToolEntry );
		
		PaletteDrawer  equationsGroup = new PaletteDrawer ( "Equation Objects" );
		root.add( equationsGroup );
		
		equationsGroup.add(new CombinedTemplateCreationEntry( "Calculate","Create a Calculate equation",
				SdCalculate.class,new SdObjectCreationFactory( SdCalculate.class ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.OBJECT_ICON ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.OBJECT_ICON ) ) );
		root.setDefaultEntry( selectionToolEntry );
		
		equationsGroup.add( new CombinedTemplateCreationEntry( "Lookup","Create a Lookup equation",
				SdLookup.class,new SdObjectCreationFactory( SdLookup.class ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", "icons/object.gif" ),
				AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner", IImageKeys.OBJECT_ICON ) ) );
		root.setDefaultEntry( selectionToolEntry );
		
      return root; 
    } 
} 