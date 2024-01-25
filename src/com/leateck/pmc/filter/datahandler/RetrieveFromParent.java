package com.leateck.pmc.filter.datahandler;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.Location;
import com.datasweep.compatibility.client.User;
import com.rockwell.mes.clientfw.pmc.ifc.datahandler.BasicDataHandler;
import com.rockwell.mes.clientfw.pmc.ifc.usecaseconfig.IDataNode;
import com.rockwell.mes.clientfw.pmc.ifc.usecaseconfig.gen.Parameter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RetrieveFromParent extends BasicDataHandler {
    private static final String GETTER = "Getter";

    public RetrieveFromParent() {
    }

    public Object executeHandler() {
        IDataNode var1 = this.getDataNode().getParent();
        Object var2 = var1.getData();
        String var3 = null;

        for(int var4 = 0; var4 < this.getConfiguration().getParameterCount(); ++var4) {
            Parameter var5 = this.getConfiguration().getParameter(var4);
            if (var5.getKey().equalsIgnoreCase("Getter")) {
                var3 = var5.getValue();
                break;
            }
        }

        if (var3 == null) {
            throw new RuntimeException("The data handler 'RetrieveFromParent' required the parameter 'Getter'.");
        } else {
            try {
                Method var11 = var2.getClass().getMethod(var3);
                //返回所有的区域和位置
                List<Location> locationList = (List<Location>) var11.invoke(var2);
                //需要展示的区域和位置集合
                ArrayList<Location> locations = new ArrayList();
                //获取当前用户
                User user = PCContext.getFunctions().getCurrentUser();

                for (Location accessRight : locationList) {
                    try {
                        if(accessRight.getUDA("location_accPriv") == null || user.hasPrivilege((AccessPrivilege) accessRight.getUDA("location_accPriv")))
                            locations.add(accessRight);
                    } catch (DatasweepException e) {
                        e.printStackTrace();
                    }
                }
                return locations;
            } catch (SecurityException var6) {
                this.getExceptionHandler().handleUnexpectedProblem(var6);
            } catch (NoSuchMethodException var7) {
                this.getExceptionHandler().handleUnexpectedProblem(var7);
            } catch (IllegalArgumentException var8) {
                this.getExceptionHandler().handleUnexpectedProblem(var8);
            } catch (IllegalAccessException var9) {
                this.getExceptionHandler().handleUnexpectedProblem(var9);
            } catch (InvocationTargetException var10) {
                this.getExceptionHandler().handleUnexpectedProblem(var10);
            }

            return null;
        }
    }
}
