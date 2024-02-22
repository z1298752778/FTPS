package com.leateck.phase.wdmaterialidentification0100;

public enum IdentificationResult0610
{
    WEIGH,
    ONLY_IDENTIFICATION,
    RELEASE_SCALE {
        @Override
        public void createCompletedUI(MatIdentView0610 view) {
            view.createUICompletedForReleaseScale((MatIdentView0610.IViewDataIdentificationResult)((IWDMatIdentModel0610)view.getModel()).getRtPhaseData());
        }
    },
    COMPLETED {
        @Override
        public void createCompletedUI(MatIdentView0610 view) {
            final MESRtPhaseDataWDMatIdent0010 MESRtPhaseDataWDMatIdent0010 = ((IWDMatIdentModel0610)view.getModel()).getRtPhaseData();
            if (MESRtPhaseDataWDMatIdent0010.getIdentifiedOnly() != null && MESRtPhaseDataWDMatIdent0010.getIdentifiedOnly()) {
                view.createUIWithIdentificationResult((MatIdentView0610.IViewDataIdentificationResult) MESRtPhaseDataWDMatIdent0010);
            }
            else {
                view.createEmptyUIForIdentificationResult((MatIdentView0610.IViewDataIdentificationResult) MESRtPhaseDataWDMatIdent0010);
            }
        }
    };

    public void createCompletedUI(MatIdentView0610 view) {
        //view.createUIWithIdentificationResult((MatIdentView0610.IViewDataIdentificationResult)((IWDMatIdentModel0610)view.getModel()).getRtPhaseData());
        view.createUIWithIdentificationResult((MatIdentView0610.IViewDataIdentificationResult) ((IWDMatIdentModel0610)view.getModel()).getRtPhaseData());
    }
}
