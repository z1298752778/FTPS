package com.leateck.phase.wdmaterialidentification0100;

import com.jgoodies.common.base.Objects;
import com.rockwell.mes.apps.ebr.ifc.swing.*;
import java.awt.event.*;
import javax.swing.Box;
import javax.swing.plaf.metal.*;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.commons.lang3.*;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rockwell.mes.clientfw.commons.ifc.swing.*;
import com.rockwell.mes.commons.base.ifc.exceptions.*;
import com.rockwell.mes.apps.ebr.ifc.phase.uiextensions.*;
import com.rockwell.mes.apps.ebr.ifc.phase.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import com.rockwell.mes.services.eqm.ifc.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import org.apache.commons.logging.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import javax.swing.event.*;
import com.datasweep.plantops.common.measuredvalue.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.services.recipe.ifc.weighing.*;
import com.rockwell.mes.commons.base.ifc.choicelist.*;

public class MatIdentView0610 extends WDOWAbstractWeighPhaseView0610<MatIdentModel0610>
{
    private static final String BATCH_SUBLOT_IDS = "BatchSublotIds";
    private static final String SOURCE_CONTAINER = "SourceContainer";
    private static final long serialVersionUID = 1L;
    protected static final Log LOGGER;
    public static final String MSG_PACK_MATIDENT = "wd_MaterialIdentification0610";
    private static final ImageIcon ICON;
    private static final ImageIcon RELEASE_SCALE_BUTTON_NORMAL_ICON;
    private static final ImageIcon RELEASE_SCALE_BUTTON_PRESSED_ICON;
    private MatIdentActiveGrid0610 materialPositionActiveGrid;
    private JButton confirmButton;
    private long currentIdentifiedOSIKey;
    private transient IPhaseCompletionSignatureUIExtension uiExtension;
    private static final String BATCH_SUBLOT_DISPLAY_STR_MSG_ID = "BatchSublotQuantity_DisplayString";
    private static final String BATCH_SUBLOT_CONTAINER_DISPLAY_STR_MSG_ID = "BatchSublotContainer_DisplayString";

    protected MatIdentView0610(final IDelegate0610<IWDMatIdentModel0610> delegate) {
        super((IDelegate0610)delegate);
    }

    protected JButton getConfirmButton() {
        return this.confirmButton;
    }

    protected void createUI() {
        this.setOpaque(false);
        switch (((IWDMatIdentModel0610)this.getModel()).getStatus()) {
            case PREVIEW: {
                this.setLayout((LayoutManager)new BorderLayout());
                final JPanel panel = PhaseSwingHelper.createPanel();
                this.confirmButton = this.configureInstructionPanel(panel, MatIdentView0610.ICON, true);
                this.add((Component)panel, (Object)"Center");
                break;
            }
            case ACTIVE: {
                this.createUIActive();
                break;
            }
            case COMPLETED: {
                this.createUICompleted();
                break;
            }
            default: {
                return;
            }
        }
        this.configureSignaturePanel();
    }

    private void createUIActive() {
        this.currentIdentifiedOSIKey = ((IWDMatIdentModel0610)this.getModel()).getIdentifiedOSIKey();
        if (((IWDMatIdentModel0610)this.getModel()).hasSublotBeenIdentified()) {
            this.createUIWithIdentificationResult(new ViewDataIdentificationResultForOSI(this.getCurrentIdentifiedOSI(), ((IWDMatIdentModel0610)this.getModel()).getCurrentSourceContainer()));
        }
        else if (((IWDMatIdentModel0610)this.getModel()).getReleaseScaleTriggered()) {
            this.createUIActiveForReleaseScale(null, new ViewDataIdentificationResultForOSI(this.getCurrentIdentifiedOSI(), ((IWDMatIdentModel0610)this.getModel()).getCurrentSourceContainer()));
        }
        else {
            this.createUIActiveShowingMaterialGrid();
        }
        this.configureConfirmButton();
    }

    OrderStepInput getCurrentIdentifiedOSI() {
        return ((IWDMatIdentModel0610)this.getModel()).getOSIByKey(this.currentIdentifiedOSIKey);
    }

