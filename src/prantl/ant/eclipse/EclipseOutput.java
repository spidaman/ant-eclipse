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

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Defines the basic functionality and interface for the classes performing the actual
 * output of the generated files. Descendants of this abstract class are expected to be
 * used within the class EclipseTask.
 * 
 * @see EclipseTask
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
abstract class EclipseOutput {

    private EclipseElement eclipse;

    /**
     * Creates a new instance of the output object.
     * 
     * @param element
     *        An object containing the configuration.
     * @since Ant-Eclipse 1.0
     */
    EclipseOutput(EclipseElement element) {
        eclipse = element;
    }

    /**
     * Returns the internal object containing the configuration.
     * 
     * @return The internal object containing the configuration.
     * @since Ant-Eclipse 1.0
     */
    EclipseElement getEclipse() {
        return eclipse;
    }

    /**
     * Checks if it is necessary to write the content of the file
     * <tt>.settings/xxx.prefs</tt>.
     * 
     * @param name
     *        The name of the package for the preferences.
     * @return <tt>True</tt> if the content needs to be (re)written.
     * @since Ant-Eclipse 1.0
     */
    abstract boolean isPreferencesUpToDate(String name);

    /**
     * Checks if it is necessary to write the content of the file .project.
     * 
     * @return <tt>True</tt> if the content needs to be (re)written.
     * @since Ant-Eclipse 1.0
     */
    abstract boolean isProjectUpToDate();

    /**
     * Checks if it is necessary to write the content of the file .classpath.
     * 
     * @return <tt>True</tt> if the content needs to be (re)written.
     * @since Ant-Eclipse 1.0
     */
    abstract boolean isClassPathUpToDate();

    /**
     * Returns a stream to read the current content of the file
     * <tt>.settings/xxx.prefs</tt>.
     * 
     * @param name
     *        The name of the package for the preferences.
     * @return Source stream with the current content.
     * @since Ant-Eclipse 1.0
     */
    abstract InputStream openPreferences(String name);

    /**
     * Returns a stream to read the current content of the file .project.
     * 
     * @return Source stream with the current content.
     * @since Ant-Eclipse 1.0
     */
    abstract InputStream openProject();

    /**
     * Returns a stream to read the current content of the file .classpath.
     * 
     * @return Source stream with the current content.
     * @since Ant-Eclipse 1.0
     */
    abstract InputStream openClassPath();

    /**
     * Returns a stream to write the content of the file <tt>.settings/xxx.prefs</tt>.
     * 
     * @param name
     *        The name of the package for the preferences.
     * @return Target stream for the content.
     * @since Ant-Eclipse 1.0
     */
    abstract OutputStream createPreferences(String name);

    /**
     * Returns a stream to write the content of the file .project.
     * 
     * @return Target stream for the content.
     * @since Ant-Eclipse 1.0
     */
    abstract OutputStream createProject();

    /**
     * Returns a stream to write the content of the file .classpath.
     * 
     * @return Target stream for the content.
     * @since Ant-Eclipse 1.0
     */
    abstract OutputStream createClassPath();

}
