package com.leateck.model.lossQuantityAccountConsumption;


/**
 * This file was generated by ATDefAccessClassGenerator and FMPP 2.3.15
 *
 * Please do not modify this file manually !!
 */
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.MeasuredValue;
import com.datasweep.compatibility.client.Server;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectFilter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import java.util.List;

/**
 * Generated filter class for application table LC_LossQtyAccountCon.
 */
public abstract class MESGeneratedLCLossQtyAccountConFilter extends MESATObjectFilter implements IMESGeneratedLCLossQtyAccountConFilter {

    /** Generated attribute definition */
    private static final long serialVersionUID = 1L;

    /** Generated attribute definition */
    protected static final String ATDEFINITION_NAME = "LC_LossQtyAccountCon";

    /**
     * Generated constructor
     * 
     * @param server The Server object
     */
    public MESGeneratedLCLossQtyAccountConFilter(Server server) {
        super(server, ATDEFINITION_NAME);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedLCLossQtyAccountConFilter() {
        this(PCContext.getServerImpl());
    }

    @Override
    public List<IMESLCLossQtyAccountCon> getFilteredObjects() {
        return MESATObject.getFilteredMESATObjectList(this, IMESLCLossQtyAccountCon.class);
    }

      @Override
      public IMESLCLossQtyAccountConFilter forLossQtyEqualTo(MeasuredValue value) //
              throws DatasweepException {
          return (IMESLCLossQtyAccountConFilter) forColumnNameEqualTo(IMESLCLossQtyAccountCon.COL_NAME_LOSSQTY, value);
      }

      @Override
      public IMESLCLossQtyAccountConFilter forLossQtyNotEqualTo(MeasuredValue value) //
              throws DatasweepException {
          return (IMESLCLossQtyAccountConFilter) forColumnNameNotEqualTo(IMESLCLossQtyAccountCon.COL_NAME_LOSSQTY, value);
      }

      @Override
      public IMESLCLossQtyAccountConFilter forPhaseKeyEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCLossQtyAccountConFilter) forColumnNameEqualTo(IMESLCLossQtyAccountCon.COL_NAME_PHASEKEY, value);
      }

      @Override
      public IMESLCLossQtyAccountConFilter forPhaseKeyGreaterThanOrEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCLossQtyAccountConFilter) forColumnNameGreaterThanOrEqualTo(IMESLCLossQtyAccountCon.COL_NAME_PHASEKEY, value);
      }

      @Override
      public IMESLCLossQtyAccountConFilter forPhaseKeyLessThan(Long value) //
              throws DatasweepException {
          return (IMESLCLossQtyAccountConFilter) forColumnNameLessThan(IMESLCLossQtyAccountCon.COL_NAME_PHASEKEY, value);
      }

      @Override
      public IMESLCLossQtyAccountConFilter forPhaseKeyNotEqualTo(Long value) //
              throws DatasweepException {
          return (IMESLCLossQtyAccountConFilter) forColumnNameNotEqualTo(IMESLCLossQtyAccountCon.COL_NAME_PHASEKEY, value);
      }

      @Override
      public IMESLCLossQtyAccountConFilter forSublotNumberContaining(String value) //
              throws DatasweepException {
          return (IMESLCLossQtyAccountConFilter) forColumnNameContaining(IMESLCLossQtyAccountCon.COL_NAME_SUBLOTNUMBER, value);
      }

      @Override
      public IMESLCLossQtyAccountConFilter forSublotNumberEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCLossQtyAccountConFilter) forColumnNameEqualTo(IMESLCLossQtyAccountCon.COL_NAME_SUBLOTNUMBER, value);
      }

      @Override
      public IMESLCLossQtyAccountConFilter forSublotNumberNotEqualTo(String value) //
              throws DatasweepException {
          return (IMESLCLossQtyAccountConFilter) forColumnNameNotEqualTo(IMESLCLossQtyAccountCon.COL_NAME_SUBLOTNUMBER, value);
      }

      @Override
      public IMESLCLossQtyAccountConFilter forSublotNumberStartingWith(String value) //
              throws DatasweepException {
          return (IMESLCLossQtyAccountConFilter) forColumnNameStartingWith(IMESLCLossQtyAccountCon.COL_NAME_SUBLOTNUMBER, value);
      }

}