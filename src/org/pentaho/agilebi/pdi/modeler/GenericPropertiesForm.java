/*
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2010 Pentaho Corporation..  All rights reserved.
 */
package org.pentaho.agilebi.pdi.modeler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.containers.XulVbox;

public class GenericPropertiesForm extends AbstractModelerNodeForm<AbstractMetaDataModelNode>{

  private XulTextbox name;
  private XulVbox messageBox;
  private XulLabel messageLabel;
  private AbstractMetaDataModelNode node;
  private PropertyChangeListener validListener = new PropertyChangeListener(){
    public void propertyChange(PropertyChangeEvent arg0) {
      setObject(node);
    }
  };
  
  public GenericPropertiesForm(){
    super("genericProps");
  }
  
  public void setObject(AbstractMetaDataModelNode node) {
    if(this.node != null){
      this.node.removePropertyChangeListener(validListener);
    }
    this.node = node;
    this.node.addPropertyChangeListener("valid", validListener);
    messageLabel.setValue(node.getValidationMessagesString());
    messageBox.setVisible(node.getValidationMessages().size() > 0);
  }

  public void init() {
    super.init();
    messageBox = (XulVbox) document.getElementById("generic_message");
    messageLabel = (XulLabel) document.getElementById("generic_message_label");
    
  }
  
}

