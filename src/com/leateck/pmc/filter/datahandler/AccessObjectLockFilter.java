package com.leateck.pmc.filter.datahandler;

import com.datasweep.compatibility.client.*;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.commons.base.impl.objectlock.MESObjectLock;
import org.apache.ecs.wml.A;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

public class AccessObjectLockFilter {
    protected static final String ACCESS_FILTER_LIST = "LC_AccessFilterList";

    public List<MESObjectLock> executeHandler(Object objectLocks) throws DatasweepException {
        // 获取对象锁权限
        DsList partAccessFilter = PCContext.getFunctions().getList(ACCESS_FILTER_LIST);
        NamedFilter filter = PCContext.getFunctions().getFilterByName(partAccessFilter.getItem(1).toString());
        Filter accessFilter = filter.getFilter();
        List<AccessPrivilege> accessRights = accessFilter.exec();
        //获取当前用户
        User user = PCContext.getFunctions().getCurrentUser();
        // 获取当前用户拥有的对象锁权限
        List<AccessPrivilege> currentAccessObjectLock = new ArrayList<AccessPrivilege>();
        for (AccessPrivilege accessRight : accessRights) {
            if (user.hasPrivilege(accessRight)) {
                currentAccessObjectLock.add(accessRight);
            }
        }

        HashSet<MESObjectLock> accessObjectLockFirst = new HashSet<>();
        List<MESObjectLock> objectLock = (List<MESObjectLock>) objectLocks;
        for (MESObjectLock accessRightObjectLock : objectLock) {
            //获取每条对象锁对应的人拥有的对象锁权限
            List<AccessPrivilege> accessObjectLock = new ArrayList<AccessPrivilege>();
            for (AccessPrivilege accessRight : accessRights) {
                if (accessRightObjectLock.getUser().hasPrivilege(accessRight)) {
                    accessObjectLock.add(accessRight);
                }
            }
            //获取当前用户拥有这条记录用户权限的记录
            if(currentAccessObjectLock.size() == 0){
                if(accessObjectLock.size() == 0){
                    accessObjectLockFirst.add(accessRightObjectLock);
                }
            }else {
                for (AccessPrivilege accessRight : currentAccessObjectLock) {
                    if(accessObjectLock.size() == 0){
                        accessObjectLockFirst.add(accessRightObjectLock);
                    }else {
                        if(accessObjectLock.contains(accessRight)){
                            accessObjectLockFirst.add(accessRightObjectLock);
                        }
                    }

                }
            }
        }
        //将HashSet转化为ArrayList
        List<MESObjectLock> accessObjectLockFirsts = new ArrayList<>();
        for (MESObjectLock mesObject : accessObjectLockFirst) {
            accessObjectLockFirsts.add(mesObject);
        }
        AccessObjectLockFilter accessObjectLockFilter = new AccessObjectLockFilter();
        return accessObjectLockFilter.locationExecuteHandler(accessObjectLockFirsts);
    }

    //筛选位置对象
    public List<MESObjectLock> locationExecuteHandler(List<MESObjectLock> objectLocks) throws DatasweepException {
        //需要展示的位置集合
        List<MESObjectLock> locations = new ArrayList();
        // 获取当前用户
        User user = PCContext.getFunctions().getCurrentUser();
        for (MESObjectLock accessRightObjectLock : objectLocks) {
            if("Location".equals(accessRightObjectLock.getObjectType())){
                Location accessRight = PCContext.getFunctions().getLocation(accessRightObjectLock.getObjectName());
                try {
                    //没有父节点，属于仓库
                    if(accessRight.getParentLocation() == null){
                        if(accessRight.getUDA("LC_location_accPriv") == null || user.hasPrivilege((AccessPrivilege) accessRight.getUDA("location_accPriv")))
                            locations.add(accessRightObjectLock);
                    }
                    //有一层父节点，属于区域
                    else if(accessRight.getParentLocation().getParentLocation() == null){
                        if(accessRight.getUDA("LC_location_accPriv") == null || user.hasPrivilege((AccessPrivilege) accessRight.getUDA("location_accPriv"))){
                            if(accessRight.getParentLocation().getUDA("LC_location_accPriv") == null || user.hasPrivilege((AccessPrivilege) accessRight.getParentLocation().getUDA("location_accPriv"))){
                                locations.add(accessRightObjectLock);
                            }
                        }
                    }else{
                        //属于位置
                        Location storageArea = accessRight.getParentLocation();
                        Location warehouse = storageArea.getParentLocation();
                        if(accessRight.getUDA("LC_location_accPriv") == null || user.hasPrivilege((AccessPrivilege) accessRight.getUDA("location_accPriv"))){
                            if(storageArea.getUDA("LC_location_accPriv") == null || user.hasPrivilege((AccessPrivilege) storageArea.getUDA("location_accPriv"))){
                                if(warehouse.getUDA("LC_location_accPriv") == null || user.hasPrivilege((AccessPrivilege) warehouse.getUDA("location_accPriv"))){
                                    locations.add(accessRightObjectLock);
                                }
                            }
                        }
                    }
                }
                catch (DatasweepException e) {
                    e.printStackTrace();
                }
            }else{
                locations.add(accessRightObjectLock);
            }
        }
        AccessObjectLockFilter accessObjectLockFilter = new AccessObjectLockFilter();
        return accessObjectLockFilter.partExecuteHandler(locations);
    }

