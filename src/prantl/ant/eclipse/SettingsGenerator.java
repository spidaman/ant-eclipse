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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Vector;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/**
 * Provides the functionality generating the file
 * <tt>.settings/org.eclipse.core.resources.prefs</tt> for the supplied task object. It
 * is expected to be used within the class EclipseTask.
 * 
 * @see EclipseTask
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
final class SettingsGenerator {

    private EclipseTask task;

    /**
     * Creates a new instance of the generating object.
     * 
     * @param parent
     *        The parent task.
     * @since Ant-Eclipse 1.0
     */
    SettingsGenerator(EclipseTask parent) {
        task = parent;
    }

    /**
     * Generates files under the directory <tt>.settings</tt> using the supplied output
     * object.
     * 
     * @since Ant-Eclipse 1.0
     */
    void generate() {
        SettingsElement settings = task.getEclipse().getSettings();
        if (settings == null) {
            task.log("There were no settings found.", Project.MSG_WARN);
            return;
        }
        checkPreferences(settings);
        Vector entries = settings.getPreferences();
        if (entries.size() == 0) {
            task.log("There were no preferences found.", Project.MSG_WARN);
            return;
        }
        for (int i = 0, size = entries.size(); i != size; ++i) {
            PreferencesElement preferences = (PreferencesElement) entries.get(i);
            preferences.validate();
            generatePreferences(preferences);
        }
    }

    /**
     * Generates a file <tt>.settings/xxx.prefs</tt> using the supplied output object.
     * 
     * @param preferences
     *        The element with the definition of preferences for output.
     * @since Ant-Eclipse 1.0
     */
    private void generatePreferences(PreferencesElement preferences) {
        EclipseOutput output = task.getOutput();
        String packageName = preferences.getName();
        if (output.isPreferencesUpToDate(packageName)) {
            task.log("The preferences for \"" + packageName + "\" are up-to-date.",
                    Project.MSG_WARN);
            return;
        }
        task.log("Writing the preferences for \"" + packageName + "\".");
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(output.createPreferences(packageName),
                    "UTF-8");
            generateTimeStamp(writer);
            Vector entries = preferences.getVariables();
            if (entries.size() == 0)
                task.log("There were no variables found for \"" + packageName + "\".",
                        Project.MSG_WARN);
            for (int i = 0, size = entries.size(); i != size; ++i)
                generateVariable(writer, (VariableElement) entries.get(i));
        } catch (UnsupportedEncodingException exception) {
            throw new BuildException("Encoder to UTF-8 is not supported.", exception);
        } catch (IOException exception) {
            throw new BuildException("Writing the settings failed.", exception);
        } finally {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException exception1) {
                    throw new BuildException("Closing the settings failed.", exception1);
                }
        }
    }

    /**
     * Writes a single comment line with the current time.
     * 
     * @param writer
     *        The output stream to write into.
     * @throws IOException
     * @throws IOException
     *         It an error during the output occurs.
     * @since Ant-Eclipse 1.0
     */
    private void generateTimeStamp(OutputStreamWriter writer) throws IOException {
        writer.write('#');
        writer.write(Calendar.getInstance().getTime().toString());
        writer.write('\n');
    }

    /**
     * Writes a single variable into a settings file.
     * 
     * @param writer
     *        The output stream to write into.
     * @param variable
     *        The variable to write out.
     * @throws IOException
     *         It an error during the output occurs.
     * @since Ant-Eclipse 1.0
     */
    private void generateVariable(OutputStreamWriter writer, VariableElement variable)
            throws IOException {
        writer.write(variable.getName());
        writer.write('=');
        writer.write(variable.getValue());
        writer.write('\n');
    }

    /**
     * Checks if an element is missing that should be generated by default and generates
     * it if so.
     * 
     * @param settings
     *        The element containing definitions of preferences.
     * @since Ant-Eclipse 1.0
     */
    private void checkPreferences(SettingsElement settings) {
        if (task.getEclipse().getMode().getIndex() == EclipseElement.Mode.ASPECTJ
                && getPreferences(settings, "org.eclipse.ajdt.ui") == null) {
            GeneralPreferencesElement preferences = settings.createGeneral();
            preferences.setName("org.eclipse.ajdt.ui");
            // VariableElement variable = preferences.createVariable();
            // variable.setName("org.eclipse.ajdt.ui.activeBuildConfiguration");
            // variable.setValue("trace.ajproperties");
        }
    }

    /**
     * Returns an element with the definition of preferences or <tt>null</tt> if there
     * is none with the specified name.
     * 
     * @param settings
     *        The element containing definitions of preferences.
     * @param name
     *        The name of the preferences to get.
     * @return The an element with the definition of preferences or <tt>null</tt> if
     *         there is none with the specified name.
     * @since Ant-Eclipse 1.0
     */
    private PreferencesElement getPreferences(SettingsElement settings, String name) {
        Vector entries = settings.getPreferences();
        for (int i = 0, size = entries.size(); i != size; ++i) {
            PreferencesElement preferences = (PreferencesElement) entries.get(i);
            if (name.equals(preferences.getName()))
                return preferences;
        }
        return null;
    }

}
