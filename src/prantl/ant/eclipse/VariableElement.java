// Copyright 2005-2006 Ferdinand Prantl <prantl@users.sourceforge.net>
// Copyright 2001-2004 The Apache Software Foundation
// All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
// See http://ant-eclipse.sourceforge.net for the most recent version
// and more information.

package prantl.ant.eclipse;

import org.apache.tools.ant.BuildException;

/**
 * Configures name and value of a variable in a preferences file under the directory
 * <tt>.settings</tt>. Both attributes <tt>name</tt> and <tt>value</tt> are
 * mandatory.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class VariableElement {

    private PreferencesElement preferences = null;

    private String name = null;

    private String value = null;

    /**
     * Creates a new instance of the variable element.
     * 
     * @param parent
     *        The parent preferences element of this variable one.
     * @since Ant-Eclipse 1.0
     */
    public VariableElement(PreferencesElement parent) {
        preferences = parent;
    }

    /**
     * Returns the name of the configuration variable. The name must not be <tt>null</tt>,
     * it is a mandatory attribute.
     * 
     * @return The name of the configuration variable or <tt>null</tt> if having not
     *         been set.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of the configuration variable. The value must not be
     * <tt>null</tt>, it is a mandatory attribute.
     * 
     * @return The value of the configuration variable or <tt>null</tt> if having not
     *         been set.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the name of the configuration variable.
     * 
     * @param name
     *        A name of the configuration variable.
     * @since Ant-Eclipse 1.0
     */
    public void setName(String name) {
        preferences.validateVariableName(name);
        this.name = name;
    }

    /**
     * Sets the value of the configuration variable.
     * 
     * @param value
     *        A value of the configuration variable.
     * @since Ant-Eclipse 1.0
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Performs the validation of the element at the time when the whole build file was
     * parsed checking the content of the element and possibly adding mandatory variables
     * with default settings.
     * 
     * @since Ant-Eclipse 1.0
     */
    public void validate() {
        if (name == null)
            throw new BuildException(
                    "The mandatory attribute \"name\" was missing in an element \"variable\".");
        if (value == null)
            throw new BuildException(
                    "The mandatory attribute \"value\" was missing in an element \"variable\".");
    }

}
