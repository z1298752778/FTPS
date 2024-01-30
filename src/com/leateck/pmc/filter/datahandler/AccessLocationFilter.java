package com.leateck.pmc.filter.datahandler;

import com.datasweep.compatibility.client.*;
import com.rockwell.mes.clientfw.pmc.ifc.datahandler.FilterDataHandler;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IMESUFUserFilter;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IMESUserFilterService;
import com.rockwell.mes.commons.base.ifc.services.PCContext;

import java.util.ArrayList;
import java.util.List;

public class AccessLocationFilter extends FilterDataHandler {
    public Object executeHandler() {
        IMESUserFilterService var1 = this.getFilterService();
        IMESUFUserFilter var2 = this.getUserFilter();
        List var4 = null;
        Filter var3;
        try {
            var3 = var1.createPCFilter(var2);
            var4 = var3.exec();
        } catch (DatasweepException e) {
            e.printStackTrace();
        }
        if (((List<?>) var4).size() != 0 && "com.datasweep.compatibility.client.Location".equals(((List<?>) var4).get(0).getClass().getName())){
            //返回所有的位置
            List<Location> locationList = (List<Location>) var4;
            //需要展示的位置集合
            ArrayList<Location> locations = new ArrayList();
            // 获取当前用户
            User user = PCContext.getFunctions().getCurrentUser();

            for (Location accessRight : locationList) {
                Location storageArea = accessRight.getParentLocation();
                Location warehouse = storageArea.getParentLocation();
                try {
                    if(accessRight.getUDA("location_accPriv") == null || user.hasPrivilege((AccessPrivilege) accessRight.getUDA("location_accPriv"))){
                        if(storageArea.getUDA("location_accPriv") == null || user.hasPrivilege((AccessPrivilege) storageArea.getUDA("location_accPriv"))){
                            if(warehouse.getUDA("location_accPriv") == null || user.hasPrivilege((AccessPrivilege) warehouse.getUDA("location_accPriv"))){
                                locations.add(accessRight);
                            }
                        }
                    }
                } catch (DatasweepException e) {
                    e.printStackTrace();
                }
            }
            return locations;
        }else {
            return var4;
        }
    }

}