    //筛选物料，批次，子批次对象
    public List<MESObjectLock> partExecuteHandler(List<MESObjectLock> objectLocks) throws DatasweepException {
        //需要展示的物料，批次，子批次集合
        List<MESObjectLock> parts = new ArrayList();
        // 获取当前用户
        User user = PCContext.getFunctions().getCurrentUser();
        for (MESObjectLock accessRightObjectLock : objectLocks) {
            if("Part".equals(accessRightObjectLock.getObjectType())){
                Part accessPartRight = PCContext.getFunctions().getPart(accessRightObjectLock.getObjectName());
                if(accessPartRight != null){
                    if(accessPartRight.getUDA("LC_material_accPriv") == null || user.hasPrivilege((AccessPrivilege) accessPartRight.getUDA("LC_material_accPriv")))
                        parts.add(accessRightObjectLock);
                }
            }else if("Batch".equals(accessRightObjectLock.getObjectType())){
                Batch accessRight = PCContext.getFunctions().getBatchByName(accessRightObjectLock.getObjectName());
                Part accessPartRight = accessRight.getPart();
                if(accessPartRight != null){
                    if(accessPartRight.getUDA("LC_material_accPriv") == null || user.hasPrivilege((AccessPrivilege) accessPartRight.getUDA("LC_material_accPriv")))
                        parts.add(accessRightObjectLock);
                }
            }else if("Sublot".equals(accessRightObjectLock.getObjectType())){
                SublotFilter sublotFilter = PCContext.getFunctions().createSublotFilter();
                sublotFilter.forNameEqualTo(accessRightObjectLock.getObjectName());
                Vector<Sublot> filteredSublots = PCContext.getFunctions().getFilteredSublots(sublotFilter);
                Part accessPartRight = filteredSublots.get(0).getPart();
                if(accessPartRight != null){
                    if(accessPartRight.getUDA("LC_material_accPriv") == null || user.hasPrivilege((AccessPrivilege) accessPartRight.getUDA("LC_material_accPriv")))
                        parts.add(accessRightObjectLock);
                }
            }else {
                parts.add(accessRightObjectLock);
            }
        }
        AccessObjectLockFilter accessObjectLockFilter = new AccessObjectLockFilter();
        return accessObjectLockFilter.EquipmentExecuteHandler(parts);
    }

    //筛选设备对象
    public List<MESObjectLock> EquipmentExecuteHandler(List<MESObjectLock> objectLocks) throws DatasweepException {
        //获取当前用户
        User user = PCContext.getFunctions().getCurrentUser();
        List<MESObjectLock> accessEquipment = new ArrayList<>();
        for (MESObjectLock accessRightObjectLock : objectLocks) {
            if("ATRow X_S88Equipment".equals(accessRightObjectLock.getObjectType())){
                String sql = " SELECT LC_equip_accPriv_98 FROM AT_X_S88Equipment WHERE  X_identifier_S = N'" +accessRightObjectLock.getObjectName()+"'";
                Vector result = PCContext.getFunctions().getArrayDataFromActive(sql);
                String[] array = (String[]) result.get(0);
                if(array[0] == null){
                    accessEquipment.add(accessRightObjectLock);
                }else if (user.hasPrivilege(PCContext.getFunctions().getAccessPrivilegeByKey(Long.parseLong(array[0])))) {
                    accessEquipment.add(accessRightObjectLock);
                }
            }else {
                accessEquipment.add(accessRightObjectLock);
            }
        }
        AccessObjectLockFilter accessObjectLockFilter = new AccessObjectLockFilter();
        return accessObjectLockFilter.MasterExecuteHandler(accessEquipment);
    }

