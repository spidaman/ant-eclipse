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

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Reference;

/**
 * Describes an element <tt>classpathentry</tt> under the element classpath,
 * specifically the kind "src". Presets the value "" for the attribute <tt>path</tt> to
 * include all sources in the base directory by default. An instance of this element will
 * be created automatically with the default settings if none present.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class ClassPathEntrySourceElement extends ClassPathEntryPathElement {

    private String excluding = null;

    private String output = null;

    private boolean explicit = false;

    /**
     * Creates a new instance of the classpathentry-src element.
     * 
     * @since Ant-Eclipse 1.0
     */
    public ClassPathEntrySourceElement() {
        super.setPath("");
    }

    /**
     * Returns a list of project-relative paths to be excluded from the source path
     * delimited by vertical lines ('|') or <tt>null</tt> if it has not been set, which
     * means do not exlude anything.
     * 
     * @return A list of project-relative paths delimited by vertical lines ('|') to
     *         exclude or <tt>null</tt> if not having been set.
     */
    public String getExcluding() {
        return excluding;
    }

    /**
     * Sets the list of project-relative paths to be excluded from the source path
     * delimited by vertical lines ('|').
     * 
     * @param value
     *        The list of project-relative paths to be excluded from the sourcepath.
     * @since Ant-Eclipse 1.0
     */
    public void setExcluding(String value) {
        excluding = value;
    }

    /**
     * Returns a source-specific output directory to compile the Java classes there or
     * <tt>null</tt> if it has not been set, which means to use the common one.
     * 
     * @return A source-specific output directory or <tt>null</tt> if not having been
     *         set.
     */
    public String getOutput() {
        return output;
    }

    /**
     * Sets the source-specific output directory to compile the Java classes there.
     * 
     * @param value
     *        The source-specific output directory.
     * @since Ant-Eclipse 1.0
     */
    public void setOutput(String value) {
        output = value;
    }

    /**
     * Sets the path of the classpathentry element.
     * 
     * @param value
     *        A kind-of-element specific path value.
     * @throws BuildException
     *         If an attribute <tt>pathref</tt> has been set.
     * @since Ant-Eclipse 1.0
     */
    public void setPath(String value) {
        explicit = true;
        super.setPath(value);
    }

    /**
     * Sets the reference to a path of the classpathentry element. Additionally resets the
     * eventually present attribute <tt>path</tt> if it was set to the current directory
     * by default.
     * 
     * @param value
     *        A reference to the kind-of-element specific path value.
     * @throws BuildException
     *         If an attribute <tt>pathref</tt> has been set.
     * @since Ant-Eclipse 1.0
     */
    public void setPathRef(Reference value) {
        if (!explicit)
            setPath(null);
        super.setPathRef(value);
    }

}
