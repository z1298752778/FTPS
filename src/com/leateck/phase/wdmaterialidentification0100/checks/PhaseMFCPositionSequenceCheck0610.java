package com.leateck.phase.wdmaterialidentification0100.checks;

import com.rockwell.mes.shared.product.wd.checks.*;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.shared.phase.matident.check.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import java.util.function.*;
import java.util.*;
import com.rockwell.mes.services.inventory.ifc.checks.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import java.util.stream.*;
import com.rockwell.mes.services.wd.ifc.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;

public class PhaseMFCPositionSequenceCheck0610 extends AbstractPhaseIdentificationCheckWD0610<IIdentifiedItem0610>
{
    private static final String MSG_ID_CHECK_MFC_POSITION_SEQUENCE_EXCEPTION = "CheckMFCPositionSequence_ExceptionTxt";
    private static final String MSG_ID_CHECK_MFC_POSITION_SEQUENCE_PREDECESSORS_NOT_COMPLETED_EXCEPTION = "CheckMFCPositionSequence_PredecessorsNotCompleted";
    private static final String MSG_ID_CHECK_MFC_POSITION_SEQUENCE_SUCCESSORS_ALREADY_STARTED_EXCEPTION = "CheckMFCPositionSequence_SuccessorsAlreadyStarted";

    public PhaseMFCPositionSequenceCheck0610(final AbstractPhaseIdentificationCheck0610.CheckType argType, final String argCheckKey, final PhaseCheckConfiguration0610 checkConfig, final Map<String, Object> argContext) {
        super(argType, (String)null, argCheckKey, checkConfig, (List)null, (Map)argContext);
    }

    public IIdentificationCheck createIdentificationCheck() {
        this.setIdentificationCheck((IIdentificationCheck)new CheckMFCPositionSequence((List)this.sortBySequence(PhaseIdentificationCheckSuite0610.getMasterOSIsSortedFromContext(this))));
        return this.getIdentificationCheck();
    }

    private List<OrderStepInput> sortBySequence(final List<OrderStepInput> masterOSIsSortedByPosition) {
        if (!masterOSIsSortedByPosition.isEmpty() && !this.isSequenceDefinedByPosition((OrderStepInput)masterOSIsSortedByPosition.get(0))) {
            ArrayList var2 = new ArrayList(masterOSIsSortedByPosition);
            var2.sort(Comparator.comparingLong(MESNamedUDAOrderStepInput::getWeighingSequence));
            return var2;
        } else {
            return masterOSIsSortedByPosition;
        }
    }

    private boolean isSequenceDefinedByPosition(final OrderStepInput orderStepInput) {
        return MESNamedUDAOrderStepInput.getWeighingSequence(orderStepInput) == null;
    }

