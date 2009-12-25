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
 * Describes an element under the element classpath referencing a path consisting
 * optionally from more elements (directories or files). Only one attribute of the two
 * <tt>path</tt> and <tt>pathref</tt> is allowed.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class ClassPathEntryPathElement extends ClassPathEntryElement {

    private Reference pathRef = null;

    /**
     * Creates a new instance of the classpathentry-src element.
     * 
     * @since Ant-Eclipse 1.0
     */
    public ClassPathEntryPathElement() {
    }

    /**
     * Returns a reference to the kind-of-element specific path value or <tt>null</tt>
     * if it has not been set, which should be considered an error. However, descendant
     * classes may set a default for this attribute or to the attribute <tt>path</tt>,
     * which can be used instead or together.
     * 
     * @return A reference to the kind-of-element specific path value or <tt>null</tt>
     *         if not having been set (descendant classes may return a default in this
     *         case).
     */
    public Reference getPathRef() {
        return pathRef;
    }

    /**
     * Sets the reference to a path of the classpathentry element.
     * 
     * @param value
     *        A reference to the kind-of-element specific path value.
     * @throws BuildException
     *         If an attribute <tt>path</tt> has been set.
     * @since Ant-Eclipse 1.0
     */
    public void setPathRef(Reference value) {
        if (getPath() != null)
            throw new BuildException(
                    "Only one of the attributes \"path\" or \"pathref\" is allowed.");
        pathRef = value;
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
        if (getPathRef() != null)
            throw new BuildException(
                    "Only one of the attributes \"path\" or \"pathref\" is allowed.");
        super.setPath(value);
    }

    /**
     * Performs the validation of the element at the time when the whole build file was
     * parsed checking the content of the element and possibly adding mandatory attributes
     * with default settings.
     * 
     * @since Ant-Eclipse 1.0
     */
    public void validate() {
        if (getPath() == null && pathRef == null)
            throw new BuildException(
                    "None of the attributes \"path\" or \"pathref\" was set in an element under \"classpath\".");
    }

}
