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
 * Describes an element <tt>classpathentry</tt> under the element classpath,
 * specifically the kind "con". Presets the value
 * "org.eclipse.jdt.launching.JRE_CONTAINER" for the attribute <tt>path</tt> to use the
 * default configured JRE in Eclipse by default. An instance of this element will be
 * created automatically with the default settings if none present. Only a single instace
 * of this element is allowed, if ever.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class ClassPathEntryContainerElement extends ClassPathEntryElement {

    /**
     * Creates a new instance of the classpathentry-con element.
     * 
     * @since Ant-Eclipse 1.0
     */
    public ClassPathEntryContainerElement() {
        setPath("org.eclipse.jdt.launching.JRE_CONTAINER");
    }

}
