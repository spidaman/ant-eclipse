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
 * Configures a component preferences file under the directory <tt>.settings</tt> on the
 * low level - allowing to define the variables directly.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class GeneralPreferencesElement extends PreferencesElement {

    /**
     * Creates a new instance of the element for a general preferences under the settings
     * element.
     * 
     * @param parent
     *        The parent settings element of this preferences one.
     * @since Ant-Eclipse 1.0
     */
    public GeneralPreferencesElement(SettingsElement parent) {
        super(parent);
    }

    /**
     * Sets the name of the file with preferences.
     * 
     * @param value
     *        A name of the file with preferences.
     * @since Ant-Eclipse 1.0
     */
    public void setName(String value) {
        internalSetName(value);
    }

    /**
     * Adds a definition of a new variable element.
     * 
     * @return A definition of a new variable element.
     * @since Ant-Eclipse 1.0
     */
    public VariableElement createVariable() {
        return internalCreateVariable();
    }

}
