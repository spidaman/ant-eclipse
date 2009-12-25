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

import java.util.HashSet;

import org.apache.tools.ant.BuildException;

/**
 * Configures the component preferences file <tt>.settings/org.eclipse.jdt.ui.prefs</tt>
 * on the high level using attributes for the typical constellations of variable values.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class OrgEclipseJdtUiPreferencesElement extends PreferencesElement {

    // private static final String ELEMENT = "jdtui";
    private static final String COMPLIANCE_ATTRIBUTE = "compliance";

    private static final String COMPLIANCE_NAME = "internal.default.compliance";

    private static final String COMPLIANCE_DEFAULT = "user";

    private static final HashSet COMPLIANCE_VALUES = new HashSet();

    /**
     * Returns the name of the package these preferences belong to.
     * 
     * @return The name of the package these preferences belong to.
     */
    static final String getPackageName() {
        return "org.eclipse.jdt.ui";
    }

    /**
     * Creates a new instance of the element for the file with preferences for
     * org.eclipse.jdt.ui.
     * 
     * @param parent
     *        The parent settings element of this preferences one.
     * @since Ant-Eclipse 1.0
     */
    public OrgEclipseJdtUiPreferencesElement(SettingsElement parent) {
        super(parent);
        internalSetName(getPackageName());
        COMPLIANCE_VALUES.add("default");
        COMPLIANCE_VALUES.add("user");
    }

    /**
     * Returns the source file encoding for the project (default is inherited from the
     * workspace settings and not set here in the file).
     * 
     * @return The source file encoding for the project (default is inherited from the
     *         workspace settings and not set here in the file)
     */
    public String getCompliance() {
        VariableElement variable = getVariable(COMPLIANCE_NAME);
        return variable == null ? null : variable.getValue();
    }

    /**
     * Sets the version of the Eclipse preferences. The default value should be left and
     * not set explicitely.
     * 
     * @param value
     *        A valid encoding for the project.
     * @since Ant-Eclipse 1.0
     */
    public void setCompliance(String value) {
        value = value.toLowerCase();
        if (!COMPLIANCE_VALUES.contains(value))
            throw new BuildException("The attribute \"" + COMPLIANCE_ATTRIBUTE
                    + "\" (variable \"" + COMPLIANCE_NAME + "\") has an invalid value \""
                    + value + "\". Valid values are " + getValidComplianceValues() + ".");
        internalCreateVariable(COMPLIANCE_NAME, value);
    }

    /**
     * Returns allowed values for the variable internal.default.compliance.
     * 
     * @return A new string with allowed values for the variable line.separator.
     * @since Ant-Eclipse 1.0
     */
    String getValidComplianceValues() {
        return getValidValues(COMPLIANCE_VALUES);
    }

    /**
     * Performs the validation of the element at the time when the whole build file was
     * parsed checking the content of the element and possibly adding mandatory variables
     * with default settings.
     * 
     * @since Ant-Eclipse 1.0
     */
    public void validate() {
        if (!hasVariable(COMPLIANCE_NAME))
            setCompliance(COMPLIANCE_DEFAULT);
        super.validate();
    }

}
