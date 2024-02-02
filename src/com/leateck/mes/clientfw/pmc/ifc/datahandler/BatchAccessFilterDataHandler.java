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
import com.rockwell.mes.clientfw.pmc.ifc.datahandler.FilterDataHandler;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IMESUFUserFilter;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IMESUserFilterService;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.datasweep.compatibility.client.User;

import java.util.ArrayList;
import java.util.List;

public class BatchAccessFilterDataHandler extends FilterDataHandler {
	protected static final String ACCESS_FILTER_LIST = "LC_AccessFilterList";
    public Object executeHandler() {
    	IMESUserFilterService var1 = this.getFilterService();
        IMESUFUserFilter var2 = this.getUserFilter();
        List<Part> var4 = null;
        List<Batch> var5 = null;
        try {
            BatchFilter var3 = (BatchFilter)var1.createPCFilter(var2);
            User user = PCContext.getFunctions().getCurrentUser();
            
            // ��ȡ���Ϲ���Ȩ��
            DsList partAccessFilter = PCContext.getFunctions().getList(ACCESS_FILTER_LIST);
            NamedFilter filter = PCContext.getFunctions().getFilterByName(partAccessFilter.getItem(0).toString());
            Filter accessFilter = filter.getFilter();
            List<AccessPrivilege> accessRights = accessFilter.exec();
            
            // ��ȡ��ǰ�û�ӵ�е����Ϲ���Ȩ��
            List<AccessPrivilege> accessMaterial = new ArrayList<AccessPrivilege>();
            for (AccessPrivilege accessRight : accessRights) {
            	
                if (user.hasPrivilege(accessRight)) {
             	   accessMaterial.add(accessRight);
                }
            }

            // ��ӹ�������
            PartFilter partFilter = PCContext.getFunctions().createPartFilter();
            partFilter.forUdaEqualTo("LC_material_accPriv", "");
            for (AccessPrivilege accessRight : accessMaterial) {
            	partFilter.forUdaEqualTo("LC_material_accPriv", accessRight);
            }
            var4 = partFilter.exec();
            for (Part part : var4) {
				var3.forPartKeyEqualTo(part.getKey());
			}
            var5 = var3.exec();
          //ˢ�½����С������-������ʾ
            if(var5.size()>0){
            	var2.setNrAllRecords(var5.size());
            }else{
            	var2.setNrAllRecords(0L);
            }
            
        } catch (DatasweepException var7) {
            this.handleException(var7);
        }
        return var5;
    }
    
}
