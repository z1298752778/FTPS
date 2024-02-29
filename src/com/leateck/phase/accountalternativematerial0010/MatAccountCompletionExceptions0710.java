package com.leateck.phase.accountalternativematerial0010;

import com.datasweep.compatibility.client.MeasuredValue;
import com.datasweep.compatibility.client.OrderStepInput;
import com.datasweep.compatibility.client.Part;
import com.datasweep.compatibility.client.Sublot;
import com.datasweep.plantops.common.measuredvalue.IMeasuredValue;
import com.datasweep.plantops.common.measuredvalue.IUnitOfMeasure;
import com.jgoodies.common.base.Strings;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.PhaseErrorDialog;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.PhaseQuestionDialog;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseSystemTriggeredExceptionsCollector;
import com.rockwell.mes.commons.base.ifc.exceptions.MESException;
import com.rockwell.mes.commons.base.ifc.exceptions.MESIncompatibleUoMException;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.commons.base.ifc.functional.IMeasuredValueConverter;
import com.rockwell.mes.commons.base.ifc.functional.MeasuredValueUtilities;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.nameduda.MESNamedUDAOrderStepInput;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.commons.base.ifc.utility.StringConstants;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.IMESExceptionRecord;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.IMESExceptionRecord.RiskClass;
import com.rockwell.mes.commons.parameter.exceptiondef.MESParamExceptionDef0300;
import com.rockwell.mes.commons.parameter.string.MESParamString0100;
import com.rockwell.mes.parameter.product.excptenabledef.MESParamExcptEnableDef0200;
import com.rockwell.mes.services.order.ifc.EnumOrderStepInputStatus;
import com.rockwell.mes.services.s88.ifc.recipe.IMESMaterialParameter;
import com.rockwell.mes.services.s88.ifc.recipe.PlannedQuantityMode;
import com.rockwell.mes.services.s88.impl.recipe.MESMaterialParameter;
import com.rockwell.mes.services.wip.ifc.IOrderStepExecutionService;
import com.rockwell.mes.services.wip.ifc.IOrderStepExecutionService.QuantityRangeCondition;
import com.rockwell.mes.services.wip.ifc.LimitsAndPlannedQuantity;
import com.rockwell.mes.shared.product.material.AccountMaterialDAO0710;
import com.rockwell.mes.shared.product.material.MaterialModel0710;
import com.rockwell.mes.shared0400.product.util.ParamClassConstants0400;
import org.apache.commons.lang3.StringUtils;
import org.apache.ecs.html.S;
import org.apache.ecs.wml.I;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Extension of {@link PhaseSystemTriggeredExceptionsCollector}, which contains the specific system triggered exceptions
 * triggered on completion of this phase.
 * <p>
 *
 * @author ikoleva, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
 */
public class MatAccountCompletionExceptions0710 extends PhaseSystemTriggeredExceptionsCollector<RtPhaseExecutorMatAlterAcct0010> {

