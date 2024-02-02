package com.leateck.pmc.filter.datahandler;

import com.datasweep.compatibility.client.*;
import com.rockwell.mes.clientfw.pmc.ifc.datahandler.FilterDataHandler;
import com.rockwell.mes.clientfw.pmc.ifc.usecaseconfig.IDataNode;
import com.rockwell.mes.clientfw.pmc.ifc.usecaseconfig.gen.Parameter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.commons.base.ifc.utility.MesClassUtility;

import java.util.ArrayList;
import java.util.List;

public class ExecuteFilterWithArrayData extends FilterDataHandler {
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

    public ExecuteFilterWithArrayData() {
    }

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

        //返回所有的位置
        List<String[]> locationList = (List<String[]>) var7;
        //需要展示的位置集合
        ArrayList<Location> locations = new ArrayList();
        // 获取当前用户
        User user = PCContext.getFunctions().getCurrentUser();
        for(int i = 0 ; i < locationList.size() ; i++){
            Location accessRight = PCContext.getFunctions().getLocation(locationList.get(i)[1]);
            Location storageArea = accessRight.getParentLocation();
            Location warehouse = storageArea.getParentLocation();
            try {
                if(accessRight.getUDA("LC_location_accPriv") == null || user.hasPrivilege((AccessPrivilege) accessRight.getUDA("location_accPriv"))){
                    if(storageArea.getUDA("LC_location_accPriv") == null || user.hasPrivilege((AccessPrivilege) storageArea.getUDA("location_accPriv"))){
                        if(warehouse.getUDA("LC_location_accPriv") == null || user.hasPrivilege((AccessPrivilege) warehouse.getUDA("location_accPriv"))){
                            locations.add(accessRight);
                        }
                    }
                }
            } catch (DatasweepException e) {
                e.printStackTrace();
            }
        }
        this.setNrOfRecords(locations.size(), locations.size());
        return locations;
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

    public void loadDataByKey(IDataNode dataNode) {
        long var2 = dataNode.getDataObjectKey();
        if (dataNode.getData() == null && var2 != -1L) {
            try {
                Class var4 = MesClassUtility.getBeanClass(dataNode.getTemplate().getDataClass());
                Keyed var5 = PCContext.getFunctions().getObject(var4, var2);
                dataNode.setData(var5);
            } catch (DatasweepException var6) {
                this.getExceptionHandler().handleUnexpectedProblem(var6);
            }
        }

    }
}