    public String getExtendedMessage(final IIdentifiedItem0610 item, final Collection<String> exceptionsFromCheck) {
        final StringBuilder sb = new StringBuilder(this.getMessage());
        final Iterator<String> iterator = exceptionsFromCheck.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
        }
        return sb.toString();
    }

    static /* synthetic */ String access$100(final PhaseMFCPositionSequenceCheck0610 phaseMFCPositionSequenceCheck0610) {
        return phaseMFCPositionSequenceCheck0610.getMessagePack();
    }

    static /* synthetic */ String access$200(final PhaseMFCPositionSequenceCheck0610 phaseMFCPositionSequenceCheck0610) {
        return phaseMFCPositionSequenceCheck0610.getMessagePack();
    }

    static /* synthetic */ String access$300(final PhaseMFCPositionSequenceCheck0610 phaseMFCPositionSequenceCheck0610) {
        return phaseMFCPositionSequenceCheck0610.getMessagePack();
    }

    private final class CheckMFCPositionSequence extends AbstractIdentificationCheck
    {
        private final List<OrderStepInput> masterOSIsSorted;
        private final OrderStepInputNumberComparator comparator;

        private CheckMFCPositionSequence(final List<OrderStepInput> masterOSIsSortedParam) {
            this.masterOSIsSorted = masterOSIsSortedParam;
            this.comparator = OrderStepInputComparatorFactory.WEIGHING_SEQUENCE.create((Collection)this.masterOSIsSorted);
        }

        public void performCheck(final IExecuteCheckParameter parameterObject) {
            final OrderStepInput orderStepInput = ((ExecuteCheckParameter)parameterObject).getOrderStepInput();
            final OrderStepInput masterOSIForOSI = ((IWDOrderStepInputService)ServiceFactory.getService((Class)IWDOrderStepInputService.class)).getMasterOSIForOSI(orderStepInput);
            final int index = this.masterOSIsSorted.indexOf(masterOSIForOSI);
            final List<OrderStepInput> predecessorOrderStepInputs = this.getPredecessorOrderStepInputs(masterOSIForOSI, this.masterOSIsSorted.subList(0, index));
            final List<OrderStepInput> successorOrderStepInputs = this.getSuccessorOrderStepInputs(masterOSIForOSI, this.masterOSIsSorted.subList(index + 1, this.masterOSIsSorted.size()));
            final boolean checkPredecessorPositionsInACompletedStateOrAbortedOrCanceled = this.checkPredecessorPositionsInACompletedStateOrAbortedOrCanceled(predecessorOrderStepInputs);
            final boolean checkSuccessorPositionsNotStartedOrAbortedOrCanceled = this.checkSuccessorPositionsNotStartedOrAbortedOrCanceled(successorOrderStepInputs);
            if (!checkPredecessorPositionsInACompletedStateOrAbortedOrCanceled || !checkSuccessorPositionsNotStartedOrAbortedOrCanceled) {
                this.getExceptionList().add(this.createExceptionText(orderStepInput, checkPredecessorPositionsInACompletedStateOrAbortedOrCanceled, checkSuccessorPositionsNotStartedOrAbortedOrCanceled));
            }
        }

        private List<OrderStepInput> getPredecessorOrderStepInputs(OrderStepInput selectedMasterOSI, List<OrderStepInput> candidates) {
            return candidates.stream().filter((osi) -> {
                return this.comparator.compare(osi, selectedMasterOSI) < 0;
            }).collect(Collectors.toList());
        }

        private List<OrderStepInput> getSuccessorOrderStepInputs(OrderStepInput selectedMasterOSI, List<OrderStepInput> candidates) {
            return candidates.stream().filter((osi) -> {
                return this.comparator.compare(osi, selectedMasterOSI) > 0;
            }).collect(Collectors.toList());
        }

        private boolean checkPredecessorPositionsInACompletedStateOrAbortedOrCanceled(final List<OrderStepInput> predecessorMasterOSIs) {
            ArrayList var2 = new ArrayList<>();
            var2.addAll(OSIPositionStatus.COMPLETED_STATES);
            var2.add(OSIPositionStatus.ABORTED);
            var2.add(OSIPositionStatus.CANCELED);
            return this.checkOSIsHaveAllowedState(predecessorMasterOSIs, var2);
        }

        private boolean checkSuccessorPositionsNotStartedOrAbortedOrCanceled(final List<OrderStepInput> successorMasterOSIs) {
            final ArrayList<OSIPositionStatus> allowedStates = new ArrayList<OSIPositionStatus>();
            allowedStates.add(OSIPositionStatus.NOT_STARTED);
            allowedStates.add(OSIPositionStatus.ABORTED);
            allowedStates.add(OSIPositionStatus.CANCELED);
            return this.checkOSIsHaveAllowedState(successorMasterOSIs, allowedStates);
        }

        private boolean checkOSIsHaveAllowedState(final List<OrderStepInput> masterOSIs, final List<OSIPositionStatus> allowedStates) {
            final Iterator<OrderStepInput> iterator = masterOSIs.iterator();
            while (iterator.hasNext()) {
                if (!allowedStates.contains(OSIPositionStatus.get((OrderStepInput)iterator.next()))) {
                    return false;
                }
            }
            return true;
        }

        private String createExceptionText(final OrderStepInput selectedOSI, final boolean checkPredecessorsOk, final boolean checkSuccessorsOk) {
            final StringBuilder sb = new StringBuilder();
            sb.append(StringConstants.LINE_BREAK);
            sb.append(I18nMessageUtility.getLocalizedMessage(PhaseMFCPositionSequenceCheck0610.access$100(PhaseMFCPositionSequenceCheck0610.this), "CheckMFCPositionSequence_ExceptionTxt", new Object[] { MESNamedUDAOrderStepInput.getPosition(selectedOSI) }));
            if (!checkPredecessorsOk) {
                sb.append(StringConstants.LINE_BREAK);
                sb.append(I18nMessageUtility.getLocalizedMessage(PhaseMFCPositionSequenceCheck0610.access$200(PhaseMFCPositionSequenceCheck0610.this), "CheckMFCPositionSequence_PredecessorsNotCompleted"));
            }
            if (!checkSuccessorsOk) {
                sb.append(StringConstants.LINE_BREAK);
                sb.append(I18nMessageUtility.getLocalizedMessage(PhaseMFCPositionSequenceCheck0610.access$300(PhaseMFCPositionSequenceCheck0610.this), "CheckMFCPositionSequence_SuccessorsAlreadyStarted"));
            }
            return sb.toString();
        }
    }
}
