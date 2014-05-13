package org.promasi.coredesigner.utilities;


import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommandI;


/**
 * 
 * Implements the ZIDZ function. Divide A by B. If B is zero (actually smaller
 * than 1E-6), then return 0.0.
 * 
 * @author eddiefullmetal
 * 
 */
public class ZidzFunction
        implements PostfixMathCommandI
{

    /**
     * The lower value of a double in order to be considered as 0.
     */
    private static final double ACTUAL_ZERO = 1.0 * Math.pow( Math.E, -6.0 );

    @Override
    public boolean checkNumberOfParameters ( int arg0 )
    {
        return arg0 == 2;
    }

    @Override
    public int getNumberOfParameters ( )
    {
        return 2;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void run ( Stack stack )
            throws ParseException
    {

        double var2 = (Double) stack.pop( );
        double var1 = (Double) stack.pop( );

        double val;
        if ( var2 < ACTUAL_ZERO )
        {
            val = 0.0;
        }
        else
        {
            val = var1 / var2;
        }
        stack.push( val );
    }

    @Override
    public void setCurNumberOfParameters ( int arg0 )
    {

    }
}
