package com.leateck.model.userProcessPackRecord;

/**
 * This file was generated by ATDefAccessClassGenerator and FMPP 2.3.15
 *
 * Please do not modify this file manually !!
 */
import com.leateck.model.userProcessPackRecord.IMESGeneratedLCUserProcessPackRecordFilter;
import com.leateck.model.userProcessPackRecord.IMESLCUserProcessPackRecord;
import com.leateck.model.userProcessPackRecord.IMESLCUserProcessPackRecordFilter;
import com.leateck.model.userProcessPackRecord.MESLCUserProcessPackRecord;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.Server;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectFilter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import java.util.List;

/**
 * Generated filter class for application table LC_UserProcessPackRecord.
 */
public abstract class MESGeneratedLCUserProcessPackRecordFilter extends MESATObjectFilter implements IMESGeneratedLCUserProcessPackRecordFilter {

    /** Generated attribute definition */
    private static final long serialVersionUID = 1L;

    /** Generated attribute definition */
    protected static final String ATDEFINITION_NAME = "LC_UserProcessPackRecord";

    /**
     * Generated constructor
     * 
     * @param server The Server object
     */
    public MESGeneratedLCUserProcessPackRecordFilter(Server server) {
        super(server, ATDEFINITION_NAME);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedLCUserProcessPackRecordFilter() {
        this(PCContext.getServerImpl());
    }

    @Override
    public List<IMESLCUserProcessPackRecord> getFilteredObjects() {
        return MESATObject.getFilteredMESATObjectList(this, IMESLCUserProcessPackRecord.class);
    }

      @Override
      public IMESLCUserProcessPackRecordFilter forCommentsContaining(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameContaining(IMESLCUserProcessPackRecord.COL_NAME_COMMENTS, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forCommentsEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameEqualTo(IMESLCUserProcessPackRecord.COL_NAME_COMMENTS, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forCommentsNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameNotEqualTo(IMESLCUserProcessPackRecord.COL_NAME_COMMENTS, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forCommentsStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameStartingWith(IMESLCUserProcessPackRecord.COL_NAME_COMMENTS, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forOperatorContaining(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameContaining(IMESLCUserProcessPackRecord.COL_NAME_OPERATOR, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forOperatorEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameEqualTo(IMESLCUserProcessPackRecord.COL_NAME_OPERATOR, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forOperatorNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameNotEqualTo(IMESLCUserProcessPackRecord.COL_NAME_OPERATOR, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forOperatorStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameStartingWith(IMESLCUserProcessPackRecord.COL_NAME_OPERATOR, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forProcessPackIDesContaining(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameContaining(IMESLCUserProcessPackRecord.COL_NAME_PROCESSPACKIDES, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forProcessPackIDesEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameEqualTo(IMESLCUserProcessPackRecord.COL_NAME_PROCESSPACKIDES, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forProcessPackIDesNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameNotEqualTo(IMESLCUserProcessPackRecord.COL_NAME_PROCESSPACKIDES, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forProcessPackIDesStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameStartingWith(IMESLCUserProcessPackRecord.COL_NAME_PROCESSPACKIDES, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forProcessPackIdeContaining(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameContaining(IMESLCUserProcessPackRecord.COL_NAME_PROCESSPACKIDE, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forProcessPackIdeEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameEqualTo(IMESLCUserProcessPackRecord.COL_NAME_PROCESSPACKIDE, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forProcessPackIdeNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameNotEqualTo(IMESLCUserProcessPackRecord.COL_NAME_PROCESSPACKIDE, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forProcessPackIdeStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameStartingWith(IMESLCUserProcessPackRecord.COL_NAME_PROCESSPACKIDE, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forProcessPackStateEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameEqualTo(IMESLCUserProcessPackRecord.COL_NAME_PROCESSPACKSTATE, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forProcessPackStateGreaterThanOrEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameGreaterThanOrEqualTo(IMESLCUserProcessPackRecord.COL_NAME_PROCESSPACKSTATE, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forProcessPackStateLessThan(Long value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameLessThan(IMESLCUserProcessPackRecord.COL_NAME_PROCESSPACKSTATE, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forProcessPackStateNotEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameNotEqualTo(IMESLCUserProcessPackRecord.COL_NAME_PROCESSPACKSTATE, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forStateTimeEqualTo(Time value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameEqualTo(IMESLCUserProcessPackRecord.COL_NAME_STATETIME, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forStateTimeGreaterThanOrEqualTo(Time value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameGreaterThanOrEqualTo(IMESLCUserProcessPackRecord.COL_NAME_STATETIME, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forStateTimeLessThan(Time value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameLessThan(IMESLCUserProcessPackRecord.COL_NAME_STATETIME, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forStateTimeNotEqualTo(Time value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameNotEqualTo(IMESLCUserProcessPackRecord.COL_NAME_STATETIME, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forVersionContaining(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameContaining(IMESLCUserProcessPackRecord.COL_NAME_VERSION, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forVersionEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameEqualTo(IMESLCUserProcessPackRecord.COL_NAME_VERSION, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forVersionNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameNotEqualTo(IMESLCUserProcessPackRecord.COL_NAME_VERSION, value);
      }

      @Override
      public IMESLCUserProcessPackRecordFilter forVersionStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCUserProcessPackRecordFilter) forColumnNameStartingWith(IMESLCUserProcessPackRecord.COL_NAME_VERSION, value);
      }

}