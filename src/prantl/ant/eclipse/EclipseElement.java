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
import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * Configures the behavior of the generation of the output files and contains descriptions
 * of ther content.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class EclipseElement {

    /**
     * EnumeratedAttribute implementation supporting the available project modes.
     * 
     * @since Ant-Eclipse 1.0
     * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
     */
    public static class Mode extends EnumeratedAttribute {

        /**
         * Forces the Java mode to use JDT in the project.
         */
        public final static int JAVA = 0;

        /**
         * Forces the AspectJ mode to use AJDT in the project.
         */
        public final static int ASPECTJ = 1;

        /**
         * @see EnumeratedAttribute#getValues()
         */
        public String[] getValues() {
            return new String[] { "java", "aspectj" };
        }

    }

    private boolean updateAlways = false;

    private boolean failOnError = true;

    private String errorProperty = null;

    private File destDir = new File(".");

    private Mode mode;

    private SettingsElement settings = null;

    private ProjectElement project = null;

    private ClassPathElement classPath = null;

    /**
     * Creates a new instance of the configuration container.
     * 
     * @since Ant-Eclipse 1.0
     */
    public EclipseElement() {
        mode = new Mode();
        mode.setValue(mode.getValues()[Mode.JAVA]);
    }

    /**
     * @return Returns the updateAlways.
     */
    public boolean isUpdateAlways() {
        return updateAlways;
    }

    /**
     * @param dir
     *        The updateAlways to set.
     */
    public void setUpdateAlways(boolean flag) {
        updateAlways = flag;
    }

    /**
     * @return Returns the destDir.
     */
    public File getDestDir() {
        return destDir;
    }

    /**
     * @param dir
     *        The destDir to set.
     */
    public void setDestDir(File dir) {
        destDir = dir;
    }

    /**
     * @return Returns the failOnError.
     */
    public boolean isFailOnError() {
        return failOnError;
    }

    /**
     * @param flag
     *        The failOnError to set.
     */
    public void setFailOnError(boolean flag) {
        failOnError = flag;
    }

    /**
     * @return Returns the errorProperty.
     */
    public String getErrorProperty() {
        return errorProperty;
    }

    /**
     * @param name
     *        The name of the property to set.
     */
    public void setErrorProperty(String name) {
        errorProperty = name;
    }

    /**
     * @return Returns the mode.
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * @param value
     *        The value of the property to set.
     */
    public void setMode(Mode value) {
        mode = value;
    }

    /**
     * @return Returns the settings.
     */
    public SettingsElement getSettings() {
        return settings;
    }

    /**
     * @param element
     *        The settings to set.
     */
    public void setSettings(SettingsElement element) {
        if (project != null)
            throw new BuildException("The element <settings> has been already defined.");
        settings = element;
    }

    /**
     * @return Returns the project.
     */
    public ProjectElement getProject() {
        return project;
    }

    /**
     * @param element
     *        The project to set.
     */
    public void setProject(ProjectElement element) {
        if (project != null)
            throw new BuildException("The element <project> has been already defined.");
        project = element;
    }

    /**
     * @return Returns the classPath.
     */
    public ClassPathElement getClassPath() {
        return classPath;
    }

    /**
     * @param element
     *        The classPath to set.
     */
    public void setClassPath(ClassPathElement element) {
        if (classPath != null)
            throw new BuildException("The element <classpath> has been already defined.");
        classPath = element;
    }

}
