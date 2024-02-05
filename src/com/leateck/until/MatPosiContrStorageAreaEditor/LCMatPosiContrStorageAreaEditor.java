package com.leateck.until.MatPosiContrStorageAreaEditor;

import com.datasweep.compatibility.client.LocationFilter;
import com.jidesoft.grid.ListComboBoxCellEditor;
import com.rockwell.mes.clientfw.commons.ifc.grid.IMESExtendedPropertyDescriptor;
import com.rockwell.mes.clientfw.commons.ifc.grid.converter.MESConverterContext;
import com.rockwell.mes.clientfw.commons.ifc.grid.editor.DefaultMESCellEditorFactory;
import com.rockwell.mes.clientfw.commons.ifc.grid.editor.IMESCellEditorFactory;
import com.rockwell.mes.commons.base.ifc.services.PCContext;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class LCMatPosiContrStorageAreaEditor extends ListComboBoxCellEditor{

	private static final long serialVersionUID = 6193606057598396080L;
	private static final IMESCellEditorFactory CELL_EDITOR_FACTORY;

	public LCMatPosiContrStorageAreaEditor(){
		this(getData());
	}

	public LCMatPosiContrStorageAreaEditor(List<String> data) {
		super(data.toArray(),String.class);
	}
	
	private static List<String> getData() {
		List<String> data = new ArrayList<>();
		String[] value;
		String sql = "select location from LOCATION_UV lu where X_hierarchy_I = '1'";
		Vector vector = PCContext.getFunctions().getArrayDataFromActive(sql);

		for (int i = 0; i < vector.size(); i++) {
			value = (String [])vector.get(i);
            data.add(value[0]);
		}
		return data;
	}
	
	public static class LCMatPosiContrStorageAreaEditorFactory extends DefaultMESCellEditorFactory{

		public LCMatPosiContrStorageAreaEditorFactory() {
			this((Class<? extends CellEditor>) LCMatPosiContrStorageAreaEditor.class);
		}

		protected LCMatPosiContrStorageAreaEditorFactory(Class<? extends CellEditor> editorClass) {
			super(editorClass);
		}
		
		public MESConverterContext createConverterContext(final IMESExtendedPropertyDescriptor beanPropDescr){
			return new MESConverterContext("StorageAreaConverter",getString());
		}
		
		protected List<String> getString(){
			return LCMatPosiContrStorageAreaEditor.getData();
		}
		
	}
	static{
		CELL_EDITOR_FACTORY = new LCMatPosiContrStorageAreaEditorFactory();
	}

}
