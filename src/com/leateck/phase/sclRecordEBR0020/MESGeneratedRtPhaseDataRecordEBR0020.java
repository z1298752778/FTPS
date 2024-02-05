package com.leateck.phase.sclRecordEBR0020;

import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.MeasuredValue;
import com.datasweep.compatibility.client.Response;
import com.rockwell.mes.commons.base.ifc.utility.StringUtilsEx;
import com.rockwell.mes.services.s88.ifc.processdata.MESRtPhaseData;

public abstract class MESGeneratedRtPhaseDataRecordEBR0020 extends MESRtPhaseData {
    protected static final String ATDEFINITION_NAME = "SC_PhDatRecordEBR0020";

    public String getATDefinitionName() {
        return "SC_PhDatRecordEBR0020";
    }

    public MESGeneratedRtPhaseDataRecordEBR0020(long key) {
        super(key);
    }

    public MESGeneratedRtPhaseDataRecordEBR0020(MESGeneratedRtPhaseDataRecordEBR0020 source) {
        super(source);
    }

    public MESGeneratedRtPhaseDataRecordEBR0020(ATRow baseATRow) {
        super(baseATRow);
    }

    public MESGeneratedRtPhaseDataRecordEBR0020() {}

    protected void synchronizeAfterATRowRefresh() {
        super.synchronizeAfterATRowRefresh();
    }

