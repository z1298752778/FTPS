package com.leateck.mes.clientfw.pmc.ifc.datahandler;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.Batch;
import com.datasweep.compatibility.client.BatchFilter;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.DsList;
import com.datasweep.compatibility.client.NamedFilter;
import com.datasweep.compatibility.client.Filter;
import com.datasweep.compatibility.client.Part;
import com.datasweep.compatibility.client.PartFilter;
import com.datasweep.compatibility.client.Sublot;
import com.datasweep.compatibility.client.SublotFilter;
import com.rockwell.mes.clientfw.pmc.ifc.datahandler.FilterDataHandler;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IMESUFUserFilter;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IMESUserFilterService;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.datasweep.compatibility.client.User;

import java.util.ArrayList;
import java.util.List;

public class SublotAccessFilterDataHandler extends FilterDataHandler {
	protected static final String ACCESS_FILTER_LIST = "LC_AccessFilterList";
    public Object executeHandler() {
    	IMESUserFilterService var1 = this.getFilterService();
        IMESUFUserFilter var2 = this.getUserFilter();
        List<Part> var4 = null;
        List<Batch> var5 = null;
        List<Sublot> var6 = null;
        try {
            SublotFilter var3 = (SublotFilter)var1.createPCFilter(var2);
            User user = PCContext.getFunctions().getCurrentUser();
            
            // 获取物料管理权限
            DsList partAccessFilter = PCContext.getFunctions().getList(ACCESS_FILTER_LIST);
            NamedFilter filter = PCContext.getFunctions().getFilterByName(partAccessFilter.getItem(0).toString());
            Filter accessFilter = filter.getFilter();
            List<AccessPrivilege> accessRights = accessFilter.exec();
            
            // 获取当前用户拥有的物料管理权限
            List<AccessPrivilege> accessMaterial = new ArrayList<AccessPrivilege>();
            for (AccessPrivilege accessRight : accessRights) {
            	
                if (user.hasPrivilege(accessRight)) {
             	   accessMaterial.add(accessRight);
                }
            }

            // 添加过滤条件
            PartFilter partFilter = PCContext.getFunctions().createPartFilter();
            partFilter.forUdaEqualTo("LC_material_accPriv", "");
            for (AccessPrivilege accessRight : accessMaterial) {
            	partFilter.forUdaEqualTo("LC_material_accPriv", accessRight);
            }
            var4 = partFilter.exec();
          //获取批次
            BatchFilter batchFilter = PCContext.getFunctions().createBatchFilter();
            for (Part part : var4) {
				batchFilter.forPartKeyEqualTo(part.getKey());
			}
            var5 = batchFilter.exec();
            //获取子批次
            for (Batch batch : var5) {
				var3.forBatchKeyEqualTo(batch.getKey());
			}
            var6 = var3.exec();
            //刷新界面“结果（-）”的数据
            if(var6.size()>0){
            	var2.setNrAllRecords(var6.size());
            }else{
            	var2.setNrAllRecords(0L);
            }
        } catch (DatasweepException var7) {
            this.handleException(var7);
        }
        return var6;
    }
    
}
