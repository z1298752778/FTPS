package com.leateck.phase.wdmaterialidentification0100;

import javax.swing.*;
import java.awt.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.shared.product.wd.*;
import java.util.*;
import java.util.List;

import com.rockwell.mes.commons.base.ifc.exceptions.*;

public class MatIdentActionView0610 extends WDOWAbstractWeighActionView0610<MatIdentModel0610>
{
    private static final long serialVersionUID = 1L;

    public MatIdentActionView0610(final IDelegate0610<MatIdentModel0610> delegate) {
        super((IDelegate0610)delegate);
    }

    protected void createActionPanel(final int actionIndex) {
        this.setLayout((LayoutManager)new BoxLayout((Container)this, 1));
        switch (actionIndex) {
            case 0: {
                final Vector<Sublot> vector = new Vector<Sublot>();
                vector.add(((IWDMatIdentModel0610)this.getModel()).getPrintedSublot());
                ActionSublotHelper0610.createActionPanels4PrintSublot((AbstractWeighActionView0610)this, (List)vector);
                ActionSublotHelper0610.createActionPanels4ReplaceSublot((AbstractWeighActionView0610)this, (List)vector);
            }
            default: {
                throw new MESRuntimeException("unsupported actionIndex");
            }
        }
    }

    protected List<String> getActionButtonTexts() {
        final Vector<String> vector = new Vector<String>();
        final Sublot printedSublot = ((IWDMatIdentModel0610)this.getModel()).getPrintedSublot();
        if (printedSublot != null) {
            vector.add(printedSublot.getName());
        }
        return vector;
    }
}