    public String getMeasuredKey1() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey1"));
    }

    public void setMeasuredKey1(String value) {
        String oldValue = getMeasuredKey1();
        this.dgtATRow.setValue("SC_measuredKey1", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey1", oldValue, value);
    }

    public MeasuredValue getMeasuredValue1() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue1");
    }

    public void setMeasuredValue1(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue1();
        this.dgtATRow.setValue("SC_measuredValue1", value);
        this.pcs.firePropertyChange("measuredValue1", oldValue, value);
    }

    public String getMeasuredKey2() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey2"));
    }

    public void setMeasuredKey2(String value) {
        String oldValue = getMeasuredKey2();
        this.dgtATRow.setValue("SC_measuredKey2", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey2", oldValue, value);
    }

    public MeasuredValue getMeasuredValue2() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue2");
    }

    public void setMeasuredValue2(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue2();
        this.dgtATRow.setValue("SC_measuredValue2", value);
        this.pcs.firePropertyChange("measuredValue2", oldValue, value);
    }

    public String getMeasuredKey3() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey3"));
    }

    public void setMeasuredKey3(String value) {
        String oldValue = getMeasuredKey3();
        this.dgtATRow.setValue("SC_measuredKey3", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey3", oldValue, value);
    }

    public MeasuredValue getMeasuredValue3() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue3");
    }

    public void setMeasuredValue3(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue3();
        this.dgtATRow.setValue("SC_measuredValue3", value);
        this.pcs.firePropertyChange("measuredValue3", oldValue, value);
    }

    public String getMeasuredKey4() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey4"));
    }

    public void setMeasuredKey4(String value) {
        String oldValue = getMeasuredKey4();
        this.dgtATRow.setValue("SC_measuredKey4", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey4", oldValue, value);
    }

    public MeasuredValue getMeasuredValue4() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue4");
    }

    public void setMeasuredValue4(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue4();
        this.dgtATRow.setValue("SC_measuredValue4", value);
        this.pcs.firePropertyChange("measuredValue4", oldValue, value);
    }

    public String getMeasuredKey5() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey5"));
    }

    public void setMeasuredKey5(String value) {
        String oldValue = getMeasuredKey5();
        this.dgtATRow.setValue("SC_measuredKey5", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey5", oldValue, value);
    }

    public MeasuredValue getMeasuredValue5() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue5");
    }

    public void setMeasuredValue5(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue5();
        this.dgtATRow.setValue("SC_measuredValue5", value);
        this.pcs.firePropertyChange("measuredValue5", oldValue, value);
    }

    public String getMeasuredKey6() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey6"));
    }

    public void setMeasuredKey6(String value) {
        String oldValue = getMeasuredKey6();
        this.dgtATRow.setValue("SC_measuredKey6", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey6", oldValue, value);
    }

    public MeasuredValue getMeasuredValue6() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue6");
    }

    public void setMeasuredValue6(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue6();
        this.dgtATRow.setValue("SC_measuredValue6", value);
        this.pcs.firePropertyChange("measuredValue6", oldValue, value);
    }

    public String getMeasuredKey7() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey7"));
    }

    public void setMeasuredKey7(String value) {
        String oldValue = getMeasuredKey7();
        this.dgtATRow.setValue("SC_measuredKey7", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey7", oldValue, value);
    }

    public MeasuredValue getMeasuredValue7() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue7");
    }

    public void setMeasuredValue7(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue7();
        this.dgtATRow.setValue("SC_measuredValue7", value);
        this.pcs.firePropertyChange("measuredValue7", oldValue, value);
    }

    public String getMeasuredKey8() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey8"));
    }

    public void setMeasuredKey8(String value) {
        String oldValue = getMeasuredKey8();
        this.dgtATRow.setValue("SC_measuredKey8", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey8", oldValue, value);
    }

    public MeasuredValue getMeasuredValue8() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue8");
    }

    public void setMeasuredValue8(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue8();
        this.dgtATRow.setValue("SC_measuredValue8", value);
        this.pcs.firePropertyChange("measuredValue8", oldValue, value);
    }

    public String getMeasuredKey9() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey9"));
    }

    public void setMeasuredKey9(String value) {
        String oldValue = getMeasuredKey9();
        this.dgtATRow.setValue("SC_measuredKey9", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey9", oldValue, value);
    }

    public MeasuredValue getMeasuredValue9() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue9");
    }

    public void setMeasuredValue9(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue9();
        this.dgtATRow.setValue("SC_measuredValue9", value);
        this.pcs.firePropertyChange("measuredValue9", oldValue, value);
    }

    public String getMeasuredKey10() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey10"));
    }

    public void setMeasuredKey10(String value) {
        String oldValue = getMeasuredKey10();
        this.dgtATRow.setValue("SC_measuredKey10", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey10", oldValue, value);
    }

    public MeasuredValue getMeasuredValue10() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue10");
    }

    public void setMeasuredValue10(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue10();
        this.dgtATRow.setValue("SC_measuredValue10", value);
        this.pcs.firePropertyChange("measuredValue10", oldValue, value);
    }

    public String getMeasuredKey11() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey11"));
    }

    public void setMeasuredKey11(String value) {
        String oldValue = getMeasuredKey11();
        this.dgtATRow.setValue("SC_measuredKey11", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey11", oldValue, value);
    }

    public MeasuredValue getMeasuredValue11() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue11");
    }

    public void setMeasuredValue11(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue11();
        this.dgtATRow.setValue("SC_measuredValue11", value);
        this.pcs.firePropertyChange("measuredValue11", oldValue, value);
    }

    public String getMeasuredKey12() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey12"));
    }

    public void setMeasuredKey12(String value) {
        String oldValue = getMeasuredKey12();
        this.dgtATRow.setValue("SC_measuredKey12", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey12", oldValue, value);
    }

    public MeasuredValue getMeasuredValue12() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue12");
    }

    public void setMeasuredValue12(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue12();
        this.dgtATRow.setValue("SC_measuredValue12", value);
        this.pcs.firePropertyChange("measuredValue12", oldValue, value);
    }

    public String getMeasuredKey13() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey13"));
    }

    public void setMeasuredKey13(String value) {
        String oldValue = getMeasuredKey13();
        this.dgtATRow.setValue("SC_measuredKey13", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey13", oldValue, value);
    }

    public MeasuredValue getMeasuredValue13() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue13");
    }

    public void setMeasuredValue13(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue13();
        this.dgtATRow.setValue("SC_measuredValue13", value);
        this.pcs.firePropertyChange("measuredValue13", oldValue, value);
    }

    public String getMeasuredKey14() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey14"));
    }

    public void setMeasuredKey14(String value) {
        String oldValue = getMeasuredKey14();
        this.dgtATRow.setValue("SC_measuredKey14", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey14", oldValue, value);
    }

    public MeasuredValue getMeasuredValue14() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue14");
    }

    public void setMeasuredValue14(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue14();
        this.dgtATRow.setValue("SC_measuredValue14", value);
        this.pcs.firePropertyChange("measuredValue14", oldValue, value);
    }

    public String getMeasuredKey15() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey15"));
    }

    public void setMeasuredKey15(String value) {
        String oldValue = getMeasuredKey15();
        this.dgtATRow.setValue("SC_measuredKey15", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey15", oldValue, value);
    }

    public MeasuredValue getMeasuredValue15() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue15");
    }

    public void setMeasuredValue15(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue15();
        this.dgtATRow.setValue("SC_measuredValue15", value);
        this.pcs.firePropertyChange("measuredValue15", oldValue, value);
    }

    public String getMeasuredKey16() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey16"));
    }

    public void setMeasuredKey16(String value) {
        String oldValue = getMeasuredKey16();
        this.dgtATRow.setValue("SC_measuredKey16", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey16", oldValue, value);
    }

    public MeasuredValue getMeasuredValue16() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue16");
    }

    public void setMeasuredValue16(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue16();
        this.dgtATRow.setValue("SC_measuredValue16", value);
        this.pcs.firePropertyChange("measuredValue16", oldValue, value);
    }

    public String getMeasuredKey17() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey17"));
    }

    public void setMeasuredKey17(String value) {
        String oldValue = getMeasuredKey17();
        this.dgtATRow.setValue("SC_measuredKey17", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey17", oldValue, value);
    }

    public MeasuredValue getMeasuredValue17() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue17");
    }

    public void setMeasuredValue17(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue17();
        this.dgtATRow.setValue("SC_measuredValue17", value);
        this.pcs.firePropertyChange("measuredValue17", oldValue, value);
    }

    public String getMeasuredKey18() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey18"));
    }

    public void setMeasuredKey18(String value) {
        String oldValue = getMeasuredKey18();
        this.dgtATRow.setValue("SC_measuredKey18", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey18", oldValue, value);
    }

    public MeasuredValue getMeasuredValue18() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue18");
    }

    public void setMeasuredValue18(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue18();
        this.dgtATRow.setValue("SC_measuredValue18", value);
        this.pcs.firePropertyChange("measuredValue18", oldValue, value);
    }

    public String getMeasuredKey19() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey19"));
    }

    public void setMeasuredKey19(String value) {
        String oldValue = getMeasuredKey19();
        this.dgtATRow.setValue("SC_measuredKey19", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey19", oldValue, value);
    }

    public MeasuredValue getMeasuredValue19() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue19");
    }

    public void setMeasuredValue19(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue19();
        this.dgtATRow.setValue("SC_measuredValue19", value);
        this.pcs.firePropertyChange("measuredValue19", oldValue, value);
    }

    public String getMeasuredKey20() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_measuredKey20"));
    }

    public void setMeasuredKey20(String value) {
        String oldValue = getMeasuredKey20();
        this.dgtATRow.setValue("SC_measuredKey20", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("measuredKey20", oldValue, value);
    }

    public MeasuredValue getMeasuredValue20() {
        return (MeasuredValue)this.dgtATRow.getValue("SC_measuredValue20");
    }

    public void setMeasuredValue20(MeasuredValue value) {
        MeasuredValue oldValue = getMeasuredValue20();
        this.dgtATRow.setValue("SC_measuredValue20", value);
        this.pcs.firePropertyChange("measuredValue20", oldValue, value);
    }

    public String getStringKey1() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey1"));
    }

    public void setStringKey1(String value) {
        String oldValue = getStringKey1();
        this.dgtATRow.setValue("SC_stringKey1", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey1", oldValue, value);
    }

    public String getString1() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string1"));
    }

    public void setString1(String value) {
        String oldValue = getString1();
        this.dgtATRow.setValue("SC_string1", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string1", oldValue, value);
    }

    public String getStringKey2() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey2"));
    }

    public void setStringKey2(String value) {
        String oldValue = getStringKey2();
        this.dgtATRow.setValue("SC_stringKey2", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey2", oldValue, value);
    }

    public String getString2() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string2"));
    }

    public void setString2(String value) {
        String oldValue = getString2();
        this.dgtATRow.setValue("SC_string2", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string2", oldValue, value);
    }

    public String getStringKey3() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey3"));
    }

    public void setStringKey3(String value) {
        String oldValue = getStringKey3();
        this.dgtATRow.setValue("SC_stringKey3", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey3", oldValue, value);
    }

    public String getString3() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string3"));
    }

    public void setString3(String value) {
        String oldValue = getString3();
        this.dgtATRow.setValue("SC_string3", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string3", oldValue, value);
    }

    public String getStringKey4() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey4"));
    }

    public void setStringKey4(String value) {
        String oldValue = getStringKey4();
        this.dgtATRow.setValue("SC_stringKey4", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey4", oldValue, value);
    }

    public String getString4() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string4"));
    }

    public void setString4(String value) {
        String oldValue = getString4();
        this.dgtATRow.setValue("SC_string4", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string4", oldValue, value);
    }

    public String getStringKey5() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey5"));
    }

    public void setStringKey5(String value) {
        String oldValue = getStringKey5();
        this.dgtATRow.setValue("SC_stringKey5", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey5", oldValue, value);
    }

    public String getString5() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string5"));
    }

    public void setString5(String value) {
        String oldValue = getString5();
        this.dgtATRow.setValue("SC_string5", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string5", oldValue, value);
    }

    public String getStringKey6() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey6"));
    }

    public void setStringKey6(String value) {
        String oldValue = getStringKey6();
        this.dgtATRow.setValue("SC_stringKey6", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey6", oldValue, value);
    }

    public String getString6() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string6"));
    }

    public void setString6(String value) {
        String oldValue = getString6();
        this.dgtATRow.setValue("SC_string6", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string6", oldValue, value);
    }

    public String getStringKey7() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey7"));
    }

    public void setStringKey7(String value) {
        String oldValue = getStringKey7();
        this.dgtATRow.setValue("SC_stringKey7", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey7", oldValue, value);
    }

    public String getString7() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string7"));
    }

    public void setString7(String value) {
        String oldValue = getString7();
        this.dgtATRow.setValue("SC_string7", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string7", oldValue, value);
    }

    public String getStringKey8() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey8"));
    }

    public void setStringKey8(String value) {
        String oldValue = getStringKey8();
        this.dgtATRow.setValue("SC_stringKey8", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey8", oldValue, value);
    }

    public String getString8() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string8"));
    }

    public void setString8(String value) {
        String oldValue = getString8();
        this.dgtATRow.setValue("SC_string8", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string8", oldValue, value);
    }

    public String getStringKey9() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey9"));
    }

    public void setStringKey9(String value) {
        String oldValue = getStringKey9();
        this.dgtATRow.setValue("SC_stringKey9", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey9", oldValue, value);
    }

    public String getString9() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string9"));
    }

    public void setString9(String value) {
        String oldValue = getString9();
        this.dgtATRow.setValue("SC_string9", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string9", oldValue, value);
    }

    public String getStringKey10() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey10"));
    }

    public void setStringKey10(String value) {
        String oldValue = getStringKey10();
        this.dgtATRow.setValue("SC_stringKey10", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey10", oldValue, value);
    }

    public String getString10() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string10"));
    }

    public void setString10(String value) {
        String oldValue = getString10();
        this.dgtATRow.setValue("SC_string10", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string10", oldValue, value);
    }

    public String getStringKey11() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey11"));
    }

    public void setStringKey11(String value) {
        String oldValue = getStringKey11();
        this.dgtATRow.setValue("SC_stringKey11", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey11", oldValue, value);
    }

    public String getString11() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string11"));
    }

    public void setString11(String value) {
        String oldValue = getString11();
        this.dgtATRow.setValue("SC_string11", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string11", oldValue, value);
    }

    public String getStringKey12() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey12"));
    }

    public void setStringKey12(String value) {
        String oldValue = getStringKey12();
        this.dgtATRow.setValue("SC_stringKey12", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey12", oldValue, value);
    }

    public String getString12() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string12"));
    }

    public void setString12(String value) {
        String oldValue = getString12();
        this.dgtATRow.setValue("SC_string12", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string12", oldValue, value);
    }

    public String getStringKey13() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey13"));
    }

    public void setStringKey13(String value) {
        String oldValue = getStringKey13();
        this.dgtATRow.setValue("SC_stringKey13", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey13", oldValue, value);
    }

    public String getString13() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string13"));
    }

    public void setString13(String value) {
        String oldValue = getString13();
        this.dgtATRow.setValue("SC_string13", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string13", oldValue, value);
    }

    public String getStringKey14() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey14"));
    }

    public void setStringKey14(String value) {
        String oldValue = getStringKey14();
        this.dgtATRow.setValue("SC_stringKey14", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey14", oldValue, value);
    }

    public String getString14() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string14"));
    }

    public void setString14(String value) {
        String oldValue = getString14();
        this.dgtATRow.setValue("SC_string14", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string14", oldValue, value);
    }

    public String getStringKey15() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey15"));
    }

    public void setStringKey15(String value) {
        String oldValue = getStringKey15();
        this.dgtATRow.setValue("SC_stringKey15", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey15", oldValue, value);
    }

    public String getString15() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string15"));
    }

    public void setString15(String value) {
        String oldValue = getString15();
        this.dgtATRow.setValue("SC_string15", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string15", oldValue, value);
    }

    public String getStringKey16() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey16"));
    }

    public void setStringKey16(String value) {
        String oldValue = getStringKey16();
        this.dgtATRow.setValue("SC_stringKey16", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey16", oldValue, value);
    }

    public String getString16() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string16"));
    }

    public void setString16(String value) {
        String oldValue = getString16();
        this.dgtATRow.setValue("SC_string16", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string16", oldValue, value);
    }

    public String getStringKey17() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey17"));
    }

    public void setStringKey17(String value) {
        String oldValue = getStringKey17();
        this.dgtATRow.setValue("SC_stringKey17", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey17", oldValue, value);
    }

    public String getString17() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string17"));
    }

    public void setString17(String value) {
        String oldValue = getString17();
        this.dgtATRow.setValue("SC_string17", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string17", oldValue, value);
    }

    public String getStringKey18() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey18"));
    }

    public void setStringKey18(String value) {
        String oldValue = getStringKey18();
        this.dgtATRow.setValue("SC_stringKey18", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey18", oldValue, value);
    }

    public String getString18() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string18"));
    }

    public void setString18(String value) {
        String oldValue = getString18();
        this.dgtATRow.setValue("SC_string18", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string18", oldValue, value);
    }

    public String getStringKey19() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey19"));
    }

    public void setStringKey19(String value) {
        String oldValue = getStringKey19();
        this.dgtATRow.setValue("SC_stringKey19", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey19", oldValue, value);
    }

    public String getString19() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string19"));
    }

    public void setString19(String value) {
        String oldValue = getString19();
        this.dgtATRow.setValue("SC_string19", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string19", oldValue, value);
    }

    public String getStringKey20() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_stringKey20"));
    }

    public void setStringKey20(String value) {
        String oldValue = getStringKey20();
        this.dgtATRow.setValue("SC_stringKey20", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("stringKey20", oldValue, value);
    }

    public String getString20() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_string20"));
    }

    public void setString20(String value) {
        String oldValue = getString20();
        this.dgtATRow.setValue("SC_string20", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("string20", oldValue, value);
    }

    public String getOrderNumber() {
        return StringUtilsEx.decodeStringForUI((String)this.dgtATRow.getValue("SC_orderNumber"));
    }

    public void setOrderNumber(String value) {
        String oldValue = getOrderNumber();
        this.dgtATRow.setValue("SC_orderNumber", StringUtilsEx.encodeStringForDB(value));
        this.pcs.firePropertyChange("orderNumber", oldValue, value);
    }

    protected Response prepareATRowForSave() {
        Response res = super.prepareATRowForSave();
        return res;
    }
}
