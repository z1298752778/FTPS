package com.leateck.library;

import com.datasweep.compatibility.client.ATRow;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.services.s88.ifc.HierarchyLevel;
import com.rockwell.mes.services.s88.ifc.IRecipeEntity;
import com.rockwell.mes.services.s88.ifc.library.BuildingBlockUsageType;
import com.rockwell.mes.services.s88.ifc.library.IBuildingBlockTemplate;
import com.rockwell.mes.services.s88.ifc.library.IMESOperationLib;
import com.rockwell.mes.services.s88.ifc.recipe.IMESOperation;
import com.rockwell.mes.services.s88.impl.library.LibraryEntityHelper;
import com.rockwell.mes.services.s88.impl.library.MESOperationLib;

public class MESProcessPackOperationLib extends MESOperationLib implements IMESOperationLib {
    public MESProcessPackOperationLib(long l) {
        super(l);
    }

    public MESProcessPackOperationLib(MESProcessPackOperationLib mESOperationLib) {
        super(mESOperationLib);
        this.setInternal(Boolean.valueOf(false));
        LibraryEntityHelper.init((IBuildingBlockTemplate) this);
    }

    public MESProcessPackOperationLib(ATRow aTRow) {
        super(aTRow);
    }

    public MESProcessPackOperationLib() {
        this.setBbType(BB_TYPE_CUSTOM_VALUE);
        this.setIsWeighAndDispense(Boolean.FALSE);
        this.setInternal(Boolean.valueOf(false));
        super.setAllowedUsageTypesSet(Long.valueOf(BuildingBlockUsageType.getDefaultBitSet()));
        LibraryEntityHelper.init((IBuildingBlockTemplate) this);
    }

    public IBuildingBlockTemplate copy() {
        return new MESProcessPackOperationLib(this);
    }

    public String getName() {
        return this.getOperationLibName();
    }

    public void setName(String string) {
        this.setOperationLibName(string);
    }

    public void setOperationLibName(String string) {
        String string2 = this.getName();
        super.setOperationLibName(string);
        this.pcs.firePropertyChange("name", string2, string);
    }

    public HierarchyLevel getHierarchyLevel() {
        return HierarchyLevel.OPERATION;
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

    public IMESOperation getCustomizedBuildingBlock() {
        return this.getWhiteBoxBB();
    }

    public void setCustomizedBuildingBlock(IRecipeEntity iRecipeEntity) {
        if (iRecipeEntity != null && !(iRecipeEntity instanceof IMESOperation)) {
            throw new MESRuntimeException("Operation library expects an operation as customized building block!");
        }
        this.setWhiteBoxBB((IMESOperation) iRecipeEntity);
    }
}