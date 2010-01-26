/**
 * 
 */
package org.pentaho.agilebi.pdi.modeler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DimensionMetaDataCollection extends AbstractMetaDataModelNode<DimensionMetaData> implements Serializable {

  private static final long serialVersionUID = -6327799582519270107L;
  
  private String name = "Dimensions";

  public DimensionMetaDataCollection(){
    this.valid = false;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isUiExpanded(){
    return true;
  }

  private transient PropertyChangeListener listener = new PropertyChangeListener(){
    public void propertyChange(PropertyChangeEvent evt) {
      fireCollectionChanged();
    }
  };

  //TODO: investigate using "this" form of notification in super-class
  protected void fireCollectionChanged() {
    this.changeSupport.firePropertyChange("children", null, this); //$NON-NLS-1$
    validateNode();
  }

  @Override
  public void onAdd(DimensionMetaData child) {
    child.setParent(this);
    child.addPropertyChangeListener("children", listener); //$NON-NLS-1$
    child.addPropertyChangeListener("valid",validListener); //$NON-NLS-1$
  }

  @Override
  public void onRemove(DimensionMetaData child) {
    child.removePropertyChangeListener(listener);
    child.removePropertyChangeListener(validListener);
  }

  @Override
  public String getValidImage() {
    return "images/sm_folder_icon.png"; //$NON-NLS-1$
  }

  @Override
  public void validate() {
    valid = true;
    validationMessages.clear();
    if (size() == 0) {
      validationMessages.add("Model requires at least one Dimension");
      valid = false;
    }
    List<String> usedNames = new ArrayList<String>();
    
    for(DimensionMetaData dim: children){
      valid &= dim.isValid();
      validationMessages.addAll(dim.getValidationMessages());
      if(usedNames.contains(dim.getName())){
        valid = false;
        validationMessages.add(Messages.getString("duplicate_dimension_names"));
      }
      usedNames.add(dim.getName());
    }
    this.firePropertyChange("valid", null, valid);
  }
  
  public boolean isEditingDisabled(){
    return true;
  }
  
  @Override
  public Class<? extends ModelerNodePropertiesForm> getPropertiesForm() {
    return GenericPropertiesForm.class;
  }
  
}