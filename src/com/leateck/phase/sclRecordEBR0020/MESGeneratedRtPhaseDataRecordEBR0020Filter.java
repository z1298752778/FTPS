package com.leateck.phase.sclRecordEBR0020;

import com.datasweep.compatibility.client.ATRowFilter;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.MeasuredValue;
import com.datasweep.compatibility.client.Server;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectFilter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.services.s88.ifc.execution.IMESRtPhase;
import java.util.List;

public abstract class MESGeneratedRtPhaseDataRecordEBR0020Filter extends MESATObjectFilter {
    private static final long serialVersionUID = 1L;

    private static final String ATDEFINITION_NAME = "SC_PhDatRecordEBR0020";

    public MESGeneratedRtPhaseDataRecordEBR0020Filter(Server server) {
        super(server, "SC_PhDatRecordEBR0020");
    }

    public MESGeneratedRtPhaseDataRecordEBR0020Filter() {
        super((Server)PCContext.getServerImpl(), "SC_PhDatRecordEBR0020");
    }

    public List<MESRtPhaseDataRecordEBR0020> getFilteredObjects() {
        return MESATObject.getFilteredMESATObjectList((ATRowFilter)this, MESRtPhaseDataRecordEBR0020.class);
    }

    public MESRtPhaseDataRecordEBR0020Filter forParentEqualTo(IMESRtPhase value) throws DatasweepException {
        String columnName = "X_parent";
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo(columnName, Long.valueOf(value.getKey()));
    }

    public MESRtPhaseDataRecordEBR0020Filter forParentNotEqualTo(IMESRtPhase value) throws DatasweepException {
        String columnName = "X_parent";
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo(columnName, Long.valueOf(value.getKey()));
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey1EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey1NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey1Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey1StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue1EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue1NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey2EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey2NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey2Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey2StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue2EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue2NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey3EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey3NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey3Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey3StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue3EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue3NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey4EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey4NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey4Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey4StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue4EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue4NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey5EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey5NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey5Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey5StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue5EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue5NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey6EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey6NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey6Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey6StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue6EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue6NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey7EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey7NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey7Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey7StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue7EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue7NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey8EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey8NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey8Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey8StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue8EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue8NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey9EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey9NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey9Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey9StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue9EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue9NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey10EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey10NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey10Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey10StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue10EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue10NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey11EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey11NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey11Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey11StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue11EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue11NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey12EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey12NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey12Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey12StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue12EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue12NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey13EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey13NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey13Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey13StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue13EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue13NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey14EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey14NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey14Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey14StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue14EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue14NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey15EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey15NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey15Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey15StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue15EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue15NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey16EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey16NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey16Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey16StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue16EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue16NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey17EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey17NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey17Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey17StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue17EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue17NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey18EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey18NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey18Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey18StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue18EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue18NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey19EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey19NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey19Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey19StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue19EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue19NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey20EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredKey20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey20NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredKey20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey20Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_measuredKey20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredKey20StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_measuredKey20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue20EqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_measuredValue20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forMeasuredValue20NotEqualTo(MeasuredValue value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_measuredValue20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey1EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey1NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey1Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey1StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString1EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString1NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString1Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString1StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string1", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey2EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey2NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey2Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey2StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString2EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString2NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString2Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString2StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string2", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey3EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey3NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey3Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey3StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString3EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString3NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString3Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString3StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string3", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey4EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey4NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey4Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey4StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString4EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString4NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString4Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString4StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string4", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey5EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey5NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey5Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey5StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString5EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString5NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString5Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString5StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string5", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey6EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey6NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey6Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey6StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString6EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString6NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString6Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString6StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string6", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey7EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey7NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey7Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey7StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString7EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString7NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString7Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString7StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string7", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey8EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey8NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey8Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey8StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString8EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString8NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString8Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString8StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string8", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey9EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey9NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey9Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey9StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString9EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString9NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString9Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString9StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string9", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey10EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey10NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey10Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey10StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString10EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString10NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString10Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString10StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string10", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey11EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey11NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey11Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey11StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString11EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString11NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString11Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString11StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string11", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey12EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey12NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey12Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey12StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString12EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString12NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString12Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString12StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string12", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey13EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey13NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey13Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey13StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString13EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString13NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString13Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString13StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string13", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey14EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey14NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey14Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey14StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString14EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString14NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString14Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString14StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string14", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey15EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey15NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey15Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey15StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString15EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString15NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString15Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString15StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string15", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey16EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey16NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey16Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey16StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString16EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString16NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString16Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString16StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string16", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey17EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey17NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey17Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey17StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString17EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString17NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString17Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString17StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string17", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey18EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey18NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey18Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey18StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString18EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString18NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString18Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString18StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string18", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey19EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey19NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey19Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey19StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString19EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString19NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString19Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString19StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string19", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey20EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_stringKey20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey20NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_stringKey20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey20Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_stringKey20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forStringKey20StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_stringKey20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString20EqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_string20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString20NotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_string20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString20Containing(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_string20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forString20StartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_string20", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forOrderNumberEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameEqualTo("SC_orderNumber", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forOrderNumberNotEqualTo(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameNotEqualTo("SC_orderNumber", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forOrderNumberContaining(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameContaining("SC_orderNumber", value);
    }

    public MESRtPhaseDataRecordEBR0020Filter forOrderNumberStartingWith(String value) throws DatasweepException {
        return (MESRtPhaseDataRecordEBR0020Filter)forColumnNameStartingWith("SC_orderNumber", value);
    }
}
