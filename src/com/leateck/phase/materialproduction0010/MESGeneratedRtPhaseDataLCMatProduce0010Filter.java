package com.leateck.phase.materialproduction0010;

/**
 * This file is generated by the PhaseLibManager
 *
 * Please do not modify this file manually !!
 */
import java.util.List;

import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.Server;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectFilter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.services.s88.ifc.execution.IMESRtPhase;

import com.datasweep.compatibility.client.MeasuredValue;


/**
 * Generated class definition
 */
public abstract class MESGeneratedRtPhaseDataLCMatProduce0010Filter extends MESATObjectFilter  {

    /** Generated attribute definition */
    private static final long serialVersionUID = 1L;

    /** Generated attribute definition */
    private static final String ATDEFINITION_NAME = "LC_PhDatLCMatProduce0010";

    /**
     * Generated method definition
     *
     * @param server The Server object
     */
    public MESGeneratedRtPhaseDataLCMatProduce0010Filter(Server server) {
        super(server, ATDEFINITION_NAME);
    }

    /**
     * Generated method definition
     *
     */
    public MESGeneratedRtPhaseDataLCMatProduce0010Filter() {
        super(PCContext.getServerImpl(), ATDEFINITION_NAME);
    }

    /**
     * Generated method definition
     *
     * @return the list of the objects
     */
    @Override     
    public List<MESRtPhaseDataLCMatProduce0010> getFilteredObjects () {
        return MESATObject.getFilteredMESATObjectList(this, MESRtPhaseDataLCMatProduce0010.class);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forParentEqualTo(IMESRtPhase value) //
            throws DatasweepException {
        String columnName = MESRtPhaseDataLCMatProduce0010.COL_NAME_PARENT;
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo(columnName, Long.valueOf(value.getKey()));
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forParentNotEqualTo(IMESRtPhase value) //
            throws DatasweepException {
        String columnName = MESRtPhaseDataLCMatProduce0010.COL_NAME_PARENT;
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo(columnName, Long.valueOf(value.getKey()));
    }



    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forMaterialIDEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_materialID", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forMaterialIDNotEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_materialID", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forMaterialIDContaining(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameContaining("LC_materialID", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forMaterialIDStartingWith(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameStartingWith("LC_materialID", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forIsHeaderEqualTo(Boolean value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_isHeader", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forIsHeaderNotEqualTo(Boolean value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_isHeader", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forMaterialDescriptionEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_materialDescription", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forMaterialDescriptionNotEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_materialDescription", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forMaterialDescriptionContaining(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameContaining("LC_materialDescription", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forMaterialDescriptionStartingWith(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameStartingWith("LC_materialDescription", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forCommentToExecutionEqualTo(byte[] value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_commentToExecution", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forCommentToExecutionNotEqualTo(byte[] value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_commentToExecution", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forBatchIDEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_batchID", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forBatchIDNotEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_batchID", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forBatchIDContaining(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameContaining("LC_batchID", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forBatchIDStartingWith(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameStartingWith("LC_batchID", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forSublotIDEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_sublotID", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forSublotIDNotEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_sublotID", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forSublotIDContaining(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameContaining("LC_sublotID", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forSublotIDStartingWith(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameStartingWith("LC_sublotID", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forPlannedQtyEqualTo(MeasuredValue value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_plannedQty", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forPlannedQtyNotEqualTo(MeasuredValue value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_plannedQty", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forProducedQtyEqualTo(MeasuredValue value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_producedQty", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forProducedQtyNotEqualTo(MeasuredValue value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_producedQty", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forSublotQtyEqualTo(MeasuredValue value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_sublotQty", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forSublotQtyNotEqualTo(MeasuredValue value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_sublotQty", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forDefinedPackingLevelsEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_definedPackingLevels", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forDefinedPackingLevelsNotEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_definedPackingLevels", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forDefinedPackingLevelsContaining(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameContaining("LC_definedPackingLevels", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forDefinedPackingLevelsStartingWith(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameStartingWith("LC_definedPackingLevels", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forPackingLevelNamesEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_packingLevelNames", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forPackingLevelNamesNotEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_packingLevelNames", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forPackingLevelNamesContaining(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameContaining("LC_packingLevelNames", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forPackingLevelNamesStartingWith(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameStartingWith("LC_packingLevelNames", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forSublotLevelQtyEqualTo(MeasuredValue value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_sublotLevelQty", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forSublotLevelQtyNotEqualTo(MeasuredValue value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_sublotLevelQty", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forLogisticUnitLevelQtyEqualTo(MeasuredValue value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_logisticUnitLevelQty", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forLogisticUnitLevelQtyNotEqualTo(MeasuredValue value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_logisticUnitLevelQty", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forResultEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_Result", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forResultNotEqualTo(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_Result", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forResultContaining(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameContaining("LC_Result", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forResultStartingWith(String value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameStartingWith("LC_Result", value);
    }


    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forIsSummaryDataEqualTo(Boolean value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameEqualTo("LC_isSummaryData", value);
    }

    /**
     * Generated method definition
     *
     * @param value the value to be filtered for
     * @return the filter object
     * @throws DatasweepException the PC exception
     */
    public MESRtPhaseDataLCMatProduce0010Filter forIsSummaryDataNotEqualTo(Boolean value) //
            throws DatasweepException {
        return (MESRtPhaseDataLCMatProduce0010Filter) forColumnNameNotEqualTo("LC_isSummaryData", value);
    }

}
