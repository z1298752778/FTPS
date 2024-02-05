package com.leateck.mes.clientfw.pmc.ifc.datahandler;

import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.Batch;
import com.datasweep.compatibility.client.BatchFilter;
import com.datasweep.compatibility.client.DsList;
import com.datasweep.compatibility.client.Filter;
import com.datasweep.compatibility.client.NamedFilter;
import com.datasweep.compatibility.client.Part;
import com.datasweep.compatibility.client.PartFilter;
import com.datasweep.compatibility.client.User;
import com.rockwell.mes.clientfw.pmc.ifc.datahandler.FilterDataHandler;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IMESUFUserFilter;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.services.PCContext;

import java.util.ArrayList;
import java.util.List;

public class ExecuteAtRowFilterToMESATObject extends FilterDataHandler{
	protected static final String ACCESS_FILTER_LIST = "LC_AccessFilterList";
	public Object executeHandler(){
		IMESUFUserFilter var2 = this.getUserFilter();
		try{
			List localList = executeFilter();
			List<Part> var4 = null;
			List<Batch> var5 = null;
			List<ATRow> historyAllList = localList;
			List<ATRow> historyList = new ArrayList<ATRow>();
			User user = PCContext.getFunctions().getCurrentUser();
          
			//获取物料管理权限
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
          	BatchFilter batchFilter = PCContext.getFunctions().createBatchFilter();
          	for (Part part : var4) {
				batchFilter.forPartKeyEqualTo(part.getKey());
          	}
          	var5 = batchFilter.exec();
          
          	for(ATRow history :historyAllList){
            	for(Batch batch : var5){
                	if(history.getValue("X_batchIdentifierNew").equals(batch.getName())){
                		historyList.add(history);
                	}
                }
            }
          	//刷新界面“结果（-）”的数据
          	if(historyList.size()>0){
            	var2.setNrAllRecords(historyList.size());
            }else{
            	var2.setNrAllRecords(0L);
            }
          if (historyList != null)
          {
        	  Class localClass = getDataClass();
        	  this.setNrOfRecords((long)historyList.size(), (long)historyList.size());
        	  return MESATObject.convertATRowsToMESATObjects(historyList, localClass);
          }
		}
		catch (Exception localException)
		{
			getExceptionHandler().handleUnexpectedProblem(localException);
		}
		return null;
  }
  
  private Class<? extends IMESATObject> getDataClass()
  {
    String str = getDataNode().getTemplate().getDataClass();
    try
    {
      Class localClass = Class.forName(str);
      if (!IMESATObject.class.isAssignableFrom(localClass)) {
        throw new MESRuntimeException("expected sub-class or sub-interface of IMESATObject");
      }
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new MESRuntimeException(localClassNotFoundException);
    }
  }
}

/* Location:           F:\MPS\mps-localization\lib\FTPS_DSX\addon_jars\clientfw-pmc-ifc-10.2.0.9.jar.jar
 * Qualified Name:     com.rockwell.mes.clientfw.pmc.ifc.datahandler.ExecuteAtRowFilterToMESATObject
 * Java Class Version: 8 (52.0)
 * JD-Core Version:    0.7.1
 */