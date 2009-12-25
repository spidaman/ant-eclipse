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

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import junit.framework.TestCase;

/**
 * Simplifies testing of an ant task with a prepared build file automating the task
 * execution and providing content of the log, standard output and error and an eventual
 * exception.
 * 
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt; (based on
 *         org.apache.tools.ant.BuildFileTest from the Ant distribution)
 */
public abstract class BuildFileTestBase extends TestCase {

    /** The project currently being processed. */
    Project project;

    /** The buffer for the captured log messages. */
    StringBuffer logBuffer;

    /** The buffer for the captured standard output. */
    StringBuffer outBuffer;

    /** The buffer for the captured standard error. */
    StringBuffer errBuffer;

    /** The exception eventually thrown during a task execution. */
    BuildException buildException;

    /** The maximum log level of the captured traces. */
    int logLevel = -1;

    /** The name of the target the captured traces must match. */
    String targetName = null;

    /** The name of the task the captured traces must match. */
    String taskName = null;

    /**
     * Passes the construction of a new instance of the test fixture from the descendant
     * to the ancestor.
     * 
     * @param name
     *        The name of the test fixture.
     * @since Ant-Eclipse 1.0
     * @see TestCase#TestCase(String)
     */
    protected BuildFileTestBase(String name) {
        super(name);
    }

    /**
     * Sets the name of the target, which traces only will be captured. <tt>Null</tt>
     * turns off the matching, any target will be monitored.
     * 
     * @param targetName
     *        The name of the target to capture the traces of.
     * @since Ant-Eclipse 1.0
     */
    protected void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    /**
     * Sets the name of the task, which traces only will be captured. <tt>Null</tt>
     * turns off the matching, any task will be monitored.
     * 
     * @param taskName
     *        The name of the target to capture the traces of.
     * @since Ant-Eclipse 1.0
     */
    protected void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Sets the maximum log level of messages to be captured. <tt>-1</tt> turns off the
     * matching, any log level will be monitored.
     * 
     * @param logLevel
     *        Maximum log level of the captured traces.
     * @since Ant-Eclipse 1.0
     */
    protected void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Initializes a new project from the specified build file capturing selected traces.
     * This is usually the first call in a file-oriented test case; most methods of this
     * class expect this one having been called before. Log capturing can be controlled by
     * calling setTarget, setTask and setLogLevel. The previous project and captured log
     * is discarded by calling this method.
     * 
     * @param fileName
     *        The name of the project file to process.
     * @throws NullPointerException
     *         If the parameter <tt>fileName</tt> is <tt>null</tt>.
     * @since Ant-Eclipse 1.0
     */
    protected void parseBuildFile(String fileName) {
        logBuffer = new StringBuffer();
        project = new Project();
        project.init();
        File buildFile = new File(fileName);
        project.setUserProperty("ant.file", buildFile.getAbsolutePath());
        project.addBuildListener(new LogBuildListener());
        ProjectHelper helper = ProjectHelper.getProjectHelper();
        project.addReference("ant.projectHelper", helper);
        helper.parse(project, buildFile);
    }

    /**
     * Initializes a new project from the specified build file capturing selected traces
     * and expecting an exception to be thrown during the parsing. This is usually the
     * only call in a file-oriented test case; most checking methods of this class expect
     * this one having been called before. Log capturing can be controlled by calling
     * setTarget, setTask and setLogLevel. The previous project and captured log is
     * discarded by calling this method.
     * 
     * @param fileName
     *        The name of the project file to process.
     * @param cause
     *        The reason why the exception should have been thrown
     * @throws NullPointerException
     *         If the parameters <tt>fileName</tt> or <tt>cause</tt> are <tt>null</tt>.
     * @since Ant-Eclipse 1.0
     */
    protected void parseInvalidBuildFile(String fileName, String cause) {
        try {
            parseBuildFile(fileName);
            fail("BuildException expected because " + cause);
        } catch (BuildException exception) {
            buildException = exception;
        }
    }

