package com.leateck.phase.wdmaterialidentification0100;

import com.rockwell.mes.apps.ebr.ifc.phase.ui.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.rockwell.mes.clientfw.commons.ifc.view.activities.grid.*;
import java.util.*;
import java.util.List;

import com.rockwell.mes.clientfw.commons.ifc.view.activities.*;

public class MatIdentActiveGrid0610 extends PhaseStandardGrid
{
    private static final long serialVersionUID = 1L;
    private final int theColumnIndex;
    private final IWDMatIdentModel0610 theModel;

    public MatIdentActiveGrid0610(final int editableColumnIndex, final IWDMatIdentModel0610 model) {
        super((IPhaseStandardGridConfigurator)new PhaseStandardGridConfigurator());
        this.theColumnIndex = editableColumnIndex;
        this.theModel = model;
    }

    protected void setupCustomRenderers() {
        if (this.getGrid().getColumnModel().getColumnCount() > this.theColumnIndex) {
            this.setupCheckBoxColumn(this.getGrid().getColumnModel().getColumn(this.theColumnIndex));
        }
        super.setupCustomRenderers();
    }

    private void setupCheckBoxColumn(final TableColumn column) {
        column.setCellRenderer(new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
                if (!((IMatIdentRow0610)((IGridTableModel)table.getModel()).getObject(row)).isMasterRow()) {
                    final JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    label.setText(null);
                    return label;
                }
                return table.getDefaultRenderer(table.getColumnClass(column)).getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });
    }

    protected PhaseStandardGrid.ExtendedGridTableModel createTableModel(final GridBeanUtility gbu, final List<IGridColumnDescriptor> columns, final Object[] objects) {
        return new MatIdentExtendedGridTableModel(gbu, columns, this.getUseShortLabels(), this.getWithSelectColumn(), hasTouchScreen(), objects);
    }

    private static class PhaseStandardGridConfigurator implements IPhaseStandardGridConfigurator
    {
        public boolean isSubrow(final Object rowObject) {
            return !((IMatIdentRow0610)rowObject).isMasterRow();
        }
    }

    private class MatIdentExtendedGridTableModel extends PhaseStandardGrid.ExtendedGridTableModel
    {
        private static final long serialVersionUID = 1L;

        public MatIdentExtendedGridTableModel(final GridBeanUtility gbu, final List<IGridColumnDescriptor> columns, final boolean useShortLabel, final boolean withSelectColumn, final boolean withTouchScreen, final Object[] objectArray) {
            super(gbu, columns, useShortLabel, withSelectColumn, withTouchScreen, objectArray);
        }

        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            boolean editableIdOnly = false;
            if (columnIndex == MatIdentActiveGrid0610.this.theColumnIndex) {
                editableIdOnly = ((IMatIdentRow0610)this.getObject(rowIndex)).isEditableIdOnly();
            }
            return editableIdOnly;
        }

        public void setValueAt(final Object value, final int rowIndex, final int columnIndex) {
            if (value instanceof Boolean && columnIndex == MatIdentActiveGrid0610.this.theColumnIndex) {
                final IMatIdentRow0610 matIdentRow0610 = (IMatIdentRow0610)this.getObject(rowIndex);
                final boolean booleanValue = (boolean)value;
                matIdentRow0610.setIdOnly(booleanValue);
                MatIdentActiveGrid0610.this.theModel.setIdOnly(matIdentRow0610.getOSI(), booleanValue);
            }
        }
    }
}
