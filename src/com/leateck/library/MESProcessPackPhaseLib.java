package com.leateck.library;

import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.DatasweepException;
import com.rockwell.mes.commons.base.ifc.exceptions.MESException;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.commons.base.ifc.objectlock.MESObjectManagerSupport;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.commons.base.ifc.utility.LibraryHelper;
import com.rockwell.mes.services.s88.ifc.EnumPhaseUsageContext;
import com.rockwell.mes.services.s88.ifc.HierarchyLevel;
import com.rockwell.mes.services.s88.ifc.IRecipeEntity;
import com.rockwell.mes.services.s88.ifc.IS88LibraryService;
import com.rockwell.mes.services.s88.ifc.library.IBuildingBlockOutputDescriptor;
import com.rockwell.mes.services.s88.ifc.library.IBuildingBlockTemplate;
import com.rockwell.mes.services.s88.ifc.library.IMESParameterClass;
import com.rockwell.mes.services.s88.ifc.library.IMESPhaseLib;
import com.rockwell.mes.services.s88.ifc.processdata.MESRtPhaseOutput;
import com.rockwell.mes.services.s88.ifc.recipe.IMESPhase;
import com.rockwell.mes.services.s88.impl.library.LibraryEntityHelper;
import com.rockwell.mes.services.s88.impl.library.MESPhaseLib;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MESProcessPackPhaseLib extends MESPhaseLib implements IMESPhaseLib {
    private static final Log LOGGER = LogFactory.getLog(MESProcessPackPhaseLib.class);

    public static final String PROP_NAME_PARAMETERCLASSES = "parameterClasses";

    private List<IBuildingBlockOutputDescriptor> outputDescriptors;

    public MESProcessPackPhaseLib(long l) {
		super(l);
	}

    public MESProcessPackPhaseLib(MESProcessPackPhaseLib mESPhaseLib) {
        super();
		this.setInternal(Boolean.valueOf(false));
		LibraryEntityHelper.init((IBuildingBlockTemplate) this);
	}

    public MESProcessPackPhaseLib(ATRow aTRow) {
		super(aTRow);
	}

    public MESProcessPackPhaseLib() {
		this.setBbType(BB_TYPE_CUSTOM_VALUE);
		this.setMaterialInputMinimum(Long.valueOf(0L));
		this.setMaterialInputMaximum(Long.valueOf(0L));
		this.setMaterialOutputMinimum(Long.valueOf(0L));
		this.setMaterialOutputMaximum(Long.valueOf(0L));
		this.setNavigatorVisible(Boolean.TRUE);
		this.setInternal(Boolean.valueOf(false));
		this.setUsageContext(EnumPhaseUsageContext.USAGE_IN_ALL_STRUCTURES_VALUE);
		LibraryEntityHelper.init((IBuildingBlockTemplate) this);
	}

    public IBuildingBlockTemplate copy() {
        return new MESProcessPackPhaseLib(this);
    }

    public String getGUID() {
        return this.getATRow().getName();
    }

    public String getName() {
        return this.getPhaseLibName();
    }

    public void setName(String string) {
        this.setPhaseLibName(string);
    }

    public void setPhaseLibName(String string) {
        String string2 = this.getPhaseLibName();
        super.setPhaseLibName(string);
        this.pcs.firePropertyChange("name", string2, string);
    }

    public HierarchyLevel getHierarchyLevel() {
        return HierarchyLevel.PHASE;
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

    public Boolean getIsWhiteBoxBB() {
        return Boolean.FALSE;
    }

    public void setIsWhiteBoxBB(Boolean bl) {
        if (!Boolean.FALSE.equals(bl)) {
            throw new IllegalArgumentException("PhaseLib does only support Closed (black-box) building blocks!");
        }
    }

    public IMESPhase getCustomizedBuildingBlock() {
        return this.getCustomBB();
    }

    public void setCustomizedBuildingBlock(IRecipeEntity iRecipeEntity) {
        if (iRecipeEntity != null && !(iRecipeEntity instanceof IMESPhase)) {
            throw new MESRuntimeException("Phase library expects a phase as customized building block!");
        }
        this.setCustomBB((IMESPhase) iRecipeEntity);
    }

    public List<IMESParameterClass> getParameterClasses() {
        IS88LibraryService iS88LibraryService = (IS88LibraryService) ServiceFactory.getService(IS88LibraryService.class);
        return iS88LibraryService.getParameterClassesOfPhaseLibEntry((IMESPhaseLib) this);
    }

    public void setParameterClasses(List<IMESParameterClass> list) {
        List<IMESParameterClass> list2 = this.getParameterClasses();
        IS88LibraryService iS88LibraryService = (IS88LibraryService) ServiceFactory.getService(IS88LibraryService.class);
        try {
            iS88LibraryService.deassignAllParameterClassesFromPhaseLibEntry((IMESPhaseLib) this);
            long l = 0L;
            for (IMESParameterClass iMESParameterClass : list) {
                String string = iMESParameterClass.getName() + "_" + l;
                iS88LibraryService.assignParameterClassToPhaseLibEntry(iMESParameterClass, (IMESPhaseLib) this, string, l);
                ++l;
            }
        } catch (DatasweepException | MESException mESException) {
            throw new MESRuntimeException("Error when setting parameter classes to phase lib", (Throwable) mESException);
        }
        this.pcs.firePropertyChange(PROP_NAME_PARAMETERCLASSES, list2, list);
    }

    public boolean hasExceptions() {
        return Boolean.TRUE.equals(this.getHasExceptions());
    }

    public String getPhaseLibNameDerivedFrom() {
        String string = this.getDerivedFromName();
        if (StringUtils.isEmpty((CharSequence) string)) {
            string = this.getPhaseLibName();
        }
        return string;
    }

    public Boolean getIsServerRunPhase() {
        return Boolean.TRUE.equals(super.getIsServerRunPhase());
    }

    public Boolean getIsPauseAware() {
        return Boolean.TRUE.equals(super.getIsPauseAware());
    }

    public List<IBuildingBlockOutputDescriptor> getOutputDescriptors() {
        if (this.outputDescriptors == null) {
            this.outputDescriptors = MESRtPhaseOutput.getStaticOutputDescriptorsForClass((String) this.getRtPhaseOuputClassName());
        }
        return this.outputDescriptors;
    }

    public boolean haveDerivedEntitiesDynamicOutputs() {
        return MESRtPhaseOutput.hasOutputClassDynamicOutputDescriptors((String) this.getRtPhaseOuputClassName());
    }

    public boolean supportsDynamicProcessParameters() {
        Long l = this.getDynProcParamMaximum();
        return l != null && l != 0L;
    }

    public Long getUsageContext() {
        Long l = super.getUsageContext();
        if (l == null) {
            l = EnumPhaseUsageContext.USAGE_IN_MASTER_RECIPE_STRUCTURES_VALUE;
        }
        return l;
    }

    public String getInternalMaintenanceVersion() {
        if (!BB_TYPE_STANDARD_VALUE.equals(this.getBbType())) {
            return null;
        }
        String string = this.getExecutorClassName();
        if (StringUtils.isEmpty((CharSequence) string)) {
            return null;
        }
        try {
            String string2 = LibraryHelper.getJarName((String) string);
            if (string2 == null) {
                LOGGER.warn((Object) ("Could not determine the JAR name for executor class '" + string + "'"));
            }
            return string2;
        } catch (MESRuntimeException mESRuntimeException) {
            LOGGER.error((Object) ("Could not determine the JAR name for for executor class '" + string + "'"), (Throwable) mESRuntimeException);
            return null;
        }
    }

    public String getSubreportName() {
        String string = super.getSubreportName();
        return MESObjectManagerSupport.concatName((String) string);
    }

    public String toString() {
        return this.getPhaseLibName();
    }
}
