package com.leateck.model.ordermapping;

/**
 * This file was generated by ATDefAccessClassGenerator and FMPP 2.3.15
 *
 * Please do not modify this file manually !!
 */
import com.leateck.model.ordermapping.IMESGeneratedLCOrderMappingFilter;
import com.leateck.model.ordermapping.IMESLCOrderMapping;
import com.leateck.model.ordermapping.IMESLCOrderMappingFilter;
import com.leateck.model.ordermapping.MESLCOrderMapping;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.Server;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectFilter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import java.util.List;

/**
 * Generated filter class for application table LC_OrderMapping.
 */
public abstract class MESGeneratedLCOrderMappingFilter extends MESATObjectFilter implements IMESGeneratedLCOrderMappingFilter {

    /** Generated attribute definition */
    private static final long serialVersionUID = 1L;

    /** Generated attribute definition */
    protected static final String ATDEFINITION_NAME = "LC_OrderMapping";

    /**
     * Generated constructor
     * 
     * @param server The Server object
     */
    public MESGeneratedLCOrderMappingFilter(Server server) {
        super(server, ATDEFINITION_NAME);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedLCOrderMappingFilter() {
        this(PCContext.getServerImpl());
    }

    @Override
    public List<IMESLCOrderMapping> getFilteredObjects() {
        return MESATObject.getFilteredMESATObjectList(this, IMESLCOrderMapping.class);
    }

      @Override
      public IMESLCOrderMappingFilter forCreateuserContaining(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameContaining(IMESLCOrderMapping.COL_NAME_CREATEUSER, value);
      }

      @Override
      public IMESLCOrderMappingFilter forCreateuserEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameEqualTo(IMESLCOrderMapping.COL_NAME_CREATEUSER, value);
      }

      @Override
      public IMESLCOrderMappingFilter forCreateuserNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameNotEqualTo(IMESLCOrderMapping.COL_NAME_CREATEUSER, value);
      }

      @Override
      public IMESLCOrderMappingFilter forCreateuserStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameStartingWith(IMESLCOrderMapping.COL_NAME_CREATEUSER, value);
      }

      @Override
      public IMESLCOrderMappingFilter forDelflagEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameEqualTo(IMESLCOrderMapping.COL_NAME_DELFLAG, value);
      }

      @Override
      public IMESLCOrderMappingFilter forDelflagGreaterThanOrEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameGreaterThanOrEqualTo(IMESLCOrderMapping.COL_NAME_DELFLAG, value);
      }

      @Override
      public IMESLCOrderMappingFilter forDelflagLessThan(Long value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameLessThan(IMESLCOrderMapping.COL_NAME_DELFLAG, value);
      }

      @Override
      public IMESLCOrderMappingFilter forDelflagNotEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameNotEqualTo(IMESLCOrderMapping.COL_NAME_DELFLAG, value);
      }

      @Override
      public IMESLCOrderMappingFilter forLastmodifieduserContaining(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameContaining(IMESLCOrderMapping.COL_NAME_LASTMODIFIEDUSER, value);
      }

      @Override
      public IMESLCOrderMappingFilter forLastmodifieduserEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameEqualTo(IMESLCOrderMapping.COL_NAME_LASTMODIFIEDUSER, value);
      }

      @Override
      public IMESLCOrderMappingFilter forLastmodifieduserNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameNotEqualTo(IMESLCOrderMapping.COL_NAME_LASTMODIFIEDUSER, value);
      }

      @Override
      public IMESLCOrderMappingFilter forLastmodifieduserStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameStartingWith(IMESLCOrderMapping.COL_NAME_LASTMODIFIEDUSER, value);
      }

      @Override
      public IMESLCOrderMappingFilter forMappingordernameContaining(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameContaining(IMESLCOrderMapping.COL_NAME_MAPPINGORDERNAME, value);
      }

      @Override
      public IMESLCOrderMappingFilter forMappingordernameEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameEqualTo(IMESLCOrderMapping.COL_NAME_MAPPINGORDERNAME, value);
      }

      @Override
      public IMESLCOrderMappingFilter forMappingordernameNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameNotEqualTo(IMESLCOrderMapping.COL_NAME_MAPPINGORDERNAME, value);
      }

      @Override
      public IMESLCOrderMappingFilter forMappingordernameStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameStartingWith(IMESLCOrderMapping.COL_NAME_MAPPINGORDERNAME, value);
      }

      @Override
      public IMESLCOrderMappingFilter forMappingtypeEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameEqualTo(IMESLCOrderMapping.COL_NAME_MAPPINGTYPE, value);
      }

      @Override
      public IMESLCOrderMappingFilter forMappingtypeGreaterThanOrEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameGreaterThanOrEqualTo(IMESLCOrderMapping.COL_NAME_MAPPINGTYPE, value);
      }

      @Override
      public IMESLCOrderMappingFilter forMappingtypeLessThan(Long value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameLessThan(IMESLCOrderMapping.COL_NAME_MAPPINGTYPE, value);
      }

      @Override
      public IMESLCOrderMappingFilter forMappingtypeNotEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameNotEqualTo(IMESLCOrderMapping.COL_NAME_MAPPINGTYPE, value);
      }

      @Override
      public IMESLCOrderMappingFilter forOrdernameContaining(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameContaining(IMESLCOrderMapping.COL_NAME_ORDERNAME, value);
      }

      @Override
      public IMESLCOrderMappingFilter forOrdernameEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameEqualTo(IMESLCOrderMapping.COL_NAME_ORDERNAME, value);
      }

      @Override
      public IMESLCOrderMappingFilter forOrdernameNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameNotEqualTo(IMESLCOrderMapping.COL_NAME_ORDERNAME, value);
      }

      @Override
      public IMESLCOrderMappingFilter forOrdernameStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameStartingWith(IMESLCOrderMapping.COL_NAME_ORDERNAME, value);
      }

      @Override
      public IMESLCOrderMappingFilter forTypeEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameEqualTo(IMESLCOrderMapping.COL_NAME_TYPE, value);
      }

      @Override
      public IMESLCOrderMappingFilter forTypeGreaterThanOrEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameGreaterThanOrEqualTo(IMESLCOrderMapping.COL_NAME_TYPE, value);
      }

      @Override
      public IMESLCOrderMappingFilter forTypeLessThan(Long value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameLessThan(IMESLCOrderMapping.COL_NAME_TYPE, value);
      }

      @Override
      public IMESLCOrderMappingFilter forTypeNotEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCOrderMappingFilter) forColumnNameNotEqualTo(IMESLCOrderMapping.COL_NAME_TYPE, value);
      }

}