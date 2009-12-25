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

/**
 * Describes an element under the element classpath referring to compiled classes. The
 * attributes <tt>exported</tt> and <tt>source</tt> are optional.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public abstract class ClassPathEntryBinaryElement extends ClassPathEntryPathElement {

    private boolean exported = false;

    private String source = null;

    private String javadoc = null;

    /**
     * Creates a new instance of the element containing binary code.
     * 
     * @since Ant-Eclipse 1.0
     */
    public ClassPathEntryBinaryElement() {
    }

    /**
     * Returns if the referred compiled byte-code is exported during the jar-file
     * generation (not by default).
     * 
     * @return <tt>True</tt> if the referred compiled byte-code is exported during the
     *         jar-file generation.
     */
    public boolean getExported() {
        return exported;
    }

    /**
     * Sets if the referred compiled byte-code is exported during the jar-file generation.
     * 
     * @param flag
     *        <tt>True</tt> if the referred compiled byte-code is exported during the
     *        jar-file generation.
     * @since Ant-Eclipse 1.0
     */
    public void setExported(boolean flag) {
        exported = flag;
    }

    /**
     * Returns a path with source code to the referred compiled byte-code or <tt>null</tt>
     * if it has not been set, which means that the sources are not available.
     * 
     * @return A path with source code to the referred compiled byte-code or <tt>null</tt>
     *         if not having been set.
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the path with source code to the referred compiled byte-code.
     * 
     * @param value
     *        The path with source code to the referred compiled byte-code.
     * @since Ant-Eclipse 1.0
     */
    public void setSource(String value) {
        source = value;
    }

    /**
     * Returns a path with a generated javadoc documentation to the referred compiled
     * byte-code or <tt>null</tt> if it has not been set, which means that the javadoc
     * is not available.
     * 
     * The path must be in formats accepted by Eclipse; either a path to a directory or a
     * path to a zip-archive with correct prefixing:
     * 
     * <ul>
     * <li>file:/path/to/a/directory/</li>
     * <li>jar:file:/path/to/a/file.zip!/</li>
     * </ul>
     * 
     * @return A path with javadoc to the referred compiled byte-code or <tt>null</tt>
     *         if not having been set.
     */
    public String getJavadoc() {
        return javadoc;
    }

    /**
     * Sets the path with a generated javadoc documentation to the referred compiled
     * byte-code.
     * 
     * The path must be in formats accepted by Eclipse; either a path to a directory or a
     * path to a zip-archive with correct prefixing:
     * 
     * <ul>
     * <li>file:/path/to/a/directory/</li>
     * <li>jar:file:/path/to/a/file.zip!/</li>
     * </ul>
     * 
     * @param value
     *        The path with javadoc to the referred compiled byte-code.
     * @since Ant-Eclipse 1.0
     */
    public void setJavadoc(String value) {
        javadoc = value;
    }

}
