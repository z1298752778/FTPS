package com.rockwell.mes.services.s88equipment.ifc;

import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;
import com.rockwell.mes.commons.base.ifc.validation.IValidatableBean;
import com.rockwell.mes.commons.versioning.ifc.fsm.IStatusControlled;
import com.rockwell.mes.commons.versioning.ifc.fsm.StatusControlledObjectManager;
import com.rockwell.mes.services.commons.ifc.statushistory.IMESStatusHistoryOwner;
import com.rockwell.mes.services.s88equipment.ifc.EquipmentPropertyUniquePurposeValidationErrorResult;
import com.rockwell.mes.services.s88equipment.ifc.IEquipmentPropertyHolder;
import com.rockwell.mes.services.s88equipment.ifc.IEquipmentValidationRequirement;
import com.rockwell.mes.services.s88equipment.ifc.IMESEqmChangeHistoryOwner;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.EquipmentStatusGraphPropertiesValidationErrorResult;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraph;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraphAssignment;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.StatusGraphUniquePurposeValidationErrorResult;
import java.util.List;

public interface IEquipmentClassEntityCommons extends IMESATObject, IValidatableBean, IEquipmentValidationRequirement, IEquipmentPropertyHolder,
        IMESEqmChangeHistoryOwner, IMESStatusHistoryOwner, IStatusControlled {
    public String getDescription();

    public void setDescription(String var1);

    //
    public String getProcessPackName();

    public void setProcessPackName(String var1);


    public Boolean getDisposable();

    public void setDisposable(Boolean var1);

    public IMESChoiceElement getEquipmentLevel();

    public String getEquipmentLevelAsMeaning();

    public Long getEquipmentLevelAsValue();

    public void setEquipmentLevel(IMESChoiceElement var1);

    public void setEquipmentLevelAsMeaning(String var1);

    public void setEquipmentLevelAsValue(Long var1);

    public String getIdentifier();

    public void setIdentifier(String var1);

    public String getInventoryNumber();

    public void setInventoryNumber(String var1);

    public Boolean getLogbookEnabled();

    public void setLogbookEnabled(Boolean var1);

    public String getManufacturer();

    public void setManufacturer(String var1);

    public Time getManufacturingDate();

    public void setManufacturingDate(Time var1);

    public String getModel();

    public void setModel(String var1);

    public String getSerialNumber();

    public void setSerialNumber(String var1);

    public String getShortDescription();

    public void setShortDescription(String var1);

    public StatusControlledObjectManager getStatusManager();

    public List<IMESS88StatusGraphAssignment> getStatusGraphAssignments();

    public IMESS88StatusGraphAssignment getStatusGraphAssignment(String var1);

    public IMESS88StatusGraphAssignment getStatusGraphAssignment(IMESChoiceElement var1);

    public boolean hasStatusGraphAssignment(IMESChoiceElement var1);

    public boolean hasStatusGraphAssignment(String var1);

    public IMESS88StatusGraphAssignment addStatusGraph(IMESS88StatusGraph var1);

    public void removeStatusGraphAssignment(IMESS88StatusGraphAssignment var1);

    public EquipmentStatusGraphPropertiesValidationErrorResult validateRequiredStatusGraphProperties(IMESS88StatusGraph var1);

    public StatusGraphUniquePurposeValidationErrorResult validateUniquePurposeOfAssignedStatusGraphs();

    public EquipmentPropertyUniquePurposeValidationErrorResult validateUniquePurposeOfAssignedProperties();

    public boolean isRetired();
}