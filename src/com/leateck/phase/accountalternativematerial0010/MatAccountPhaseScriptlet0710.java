package com.leateck.phase.accountalternativematerial0010;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.utility.ObjectFactory;
import com.rockwell.mes.services.batchreport.ifc.IBatchProductionRecordDocumentWrapper.IProcessDataWrapper;
import com.rockwell.mes.services.batchreport.ifc.IMESB2MMLJRDataSource;
import com.rockwell.mes.shared.product.material.MaterialModel0710;
import com.rockwell.mes.shared.product.material.MaterialPhasesScriptlet0710;

import net.sf.jasperreports.engine.JRDataSource;

/**
 * Scriptlet used for the Account Material phases reports
 * <p>
 * 
 * @author bdreyer, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
 */
public class MatAccountPhaseScriptlet0710 extends MaterialPhasesScriptlet0710 {

    private static final String RESULT_COL_NAME = "result";
    private static final String IS_HEADER_COL_NAME = "isHeader";
    private static final String BATCH_ID_COL_NAME = "batchID";

    private static final String SUBLOT_ID_COL_NAME = "sublotID";

    private static final String MFC_POS = "mfcPos";

    /**
     * Get a localized message from the message pack containing header texts for the account material grids
     * 
     * @param msgID message ID
     * @return localized message
     */
    public String getAccountLocalizedGridHeaderMessage(String msgID) {
        return I18nMessageUtility.getLocalizedMessage("DataDictionary_Default_AccountMaterialDAO0710", msgID);
    }

    /**
     * @param phaseData the wrapped corresponding phase data
     * @return combined batch/sublot string
     */
    public String getCombinedBatchSublotDisplayString(IProcessDataWrapper phaseData) {
        String batchID = phaseData.getProcessValue(BATCH_ID_COL_NAME);
        String sublotID = phaseData.getProcessValue(SUBLOT_ID_COL_NAME);
        if (StringUtils.isEmpty(batchID)) {
            return sublotID;
        }
        final String[] args = new String[] { batchID, sublotID };
        return I18nMessageUtility.getLocalizedMessage(MaterialModel0710.PHASE_PRODUCT_MATERIAL_MSGPACK, "BatchSublot_ReportDisplayString", args);
    }

    /**
     * Get the data for grid
     * 
     * @param data data from the phase
     * 
     * @return the by MFC Position sorted data source
     */
    public JRDataSource dataForGrid(Collection<IProcessDataWrapper> data) {
        return sortDataByMFCPos(data);
    }

    /** */
    private static final class ComparatorByMFCPos implements Comparator<IProcessDataWrapper>, Serializable {
        /** Comment for <code>serialVersionUID</code> */
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(IProcessDataWrapper o1, IProcessDataWrapper o2) {
            // we compare the entire materialID entries, which also includes the master OSI key; this way different
            // positions of the same material are sorted correctly
            String matId1 = o1.getProcessValue(MATERIAL_ID_COL_NAME);
            String matId2 = o2.getProcessValue(MATERIAL_ID_COL_NAME);
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
            String batchO1 = o1.getProcessValue(BATCH_ID_COL_NAME);
            String batchO2 = o2.getProcessValue(BATCH_ID_COL_NAME);
            if (!StringUtils.equals(batchO1, batchO2)) {
                return (batchO1 == null) ? -1 : batchO1.compareTo(batchO2);
            }
            // last, sort by sublot id
            String sublotO1 = o1.getProcessValue(SUBLOT_ID_COL_NAME);
            String sublotO2 = o2.getProcessValue(SUBLOT_ID_COL_NAME);
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

    private JRDataSource sortDataByMFCPos(Collection<IProcessDataWrapper> data) {
        List<IProcessDataWrapper> dataList = new ArrayList<>();
        dataList.addAll(data);

        // create a comparator that will sort the data by material ID, leaving the header row on top
        Comparator<IProcessDataWrapper> comparator = new ComparatorByMFCPos();
        Collections.sort(dataList, comparator);

        final Object[] beanCollection = new Object[] { dataList };
        return ObjectFactory.getInstance().createObject(IMESB2MMLJRDataSource.class, //
                new Class[] { Collection.class }, beanCollection);
    }


    /**
     * @param data data data from the phase
     * 
     * @return the phase result
     */
    @SuppressWarnings("squid:S1751") // see below
    public String getPhaseResult(Collection<IProcessDataWrapper> data) {
        for (IProcessDataWrapper aData : data) {
            // We want to return the value of the first row because all have the same value
            return aData.getProcessValue(RESULT_COL_NAME);
        }
        return StringUtils.EMPTY;
    }

    /**
     * @param data data data from the phase
     * 
     * @return the localized phase result
     */
    public String getLocalizedPhaseResult(Collection<IProcessDataWrapper> data) {
        String result = getPhaseResult(data);
        return StringUtils.isEmpty(result) ? result : MaterialModel0710.getLocalizedResult(result);
    }
}
