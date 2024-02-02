package com.leateck.mes.apps.inventory.impl.datahandler;

import com.datasweep.compatibility.client.Filter;
import com.datasweep.compatibility.client.FilterSortConstraint;
import com.leateck.mes.clientfw.pmc.ifc.datahandler.ExecuteAtRowFilterToMESATObject;
import com.rockwell.mes.clientfw.pmc.ifc.filters.IConfidentialObjectFilterService;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.commons.base.ifc.utility.ObjectUtilsEx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 修改继承类
 */
public class ExecuteTransactionHistoryObjectFilter extends ExecuteAtRowFilterToMESATObject {
    private static final Log LOGGER = LogFactory.getLog(ExecuteTransactionHistoryObjectFilter.class);

    public ExecuteTransactionHistoryObjectFilter() {
    }

    private static void initialize() {
        IConfidentialObjectFilterService var0 = (IConfidentialObjectFilterService)ServiceFactory.getService(IConfidentialObjectFilterService.class);
        var0.registerFilterColumnForATDefiniton("X_TransactionHistory", "X_accPrvConfidentialObj");
    }
        
    protected void configureFilter(final Filter var1) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Configuring filter data handler for transaction history object:");
            LOGGER.debug("\tPC filter " + var1.getObjectIdentifier());
            LOGGER.debug("\tFilter order by count: " + var1.getOrderByCount());
            logFilterSortCriteria("\t-> Before modification:", var1);
        }

        ArrayList var2 = new ArrayList(var1.getOrderBy());
        FilterSortConstraint var3 = null;
        FilterSortConstraint var4 = null;
        boolean var5 = false;
        Iterator var6 = var2.iterator();

        while(true) {
            while(var6.hasNext()) {
                FilterSortConstraint var7 = (FilterSortConstraint)var6.next();
                if (var7.getAttribute() == 2) {
                    var4 = var7;
                } else if (var7.getAttribute() == 7 && "X_timestamp".equals(var7.getAttributeIdentifier())) {
                    var3 = var7;
                } else if (var7.getAttribute() == 7 && "X_creationSeqIndex".equals(var7.getAttributeIdentifier())) {
                    var5 = true;
                }
            }

            this.removeSortOrder(var1, var2);
            if (var4 == null && var3 == null) {
                this.restoreSortOrder(var1, var2);
            } else if (var4 != null && var3 == null) {
                this.sortByKeySortOrder(var1, var2, var4);
            } else if (var4 == null && var3 != null) {
                this.sortByTimestampSortOrder(var1, var2, var3, var5);
            } else {
                this.sortByBothSortOrders(var1, var2, var3, var4, var5);
            }

            if (LOGGER.isDebugEnabled()) {
                logFilterSortCriteria("\t=> After modification:", var1);
            }

            return;
        }
    }

    private void removeSortOrder(Filter var1, List<FilterSortConstraint> var2) {
        Iterator var3 = var2.iterator();

        while(var3.hasNext()) {
            FilterSortConstraint var4 = (FilterSortConstraint)var3.next();
            var1.removeOrderBy(var4);
          
        }

    }

    private void restoreSortOrder(Filter var1, List<FilterSortConstraint> var2) {
        Iterator var3 = var2.iterator();

        while(var3.hasNext()) {
            FilterSortConstraint var4 = (FilterSortConstraint)var3.next();
            this.addOrderBy(var1, var4);
        }

    }

    private void sortByKeySortOrder(Filter var1, List<FilterSortConstraint> var2, FilterSortConstraint var3) {
        FilterSortConstraint var5;
        for(Iterator var4 = var2.iterator(); var4.hasNext(); this.addOrderBy(var1, var5)) {
            var5 = (FilterSortConstraint)var4.next();
            if (ObjectUtilsEx.isSame(var5, var3)) {
                var1.addOrderATColumnBy("X_timestamp", var3.getSortOrder());
                var1.addOrderATColumnBy("X_creationSeqIndex", var3.getSortOrder());
            }
        }

    }

    private void sortByTimestampSortOrder(Filter var1, List<FilterSortConstraint> var2, FilterSortConstraint var3, boolean var4) {
        Iterator var5 = var2.iterator();

        while(var5.hasNext()) {
            FilterSortConstraint var6 = (FilterSortConstraint)var5.next();
            this.addOrderBy(var1, var6);
            if (ObjectUtilsEx.isSame(var6, var3) && !var4) {
                var1.addOrderATColumnBy("X_creationSeqIndex", var3.getSortOrder());
            }
        }

    }

    private void sortByBothSortOrders(Filter var1, List<FilterSortConstraint> var2, FilterSortConstraint var3, FilterSortConstraint var4, boolean var5) {
        boolean var6 = false;
        Iterator var7 = var2.iterator();

        while(var7.hasNext()) {
            FilterSortConstraint var8 = (FilterSortConstraint)var7.next();
            if (ObjectUtilsEx.isSame(var8, var4)) {
                if (!var6) {
                    var1.addOrderATColumnBy("X_timestamp", var4.getSortOrder());
                    if (!var5) {
                        var1.addOrderATColumnBy("X_creationSeqIndex", var4.getSortOrder());
                    }

                    this.addOrderBy(var1, var8);
                    var6 = true;
                }
            } else if (ObjectUtilsEx.isSame(var8, var3)) {
                if (!var6) {
                    this.addOrderBy(var1, var8);
                    if (!var5) {
                        var1.addOrderATColumnBy("X_creationSeqIndex", var4.getSortOrder());
                    }

                    var1.addOrderBy((short)2, var3.getSortOrder());
                    var6 = true;
                }
            } else {
                this.addOrderBy(var1, var8);
            }
        }

    }

    private void addOrderBy(Filter var1, FilterSortConstraint var2) {
        if (!StringUtils.isEmpty(var2.getAttributeIdentifier())) {
            var1.addOrderBy(var2.getAttributeIdentifier(), var2.getAttribute(), var2.getSortOrder());
        } else {
            var1.addOrderBy(var2.getAttribute(), var2.getSortOrder());
        }

    }

    private static void logFilterSortCriteria(String var0, Filter var1) {
        LOGGER.debug(var0);
        List var2 = var1.getOrderBy();
        Iterator var3 = var2.iterator();

        while(var3.hasNext()) {
            FilterSortConstraint var4 = (FilterSortConstraint)var3.next();
            LOGGER.debug("\t\tFilter order by: " + filterAttributeToString(var4.getAttribute()) + " " + (!StringUtils.isEmpty(var4.getAttributeIdentifier()) ? var4.getAttributeIdentifier() + " " : "") + sortOrderToString(var4.getSortOrder()));
        }

    }

    private static String filterAttributeToString(short var0) {
        switch(var0) {
        case 2:
            return "KEY";
        case 7:
            return "ATCOLUMN";
        default:
            return Short.toString(var0);
        }
    }

    private static String sortOrderToString(short var0) {
        switch(var0) {
        case 1:
            return "ASC";
        case 2:
            return "DESC";
        default:
            return "N/A (" + Short.toString(var0) + ")";
        }
    }

    static {
        initialize();
    }
}
