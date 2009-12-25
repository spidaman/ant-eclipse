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

/**
 * Base for the entries under the element classpath, specifically the elements
 * <tt>classpathentry</tt>.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public abstract class ClassPathEntryElement {

    private String path = null;

    /**
     * Creates a new instance of the classpathentry element.
     * 
     * @since Ant-Eclipse 1.0
     */
    ClassPathEntryElement() {
    }

    /**
     * Returns a kind-of-element specific path value or <tt>null</tt> if it has not been
     * set, which should be considered an error. However, descendant classes may set a
     * default for this attribute.
     * 
     * @return A kind-of-element specific path value or <tt>null</tt> if not having been
     *         set (descendant classes may return a default in this case).
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path of the classpathentry element.
     * 
     * @param value
     *        A kind-of-element specific path value.
     * @since Ant-Eclipse 1.0
     */
    public void setPath(String value) {
        path = value;
    }

    /**
     * Performs the validation of the element at the time when the whole build file was
     * parsed checking the content of the element and possibly adding mandatory attributes
     * with default settings.
     * 
     * @since Ant-Eclipse 1.0
     */
    public void validate() {
        if (path == null)
            throw new BuildException(
                    "The mandatory attribute \"path\" was missing in an element under \"classpath\".");
    }

}
