package com.leateck.library;

import com.datasweep.compatibility.client.ATRow;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.services.s88.ifc.HierarchyLevel;
import com.rockwell.mes.services.s88.ifc.IRecipeEntity;
import com.rockwell.mes.services.s88.ifc.library.BuildingBlockUsageType;
import com.rockwell.mes.services.s88.ifc.library.IBuildingBlockTemplate;
import com.rockwell.mes.services.s88.ifc.library.IMESUnitProcedureLib;
import com.rockwell.mes.services.s88.ifc.recipe.IMESUnitProcedure;
import com.rockwell.mes.services.s88.impl.library.LibraryEntityHelper;
import com.rockwell.mes.services.s88.impl.library.MESUnitProcedureLib;

public class MESProcessPackUnitProcedureLib extends MESUnitProcedureLib implements IMESUnitProcedureLib {
    public MESProcessPackUnitProcedureLib(long l) {
        super(l);
    }

    public MESProcessPackUnitProcedureLib(MESProcessPackUnitProcedureLib mESUnitProcedureLib) {
        super(mESUnitProcedureLib);
        this.setInternal(Boolean.valueOf(false));
        LibraryEntityHelper.init((IBuildingBlockTemplate) this);
    }

    public MESProcessPackUnitProcedureLib(ATRow aTRow) {
        super(aTRow);
    }

    public MESProcessPackUnitProcedureLib() {
        this.setBbType(BB_TYPE_CUSTOM_VALUE);
        this.setIsWeighAndDispense(Boolean.FALSE);
        this.setInternal(Boolean.valueOf(false));
        super.setAllowedUsageTypesSet(Long.valueOf(BuildingBlockUsageType.getDefaultBitSet()));
        LibraryEntityHelper.init((IBuildingBlockTemplate) this);
    }

    public IBuildingBlockTemplate copy() {
        return new MESUnitProcedureLib(this);
    }

    public String getName() {
        return this.getUnitProcedureLibName();
    }

    public void setName(String string) {
        this.setUnitProcedureLibName(string);
    }

    public void setUnitProcedureLibName(String string) {
        String string2 = this.getName();
        super.setUnitProcedureLibName(string);
        this.pcs.firePropertyChange("name", string2, string);
    }

    public HierarchyLevel getHierarchyLevel() {
        return HierarchyLevel.UNIT_PROCEDURE;
    }

    public void setValid(boolean bl) {
        boolean bl2 = this.isValid();
        this.setIsRecipeStructureValid(Boolean.valueOf(bl));
        if (bl != bl2) {
            this.pcs.firePropertyChange("valid", bl2, bl);
        }
    }

    public boolean isValid() {
        return Boolean.TRUE.equals(this.getIsRecipeStructureValid());
    }

    public IMESUnitProcedure getCustomizedBuildingBlock() {
        return this.getWhiteBoxBB();
    }

    public void setCustomizedBuildingBlock(IRecipeEntity iRecipeEntity) {
        if (iRecipeEntity != null && !(iRecipeEntity instanceof IMESUnitProcedure)) {
            throw new MESRuntimeException("Unit procedure library expects a unit procedure as customized building block!");
        }
        this.setWhiteBoxBB((IMESUnitProcedure) iRecipeEntity);
    }
}