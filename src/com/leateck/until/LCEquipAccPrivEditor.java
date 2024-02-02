package com.leateck.until;

import java.util.ArrayList;
import java.util.List;

import javax.swing.CellEditor;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.DsList;
import com.datasweep.compatibility.client.Filter;
import com.datasweep.compatibility.client.NamedFilter;
import com.jidesoft.grid.ListComboBoxCellEditor;
import com.rockwell.mes.clientfw.commons.ifc.grid.IMESExtendedPropertyDescriptor;
import com.rockwell.mes.clientfw.commons.ifc.grid.converter.MESConverterContext;
import com.rockwell.mes.clientfw.commons.ifc.grid.editor.DefaultMESCellEditorFactory;
import com.rockwell.mes.clientfw.commons.ifc.grid.editor.IMESCellEditorFactory;
import com.rockwell.mes.commons.base.ifc.services.PCContext;

public class LCEquipAccPrivEditor extends ListComboBoxCellEditor{

	/**
	 * 权限下拉列表工厂类
	 */
	protected static final String ACCESS_FILTER_LIST = "LC_AccessFilterList";
	private static final long serialVersionUID = 6193606057598396078L;
	private static final IMESCellEditorFactory CELL_EDITOR_FACTORY;
	
	public LCEquipAccPrivEditor(){
		this(getData());
	}

	public LCEquipAccPrivEditor(List<String> data) {
		super(data.toArray(),String.class);
	}
	
	private static List<String> getData() {
		List<String> data = new ArrayList<String>();
		List<AccessPrivilege> accessRights = new ArrayList<AccessPrivilege>();
		DsList partAccessFilter = PCContext.getFunctions().getList(ACCESS_FILTER_LIST);
        NamedFilter filter = PCContext.getFunctions().getFilterByName(partAccessFilter.getItem(2).toString());
        Filter accessFilter = filter.getFilter();
        try {
        	accessRights = accessFilter.exec();
        	for (AccessPrivilege accessPrivilege : accessRights) {
				data.add(accessPrivilege.getName());
			}
		} catch (DatasweepException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	public static class LCEquipAccPrivEditorFactory extends DefaultMESCellEditorFactory{

		public LCEquipAccPrivEditorFactory() {
			this((Class<? extends CellEditor>) LCEquipAccPrivEditor.class);
		}

		protected LCEquipAccPrivEditorFactory(Class<? extends CellEditor> editorClass) {
			super(editorClass);
		}
		
		public MESConverterContext createConverterContext(final IMESExtendedPropertyDescriptor beanPropDescr){
			return new MESConverterContext("EquipAccPriv",getAccess());
		}
		
		protected List<String> getAccess(){
			return LCEquipAccPrivEditor.getData();
		}
		
	}
	static{
		CELL_EDITOR_FACTORY = new LCEquipAccPrivEditorFactory();
	}

}
