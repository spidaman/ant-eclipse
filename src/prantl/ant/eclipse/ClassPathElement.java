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
 * Configures contents of the file .classpath with paths to source files, binary java
 * archives and output directories, this class specifically the root element
 * <tt>classpath</tt>.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class ClassPathElement {

    private Vector sources = new Vector();

    private Vector libraries = new Vector();

    private ClassPathEntryContainerElement container = null;

    private Vector variables = new Vector();

    private ClassPathEntryOutputElement output = null;

    /**
     * Creates a new instance of the classpath element.
     * 
     * @since Ant-Eclipse 1.0
     */
    public ClassPathElement() {
    }

    /**
     * Returns a list of instances of the class ClassPathEntrySourceElement describing
     * elements <tt>classpathentry</tt> of the kind "src" in the file .classpath. If it
     * is empty, a single source element should be created with default settings.
     * 
     * @return A list of instances of the class ClassPathEntrySourceElement.
     */
    public Vector getSources() {
        return sources;
    }

    /**
     * Returns a list of instances of the class ClassPathEntryLibraryElement describing
     * elements <tt>classpathentry</tt> of the kind "lib" in the file .classpath.
     * 
     * @return A list of instances of the class ClassPathEntryLibraryElement.
     */
    public Vector getLibraries() {
        return libraries;
    }

    /**
     * Returns an instance of the class ClassPathEntryContainerElement describing the
     * element <tt>classpathentry</tt> of the kind "con" in the file .classpath or
     * <tt>null</tt> if the element was not present, which means creating a default one.
     * 
     * @return An instance of the class ClassPathEntryContainerElement or <tt>null</tt>
     *         if not having been present.
     */
    public ClassPathEntryContainerElement getContainer() {
        return container;
    }

    /**
     * Returns a list of instances of the class ClassPathEntryVariableElement describing
     * elements <tt>classpathentry</tt> of the kind "var" in the file .classpath.
     * 
     * @return A list of instances of the class ClassPathEntryVariableElement.
     */
    public Vector getVariables() {
        return variables;
    }

    /**
     * Returns an instance of the class ClassPathEntryOutputElement describing the element
     * <tt>classpathentry</tt> of the kind "output" in the file .classpath or
     * <tt>null</tt> if the element was not present, which means creating a default one.
     * 
     * @return An instance of the class ClassPathEntryOutputElement or <tt>null</tt> if
     *         not having been present.
     */
    public ClassPathEntryOutputElement getOutput() {
        return output;
    }

    /**
     * Adds a definition of the classpathentry element of the kind "src".
     * 
     * @return A definition of the classpathentry-src element.
     * @since Ant-Eclipse 1.0
     */
    public ClassPathEntrySourceElement createSource() {
        sources.addElement(new ClassPathEntrySourceElement());
        return (ClassPathEntrySourceElement) sources.lastElement();
    }

    /**
     * Adds a definition of the classpathentry element of the kind "lib".
     * 
     * @return A definition of the classpathentry-lib element.
     * @since Ant-Eclipse 1.0
     */
    public ClassPathEntryLibraryElement createLibrary() {
        libraries.addElement(new ClassPathEntryLibraryElement());
        return (ClassPathEntryLibraryElement) libraries.lastElement();
    }

    /**
     * Adds a definition of the classpathentry element of the kind "con".
     * 
     * @return A definition of the classpathentry-con element.
     * @since Ant-Eclipse 1.0
     */
    public ClassPathEntryContainerElement createContainer() {
        if (container != null)
            throw new BuildException(
                    "The element <classpathentry kind=\"con\" ...> has been already defined.");
        return container = new ClassPathEntryContainerElement();
    }

    /**
     * Adds a definition of the classpathentry element of the kind "var".
     * 
     * @return A definition of the classpathentry-var element.
     * @since Ant-Eclipse 1.0
     */
    public ClassPathEntryVariableElement createVariable() {
        variables.addElement(new ClassPathEntryVariableElement());
        return (ClassPathEntryVariableElement) variables.lastElement();
    }

    /**
     * Adds a definition of the classpathentry element of the kind "output".
     * 
     * @return A definition of the classpathentry-output element.
     * @since Ant-Eclipse 1.0
     */
    public ClassPathEntryOutputElement createOutput() {
        if (output != null)
            throw new BuildException(
                    "The element <classpathentry kind=\"output\" ...> has been already defined.");
        return output = new ClassPathEntryOutputElement();
    }

}
