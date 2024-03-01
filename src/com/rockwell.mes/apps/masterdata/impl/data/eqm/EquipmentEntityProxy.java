package com.rockwell.mes.apps.masterdata.impl.data.eqm;

import com.rockwell.mes.apps.masterdata.ifc.data.AdvancedCardContentAttribute;
import com.rockwell.mes.apps.masterdata.ifc.data.IMasterDataObjectType;
import com.rockwell.mes.apps.masterdata.ifc.data.eqm.IEquipmentObjectType;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.AbstractEquipmentEntityClassCommonsProxy;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentEntityDataHolder;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class EquipmentEntityProxy extends AbstractEquipmentEntityClassCommonsProxy<EquipmentEntityDataHolder> {
    @AdvancedCardContentAttribute
    private final List<String> classNames = new ArrayList<String>();

    @AdvancedCardContentAttribute
    private String parentEntityName = null;

    @AdvancedCardContentAttribute
    private final List<String> childEntityNames = new ArrayList<String>();

    @AdvancedCardContentAttribute
    private final List<String> indirectParentEntityNames = new ArrayList<String>();

    @AdvancedCardContentAttribute
    private final List<String> statusGraphNames = new ArrayList<String>();

    private String barcode;

    private IMESChoiceElement expiryChangeErrorBehavior;

    private String expiryChangeErrorBehaviorAsLocalizedMessage;

    @AdvancedCardContentAttribute
    private String templateUsed;

    public EquipmentEntityProxy(IEquipmentObjectType iEquipmentObjectType, String string, Color color, Color color2, String string2) {
        super(iEquipmentObjectType, string, color, color2, string2);
    }

    protected final void initClassNames(Collection<String> collection) {
        this.assureNotInitialized("ClassNames");
        this.classNames.addAll(collection);
    }

    protected final void initParentEntityName(String string) {
        this.assureNotInitialized("ParentEntityName");
        this.parentEntityName = string;
    }

    protected final void initChildEntityNames(Collection<String> collection) {
        this.assureNotInitialized("ChildEntityNames");
        this.childEntityNames.addAll(collection);
    }

    protected final void initIndirectParentEntityNames(Collection<String> collection) {
        this.assureNotInitialized("IndirectParentEntityNames");
        this.indirectParentEntityNames.addAll(collection);
    }

    protected final void initStatusGraphNames(Collection<String> collection) {
        this.assureNotInitialized("StatusGraphNames");
        this.statusGraphNames.addAll(collection);
    }

    protected void initBarcode(String string) {
        this.assureNotInitialized("Barcode");
        this.barcode = string;
    }

    protected final void initExpiryChangeErrorBehavior(IMESChoiceElement iMESChoiceElement) {
        this.assureNotInitialized("ExpiryChangeErrorBehavior");
        this.expiryChangeErrorBehavior = iMESChoiceElement;
        this.expiryChangeErrorBehaviorAsLocalizedMessage = this.expiryChangeErrorBehavior.getLocalizedMessage();
    }

    void initTemplateUsed(String string) {
        this.assureNotInitialized("TemplateEntityIdentifier");
        this.templateUsed = string;
    }

    protected void addSearchTexts(Collection<String> collection) {
        super.addSearchTexts(collection);
        for (String string : this.classNames) {
            collection.add(string);
        }
        if (!StringUtils.isEmpty((CharSequence) this.parentEntityName)) {
            collection.add(this.parentEntityName);
        }
        for (String string : this.childEntityNames) {
            collection.add(string);
        }
        for (String string : this.indirectParentEntityNames) {
            collection.add(string);
        }
        for (String string : this.statusGraphNames) {
            collection.add(string);
        }
        collection.add(this.getManufacturer());
        collection.add(this.getModelName());
        collection.add(this.getEquipmentLevelAsLocalizedMessage());
        collection.add(this.getStatus());
        collection.add(this.getSerialNumber());
        //
        collection.add(this.getProcessPackName());

        collection.add(this.barcode);
        collection.add(this.expiryChangeErrorBehaviorAsLocalizedMessage);
        if (!StringUtils.isEmpty((CharSequence) this.templateUsed)) {
            collection.add(this.templateUsed);
        }
    }

    public boolean isDraggable() {
        return true;
    }

    public void normalize() {
        Collections.sort(this.classNames);
        Collections.sort(this.childEntityNames);
        Collections.sort(this.indirectParentEntityNames);
        Collections.sort(this.statusGraphNames);
    }

    public String toString() {
        return "[equipment entity proxy: " + this.propertiesToString() + "]";
    }

    String propertiesToString() {
        return "identifier=" + this.getIdentifier() + ", object type=" + (Object) this.getObjectType() + ", dbKey=" + this.getDBKey() + " title="
                + this.getTitle() + ", groupId=" + this.getMainGroupId() + " / " + this.getSubGroupId() + ", short desc=" + this.getShortDescription()
                + ", desc=" + this.getDescription() + ", level=" + (Object) this.getEquipmentLevel() + ", status=" + this.getStatus()
                + ", classNames=" + this.classNames + ", parentEntityName=" + this.parentEntityName + ", childEntityNames=" + this.childEntityNames
                + ", indirectParentEntityNames=" + this.indirectParentEntityNames + ", statusGraphNames=" + this.statusGraphNames;
    }
}