    private void createUIActiveForReleaseScale(final Batch batch, final ViewDataIdentificationResultForOSI viewData) {
        final JPanel emptyUIForIdentificationResult = this.createEmptyUIForIdentificationResult(null);
        emptyUIForIdentificationResult.add(PhaseSwingHelper.createJLabelMedium((batch == null) ? FieldHolder.TARGET_CONTAINER_CLOSED_MSG : FieldHolder.DIFFERENT_BATCH_IDENTIFIED_MSG, "InstructionText1"));
        if (batch != null) {
            emptyUIForIdentificationResult.add(PhaseSwingHelper.createJLabelMedium(AbstractWeighView0610.getBatchName(batch), "BatchSublotIds"));
            this.addSourceContainerLabelIfNecessary(viewData, emptyUIForIdentificationResult);
        }
    }

    private void createUIActiveShowingMaterialGrid() {
        this.setLayout((LayoutManager)new BorderLayout());
        final JPanel panel = PhaseSwingHelper.createPanel();
        this.configureInstructionPanel(panel, MatIdentView0610.ICON, false);
        this.add((Component)panel, (Object)"North");
        this.add((Component)(this.materialPositionActiveGrid = MatIdentViewHelper0610.buildMatIdentActiveGrid((IWDMatIdentModel0610)this.getModel(), true)), (Object)"Center");
        this.materialPositionActiveGrid.addAncestorListener((AncestorListener)new RefreshMaterialGridListener());
        final JPanel panel2 = PhaseSwingHelper.createPanel(PhaseColumnLayout.Layout.LAYOUT_ICON_SINGLE_COLUMN, ((IWDMatIdentModel0610)this.getModel()).getStatus());
        this.add((Component)panel2, (Object)"South");
        this.confirmButton = (JButton)((PhaseColumnLayout)panel2.getLayout()).getConfirmButton();
        if (((IWDMatIdentModel0610)this.getModel()).getKeepSource()) {
            panel2.add(this.createReleaseScaleButton(), PhaseColumnLayout.Column.FIRST_COLUMN);
        }
    }

    private JToggleButton createReleaseScaleButton() {
        final JToggleButton val$releaseScaleBtn = new JToggleButton(MatIdentView0610.RELEASE_SCALE_BUTTON_NORMAL_ICON);
        val$releaseScaleBtn.setName("releaseScaleButton");
        val$releaseScaleBtn.setFocusPainted(false);
        final class TriggerReleaseButtonUI extends MetalToggleButtonUI
        {
            @Override
            protected void paintButtonPressed(final Graphics g, final AbstractButton b) {
            }
        }
        val$releaseScaleBtn.setUI(new TriggerReleaseButtonUI());
        val$releaseScaleBtn.setSelectedIcon(MatIdentView0610.RELEASE_SCALE_BUTTON_PRESSED_ICON);
        val$releaseScaleBtn.setOpaque(false);
        val$releaseScaleBtn.setBorder(BorderFactory.createEmptyBorder());
        final class TriggerReleaseButtonListener implements ActionListener
        {
            @Override
            public void actionPerformed(final ActionEvent event) {
                ((IWDMatIdentModel0610)MatIdentView0610.this.getModel()).setDoUserTriggeredReleaseScale(val$releaseScaleBtn.isSelected());
            }
        }
        val$releaseScaleBtn.addActionListener(new TriggerReleaseButtonListener());
        return val$releaseScaleBtn;
    }

    private void createUICompleted() {
        if (((IWDMatIdentModel0610)this.getModel()).isFallThrough()) {
            return;
        }
        ((IWDMatIdentModel0610)this.getModel()).getIdentificationResult().createCompletedUI(this);
    }

    void setLayoutForColumn(final JPanel column, final IViewDataIdentificationResult rtPhaseData) {
        column.setLayout(new GridLayout(2 + ((((rtPhaseData == null) ? null : rtPhaseData.getWeighingMethod()) != null) ? 1 : 0) + ((rtPhaseData != null && StringUtils.isNotEmpty((CharSequence)rtPhaseData.getSrcContainerId())) ? 1 : 0), 1, 0, 9));
    }

