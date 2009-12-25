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

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Generates project configuration files for Eclipse. Writes files .project and .classpath
 * for the Eclipse IDE using the settings from the Ant script. The configuration for the
 * task is stored in an object of the class EclipseElement and the actual output is
 * delegated into an implementation of the interface EclipseOutput.
 * 
 * @see EclipseElement
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class EclipseTask extends Task {

    private EclipseElement eclipse;

    private EclipseOutput output;

    /**
     * Creates a new instance of the task. Default constructor, to be called by ant in the
     * productive environment.
     * 
     * @since Ant-Eclipse 1.0
     */
    public EclipseTask() {
        eclipse = new EclipseElement();
        output = null;
    }

    /**
     * Creates a new instance of the task. Testing constructor, to be called in unit tests
     * only.
     * 
     * @param object
     *        An object that the output is delegated into.
     * @throws NullPointerException
     *         If the parameter <tt>object</tt> is null.
     * @since Ant-Eclipse 1.0
     */
    protected EclipseTask(EclipseOutput object) {
        eclipse = object.getEclipse();
        output = object;
    }

    /**
     * Returns the internal object containing the configuration. The method is supposed to
     * be used in unit tests only.
     * 
     * @return The internal object containing the configuration.
     * @since Ant-Eclipse 1.0
     */
    EclipseElement getEclipse() {
        return eclipse;
    }

    /**
     * Returns the internal object performing the output. The method is supposed to be
     * used in unit tests only.
     * 
     * @return The internal object performing the output.
     * @since Ant-Eclipse 1.0
     */
    EclipseOutput getOutput() {
        return output;
    }

    /**
     * If true, stop the build process if the generation of the project files fails.
     * 
     * @param value
     *        <tt>True</tt> if it should halt, otherwise <tt>false</tt>.
     * @since Ant-Eclipse 1.0
     */
    public void setFailOnError(boolean value) {
        eclipse.setFailOnError(value);
    }

    /**
     * Property to set to "true" if there is an error in the generation of the project
     * files.
     * 
     * @param name
     *        A name of the property to set in case of an error.
     * @since Ant-Eclipse 1.0
     */
    public void setErrorProperty(String name) {
        eclipse.setErrorProperty(name);
    }

    /**
     * High-level project creation mode controlling defaults for the generated files and
     * changing behavior according to the specified project type, which usually responds
     * to the chosen development toolkit.
     * 
     * @param value
     *        A project creation mode.
     * @since Ant-Eclipse 1.0
     */
    public void setMode(EclipseElement.Mode value) {
        eclipse.setMode(value);
    }

    /**
     * Sets if the generated files are to be written always or only if the Ant build
     * script has been changed. The latter is default.
     * 
     * @param flag
     *        <tt>True</tt> if the files should always be overwritten, otherwise
     *        <tt>false</tt>.
     * @since Ant-Eclipse 1.0
     */
    public void setUpdateAlways(boolean flag) {
        eclipse.setUpdateAlways(flag);
    }

    /**
     * Sets the destination directory to place generated files into.
     * 
     * @param dir
     *        A directory to place output files into.
     * @since Ant-Eclipse 1.0
     */
    public void setDestDir(File dir) {
        eclipse.setDestDir(dir);
    }

    /**
     * Adds a definition of the settings element. Files in the directory
     * <tt>.settings</tt> will be generated according to settings in this object. Only
     * one settings element is allowed.
     * 
     * @return A definition of the settings element.
     * @throws BuildException
     *         If another settings element has been defined.
     * @since Ant-Eclipse 1.0
     */
    public SettingsElement createSettings() {
        eclipse.setSettings(new SettingsElement());
        return eclipse.getSettings();
    }

    /**
     * Adds a definition of the project element. The file .project will be generated
     * according to settings in this object. Only one project element is allowed.
     * 
     * @return A definition of the project element.
     * @throws BuildException
     *         If another project element has been defined.
     * @since Ant-Eclipse 1.0
     */
    public ProjectElement createProject() {
        eclipse.setProject(new ProjectElement());
        return eclipse.getProject();
    }

    /**
     * Adds a definition of the classpath element. The file .classpath will be generated
     * according to settings in this object. Only one classpath element is allowed.
     * 
     * @return A definition of the classpath element.
     * @throws BuildException
     *         If another classpath element has been defined.
     * @since Ant-Eclipse 1.0
     */
    public ClassPathElement createClassPath() {
        eclipse.setClassPath(new ClassPathElement());
        return eclipse.getClassPath();
    }

    /**
     * Generates the output files. Eventually existing files will be overwritten only if
     * the timestamp of the ant project file is newer as the timestamp of a particular
     * file.
     * 
     * @throws BuildException
     *         In case of misconfiguration or errors.
     * @since Ant-Eclipse 1.0
     */
    public void execute() throws BuildException {
        if (output == null)
            output = new FileEclipseOutput(this);
        new SettingsGenerator(this).generate();
        new ProjectGenerator(this).generate();
        new ClassPathGenerator(this).generate();
    }

}
