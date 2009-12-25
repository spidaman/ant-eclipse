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
 * Configures contents of the file .project with the name of the project, this class
 * specifically the root element <tt>project</tt>.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class ProjectElement {

    private String name = null;

    /**
     * Creates a new instance of the project element.
     * 
     * @since Ant-Eclipse 1.0
     */
    public ProjectElement() {
    }

    /**
     * Returns the name of the Eclipse project or <tt>null</tt> if it has not been set
     * and the default shoud be used.
     * 
     * @return The name of the Eclipse project or <tt>null</tt> if not having been set.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Eclipse project. The name of the ant build project will be
     * used by default if not set here.
     * 
     * @param value
     *        A valid name of the Eclipse project.
     * @since Ant-Eclipse 1.0
     */
    public void setName(String value) {
        name = value;
    }

}