    JPanel createEmptyUIForIdentificationResult(final IViewDataIdentificationResult rtPhaseData) {
        this.setLayout((LayoutManager)new BorderLayout());
        final JPanel panel = PhaseSwingHelper.createPanel(PhaseColumnLayout.Layout.LAYOUT_ICON_TWO_1ST_WIDER_COLUMN, ((IWDMatIdentModel0610)this.getModel()).getStatus());
        panel.add(new JLabel(MatIdentView0610.ICON), PhaseColumnLayout.Column.FIRST_COLUMN);
        final JPanel panel2 = PhaseSwingHelper.createPanel();
        this.setLayoutForColumn(panel2, rtPhaseData);
        panel.add(panel2, PhaseColumnLayout.Column.SECOND_COLUMN);
        this.add((Component)panel, (Object)"Center");
        this.confirmButton = (JButton)((PhaseColumnLayout)panel.getLayout()).getConfirmButton();
        return panel2;
    }

    void createUIWithIdentificationResult(IViewDataIdentificationResult viewData) {
        final JPanel emptyUIForIdentificationResult = this.createEmptyUIForIdentificationResult(viewData);
        emptyUIForIdentificationResult.add(PhaseSwingHelper.createJLabelMedium(viewData.getMaterialIdentifier() + " " + viewData.getMaterialShortDescr(), "Material"));
        emptyUIForIdentificationResult.add(PhaseSwingHelper.createJLabelMedium(viewData.getBatchIdentifier() + "  " + viewData.getSublotIdentifier(), "BatchSublotIds"));
        this.addSourceContainerLabelIfNecessary(viewData, emptyUIForIdentificationResult);
        if (viewData.getWeighingMethod() != null) {
            emptyUIForIdentificationResult.add(PhaseSwingHelper.createJLabelMedium(viewData.getWeighingMethod(), "WeighingMethod"));
        }
        final JPanel panel = PhaseSwingHelper.createPanel();
        this.setLayoutForColumn(panel, viewData);
        emptyUIForIdentificationResult.getParent().add(panel, PhaseColumnLayout.Column.THIRD_COLUMN);
        panel.add(PhaseSwingHelper.createJLabelMedium((viewData.getNominalQuantity() == null) ? "" : viewData.getNominalQuantity().toString(), "Nominal"));
        panel.add(PhaseSwingHelper.createJLabelMedium((viewData.getActualPotency() == null) ? "" : viewData.getActualPotency().toString(), "Potency"));
        final String s = (viewData.getActualQuantity() == null) ? "" : viewData.getActualQuantity().toString();
        if (StringUtils.isNotEmpty((CharSequence)s)) {
            if (StringUtils.isNotEmpty((CharSequence)viewData.getSrcContainerId())) {
                panel.add(Box.createHorizontalStrut(0));
            }
            panel.add(PhaseSwingHelper.createJLabelMedium(s, "Weight"));
        }
    }

    private void addSourceContainerLabelIfNecessary(final IViewDataIdentificationResult viewData, final JPanel identifierColumn) {
        if (StringUtils.isNotEmpty((CharSequence)viewData.getSrcContainerId())) {
            identifierColumn.add(PhaseSwingHelper.createJLabelMedium(viewData.getSrcContainerId(), "SourceContainer"));
        }
    }

    void createUICompletedForReleaseScale(final IViewDataIdentificationResult viewData) {
        final JPanel emptyUIForIdentificationResult = this.createEmptyUIForIdentificationResult(viewData);
        final String batchIdentifier = viewData.getBatchIdentifier();
        emptyUIForIdentificationResult.add(PhaseSwingHelper.createJLabelMedium(StringUtils.isEmpty((CharSequence)batchIdentifier) ? FieldHolder.TARGET_CONTAINER_CLOSED_MSG : FieldHolder.DIFFERENT_BATCH_IDENTIFIED_MSG, "InstructionText1"));
        if (!StringUtils.isEmpty((CharSequence)batchIdentifier)) {
            emptyUIForIdentificationResult.add(PhaseSwingHelper.createJLabelMedium(batchIdentifier + "  " + viewData.getSublotIdentifier(), "BatchSublotIds"));
            this.addSourceContainerLabelIfNecessary(viewData, emptyUIForIdentificationResult);
        }
    }

