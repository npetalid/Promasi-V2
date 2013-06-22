/**
 * 
 */
package org.promasi.game.company;

import org.promasi.game.model.generated.MarketPlaceModel;

/**
 * @author alekstheod
 *
 */
public interface IMarketPlaceListener {
	
	/**
	 * 
	 * @param marketPlace
	 */
	void MarketPlaceChanged( MarketPlaceModel marketPlace );
}
