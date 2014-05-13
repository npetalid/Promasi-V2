package org.promasi.coredesigner.ui.dialogs;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.List;

import org.nfunk.jep.JEP;
import org.nfunk.jep.function.PostfixMathCommandI;

import org.promasi.coredesigner.resources.ImageUtilities;
import org.promasi.coredesigner.utilities.JepInitializer;
/**
 * 
 * @author antoxron
 *
 */
public class CalculateEquationDialog extends ApplicationWindow implements SelectionListener , MouseListener{
	
	
	
	private java.util.List<String> _variables;
	private boolean _saveSettings = false;

	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text _calculateText;
	private JEP _jep;
	private Button _sevenButton;
	private Button _eightButton;
	private Button _nineButton;
	private Button _plusButton;
	private Button _fourButton;
	private Button _fiveButton;
	private Button _sixButton;
	private Button _minButton;
	private Button _oneButton;
	private Button _twoButton;
	private Button _threeButton;
	private Button _mulButton;
	private Button _zeroButton;
	private Button _eButton;
	private Button _dotButton;
	private Button _slashButton;
	private Button _lBracketButton;
	private Button _rBracketButton;
	private Button _commaButton;
	private Button _powerButton;
	private List _functionsList;
	private List _variableList;
	
	
	private String _calculatedString;
	
	
	/**
	 * Create the application window.
	 */
	public CalculateEquationDialog(java.util.List<String> variables ,String calculateString) {
		super(null);
		setShellStyle(SWT.SYSTEM_MODAL | SWT.TITLE);
		_variables = variables;
		_calculatedString = calculateString;
		
	}
	public boolean saveSettings() {
		return _saveSettings;
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		final ScrolledForm scrolledForm = formToolkit.createScrolledForm(container);
		formToolkit.paintBordersFor(scrolledForm);
		scrolledForm.setText("Calculate Equation");
		scrolledForm.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm mainSashForm = new SashForm(scrolledForm.getBody(), SWT.NONE);
		mainSashForm.setOrientation(SWT.VERTICAL);
		formToolkit.adapt(mainSashForm);
		formToolkit.paintBordersFor(mainSashForm);
		
		Composite topContainer = formToolkit.createComposite(mainSashForm, SWT.NONE);
		formToolkit.paintBordersFor(topContainer);
		topContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		_calculateText = formToolkit.createText(topContainer, "New Text", SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		_calculateText.setText("");
		
		Composite bottomContainer = formToolkit.createComposite(mainSashForm, SWT.NONE);
		formToolkit.paintBordersFor(bottomContainer);
		bottomContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm bottomSashForm = new SashForm(bottomContainer, SWT.NONE);
		formToolkit.adapt(bottomSashForm);
		formToolkit.paintBordersFor(bottomSashForm);
		
		Composite leftContainer = new Composite(bottomSashForm, SWT.NONE);
		formToolkit.adapt(leftContainer);
		formToolkit.paintBordersFor(leftContainer);
		GridLayout gl_leftContainer = new GridLayout(4, true);
		gl_leftContainer.marginWidth = 2;
		gl_leftContainer.horizontalSpacing = 2;
		leftContainer.setLayout(gl_leftContainer);

		_sevenButton = new Button(leftContainer, SWT.NONE);
		_sevenButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_sevenButton, true, true);
		_sevenButton.setText("7");
		_sevenButton.addSelectionListener(this);
		
		_eightButton = new Button(leftContainer, SWT.NONE);
		_eightButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_eightButton, true, true);
		_eightButton.setText("8");
		_eightButton.addSelectionListener(this);
		
