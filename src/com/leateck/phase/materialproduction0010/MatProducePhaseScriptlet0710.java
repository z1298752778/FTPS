package com.leateck.phase.materialproduction0010;

import com.rockwell.mes.commons.base.ifc.i18n.*;
import org.apache.commons.lang3.*;
import net.sf.jasperreports.engine.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.rockwell.mes.services.batchreport.ifc.*;
import java.util.*;

public class MatProducePhaseScriptlet0710 extends JRDefaultScriptlet
{
    private static final String ABSTRACT_PROC_MATERIAL_MSGPACK = "DataDictionary_Default_AbstractProcMaterialDAO0710";
    private static final String PHASE_PRODUCT_MATERIAL_MSGPACK = "PhaseProductMaterial0710";
    private static final String IS_HEADER_COL_NAME = "isHeader";
    private static final String IS_SUMMARY_DATA_COL_NAME = "isSummaryData";
    private static final String RESULT_COL_NAME = "result";
    private static final String RESULT_DONE = "DONE";
    private static final String SUBLOT_ID_COL_NAME = "sublotID";
    private static final String MATERIAL_ID_COL_NAME = "materialID";
    private static final String COMMA_SEPARATOR = ",";
    private static final String END_CHAR_SINGULAR_PLURAL = "_SP";
    private static final String PREFIX_FOR_PACKING_NAME = "PackagingLevel_";

    public String getComment(final Collection<IBatchProductionRecordDocumentWrapper.IProcessDataWrapper> data) {
        final StringBuilder sb = new StringBuilder();
        sb.append(I18nMessageUtility.getLocalizedMessage("PhaseProductMaterial0710", "CommentHeader"));
        boolean b = false;
        for (final IBatchProductionRecordDocumentWrapper.IProcessDataWrapper processDataWrapper : data) {
            if (Boolean.valueOf(processDataWrapper.getProcessValue("isHeader"))) {
                final String processValue = processDataWrapper.getProcessValue("commentToExecutionStr");
                if (StringUtils.isEmpty((CharSequence)processValue)) {
                    continue;
                }
                b = true;
                sb.append(StringConstants.LINE_BREAK);
                final String processValue2 = processDataWrapper.getProcessValue("materialID");
                sb.append(I18nMessageUtility.getLocalizedMessage("PhaseProductMaterial0710", "CommentFormat", new Object[] { (processValue2.indexOf(44) > 0) ? processValue2.split(",", 2)[1] : processValue2, processValue }));
            }
        }
        if (b) {
            return sb.toString();
        }
        return "";
    }

    public JRDataSource sortDataForProcMaterial(Collection<IBatchProductionRecordDocumentWrapper.IProcessDataWrapper> data) {
        List<IBatchProductionRecordDocumentWrapper.IProcessDataWrapper> dataListOfProcMat = new ArrayList<>();
        boolean isSummaryData = false;
        // Get only the records which not belongs to summary grid
        for (IBatchProductionRecordDocumentWrapper.IProcessDataWrapper ipdw : data) {
            isSummaryData = Boolean.valueOf(ipdw.getProcessValue(IS_SUMMARY_DATA_COL_NAME));
            if (!isSummaryData) {
                dataListOfProcMat.add(ipdw);
            }
        }

        // create a comparator that will sort the data by material ID, sublot ID, leaving the header row on top
        Comparator<IBatchProductionRecordDocumentWrapper.IProcessDataWrapper> comparator = new Comparator<IBatchProductionRecordDocumentWrapper.IProcessDataWrapper>() {

            @Override
            public int compare(IBatchProductionRecordDocumentWrapper.IProcessDataWrapper o1, IBatchProductionRecordDocumentWrapper.IProcessDataWrapper o2) {
                int result = o1.getProcessValue(MATERIAL_ID_COL_NAME).compareTo(o2.getProcessValue(MATERIAL_ID_COL_NAME));
                if (result != 0) {
                    return result;
                }

                boolean isO1Header = Boolean.parseBoolean(o1.getProcessValue(IS_HEADER_COL_NAME));
                boolean isO2Header = Boolean.parseBoolean(o2.getProcessValue(IS_HEADER_COL_NAME));
                if (isO1Header) {
                    return -1;
                }
                if (isO2Header) {
                    return 1;
                }

                result = o1.getProcessValue(SUBLOT_ID_COL_NAME).compareTo(o2.getProcessValue(SUBLOT_ID_COL_NAME));
                if (result != 0) {
                    return result;
                }
                return 0;
            }
        };
        Collections.sort(dataListOfProcMat, comparator);
        final Object[] beanCollection = new Object[] { dataListOfProcMat };
        return ObjectFactory.getInstance().createObject(IMESB2MMLJRDataSource.class, //
                new Class[] { Collection.class }, beanCollection);
    }

