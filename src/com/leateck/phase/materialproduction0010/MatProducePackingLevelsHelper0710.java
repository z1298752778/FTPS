package com.leateck.phase.materialproduction0010;

import com.rockwell.mes.services.s88.ifc.recipe.*;
import com.rockwell.mes.services.commons.ifc.packaginglevels.*;
import java.util.*;
import com.rockwell.mes.commons.base.ifc.choicelist.*;
import java.math.*;
import com.rockwell.mes.commons.base.ifc.functional.*;
import com.datasweep.plantops.common.measuredvalue.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.commons.base.ifc.exceptions.*;

public class MatProducePackingLevelsHelper0710
{
    private static long defaultValueForLevel0;
    protected static final String SUBLOT_INVENTORY_MEANING = "Sublot";
    protected static final String LOGISTICUNIT_INVENTORY_MEANING = "LogisticUnit";
    protected static final String SUBLOT_LOGISTIC_INVENTORY_MEANING = "SublotAndLogisticUnit";

    public static List<IPackagingLevelBean> getAllPackagingLevels(final IMESMaterialParameter materialParameter, final IMESMasterRecipe mr) {
        List<IPackagingLevelBean> list = new ArrayList<>();
        if (materialParameter != null) {
            list.addAll(materialParameter.getPackingLevels());
        }
        if (mr != null) {
            list.addAll(mr.getPackingLevels());
        }
        return list;
    }

    public static List<IPackagingLevelBean> getConfiguredPackagingLevels(final List<IPackagingLevelBean> allPackingLevelsList) {
        final ArrayList<IPackagingLevelBean> list = new ArrayList<IPackagingLevelBean>();
        if (!allPackingLevelsList.isEmpty()) {
            for (int i = 0; i < allPackingLevelsList.size(); ++i) {
                if (allPackingLevelsList.get(i).getPackagingLevelContent() != null && allPackingLevelsList.get(i).getPackagingLevelContent() != 0L) {
                    list.add(allPackingLevelsList.get(i));
                }
                if (allPackingLevelsList.get(i).getPackagingLevelNumber() == 0 && !allPackingLevelsList.get(i).getPackagingLevel().getMeaning().equals("NotApplicable") && !allPackingLevelsList.get(i).isHiddenDuringExecution()) {
                    allPackingLevelsList.get(i).setPackagingLevelContent(MatProducePackingLevelsHelper0710.defaultValueForLevel0);
                    list.add(allPackingLevelsList.get(i));
                }
            }
        }
        return list;
    }

    public static IMESChoiceElement getDefaultPackagingCE(final List<IPackagingLevelBean> allPackingLevelsList) {
        IMESChoiceElement packagingLevel = null;
        if (!allPackingLevelsList.isEmpty()) {
            for (int i = 0; i < allPackingLevelsList.size(); ++i) {
                if (allPackingLevelsList.get(i).getPackagingLevelNumber() == 0) {
                    packagingLevel = allPackingLevelsList.get(i).getPackagingLevel();
                }
            }
        }
        return packagingLevel;
    }

    public static PackingLevelSublotsQuantitiesResult calculateSublotQtyAndNumberOfSubLots(final IMeasuredValue sublotLevelQuantity, final IMeasuredValue totalQuantity) {
        final BigDecimal value = totalQuantity.getValue();
        final IUnitOfMeasure unitOfMeasure = totalQuantity.getUnitOfMeasure();
        int intValue = 0;
        Object mv = MeasuredValueUtilities.createMV(BigDecimal.ZERO, unitOfMeasure);
        int partialSublots = 0;
        MeasuredValue partialSublotsQty = MeasuredValueUtilities.createMV(BigDecimal.ZERO, unitOfMeasure);
        if (value.compareTo(BigDecimal.ZERO) > 0) {
            if (sublotLevelQuantity == null) {
                intValue = 1;
                mv = totalQuantity;
            }
            else {
                mv = sublotLevelQuantity;
                final BigDecimal[] divideAndRemainder = value.divideAndRemainder(((IMeasuredValue)mv).getValue());
                intValue = divideAndRemainder[0].intValue();
                partialSublotsQty = MeasuredValueUtilities.createMV(divideAndRemainder[1], unitOfMeasure);
                if (((IMeasuredValue)partialSublotsQty).getValue().compareTo(BigDecimal.ZERO) != 0) {
                    partialSublots = 1;
                }
            }
        }
        return new PackingLevelSublotsQuantitiesResult(intValue, (IMeasuredValue)mv, partialSublots, (IMeasuredValue)partialSublotsQty);
    }

