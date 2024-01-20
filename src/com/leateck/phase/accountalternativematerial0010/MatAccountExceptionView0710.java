package com.leateck.phase.accountalternativematerial0010;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.rockwell.mes.apps.ebr.ifc.phase.ui.PhaseExceptionPanelUI;
import com.rockwell.mes.apps.ebr.ifc.swing.ConfirmButton;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseColumnLayout;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseSwingHelper;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.shared.phase.mvc.AbstractPhaseExceptionView0200;
import com.rockwell.mes.commons.shared.phase.mvc.PhaseViewHelper0200;
import com.rockwell.mes.shared.product.material.MaterialModel0710;

/**
 * Exception view for material accounting phase (as part of MVC structure)
 * 
 * @author mnikiforov, (c) Copyright 2018 Rockwell Automation Technologies, Inc. All Rights Reserved.
 */
public class MatAccountExceptionView0710 extends AbstractPhaseExceptionView0200<MatAccountModel0710> {
    private static final long serialVersionUID = 1L;

    private static final String RECORD_WAREHOUSE_ERROR_LABEL = "recordWarehouseErrorLabel";

    private static final String PANEL_RECORD_WAREHOUSE_ERROR_LABEL = RECORD_WAREHOUSE_ERROR_LABEL + "Panel";

    private static final String LABEL_RECORD_WAREHOUSE_ERROR_LABEL = RECORD_WAREHOUSE_ERROR_LABEL + "Label";

    private transient IMatAccountEventListener0710 eventListener;

    /**
     * @param theModel the phase model
     * @param argListener the listener that should react to events from this view
     */
    public MatAccountExceptionView0710(MatAccountModel0710 theModel, IMatAccountEventListener0710 argListener) {
        super(theModel);
        eventListener = argListener;
    }
    
    @Override
    public void createUI() {
        setOpaque(false);
        setEnabled(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        final boolean isBlocked = getModel().isBlockedByWarehouseError();

        JPanel recordWarehouseErrorPanel = createRecordWarehouseErrorPanel(RtPhaseExecutorMatAlterAcct0010.RECORD_WAREHOUSE_ERROR_EVENT);
        if (!isBlocked) {
            PhaseViewHelper0200.disableComponentTree(recordWarehouseErrorPanel);
        }
        add(recordWarehouseErrorPanel);
    }

    /**
     * @return panel to record a warehouse error
     */
    private JPanel createRecordWarehouseErrorPanel(final String controlName) {
        JPanel recordWarehouseErrorPanel =
                PhaseSwingHelper.createExceptionPanel(PhaseColumnLayout.Layout.LAYOUT_TWO_1ST_WIDER_COLUMN);
        recordWarehouseErrorPanel.setName(PANEL_RECORD_WAREHOUSE_ERROR_LABEL); // for Marathon
        recordWarehouseErrorPanel.setUI(new PhaseExceptionPanelUI(recordWarehouseErrorPanel));

        String recordWarehouseErrorLabelText =
                I18nMessageUtility.getLocalizedMessage(MaterialModel0710.PHASE_PRODUCT_MATERIAL_MSGPACK, "RecordWarehouseError_Label");
        recordWarehouseErrorPanel.add(PhaseSwingHelper.createMultiLineLabel(recordWarehouseErrorLabelText,
                PhaseColumnLayout.LEFT_COLUMN_WIDTH + PhaseColumnLayout.MIDDLE_COLUMN_WIDTH + PhaseColumnLayout.RIGHT_COLUMN_WIDTH,
                LABEL_RECORD_WAREHOUSE_ERROR_LABEL), PhaseColumnLayout.Column.FIRST_COLUMN);

        ConfirmButton confirmButton = ((PhaseColumnLayout) recordWarehouseErrorPanel.getLayout()).getConfirmButton();
        confirmButton.addActionListener(e -> eventListener.onWarehouseErrorRecordRequest());
        confirmButton.setName("button" + controlName);

        return recordWarehouseErrorPanel;
    }
}
