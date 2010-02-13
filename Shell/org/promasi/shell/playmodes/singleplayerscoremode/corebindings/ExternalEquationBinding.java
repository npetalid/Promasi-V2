/**
 *
 */
package org.promasi.shell.playmodes.singleplayerscoremode.corebindings;


import org.promasi.core.equations.ExternalEquation;
import org.promasi.core.sdobjects.OutputSdObject;


/**
 * 
 * Defines the key of an {@link ExternalEquation} to which XPath it will bind.
 * 
 * @author eddiefullmetal
 * 
 */
public class ExternalEquationBinding
{
    /**
     * The key of the {@link OutputSdObject}.
     */
    private String _sdObjectKey;

    /**
     * The XPath of the that define the model property to bind.
     */
    private String _modelXPath;

    /**
     * Initializes the object.
     * 
     */
    public ExternalEquationBinding( )
    {

    }

    /**
     * @return the {@link #_sdObjectKey}.
     */
    public String getSdObjectKey ( )
    {
        return _sdObjectKey;
    }

    /**
     * @param sdObjectKey
     *            the {@link #_sdObjectKey} to set.
     */
    public void setSdObjectKey ( String sdObjectKey )
    {
        _sdObjectKey = sdObjectKey;
    }

    /**
     * @return the {@link #_modelXPath}.
     */
    public String getModelXPath ( )
    {
        return _modelXPath;
    }

    /**
     * @param modelXPath
     *            the {@link #_modelXPath} to set.
     */
    public void setModelXPath ( String modelXPath )
    {
        _modelXPath = modelXPath;
    }
}
