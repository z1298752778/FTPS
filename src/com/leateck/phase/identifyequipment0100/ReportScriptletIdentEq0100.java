package com.leateck.phase.identifyequipment0100;

import com.rockwell.mes.commons.base.ifc.comparators.MESComparableToComparatorAdapter;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.utility.MesXMLConstants;
import com.rockwell.mes.phase.eqidentification.report.ReportScriptletEqIdent0210;
import com.rockwell.mes.services.s88.ifc.EquipmentPropertyListRowModel;
import com.rockwell.mes.services.s88equipment.ifc.EnumEqmPropertyTypeUsage;
import com.rockwell.mes.shared.childgrid.AbstractReportScriptletGroupingPhases;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class ReportScriptletIdentEq0100 extends AbstractReportScriptletGroupingPhases {
    public ReportScriptletIdentEq0100() {
    }

    public static String getDisplayText(String text, String defaultText) {
        return StringUtils.defaultString(text, defaultText);
    }

    public static String getDisplayTextCombined(String textPart1, String textPart2, String textSeparator, String defaultText) {
        if (StringUtils.isEmpty(textPart1) && StringUtils.isEmpty(textPart2)) {
            return defaultText;
        } else {
            return StringUtils.isEmpty(textPart2) ? textPart1 : textPart1 + textSeparator + textPart2;
        }
    }

    /** @deprecated */
    @Deprecated
    public String getLocalizedMessage(String msgId) {
        return I18nMessageUtility.getLocalizedMessage("PhaseEqmEqIdentification0210", msgId);
    }

    public static Collection<ReportScriptletEqIdent0210.PropertyRequirementItem> propertyItemsEncoded2Collection(String base64EncodedString) {
        byte[] var1 = MesXMLConstants.byteArrayFromBase64EncodedString(base64EncodedString);
        if (ArrayUtils.isEmpty(var1)) {
            return Collections.emptyList();
        } else {
            List var2 = (List) SerializationUtils.deserialize(var1);
            if (var2.isEmpty()) {
                return Collections.emptyList();
            } else {
                ArrayList var3 = new ArrayList();
                Iterator var4 = var2.iterator();

                while(var4.hasNext()) {
                    String[] var5 = (String[])var4.next();
                    if (var5.length != 3) {
                        throw new MESRuntimeException("Invalid length of property string array: " + var5.length);
                    }

                    ReportScriptletEqIdent0210.PropertyRequirementItem var7 = new ReportScriptletEqIdent0210.PropertyRequirementItem(var5[0], var5[1], var5[2]);
                    var3.add(var7);
                }

                Collections.sort(var3);
                return var3;
            }
        }
    }

    public static Collection<EquipmentPropertyListRowModel> propertyListItemsEncoded2Collection(String base64EncodedString) {
        byte[] var1 = MesXMLConstants.byteArrayFromBase64EncodedString(base64EncodedString);
        if (ArrayUtils.isEmpty(var1)) {
            return Collections.emptyList();
        } else {
            List var2 = (List)SerializationUtils.deserialize(var1);
            Collections.sort(var2);
            return var2;
        }
    }

    public static class PropertyRequirementItem implements Comparable<ReportScriptletIdentEq0100.PropertyRequirementItem> {
        private final String propertyName;
        private final String propertyValue;
        private final String propertyTypeUsage;

        public PropertyRequirementItem(String name, String value, String usage) {
            this.propertyName = name;
            this.propertyValue = value;
            this.propertyTypeUsage = usage;
        }

        public String getPropertyName() {
            return this.propertyName;
        }

        public String getPropertyValue() {
            return this.propertyValue;
        }

        public String getPropertyTypeUsage() {
            return this.propertyTypeUsage;
        }

        public Boolean getIsUsageTypeAutomation() {
            boolean var1 = StringUtils.equals(this.propertyTypeUsage, EnumEqmPropertyTypeUsage.AUTOMATION.getMeaning());
            return var1;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.propertyName, this.propertyTypeUsage, this.propertyValue});
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (obj == null) {
                return false;
            } else if (this.getClass() != obj.getClass()) {
                return false;
            } else {
                ReportScriptletIdentEq0100.PropertyRequirementItem var2 = (ReportScriptletIdentEq0100.PropertyRequirementItem)obj;
                boolean var3 = StringUtils.equals(this.propertyName, var2.propertyName);
                var3 = var3 && StringUtils.equals(this.propertyTypeUsage, var2.propertyTypeUsage);
                var3 = var3 && StringUtils.equals(this.propertyValue, var2.propertyValue);
                return var3;
            }
        }

        public int compareTo(ReportScriptletIdentEq0100.PropertyRequirementItem other) {
            int var2 = MESComparableToComparatorAdapter.compareObjects(this.propertyName, other.propertyName);
            if (var2 != 0) {
                return var2;
            } else {
                var2 = MESComparableToComparatorAdapter.compareObjects(this.propertyTypeUsage, other.propertyTypeUsage);
                return var2 != 0 ? var2 : MESComparableToComparatorAdapter.compareObjects(this.propertyValue, other.propertyValue);
            }
        }
    }
}
