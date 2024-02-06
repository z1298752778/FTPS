package com.leateck.model.processPackItemObject;

/**
 * This file was generated by ATDefAccessClassGenerator and FMPP 2.3.15
 *
 * Please do not modify this file manually !!
 */
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.Server;
import com.leateck.model.processPackObject.IMESLCUserProcessPack;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectFilter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import java.util.List;
//import TO_BE_DEFINED.IMESLCUserProcessPack;

/**
 * Generated filter class for application table LC_UserProcessPackItem.
 */
public abstract class MESGeneratedLCUserProcessPackItemFilter extends MESATObjectFilter implements IMESGeneratedLCUserProcessPackItemFilter {

    /** Generated attribute definition */
    private static final long serialVersionUID = 1L;

    /** Generated attribute definition */
    protected static final String ATDEFINITION_NAME = "LC_UserProcessPackItem";

    /**
     * Generated constructor
     * 
     * @param server The Server object
     */
    public MESGeneratedLCUserProcessPackItemFilter(Server server) {
        super(server, ATDEFINITION_NAME);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedLCUserProcessPackItemFilter() {
        this(PCContext.getServerImpl());
    }

    @Override
    public List<IMESLCUserProcessPackItem> getFilteredObjects() {
        return MESATObject.getFilteredMESATObjectList(this, IMESLCUserProcessPackItem.class);
    }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemDesContaining(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameContaining(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMDES, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemDesEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameEqualTo(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMDES, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemDesNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameNotEqualTo(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMDES, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemDesStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameStartingWith(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMDES, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemIdeContaining(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameContaining(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMIDE, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemIdeEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameEqualTo(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMIDE, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemIdeNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameNotEqualTo(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMIDE, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemIdeStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameStartingWith(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMIDE, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemTypeEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameEqualTo(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMTYPE, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemTypeGreaterThanOrEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameGreaterThanOrEqualTo(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMTYPE, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemTypeLessThan(Long value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameLessThan(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMTYPE, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemTypeNotEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameNotEqualTo(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMTYPE, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemVersionContaining(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameContaining(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMVERSION, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemVersionEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameEqualTo(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMVERSION, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemVersionNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameNotEqualTo(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMVERSION, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessItemVersionStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackItemFilter) forColumnNameStartingWith(IMESLCUserProcessPackItem.COL_NAME_PROCESSITEMVERSION, value);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessPackKeyEqualTo(IMESLCUserProcessPack value) //
              throws DatasweepException {
          Long key = (value == null) ? null : Long.valueOf(value.getKey());
          return (IMESLCUserProcessPackItemFilter) forColumnNameEqualTo(IMESLCUserProcessPackItem.COL_NAME_PROCESSPACKKEY, key);
      }

      @Override
      public IMESLCUserProcessPackItemFilter forProcessPackKeyNotEqualTo(IMESLCUserProcessPack value) //
              throws DatasweepException {
          Long key = (value == null) ? null : Long.valueOf(value.getKey());
          return (IMESLCUserProcessPackItemFilter) forColumnNameNotEqualTo(IMESLCUserProcessPackItem.COL_NAME_PROCESSPACKKEY, key);
      }

}