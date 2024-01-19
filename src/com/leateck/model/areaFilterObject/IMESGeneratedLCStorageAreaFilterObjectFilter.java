package com.leateck.model.areaFilterObject;

import com.datasweep.compatibility.client.DatasweepException;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObjectFilter;
import java.util.List;

/**
 * Generated filter interface for application table LC_StorageAreaFilterObject.
 * 
 * @ftps.exclude 
 */
public interface IMESGeneratedLCStorageAreaFilterObjectFilter extends IMESATObjectFilter {

    /**
     * Generated method definition
     * 
     * @return the list of the objects
     */
    @Override
    public List<IMESLCStorageAreaFilterObject> getFilteredObjects();

    /**
     * Generated method definition
     * 
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException thrown when error occurs
     */
    public IMESLCStorageAreaFilterObjectFilter forStorageAreaContaining(String value) //
            throws DatasweepException;

    /**
     * Generated method definition
     * 
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException thrown when error occurs
     */
    public IMESLCStorageAreaFilterObjectFilter forStorageAreaEqualTo(String value) //
            throws DatasweepException;

    /**
     * Generated method definition
     * 
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException thrown when error occurs
     */
    public IMESLCStorageAreaFilterObjectFilter forStorageAreaNotEqualTo(String value) //
            throws DatasweepException;

    /**
     * Generated method definition
     * 
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException thrown when error occurs
     */
    public IMESLCStorageAreaFilterObjectFilter forStorageAreaStartingWith(String value) //
            throws DatasweepException;

    /**
     * Generated method definition
     * 
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException thrown when error occurs
     */
    public IMESLCStorageAreaFilterObjectFilter forStorageAreaDescContaining(String value) //
            throws DatasweepException;

    /**
     * Generated method definition
     * 
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException thrown when error occurs
     */
    public IMESLCStorageAreaFilterObjectFilter forStorageAreaDescEqualTo(String value) //
            throws DatasweepException;

    /**
     * Generated method definition
     * 
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException thrown when error occurs
     */
    public IMESLCStorageAreaFilterObjectFilter forStorageAreaDescNotEqualTo(String value) //
            throws DatasweepException;

    /**
     * Generated method definition
     * 
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException thrown when error occurs
     */
    public IMESLCStorageAreaFilterObjectFilter forStorageAreaDescStartingWith(String value) //
            throws DatasweepException;

    /**
     * Generated method definition
     * 
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException thrown when error occurs
     */
    public IMESLCStorageAreaFilterObjectFilter forWarehouseContaining(String value) //
            throws DatasweepException;

    /**
     * Generated method definition
     * 
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException thrown when error occurs
     */
    public IMESLCStorageAreaFilterObjectFilter forWarehouseEqualTo(String value) //
            throws DatasweepException;

    /**
     * Generated method definition
     * 
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException thrown when error occurs
     */
    public IMESLCStorageAreaFilterObjectFilter forWarehouseNotEqualTo(String value) //
            throws DatasweepException;

    /**
     * Generated method definition
     * 
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException thrown when error occurs
     */
    public IMESLCStorageAreaFilterObjectFilter forWarehouseStartingWith(String value) //
            throws DatasweepException;

}