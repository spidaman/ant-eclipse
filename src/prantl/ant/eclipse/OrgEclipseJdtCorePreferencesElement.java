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
import java.util.Hashtable;

import org.apache.tools.ant.BuildException;

/**
 * Configures the component preferences file <tt>.settings/org.eclipse.jdt.core.prefs</tt>
 * on the high level using attributes for the typical constellations of variable values.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class OrgEclipseJdtCorePreferencesElement extends PreferencesElement {

    private static final String ELEMENT = "jdtcore";

    private static final String COMPILERCOMPLIANCE_ATTRIBUTE = "compilercompliance";

    private static final String COMPILERCOMPLIANCE_NAME = "org.eclipse.jdt.core.compiler.compliance";

    private static final HashSet COMPILERCOMPLIANCE_VALUES = new HashSet();

    private static final String[] ORGECLIPSEJDTCORE_NAMES = {
            "org.eclipse.jdt.core.compiler.codegen.inlineJsrBytecode",
            "org.eclipse.jdt.core.compiler.codegen.targetPlatform",
            "org.eclipse.jdt.core.compiler.codegen.unusedLocal",
            "org.eclipse.jdt.core.compiler.compliance",
            "org.eclipse.jdt.core.compiler.debug.lineNumber",
            "org.eclipse.jdt.core.compiler.debug.localVariable",
            "org.eclipse.jdt.core.compiler.debug.sourceFile",
            "org.eclipse.jdt.core.compiler.problem.assertIdentifier",
            "org.eclipse.jdt.core.compiler.problem.enumIdentifier",
            "org.eclipse.jdt.core.compiler.source" };

    private static final Hashtable ORGECLIPSEJDTCORE_DEFAULTS = new Hashtable();

    /**
     * Returns the name of the package these preferences belong to.
     * 
     * @return The name of the package these preferences belong to.
     */
    static final String getPackageName() {
        return "org.eclipse.jdt.core";
    }

    /**
     * Creates a new instance of the element for the file with preferences for
     * org.eclipse.jdt.core.
     * 
     * @param parent
     *        The parent settings element of this preferences one.
     * @since Ant-Eclipse 1.0
     */
    public OrgEclipseJdtCorePreferencesElement(SettingsElement parent) {
        super(parent);
        internalSetName(getPackageName());
        COMPILERCOMPLIANCE_VALUES.add("1.3");
        COMPILERCOMPLIANCE_VALUES.add("1.4");
        COMPILERCOMPLIANCE_VALUES.add("5.0");
        COMPILERCOMPLIANCE_VALUES.add("6.0");
        ORGECLIPSEJDTCORE_DEFAULTS.put("1.3", new String[] { "enabled", // org.eclipse.jdt.core.compiler.codegen.inlineJsrBytecode
                "1.1", // org.eclipse.jdt.core.compiler.codegen.targetPlatform
                "preserve", // org.eclipse.jdt.core.compiler.codegen.unusedLocal
                "1.3", // org.eclipse.jdt.core.compiler.compliance
                "generate", // org.eclipse.jdt.core.compiler.debug.lineNumber
                "generate", // org.eclipse.jdt.core.compiler.debug.localVariable
                "generate", // org.eclipse.jdt.core.compiler.debug.sourceFile
                "ignore", // org.eclipse.jdt.core.compiler.problem.assertIdentifier
                "ignore", // org.eclipse.jdt.core.compiler.problem.enumIdentifier
                "1.3" // org.eclipse.jdt.core.compiler.source
        });
        ORGECLIPSEJDTCORE_DEFAULTS.put("1.4", new String[] { "enabled", // org.eclipse.jdt.core.compiler.codegen.inlineJsrBytecode
                "1.2", // org.eclipse.jdt.core.compiler.codegen.targetPlatform
                "preserve", // org.eclipse.jdt.core.compiler.codegen.unusedLocal
                "1.4", // org.eclipse.jdt.core.compiler.compliance
                "generate", // org.eclipse.jdt.core.compiler.debug.lineNumber
                "generate", // org.eclipse.jdt.core.compiler.debug.localVariable
                "generate", // org.eclipse.jdt.core.compiler.debug.sourceFile
                "warning", // org.eclipse.jdt.core.compiler.problem.assertIdentifier
                "warning", // org.eclipse.jdt.core.compiler.problem.enumIdentifier
                "1.3" // org.eclipse.jdt.core.compiler.source
        });
        ORGECLIPSEJDTCORE_DEFAULTS.put("5.0", new String[] { "enabled", // org.eclipse.jdt.core.compiler.codegen.inlineJsrBytecode
                "1.5", // org.eclipse.jdt.core.compiler.codegen.targetPlatform
                "preserve", // org.eclipse.jdt.core.compiler.codegen.unusedLocal
                "1.5", // org.eclipse.jdt.core.compiler.compliance
                "generate", // org.eclipse.jdt.core.compiler.debug.lineNumber
                "generate", // org.eclipse.jdt.core.compiler.debug.localVariable
                "generate", // org.eclipse.jdt.core.compiler.debug.sourceFile
                "error", // org.eclipse.jdt.core.compiler.problem.assertIdentifier
                "error", // org.eclipse.jdt.core.compiler.problem.enumIdentifier
                "1.5" // org.eclipse.jdt.core.compiler.source
        });
        ORGECLIPSEJDTCORE_DEFAULTS.put("6.0", new String[] { "enabled", // org.eclipse.jdt.core.compiler.codegen.inlineJsrBytecode
                "1.6", // org.eclipse.jdt.core.compiler.codegen.targetPlatform
                "preserve", // org.eclipse.jdt.core.compiler.codegen.unusedLocal
                "1.6", // org.eclipse.jdt.core.compiler.compliance
                "generate", // org.eclipse.jdt.core.compiler.debug.lineNumber
                "generate", // org.eclipse.jdt.core.compiler.debug.localVariable
                "generate", // org.eclipse.jdt.core.compiler.debug.sourceFile
                "error", // org.eclipse.jdt.core.compiler.problem.assertIdentifier
                "error", // org.eclipse.jdt.core.compiler.problem.enumIdentifier
                "1.6" // org.eclipse.jdt.core.compiler.source
        });
    }

    /**
     * Returns the line separator (default is inherited from the workspace settings and
     * not set here in the file).
     * 
     * @return The line separator (default is inherited from the workspace settings and
     *         not set here in the file).
     */
    public String getCompilerCompliance() {
        VariableElement variable = getVariable(COMPILERCOMPLIANCE_NAME);
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
    public void setCompilerCompliance(String value) {
        if (!COMPILERCOMPLIANCE_VALUES.contains(value))
            throw new BuildException("The attribute \"" + COMPILERCOMPLIANCE_ATTRIBUTE
                    + "\" (variable \"" + COMPILERCOMPLIANCE_NAME
                    + "\") has an invalid value \"" + value + "\". Valid values are "
                    + getValidCompilerComplianceValues() + ".");
        internalCreateVariable(COMPILERCOMPLIANCE_NAME, value);
    }

    /**
     * Returns allowed values for the variable org.eclipse.jdt.core.compiler.compliance.
     * 
     * @return A new string with allowed values for the variable
     *         org.eclipse.jdt.core.compiler.compliance.
     * @since Ant-Eclipse 1.0
     */
    String getValidCompilerComplianceValues() {
        return getValidValues(COMPILERCOMPLIANCE_VALUES);
    }

    /**
     * Performs the validation of the element at the time when the whole build file was
     * parsed checking the content of the element and possibly adding mandatory variables
     * with default settings.
     * 
     * @since Ant-Eclipse 1.0
     */
    public void validate() {
        VariableElement variable = getVariable(COMPILERCOMPLIANCE_NAME);
        if (variable == null)
            throw new BuildException("The attribute \"" + COMPILERCOMPLIANCE_ATTRIBUTE
                    + "\" (variable \"" + COMPILERCOMPLIANCE_NAME
                    + "\") was missing in the element \"" + ELEMENT + "\".");
        String[] defaults = (String[]) ORGECLIPSEJDTCORE_DEFAULTS
                .get(variable.getValue());
        for (int i = 0; i < ORGECLIPSEJDTCORE_NAMES.length; ++i)
            internalAddVariable(ORGECLIPSEJDTCORE_NAMES[i], defaults[i]);

        super.validate();
    }

}
