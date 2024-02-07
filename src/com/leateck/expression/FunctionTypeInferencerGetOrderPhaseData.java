package com.leateck.expression;

import com.rockwell.mes.commons.base.ifc.expression.processing.IExpressionTypeDescriptor;
import com.rockwell.mes.commons.base.ifc.expression.processing.function.IExpressionFunctionTypeInferenceSupport;
import com.rockwell.mes.commons.base.ifc.expression.processing.function.IExpressionFunctionTypeInferencer;

public final class FunctionTypeInferencerGetOrderPhaseData implements IExpressionFunctionTypeInferencer {

    @Override
    public IExpressionTypeDescriptor inferResultTypeForFunctionCall(IExpressionFunctionTypeInferenceSupport support) {
        support.checkArgumentHasType(1, String.class);
        support.checkArgumentHasType(2, String.class);
        support.checkArgumentHasType(3, String.class);
        support.checkArgumentHasType(4, String.class);
        support.checkArgumentHasType(5, String.class);
        support.checkArgumentHasType(6, String.class);
        if (support.hasErrors()) {
            return support.createTypeDescriptorForUnknownType();

        }
//        return support.createTypeDescriptor(Object.class, true);
        return support.createTypeDescriptorForUnknownType();
    }

}
