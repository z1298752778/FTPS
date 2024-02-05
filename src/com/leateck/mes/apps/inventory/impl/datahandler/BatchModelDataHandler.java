package com.leateck.mes.apps.inventory.impl.datahandler;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.Batch;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.DsList;
import com.datasweep.compatibility.client.Filter;
import com.datasweep.compatibility.client.NamedFilter;
import com.datasweep.compatibility.client.Part;
import com.datasweep.compatibility.client.PartFilter;
import com.datasweep.compatibility.client.User;
import com.rockwell.mes.apps.inventory.impl.model.BatchModel;
import com.rockwell.mes.clientfw.pmc.ifc.datahandler.FilterDataHandler;
import com.rockwell.mes.commons.base.ifc.services.PCContext;

import java.util.ArrayList;
import java.util.List;

public class BatchModelDataHandler extends FilterDataHandler {
	protected static final String ACCESS_FILTER_LIST = "LC_AccessFilterList";
    public BatchModelDataHandler() {
    }

    public Object executeHandler() {
        List var1 = super.executeFilter();
        List<Batch> batchAllList = var1; 
        List<Batch> batchList = new ArrayList<Batch>();
        List<Part> partList = new ArrayList<Part>(); 
        try {
            User user = PCContext.getFunctions().getCurrentUser();
            
            // 获取物料管理权限
            DsList partAccessFilter = PCContext.getFunctions().getList(ACCESS_FILTER_LIST);
            NamedFilter filter = PCContext.getFunctions().getFilterByName(partAccessFilter.getItem(0).toString());
            Filter accessFilter = filter.getFilter();
            List<AccessPrivilege> accessRights = accessFilter.exec();
            
            // 获取当前用户的物料管理权限
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
            //获取显示的物料集合
            partList = partFilter.exec();
            
            for (Batch batch : batchAllList) {
            	for (Part part : partList) {
            		if((batch.getPart()).equals(part)){
            			batchList.add(batch);
            		}
            	}
			}
            if(batchList.size()>0){
            	this.setNrOfRecords(batchList.size(),batchList.size());
            }else{
            	this.setNrOfRecords(0L,0L);
            }
            
        }catch (DatasweepException var7) {
            this.handleException(var7);
        }
            
        return convertToModel(batchList);
    }

    public static List<BatchModel> convertToModel(List<Batch> var0) {
        if (var0 == null) {
            return null;
        } else {
            ArrayList var1 = new ArrayList(var0.size());

            for(int var2 = 0; var2 < var0.size(); ++var2) {
                BatchModel var3 = new BatchModel();
                var3.setBatch((Batch)var0.get(var2));
                var1.add(var2, var3);
            }

            return var1;
        }
    }
}
