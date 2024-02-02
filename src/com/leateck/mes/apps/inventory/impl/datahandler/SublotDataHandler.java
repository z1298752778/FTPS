package com.leateck.mes.apps.inventory.impl.datahandler;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.Batch;
import com.datasweep.compatibility.client.BatchFilter;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.DsList;
import com.datasweep.compatibility.client.Filter;
import com.datasweep.compatibility.client.NamedFilter;
import com.datasweep.compatibility.client.Part;
import com.datasweep.compatibility.client.PartFilter;
import com.datasweep.compatibility.client.Sublot;
import com.datasweep.compatibility.client.SublotFilter;
import com.datasweep.compatibility.client.User;
import com.rockwell.mes.clientfw.pmc.ifc.datahandler.FilterDataHandler;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IMESUFUserFilter;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IMESUserFilterService;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.services.inventory.ifc.ISublotService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SublotDataHandler extends FilterDataHandler {
	protected static final String ACCESS_FILTER_LIST = "LC_AccessFilterList";
    public SublotDataHandler() {
    }

    public Object executeHandler() {
        List var0 = super.executeFilter();
        ArrayList var1 = new ArrayList();
        List<Sublot> sublotAccessList = sublotAccessList();
    	for (Sublot sublot : sublotAccessList) {
    		for (Object sublotBefore : var0) {
    			if(sublotBefore.equals(sublot)){
        			var1.add(sublotBefore);
        		}
			}
        }
        List var2 = filterOutDeletedAndTemporarySublots(var1);
        int var3 = var2.size();
        this.setNrOfRecords((long)var3, (long)var3);
        this.getUserFilter().setNrAllRecords((long)var3);
        return var2;
    }

    public static List<Sublot> filterOutDeletedAndTemporarySublots(List<Sublot> var0) {
        if (var0 == null) {
            return null;
        } else {
            ArrayList var1 = new ArrayList(var0.size());
            Iterator var2 = var0.iterator();

            while(var2.hasNext()) {
                Sublot var3 = (Sublot)var2.next();
                ISublotService var4 = (ISublotService)ServiceFactory.getService(ISublotService.class);
                if (!var4.isSublotLogicallyDeleted(var3) && !var4.isSublotTemporary(var3)) {
                	var1.add(var3);
                }
            }

            return var1;
        }
    }

    private static List<Sublot> sublotAccessList() {
    	List<Part> partList = null;
        List<Batch> batchList = null;
        List<Sublot> sublotList = new ArrayList<Sublot>();
        try {
            User user = PCContext.getFunctions().getCurrentUser();
            
            // 获取物料管理权限
            DsList partAccessFilter = PCContext.getFunctions().getList(ACCESS_FILTER_LIST);
            NamedFilter filter = PCContext.getFunctions().getFilterByName(partAccessFilter.getItem(0).toString());
            Filter accessFilter = filter.getFilter();
            List<AccessPrivilege> accessRights;
			
				accessRights = accessFilter.exec();
			
            
            // 获取当前用户的物料管理权限
            List<AccessPrivilege> accessMaterial = new ArrayList<AccessPrivilege>();
            for (AccessPrivilege accessRight : accessRights) {
            	
                if (user.hasPrivilege(accessRight)) {
             	   accessMaterial.add(accessRight);
                }
            }

            // 获取物料集合
            PartFilter partFilter = PCContext.getFunctions().createPartFilter();
            partFilter.forUdaEqualTo("LC_material_accPriv", "");
            for (AccessPrivilege accessRight : accessMaterial) {
            	partFilter.forUdaEqualTo("LC_material_accPriv", accessRight);
            }
            partList = partFilter.exec();
            //获取批次
            BatchFilter batchFilter = PCContext.getFunctions().createBatchFilter();
            for (Part part : partList) {
				batchFilter.forPartKeyEqualTo(part.getKey());
			}
            batchList = batchFilter.exec();
            //获取子批次
            SublotFilter sublotFilter = PCContext.getFunctions().createSublotFilter();
            for (Batch batch : batchList) {
				sublotFilter.forBatchKeyEqualTo(batch.getKey());
			}
            sublotList = sublotFilter.exec();
		} catch (DatasweepException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return sublotList;
	}

	protected List<Object> executeFilter(boolean var1, String[] var2) {
        if (var1) {
            this.resetNrOfRecords();
            IMESUserFilterService var3 = this.getFilterService();
            IMESUFUserFilter var4 = this.getUserFilter();

            try {
                long var5 = var3.getCountOfUserFilter(var4);
                long var7 = this.getNumOfTemporarySublots();
                this.setNrOfRecords(var5 - var7, -1L);
            } catch (DatasweepException var9) {
                this.handleException(var9);
            }

            return null;
        } else {
            return super.executeFilter(var1, var2);
        }
    }

    private long getNumOfTemporarySublots() throws DatasweepException {
        SublotFilter var1 = new SublotFilter(PCContext.getServerImpl());
        var1.forUdaEqualTo("X_temporary", 1);
        long var2 = var1.getCount();
        return var2;
    }
}