    /**
     * Executes the specified target expecting a successful run.
     * 
     * @pre The method parseBuildFile has been called.
     * @param targetName
     *        The name of the target to execute.
     * @throws BuildException
     *         If the parameter <tt>targetName</tt> is <tt>null</tt>.
     * @since Ant-Eclipse 1.0
     */
    protected void executeTarget(String targetName) {
        PrintStream systemOut = System.out;
        PrintStream systemErr = System.err;
        try {
            logBuffer = new StringBuffer();
            outBuffer = new StringBuffer();
            systemOut.flush();
            System.setOut(new PrintStream(new StringBufferOutputStream(outBuffer)));
            errBuffer = new StringBuffer();
            systemErr.flush();
            System.setErr(new PrintStream(new StringBufferOutputStream(errBuffer)));
            buildException = null;
            project.executeTarget(targetName);
        } finally {
            System.setOut(systemOut);
            System.setErr(systemErr);
        }
    }

    /**
     * Executes the specified target expecting an exception to be thrown during the run.
     * 
     * @pre The method parseBuildFile has been called.
     * @param targetName
     *        The name of the target to execute.
     * @param cause
     *        The reason why the exception should have been thrown if it had not happened
     *        to be written in a sentence after 'bacause'.
     * @throws BuildException
     *         If the parameters <tt>targetName</tt> or <tt>cause</tt> are
     *         <tt>null</tt>.
     * @since Ant-Eclipse 1.0
     */
    protected void executeFailingTarget(String targetName, String cause) {
        try {
            executeTarget(targetName);
            fail("BuildException expected because " + cause);
        } catch (BuildException exception) {
            buildException = exception;
        }
    }

    /**
     * Gets the project, which has been most recently initialized or <tt>null</tt> if
     * there has been none yet.
     * 
     * @pre The method parseBuildFile has been called.
     * @return The recently initialized project instance.
     * @since Ant-Eclipse 1.0
     */
    protected Project getProject() {
        return project;
    }

    /**
     * Gets the most recent log or <tt>null</tt> if there has been none written yet.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget rewrite the previous content of the buffer.
     * @return The content of the log.
     * @since Ant-Eclipse 1.0
     */
    protected String getLog() {
        return logBuffer.toString();
    }

    /**
     * Gets the most recent standard output or <tt>null</tt> if there has been none
     * written yet.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget rewrite the previous content of the buffer.
     * @return The content of the standard output.
     * @since Ant-Eclipse 1.0
     */
    protected String getOutput() {
        return normalizeEolns(outBuffer);
    }

    /**
     * Gets the most recent standard error or <tt>null</tt> if there has been none
     * written yet.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget rewrite the previous content of the buffer.
     * @return The content of the standard error.
     * @since Ant-Eclipse 1.0
     */
    protected String getError() {
        return normalizeEolns(errBuffer);
    }

    /**
     * Gets the most recently thrown exception instance or <tt>null</tt> if there has
     * been none thrown (the task execution was successful).
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget rewrite the previous content of the buffer.
     * @return The content of the standard error.
     * @since Ant-Eclipse 1.0
     */
    protected BuildException getBuildException() {
        return buildException;
    }

    /**
     * Checks that the captured log has the specified content. The comparison is case
     * sensitive.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget rewrite the previous content of the buffer.
     * @param content
     *        The content to be matched.
     * @since Ant-Eclipse 1.0
     */
    protected void assertLogEquals(String content) {
        String realLog = getLog();
        assertTrue("Log expected to be equal to \"" + content + "\" (actual log \""
                + realLog + "\")", realLog.equals(content));
    }

    /**
     * Checks that the captured log has the specified value as a substring. The comparison
     * is case sensitive.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget rewrite the previous content of the buffer.
     * @param part
     *        The part of the content to be searched for.
     * @since Ant-Eclipse 1.0
     */
    protected void assertLogContains(String part) {
        String realLog = getLog();
        assertTrue("Log expected to contain \"" + part + "\" (actual log \"" + realLog
                + "\")", realLog.indexOf(part) >= 0);
    }

    /**
     * Checks that the captured standard output has the specified content. The comparison
     * is case sensitive.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget rewrite the previous content of the buffer.
     * @param content
     *        The content to be matched.
     * @since Ant-Eclipse 1.0
     */
    protected void assertOutputEquals(String content) {
        String realOutput = getOutput();
        assertTrue("Output expected to be equal to \"" + content + "\" (actual output \""
                + realOutput + "\")", realOutput.equals(content));
    }