    /**
     * Enumeration containing all system triggered exceptions for this phase.
     */
    enum SystemtriggeredExceptionEnum {
        /**
         * Wrong number of items.
         */
        UnaccountedSublots(ParamClassConstants0400.PARAMETER_ACC_CONFIG) {
            @Override
            boolean conditionForSystemtriggeredException(RtPhaseExecutorMatAlterAcct0010 executor) {
                MESParamExcptEnableDef0200 accountConfig = executor.getModel().getAccountConfig();
                int unaccountedSublots = executor.getUnaccountedSublotsCount();
                return accountConfig.getEnabled() != null && accountConfig.getEnabled() && unaccountedSublots > 0;
            }

            @Override
            void createException(final MatAccountCompletionExceptions0710 exceptions) {
                final long unaccountedSublots = exceptions.executor.getUnaccountedSublotsCount();
                String additionalInfoText;
                if (unaccountedSublots == 1) {
                    additionalInfoText = I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "UnaccountedRowMsg");
                } else {
                    additionalInfoText = I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "UnaccountedRowsMsg",
                            new Object[]{unaccountedSublots});

                }
                String exceptionText = exceptions.executor.getModel().getExceptionTextFromParameter(getParameterName());
                RiskClass riskClass = exceptions.executor.getModel().getExceptionRiskFromParameter(getParameterName());
                exceptions.addSystemtriggeredException(exceptionText, riskClass, getCheckKey(), additionalInfoText);
            }
        },
        /**
         * 子批次没有消耗异常
         */
        UnconsumedSublot(LcAccountMaterialDAO0710.UNCONSUMED_SUBLOT) {
            @Override
            boolean conditionForSystemtriggeredException(RtPhaseExecutorMatAlterAcct0010 executor) {
                //有子批次未消耗
                List<String> allSublotConsume = new ArrayList<>();
                allSublotConsume= executor.getModel().isAllSublotConsume(executor.getModel().getMaterialList());
                if (allSublotConsume.size() > 0) {
                    final String isContinueMsg =
                            I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK,
                                    "SubBatchNotConsumed_Error");
                    PhaseQuestionDialog questionDialog = new PhaseQuestionDialog();
                    int userChoice = questionDialog.showDialog(isContinueMsg);
                    isOK = userChoice;
                    if (userChoice != 0) {
                        return false;
                    }else {return true;}
                }
                return false;
            }

            @Override
            void createException(final MatAccountCompletionExceptions0710 exceptions) {
                StringBuffer msg = new StringBuffer("以下子批次：\n");

                for(String sub: exceptions.executor.getModel().isAllSublotConsume(
                        exceptions.executor.getModel().getMaterialList())){
                    msg.append(sub);
                    msg.append(" \n");
                }

                msg.append("未消耗");
                MESParamExceptionDef0300 paramExceptionDef0300 = null;
                paramExceptionDef0300 = exceptions.executor.getProcessParameterData(MESParamExceptionDef0300.class, LcAccountMaterialDAO0710.UNCONSUMED_SUBLOT);
                String exceptionStr = paramExceptionDef0300.getExceptionDescr() + "\r\n" + msg;
                long risk = paramExceptionDef0300.getRiskAssessment();
                IMESExceptionRecord.RiskClass riskclass = IMESExceptionRecord.RiskClass.valueOf(risk);
                //exceptions.showExceptionDialog()
                exceptions.executor.displayException(getCheckKey(),riskclass,exceptionStr);
                //exceptions.addSystemtriggeredException(exceptionStr, riskclass, getCheckKey(), exceptionStr);
            }
        },
        /**
         * 组合比例不一致异常
         */
        AbnormalMaterialCombinationRatio(LcAccountMaterialDAO0710.ABNORMAL_MATERIAL_COMBINATIONRATIO) {
            @Override
            boolean conditionForSystemtriggeredException(RtPhaseExecutorMatAlterAcct0010 executor) {


                //判断组合比例是否一致
                if (executor.getModel().checkCombineGroupIsEqualWithRate() == false) {
                    StringBuffer errorMag = new StringBuffer("");
                    List<String[]> notEqualRatePartList = executor.getModel().getNotEqualRatePartList();
                    Map<String,String> diffCom = new HashMap<>();
                    errorMag.append(I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK,"configurationRatioDialog"));
                    for (String[] notEqualRatePart: notEqualRatePartList){
                        if (diffCom.get(notEqualRatePart[1]) != null) {
                            String subs = diffCom.get(notEqualRatePart[1]);
                            diffCom.put(notEqualRatePart[1],subs+","+notEqualRatePart[2]);
                        }else {
                            diffCom.put(notEqualRatePart[1],notEqualRatePart[2]);
                        }
                    }
                    ArrayList<String> allcom = new ArrayList<>();
                    for (String[] notEqualRatePart: notEqualRatePartList){
                        if (allcom.contains(notEqualRatePart[0])) {
                            continue;
                        }
                        allcom.add(notEqualRatePart[0]);
                        notEqualRatePart[2] = diffCom.get(notEqualRatePart[1]);
                        errorMag.append(
                                I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK, "CombineGroupIsEqualWithRate_Error",
                                        notEqualRatePart));
                        errorMag.append("\n");
                    }

                    errorMag.append(I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK,"RecordAnomalieDialog"));
                    //提示弹框
                    PhaseQuestionDialog questionDialog = new PhaseQuestionDialog();
                    int userChoice = questionDialog.showDialog(errorMag.toString());
                    isOK = userChoice;
                    if (userChoice != 0) {
                        return false;
                    }else {
                        return true;
                    }
                }
                return false;
            }

            @Override
            void createException(final MatAccountCompletionExceptions0710 exceptions) {

                StringBuffer errorMag = new StringBuffer("");
                List<String[]> notEqualRatePartList = exceptions.executor.getModel().getNotEqualRatePartList();
                Map<String,String> diffCom = new HashMap<>();
                errorMag.append(I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK,"configurationRatioDialog"));
                for (String[] notEqualRatePart: notEqualRatePartList){
                    if (diffCom.get(notEqualRatePart[1]) != null) {
                        String subs = diffCom.get(notEqualRatePart[1]);
                        diffCom.put(notEqualRatePart[1],subs+","+notEqualRatePart[2]);
                    }else {
                        diffCom.put(notEqualRatePart[1],notEqualRatePart[2]);
                    }
                }
                ArrayList<String> allcom = new ArrayList<>();
                for (String[] notEqualRatePart: notEqualRatePartList){
                    if (allcom.contains(notEqualRatePart[0])) {
                        continue;
                    }
                    allcom.add(notEqualRatePart[0]);
                    notEqualRatePart[2] = diffCom.get(notEqualRatePart[1]);
                    errorMag.append(
                            I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK, "CombineGroupIsEqualWithRate_Error",
                                    notEqualRatePart));
                    errorMag.append("\n");
                }



                MESParamExceptionDef0300 paramExceptionDef0300 = null;
                paramExceptionDef0300 = exceptions.executor.getProcessParameterData(MESParamExceptionDef0300.class, LcAccountMaterialDAO0710.ABNORMAL_MATERIAL_COMBINATIONRATIO);
                String exceptionStr = paramExceptionDef0300.getExceptionDescr() + "\r\n" + errorMag.toString();
                long risk = paramExceptionDef0300.getRiskAssessment();
                IMESExceptionRecord.RiskClass riskclass = IMESExceptionRecord.RiskClass.valueOf(risk);
                exceptions.executor.displayException(getCheckKey(),riskclass,exceptionStr);
            }
        },
        /*
        * 消耗数量与计划量不一致
        * */
        /**
         * Overweigh.
         */
        QuantityOutOfTolerance(ParamClassConstants0400.PARAMETER_QTY_OUF_OF_TOLERANCE) {
            /**
             * The result set
             * <p>
             *
             * @author ikoleva, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
             */
            final class ResultSet {
                private final Part material;

                private final LimitsAndPlannedQuantity limitsAndPlannedQuantity;

                private final MeasuredValue consumedQuantity;

                private final QuantityRangeCondition result;

                private final OrderStepInput orderStepInput;

                private ResultSet(Part mat, LimitsAndPlannedQuantity limits, MeasuredValue consumed, QuantityRangeCondition res,OrderStepInput orderStepInput) {
                    material = mat;
                    limitsAndPlannedQuantity = limits;
                    consumedQuantity = consumed;
                    result = res;
                    this.orderStepInput = orderStepInput;
                }
            }

            @Override
            boolean conditionForSystemtriggeredException(RtPhaseExecutorMatAlterAcct0010 executor) {
                List<ResultSet> quantitiesOutOfRange = getQuantitiesOutOfRange(executor);
                List<ResultSet> limitPlanned = new ArrayList<>();//保存消耗量到计划量
                List<ResultSet> limitPlannedRange = new ArrayList<>();//保存消耗量到计划量范围
                    //判断计划量是否在范围

                //判断是否有主料，如果有主料，并且主料有替换，替换物料不判断是否有消耗
                //获取该物料是否设置为主料
                List<IMESMaterialParameter> materialParameters1 = executor.getPhase().getMaterialParameters();
                if(quantitiesOutOfRange.isEmpty()) return false;
                List<IMESMaterialParameter> matParamList1 = materialParameters1.stream()
                        .filter(p -> p.getATRow().getValue("LC_isMainPart") != null
                                && (Boolean) p.getATRow().getValue("LC_isMainPart")).collect(Collectors.toList());
                //如果当前配置了主料
                if (matParamList1.size() > 0 ){
                    Iterator<ResultSet> iterator = quantitiesOutOfRange.iterator();
                    while (iterator.hasNext()){
                        ResultSet resultSet = iterator.next();
                        if (null == resultSet.consumedQuantity){
                            iterator.remove();
                        }
                    }
                }
                if(quantitiesOutOfRange.isEmpty()) return false;
                for(ResultSet resultSet: quantitiesOutOfRange){

                    List<IMESMaterialParameter> materialParameters = executor.getPhase().getMaterialParameters();
                    OrderStepInput orderStepInput = resultSet.orderStepInput;
                    //获取限制模式不为空的物料
                    List<IMESMaterialParameter> matParamList = materialParameters.stream()
                            .filter(p -> p.getMaterial() == orderStepInput.getPart()
                                    && p.getATRow().getValue("LC_restrictionMode") != null).collect(Collectors.toList());
                    if(matParamList.size() > 0){
                        //将不同限制模式的物料放入集合
                        if(20 == (Long)matParamList.get(0).getATRow().getValue("LC_restrictionMode")){
                            //限制到达计划量
                            limitPlanned.add(resultSet);
                            //提示弹框
                        }
                        else if (30 ==(Long)matParamList.get(0).getATRow().getValue("LC_restrictionMode")){
                            //限制到达计划量范围
                            limitPlannedRange.add(resultSet);
                        }
                    }
                }

                if(limitPlannedRange.size() >0 || limitPlanned.size()>0) {
                    //提示框
                    StringBuffer isContinueMsg = new StringBuffer(I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK, "FollowMaterialDialog" ));
                    //不等于计划量的物料
                    for (ResultSet set: limitPlanned){
                        isContinueMsg.append(
                                I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK, "PlannedQtyNotReached_dialog",
                                        new Object[]{set.material.getPartNumber(),set.orderStepInput.getPlannedQuantity().toString()}));
                        isContinueMsg.append("\n");
                    }

                    //不等于计划量范围的物料
                    for (ResultSet set: limitPlannedRange){
                        //拼接计划量范围
                        String range = (set.limitsAndPlannedQuantity.getLowerLimit()==null?set.limitsAndPlannedQuantity.getPlannedQuantity():set.limitsAndPlannedQuantity.getLowerLimit())
                                +"~"+ (set.limitsAndPlannedQuantity.getUpperLimit()==null?set.limitsAndPlannedQuantity.getPlannedQuantity():set.limitsAndPlannedQuantity.getUpperLimit());
                        //提示计划量范围
                        isContinueMsg.append(
                                I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK, "PlannedQtyRangeNotReached_dialog",
                                        new Object[]{set.material.getPartNumber(),range}));
                        isContinueMsg.append("\n");
                    }

                    isContinueMsg.append(I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK, "RecordAnomalieDialog" ));
                    PhaseQuestionDialog questionDialog = new PhaseQuestionDialog();
                    int userChoice = questionDialog.showDialog(isContinueMsg.toString());
                    isOK = userChoice;
                    //点击否
                    if (userChoice != 0) {
                        return false;
                    }else {//点击是
                        return true;
                    }
                }
                //消耗量满足计划量
                return false;
                //return !getQuantitiesOutOfRange(executor).isEmpty();
            }


            @Override
            void createException(final MatAccountCompletionExceptions0710 exceptions) {
                final String additionalInfoHeader =
                        I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "QuantitiesOufOfRangeMsg");
                List<ResultSet> result = getQuantitiesOutOfRange(exceptions.executor);

                List<ResultSet> limitPlanned = new ArrayList<>();//保存消耗量到计划量
                List<ResultSet> limitPlannedRange = new ArrayList<>();//保存消耗量到计划量范围
                for (ResultSet resultSet :result){
                    List<IMESMaterialParameter> materialParameters = exceptions.executor.getPhase().getMaterialParameters();
                    OrderStepInput orderStepInput = resultSet.orderStepInput;
                    //获取限制模式不为空的物料
                    List<IMESMaterialParameter> matParamList = materialParameters.stream()
                            .filter(p -> p.getMaterial() == orderStepInput.getPart()
                                    && p.getATRow().getValue("LC_restrictionMode") != null).collect(Collectors.toList());

                    if(matParamList.size() > 0){
                        //设置计划量的放入集合
                        if(20 == (Long)matParamList.get(0).getATRow().getValue("LC_restrictionMode")){
                            //限制到达计划量
                            limitPlanned.add(resultSet);
                        } //设置计划量范围的放入集合
                        else if (30 ==(Long)matParamList.get(0).getATRow().getValue("LC_restrictionMode")){
                            //限制到达计划量范围
                            limitPlannedRange.add(resultSet);

                        }
                    }
                }
                //提示
                StringBuffer isContinueMsg = new StringBuffer(I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK, "FollowMaterialDialog" ));
                //提示不等于集合量的物料
                for (ResultSet set: limitPlanned){
                    //提示计划量
                    isContinueMsg.append(
                            I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK, "PlannedQtyNotReached_dialog",
                                    new Object[]{set.material.getPartNumber(),set.orderStepInput.getPlannedQuantity().toString()}));
                    //提示消耗量
                    isContinueMsg.append( I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK, "ConsumeQtyException",
                            new Object[]{set.consumedQuantity== null? MeasuredValueUtilities.createZero(set.limitsAndPlannedQuantity.getPlannedQuantity().getUnitOfMeasure()).toString():set.consumedQuantity.toString()}));
                    isContinueMsg.append("\n");
                }

                //获取不在计划量范围的物料
                for (ResultSet set: limitPlannedRange){
                    //拼接计划量范围
                    String range = (set.limitsAndPlannedQuantity.getLowerLimit()==null?set.limitsAndPlannedQuantity.getPlannedQuantity():set.limitsAndPlannedQuantity.getLowerLimit())
                            +"~"+ (set.limitsAndPlannedQuantity.getUpperLimit()==null?set.limitsAndPlannedQuantity.getPlannedQuantity():set.limitsAndPlannedQuantity.getUpperLimit());
                    //提示计划量
                    isContinueMsg.append(
                            I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK, "PlannedQtyRangeNotReached_dialog",
                                    new Object[]{set.material.getPartNumber(),range}));
                    //提示消耗量
                    isContinueMsg.append( I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK, "ConsumeQtyException",
                            new Object[]{set.consumedQuantity== null? MeasuredValueUtilities.createZero(set.limitsAndPlannedQuantity.getPlannedQuantity().getUnitOfMeasure()).toString():set.consumedQuantity.toString()}));

                    isContinueMsg.append("\n");
                }
                RiskClass riskClass = exceptions.executor.getModel().getExceptionRiskFromParameter(getParameterName());
                exceptions.executor.displayException(getCheckKey(),riskClass,isContinueMsg.toString());
            }

            private List<ResultSet> getQuantitiesOutOfRange(RtPhaseExecutorMatAlterAcct0010 executor) {
                IOrderStepExecutionService service = ServiceFactory.getService(IOrderStepExecutionService.class);
                List<OrderStepInput> masterOSIToCheck = new ArrayList();
                List<OrderStepInput> allMasterOSIs = executor.getModel().getMasterOSIsForPhase();
                masterOSIToCheck.addAll(allMasterOSIs.stream().filter(osi -> mustCheckQuantityForOsi(executor, osi)).collect(Collectors.toList()));
                List<ResultSet> result = new ArrayList();
                //dustin:获取物料参数配置集合
                List<IMESMaterialParameter> materialParameters = executor.getPhase().getMaterialParameters();
                for (OrderStepInput osi : masterOSIToCheck) {
                    try {
                        //获取替代消耗数量和本身消耗数量总和
                        MeasuredValue totalConsumedQty = getSelfAndReplaceConsumedQty(osi, allMasterOSIs, materialParameters);
                        if (executor.getModel().getIncludeSampleAndWaste()) {
                            IMeasuredValueConverter converter = MeasuredValueUtilities.getMVConverter(osi.getPart());
                            totalConsumedQty = MeasuredValueUtilities.addArgsOptional(totalConsumedQty,
                                    MESNamedUDAOrderStepInput.getTotalSampledQuantity(osi), converter);
                            totalConsumedQty = MeasuredValueUtilities.addArgsOptional(totalConsumedQty,
                                    MESNamedUDAOrderStepInput.getTotalWastedQuantity(osi), converter);
                        }
                        if (totalConsumedQty == null) {
                            result.add(new ResultSet(osi.getPart(), service.determineLimitsAndPlannedQuantity(osi), null, null,osi));
                        } else {
                            QuantityRangeCondition qtyCheckResult = service.checkQuantityInRange(osi, totalConsumedQty);
                            if (qtyCheckResult == QuantityRangeCondition.UNDEFINED || qtyCheckResult.isOutOfRange()) {
                                result.add(new ResultSet(osi.getPart(), service.determineLimitsAndPlannedQuantity(osi), totalConsumedQty,
                                        qtyCheckResult,osi));
                            }
                        }


                    } catch (MESException e) {
                        throw new MESRuntimeException(e);
                    }
                }
                return result;
            }

            private boolean mustCheckQuantityForOsi(RtPhaseExecutorMatAlterAcct0010 executor, OrderStepInput osi) {
                // check master OSIs with planned quantity that are not finished yet
                return !PlannedQuantityMode.NONE.equals(executor.getModel().getPlannedQuantityMode(osi))
                        && !EnumOrderStepInputStatus.isFinishedStatus(MESNamedUDAOrderStepInput.getStatus(osi));
            }
        },
        /**
         * 物料平衡不在范围异常
         */
        MaterialBalanceCheckConfiguration(LcAccountMaterialDAO0710.Material_Balance_Check_Configuration) {
            /**
             * The result set
             * <p>
             *
             * @author ikoleva, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
             */

            @Override
            boolean conditionForSystemtriggeredException(RtPhaseExecutorMatAlterAcct0010 executor) {
                //判断是否启用了异常
                MESParamExcptEnableDef0200 paramExcptEnableDef0200  =  executor.getProcessParameterData(MESParamExcptEnableDef0200.class, LcAccountMaterialDAO0710.Material_Balance_Check_Configuration);
                if (!paramExcptEnableDef0200.getEnabled()) {
                    return false;
                }
                //判断处方是否配置物料平衡检查
                MESParamString0100 paramString0100 =  executor.getProcessParameterData(MESParamString0100.class,LcAccountMaterialDAO0710.Position_And_Material_Balance);

                String balanceValues = paramString0100.getValue();
                if (balanceValues == null || "".equals(balanceValues)) {//上下限未配置
                    PhaseErrorDialog phaseErrorDialog = new PhaseErrorDialog();
                    phaseErrorDialog.showDialog(LcAccountMaterialDAO0710.MSG_PACK,"MaterialBalance_Error");
                    isOK = 2;
                    return false;
                }
                //获取所有消耗的物料
                List<AccountMaterialDAO0710> materialList = executor.getModel().getMaterialList();
                List<String[]> balanceNotScope = new ArrayList<>();
                //解析配置的上下限
                try {
                    String[]  balanceValue = balanceValues.split(",");
                    for (String materialAndBalances :balanceValue){
                        String[] materialAndBalance = materialAndBalances.split(":");
                        if(materialAndBalance.length != 2){//才分物料和物料平衡
                            PhaseErrorDialog phaseErrorDialog = new PhaseErrorDialog();
                            phaseErrorDialog.showDialog(LcAccountMaterialDAO0710.MSG_PACK,"MaterialBalance_Error");
                            isOK = 2;
                            return false;
                        }
                        //解析物料平衡范围
                        String[] balances = materialAndBalance[1].split("-");
                        BigDecimal upperLimit ;//物料平衡上限
                        BigDecimal lowerLimit ;//物料平衡下限
                        try {
                            if (balances.length != 2){
                                PhaseErrorDialog phaseErrorDialog = new PhaseErrorDialog();
                                phaseErrorDialog.showDialog(LcAccountMaterialDAO0710.MSG_PACK,"MaterialBalance_Error");
                                isOK = 2;
                                return false;
                            }
                            upperLimit = BigDecimal.valueOf(Long.valueOf(balances[1]));
                            lowerLimit = BigDecimal.valueOf(Long.valueOf(balances[0]));

                        }catch (Exception e){
                            PhaseErrorDialog phaseErrorDialog = new PhaseErrorDialog();
                            phaseErrorDialog.showDialog(LcAccountMaterialDAO0710.MSG_PACK,"MaterialBalance_Error");
                            e.printStackTrace();
                            isOK = 2;
                            return false;
                        }
                        String pos = materialAndBalance[0];
                        int index = -1; //记录下标
                        //将不是表头的去除掉 ,获取行号对应的物料
                        for(int i = 0; i<materialList.size();i++){
                            AccountMaterialDAO0710 accountMaterialDAO0710 = materialList.get(i);
                            if (!accountMaterialDAO0710.isHeader()) continue;//不是表头
                            if (accountMaterialDAO0710.getMfcPosition().equals(pos)) {
                                index = i;
                                break;
                            }

                        }
                        if (index == -1){
                            PhaseErrorDialog phaseErrorDialog = new PhaseErrorDialog();
                            phaseErrorDialog.showDialog(LcAccountMaterialDAO0710.MSG_PACK,"MaterialBalance_Error");
                            isOK = 2;
                            return false;
                        }
                        AccountMaterialDAO0710 accountMaterialDAO0710 = materialList.get(index);
                        //获取设置上下值，判断物料平衡是否超限
                        double overLimit = isOverLimit(materialAndBalance[0] , executor,accountMaterialDAO0710);
                        if (overLimit == -1.0) {
                            isOK = 2;
                            return false;
                        }
                        //物料平衡不在范围
                        if (upperLimit.compareTo(BigDecimal.valueOf(overLimit)) < 0  || lowerLimit.compareTo(BigDecimal.valueOf(overLimit)) > 0){
                            //存入集合
                            balanceNotScope.add(new String[]{pos,accountMaterialDAO0710.getMaterialID(),MeasuredValueUtilities.createMV(String.valueOf(overLimit),"%").toString()});
                        }
                    }
                    if(balanceNotScope.size() < 1 ){
                        isOK = 0;//不触发异常，并且校验通过
                        return false;//没有超限物料
                    }
                    Boolean recordException = isRecordException(balanceNotScope);
                    if (recordException) {
                        isOK = 0;
                        return true;
                    }else {
                        isOK = 2;
                        return false;
                    }


                }catch (Exception e){
                    PhaseErrorDialog phaseErrorDialog = new PhaseErrorDialog();
                    phaseErrorDialog.showDialog(LcAccountMaterialDAO0710.MSG_PACK,"MaterialBalance_Error");
                    isOK = 2;
                    return false;
                }
            }

            @Override
            void createException(final MatAccountCompletionExceptions0710 exceptions) {


                //获取所有消耗的物料
                List<AccountMaterialDAO0710> materialList = exceptions.executor.getModel().getMaterialList();
                List<String[]> balanceNotScope = new ArrayList<>();
                //获取配置物料平衡检查
                MESParamString0100 paramString0100 =  exceptions.executor.getProcessParameterData(MESParamString0100.class,LcAccountMaterialDAO0710.Position_And_Material_Balance);
                String balanceValues = paramString0100.getValue();
                String[]  balanceValue = balanceValues.split(",");
                for (String materialAndBalances :balanceValue){
                    //拆分行号
                    String[] materialAndBalance = materialAndBalances.split(":");
                    //解析物料平衡范围
                    String[] balances = materialAndBalance[1].split("-");
                    BigDecimal upperLimit ;//物料平衡上限
                    BigDecimal lowerLimit ;//物料平衡下限
                    upperLimit = BigDecimal.valueOf(Long.valueOf(balances[1]));
                    lowerLimit = BigDecimal.valueOf(Long.valueOf(balances[0]));
                   //行号
                    String pos = materialAndBalance[0];
                    int index = -1; //记录下标
                    //将不是表头的去除掉 ,获取行号对应的物料
                    for(int i = 0; i<materialList.size();i++){
                        AccountMaterialDAO0710 accountMaterialDAO0710 = materialList.get(i);
                        if (!accountMaterialDAO0710.isHeader()) continue;//不是表头
                        if (accountMaterialDAO0710.getMfcPosition().equals(pos)) {
                            index = i;
                            break;
                        }

                    }
                    AccountMaterialDAO0710 accountMaterialDAO0710 = materialList.get(index);
                    //获取设置上下值，判断物料平衡是否超限
                    double overLimit = isOverLimit(materialAndBalance[0] , exceptions.executor,accountMaterialDAO0710);
                    //物料平衡不在范围
                    if (upperLimit.compareTo(BigDecimal.valueOf(overLimit)) < 0  || lowerLimit.compareTo(BigDecimal.valueOf(overLimit)) > 0){
                        //存入集合
                        balanceNotScope.add(new String[]{pos,accountMaterialDAO0710.getMaterialID(),MeasuredValueUtilities.createMV(String.valueOf(overLimit),"%").toString(),lowerLimit+"%"+"~"+upperLimit+"%"});
                    }
                }

                MESParamExcptEnableDef0200 paramExcptEnableDef0200  =  exceptions.executor.getProcessParameterData(MESParamExcptEnableDef0200.class, LcAccountMaterialDAO0710.Material_Balance_Check_Configuration);

                //将物料平衡不在范围的信息记录
                StringBuffer errorMag = new StringBuffer("");
                errorMag.append(paramExcptEnableDef0200.getMessage());
                errorMag.append("\n");
                errorMag.append(I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK,"MaterialBalanceNotRange_dialog"));

                for(String[] balance:balanceNotScope){
                    errorMag.append(I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK,"Material_Error",balance));
                }


                String exceptionStr =  errorMag.toString();
                long risk = paramExcptEnableDef0200.getRiskAssessment();
                IMESExceptionRecord.RiskClass riskclass = IMESExceptionRecord.RiskClass.valueOf(risk);
                exceptions.executor.displayException(getCheckKey(),riskclass,exceptionStr);
            }

            /**
             * * 判断是否物料超限
             *              * 根据转入过来的物料行号，判断是否平衡物料超限
             * @param pos 行号
             * @param executor RtPhaseExecutorMatAlterAcct0010
             * @param accountMaterialDAO0710 物料
             * @return  -1参数不符合  物料平衡
             */
            private Double isOverLimit(String pos,RtPhaseExecutorMatAlterAcct0010 executor,AccountMaterialDAO0710 accountMaterialDAO0710){
                OrderStepInput osi = executor.getOrderStep().getOrderStepInput(accountMaterialDAO0710.getOsiKey());
                List<OrderStepInput> allMasterOSIs = executor.getModel().getMasterOSIsForPhase();
                List<IMESMaterialParameter> materialParameters = executor.getPhase().getMaterialParameters();

                //总消耗数量=消耗数量+折算数量
                MeasuredValue selfAndReplaceConsumedQty = MeasuredValueUtilities.createZero();

                //是否识别了物料
                if (accountMaterialDAO0710.getIdentifiedQtyMV() == null  || "".equals(accountMaterialDAO0710.getIdentifiedQtyMV())) {
                    //没有识别数量
                    PhaseErrorDialog phaseErrorDialog = new PhaseErrorDialog();
                    phaseErrorDialog.showDialog(LcAccountMaterialDAO0710.MSG_PACK,"MaterialBalanceNotIden_Error");

                    return -1.0;
                }
                try {
                    selfAndReplaceConsumedQty = getSelfAndReplaceConsumedQty(osi, allMasterOSIs, materialParameters);
                } catch (MESIncompatibleUoMException e) {
                    e.printStackTrace();
                }
                //取样数量
                String sampledQty = accountMaterialDAO0710.getSampledQty();
                MeasuredValue sampledQtyMV = StringUtils.isEmpty(sampledQty)?MeasuredValueUtilities.createZero(accountMaterialDAO0710.getIdentifiedQtyMV().getUnitOfMeasure()):MeasuredValueUtilities.createMV(sampledQty);
                //报废数量
                String wastedQty = accountMaterialDAO0710.getWastedQty();
                MeasuredValue wastedQtyMV = StringUtils.isEmpty(wastedQty)?MeasuredValueUtilities.createZero(accountMaterialDAO0710.getIdentifiedQtyMV().getUnitOfMeasure()):MeasuredValueUtilities.createMV(wastedQty);

                //退库数量
                String returnedQty = accountMaterialDAO0710.getReturnedQty();
                MeasuredValue returnedQtyMV = StringUtils.isEmpty(returnedQty)?MeasuredValueUtilities.createZero(accountMaterialDAO0710.getIdentifiedQtyMV().getUnitOfMeasure()):MeasuredValueUtilities.createMV(returnedQty);
                //识别量
                IMeasuredValue identifiedQtyMV = accountMaterialDAO0710.getIdentifiedQtyMV();

                //计算物料平衡  = （消耗量+退库量+取样量+报废量）/识别量
                IMeasuredValue materialBalanceMv = MeasuredValueUtilities.createZero();//物料平衡
                IMeasuredValue materialTotalMv;//总消耗量
                try {
                     materialTotalMv = wastedQtyMV.add(returnedQtyMV).add(selfAndReplaceConsumedQty).add(sampledQtyMV);
                     materialBalanceMv = materialTotalMv.divide(identifiedQtyMV,4);
                }catch (Exception e){
                        PhaseErrorDialog phaseErrorDialog = new PhaseErrorDialog();
                        phaseErrorDialog.showDialog(LcAccountMaterialDAO0710.MSG_PACK,"MaterialBalance_Error");
                        e.printStackTrace();
                        return -1.0;
                }
                return materialBalanceMv.getValue().doubleValue()*100;
            }

            protected Boolean isRecordException(List<String[]> materialList){
                StringBuffer stringBuffer = new StringBuffer("");
                stringBuffer.append(I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK,"MaterialBalanceNotRange_dialog"));
                for (String[] materialInfo:materialList){
                    stringBuffer.append(I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK,"MaterInfo_dialog",materialInfo));

                }
                stringBuffer.append(I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK,"RecordAnomalieDialog"));
                PhaseQuestionDialog questionDialog = new PhaseQuestionDialog();
                int userChoice = questionDialog.showDialog(stringBuffer.toString());
                isOK = userChoice;
                //点击否
                if (userChoice != 0) {
                    return false;
                }else {//点击是
                    return true;
                }
            }
        };

        private final String parameterName;
        private static Integer isOK = -1;
        private SystemtriggeredExceptionEnum(final String name) {
            parameterName = name;
        }

        /**
         * Determine if condition to record a system triggered exception is fulfilled.
         * <p>
         *
         * @param executor the executor
         * @return {@code true} if the condition for system triggered exception is fulfilled
         */
        abstract boolean conditionForSystemtriggeredException(RtPhaseExecutorMatAlterAcct0010 executor);

        /**
         * Creates a new system triggered exception within the given exception collection.
         * <p>
         *
         * @param exceptions the collection holding all system triggered exceptions
         */
        abstract void createException(final MatAccountCompletionExceptions0710 exceptions);

        /**
         * @return the name of the parameter
         */
        String getParameterName() {
            return parameterName;
        }

        /**
         * @return the check key for this system triggered exception
         */
        String getCheckKey() {
            return name();
        }
    }

    /**
     * Constructor.
     * <p>
     *
     * @param executor the executor
     */
    MatAccountCompletionExceptions0710(final RtPhaseExecutorMatAlterAcct0010 executor) {
        super(executor);
    }



    /**
     * Checks condition for any of the available system triggered exception and triggers a combined system triggered
     * exception if at least one exception condition is fulfilled.
     * <p>
     *
     * @return {@code true} if no system triggered exception was triggered, i.e. all checks passed successfully
     */
    public boolean checkAndHandleExceptions() {
        for (final SystemtriggeredExceptionEnum e : SystemtriggeredExceptionEnum.values()) {
            //判断异常是否已经签名，是否要触发异常
            if (isNotSigned(e.getCheckKey()) && e.conditionForSystemtriggeredException(executor)){

       /*         if(SystemtriggeredExceptionEnum.MaterialBalanceCheckConfiguration.equals(e)){
                    return false;
                }*/
                e.createException(this);
                //有消耗子批次或组合比例不一致
                if(SystemtriggeredExceptionEnum.UnconsumedSublot.equals(e) ||
                        SystemtriggeredExceptionEnum.AbnormalMaterialCombinationRatio.equals(e)
                || SystemtriggeredExceptionEnum.QuantityOutOfTolerance.equals(e)
                        || SystemtriggeredExceptionEnum.MaterialBalanceCheckConfiguration.equals(e)){
                    return false;
                }
                return !showExceptionDialog();
            }
            else if(SystemtriggeredExceptionEnum.UnconsumedSublot.equals(e) && SystemtriggeredExceptionEnum.UnconsumedSublot.isOK == 2){ ;
                SystemtriggeredExceptionEnum.UnconsumedSublot.isOK = -1;
                return false;
            }
            else if(SystemtriggeredExceptionEnum.AbnormalMaterialCombinationRatio.equals(e) && SystemtriggeredExceptionEnum.AbnormalMaterialCombinationRatio.isOK == 2){ ;
                SystemtriggeredExceptionEnum.AbnormalMaterialCombinationRatio.isOK = -1;
                return false;
            }else if(SystemtriggeredExceptionEnum.QuantityOutOfTolerance.equals(e) && SystemtriggeredExceptionEnum.QuantityOutOfTolerance.isOK == 2){
                SystemtriggeredExceptionEnum.QuantityOutOfTolerance.isOK = -1;
                return false;
            }else if(SystemtriggeredExceptionEnum.MaterialBalanceCheckConfiguration.equals(e) && SystemtriggeredExceptionEnum.MaterialBalanceCheckConfiguration.isOK == 2){
                SystemtriggeredExceptionEnum.MaterialBalanceCheckConfiguration.isOK = -1;
                return false;
            }
        }
        // If we don't show an exception dialog, then all checks have passed successfully.
        // We could remove pending checkkeys.
        return true;
    }

    /**
     * @param checkKey the check key to test. It may be a concatenated check key
     * @return {@code true} if the check key or a part of it belongs to system triggered exception checkkey
     */
    public static boolean isCompletionCheck(final String checkKey) {
        final String[] checkKeys = StringUtils.split(checkKey, StringConstants.LINE_BREAK);
        for (final String key : checkKeys) {
            for (final SystemtriggeredExceptionEnum value : SystemtriggeredExceptionEnum.values()) {
                if (value.getCheckKey().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected String getDialogTextForMultipleSystemtriggeredExceptions() {
        return I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "MultipleSystemtriggeredExceptions_Text");
    }

    /**
     * dustin:获取主料及其对应的替代料数量
     *
     * @param osi
     * @param allMasterOSIs
     * @param materialParameters
     * @return
     */
    private static MeasuredValue getSelfAndReplaceConsumedQty(OrderStepInput osi, List<OrderStepInput> allMasterOSIs,
                                                              List<IMESMaterialParameter> materialParameters) throws MESIncompatibleUoMException {
        //获取本身消耗数量
        MeasuredValue totalConsumedQtyMV = MESNamedUDAOrderStepInput.getTotalConsumedQuantity(osi);
        /**计算替代料数量**/
        //获取该物料是否设置为主料
        List<IMESMaterialParameter> matParamList = materialParameters.stream()
                .filter(p -> p.getMaterial() == osi.getPart()
                        && p.getATRow().getValue("LC_isMainPart") != null
                        && (Boolean) p.getATRow().getValue("LC_isMainPart")).collect(Collectors.toList());
        if (matParamList == null || matParamList.size() == 0) {
            return totalConsumedQtyMV;
        }
        MESNamedUDAMaterialParameter matParam = new MESNamedUDAMaterialParameter(matParamList.get(0));
        //获取换算比例
        BigDecimal mainRatio = matParam.getReplaceRatio();
        //获取替代组号
        String masterReplaceGroupName = matParam.getReplaceGroupName();
        //判断是否存在替代组号
        if (!StringUtils.isEmpty(masterReplaceGroupName)) {
            //获取替代物料集合(不能包含本身)
            Part material = matParam.getMatParam().getMaterial();
            List<IMESMaterialParameter> replaceGroupNameList = materialParameters.stream().filter(p -> {
                MESNamedUDAMaterialParameter replaceMatParam = new MESNamedUDAMaterialParameter(p);
                String replaceGroupName = replaceMatParam.getReplaceGroupName();
                Part replaceMaterial = p.getMaterial();
                return masterReplaceGroupName.equals(replaceGroupName) && !material.equals(replaceMaterial);
            }).collect(Collectors.toList());

            //替代消耗量
            IMeasuredValueConverter converter = MeasuredValueUtilities.getMVConverter(osi.getPart());

            IUnitOfMeasure unitOfMeasure = osi.getPlannedQuantity().getUnitOfMeasure();
            MeasuredValue replaceConsumedQtyMV = MeasuredValueUtilities.createZero(unitOfMeasure);
            //组合组号HashMap:组合组号-可消耗最小值
            Map<String, MeasuredValue> groupConsumedMap = new HashMap<>();
            //遍历替代料集合
            for (IMESMaterialParameter item : replaceGroupNameList) {
                MESNamedUDAMaterialParameter itemMatParam = new MESNamedUDAMaterialParameter(item);
                BigDecimal replaceRatio = itemMatParam.getReplaceRatio();
                //获取组合组号
                String combinationGroup = itemMatParam.getCombinationGroup();
                //根据OSI获取替代消耗数量
                List<OrderStepInput> consumedList = allMasterOSIs.stream().filter(p -> p.getPart() == item.getMaterial()).collect(Collectors.toList());
                for (OrderStepInput replaceOSI : consumedList) {
                    MeasuredValue consumedQtyMV = MESNamedUDAOrderStepInput.getTotalConsumedQuantity(replaceOSI);
                    if (consumedQtyMV == null || replaceRatio == null) {
                        groupConsumedMap.put(combinationGroup, MeasuredValueUtilities.createZero(unitOfMeasure));
                        continue;
                    }
                    BigDecimal consumedQty = consumedQtyMV.getValue();
                    BigDecimal calcConsumedQty;
                    try {
                        calcConsumedQty = consumedQty.divide(replaceRatio).multiply(mainRatio);
                   }catch (ArithmeticException e){
                        calcConsumedQty = consumedQty.divide(replaceRatio,6,BigDecimal.ROUND_HALF_UP).multiply(mainRatio).multiply(mainRatio);
                    }
                    MeasuredValue calcConsumedQtyMV = MeasuredValueUtilities.createMV(calcConsumedQty, consumedQtyMV.getUnitOfMeasure());
                    //组合组号是空，表示是完全替代料计算
                    if (Strings.isEmpty(combinationGroup)) {
                        replaceConsumedQtyMV = MeasuredValueUtilities.addArgsOptional(replaceConsumedQtyMV,
                                calcConsumedQtyMV, converter);
                    } else {
                        //组合组号计算，以最小值计算
                        MeasuredValue minConsumedQtyMV = groupConsumedMap.get(combinationGroup);
                        if (minConsumedQtyMV == null) {
                            groupConsumedMap.put(combinationGroup, calcConsumedQtyMV);
                            continue;
                        }
                        BigDecimal minConsumedQty = minConsumedQtyMV.getValue();
                        minConsumedQtyMV = calcConsumedQty.compareTo(minConsumedQty) < 0 ? calcConsumedQtyMV : minConsumedQtyMV;
                        groupConsumedMap.put(combinationGroup, minConsumedQtyMV);
                    }
                }
            }
            //增加组合组号数量
            if (groupConsumedMap != null) {
                for (MeasuredValue minConsumedQtyMV : groupConsumedMap.values()) {
                    replaceConsumedQtyMV = MeasuredValueUtilities.addArgsOptional(replaceConsumedQtyMV,
                            minConsumedQtyMV, converter);
                }
            }
            //替代料数量+本身数量
            if (totalConsumedQtyMV != null) {
                replaceConsumedQtyMV = MeasuredValueUtilities.addArgsOptional(totalConsumedQtyMV,
                        replaceConsumedQtyMV, converter);
            }
            totalConsumedQtyMV = replaceConsumedQtyMV;
        }
        return totalConsumedQtyMV;
    }
}
