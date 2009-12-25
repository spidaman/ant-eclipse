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

import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.apache.tools.ant.BuildException;

/**
 * Configures a component preferences in a file under the directory <tt>.settings</tt>
 * using variable elements. The version element is mandatory and will be generated
 * automatically if not provided. The attributes <tt>name</tt> of all elements
 * describing the preferences must be distinct.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public abstract class PreferencesElement {

    private static final String VERSION_NAME = "eclipse.preferences.version";

    private static final String VERSION_VALUE = "1";

    private SettingsElement settings = null;

    private String name = null;

    private Vector variables = new Vector();

    /**
     * Creates a new instance of the element for preferences under the settings element.
     * 
     * @param parent
     *        The parent settings element of this preferences one.
     * @since Ant-Eclipse 1.0
     */
    PreferencesElement(SettingsElement parent) {
        settings = parent;
    }

    /**
     * Returns the name of the file with preferences. The name must not be <tt>null</tt>,
     * it is a mandatory attribute.
     * 
     * @return The name of the file with preferences or <tt>null</tt> if not having been
     *         set.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the file with preferences. Used internally to set default names of
     * the files for preferences.
     * 
     * @param value
     *        A name of the file with preferences.
     * @since Ant-Eclipse 1.0
     */
    void internalSetName(String value) {
        settings.validatePreferencesName(value);
        name = value;
    }

    /**
     * Returns the version of the preferences set by this element (<tt>1</tt> is used
     * as a default). The default value should be left.
     * 
     * @return The version of the Eclipse preferences (<tt>1</tt> is used as a
     *         default).
     */
    public String getVersion() {
        // return version;
        VariableElement variable = getVariable(VERSION_NAME);
        return variable == null ? VERSION_VALUE : variable.getValue();
    }

    /**
     * Sets the version of the Eclipse preferences. The default value should be left and
     * not set explicitely.
     * 
     * @param value
     *        A valid version of the Eclipse preferences.
     * @since Ant-Eclipse 1.0
     */
    public void setVersion(String value) {
        internalCreateVariable(VERSION_NAME, value);
    }

    /**
     * Returns a list with instances of the class VariableElement defining variables for a
     * file <em>&lt;full qualified class
     * name&gt;</em> under the directory
     * <tt>.settings</tt>. If it is empty the version element will be generated
     * automatically.
     * 
     * @return A list with instances of the descendants of the class PreferencesElement.
     */
    public Vector getVariables() {
        return variables;
    }

    /**
     * Adds a definition of a new variable element. Used internally to add undeclared
     * variable definitions and allow the low-level variable element.
     * 
     * @return A definition of a new variable element.
     * @since Ant-Eclipse 1.0
     */
    VariableElement internalCreateVariable() {
        variables.addElement(new VariableElement(this));
        return (VariableElement) variables.lastElement();
    }

    /**
     * Adds a definition of a new variable element, with its name and value. Used
     * internally to add undeclared variable definitions.
     * 
     * @param name
     *        A name of the configuration variable.
     * @param value
     *        A value of the configuration variable.
     * @since Ant-Eclipse 1.0
     */
    void internalCreateVariable(String name, String value) {
        VariableElement variable = internalCreateVariable();
        variable.setName(name);
        variable.setValue(value);
    }

    /**
     * Adds a definition of a new variable element, with its name and value, only if not
     * yet defined. Used internally to add default variable definitions.
     * 
     * @param name
     *        A name of the configuration variable.
     * @param value
     *        A value of the configuration variable.
     * @since Ant-Eclipse 1.0
     */
    void internalAddVariable(String name, String value) {
        if (!hasVariable(name))
            internalCreateVariable(name, value);
    }

    /**
     * Performs the validation of the element at the time when the whole build file was
     * parsed checking the content of the element and possibly adding mandatory variables
     * with default settings.
     * 
     * @since Ant-Eclipse 1.0
     */
    public void validate() {
        if (!hasVariable(VERSION_NAME))
            setVersion(VERSION_VALUE);
        for (int i = 0, size = variables.size(); i != size; ++i) {
            VariableElement variable = (VariableElement) variables.get(i);
            variable.validate();
        }
    }

    /**
     * Checks if the variable with the specified name is allowed to be defined according
     * to the already parsed content.
     * 
     * @param name
     *        The name of the configuration variable to validate.
     * @since Ant-Eclipse 1.0
     */
    void validateVariableName(String name) {
        if (hasVariable(name))
            throw new BuildException(
                    name.equals(VERSION_NAME) ? "The variable \""
                            + VERSION_NAME
                            + "\" cannot be defined as an element if there has been an attribute \"version\" used for the whole preferences."
                            : "The variable named \"" + name
                                    + "\" has alredy been defined.");
    }

    /**
     * Checks if the variable with the specified name has already been defined for this
     * preferences.
     * 
     * @param name
     *        The name of the configuration variable to look for.
     * @return <tt>True</tt> if the variable with the specified name is present.
     * @since Ant-Eclipse 1.0
     */
    boolean hasVariable(String name) {
        return getVariable(name) != null;
    }

    /**
     * Returns the element defining the variable with the specified name or <tt>null</tt>
     * if not having been defined.
     * 
     * @param name
     *        The element defining the configuration variable or <tt>null</tt> if not
     *        present.
     * @return The element defining the variable with the specified name or <tt>null</tt>
     *         if not present.
     * @since Ant-Eclipse 1.0
     */
    VariableElement getVariable(String name) {
        for (int i = 0, size = variables.size(); i != size; ++i) {
            VariableElement variable = (VariableElement) variables.get(i);
            if (name.equals(variable.getName()))
                return variable;
        }
        return null;
    }

    /**
     * Returns a new string with a list of string representations of the items from the
     * passed set in the format "item1", "item2" and "item3" useful to display allowed
     * value list as a string.
     * 
     * @param set
     *        Set to list as a string.
     * @return A new string with a list of string representations of the items from the
     *         passed set in the format "item1", "item2" and "item3".
     * @since Ant-Eclipse 1.0
     */
    String getValidValues(Set set) {
        StringBuffer result = new StringBuffer();
        Iterator iterator = set.iterator();
        for (int i = 0, size = set.size(); i != size; ++i) {
            result.append('\"');
            result.append(iterator.next());
            if (i == size - 2)
                result.append("\" and ");
            else if (i == size - 1)
                result.append('\"');
            else
                // if (i < count - 2)
                result.append("\", ");
        }
        return result.toString();
    }

}