    /**
     * Checks that the captured standard output has the specified value as a substring.
     * The comparison is case sensitive.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget rewrite the previous content of the buffer.
     * @param part
     *        The part of the content to be searched for.
     * @since Ant-Eclipse 1.0
     */
    protected void assertOutputContains(String part) {
        String realOutput = getOutput();
        assertTrue("Log expected to contain \"" + part + "\" (actual log \"" + realOutput
                + "\")", realOutput.indexOf(part) >= 0);
    }

    /**
     * Checks that the captured standard error has the specified content. The comparison
     * is case sensitive.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget rewrite the previous content of the buffer.
     * @param content
     *        The content to be matched.
     * @since Ant-Eclipse 1.0
     */
    protected void assertErrorEquals(String content) {
        String realError = getError();
        assertTrue("Error expected to be equal to \"" + content + "\" (actual error \""
                + realError + "\")", realError.equals(content));
    }

    /**
     * Checks that the captured standard error has the specified value as a substring. The
     * comparison is case sensitive.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget rewrite the previous content of the buffer.
     * @param part
     *        The part of the content to be searched for.
     * @since Ant-Eclipse 1.0
     */
    protected void assertErrorContains(String part) {
        String realError = getError();
        assertTrue("Error expected to contain \"" + part + "\" (actual error \""
                + realError + "\")", realError.indexOf(part) >= 0);
    }

    /**
     * Checks that the message of the last thrown exception has the specified value. The
     * comparison is case sensitive.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget rewrite the previous content of the buffer.
     * @param message
     *        The message to be matched.
     * @since Ant-Eclipse 1.0
     */
    protected void assertBuildExceptionEquals(String message) {
        String realMessage = getBuildException().getMessage();
        assertTrue("BuildException expected with message equal to \"" + message
                + "\" (actual message \"" + realMessage + "\").", realMessage
                .equals(message));
    }

    /**
     * Checks that the message of the last thrown exception has the specified value as a
     * substring. The comparison is case sensitive.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget rewrite the previous content of the buffer.
     * @param part
     *        The part of the message to be searched for.
     * @since Ant-Eclipse 1.0
     */
    protected void assertBuildExceptionContains(String part) {
        String realMessage = getBuildException().getMessage();
        assertTrue("BuildException expected to contain \"" + part
                + "\" (actual message \"" + realMessage + "\").", realMessage
                .indexOf(part) >= 0);
    }

    /**
     * Checks that the property has the specified value. The comparison is case sensitive.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget may change the properties.
     * @param property
     *        The name of the property to check.
     * @param value
     *        The expected value.
     * @since Ant-Eclipse 1.0
     */
    protected void assertPropertyEquals(String property, String value) {
        String result = project.getProperty(property);
        assertEquals("property " + property, value, result);
    }

    /**
     * Checks that the property has the specified value as a substring. The comparison is
     * case sensitive.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget may change the properties.
     * @param property
     *        The name of the property to check.
     * @param part
     *        The part of the value to be searched for.
     * @since Ant-Eclipse 1.0
     */
    protected void assertPropertyContains(String property, String part) {
        String result = project.getProperty(property);
        assertTrue("The property " + property + " expected to contain \"" + part
                + "\" (actual value \"" + result + "\").", result.indexOf(part) >= 0);
    }

    /**
     * Checks that the property has been set to <tt>true</tt>.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget may change the properties.
     * @param property
     *        The name of the property to check.
     * @since Ant-Eclipse 1.0
     */
    protected void assertPropertyTrue(String property) {
        assertPropertyEquals(property, "true");
    }

    /**
     * Checks that the property has been set to <tt>false</tt>.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget may change the properties.
     * @param property
     *        The name of the property to check.
     * @since Ant-Eclipse 1.0
     */
    protected void assertPropertyFalse(String property) {
        assertPropertyEquals(property, "false");
    }

    /**
     * Checks that the property has never been set.
     * 
     * @pre The method parseBuildFile has been called; the methods executeTarget or
     *      executeFailingTarget may change the properties.
     * @param property
     *        The name of the property to check.
     * @since Ant-Eclipse 1.0
     */
    protected void assertPropertyUnset(String property) {
        assertPropertyEquals(property, null);
    }