    public JRDataSource dataForSummaryGrid(Collection<IBatchProductionRecordDocumentWrapper.IProcessDataWrapper> data) {
        List<IBatchProductionRecordDocumentWrapper.IProcessDataWrapper> dataListOfProcMat = new ArrayList<>();
        boolean isHeader;
        boolean isSummaryData;
        String phaseResult;
        // Get the header only from the done phase data
        for (IBatchProductionRecordDocumentWrapper.IProcessDataWrapper ipdw : data) {
            isHeader = Boolean.valueOf(ipdw.getProcessValue(IS_HEADER_COL_NAME));
            phaseResult = ipdw.getProcessValue(RESULT_COL_NAME);
            if (isHeader && phaseResult.equals(RESULT_DONE)) {
                dataListOfProcMat.add(ipdw);
            } else {
                // Get only the records which belongs to summary grid
                isSummaryData = Boolean.valueOf(ipdw.getProcessValue(IS_SUMMARY_DATA_COL_NAME));
                if (isSummaryData) {
                    dataListOfProcMat.add(ipdw);
                }
            }
        }
        // create a comparator that will sort the data by sublot ID, leaving the header row on top
        Comparator<IBatchProductionRecordDocumentWrapper.IProcessDataWrapper> comparator = (o1, o2) -> {
            boolean isO1Header = Boolean.parseBoolean(o1.getProcessValue(IS_HEADER_COL_NAME));
            boolean isO2Header = Boolean.parseBoolean(o2.getProcessValue(IS_HEADER_COL_NAME));
            if (isO1Header) {
                return -1;
            }
            if (isO2Header) {
                return 1;
            }
            int result = o1.getProcessValue(SUBLOT_ID_COL_NAME).compareTo(o2.getProcessValue(SUBLOT_ID_COL_NAME));
            if (result != 0) {
                return result;
            }
            return 0;
        };
        Collections.sort(dataListOfProcMat, comparator);

        final Object[] beanCollection = new Object[] { dataListOfProcMat };
        return ObjectFactory.getInstance().createObject(IMESB2MMLJRDataSource.class, //
                new Class[] { Collection.class }, beanCollection);
    }

    public String getField(final JRDataSource ds, final String fieldID) {
        return ((IBatchProductionRecordDocumentWrapper.IProcessDataWrapper)((IMESB2MMLJRDataSource)ds).getCurrentBean()).getProcessValue(fieldID);
    }

    public String getResultForProcMaterialPhase(final Collection<IBatchProductionRecordDocumentWrapper.IProcessDataWrapper> dataListForResult, final String fieldID) {
        String processValue = null;
        final Iterator<IBatchProductionRecordDocumentWrapper.IProcessDataWrapper> iterator = dataListForResult.iterator();
        while (iterator.hasNext()) {
            processValue = iterator.next().getProcessValue(fieldID);
        }
        return processValue;
    }

    public String getLocalizedGridHeaderMessage(final String msgID) {
        return I18nMessageUtility.getLocalizedMessage("DataDictionary_Default_AbstractProcMaterialDAO0710", msgID);
    }

    public String getLocalizedMessageForProcMaterialSummaryGrid() {
        return I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "Summary_Grid_Label");
    }

    public String getDefinedPackingLevelsDataIntoSubReport(final Collection<IBatchProductionRecordDocumentWrapper.IProcessDataWrapper> phaseData, final String fieldIDForDefinedValues, final String fieldIDForDefinedNames) {
        String processValue = null;
        String processValue2 = null;
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<Object> list2 = new ArrayList<Object>();
        final StringBuilder sb = new StringBuilder();
        for (final IBatchProductionRecordDocumentWrapper.IProcessDataWrapper processDataWrapper : phaseData) {
            if (Boolean.parseBoolean(processDataWrapper.getProcessValue("isHeader"))) {
                processValue = processDataWrapper.getProcessValue(fieldIDForDefinedValues);
                processValue2 = processDataWrapper.getProcessValue(fieldIDForDefinedNames);
                break;
            }
        }
        if (processValue != null) {
            list = new ArrayList<String>(Arrays.asList(processValue.split(",")));
        }
        if (processValue2 != null) {
            list2 = new ArrayList<Object>(Arrays.asList(processValue2.split(",")));
            Collections.reverse(list2);
        }
        if (processValue2 != null && !processValue2.equals("")) {
            for (int i = 0; i < list2.size(); ++i) {
                if (Integer.parseInt((String)list.get(i)) != 0) {
                    sb.append((String)list.get(i) + " " + " " + " " + I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "PackagingLevel_" + list2.get(i) + "_SP"));
                    sb.append(StringConstants.LINE_BREAK);
                }
            }
        }
        return sb.toString();
    }

    public Boolean isPrintSummary(final Collection<IBatchProductionRecordDocumentWrapper.IProcessDataWrapper> data) {
        for (final IBatchProductionRecordDocumentWrapper.IProcessDataWrapper processDataWrapper : data) {
            if (!processDataWrapper.getProcessValue("result").equals("DONE")) {
                return Boolean.FALSE;
            }
            if (Boolean.valueOf(processDataWrapper.getProcessValue("isSummaryData"))) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
