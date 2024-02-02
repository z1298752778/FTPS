package com.leateck.mes.clientfw.pmc.ifc.datahandler;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.DsList;
import com.datasweep.compatibility.client.NamedFilter;
import com.datasweep.compatibility.client.Filter;
import com.rockwell.mes.clientfw.pmc.ifc.usecaseconfig.IDataNode;
import com.rockwell.mes.clientfw.pmc.ifc.usecaseconfig.gen.Parameter;
import com.rockwell.mes.clientfw.pmc.ifc.datahandler.FilterDataHandler;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IMESUFUserFilter;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IMESUserFilterService;
import com.rockwell.mes.commons.base.ifc.exceptions.MESException;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.datasweep.compatibility.client.User;
import com.datasweep.compatibility.client.Keyed;
import com.rockwell.mes.commons.base.ifc.utility.MesClassUtility;

import java.util.ArrayList;
import java.util.List;

public class PartFilterDataHandler extends FilterDataHandler {
	protected static final String ACCESS_FILTER_LIST = "LC_AccessFilterList";
	protected static final String TABLE_NAME_PARAM = "tableName";
    protected static final String TABLE_KEY_PARAM = "tableKey";
    protected static final String FETCHED_COLUMNS_PARAM = "fetchedColumns";
    protected static final String UDA_TABLE_NAME_PARAM = "UDAsTableName";
    protected static final String FETCHED_UDA_COLUMNS_PARAM = "fetchedUDAColumns";
    protected String tableName;
    protected String tableKey;
    protected String[] fetchedColumns = new String[0];
    protected String udaTableName;
    protected String[] fetchedUDAColumns = new String[0];

    

    public Object executeHandler() {
        this.retrieveParamValues();
        String[] var1 = new String[this.fetchedColumns.length + this.fetchedUDAColumns.length + 1];
        var1[0] = this.tableName + "." + this.tableKey;
        int var2 = 1;
        String[] var3 = this.fetchedColumns;
        int var4 = var3.length;

        int var5;
        String var6;
        for(var5 = 0; var5 < var4; ++var5) {
            var6 = var3[var5];
            var1[var2++] = this.tableName + "." + var6;
        }

        var3 = this.fetchedUDAColumns;
        var4 = var3.length;

        for(var5 = 0; var5 < var4; ++var5) {
            var6 = var3[var5];
            var1[var2++] = this.udaTableName + "." + var6;
        }

        List var7 = this.executeFilterWithArrayData(var1);
        return var7;
    }
    
    protected List<Object> executeFilter(boolean var1, String[] var2) {
        this.resetNrOfRecords();
        IMESUserFilterService var3 = this.getFilterService();
        IMESUFUserFilter var4 = this.getUserFilter();
        try {
            Filter var9 = var3.createPCFilter(var4);
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
            var9.forUdaEqualTo("LC_material_accPriv", "");
            for (AccessPrivilege accessRight : accessMaterial) {
                var9.forUdaEqualTo("LC_material_accPriv", accessRight);
            }

            if (!var1) {
                // Filter var9 = var3.createPCFilter(var4);

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
            long var5 = var9.getCount();
            this.setNrOfRecords(var5, -1L);
        } catch (DatasweepException var7) {
            this.handleException(var7);
        } catch (MESException var8) {
            this.getExceptionHandler().handleError(var8.getLocalizedMessage(), (String) null, (String) null, -1, var8);
        }

        return null;
    }

    protected void retrieveParamValues() {
        Parameter[] var1 = this.getConfiguration().getParameter();
        Parameter[] var2 = var1;
        int var3 = var1.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Parameter var5 = var2[var4];
            if (var5.getKey().equals("tableName")) {
                this.tableName = var5.getValue();
            } else if (var5.getKey().equals("tableKey")) {
                this.tableKey = var5.getValue();
            } else if (var5.getKey().equals("UDAsTableName")) {
                this.udaTableName = var5.getValue();
            } else if (var5.getKey().equals("fetchedColumns")) {
                this.fetchedColumns = var5.getValue().split(",");
            } else if (var5.getKey().equals("fetchedUDAColumns")) {
                this.fetchedUDAColumns = var5.getValue().split(",");
            }
        }

    }

    public void loadDataByKey(IDataNode var1) {
        long var2 = var1.getDataObjectKey();
        if (var1.getData() == null && var2 != -1L) {
            try {
                Class var4 = MesClassUtility.getBeanClass(var1.getTemplate().getDataClass());
                Keyed var5 = PCContext.getFunctions().getObject(var4, var2);
                var1.setData(var5);
            } catch (DatasweepException var6) {
                this.getExceptionHandler().handleUnexpectedProblem(var6);
            }
        }

    }

}
