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

import java.util.Vector;

import org.apache.tools.ant.BuildException;

/**
 * Configures components creating their preference files under under the directory
 * <tt>.settings</tt>. The attributes <tt>name</tt> of all elements for components
 * describing preferences must be distinct.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class SettingsElement {

    private Vector preferences = new Vector();

    /**
     * Creates a new instance of the settings element.
     * 
     * @since Ant-Eclipse 1.0
     */
    public SettingsElement() {
    }

    /**
     * Returns a list with instances of the descendants of the class PreferencesElement
     * describing files <em>&lt;full qualified class
     * name&gt;</em> under the directory
     * <tt>.settings</tt>. If it is empty nothing happens.
     * 
     * @return A list with instances of the descendants of the class PreferencesElement.
     */
    public Vector getPreferences() {
        return preferences;
    }

    /**
     * Adds a definition of the general preferences element.
     * 
     * @return A definition of the general preferences element.
     * @since Ant-Eclipse 1.0
     */
    public GeneralPreferencesElement createGeneral() {
        preferences.addElement(new GeneralPreferencesElement(this));
        return (GeneralPreferencesElement) preferences.lastElement();
    }

    /**
     * Adds a definition of the convenience preferences element specific for the package
     * org.eclipse.core.resources.
     * 
     * @return A definition of the convenience preferences element specific for the
     *         package org.eclipse.core.resources.
     * @since Ant-Eclipse 1.0
     */
    public OrgEclipseCoreResourcesPreferencesElement createResources() {
        preferences.addElement(new OrgEclipseCoreResourcesPreferencesElement(this));
        return (OrgEclipseCoreResourcesPreferencesElement) preferences.lastElement();
    }

    /**
     * Adds a definition of the convenience preferences element specific for the package
     * org.eclipse.core.runtime.
     * 
     * @return A definition of the convenience preferences element specific for the
     *         package org.eclipse.core.runtime.
     * @since Ant-Eclipse 1.0
     */
    public OrgEclipseCoreRuntimePreferencesElement createRuntime() {
        preferences.addElement(new OrgEclipseCoreRuntimePreferencesElement(this));
        return (OrgEclipseCoreRuntimePreferencesElement) preferences.lastElement();
    }

    /**
     * Adds a definition of the convenience preferences element specific for the package
     * org.eclipse.jdt.core.
     * 
     * @return A definition of the convenience preferences element specific for the
     *         package org.eclipse.jdt.core.
     * @since Ant-Eclipse 1.0
     */
    public OrgEclipseJdtCorePreferencesElement createJdtCore() {
        preferences.addElement(new OrgEclipseJdtCorePreferencesElement(this));
        return (OrgEclipseJdtCorePreferencesElement) preferences.lastElement();
    }

    /**
     * Adds a definition of the convenience preferences element specific for the package
     * org.eclipse.jdt.ui.
     * 
     * @return A definition of the convenience preferences element specific for the
     *         package org.eclipse.jdt.ui.
     * @since Ant-Eclipse 1.0
     */
    public OrgEclipseJdtUiPreferencesElement createJdtUi() {
        preferences.addElement(new OrgEclipseJdtUiPreferencesElement(this));
        return (OrgEclipseJdtUiPreferencesElement) preferences.lastElement();
    }

    /**
     * Checks if the preferences with the specified name is allowed to be defined
     * according to the already parsed content.
     * 
     * @param name
     *        The name of the configuration preferences to validate.
     * @since Ant-Eclipse 1.0
     */
    void validatePreferencesName(String name) {
        if (hasPreferences(name))
            throw new BuildException("The preferences for \"" + name
                    + "\" has alredy been defined.");
    }

    /**
     * Checks if the preferences with the specified name has already been defined for this
     * preferences.
     * 
     * @param name
     *        The name of the configuration preferences to look for.
     * @return <tt>True</tt> if the preferences with the specified name are present.
     * @since Ant-Eclipse 1.0
     */
    boolean hasPreferences(String name) {
        return getPreferences(name) != null;
    }

    /**
     * Returns the element defining the preferences with the specified name or
     * <tt>null</tt> if not having been defined.
     * 
     * @param name
     *        The element defining the configuration preferences or <tt>null</tt> if not
     *        present.
     * @return The element defining the preferences with the specified name or
     *         <tt>null</tt> if not present.
     * @since Ant-Eclipse 1.0
     */
    PreferencesElement getPreferences(String name) {
        for (int i = 0, size = preferences.size(); i != size; ++i) {
            PreferencesElement preference = (PreferencesElement) preferences.get(i);
            if (name.equals(preference.getName()))
                return preference;
        }
        return null;
    }

}
