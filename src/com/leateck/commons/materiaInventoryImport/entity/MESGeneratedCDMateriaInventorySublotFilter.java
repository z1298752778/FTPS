package com.leateck.commons.materiaInventoryImport.entity;

import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.Server;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectFilter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;

import java.util.List;

/**
 * Generated filter class for application table CD_MateriaInventorySublot.
 */
public abstract class MESGeneratedCDMateriaInventorySublotFilter extends MESATObjectFilter implements IMESGeneratedCDMateriaInventorySublotFilter {

    /** Generated attribute definition */
    private static final long serialVersionUID = 1L;

    /** Generated attribute definition */
    protected static final String ATDEFINITION_NAME = "CD_MateriaInventorySublot";

    /**
     * Generated constructor
     * 
     * @param server The Server object
     */
    public MESGeneratedCDMateriaInventorySublotFilter(Server server) {
        super(server, ATDEFINITION_NAME);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedCDMateriaInventorySublotFilter() {
        this(PCContext.getServerImpl());
    }

    @Override
    public List<IMESCDMateriaInventorySublot> getFilteredObjects() {
        return MESATObject.getFilteredMESATObjectList(this, IMESCDMateriaInventorySublot.class);
    }

      @Override
      public IMESCDMateriaInventorySublotFilter forBatchNoContaining(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameContaining(IMESCDMateriaInventorySublot.COL_NAME_BATCHNO, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forBatchNoEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameEqualTo(IMESCDMateriaInventorySublot.COL_NAME_BATCHNO, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forBatchNoNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameNotEqualTo(IMESCDMateriaInventorySublot.COL_NAME_BATCHNO, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forBatchNoStartingWith(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameStartingWith(IMESCDMateriaInventorySublot.COL_NAME_BATCHNO, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forImportNumberContaining(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameContaining(IMESCDMateriaInventorySublot.COL_NAME_IMPORTNUMBER, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forImportNumberEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameEqualTo(IMESCDMateriaInventorySublot.COL_NAME_IMPORTNUMBER, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forImportNumberNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameNotEqualTo(IMESCDMateriaInventorySublot.COL_NAME_IMPORTNUMBER, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forImportNumberStartingWith(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameStartingWith(IMESCDMateriaInventorySublot.COL_NAME_IMPORTNUMBER, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forInventoryQtyContaining(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameContaining(IMESCDMateriaInventorySublot.COL_NAME_INVENTORYQTY, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forInventoryQtyEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameEqualTo(IMESCDMateriaInventorySublot.COL_NAME_INVENTORYQTY, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forInventoryQtyNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameNotEqualTo(IMESCDMateriaInventorySublot.COL_NAME_INVENTORYQTY, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forInventoryQtyStartingWith(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameStartingWith(IMESCDMateriaInventorySublot.COL_NAME_INVENTORYQTY, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forMaterialDescContaining(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameContaining(IMESCDMateriaInventorySublot.COL_NAME_MATERIALDESC, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forMaterialDescEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameEqualTo(IMESCDMateriaInventorySublot.COL_NAME_MATERIALDESC, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forMaterialDescNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameNotEqualTo(IMESCDMateriaInventorySublot.COL_NAME_MATERIALDESC, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forMaterialDescStartingWith(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameStartingWith(IMESCDMateriaInventorySublot.COL_NAME_MATERIALDESC, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forMaterialNoContaining(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameContaining(IMESCDMateriaInventorySublot.COL_NAME_MATERIALNO, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forMaterialNoEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameEqualTo(IMESCDMateriaInventorySublot.COL_NAME_MATERIALNO, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forMaterialNoNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameNotEqualTo(IMESCDMateriaInventorySublot.COL_NAME_MATERIALNO, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forMaterialNoStartingWith(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameStartingWith(IMESCDMateriaInventorySublot.COL_NAME_MATERIALNO, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forQuantityContaining(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameContaining(IMESCDMateriaInventorySublot.COL_NAME_QUANTITY, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forQuantityEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameEqualTo(IMESCDMateriaInventorySublot.COL_NAME_QUANTITY, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forQuantityNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameNotEqualTo(IMESCDMateriaInventorySublot.COL_NAME_QUANTITY, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forQuantityStartingWith(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameStartingWith(IMESCDMateriaInventorySublot.COL_NAME_QUANTITY, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forSpecificationsContaining(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameContaining(IMESCDMateriaInventorySublot.COL_NAME_SPECIFICATIONS, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forSpecificationsEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameEqualTo(IMESCDMateriaInventorySublot.COL_NAME_SPECIFICATIONS, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forSpecificationsNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameNotEqualTo(IMESCDMateriaInventorySublot.COL_NAME_SPECIFICATIONS, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forSpecificationsStartingWith(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameStartingWith(IMESCDMateriaInventorySublot.COL_NAME_SPECIFICATIONS, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forSublotNumberContaining(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameContaining(IMESCDMateriaInventorySublot.COL_NAME_SUBLOTNUMBER, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forSublotNumberEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameEqualTo(IMESCDMateriaInventorySublot.COL_NAME_SUBLOTNUMBER, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forSublotNumberNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameNotEqualTo(IMESCDMateriaInventorySublot.COL_NAME_SUBLOTNUMBER, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forSublotNumberStartingWith(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameStartingWith(IMESCDMateriaInventorySublot.COL_NAME_SUBLOTNUMBER, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forUnitContaining(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameContaining(IMESCDMateriaInventorySublot.COL_NAME_UNIT, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forUnitEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameEqualTo(IMESCDMateriaInventorySublot.COL_NAME_UNIT, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forUnitNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameNotEqualTo(IMESCDMateriaInventorySublot.COL_NAME_UNIT, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forUnitStartingWith(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameStartingWith(IMESCDMateriaInventorySublot.COL_NAME_UNIT, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forUomContaining(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameContaining(IMESCDMateriaInventorySublot.COL_NAME_UOM, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forUomEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameEqualTo(IMESCDMateriaInventorySublot.COL_NAME_UOM, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forUomNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameNotEqualTo(IMESCDMateriaInventorySublot.COL_NAME_UOM, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forUomStartingWith(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameStartingWith(IMESCDMateriaInventorySublot.COL_NAME_UOM, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forValidityDataContaining(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameContaining(IMESCDMateriaInventorySublot.COL_NAME_VALIDITYDATA, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forValidityDataEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameEqualTo(IMESCDMateriaInventorySublot.COL_NAME_VALIDITYDATA, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forValidityDataNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameNotEqualTo(IMESCDMateriaInventorySublot.COL_NAME_VALIDITYDATA, value);
      }

      @Override
      public IMESCDMateriaInventorySublotFilter forValidityDataStartingWith(String value) //
              throws DatasweepException {
          return (IMESCDMateriaInventorySublotFilter) forColumnNameStartingWith(IMESCDMateriaInventorySublot.COL_NAME_VALIDITYDATA, value);
      }

}