    void recreateActiveUIIfBlocked() {
        if (((IWDMatIdentModel0610)this.getModel()).isBlocked() || ((IWDMatIdentModel0610)this.getModel()).isBlockedByWarehouseError() || ((IWDMatIdentModel0610)this.getModel()).isUndoIdentificationApplicable()) {
            if (Objects.equals(this.getCurrentIdentifiedOSI(), ((IWDMatIdentModel0610)this.getModel()).getIdentifiedOSI())) {
                return;
            }
            this.recreateActiveUI();
        }
    }

    void recreateActiveUI() {
        final boolean confirmButtonEnabled = this.isConfirmButtonEnabled();
        this.cleanUpConfirmButton();
        this.removeAll();
        this.createUIActive();
        this.configureSignaturePanel();
        this.enableConfirmButton(confirmButtonEnabled);
        final FocusTraversalPolicy focusTraversalPolicy = this.getFocusTraversalPolicy();
        if (focusTraversalPolicy instanceof SwingPhasePanelFocusTraversalPolicy) {
            ((SwingPhasePanelFocusTraversalPolicy)focusTraversalPolicy).setFocusDefaultComponent((JComponent)this.confirmButton);
        }
        StyleSheetUtility.style((JComponent)this);
    }

    public void busyPainter(final boolean busy) {
        super.busyPainter(busy);
        if (busy && this.materialPositionActiveGrid != null) {
            this.materialPositionActiveGrid.requestFocus();
        }
    }

    protected String getNavigatorInfoColumn() {
        if (((IWDMatIdentModel0610)this.getModel()).isFallThrough()) {
            return "---";
        }
        return ((((MESRtPhaseDataWDMatIdent0010)((IWDMatIdentModel0610)this.getModel()).getRtPhaseData()).getBatchIdentifier() != null) ? ((MESRtPhaseDataWDMatIdent0010)((IWDMatIdentModel0610)this.getModel()).getRtPhaseData()).getBatchIdentifier() : "-") + " / " + ((((MESRtPhaseDataWDMatIdent0010)((IWDMatIdentModel0610)this.getModel()).getRtPhaseData()).getMaterialIdentifier() != null) ? ((MESRtPhaseDataWDMatIdent0010)((IWDMatIdentModel0610)this.getModel()).getRtPhaseData()).getMaterialIdentifier() : "-");
    }

    public void refreshActiveGrid() {
        MatIdentViewHelper0610.refreshMaterialGrid((MESStandardGrid)this.materialPositionActiveGrid, (IWDMatIdentModel0610)this.getModel(), BOMPositionType0610.ANY);
    }

    public OrderStepInput getSelectedOsiInGrid() {
        final MatIdentRow0610 matIdentRow0610 = (MatIdentRow0610)this.materialPositionActiveGrid.getSelectedRowObject();
        if (matIdentRow0610 != null) {
            return matIdentRow0610.getOSI();
        }
        return null;
    }

    public void configureSignaturePanel() {
        int n = 0;
        switch (((IWDMatIdentModel0610)this.getModel()).getStatus()) {
            case PREVIEW: {
                n = 1;
                break;
            }
            case ACTIVE: {
                n = ((((IWDMatIdentModel0610)this.getModel()).hasSublotBeenIdentified() && ((IWDMatIdentModel0610)this.getModel()).isIdentificationOnly(((IWDMatIdentModel0610)this.getModel()).getIdentifiedOSI())) ? 1 : 0);
                break;
            }
            case COMPLETED: {
                n = ((((MESRtPhaseDataWDMatIdent0010)((IWDMatIdentModel0610)this.getModel()).getRtPhaseData()).getWeighingMethod() != null) ? 1 : 0);
                break;
            }
            default: {
                throw new MESRuntimeException("Undefined phase state" + ((IWDMatIdentModel0610)this.getModel()).getStatus());
            }
        }
        if (n != 0) {
            this.addCompletionSignaturePanel();
        }
        else {
            this.removeCompletionSignaturePanel();
        }
    }

