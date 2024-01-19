package com.leateck.model.areaFilterObject;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.Server;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectFilter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import java.util.List;

/**
 * Generated filter class for application table LC_StorageAreaFilterObject.
 */
public abstract class MESGeneratedLCStorageAreaFilterObjectFilter extends MESATObjectFilter implements IMESGeneratedLCStorageAreaFilterObjectFilter {

    /** Generated attribute definition */
    private static final long serialVersionUID = 1L;

    /** Generated attribute definition */
    protected static final String ATDEFINITION_NAME = "LC_StorageAreaFilterObject";

    /**
     * Generated constructor
     * 
     * @param server The Server object
     */
    public MESGeneratedLCStorageAreaFilterObjectFilter(Server server) {
        super(server, ATDEFINITION_NAME);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedLCStorageAreaFilterObjectFilter() {
        this(PCContext.getServerImpl());
    }

    @Override
    public List<IMESLCStorageAreaFilterObject> getFilteredObjects() {
        return MESATObject.getFilteredMESATObjectList(this, IMESLCStorageAreaFilterObject.class);
    }

      @Override
      public IMESLCStorageAreaFilterObjectFilter forStorageAreaContaining(String value) //
              throws DatasweepException {
          return (IMESLCStorageAreaFilterObjectFilter) forColumnNameContaining(IMESLCStorageAreaFilterObject.COL_NAME_STORAGEAREA, value);
      }

      @Override
      public IMESLCStorageAreaFilterObjectFilter forStorageAreaEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCStorageAreaFilterObjectFilter) forColumnNameEqualTo(IMESLCStorageAreaFilterObject.COL_NAME_STORAGEAREA, value);
      }

      @Override
      public IMESLCStorageAreaFilterObjectFilter forStorageAreaNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCStorageAreaFilterObjectFilter) forColumnNameNotEqualTo(IMESLCStorageAreaFilterObject.COL_NAME_STORAGEAREA, value);
      }

      @Override
      public IMESLCStorageAreaFilterObjectFilter forStorageAreaStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCStorageAreaFilterObjectFilter) forColumnNameStartingWith(IMESLCStorageAreaFilterObject.COL_NAME_STORAGEAREA, value);
      }

      @Override
      public IMESLCStorageAreaFilterObjectFilter forStorageAreaDescContaining(String value) //
              throws DatasweepException {
          return (IMESLCStorageAreaFilterObjectFilter) forColumnNameContaining(IMESLCStorageAreaFilterObject.COL_NAME_STORAGEAREADESC, value);
      }

      @Override
      public IMESLCStorageAreaFilterObjectFilter forStorageAreaDescEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCStorageAreaFilterObjectFilter) forColumnNameEqualTo(IMESLCStorageAreaFilterObject.COL_NAME_STORAGEAREADESC, value);
      }

      @Override
      public IMESLCStorageAreaFilterObjectFilter forStorageAreaDescNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCStorageAreaFilterObjectFilter) forColumnNameNotEqualTo(IMESLCStorageAreaFilterObject.COL_NAME_STORAGEAREADESC, value);
      }

      @Override
      public IMESLCStorageAreaFilterObjectFilter forStorageAreaDescStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCStorageAreaFilterObjectFilter) forColumnNameStartingWith(IMESLCStorageAreaFilterObject.COL_NAME_STORAGEAREADESC, value);
      }

      @Override
      public IMESLCStorageAreaFilterObjectFilter forWarehouseContaining(String value) //
              throws DatasweepException {
          return (IMESLCStorageAreaFilterObjectFilter) forColumnNameContaining(IMESLCStorageAreaFilterObject.COL_NAME_WAREHOUSE, value);
      }

      @Override
      public IMESLCStorageAreaFilterObjectFilter forWarehouseEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCStorageAreaFilterObjectFilter) forColumnNameEqualTo(IMESLCStorageAreaFilterObject.COL_NAME_WAREHOUSE, value);
      }

      @Override
      public IMESLCStorageAreaFilterObjectFilter forWarehouseNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCStorageAreaFilterObjectFilter) forColumnNameNotEqualTo(IMESLCStorageAreaFilterObject.COL_NAME_WAREHOUSE, value);
      }

      @Override
      public IMESLCStorageAreaFilterObjectFilter forWarehouseStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCStorageAreaFilterObjectFilter) forColumnNameStartingWith(IMESLCStorageAreaFilterObject.COL_NAME_WAREHOUSE, value);
      }

}