package com.leateck.phase.materialalternativeidentification0010;

import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.utility.ObjectFactory;
import com.rockwell.mes.services.batchreport.ifc.IBatchProductionRecordDocumentWrapper.IProcessDataWrapper;
import com.rockwell.mes.services.batchreport.ifc.IMESB2MMLJRDataSource;
import com.rockwell.mes.shared.product.material.MaterialPhasesScriptlet0710;
import net.sf.jasperreports.engine.JRDataSource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Scriptlet used for the Identify Material phases reports
 * <p>
 *
 * @author bdreyer, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
 */
public class MatIdentPhaseScriptlet0710 extends MaterialPhasesScriptlet0710 {


    private static final String IS_HEADER_COL_NAME = "isHeader";

    private static final String LOCAL_IDENTIFIED = "localIdentified";

    private static final String MFC_POS = "mfcPos";

    private static final String BATCH_ID = "batchIDStr";

    private static final String SUBLOT_ID = "sublotIDStr";

    /**
     * Get a localized message from the message pack (summary grid header)
     *
     * @return localized message
     */
    public String getLocalizedMessageForSummaryGrid() {
        return I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "Summary_Grid_Label");
    }

    /**
     * Get the phase result value from the data source
     *
     * @param dataListForResult data source
     * @param fieldID           field name
     * @return the localized value
     */
    public String getResultForIdentMaterialPhase(Collection<IProcessDataWrapper> dataListForResult, String fieldID) {
        String resultValue = null;
        for (IProcessDataWrapper ipdw : dataListForResult) {
            resultValue = ipdw.getProcessValue(fieldID);
        }
        return resultValue;
    }

    /**
     * Get the data for summary grid
     *
     * @param data data from the phase
     * @return the by MFC Position sorted and filtered data source
     */
    public JRDataSource dataForInstanceGrid(Collection<IProcessDataWrapper> data) {
        boolean summary = false;
        List<IProcessDataWrapper> filteredData = filterData(data, summary);
        return sortDataMFCPos(filteredData);
    }

    /**
     * Get the data for summary grid
     *
     * @param data data from the phase
     * @return the by MFC Position sorted and filtered data source
     */
    public JRDataSource dataForSummaryGrid(Collection<IProcessDataWrapper> data) {
        boolean summary = true;
        List<IProcessDataWrapper> filteredData = filterData(data, summary);
        return sortDataMFCPos(filteredData);
    }


    private List<IProcessDataWrapper> filterData(Collection<IProcessDataWrapper> data, boolean summary) {
        List<IProcessDataWrapper> dataListOfProcMat = new ArrayList<>();
        for (IProcessDataWrapper ipdw : data) {
            Boolean isHeader = Boolean.valueOf(ipdw.getProcessValue(IS_HEADER_COL_NAME));
            if (isHeader) {
                dataListOfProcMat.add(ipdw);
            } else {
                Boolean locallyIdentified = Boolean.valueOf(ipdw.getProcessValue(LOCAL_IDENTIFIED));
                // Get only the records which belongs to summary grid
                if (summary || locallyIdentified) {
                    dataListOfProcMat.add(ipdw);
                }
            }
        }
        return dataListOfProcMat;
    }

    /**
     *
     */
    private static final class ComparatorByMFCPos implements Comparator<IProcessDataWrapper>, Serializable {
        /**
         * Comment for <code>serialVersionUID</code>
         */
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(IProcessDataWrapper o1, IProcessDataWrapper o2) {
            String matId1 = MatIdentModel0710.getMaterialNameOfPhaseData(o1.getProcessValue(MATERIAL_ID_COL_NAME));
            String matId2 = MatIdentModel0710.getMaterialNameOfPhaseData(o2.getProcessValue(MATERIAL_ID_COL_NAME));
            final int compareResultMaterialID = ObjectUtils.compare(matId1, matId2);

            if (compareResultMaterialID == 0) {
                // same materials: put the header on top
                return compareSameMaterial(o1, o2);
            } else {
                return compareDifferentMaterials(o1, o2, compareResultMaterialID);
            }
        }

        private int compareSameMaterial(IProcessDataWrapper o1, IProcessDataWrapper o2) {
            boolean isO1Header = Boolean.parseBoolean(o1.getProcessValue(IS_HEADER_COL_NAME));
            boolean isO2Header = Boolean.parseBoolean(o2.getProcessValue(IS_HEADER_COL_NAME));
            if (isO1Header != isO2Header) {
                return isO1Header ? -1 : 1;
            }
            // for all subrows of the same material, keep the original sorting
            // next sort by batch id
            String batchO1 = o1.getProcessValue(BATCH_ID);
            String batchO2 = o2.getProcessValue(BATCH_ID);
            if (!StringUtils.equals(batchO1, batchO2)) {
                return (batchO1 == null) ? -1 : batchO1.compareTo(batchO2);
            }
            // last, sort by sublot id
            String sublotO1 = o1.getProcessValue(SUBLOT_ID);
            String sublotO2 = o2.getProcessValue(SUBLOT_ID);
            return (sublotO1 == null) ? -1 : sublotO1.compareTo(sublotO2);
        }

        private int compareDifferentMaterials(IProcessDataWrapper o1, IProcessDataWrapper o2, int compareResultMaterialID) {
            final String pos1 = o1.getProcessValue(MFC_POS);
            final String pos2 = o2.getProcessValue(MFC_POS);
            final boolean pos1NotBlank = StringUtils.isNotBlank(pos1);
            final boolean pos2NotBlank = StringUtils.isNotBlank(pos2);

            if (pos1NotBlank && pos2NotBlank) {
                // sort by MFC Position
                return pos1.compareTo(pos2);
            } else if (pos1NotBlank) {
                // MFC Position before empty
                return -1;
            } else if (pos2NotBlank) {
                return 1;
            } else {
                // both without MFC positions: sort by MaterialID
                return compareResultMaterialID;
            }
        }
    }

    private JRDataSource sortDataMFCPos(List<IProcessDataWrapper> data) {
        List<IProcessDataWrapper> dataList = new ArrayList<>();
        dataList.addAll(data);

        // create a comparator that will sort the data by material ID, leaving the header row on top
        Comparator<IProcessDataWrapper> comparator = new ComparatorByMFCPos();
        Collections.sort(dataList, comparator);

        final Object[] beanCollection = new Object[]{dataList};
        return ObjectFactory.getInstance().createObject(IMESB2MMLJRDataSource.class, //
                new Class[]{Collection.class}, beanCollection);
    }

}
