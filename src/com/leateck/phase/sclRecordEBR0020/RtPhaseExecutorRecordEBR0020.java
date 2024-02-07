package com.leateck.phase.sclRecordEBR0020;

import com.datasweep.compatibility.client.ActivitySetStep;
import com.datasweep.compatibility.client.MeasuredValue;
import com.datasweep.compatibility.client.ProcessOrderItem;
import com.datasweep.compatibility.client.Response;
import com.leateck.phase.sclRecordEBR0020.AT.MESSCLIFRecordEBRData;
import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseCompleter;
import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseExecutor;
import com.rockwell.mes.apps.ebr.ifc.swing.AbstractPhaseExecutorSwing;
import com.rockwell.mes.apps.ebr.ifc.swing.ConfirmButton;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseColumnLayout;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseSwingHelper;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.commons.parameter.instruction.MESParamInstruction0300;
import com.rockwell.mes.commons.parameter.keyvaluemeasuredvalue.MESParamKeyValMVal0100;
import com.rockwell.mes.commons.parameter.keyvaluestring.MESParamKeyValString0100;
import com.rockwell.mes.commons.parameter.phasecompletionmode.MESParamCompletionMode0200;
import com.rockwell.mes.commons.parameter.phasecompletionmode.PhaseCompletionMode0200;
import com.rockwell.mes.services.s88.ifc.execution.IMESRtPhase;
import com.rockwell.mes.services.s88.ifc.recipe.IMESPhase;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RtPhaseExecutorRecordEBR0020 extends AbstractPhaseExecutorSwing {
    String instructionString = ((MESParamInstruction0300)getProcessParameterData(MESParamInstruction0300.class, "Instruction")).getColumn1();

    private MeasuredValue mv01;

    private MeasuredValue mv02;

    private MeasuredValue mv03;

    private MeasuredValue mv04;

    private MeasuredValue mv05;

    private MeasuredValue mv06;

    private MeasuredValue mv07;

    private MeasuredValue mv08;

    private MeasuredValue mv09;

    private MeasuredValue mv10;

    private MeasuredValue mv11;

    private MeasuredValue mv12;

    private MeasuredValue mv13;

    private MeasuredValue mv14;

    private MeasuredValue mv15;

    private MeasuredValue mv16;

    private MeasuredValue mv17;

    private MeasuredValue mv18;

    private MeasuredValue mv19;

    private MeasuredValue mv20;

    private String string1;

    private String string2;

    private String string3;

    private String string4;

    private String string5;

    private String string6;

    private String string7;

    private String string8;

    private String string9;

    private String string10;

    private String string11;

    private String string12;

    private String string13;

    private String string14;

    private String string15;

    private String string16;

    private String string17;

    private String string18;

    private String string19;

    private String string20;

    private String measuredKey1;

    private String measuredKey2;

    private String measuredKey3;

    private String measuredKey4;

    private String measuredKey5;

    private String measuredKey6;

    private String measuredKey7;

    private String measuredKey8;

    private String measuredKey9;

    private String measuredKey10;

    private String measuredKey11;

    private String measuredKey12;

    private String measuredKey13;

    private String measuredKey14;

    private String measuredKey15;

    private String measuredKey16;

    private String measuredKey17;

    private String measuredKey18;

    private String measuredKey19;

    private String measuredKey20;

    private String stringKey1;

    private String stringKey2;

    private String stringKey3;

    private String stringKey4;

    private String stringKey5;

    private String stringKey6;

    private String stringKey7;

    private String stringKey8;

    private String stringKey9;

    private String stringKey10;

    private String stringKey11;

    private String stringKey12;

    private String stringKey13;

    private String stringKey14;

    private String stringKey15;

    private String stringKey16;

    private String stringKey17;

    private String stringKey18;

    private String stringKey19;

    private String stringKey20;

    Boolean isAutoComplete = Boolean.valueOf(isModeAutoComplete());

    private String STRING_ZERO = "0";

    private String STRING_NULL = "";

    private boolean isModeAutoComplete() {
        MESParamCompletionMode0200 param = (MESParamCompletionMode0200)getProcessParameterData(MESParamCompletionMode0200.class, "Completion Mode");
        PhaseCompletionMode0200 mode = param.getPhaseCompletionMode();
        return PhaseCompletionMode0200.AUTO_COMPLETE.equals(mode);
    }

    public RtPhaseExecutorRecordEBR0020(IPhaseCompleter inPhaseCompleter, IMESRtPhase inRtPhase) {
        super(inPhaseCompleter, inRtPhase);
    }

    public RtPhaseExecutorRecordEBR0020(IMESPhase inPhase, ActivitySetStep inStep) {
        super(inPhase, inStep);
    }

    public JComponent createPhaseComponent() {
        this.instructionString = ((MESParamInstruction0300)getProcessParameterData(MESParamInstruction0300.class, "Instruction")).getColumn1();
        this.isAutoComplete = Boolean.valueOf(isModeAutoComplete());
        JPanel layoutPanel = PhaseSwingHelper.createPanel(PhaseColumnLayout.Layout.LAYOUT_SINGLE_COLUMN, getStatus());
        ConfirmButton confirmButton = ((PhaseColumnLayout)layoutPanel.getLayout()).getConfirmButton();
        JLabel instructionLabel = PhaseSwingHelper.createMultiLineLabel(this.instructionString, 794);
        layoutPanel.add(instructionLabel, PhaseColumnLayout.Column.FIRST_COLUMN);
        class ConfirmButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                RtPhaseExecutorRecordEBR0020.this.getPhaseCompleter().completePhase();
            }
        };
        if (getStatus() == IPhaseExecutor.Status.ACTIVE) {
            MESParamKeyValMVal0100 data01 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue1");
            this.mv01 = data01.getDataValue();
            this.measuredKey1 = data01.getDataKey();
            MESParamKeyValMVal0100 data02 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue2");
            this.mv02 = data02.getDataValue();
            this.measuredKey2 = data02.getDataKey();
            MESParamKeyValMVal0100 data03 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue3");
            this.mv03 = data03.getDataValue();
            this.measuredKey3 = data03.getDataKey();
            MESParamKeyValMVal0100 data04 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue4");
            this.mv04 = data04.getDataValue();
            this.measuredKey4 = data04.getDataKey();
            MESParamKeyValMVal0100 data05 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue5");
            this.mv05 = data05.getDataValue();
            this.measuredKey5 = data05.getDataKey();
            MESParamKeyValMVal0100 data06 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue6");
            this.mv06 = data06.getDataValue();
            this.measuredKey6 = data06.getDataKey();
            MESParamKeyValMVal0100 data07 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue7");
            this.mv07 = data07.getDataValue();
            this.measuredKey7 = data07.getDataKey();
            MESParamKeyValMVal0100 data08 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue8");
            this.mv08 = data08.getDataValue();
            this.measuredKey8 = data08.getDataKey();
            MESParamKeyValMVal0100 data09 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue9");
            this.mv09 = data09.getDataValue();
            this.measuredKey9 = data09.getDataKey();
            MESParamKeyValMVal0100 data10 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue10");
            this.mv10 = data10.getDataValue();
            this.measuredKey10 = data10.getDataKey();
            MESParamKeyValMVal0100 data11 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue11");
            this.mv11 = data11.getDataValue();
            this.measuredKey11 = data11.getDataKey();
            MESParamKeyValMVal0100 data12 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue12");
            this.mv12 = data12.getDataValue();
            this.measuredKey12 = data12.getDataKey();
            MESParamKeyValMVal0100 data13 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue13");
            this.mv13 = data13.getDataValue();
            this.measuredKey13 = data13.getDataKey();
            MESParamKeyValMVal0100 data14 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue14");
            this.mv14 = data14.getDataValue();
            this.measuredKey14 = data14.getDataKey();
            MESParamKeyValMVal0100 data15 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue15");
            this.mv15 = data15.getDataValue();
            this.measuredKey15 = data15.getDataKey();
            MESParamKeyValMVal0100 data16 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue16");
            this.mv16 = data16.getDataValue();
            this.measuredKey16 = data16.getDataKey();
            MESParamKeyValMVal0100 data17 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue17");
            this.mv17 = data17.getDataValue();
            this.measuredKey17 = data17.getDataKey();
            MESParamKeyValMVal0100 data18 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue18");
            this.mv18 = data18.getDataValue();
            this.measuredKey18 = data18.getDataKey();
            MESParamKeyValMVal0100 data19 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue19");
            this.mv19 = data19.getDataValue();
            this.measuredKey19 = data19.getDataKey();
            MESParamKeyValMVal0100 data20 = (MESParamKeyValMVal0100)getProcessParameterData(MESParamKeyValMVal0100.class, "MeasuredValue20");
            this.mv20 = data20.getDataValue();
            this.measuredKey20 = data20.getDataKey();
            MESParamKeyValString0100 dataStr1 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String1");
            this.string1 = dataStr1.getDataValue();
            this.stringKey1 = dataStr1.getDataKey();
            MESParamKeyValString0100 dataStr2 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String2");
            this.string2 = dataStr2.getDataValue();
            this.stringKey2 = dataStr2.getDataKey();
            MESParamKeyValString0100 dataStr3 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String3");
            this.string3 = dataStr3.getDataValue();
            this.stringKey3 = dataStr3.getDataKey();
            MESParamKeyValString0100 dataStr4 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String4");
            this.string4 = dataStr4.getDataValue();
            this.stringKey4 = dataStr4.getDataKey();
            MESParamKeyValString0100 dataStr5 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String5");
            this.string5 = dataStr5.getDataValue();
            this.stringKey5 = dataStr5.getDataKey();
            MESParamKeyValString0100 dataStr6 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String6");
            this.string6 = dataStr6.getDataValue();
            this.stringKey6 = dataStr6.getDataKey();
            MESParamKeyValString0100 dataStr7 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String7");
            this.string7 = dataStr7.getDataValue();
            this.stringKey7 = dataStr7.getDataKey();
            MESParamKeyValString0100 dataStr8 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String8");
            this.string8 = dataStr8.getDataValue();
            this.stringKey8 = dataStr8.getDataKey();
            MESParamKeyValString0100 dataStr9 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String9");
            this.string9 = dataStr9.getDataValue();
            this.stringKey9 = dataStr9.getDataKey();
            MESParamKeyValString0100 dataStr10 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String10");
            this.string10 = dataStr10.getDataValue();
            this.stringKey10 = dataStr10.getDataKey();
            MESParamKeyValString0100 dataStr11 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String11");
            this.string11 = dataStr11.getDataValue();
            this.stringKey11 = dataStr11.getDataKey();
            MESParamKeyValString0100 dataStr12 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String12");
            this.string12 = dataStr12.getDataValue();
            this.stringKey12 = dataStr12.getDataKey();
            MESParamKeyValString0100 dataStr13 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String13");
            this.string13 = dataStr13.getDataValue();
            this.stringKey13 = dataStr13.getDataKey();
            MESParamKeyValString0100 dataStr14 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String14");
            this.string14 = dataStr14.getDataValue();
            this.stringKey14 = dataStr14.getDataKey();
            MESParamKeyValString0100 dataStr15 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String15");
            this.string15 = dataStr15.getDataValue();
            this.stringKey15 = dataStr15.getDataKey();
            MESParamKeyValString0100 dataStr16 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String16");
            this.string16 = dataStr16.getDataValue();
            this.stringKey16 = dataStr16.getDataKey();
            MESParamKeyValString0100 dataStr17 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String17");
            this.string17 = dataStr17.getDataValue();
            this.stringKey17 = dataStr17.getDataKey();
            MESParamKeyValString0100 dataStr18 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String18");
            this.string18 = dataStr18.getDataValue();
            this.stringKey18 = dataStr18.getDataKey();
            MESParamKeyValString0100 dataStr19 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String19");
            this.string19 = dataStr19.getDataValue();
            this.stringKey19 = dataStr19.getDataKey();
            MESParamKeyValString0100 dataStr20 = (MESParamKeyValString0100)getProcessParameterData(MESParamKeyValString0100.class, "String20");
            this.string20 = dataStr20.getDataValue();
            this.stringKey20 = dataStr20.getDataKey();
            if (Boolean.TRUE.equals(this.isAutoComplete))
                SwingUtilities.invokeLater(() -> getPhaseCompleter().completePhase());
            confirmButton.addActionListener(new ConfirmButtonListener());
            addComponentForCompletion((JComponent)confirmButton);
        }
        return layoutPanel;
    }

    public boolean performCompletionCheck() {
        return true;
    }

    public Response performComplete() {
        MESRtPhaseDataRecordEBR0020 data = addRtPhaseData();
        data.setMeasuredValue1(this.mv01);
        data.setMeasuredValue2(this.mv02);
        data.setMeasuredValue3(this.mv03);
        data.setMeasuredValue4(this.mv04);
        data.setMeasuredValue5(this.mv05);
        data.setMeasuredValue6(this.mv06);
        data.setMeasuredValue7(this.mv07);
        data.setMeasuredValue8(this.mv08);
        data.setMeasuredValue9(this.mv09);
        data.setMeasuredValue10(this.mv10);
        data.setMeasuredValue11(this.mv11);
        data.setMeasuredValue12(this.mv12);
        data.setMeasuredValue13(this.mv13);
        data.setMeasuredValue14(this.mv14);
        data.setMeasuredValue15(this.mv15);
        data.setMeasuredValue16(this.mv16);
        data.setMeasuredValue17(this.mv17);
        data.setMeasuredValue18(this.mv18);
        data.setMeasuredValue19(this.mv19);
        data.setMeasuredValue20(this.mv20);
        data.setMeasuredKey1(this.measuredKey1);
        data.setMeasuredKey2(this.measuredKey2);
        data.setMeasuredKey3(this.measuredKey3);
        data.setMeasuredKey4(this.measuredKey4);
        data.setMeasuredKey5(this.measuredKey5);
        data.setMeasuredKey6(this.measuredKey6);
        data.setMeasuredKey7(this.measuredKey7);
        data.setMeasuredKey8(this.measuredKey8);
        data.setMeasuredKey9(this.measuredKey9);
        data.setMeasuredKey10(this.measuredKey10);
        data.setMeasuredKey11(this.measuredKey11);
        data.setMeasuredKey12(this.measuredKey12);
        data.setMeasuredKey13(this.measuredKey13);
        data.setMeasuredKey14(this.measuredKey14);
        data.setMeasuredKey15(this.measuredKey15);
        data.setMeasuredKey16(this.measuredKey16);
        data.setMeasuredKey17(this.measuredKey17);
        data.setMeasuredKey18(this.measuredKey18);
        data.setMeasuredKey19(this.measuredKey19);
        data.setMeasuredKey20(this.measuredKey20);
        data.setString1(this.string1);
        data.setString2(this.string2);
        data.setString3(this.string3);
        data.setString4(this.string4);
        data.setString5(this.string5);
        data.setString6(this.string6);
        data.setString7(this.string7);
        data.setString8(this.string8);
        data.setString9(this.string9);
        data.setString10(this.string10);
        data.setString11(this.string11);
        data.setString12(this.string12);
        data.setString13(this.string13);
        data.setString14(this.string14);
        data.setString15(this.string15);
        data.setString16(this.string16);
        data.setString17(this.string17);
        data.setString18(this.string18);
        data.setString19(this.string19);
        data.setString20(this.string20);
        data.setStringKey1(this.stringKey1);
        data.setStringKey2(this.stringKey2);
        data.setStringKey3(this.stringKey3);
        data.setStringKey4(this.stringKey4);
        data.setStringKey5(this.stringKey5);
        data.setStringKey6(this.stringKey6);
        data.setStringKey7(this.stringKey7);
        data.setStringKey8(this.stringKey8);
        data.setStringKey9(this.stringKey9);
        data.setStringKey10(this.stringKey10);
        data.setStringKey11(this.stringKey11);
        data.setStringKey12(this.stringKey12);
        data.setStringKey13(this.stringKey13);
        data.setStringKey14(this.stringKey14);
        data.setStringKey15(this.stringKey15);
        data.setStringKey16(this.stringKey16);
        data.setStringKey17(this.stringKey17);
        data.setStringKey18(this.stringKey18);
        data.setStringKey19(this.stringKey19);
        data.setStringKey20(this.stringKey20);
        ProcessOrderItem processOrderItem = getOrderStep().getControlRecipe().getProcessOrderItem();
        data.setOrderNumber(processOrderItem.getOrderName());
        try {
            MESSCLIFRecordEBRData messclifRecordEBRData = new MESSCLIFRecordEBRData();
            messclifRecordEBRData.setOrderNumber(getRtOperation().getProcessOrderItem().getName());
            messclifRecordEBRData.setExecuteOpr(PCContext.getFunctions().getCurrentUser().getName());
            messclifRecordEBRData.setStationName(PCContext.getFunctions().getStation().getName());
            messclifRecordEBRData.setRtPhaseKey(Long.valueOf(getRtPhase().getKey()));
            messclifRecordEBRData.setErpProcessFlag(Long.valueOf(0L));
            messclifRecordEBRData.setLimsProcessFlag(Long.valueOf(0L));
            messclifRecordEBRData.setMesProcessFlag(Long.valueOf(0L));
            messclifRecordEBRData.setWmsProcessFlag(Long.valueOf(0L));
            messclifRecordEBRData.setMvValue1(getMvValue(this.mv01));
            messclifRecordEBRData.setMvValue2(getMvValue(this.mv02));
            messclifRecordEBRData.setMvValue3(getMvValue(this.mv03));
            messclifRecordEBRData.setMvValue4(getMvValue(this.mv04));
            messclifRecordEBRData.setMvValue5(getMvValue(this.mv05));
            messclifRecordEBRData.setMvValue6(getMvValue(this.mv06));
            messclifRecordEBRData.setMvValue7(getMvValue(this.mv07));
            messclifRecordEBRData.setMvValue8(getMvValue(this.mv08));
            messclifRecordEBRData.setMvValue9(getMvValue(this.mv09));
            messclifRecordEBRData.setMvValue10(getMvValue(this.mv10));
            messclifRecordEBRData.setMvValue11(getMvValue(this.mv11));
            messclifRecordEBRData.setMvValue12(getMvValue(this.mv12));
            messclifRecordEBRData.setMvValue13(getMvValue(this.mv13));
            messclifRecordEBRData.setMvValue14(getMvValue(this.mv14));
            messclifRecordEBRData.setMvValue15(getMvValue(this.mv15));
            messclifRecordEBRData.setMvValue16(getMvValue(this.mv16));
            messclifRecordEBRData.setMvValue17(getMvValue(this.mv17));
            messclifRecordEBRData.setMvValue18(getMvValue(this.mv18));
            messclifRecordEBRData.setMvValue19(getMvValue(this.mv19));
            messclifRecordEBRData.setMvValue20(getMvValue(this.mv20));
            messclifRecordEBRData.setMvUom1(getMvUom(this.mv01));
            messclifRecordEBRData.setMvUom2(getMvUom(this.mv02));
            messclifRecordEBRData.setMvUom3(getMvUom(this.mv03));
            messclifRecordEBRData.setMvUom4(getMvUom(this.mv04));
            messclifRecordEBRData.setMvUom5(getMvUom(this.mv05));
            messclifRecordEBRData.setMvUom6(getMvUom(this.mv06));
            messclifRecordEBRData.setMvUom7(getMvUom(this.mv07));
            messclifRecordEBRData.setMvUom8(getMvUom(this.mv08));
            messclifRecordEBRData.setMvUom9(getMvUom(this.mv09));
            messclifRecordEBRData.setMvUom10(getMvUom(this.mv10));
            messclifRecordEBRData.setMvUom11(getMvUom(this.mv11));
            messclifRecordEBRData.setMvUom12(getMvUom(this.mv12));
            messclifRecordEBRData.setMvUom13(getMvUom(this.mv13));
            messclifRecordEBRData.setMvUom14(getMvUom(this.mv14));
            messclifRecordEBRData.setMvUom15(getMvUom(this.mv15));
            messclifRecordEBRData.setMvUom16(getMvUom(this.mv16));
            messclifRecordEBRData.setMvUom17(getMvUom(this.mv17));
            messclifRecordEBRData.setMvUom18(getMvUom(this.mv18));
            messclifRecordEBRData.setMvUom19(getMvUom(this.mv19));
            messclifRecordEBRData.setMvUom20(getMvUom(this.mv20));
            messclifRecordEBRData.setMvKey1(this.measuredKey1);
            messclifRecordEBRData.setMvKey2(this.measuredKey2);
            messclifRecordEBRData.setMvKey3(this.measuredKey3);
            messclifRecordEBRData.setMvKey4(this.measuredKey4);
            messclifRecordEBRData.setMvKey5(this.measuredKey5);
            messclifRecordEBRData.setMvKey6(this.measuredKey6);
            messclifRecordEBRData.setMvKey7(this.measuredKey7);
            messclifRecordEBRData.setMvKey8(this.measuredKey8);
            messclifRecordEBRData.setMvKey9(this.measuredKey9);
            messclifRecordEBRData.setMvKey10(this.measuredKey10);
            messclifRecordEBRData.setMvKey11(this.measuredKey11);
            messclifRecordEBRData.setMvKey12(this.measuredKey12);
            messclifRecordEBRData.setMvKey13(this.measuredKey13);
            messclifRecordEBRData.setMvKey14(this.measuredKey14);
            messclifRecordEBRData.setMvKey15(this.measuredKey15);
            messclifRecordEBRData.setMvKey16(this.measuredKey16);
            messclifRecordEBRData.setMvKey17(this.measuredKey17);
            messclifRecordEBRData.setMvKey18(this.measuredKey18);
            messclifRecordEBRData.setMvKey19(this.measuredKey19);
            messclifRecordEBRData.setMvKey20(this.measuredKey20);
            messclifRecordEBRData.setStringValue1(this.string1);
            messclifRecordEBRData.setStringValue2(this.string2);
            messclifRecordEBRData.setStringValue3(this.string3);
            messclifRecordEBRData.setStringValue4(this.string4);
            messclifRecordEBRData.setStringValue5(this.string5);
            messclifRecordEBRData.setStringValue6(this.string6);
            messclifRecordEBRData.setStringValue7(this.string7);
            messclifRecordEBRData.setStringValue8(this.string8);
            messclifRecordEBRData.setStringValue9(this.string9);
            messclifRecordEBRData.setStringValue10(this.string10);
            messclifRecordEBRData.setStringValue11(this.string11);
            messclifRecordEBRData.setStringValue12(this.string12);
            messclifRecordEBRData.setStringValue13(this.string13);
            messclifRecordEBRData.setStringValue14(this.string14);
            messclifRecordEBRData.setStringValue15(this.string15);
            messclifRecordEBRData.setStringValue16(this.string16);
            messclifRecordEBRData.setStringValue17(this.string17);
            messclifRecordEBRData.setStringValue18(this.string18);
            messclifRecordEBRData.setStringValue19(this.string19);
            messclifRecordEBRData.setStringValue20(this.string20);
            messclifRecordEBRData.setStringKey1(this.stringKey1);
            messclifRecordEBRData.setStringKey2(this.stringKey2);
            messclifRecordEBRData.setStringKey3(this.stringKey3);
            messclifRecordEBRData.setStringKey4(this.stringKey4);
            messclifRecordEBRData.setStringKey5(this.stringKey5);
            messclifRecordEBRData.setStringKey6(this.stringKey6);
            messclifRecordEBRData.setStringKey7(this.stringKey7);
            messclifRecordEBRData.setStringKey8(this.stringKey8);
            messclifRecordEBRData.setStringKey9(this.stringKey9);
            messclifRecordEBRData.setStringKey10(this.stringKey10);
            messclifRecordEBRData.setStringKey11(this.stringKey11);
            messclifRecordEBRData.setStringKey12(this.stringKey12);
            messclifRecordEBRData.setStringKey13(this.stringKey13);
            messclifRecordEBRData.setStringKey14(this.stringKey14);
            messclifRecordEBRData.setStringKey15(this.stringKey15);
            messclifRecordEBRData.setStringKey16(this.stringKey16);
            messclifRecordEBRData.setStringKey17(this.stringKey17);
            messclifRecordEBRData.setStringKey18(this.stringKey18);
            messclifRecordEBRData.setStringKey19(this.stringKey19);
            messclifRecordEBRData.setStringKey20(this.stringKey20);
            messclifRecordEBRData.Save(PCContext.getFunctions().getDBTime(), null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Response();
    }

    public String getMvValue(MeasuredValue measuredValue) {
        return Objects.isNull(measuredValue) ? this.STRING_ZERO : measuredValue.getValue().toString();
    }

    public String getMvUom(MeasuredValue measuredValue) {
        return Objects.isNull(measuredValue) ? this.STRING_NULL : measuredValue.getUnitOfMeasure().toString();
    }

    protected final MESRtPhaseDataRecordEBR0020 addRtPhaseData() {
        return (MESRtPhaseDataRecordEBR0020)getRtPhase().addRtPhaseData();
    }

    protected final MESRtPhaseDataRecordEBR0020 getRtPhaseData() {
        return (MESRtPhaseDataRecordEBR0020)getRtPhase().getRtPhaseData();
    }

    protected final List<MESRtPhaseDataRecordEBR0020> getAllRtPhaseData() {
        return (List)getRtPhase().getAllRtPhaseData();
    }
}
