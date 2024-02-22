package com.leateck.phase.wdmaterialidentification0100;

import com.rockwell.mes.commons.base.ifc.exceptions.*;
import com.rockwell.mes.services.eqm.ifc.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import java.util.*;
import org.apache.commons.lang3.*;
import com.rockwell.mes.services.inventory.ifc.*;
import com.rockwell.mes.apps.ebr.ifc.phase.*;
import com.rockwell.mes.commons.parameter.exceptiondef.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.rockwell.mes.shared.product.wd.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.*;
import com.rockwell.mes.commons.base.ifc.choicelist.*;
import java.io.*;

public class ManualIdentificationHelper0610
{
    private static final String MSGCAT = "wd_MaterialIdentification0610";

    static IIdentifiedItem0610 getIdentifiedItemByManualIdentificationModel(final MatIdentExceptionView0610.ManualIdentificationModel model) {
        if (model.isEmpty()) {
            WDHelper0610.showInfoDialog("wd_MaterialIdentification0610", "noDataForIdentification", new Object[0]);
            return null;
        }
        if (model.hasContainer()) {
            return handleResultOfGetContainer(model);
        }
        if (model.hasSublot()) {
            if (model.isSublotUnique()) {
                return handleResultOfGetSublots(getSublotsBySublotIdentifier(model.getSublotIdentifier()), model);
            }
            return handleResultOfGetSublots(getSublotsBySublotBatchIdentifier(model), model);
        }
        else {
            try {
                return handleResultOfGetSublots(getSublotsByBatchPartIdentifier(model), model);
            }
            catch (MESMissingBarcodeDataException ex) {
                WDHelper0610.showInfoDialog("wd_MaterialIdentification0610", ex.getMissingData().getMessageId(), new Object[0]);
                return null;
            }
        }
    }

    private static IIdentifiedItem0610 handleResultOfGetContainer(final MatIdentExceptionView0610.ManualIdentificationModel model) {
        final IMESContainerEquipment loadMESContainerEquipmentByName = ((IMESContainerEquipmentService)ServiceFactory.getService((Class)IMESContainerEquipmentService.class)).loadMESContainerEquipmentByName(model.getContainerIdentifier());
        if (loadMESContainerEquipmentByName == null) {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "containerNotFound_ErrorMsg", new Object[0]);
            return null;
        }
        return IdentifiedItem0610.createItem(loadMESContainerEquipmentByName);
    }

    private static List<Sublot> getSublotsBySublotIdentifier(final String sublotID) {
        final SublotFilter sublotFilter = PCContext.getFunctions().createSublotFilter();
        sublotFilter.forNameEqualTo(sublotID);
        return (List<Sublot>)PCContext.getFunctions().getFilteredSublots(sublotFilter);
    }

    private static List<Sublot> getSublotsBySublotBatchIdentifier(final MatIdentExceptionView0610.ManualIdentificationModel model) {
        final Sublot loadSublot = ((ISublotService)ServiceFactory.getService((Class)ISublotService.class)).loadSublot(model.getBatchIdentifier(), model.getSublotIdentifier());
        return (loadSublot != null) ? Collections.singletonList(loadSublot) : Collections.emptyList();
    }

    private static List<Sublot> getSublotsByBatchPartIdentifier(final MatIdentExceptionView0610.ManualIdentificationModel model) throws MESMissingBarcodeDataException {
        final Batch batchByBatchPartIdentifier = getBatchByBatchPartIdentifier(model);
        return (batchByBatchPartIdentifier != null) ? Collections.singletonList(((IMFCService)ServiceFactory.getService((Class)IMFCService.class)).createEmptyTemporarySublot(batchByBatchPartIdentifier)) : Collections.emptyList();
    }

    private static Batch getBatchByBatchPartIdentifier(final MatIdentExceptionView0610.ManualIdentificationModel model) throws MESMissingBarcodeDataException {
        final String batchIdentifier = model.getBatchIdentifier();
        if (StringUtils.isEmpty((CharSequence)batchIdentifier)) {
            throw new MESMissingBarcodeDataException(MESMissingBarcodeDataException.MissingData.BATCH);
        }
        final IBatchService batchService = (IBatchService)ServiceFactory.getService((Class)IBatchService.class);
        Batch batch;
        if (batchService.areBatchIdentifiersUnique()) {
            batch = batchService.loadBatch(batchIdentifier);
        }
        else {
            final String partIdentifier = model.getPartIdentifier();
            if (StringUtils.isEmpty((CharSequence)partIdentifier)) {
                throw new MESMissingBarcodeDataException(MESMissingBarcodeDataException.MissingData.PART);
            }
            batch = batchService.loadBatchByCompoundIdentifier(batchIdentifier, partIdentifier);
        }
        return batch;
    }

    private static IIdentifiedItem0610 handleResultOfGetSublots(final List<Sublot> sublots, final MatIdentExceptionView0610.ManualIdentificationModel model) {
        Sublot sublot = null;
        model.setSublotUnique(true);
        if (sublots.isEmpty()) {
            if (model.hasSublot()) {
                WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "sublotNotFound_ErrorMsg", new Object[0]);
            }
            else {
                WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "batchNotFound_ErrorMsg", new Object[0]);
            }
        }
        else if (sublots.size() > 1) {
            WDHelper0610.showInfoDialog("wd_MaterialIdentification0610", "sublotIdNotUnique_ErrorMsg", new Object[] { model.getSublotIdentifier() });
            model.setSublotUnique(false);
        }
        else {
            sublot = sublots.get(0);
        }
        return (sublot != null) ? IdentifiedItem0610.createItem(sublot) : null;
    }

    static String getManualIdentExceptionText(final IPhaseExecutor executor, final Sublot sublot, final IMESContainerEquipment sourceContainer, final String appendString) {
        final String exceptionDescr = ((MESParamExceptionDef0300)executor.getProcessParameterData((Class)MESParamExceptionDef0300.class, "Identify manually")).getExceptionDescr();
        StringBuilder sb;
        if (exceptionDescr != null) {
            sb = new StringBuilder(exceptionDescr);
        }
        else {
            sb = new StringBuilder();
        }
        if (sb.length() > 0) {
            sb.append(StringConstants.LINE_BREAK);
        }
        final Part part = sublot.getBatch().getPart();
        sb.append(part.getPartNumber());
        sb.append(" / ");
        sb.append(WDHelper0610.getMaterialShortDescription(part));
        sb.append(" / ");
        sb.append(AbstractWeighView0610.getBatchName(sublot.getBatch()));
        appendWithDelimiterIfNotEmpty(sb, AbstractWeighView0610.getSublotName(sublot));
        appendWithDelimiterIfNotEmpty(sb, (sourceContainer != null) ? sourceContainer.getName() : "");
        if (StringUtils.isNotEmpty((CharSequence)appendString)) {
            sb.append(StringConstants.LINE_BREAK);
            sb.append(appendString);
        }
        return sb.toString();
    }

    private static void appendWithDelimiterIfNotEmpty(final StringBuilder exceptionText, final String theString) {
        if (StringUtils.isNotEmpty((CharSequence)theString)) {
            exceptionText.append(" / ");
            exceptionText.append(theString);
        }
    }

    static IMESExceptionRecord.RiskClass getManualIdentRisk(final IPhaseExecutor executor) {
        return IMESExceptionRecord.RiskClass.valueOf(MESChoiceListHelper.getChoiceElement("RiskClass", (long)((MESParamExceptionDef0300)executor.getProcessParameterData((Class)MESParamExceptionDef0300.class, "Identify manually")).getRiskAssessment()));
    }
}