		_nineButton = new Button(leftContainer, SWT.NONE);
		_nineButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_nineButton, true, true);
		_nineButton.setText("9");
		_nineButton.addSelectionListener(this);
		
		_plusButton = new Button(leftContainer, SWT.NONE);
		GridData gd__plusButton = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd__plusButton.widthHint = 43;
		_plusButton.setLayoutData(gd__plusButton);
		formToolkit.adapt(_plusButton, true, true);
		_plusButton.setText("+");
		_plusButton.addSelectionListener(this);
		
		_fourButton = new Button(leftContainer, SWT.NONE);
		_fourButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		formToolkit.adapt(_fourButton, true, true);
		_fourButton.setText("4");
		_fourButton.addSelectionListener(this);
		
	     _fiveButton = new Button(leftContainer, SWT.NONE);
		_fiveButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_fiveButton, true, true);
		_fiveButton.setText("5");
		_fiveButton.addSelectionListener(this);
		
		
		
		_sixButton = new Button(leftContainer, SWT.NONE);
		_sixButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_sixButton, true, true);
		_sixButton.setText("6");
		_sixButton.addSelectionListener(this);
		
		
		_minButton = new Button(leftContainer, SWT.NONE);
		GridData gd__minButton = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd__minButton.widthHint = 40;
		_minButton.setLayoutData(gd__minButton);
		formToolkit.adapt(_minButton, true, true);
		_minButton.setText("-");
		_minButton.addSelectionListener(this);
		
		_oneButton = new Button(leftContainer, SWT.NONE);
		_oneButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_oneButton, true, true);
		_oneButton.setText("1");
		_oneButton.addSelectionListener(this);
		
		_twoButton = new Button(leftContainer, SWT.NONE);
		_twoButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_twoButton, true, true);
		_twoButton.setText("2");
		_twoButton.addSelectionListener(this);
		
		_threeButton = new Button(leftContainer, SWT.NONE);
		_threeButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_threeButton, true, true);
		_threeButton.setText("3");
		_threeButton.addSelectionListener(this);
		
		_mulButton = new Button(leftContainer, SWT.NONE);
		_mulButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_mulButton, true, true);
		_mulButton.setText("*");
		_mulButton.addSelectionListener(this);
		
		_zeroButton = new Button(leftContainer, SWT.NONE);
		_zeroButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_zeroButton, true, true);
		_zeroButton.setText("0");
		_zeroButton.addSelectionListener(this);
		
		_eButton = new Button(leftContainer, SWT.NONE);
		_eButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_eButton, true, true);
		_eButton.setText("E");
		_eButton.addSelectionListener(this);
		
		_dotButton = new Button(leftContainer, SWT.NONE);
		_dotButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_dotButton, true, true);
		_dotButton.setText(".");
		_dotButton.addSelectionListener(this);
		
		
		_slashButton = new Button(leftContainer, SWT.NONE);
		_slashButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_slashButton, true, true);
		_slashButton.setText("/");
		_slashButton.addSelectionListener(this);
		
		 _lBracketButton = new Button(leftContainer, SWT.NONE);
		_lBracketButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_lBracketButton, true, true);
		_lBracketButton.setText("(");
		_lBracketButton.addSelectionListener(this);
		
		_rBracketButton = new Button(leftContainer, SWT.NONE);
		_rBracketButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_rBracketButton, true, true);
		_rBracketButton.setText(")");
		_rBracketButton.addSelectionListener(this);
		
		_commaButton = new Button(leftContainer, SWT.NONE);
		_commaButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_commaButton, true, true);
		_commaButton.setText(",");
		_commaButton.addSelectionListener(this);
		
		_powerButton = new Button(leftContainer, SWT.NONE);
		_powerButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(_powerButton, true, true);
		_powerButton.setText("^");
		_powerButton.addSelectionListener(this);
		
		Composite rightContainer = new Composite(bottomSashForm, SWT.NONE);
		formToolkit.adapt(rightContainer);
		formToolkit.paintBordersFor(rightContainer);
		rightContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		CTabFolder tabFolder = new CTabFolder(rightContainer, SWT.BORDER);
		tabFolder.setBackground(ImageUtilities.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		formToolkit.adapt(tabFolder);
		formToolkit.paintBordersFor(tabFolder);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem variablesTabItem = new CTabItem(tabFolder, SWT.NONE);
		variablesTabItem.setText("Variables");
		
		Composite listContainer = new Composite(tabFolder, SWT.NONE);
		variablesTabItem.setControl(listContainer);
		formToolkit.paintBordersFor(listContainer);
		listContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		_variableList = new List(listContainer, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		_variableList.addMouseListener(this);

		formToolkit.adapt(_variableList, true, true);
		
		CTabItem functionsTabItem = new CTabItem(tabFolder, SWT.NONE);
		functionsTabItem.setText("Functions");
		
		Composite functionsContainer = new Composite(tabFolder, SWT.NONE);
		functionsTabItem.setControl(functionsContainer);
		formToolkit.paintBordersFor(functionsContainer);
		functionsContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		_functionsList = new List(functionsContainer, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		_functionsList.addMouseListener(this);

		_jep = JepInitializer.getFullJep( );

		
		@SuppressWarnings("unchecked")
		Set<String> keys = _jep.getFunctionTable( ).keySet();
		Iterator<String> it = keys.iterator();
			
		while (it.hasNext()) {
			_functionsList.add(it.next());
		}
		formToolkit.adapt(_functionsList, true, true);
		bottomSashForm.setWeights(new int[] {188, 231});
		mainSashForm.setWeights(new int[] {79, 149});

		
		
		
		scrolledForm.getToolBarManager().add(new Action("Save") {  
            public void run() {  
            	
            		_calculatedString = _calculateText.getText();
            		if (_calculatedString != null) {
            			if (!_calculatedString.trim().isEmpty()) {
            				_saveSettings = true;
            			}
            		}
            		scrolledForm.getShell().close();
            }  
        });  
		 
		scrolledForm.getToolBarManager().add(new Action("Cancel") {  
            public void run() {  
            	scrolledForm.getShell().close();
            		
            }  
        });  
	
		scrolledForm.updateToolBar(); 
		loadVariables();
		return container;
	}
	private void loadVariables() {
		if (_variables != null) {
			for (String variable : _variables) {
				_variableList.add(variable);
			}
		}
		if (_calculatedString != null) {
			_calculateText.setText(_calculatedString);
		}
	}

	public void setCalculatedString(String calculatedString) {
		_calculatedString = calculatedString;
	}
	public String getCalculatedString() {
		return _calculatedString;
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Equation Dialog");
		
		Rectangle displayBounds = newShell.getDisplay().getBounds();
		int nMinWidth = 450;
	    int nMinHeight = 320;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
	    newShell.setBounds(nLeft, nTop, nMinWidth, nMinHeight);
	}


	@Override
	public void widgetSelected(SelectionEvent e) {
		
		
		Button source = (Button) e.getSource();
		String selectionText = source.getText();
		int caretPosition = _calculateText.getCaretPosition();
		
		String text = _calculateText.getText();
		
		String leftText = text.substring(0, caretPosition);
		String rightText = text.substring(caretPosition, text.length());
		
		text = leftText + selectionText + rightText;
		_calculateText.setText("");
		_calculateText.append(text);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
		
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		Object object = e.getSource();

		if (object.equals(_variableList))  {
			  
			  int index = _variableList.getSelectionIndex();
			  if (index >= 0) {
				  String selectionText = _variableList.getItem(index);
				  
				  int caretPosition = _calculateText.getCaretPosition();
					
					String text = _calculateText.getText();
					
					String leftText = text.substring(0, caretPosition);
					String rightText = text.substring(caretPosition, text.length());
					
					text = leftText + selectionText + rightText;
					_calculateText.setText("");
					_calculateText.append(text);
			  }
			 
		  }
		else if (object.equals(_functionsList))  {
			  
			  int index = _functionsList.getSelectionIndex();
			  if (index >= 0) {
				  String selectionText = _functionsList.getItem(index);
				  
				  int caretPosition = _calculateText.getCaretPosition();
					
					String text = _calculateText.getText();
					
					String leftText = text.substring(0, caretPosition);
					String rightText = text.substring(caretPosition, text.length());
					
					text = leftText + buildFunction (selectionText ) + rightText;
					_calculateText.setText("");
					_calculateText.append(text);
			  }
			 
		  }
		
	}
	private String buildFunction ( String functionName ) {
	      // Get the PostfixMathCommandI from the functionName if this doesn't
	      // exist return an empty string.
	      PostfixMathCommandI function = _jep.getFunctionTable( ).get( functionName );
	      if ( function == null ) {
	          return StringUtils.EMPTY;
	      }

	      // First create a string like 'sin('
	      StringBuilder builder = new StringBuilder( );
	      builder.append( functionName );
	      builder.append( "(" );
	      if ( function.getNumberOfParameters( ) > 0 ) {
	          // Depending on the number of parameters add an argument string
	          // like arg0 so the string will look like 'sin(arg0,'
	          for ( int i = 0; i < function.getNumberOfParameters( ); i++ ) {
	              builder.append( "arg" );
	              builder.append( i );
	              builder.append( "," );
	          }
	          // Remove the last char , from the string so the string will
	          // look like 'sin(arg0'
	          builder.deleteCharAt( builder.length( ) - 1 );
	      }
	      else {
	          // If the parameter are lower than 0 then
	          // the number of parameters that the function takes is unknown
	          // (cannot be calculated dynamically) so add a ? so the string
	          // will look like 'sin(?'
	          builder.append( "?" );
	      }
	      // finish the string by adding a ) so the string will look like
	      // 'sin(arg0)'
	      builder.append( ")" );

	      return builder.toString( );
	  }
	@Override
	public void mouseDown(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
