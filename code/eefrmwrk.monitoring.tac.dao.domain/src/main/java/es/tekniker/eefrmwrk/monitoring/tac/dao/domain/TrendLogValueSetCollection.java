/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Virginia Aranceta
 * License Type: Purchased
 */
package es.tekniker.eefrmwrk.monitoring.tac.dao.domain;

import org.orm.*;

public class TrendLogValueSetCollection extends org.orm.util.ORMSet {
	public TrendLogValueSetCollection(Object owner, org.orm.util.ORMAdapter adapter, int ownerKey, int targetKey, int collType) {
		super(owner, adapter, ownerKey, targetKey, true, collType);
	}
	
	public TrendLogValueSetCollection(Object owner, org.orm.util.ORMAdapter adapter, int ownerKey, int collType) {
		super(owner, adapter, ownerKey, -1, false, collType);
	}
	
	/**
	* Return an iterator over the persistent objects
	* @return The persisten objects iterator
	*/
	public java.util.Iterator getIterator() {
		return super.getIterator(_ownerAdapter);
	}
	
	/**
	 * Add the specified persistent object to ORMSet
	 * @param value the persistent object
	 */
	public void add(TrendLogValue value) {
		if (value != null) {
			super.add(value, value._ormAdapter);
		}
	}
	
	/**
	 * Remove the specified persistent object from ORMSet
	 * @param value the persistent object
	 */
	public void remove(TrendLogValue value) {
		super.remove(value, value._ormAdapter);
	}
	
	/**
	 * Return true if ORMSet contains the specified persistent object
	 * @param value the persistent object
	 * @return True if this contains the specified persistent object
	 */
	public boolean contains(TrendLogValue value) {
		return super.contains(value);
	}
	
	/**
	 * Return an array containing all of the persistent objects in ORMSet
	 * @return The persistent objects array
	 */
	public TrendLogValue[] toArray() {
		return (TrendLogValue[]) super.toArray(new TrendLogValue[size()]);
	}
	
	/**
	 * Return an sorted array containing all of the persistent objects in ORMSet
	 * @param propertyName Name of the property for sorting:<ul>
	 * <li>logTime</li>
	 * <li>valueType</li>
	 * <li>logValue</li>
	 * <li>sequence</li>
	 * </ul>
	 * @return The persistent objects sorted array
	 */
	public TrendLogValue[] toArray(String propertyName) {
		return toArray(propertyName, true);
	}
	
	/**
	 * Return an sorted array containing all of the persistent objects in ORMSet
	 * @param propertyName Name of the property for sorting:<ul>
	 * <li>logTime</li>
	 * <li>valueType</li>
	 * <li>logValue</li>
	 * <li>sequence</li>
	 * </ul>
	 * @param ascending true for ascending, false for descending
	 * @return The persistent objects sorted array
	 */
	public TrendLogValue[] toArray(String propertyName, boolean ascending) {
		return (TrendLogValue[]) super.toArray(new TrendLogValue[size()], propertyName, ascending);
	}
	
	protected PersistentManager getPersistentManager() throws PersistentException {
		return es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance();
	}
	
}

