package com.leateck.expression;

import com.rockwell.mes.commons.base.ifc.expression.processing.IExpressionTypeDescriptor;
import com.rockwell.mes.commons.base.ifc.expression.processing.function.IExpressionFunctionTypeInferenceSupport;
import com.rockwell.mes.commons.base.ifc.expression.processing.function.IExpressionFunctionTypeInferencer;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88Equipment;

public class FunctionTypeInferencerResetNumber implements IExpressionFunctionTypeInferencer {

    @Override
    public IExpressionTypeDescriptor inferResultTypeForFunctionCall(IExpressionFunctionTypeInferenceSupport support) {
        support.checkArgumentHasType(1, IMESS88Equipment.class);
        support.checkArgumentHasType(2, String.class);
        return support.createTypeDescriptor(Boolean.class, true);
    }
}
