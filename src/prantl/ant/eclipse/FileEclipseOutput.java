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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.util.FileUtils;

/**
 * Implements the output interface performing the actual output of the Eclipse project
 * files into the configured directory.
 * 
 * @see EclipseElement EclipseOutput
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
class FileEclipseOutput extends EclipseOutput {

    private EclipseTask task;

    /**
     * Creates a new instance of the output object.
     * 
     * @param parent
     *        The parent task.
     * @since Ant-Eclipse 1.0
     */
    FileEclipseOutput(EclipseTask parent) {
        super(parent.getEclipse());
        task = parent;
    }

    /**
     * Checks if it is necessary to write the content of the file
     * <tt>.settings/xxx.prefs</tt>; if the file does not exist or it is older than the
     * decription in the ant project.
     * 
     * @param name
     *        A name of the file with preferences.
     * @return <tt>True</tt> if the file needs to be (re)written.
     * @see EclipseOutput#isPreferencesUpToDate(String)
     * @since Ant-Eclipse 1.0
     */
    boolean isPreferencesUpToDate(String name) {
        return isFileUpToDate(".settings/" + name + ".prefs");
    }

    /**
     * Checks if it is necessary to write the content of the file .project; if the file
     * does not exist or it is older than the decription in the ant project.
     * 
     * @return <tt>True</tt> if the file needs to be (re)written.
     * @see EclipseOutput#isProjectUpToDate()
     * @since Ant-Eclipse 1.0
     */
    boolean isProjectUpToDate() {
        return isFileUpToDate(".project");
    }

    /**
     * Checks if it is necessary to write the content of the file .classpath; if the file
     * does not exist or it is older than the decription in the ant project.
     * 
     * @return <tt>True</tt> if the content needs to be (re)written.
     * @see EclipseOutput#isClassPathUpToDate()
     * @since Ant-Eclipse 1.0
     */
    boolean isClassPathUpToDate() {
        return isFileUpToDate(".classpath");
    }

    /**
     * Opens an existing file <tt>xxx.prefs</tt> in the subdirectory <tt>.settings</tt>
     * under the destination directory.
     * 
     * @param name
     *        A name of the file with preferences.
     * @return Input stream for the opened file.
     * @see EclipseOutput#openPreferences(String)
     * @since Ant-Eclipse 1.0
     */
    InputStream openPreferences(String name) {
        return openFile(".settings/" + name + ".prefs");
    }

    /**
     * Opens an existing file named ".project" in the destination directory.
     * 
     * @return Input stream for the opened file.
     * @see EclipseOutput#openPreferences(String)
     * @since Ant-Eclipse 1.0
     */
    InputStream openProject() {
        return openFile(".project");
    }

    /**
     * Opens an existing file named ".classpath" in the destination directory.
     * 
     * @return Input stream for the opened file.
     * @see EclipseOutput#openClassPath()
     * @since Ant-Eclipse 1.0
     */
    InputStream openClassPath() {
        return openFile(".classpath");
    }

    /**
     * Creates a new file <tt>xxx.prefs</tt> in the subdirectory <tt>.settings</tt>
     * under the destination directory.
     * 
     * @param name
     *        A name of the file with preferences.
     * @see OutputStream EclipseOutput#createPreferences(String)
     * @return Output stream for the created file.
     * @since Ant-Eclipse 1.0
     */
    OutputStream createPreferences(String name) {
        return createFile(".settings/" + name + ".prefs");
    }

    /**
     * Creates a new file named ".project" in the destination directory.
     * 
     * @see OutputStream EclipseOutput#createProject()
     * @return Output stream for the created file.
     * @since Ant-Eclipse 1.0
     */
    OutputStream createProject() {
        return createFile(".project");
    }

    /**
     * Creates a new file named ".classpath" in the destination directory.
     * 
     * @see OutputStream EclipseOutput#createClassPath()
     * @return Output stream for the created file.
     * @since Ant-Eclipse 1.0
     */
    OutputStream createClassPath() {
        return createFile(".classpath");
    }

    private boolean isFileUpToDate(String name) {
        return !getEclipse().isUpdateAlways()
                && FileUtils.getFileUtils().isUpToDate(
                        resolveFile(task.getProject().getProperty("ant.file")),
                        resolveFile(name));
    }

    private InputStream openFile(String name) {
        File input = resolveFile(name);
        try {
            task.log("Opening the file \"" + input.getAbsolutePath() + "\".",
                    Project.MSG_VERBOSE);
            return new FileInputStream(input);
        } catch (FileNotFoundException exception) {
            throw new BuildException("The file \"" + input.getAbsolutePath()
                    + "\" was not found.", exception);
        }
    }

    private OutputStream createFile(String name) {
        File output = resolveFile(name);
        try {
            task.log("Creating the file \"" + output.getAbsolutePath() + "\".",
                    Project.MSG_VERBOSE);
            FileUtils.getFileUtils().createNewFile(output, true);
            return new FileOutputStream(output);
        } catch (IOException exception) {
            throw new BuildException("Creation of the file \"" + output.getAbsolutePath()
                    + "\" falied.", exception);
        }
    }

    private File resolveFile(String name) {
        FileUtils utils = FileUtils.getFileUtils();
        return utils.resolveFile(utils.resolveFile(task.getProject().getBaseDir(),
                getEclipse().getDestDir().getName()), name);
    }

}
