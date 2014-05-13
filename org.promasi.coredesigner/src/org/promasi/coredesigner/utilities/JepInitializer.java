package org.promasi.coredesigner.utilities;


import org.nfunk.jep.JEP;


/**
 * 
 * Registers all the standard and custom functions to a {@link JEP} object and
 * returns it.
 * 
 * @author eddiefullmetal
 * 
 */
public final class JepInitializer
{

    /**
     * Initializes the object.
     */
    private JepInitializer( )
    {

    }

    /**
     * Returns a {@link JEP} object that contains all the standard and custom
     * functions.
     * 
     * @return A {@link JEP} object.
     */
    public static JEP getFullJep ( )
    {
        JEP fullJep = new JEP( );
        fullJep.addStandardFunctions( );
        fullJep.addStandardConstants( );
        fullJep.addFunction( "Max", new MaxFunction( ) );
        fullJep.addFunction( "Min", new MinFunction( ) );
        fullJep.addFunction( "Zidz", new ZidzFunction( ) );

        fullJep.setAllowUndeclared( true );

        return fullJep;
    }
}
