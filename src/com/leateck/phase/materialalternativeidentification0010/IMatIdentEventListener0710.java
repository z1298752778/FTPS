package com.leateck.phase.materialalternativeidentification0010;

/**
 * Represents an action listener for the basic events in the identification views.
 * <p>
 * 
 * @author ikoleva, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
 */
public interface IMatIdentEventListener0710 {
    /**
     * The identification action was triggered from the execution view (e.g. via barcode scan)
     */
    void onIdent();

    /**
     * Manual identification was performed (user-triggered exception)
     * 
     * @param itemId the string entered for manual identify
     */
    void onManualIdent(String itemId);

    /**
     * Undo identification was performed (user-triggered exception)
     * 
     * @param itemId the string entered for manual identify
     */
    void onUndoIdent(String itemId);

    /**
     * Additional identification was performed (post-processing action)
     * 
     * @param itemId the string entered for manual identify
     */
    void onAdditionalIdent(String itemId);

    /**
     * Undo identification was performed (post-processing action)
     * 
     * @param itemId the string entered for manual identify
     */
    void onUndoIdentAction(String itemId);

    /** Recording a warehouse error was triggered */
    void onWarehouseErrorRecordRequest();

    /**
     * Force Completion was triggered
     */
    void onForceCompletion();

    /**
     * @param enable whether the button should be enabled
     */
    void enableConfirmButton(boolean enable);

    /**
     * on refresh
     */
    void onRefresh();

    /**
     * on done button has been selected
     */
    void onDoneButtonPressed();

    /**
     * on sotp button has been selected
     */
    void onStopButtonPressed();

    /**
     * on continue button has been selected
     */
    void onContinueButtonPressed();
}