    private void addCompletionSignaturePanel() {
        boolean b = false;
        if (this.uiExtension == null) {
            this.uiExtension = PhaseUIExtensionsHelper.addPhaseCompletionSignatureUIExtension((IPhaseExecutor)this.getDelegateMatIdent().getExecutor());
            b = true;
            if (this.uiExtension.getSignaturePanel() == null) {
                this.uiExtension = null;
            }
        }
        if (this.uiExtension != null) {
            this.add((Component)this.uiExtension.getSignaturePanel(), (Object)"South");
            if (!b && ((IWDMatIdentModel0610)this.getModel()).isPhaseActive()) {
                PhaseUIExtensionsHelper.recreateFocusTraversalPolicy((IPhaseExecutor)this.getDelegateMatIdent().getExecutor(), this.uiExtension);
            }
        }
    }

    private void removeCompletionSignaturePanel() {
        if (this.uiExtension != null) {
            PhaseUIExtensionsHelper.removePhaseCompletionSignatureUIExtension(this.uiExtension, (IPhaseExecutor)this.getDelegateMatIdent().getExecutor(), (JPanel)this);
            this.uiExtension = null;
        }
    }

    private RtPhaseExecutorWDMatIdent0010.IDelegateWDMatIdent0610 getDelegateMatIdent() {
        final IDelegate0610 delegate = this.getDelegate();
        if (delegate instanceof RtPhaseExecutorWDMatIdent0010.IDelegateWDMatIdent0610) {
            return (RtPhaseExecutorWDMatIdent0010.IDelegateWDMatIdent0610)delegate;
        }
        throw new IllegalStateException("Wrong type of delegate. " + delegate);
    }

