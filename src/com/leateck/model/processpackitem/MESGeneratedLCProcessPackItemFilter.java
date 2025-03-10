package com.leateck.model.processpackitem;

/**
 * This file was generated by ATDefAccessClassGenerator and FMPP 2.3.15
 *
 * Please do not modify this file manually !!
 */
import com.leateck.model.processpackitem.IMESGeneratedLCProcessPackItemFilter;
import com.leateck.model.processpackitem.IMESLCProcessPackItem;
import com.leateck.model.processpackitem.IMESLCProcessPackItemFilter;
import com.leateck.model.processpackitem.MESLCProcessPackItem;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.Server;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectFilter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import java.util.List;
import com.leateck.model.processpack.IMESLCProcessPack;

/**
 * Generated filter class for application table LC_ProcessPackItem.
 */
public abstract class MESGeneratedLCProcessPackItemFilter extends MESATObjectFilter implements IMESGeneratedLCProcessPackItemFilter {

    /** Generated attribute definition */
    private static final long serialVersionUID = 1L;

    /** Generated attribute definition */
    protected static final String ATDEFINITION_NAME = "LC_ProcessPackItem";

    /**
     * Generated constructor
     * 
     * @param server The Server object
     */
    public MESGeneratedLCProcessPackItemFilter(Server server) {
        super(server, ATDEFINITION_NAME);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedLCProcessPackItemFilter() {
        this(PCContext.getServerImpl());
    }

    @Override
    public List<IMESLCProcessPackItem> getFilteredObjects() {
        return MESATObject.getFilteredMESATObjectList(this, IMESLCProcessPackItem.class);
    }

      @Override
      public IMESLCProcessPackItemFilter forObjectKeyEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameEqualTo(IMESLCProcessPackItem.COL_NAME_OBJECTKEY, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forObjectKeyGreaterThanOrEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameGreaterThanOrEqualTo(IMESLCProcessPackItem.COL_NAME_OBJECTKEY, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forObjectKeyLessThan(Long value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameLessThan(IMESLCProcessPackItem.COL_NAME_OBJECTKEY, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forObjectKeyNotEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameNotEqualTo(IMESLCProcessPackItem.COL_NAME_OBJECTKEY, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemDesContaining(String value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameContaining(IMESLCProcessPackItem.COL_NAME_PROCESSITEMDES, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemDesEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameEqualTo(IMESLCProcessPackItem.COL_NAME_PROCESSITEMDES, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemDesNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameNotEqualTo(IMESLCProcessPackItem.COL_NAME_PROCESSITEMDES, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemDesStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameStartingWith(IMESLCProcessPackItem.COL_NAME_PROCESSITEMDES, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemIdContaining(String value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameContaining(IMESLCProcessPackItem.COL_NAME_PROCESSITEMID, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemIdEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameEqualTo(IMESLCProcessPackItem.COL_NAME_PROCESSITEMID, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemIdNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameNotEqualTo(IMESLCProcessPackItem.COL_NAME_PROCESSITEMID, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemIdStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameStartingWith(IMESLCProcessPackItem.COL_NAME_PROCESSITEMID, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemTypeEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameEqualTo(IMESLCProcessPackItem.COL_NAME_PROCESSITEMTYPE, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemTypeGreaterThanOrEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameGreaterThanOrEqualTo(IMESLCProcessPackItem.COL_NAME_PROCESSITEMTYPE, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemTypeLessThan(Long value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameLessThan(IMESLCProcessPackItem.COL_NAME_PROCESSITEMTYPE, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemTypeNotEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameNotEqualTo(IMESLCProcessPackItem.COL_NAME_PROCESSITEMTYPE, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemeVersionContaining(String value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameContaining(IMESLCProcessPackItem.COL_NAME_PROCESSITEMEVERSION, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemeVersionEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameEqualTo(IMESLCProcessPackItem.COL_NAME_PROCESSITEMEVERSION, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemeVersionNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameNotEqualTo(IMESLCProcessPackItem.COL_NAME_PROCESSITEMEVERSION, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessItemeVersionStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCProcessPackItemFilter) forColumnNameStartingWith(IMESLCProcessPackItem.COL_NAME_PROCESSITEMEVERSION, value);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessPackKeyEqualTo(IMESLCProcessPack value) //
              throws DatasweepException {
          Long key = (value == null) ? null : Long.valueOf(value.getKey());
          return (IMESLCProcessPackItemFilter) forColumnNameEqualTo(IMESLCProcessPackItem.COL_NAME_PROCESSPACKKEY, key);
      }

      @Override
      public IMESLCProcessPackItemFilter forProcessPackKeyNotEqualTo(IMESLCProcessPack value) //
              throws DatasweepException {
          Long key = (value == null) ? null : Long.valueOf(value.getKey());
          return (IMESLCProcessPackItemFilter) forColumnNameNotEqualTo(IMESLCProcessPackItem.COL_NAME_PROCESSPACKKEY, key);
      }

}