    /**
     * Returns a new string copy with all possible forms of an end-of-line delimiter made
     * of carriage-return and/or line-feed (\r, \n, \r\n) replaced by a single line-feed
     * character (\n).
     * 
     * @param buffer
     *        The source buffer to normalize eolns in.
     * @return A new string with the content of the source buffer but with normalized
     *         eolns.
     * @since Ant-Eclipse 1.0
     */
    protected String normalizeEolns(StringBuffer buffer) {
        StringBuffer cleanedBuffer = new StringBuffer();
        boolean cr = false;
        for (int i = 0; i < buffer.length(); i++) {
            char ch = buffer.charAt(i);
            if (ch == '\r') {
                cr = true;
                continue;
            } else if (ch != '\n') {
                if (cr) {
                    cleanedBuffer.append('\n');
                    cr = false;
                }
            } else
                cr = false;

            cleanedBuffer.append(ch);
        }
        return cleanedBuffer.toString();
    }

    /**
     * An extension of OutputStream writing every byte as a character in the internal
     * StringBuffer.
     * 
     * @since Ant-Eclipse 1.0
     * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
     */
    private static class StringBufferOutputStream extends OutputStream {

        private StringBuffer buffer;

        /**
         * Creates a new instance of the output stream. Default constructor.
         * 
         * @param buffer
         *        The output buffer to write into.
         * @since Ant-Eclipse 1.0
         */
        StringBufferOutputStream(StringBuffer buffer) {
            this.buffer = buffer;
        }

        /**
         * Stores the byte as a character in the internal buffer.
         * 
         * @see java.io.OutputStream#write(int)
         * @since Ant-Eclipse 1.0
         */
        public void write(int b) {
            buffer.append((char) b);
        }

    }

    /**
     * An implementation of BuildListener capturing log messages matching the configured
     * target name, task name and the maximum log level.
     * 
     * @since Ant-Eclipse 1.0
     * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
     */
    private class LogBuildListener implements BuildListener {

        /**
         * Creates a new instance of the test listener. Default constructor.
         * 
         * @since Ant-Eclipse 1.0
         */
        LogBuildListener() {
        }

        /**
         * Empty implementation of the according interface method.
         * 
         * @see BuildListener#buildStarted(BuildEvent)
         * @since Ant-Eclipse 1.0
         */
        public void buildStarted(BuildEvent event) {
        }

        /**
         * Empty implementation of the according interface method.
         * 
         * @see BuildListener#buildFinished(BuildEvent)
         * @since Ant-Eclipse 1.0
         */
        public void buildFinished(BuildEvent event) {
        }

        /**
         * Empty implementation of the according interface method.
         * 
         * @see BuildListener#targetStarted(BuildEvent)
         * @since Ant-Eclipse 1.0
         */
        public void targetStarted(BuildEvent event) {
        }

        /**
         * Empty implementation of the according interface method.
         * 
         * @see BuildListener#targetFinished(BuildEvent)
         * @since Ant-Eclipse 1.0
         */
        public void targetFinished(BuildEvent event) {
        }

        /**
         * Empty implementation of the according interface method.
         * 
         * @see BuildListener#taskStarted(BuildEvent)
         * @since Ant-Eclipse 1.0
         */
        public void taskStarted(BuildEvent event) {
        }

        /**
         * Empty implementation of the according interface method.
         * 
         * @see BuildListener#taskFinished(BuildEvent)
         * @since Ant-Eclipse 1.0
         */
        public void taskFinished(BuildEvent event) {
        }

        /**
         * Remembers the logged message if it matches the chosen criteria.
         * 
         * @param event
         *        The event causing this method to be called.
         * @see BuildListener#messageLogged(BuildEvent)
         * @since Ant-Eclipse 1.0
         */
        public void messageLogged(BuildEvent event) {
            if ((targetName == null || targetName.equals(event.getTarget().getName()))
                    && (taskName == null || taskName
                            .equals(event.getTask().getTaskName()))
                    && (logLevel < 0 || event.getPriority() <= logLevel)) {
                logBuffer.append('[');
                logBuffer.append(logLevelPrefixes[event.getPriority()]);
                logBuffer.append("] [");
                logBuffer.append(event.getTarget().getName());
                logBuffer.append("] [");
                logBuffer.append(event.getTask().getTaskName());
                logBuffer.append("] ");
                logBuffer.append(event.getMessage());
                logBuffer.append('\n');
            }
        }

        private final String[] logLevelPrefixes = { "ERROR  ", "WARNING", "INFO   ",
                "VERBOSE", "DEBUG  " };

    }

}
