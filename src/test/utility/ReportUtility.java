package test.utility;

import com.datasweep.compatibility.ui.grid.DsGrid;
import com.rockwell.mes.clientfw.pmc.impl.view.activities.GridDataDictActivity;
import com.rockwell.mes.commons.base.ifc.services.IFunctionsEx;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import test.model.MaterialTraceabilityModel;

import java.util.List;

public class ReportUtility {

    //平台function
    static IFunctionsEx functions = PCContext.getFunctions();

    public List<MaterialTraceabilityModel> getMaterialTraceability(String materialCode,String materialName, String subBatchNumber,String batchNumber ) {

        DsGrid dsGrid = functions.getGrid("MaterialTraceability");

        GridDataDictActivity gridDataDictActivity = new GridDataDictActivity();
        gridDataDictActivity.clear();
        return null;
    }
}

