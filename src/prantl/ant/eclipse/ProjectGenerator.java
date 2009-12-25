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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/**
 * Provides the functionality generating the file <tt>.project</tt> for the supplied
 * task object. It is expected to be used within the class EclipseTask.
 * 
 * @see EclipseTask
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
final class ProjectGenerator {

    private final static String[] defaultBuidCommands = {
            "org.eclipse.jdt.core.javabuilder", "org.eclipse.ajdt.core.ajbuilder" };

    private final static String[][] defaultNatures = {
            { "org.eclipse.jdt.core.javanature" },
            { "org.eclipse.ajdt.ui.ajnature", "org.eclipse.jdt.core.javanature" } };

    private EclipseTask task;

    /**
     * Creates a new instance of the generating object.
     * 
     * @param parent
     *        The parent task.
     * @since Ant-Eclipse 1.0
     */
    ProjectGenerator(EclipseTask parent) {
        task = parent;
    }

    /**
     * Generates the file <tt>.project</tt> using the supplied output object.
     * 
     * @since Ant-Eclipse 1.0
     */
    void generate() {
        ProjectElement project = task.getEclipse().getProject();
        if (project == null) {
            task.log("There was no description of a project found.", Project.MSG_WARN);
            return;
        }
        EclipseOutput output = task.getOutput();
        if (output.isProjectUpToDate()) {
            task.log("The project definition is up-to-date.", Project.MSG_WARN);
            return;
        }
        task.log("Writing the project definition in the mode \""
                + task.getEclipse().getMode().getValue() + "\".");
        XmlWriter writer = null;
        try {
            writer = new XmlWriter(new OutputStreamWriter(new BufferedOutputStream(output
                    .createProject()), "UTF-8"));
            writer.writeXmlDeclaration("UTF-8");
            writer.openElement("projectDescription");
            writer.openElement("name");
            String name = project.getName();
            if (name == null)
                name = task.getProject().getProperty("ant.project.name");
            if (name == null)
                throw new BuildException(
                        "Both name of the Eclipse and name of the Ant project cannot be missing.");
            task.log("Project name is \"" + name + "\".", Project.MSG_VERBOSE);
            writer.writeText(name);
            writer.closeElement("name");
            writer.openElement("comment");
            writer.closeElement("comment");
            writer.openElement("projects");
            writer.closeElement("projects");
            writer.openElement("buildSpec");
            writer.openElement("buildCommand");
            writer.openElement("name");
            int mode = task.getEclipse().getMode().getIndex();
            writer.writeText(defaultBuidCommands[mode]);
            writer.closeElement("name");
            writer.openElement("arguments");
            writer.closeElement("arguments");
            writer.closeElement("buildCommand");
            writer.closeElement("buildSpec");
            writer.openElement("natures");
            for (int i = 0, length = defaultNatures[mode].length; i != length; ++i) {
                writer.openElement("nature");
                writer.writeText(defaultNatures[mode][i]);
                writer.closeElement("nature");
            }
            writer.closeElement("natures");
            writer.closeElement("projectDescription");
        } catch (UnsupportedEncodingException exception) {
            throw new BuildException("Encoder to UTF-8 is not supported.", exception);
        } catch (IOException exception) {
            throw new BuildException("Writing the project definition failed.", exception);
        } finally {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException exception1) {
                    throw new BuildException("Closing the project definition failed.",
                            exception1);
                }
        }
    }

}
