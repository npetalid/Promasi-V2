package org.promasi.shell.model.communication;


import org.apache.commons.jxpath.CompiledExpression;
import org.apache.commons.jxpath.JXPathContext;
import org.promasi.model.Company;


/**
 * 
 * An {@link IResolver} that takes string as data and uses the <a
 * href="http://commons.apache.org/jxpath/">JXPath library</a>
 * 
 * @author eddiefullmetal
 * 
 */
public class JXPathResolver
        implements IResolver
{

    @Override
    public Double getValue ( Object data, Company context )
    {
        return (Double) JXPathContext.newContext( context ).getValue( data.toString( ) );
    }

    @Override
    public void setValue ( Object data, Company context, Object value )
    {
        JXPathContext.newContext( context ).setValue( data.toString( ), value );
    }

    @Override
    public boolean canHandleData ( Object data )
    {
        if ( data instanceof String )
        {
            CompiledExpression compiledExpression;
            try
            {
                compiledExpression = JXPathContext.compile( data.toString( ) );
            }
            catch ( Exception e )
            {
                return false;
            }

            if ( compiledExpression != null )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
}
