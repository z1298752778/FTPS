package com.leateck.pmc.filter.datahandler;

import com.datasweep.compatibility.client.*;
import com.rockwell.mes.clientfw.pmc.ifc.datahandler.FilterDataHandler;
import com.rockwell.mes.clientfw.pmc.ifc.usecaseconfig.UseCaseConfigUtils;
import com.rockwell.mes.clientfw.pmc.ifc.usecaseconfig.gen.DataHandler;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.services.inventory.ifc.IWarehouseService;

import java.util.ArrayList;
import java.util.List;

public class RetrieveLocationWithFilter extends FilterDataHandler {
    private int getConfiguredLocationLevel() {
        DataHandler var1 = this.getDataNode().getDataHandlerInstance().getConfiguration();
        String var2 = UseCaseConfigUtils.getParameter(var1, 0);
        byte var3;
        if (var2.equals("StorageLocation")) {
            var3 = 0;
        } else if (var2.equals("StorageArea")) {
            var3 = 1;
        } else {
            if (!var2.equals("Warehouse")) {
                throw new IllegalArgumentException("Illegal argument '" + var2 + " 'for location class name.");
            }

            var3 = 2;
        }

        return var3;
    }

    protected List executeFilter(boolean var1, String[] var2) {
        this.resetNrOfRecords();
        int var3 = this.getConfiguredLocationLevel();
        IWarehouseService var4 = (IWarehouseService) ServiceFactory.getService(IWarehouseService.class);

        try {
            List var5;
            long var6;
            LocationFilter var8;
            if (var1) {
                var8 = var4.createFilter(var3);
                var5 = null;
                var6 = -1L;
            } else {
                var8 = var4.createFilter((Integer) null);
                var5 = var4.getFilteredLocations(var8, var3);
                var6 = (long) var5.size();
            }

            long var9 = var8.getCount();
            this.setNrOfRecords(var9, var6);

            return var5;
        } catch (DatasweepException var11) {
            this.handleException(var11);
            return null;
        }
    }

    public Object executeHandler() {
        //返回所有的仓库
        List list = (List) this.executeFilter();
        List<Location> locationList = (List<Location>) list;
        //需要展示的仓库集合
        ArrayList<Location> var1 = new ArrayList();
        // 获取当前用户
        User user = PCContext.getFunctions().getCurrentUser();

        for (Location accessRight : locationList) {
            try {
                if(accessRight.getUDA("location_accPriv") == null || user.hasPrivilege((AccessPrivilege) accessRight.getUDA("location_accPriv")))
                    var1.add(accessRight);
            } catch (DatasweepException e) {
                e.printStackTrace();
            }
        }
        int var3 = var1.size();
        this.setNrOfRecords((long) var3, (long) var3);
        return var1;
    }

}