    //筛选工单，处方对象
    public List<MESObjectLock> MasterExecuteHandler(List<MESObjectLock> objectLocks) throws DatasweepException {
        //获取当前用户
        User user = PCContext.getFunctions().getCurrentUser();
        List<MESObjectLock> accessMaster = new ArrayList<>();
        for (MESObjectLock accessRightObjectLock : objectLocks) {
            if("MasterRecipe".equals(accessRightObjectLock.getObjectType())){
                String[] masterRecipe = accessRightObjectLock.getObjectName().split(" ");
                String MasterRecipeName = masterRecipe[0];
                String MasterRecipeVersion = masterRecipe[1].substring(1, masterRecipe[1].length() - 1);
                String sql = " SELECT t3.X_accPrivConfidentialObject_98 FROM MASTER_RECIPE t2 join UDA_MasterRecipe t3 on t2.master_recipe_key = t3.object_key " +
                        "WHERE t2.master_recipe_name = N'" + MasterRecipeName + "' AND t2.master_recipe_revision = '" + MasterRecipeVersion +"'";
                Vector result = PCContext.getFunctions().getArrayDataFromActive(sql);
                String[] array = (String[]) result.get(0);
                if(array[0] == null){
                    accessMaster.add(accessRightObjectLock);
                }else if (user.hasPrivilege(PCContext.getFunctions().getAccessPrivilegeByKey(Long.parseLong(array[0])))) {
                    accessMaster.add(accessRightObjectLock);
                }
            }else if("ProcessOrder".equals(accessRightObjectLock.getObjectType())){
                String sql = " SELECT t3.X_accPrivConfidentialObject_98 FROM PROCESS_ORDER_ITEM t join CONTROL_RECIPE t1 on t.order_item_key = t1.order_item_key \n" +
                        "join MASTER_RECIPE t2 on t1.master_recipe_key = t2.master_recipe_key\n" +
                        "join UDA_MasterRecipe t3 on t2.master_recipe_key = t3.object_key  WHERE t.order_item_name = N'" +accessRightObjectLock.getObjectName()+"'";
                Vector result = PCContext.getFunctions().getArrayDataFromActive(sql);
                String[] array = (String[]) result.get(0);
                if(array[0] == null){
                    accessMaster.add(accessRightObjectLock);
                }else if (user.hasPrivilege(PCContext.getFunctions().getAccessPrivilegeByKey(Long.parseLong(array[0])))) {
                    accessMaster.add(accessRightObjectLock);
                }
            }else {
                accessMaster.add(accessRightObjectLock);
            }
        }
        AccessObjectLockFilter accessObjectLockFilter = new AccessObjectLockFilter();
        return accessObjectLockFilter.StationExecuteHandler(accessMaster);
    }

    //筛选工作站对象
    public List<MESObjectLock> StationExecuteHandler(List<MESObjectLock> objectLocks) throws DatasweepException {
        //获取当前用户
        User user = PCContext.getFunctions().getCurrentUser();
        HashSet<MESObjectLock> accessStation = new HashSet<>();
        for (MESObjectLock accessRightObjectLock : objectLocks) {
            if("WorkCenter".equals(accessRightObjectLock.getObjectType())){
                String sql = " SELECT t1.X_accessPrivilege_98 FROM STATION t join UDA_Station t1 on t.station_key = t1.object_key \n" +
                        "join WORK_CENTER t2 on t.wc_key = t2.wc_key " +
                        "WHERE  t2.wc_name = N'" +accessRightObjectLock.getObjectName()+"'";
                Vector result = PCContext.getFunctions().getArrayDataFromActive(sql);
                if(result.size() == 0){
                    accessStation.add(accessRightObjectLock);
                }else {
                    for(Object stationAccess : result){
                        String[] array = (String[]) stationAccess;
                        if(array[0] == null){
                            accessStation.add(accessRightObjectLock);
                        }else if (user.hasPrivilege(PCContext.getFunctions().getAccessPrivilegeByKey(Long.parseLong(array[0])))) {
                            accessStation.add(accessRightObjectLock);
                        }
                    }
                }
            }else if("Station".equals(accessRightObjectLock.getObjectType())){
                String sql = " SELECT t1.X_accessPrivilege_98 FROM STATION t join UDA_Station t1 on t.station_key = t1.object_key " +
                        "WHERE  t.station = N'" +accessRightObjectLock.getObjectName()+"'";
                Vector result = PCContext.getFunctions().getArrayDataFromActive(sql);
                String[] array = (String[]) result.get(0);
                if(array[0] == null){
                    accessStation.add(accessRightObjectLock);
                }else if (user.hasPrivilege(PCContext.getFunctions().getAccessPrivilegeByKey(Long.parseLong(array[0])))) {
                    accessStation.add(accessRightObjectLock);
                }
            }else {
                accessStation.add(accessRightObjectLock);
            }
        }
        List<MESObjectLock> accessObjectLockfinally = new ArrayList<>();
        for (MESObjectLock mesObject : accessStation) {
            accessObjectLockfinally.add(mesObject);
        }
        return accessObjectLockfinally;
    }
}
