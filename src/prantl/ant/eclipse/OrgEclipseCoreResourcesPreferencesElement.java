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
 * Configures the component preferences file
 * <tt>.settings/org.eclipse.core.resources.prefs</tt> on the high level using
 * attributes for the typical constellations of variable values.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class OrgEclipseCoreResourcesPreferencesElement extends PreferencesElement {

    private static final String ELEMENT = "resources";

    private static final String ENCODING_ATTRIBUTE = "encoding";

    private static final String ENCODING_NAME = "encoding/<project>";

    private static final HashSet ENCODING_VALUES = new HashSet();

    /**
     * Returns the name of the package these preferences belong to.
     * 
     * @return The name of the package these preferences belong to.
     */
    static final String getPackageName() {
        return "org.eclipse.core.resources";
    }

    /**
     * Creates a new instance of the element for the file with preferences for
     * org.eclipse.core.resources.
     * 
     * @param parent
     *        The parent settings element of this preferences one.
     * @since Ant-Eclipse 1.0
     */
    public OrgEclipseCoreResourcesPreferencesElement(SettingsElement parent) {
        super(parent);
        internalSetName(getPackageName());
        ENCODING_VALUES.add("ISO-8859-1");
        ENCODING_VALUES.add("US-ASCII");
        ENCODING_VALUES.add("UTF-16");
        ENCODING_VALUES.add("UTF-16BE");
        ENCODING_VALUES.add("UTF-16LE");
        ENCODING_VALUES.add("UTF-8");
    }

    /**
     * Returns the source file encoding for the project (default is inherited from the
     * workspace settings and not set here in the file).
     * 
     * @return The source file encoding for the project (default is inherited from the
     *         workspace settings and not set here in the file)
     */
    public String getEncoding() {
        VariableElement variable = getVariable(ENCODING_NAME);
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
    public void setEncoding(String value) {
        value = value.toUpperCase();
        if (!ENCODING_VALUES.contains(value))
            throw new BuildException("The attribute \"" + ENCODING_ATTRIBUTE
                    + "\" (variable \"" + ENCODING_NAME + "\") has an invalid value \""
                    + value + "\". Valid values are " + getValidEncodingValues() + ".");
        internalCreateVariable(ENCODING_NAME, value);
    }

    /**
     * Returns allowed values for the variable encoding/<project>.
     * 
     * @return A new string with allowed values for the variable line.separator.
     * @since Ant-Eclipse 1.0
     */
    String getValidEncodingValues() {
        return getValidValues(ENCODING_VALUES);
    }

    /**
     * Performs the validation of the element at the time when the whole build file was
     * parsed checking the content of the element and possibly adding mandatory variables
     * with default settings.
     * 
     * @since Ant-Eclipse 1.0
     */
    public void validate() {
        if (!hasVariable(ENCODING_NAME))
            throw new BuildException("The attribute \"" + ENCODING_ATTRIBUTE
                    + "\" (variable \"" + ENCODING_NAME
                    + "\") was missing in the element \"" + ELEMENT + "\".");
        super.validate();
    }

}
