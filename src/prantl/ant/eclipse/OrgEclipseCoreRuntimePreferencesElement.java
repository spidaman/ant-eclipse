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

import java.util.HashMap;

import org.apache.tools.ant.BuildException;

/**
 * Configures the component preferences file
 * <tt>.settings/org.eclipse.core.runtime.prefs</tt> on the high level using attributes
 * for the typical constellations of variable values.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class OrgEclipseCoreRuntimePreferencesElement extends PreferencesElement {

    private static final String ELEMENT = "runtime";

    private static final String LINESEPARATOR_ATTRIBUTE = "lineseparator";

    private static final String LINESEPARATOR_NAME = "line.separator";

    private static final HashMap LINESEPARATOR_VALUES = new HashMap();

    /**
     * Returns the name of the package these preferences belong to.
     * 
     * @return The name of the package these preferences belong to.
     */
    static final String getPackageName() {
        return "org.eclipse.core.runtime";
    }

    /**
     * Creates a new instance of the element for the file with preferences for
     * org.eclipse.core.runtime.
     * 
     * @param parent
     *        The parent settings element of this preferences one.
     * @since Ant-Eclipse 1.0
     */
    public OrgEclipseCoreRuntimePreferencesElement(SettingsElement parent) {
        super(parent);
        internalSetName(getPackageName());
        LINESEPARATOR_VALUES.put("unix", "\\n");
        LINESEPARATOR_VALUES.put("macintosh", "\\r");
        LINESEPARATOR_VALUES.put("windows", "\\r\\n");
    }

    /**
     * Returns the line separator (default is inherited from the workspace settings and
     * not set here in the file).
     * 
     * @return The line separator (default is inherited from the workspace settings and
     *         not set here in the file).
     */
    public String getLineSeparator() {
        VariableElement variable = getVariable(LINESEPARATOR_NAME);
        return variable == null ? null : variable.getValue();
    }

    /**
     * Sets the version of the Eclipse preferences. The default value should be left and
     * not set explicitely.
     * 
     * @param value
     *        A valid line separator.
     * @since Ant-Eclipse 1.0
     */
    public void setLineSeparator(String value) {
        String lineSeparator = (String) LINESEPARATOR_VALUES.get(value.toLowerCase());
        if (lineSeparator == null)
            throw new BuildException("The attribute \"" + LINESEPARATOR_ATTRIBUTE
                    + "\" (variable \"" + LINESEPARATOR_NAME
                    + "\")  has an invalid value \"" + value + "\". Valid values are "
                    + getValidLineSeparatorValues() + ".");
        internalCreateVariable(LINESEPARATOR_NAME, value);
    }

    /**
     * Returns allowed values for the variable line.separator.
     * 
     * @return A new string with allowed values for the variable line.separator.
     * @since Ant-Eclipse 1.0
     */
    String getValidLineSeparatorValues() {
        return getValidValues(LINESEPARATOR_VALUES.keySet());
    }

    /**
     * Performs the validation of the element at the time when the whole build file was
     * parsed checking the content of the element and possibly adding mandatory variables
     * with default settings.
     * 
     * @since Ant-Eclipse 1.0
     */
    public void validate() {
        if (!hasVariable(LINESEPARATOR_NAME))
            throw new BuildException("The attribute \"" + LINESEPARATOR_ATTRIBUTE
                    + "\" (variable \"" + LINESEPARATOR_NAME
                    + "\") was missing in the element \"" + ELEMENT + "\".");
        super.validate();
    }

}
