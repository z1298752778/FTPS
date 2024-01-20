package com.leateck.phase.accountalternativematerial0010;

/**
 * Represents an action listener for the basic events in the accounting views.
 * <p>
 * 
 * @author ikoleva, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
 */
public interface IMatAccountEventListener0710 {
    /**
     * Refresh was triggered
     */
    void onRefresh();

    /**
     * The account action was triggered
     */
    void onAccount();

    /**
     * Recording a warehouse error was triggered
     */
    void onWarehouseErrorRecordRequest();

    /**
     * @param enable whether the button should be enabled
     */
    void enableConfirmButton(boolean enable);

    /**
     * The done button was selected
     */
    void onDoneButtonPressed();

    /**
     * The continue button was selected
     */
    void onContinueButtonPressed();

    /**
     * The stop button was selected
     */
    void onStopButtonPressed();
}