    public static ContainedNumbersOfBasePackagineLevelResult calculateContainedNumbersOfBasePackagingLevel(final List<IPackagingLevelBean> definedPackagingLevels) {
        final ArrayList<BigDecimal> containedNumbersOfBaseLevel = new ArrayList<BigDecimal>();
        BigDecimal containedNumberOfSLLevelOfBaseLevel = null;
        BigDecimal containedNumberOfLULevelOfBaseLevel = null;
        long n = 1L;
        for (int i = 0; i < definedPackagingLevels.size(); ++i) {
            n *= definedPackagingLevels.get(i).getPackagingLevelContent();
            containedNumbersOfBaseLevel.add(BigDecimal.valueOf(n));
            final String meaning = definedPackagingLevels.get(i).getInventoryLevel().getMeaning();
            if (meaning != null && meaning.equals("LogisticUnit")) {
                containedNumberOfLULevelOfBaseLevel = BigDecimal.valueOf(n);
            }
            else if (meaning != null && meaning.equals("Sublot")) {
                containedNumberOfSLLevelOfBaseLevel = BigDecimal.valueOf(n);
            }
            else if (meaning != null && meaning.equals("SublotAndLogisticUnit")) {
                containedNumberOfSLLevelOfBaseLevel = BigDecimal.valueOf(n);
                containedNumberOfLULevelOfBaseLevel = BigDecimal.valueOf(n);
            }
        }
        return new ContainedNumbersOfBasePackagineLevelResult(containedNumbersOfBaseLevel, containedNumberOfSLLevelOfBaseLevel, containedNumberOfLULevelOfBaseLevel);
    }

    public static BigDecimal calculateValueForCurrentLevel(final List<BigDecimal> numbersForPackagingLevels, final List<BigDecimal> factorsForPackagingLevelsComparedToBaseLevel, final int currentPackagingLevelIndex) {
        BigDecimal bigDecimal = null;
        for (int i = 0; i < numbersForPackagingLevels.size(); ++i) {
            final BigDecimal multiply = numbersForPackagingLevels.get(i).multiply(factorsForPackagingLevelsComparedToBaseLevel.get(i));
            if (i == currentPackagingLevelIndex) {
                bigDecimal = multiply;
            }
        }
        return bigDecimal;
    }

    public static IMeasuredValue calculateSumOfAllPackagingLevels(final List<IMeasuredValue> quantitiesForPackagingLevels) {
        IMeasuredValue add = null;
        for (int i = 0; i < quantitiesForPackagingLevels.size(); ++i) {
            try {
                if (add == null) {
                    add = quantitiesForPackagingLevels.get(i);
                }
                else {
                    add = add.add(quantitiesForPackagingLevels.get(i));
                }
            }
            catch (Exception ex) {
                throw new MESRuntimeException("Calculation of total value failed", (Throwable)ex);
            }
        }
        return add;
    }

    static {
        MatProducePackingLevelsHelper0710.defaultValueForLevel0 = 1L;
    }

    public static class PackingLevelSublotsQuantitiesResult
    {
        private int numOfFullSublots;
        private int numOfPartialSublots;
        private IMeasuredValue fullSublotQty;
        private IMeasuredValue partialSublotQty;

        public PackingLevelSublotsQuantitiesResult(final int fullSublots, final IMeasuredValue fullSublotsQty, final int partialSublots, final IMeasuredValue partialSublotsQty) {
            this.numOfFullSublots = fullSublots;
            this.fullSublotQty = fullSublotsQty;
            this.numOfPartialSublots = partialSublots;
            this.partialSublotQty = partialSublotsQty;
        }

        public int getNumOfFullSublots() {
            return this.numOfFullSublots;
        }

        public IMeasuredValue getFullSublotQty() {
            return this.fullSublotQty;
        }

        public int getNumOfPartialSublots() {
            return this.numOfPartialSublots;
        }

        public IMeasuredValue getPartialSublotQty() {
            return this.partialSublotQty;
        }
    }

    public static class ContainedNumbersOfBasePackagineLevelResult
    {
        private List<BigDecimal> containedNumbersOfBasePackagingLevel;
        private BigDecimal containedNumberOfSLLevelOfBasePackagingLevel;
        private BigDecimal containedNumberOfLULevelOfBasePackagingLevel;

        public ContainedNumbersOfBasePackagineLevelResult(final List<BigDecimal> containedNumbersOfBaseLevel, final BigDecimal containedNumberOfSLLevelOfBaseLevel, final BigDecimal containedNumberOfLULevelOfBaseLevel) {
            this.containedNumbersOfBasePackagingLevel = containedNumbersOfBaseLevel;
            this.containedNumberOfSLLevelOfBasePackagingLevel = containedNumberOfSLLevelOfBaseLevel;
            this.containedNumberOfLULevelOfBasePackagingLevel = containedNumberOfLULevelOfBaseLevel;
        }

        public List<BigDecimal> getContainedNumbersOfBasePackagingLevel() {
            return this.containedNumbersOfBasePackagingLevel;
        }

        public BigDecimal getContainedNumberOfSLLevelOfBasePackagingLevel() {
            return this.containedNumberOfSLLevelOfBasePackagingLevel;
        }

        public BigDecimal getContainedNumberOfLULevelOfBasePackagingLevel() {
            return this.containedNumberOfLULevelOfBasePackagingLevel;
        }
    }
}
