package org.promasi.utilities;


/**
 * Provides methods for generating strings for various errors.
 * 
 * @author eddiefullmetal
 * 
 */
public final class ErrorBuilder
{
    /**
     * Initializes the object.
     */
    private ErrorBuilder( )
    {

    }

    /**
     * 
     * Returns an error message when one argument is null in a method. <br>
     * Example message: <code>
     * Null argument(arg) passed at method1
     * </code>
     * 
     * @param parameter
     *            the null parameter name
     * @param method
     *            the method name that the error occurred
     * @return The error message
     */
    public static String generateNullArgumentError ( String method, String parameter )
    {
        StringBuilder builder = new StringBuilder( );

        builder.append( "Null argument(" );
        builder.append( parameter );
        builder.append( ") passed at " );
        builder.append( method );

        return builder.toString( );
    }

    /**
     * Returns an error message when one argument is null or empty in a method. <br>
     * Example message: <code>
     * Null argument(arg) passed at method1
     * </code>
     * 
     * @param parameter
     *            the null or empty parameter name
     * @param method
     *            the method name that the error occurred
     * @return The error message
     */
    public static String generateNullOrEmptyArgumentError ( String method, String parameter )
    {
        StringBuilder builder = new StringBuilder( );

        builder.append( "Null or Empty argument(" );
        builder.append( parameter );
        builder.append( ") passed at " );
        builder.append( method );

        return builder.toString( );
    }
}