    static String getDisplayStringForMasterOSI(final Sublot sublot) {
        final String sourceContainerNameOfSublot = getSourceContainerNameOfSublot(sublot);
        if (StringUtils.isEmpty((CharSequence)sourceContainerNameOfSublot)) {
            return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "BatchSublotQuantity_DisplayString", (Object[])new String[] { AbstractWeighView0610.getBatchName(sublot.getBatch()), AbstractWeighView0610.getSublotName(sublot) });
        }
        return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "BatchSublotContainer_DisplayString", (Object[])new String[] { AbstractWeighView0610.getBatchName(sublot.getBatch()), AbstractWeighView0610.getSublotName(sublot), sourceContainerNameOfSublot });
    }

    static String getDisplayStringForSplitOSI(final Sublot sublot, final IMESContainerEquipment sourceContainer) {
        if (sourceContainer == null) {
            return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "BatchSublotQuantity_DisplayString", (Object[])new String[] { AbstractWeighView0610.getBatchName(sublot.getBatch()), AbstractWeighView0610.getSublotName(sublot) });
        }
        return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "BatchSublotContainer_DisplayString", (Object[])new String[] { AbstractWeighView0610.getBatchName(sublot.getBatch()), AbstractWeighView0610.getSublotName(sublot), sourceContainer.getName() });
    }

    private static String getSourceContainerNameOfSublot(final Sublot sublot) {
        final IMESContainerEquipment containerForSublot = ((IMESContainerEquipmentService)ServiceFactory.getService((Class)IMESContainerEquipmentService.class)).findContainerForSublot(sublot);
        if (containerForSublot == null) {
            return "";
        }
        return containerForSublot.getName();
    }

    static String getDisplayStringForMasterOSI(final List<Sublot> sublots) {
        ArrayList var1 = new ArrayList(sublots.size());
        Iterator var2 = sublots.iterator();

        while(var2.hasNext()) {
            Sublot var3 = (Sublot)var2.next();
            var1.add(getDisplayStringForMasterOSI(var3));
        }

        return StringUtils.join(var1, StringConstants.LINE_BREAK);
    }

    static {
        LOGGER = LogFactory.getLog((Class)MatIdentView0610.class);
        ICON = PCContext.getFunctions().getImage("ebr_wd_MaterialIdent0610");
        RELEASE_SCALE_BUTTON_NORMAL_ICON = PCContext.getFunctions().getImage("ebr_wd_ReleaseScale_Button_Normal0610");
        RELEASE_SCALE_BUTTON_PRESSED_ICON = PCContext.getFunctions().getImage("ebr_wd_ReleaseScale_Button_Pressed0610");
    }

    private static class FieldHolder
    {
        private static final String DIFFERENT_BATCH_IDENTIFIED_MSG;
        private static final String TARGET_CONTAINER_CLOSED_MSG;

        static {
            DIFFERENT_BATCH_IDENTIFIED_MSG = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "differentBatchWasScannedTxt");
            TARGET_CONTAINER_CLOSED_MSG = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "targetContainerClosedTxt");
        }
    }

    private class RefreshMaterialGridListener implements AncestorListener
    {
        @Override
        public void ancestorAdded(final AncestorEvent event) {
            MatIdentView0610.this.refreshActiveGrid();
        }

        @Override
        public void ancestorRemoved(final AncestorEvent event) {
        }

        @Override
        public void ancestorMoved(final AncestorEvent event) {
        }
    }

    static class ViewDataIdentificationResultForOSI implements IViewDataIdentificationResult
    {
        private final OrderStepInput osiWithIdentifiedSublot;
        private final IMESContainerEquipment sourceContainer;

        public ViewDataIdentificationResultForOSI(final OrderStepInput osi, final IMESContainerEquipment theSourceContainer) {
            this.osiWithIdentifiedSublot = osi;
            this.sourceContainer = theSourceContainer;
        }

        private Sublot getSublot() {
            return this.osiWithIdentifiedSublot.getAttachSublot();
        }

        @Override
        public String getSublotIdentifier() {
            return AbstractWeighView0610.getSublotName(this.getSublot());
        }

        @Override
        public String getSrcContainerId() {
            return (this.sourceContainer != null) ? this.sourceContainer.getName() : "";
        }

        @Override
        public String getBatchIdentifier() {
            return AbstractWeighView0610.getBatchName(this.getSublot().getBatch());
        }

        @Override
        public String getMaterialIdentifier() {
            return this.getSublot().getBatch().getPart().getPartNumber();
        }

        @Override
        public String getMaterialShortDescr() {
            return WDHelper0610.getMaterialShortDescription(this.getSublot().getBatch().getPart());
        }

        @Override
        public IMeasuredValue getActualPotency() {
            return (IMeasuredValue)MESNamedUDAOrderStepInput.getActualPotency(this.osiWithIdentifiedSublot);
        }

        @Override
        public IMeasuredValue getNominalQuantity() {
            if (WDOSIServiceHelper0610.isDynamicAsProduced(this.osiWithIdentifiedSublot) || WDOSIServiceHelper0610.isPlannedQtyModeNone(this.osiWithIdentifiedSublot)) {
                return null;
            }
            return (IMeasuredValue)MESNamedUDAOrderStepInput.getNominalQuantity(this.osiWithIdentifiedSublot);
        }

        @Override
        public IMeasuredValue getActualQuantity() {
            if (EnumWeighingMethods.IDENTIFICATION_ONLY.equals(MESNamedUDAOrderStepInput.getWeighingMethod(this.osiWithIdentifiedSublot)) && this.osiWithIdentifiedSublot.getActualQuantity() == null) {
                return IdOnlyWeighingMethodWDMatIdent0610.getQuantityOfSublot(this.osiWithIdentifiedSublot);
            }
            return (IMeasuredValue)this.osiWithIdentifiedSublot.getActualQuantity();
        }

        @Override
        public String getWeighingMethod() {
            final IMESChoiceElement weighingMethod = MESNamedUDAOrderStepInput.getWeighingMethod(this.osiWithIdentifiedSublot);
            if (weighingMethod == null) {
                return null;
            }
            return weighingMethod.getLocalizedMessage();
        }
    }

    public interface IViewDataIdentificationResult
    {
        String getSublotIdentifier();

        String getBatchIdentifier();

        String getSrcContainerId();

        String getMaterialIdentifier();

        String getMaterialShortDescr();

        IMeasuredValue getActualPotency();

        IMeasuredValue getNominalQuantity();

        IMeasuredValue getActualQuantity();

        String getWeighingMethod();
    }
}
