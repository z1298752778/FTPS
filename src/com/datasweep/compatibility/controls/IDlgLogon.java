package com.datasweep.compatibility.controls;

import java.awt.*;

public interface IDlgLogon
{
    boolean showDialog();
    
    String[] getResult();
    
    void dispose();
    
    void setModalityType(final Dialog.ModalityType p0);
}
