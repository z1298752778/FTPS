package com.leateck.library;

import com.datasweep.compatibility.client.ATRow;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.services.s88.ifc.HierarchyLevel;
import com.rockwell.mes.services.s88.ifc.IRecipeEntity;
import com.rockwell.mes.services.s88.ifc.library.IBuildingBlockTemplate;
import com.rockwell.mes.services.s88.ifc.library.IMESProcedureLib;
import com.rockwell.mes.services.s88.ifc.recipe.IMESProcedure;
import com.rockwell.mes.services.s88.impl.library.LibraryEntityHelper;
import com.rockwell.mes.services.s88.impl.library.MESProcedureLib;

public class MESProcessPackProcedureLib extends MESProcedureLib implements IMESProcedureLib {
    public MESProcessPackProcedureLib(long l) {
        super(l);
    }

    public MESProcessPackProcedureLib(MESProcessPackProcedureLib mESProcedureLib) {
        super(mESProcedureLib);
        this.setInternal(Boolean.valueOf(false));
        LibraryEntityHelper.init((IBuildingBlockTemplate) this);
    }

    public MESProcessPackProcedureLib(ATRow aTRow) {
        super(aTRow);
    }

    public MESProcessPackProcedureLib() {
        this.setBbType(BB_TYPE_CUSTOM_VALUE);
        this.setInternal(Boolean.valueOf(false));
        LibraryEntityHelper.init((IBuildingBlockTemplate) this);
    }

    public IBuildingBlockTemplate copy() {
        return new MESProcedureLib(this);
    }

    public String getName() {
        return this.getProcedureLibName();
    }

    public void setName(String string) {
        this.setProcedureLibName(string);
    }

    public void setProcedureLibName(String string) {
        String string2 = this.getName();
        super.setProcedureLibName(string);
        this.pcs.firePropertyChange("name", string2, string);
    }

    public HierarchyLevel getHierarchyLevel() {
        return HierarchyLevel.PROCEDURE;
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

    public IMESProcedure getCustomizedBuildingBlock() {
        return this.getWhiteBoxBB();
    }

    public void setCustomizedBuildingBlock(IRecipeEntity iRecipeEntity) {
        if (iRecipeEntity != null && !(iRecipeEntity instanceof IMESProcedure)) {
            throw new MESRuntimeException("Procedure library expects a procedure as customized building block!");
        }
        this.setWhiteBoxBB((IMESProcedure) iRecipeEntity);
    }
}