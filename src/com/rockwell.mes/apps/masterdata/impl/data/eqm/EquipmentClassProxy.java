package com.rockwell.mes.apps.masterdata.impl.data.eqm;

import com.rockwell.mes.apps.masterdata.ifc.data.AdvancedCardContentAttribute;
import com.rockwell.mes.apps.masterdata.ifc.data.eqm.IEquipmentObjectType;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.AbstractEquipmentEntityClassCommonsProxy;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentClassDataHolder;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EquipmentClassProxy extends AbstractEquipmentEntityClassCommonsProxy<EquipmentClassDataHolder> {
    @AdvancedCardContentAttribute
    private final List<String> entityNames = new ArrayList<String>();

    @AdvancedCardContentAttribute
    private final List<String> statusGraphNames = new ArrayList<String>();

    public EquipmentClassProxy(IEquipmentObjectType iEquipmentObjectType, Color color, Color color2, String string) {
        super(iEquipmentObjectType, null, color, color2, string);
    }

    protected final void initEntityNames(Collection<String> collection) {
        this.assureNotInitialized("EntityNames");
        this.entityNames.addAll(collection);
    }

    protected final void initStatusGraphNames(Collection<String> collection) {
        this.assureNotInitialized("StatusGraphNames");
        this.statusGraphNames.addAll(collection);
    }

    protected void addSearchTexts(Collection<String> collection) {
        super.addSearchTexts(collection);
        for (String string : this.entityNames) {
            collection.add(string);
        }
        for (String string : this.statusGraphNames) {
            collection.add(string);
        }
        collection.add(this.getManufacturer());
        collection.add(this.getModelName());
        collection.add(this.getStatus());

        collection.add(this.getProcessPackName());

        collection.add(this.getEquipmentLevelAsLocalizedMessage());
        collection.add(this.getSerialNumber());
    }

    public boolean isDraggable() {
        return true;
    }

    public void normalize() {
        Collections.sort(this.entityNames);
        Collections.sort(this.statusGraphNames);
    }

    public String toString() {
        return "[equipment class proxy: identifier=" + this.getIdentifier() + ", object type=" + (Object) this.getObjectType() + ", dbKey="
                + this.getDBKey() + " title=" + this.getTitle() + ", groupId=" + this.getMainGroupId() + ", short desc=" + this.getShortDescription()
                + ", desc=" + this.getDescription() + ", level=" + (Object) this.getEquipmentLevel() + ", status=" + this.getStatus()
                + ", entityNames=" + this.entityNames + ", statusGraphNames=" + this.statusGraphNames + "]";
    }
}