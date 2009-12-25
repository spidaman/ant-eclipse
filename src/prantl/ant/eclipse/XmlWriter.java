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

import java.io.IOException;
import java.io.Writer;

/**
 * Simplifies writing XML output.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
class XmlWriter {

    private Writer writer;

    private int nestedLevel = 0;

    private boolean insideDocument = false;

    private boolean containsText = false;

    /**
     * Creates a new instance on the top of an existing Writer.
     * 
     * @param writer
     *        Writer to perform the output to.
     */
    XmlWriter(Writer writer) {
        this.writer = writer;
    }

    /**
     * Closes the underlying writer.
     * 
     * @throws IOException
     *         If there was an error closing the underlying writer.
     */
    void close() throws IOException {
        writer.close();
    }

    /**
     * Writes the xml declaration without attributes.
     * 
     * @throws IOException
     *         If there was an error writing into the underlying writer.
     */
    void writeXmlDeclaration() throws IOException {
        writer.write("<?xml version=\"1.0\"?>");
        insideDocument = true;
    }

    /**
     * Writes the xml declaration with the attribute <tt>encoding</tt>.
     * 
     * @param encoding
     *        Encoding of the output stream used by the underlying writer.
     * @throws NullPointerException
     *         If the parameter <tt>encoding</tt> is null.
     * @throws IOException
     *         If there was an error writing into the underlying writer.
     */
    void writeXmlDeclaration(String encoding) throws IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>");
        insideDocument = true;
    }

    /**
     * Writes the xml declaration with the attribute <tt>encoding</tt>.
     * 
     * @param standalone
     *        <tt>True</tt> if the standalone should be set to "yes" or <tt>false</tt>
     *        if to "no".
     * @param encoding
     *        Encoding of the output stream used by the underlying writer.
     * @throws NullPointerException
     *         If the parameter <tt>encoding</tt> is null.
     * @throws IOException
     *         If there was an error writing into the underlying writer.
     */
    void writeXmlDeclaration(boolean standalone, String encoding) throws IOException {
        writer.write("<?xml version=\"1.0\" standalone=\"" + (standalone ? "yes" : "no")
                + "\" encoding=\"" + encoding + "\"?>");
        insideDocument = true;
    }

    /**
     * Writes start of the opening tag of an element.
     * 
     * @param name
     *        The name of the element.
     * @throws IOException
     *         If there was an error writing into the underlying writer.
     */
    void openOpeningTag(String name) throws IOException {
        if (insideDocument)
            writer.write('\n');
        else
            insideDocument = true;
        writeIndentation();
        writer.write('<');
        writer.write(name);
    }

    /**
     * Writes an attribute into the current element. All special characters in XML are
     * escaped in the value.
     * 
     * @param name
     *        The name of the attribute.
     * @param value
     *        The value of the attribute.
     * @throws IOException
     *         If there was an error writing into the underlying writer.
     */
    void appendAttribute(String name, String value) throws IOException {
        writer.write(' ');
        writer.write(name);
        writer.write("=\"");
        writer.write(escapeAttributeValue(value));
        writer.write('\"');
    }

    /**
     * Writes end of the opening tag of an element.
     * 
     * @throws IOException
     *         If there was an error writing into the underlying writer.
     */
    void closeOpeningTag() throws IOException {
        writer.write('>');
        ++nestedLevel;
    }

    /**
     * Writes end of the degenerated element.
     * 
     * @throws IOException
     *         If there was an error writing into the underlying writer.
     */
    void closeDegeneratedElement() throws IOException {
        writer.write(" />");
    }

    /**
     * Writes the opening tag of an element.
     * 
     * @param name
     *        The name of the element.
     * @throws IOException
     *         If there was an error writing into the underlying writer.
     */
    void openElement(String name) throws IOException {
        openOpeningTag(name);
        closeOpeningTag();
    }

    /**
     * Writes the closing tag of an element.
     * 
     * @param name
     *        The name of the element.
     * @throws IOException
     *         If there was an error writing into the writer.
     */
    void closeElement(String name) throws IOException {
        --nestedLevel;
        if (!containsText) {
            writer.write('\n');
            writeIndentation();
        } else
            containsText = false;
        writer.write("</");
        writer.write(name);
        writer.write(">");
    }

    /**
     * Writes value of an element. All special characters in XML are escaped.
     * 
     * @param text
     *        The value of an element to write.
     * @throws IOException
     *         If there was an error writing into the underlying writer.
     */
    void writeText(String text) throws IOException {
        writer.write(escapeCharacterData(text));
        containsText = true;
    }

    /**
     * Replaces all special characters in an attrubte XML with the corresponding entity
     * referenced and returns the result as a new String if necessary.
     * 
     * @param text
     *        Input text, possibly with special characters.
     * @return Output text with no special characters.
     */
    String escapeAttributeValue(String text) {
        if (text.indexOf('&') < 0 && text.indexOf('<') < 0 && text.indexOf('>') < 0
                && text.indexOf('\"') < 0 && text.indexOf('\'') < 0)
            return text;
        int length = text.length();
        StringBuffer result = new StringBuffer(length);
        for (int i = 0; i != length; ++i) {
            char ch = text.charAt(i);
            switch (ch) {
            case '&':
                result.append("&amp;");
                break;
            case '<':
                result.append("&lt;");
                break;
            case '>':
                result.append("&gt;");
                break;
            case '\"':
                result.append("&quot;");
                break;
            case '\'':
                result.append("&apos;");
                break;
            default:
                result.append(ch);
            }
        }
        return result.toString();
    }

    /**
     * Replaces all special characters in a character data node with the corresponding
     * entity referenced and returns the result as a new String if necessary.
     * 
     * @param text
     *        Input text, possibly with special characters.
     * @return Output text with no special characters.
     */
    String escapeCharacterData(String text) {
        if (text.indexOf('&') < 0 && text.indexOf('<') < 0 && text.indexOf('>') < 0)
            return text;
        int length = text.length();
        StringBuffer result = new StringBuffer(length);
        for (int i = 0; i != length; ++i) {
            char ch = text.charAt(i);
            switch (ch) {
            case '&':
                result.append("&amp;");
                break;
            case '<':
                result.append("&lt;");
                break;
            case '>':
                result.append("&gt;");
                break;
            default:
                result.append(ch);
            }
        }
        return result.toString();
    }

    private void writeIndentation() throws IOException {
        for (int i = 0; i != nestedLevel; ++i)
            writer.write("  ");
    }

}
