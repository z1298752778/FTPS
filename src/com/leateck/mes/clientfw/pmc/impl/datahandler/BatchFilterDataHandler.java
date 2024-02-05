package com.leateck.mes.clientfw.pmc.impl.datahandler;

import java.util.ArrayList;
import java.util.List;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.BatchFilter;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.DsList;
import com.datasweep.compatibility.client.Filter;
import com.datasweep.compatibility.client.NamedFilter;
import com.datasweep.compatibility.client.Part;
import com.datasweep.compatibility.client.PartFilter;
import com.datasweep.compatibility.client.User;
import com.rockwell.mes.clientfw.pmc.ifc.datahandler.FilterDataHandler;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IMESUFUserFilter;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IMESUserFilterService;
import com.rockwell.mes.commons.base.ifc.exceptions.MESException;
import com.rockwell.mes.commons.base.ifc.services.PCContext;

public class BatchFilterDataHandler extends FilterDataHandler {
	protected static final String ACCESS_FILTER_LIST = "LC_AccessFilterList";
    public BatchFilterDataHandler() {
    }

    public Object executeHandler() {
    	
        return executeFilter();
    }
    
    public List<Object> executeFilter() {
        return this.executeFilter(false, (String[])null);
    }
    
    public List<Object> executeFilter(boolean var1, String[] var2) {
        this.resetNrOfRecords();
        IMESUserFilterService var3 = this.getFilterService();
        IMESUFUserFilter var4 = this.getUserFilter();

        try {
        	BatchFilter var9 = (BatchFilter)var3.createPCFilter(var4);
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
            
            List<Part> partList = partFilter.exec();
            for (Part part : partList) {
				var9.forPartKeyEqualTo(part.getKey());
			}
            
            if (!var1) {
                //Filter var9 = var3.createPCFilter(var4);
                this.configureFilter(var9);
                List var6;
                if (var2 == null) {
                    var6 = var3.executeUserFilter(var9, var4);
                } else {
                    var6 = var3.executeUserFilter(var9, var4, var2);
                }
                
                this.setNrOfRecords(var4);
                return var6;
            }

            long var5 = var3.getCountOfUserFilter(var4);
            this.setNrOfRecords(var5, -1L);
        } catch (DatasweepException var7) {
            this.handleException(var7);
        } catch (MESException var8) {
            this.getExceptionHandler().handleError(var8.getLocalizedMessage(), (String)null, (String)null, -1, var8);
        }

        return null;
    }
}