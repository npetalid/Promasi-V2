package org.promasi.core.computationalsequences;


import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.promasi.core.IComputationalSequence;
import org.promasi.core.ISdObject;
import org.promasi.core.SdModel;
import org.promasi.core.SdObjectType;
import org.promasi.utilities.ErrorBuilder;


/**
 * Provides a default implementation of the {@link IComputationalSequence}.
 * 
 * @author eddiefullmetal
 * 
 */
public class DefaultComputationalSequence
        implements IComputationalSequence
{
    /**
     * The {@link SdModel} that this {@link IComputationalSequence} will
     * calculate the values for.
     */
    private SdModel _model;

    /**
     * Holds all the {@link ISdObject}s that their values have been computed for
     * the current step.
     */
    private List<ISdObject> _valuesComputedForCurrentStep;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( DefaultComputationalSequence.class );

    /**
     * Initializes the object.
     */
    public DefaultComputationalSequence( )
    {
        _valuesComputedForCurrentStep = new Vector<ISdObject>( );
    }

    @Override
    public void computeValues ( )
    {
        LOGGER.info( "Computing values..." );
        _valuesComputedForCurrentStep.clear( );

        // Compute the values of variable types
        List<ISdObject> variables = _model.getSdObjectsByType( SdObjectType.Variable );
        for ( ISdObject variable : variables )
        {
            recursivelyComputeValue( variable );
        }

        // Compute the values of flow types
        List<ISdObject> flows = _model.getSdObjectsByType( SdObjectType.Flow );
        for ( ISdObject flow : flows )
        {
            recursivelyComputeValue( flow );
        }

        // Compute the values of output types
        List<ISdObject> outputs = _model.getSdObjectsByType( SdObjectType.Output );
        for ( ISdObject output : outputs )
        {
            recursivelyComputeValue( output );
        }

        // Compute the values of stock types for the next step.
        List<ISdObject> stocks = _model.getSdObjectsByType( SdObjectType.Stock );
        for ( ISdObject stock : stocks )
        {
            stock.calculateValue( );
        }
    }

    /**
     * Initializes the sequence.
     * 
     * @param model
     *            The {@link SdModel} that the computation sequence will
     *            calculate the values for.
     * @throws NullArgumentException
     *             In case the model is null.
     */
    @Override
    public void initialize ( SdModel model )
            throws NullArgumentException
    {
        // Check arguments.
        if ( model == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "initialize", "model" ) );
            throw new NullArgumentException( "model" );
        }
        _model = model;
    }

    /**
     * Recursively computes the value of a variable by computing all the values
     * of its dependencies first.
     * 
     * @param sdObject
     *            The {@link ISdObject} to compute the value for.
     */
    private void recursivelyComputeValue ( ISdObject sdObject )
    {
        // If the object is in the list its value has been calculated for this
        // step so skip it.
        if ( !_valuesComputedForCurrentStep.contains( sdObject ) )
        {
            // Calculate the values of the dependencies by recursively calling
            // the method.
            List<ISdObject> dependencies = sdObject.getDependencies( );
            for ( ISdObject dependencyObject : dependencies )
            {
                // If the SdObjectType is a Stock then there is no need to
                // calculate its value. Stock values are calculated one step
                // ahead.
                if ( !dependencyObject.getType( ).equals( SdObjectType.Stock ) )
                {
                    recursivelyComputeValue( dependencyObject );
                }
            }
            // Calculate the value of the object since all dependencies have
            // been calculated.
            sdObject.calculateValue( );
            // Add it to the list so it won't be calculated again for this step.
            _valuesComputedForCurrentStep.add( sdObject );
        }
    }
}
