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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.StringTokenizer;

import junit.framework.TestCase;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;

/**
 * Test fixture with unit test cases for the class <tt>EclipseTask</tt>.
 * 
 * @see EclipseTask
 * @since Ant-Eclipse 1.0
 * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
 */
public class EclipseTaskTest extends TestCase {

    /**
     * Creates a new instance of the test fixture. Default constructor.
     * 
     * @since Ant-Eclipse 1.0
     */
    public EclipseTaskTest() {
    }

    /**
     * Helper class constructing the task object for the class <tt>EclipseTask</tt> with
     * the minimum environment simulating an ant-build file with a single target with a
     * single task.
     * 
     * @see EclipseTask
     * @since Ant-Eclipse 1.0
     * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
     */
    static final class EclipseTaskTester extends EclipseTask {

        /**
         * Creates a new instance of the task with the minimum ant-like environment.
         * 
         * @param object
         *        An object that the output is delegated into.
         * @throws NullPointerException
         *         If the parameter <tt>object</tt> is null.
         * @since Ant-Eclipse 1.0
         */
        EclipseTaskTester(EclipseOutput object) {
            super(object);
            setProject(new Project());
            getProject().init();
            getProject().setUserProperty("ant.project.name", "eclipse");
            // setTaskType("eclipse");
            // setTaskName("eclipse");
            // setOwningTarget(new Target());
        }

    }

    /**
     * Testing output class performing output into in-memory streams.
     * 
     * @see EclipseOutput
     * @since Ant-Eclipse 1.0
     * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
     */
    static final class MemoryEclipseOutput extends EclipseOutput {

        private Hashtable settings = new Hashtable();

        private ByteArrayOutputStream project = null;

        private ByteArrayOutputStream classPath = null;

        /**
         * Creates a new instance of the output object.
         * 
         * @param element
         *        An object containing the configuration.
         * @see EclipseOutput#EclipseOutput(EclipseElement)
         * @since Ant-Eclipse 1.0
         */
        MemoryEclipseOutput(EclipseElement element) {
            super(element);
        }

        /**
         * @see EclipseOutput#isPreferencesUpToDate(String)
         * @since Ant-Eclipse 1.0
         */
        boolean isPreferencesUpToDate(String name) {
            return false;
        }

        /**
         * @see EclipseOutput#isProjectUpToDate()
         * @since Ant-Eclipse 1.0
         */
        boolean isProjectUpToDate() {
            return false;
        }

        /**
         * @see EclipseOutput#isClassPathUpToDate()
         * @since Ant-Eclipse 1.0
         */
        boolean isClassPathUpToDate() {
            return false;
        }

        /**
         * @see EclipseOutput#openPreferences(String)
         * @since Ant-Eclipse 1.0
         */
        InputStream openPreferences(String name) {
            ByteArrayOutputStream preferences = (ByteArrayOutputStream) settings
                    .get(name);
            return preferences == null ? null : new ByteArrayInputStream(preferences
                    .toByteArray());
        }

        /**
         * @see EclipseOutput#openProject()
         * @since Ant-Eclipse 1.0
         */
        InputStream openProject() {
            return project == null ? null : new ByteArrayInputStream(project
                    .toByteArray());
        }

        /**
         * @see EclipseOutput#openClassPath()
         * @since Ant-Eclipse 1.0
         */
        InputStream openClassPath() {
            return classPath == null ? null : new ByteArrayInputStream(classPath
                    .toByteArray());
        }

        /**
         * @see EclipseOutput#createPreferences(String)
         * @since Ant-Eclipse 1.0
         */
        OutputStream createPreferences(String name) {
            ByteArrayOutputStream preferences = new ByteArrayOutputStream();
            settings.put(name, preferences);
            return preferences;
        }

        /**
         * @see EclipseOutput#createProject()
         * @since Ant-Eclipse 1.0
         */
        OutputStream createProject() {
            return project = new ByteArrayOutputStream();
        }

        /**
         * @see EclipseOutput#createClassPath()
         * @since Ant-Eclipse 1.0
         */
        OutputStream createClassPath() {
            return classPath = new ByteArrayOutputStream();
        }

    }

    /**
     * Testing build listener collecting the log messages up to the specified level.
     * 
     * @since Ant-Eclipse 1.0
     * @author Ferdinand Prantl &lt;prantl@users.sourceforge.net&gt;
     */
    static final class MemoryLogListener implements BuildListener {

        private StringBuffer log = new StringBuffer();

        private int logLevel;

        /**
         * Constructs a test listener which will store the log events below or equal the
         * given level.
         * 
         * @param logLevel
         *        Maximum logLevel to watch.
         */
        public MemoryLogListener(int logLevel) {
            this.logLevel = logLevel;
        }

        /**
         * @return Collected log messages.
         */
        String getLog() {
            return log.toString();
        }

        /**
         * Fired before any targets are started.
         * 
         * @param event
         *        Context information about the event.
         */
        public void buildStarted(BuildEvent event) {
        }

        /**
         * Fired after the last target has finished. This event will still be thrown if an
         * error occured during the build.
         * 
         * @param event
         *        Context information about the event.
         */
        public void buildFinished(BuildEvent event) {
        }

        /**
         * Fired when a target is started.
         * 
         * @param event
         *        Context information about the event.
         */
        public void targetStarted(BuildEvent event) {
        }

        /**
         * Fired when a target has finished. This event will still be thrown if an error
         * occured during the build.
         * 
         * @param event
         *        Context information about the event.
         */
        public void targetFinished(BuildEvent event) {
        }

        /**
         * Fired when a task is started.
         * 
         * @param event
         *        Context information about the event.
         */
        public void taskStarted(BuildEvent event) {
        }

        /**
         * Fired when a task has finished. This event will still be thrown if an error
         * occured during the build.
         * 
         * @param event
         *        Context information about the event.
         */
        public void taskFinished(BuildEvent event) {
        }

        /**
         * Fired whenever a message is logged.
         * 
         * @param event
         *        Context information about the event.
         */
        public void messageLogged(BuildEvent event) {
            if (event.getPriority() <= logLevel) {
                log.append(levels[event.getPriority()]);
                log.append(' ');
                log.append(event.getMessage());
                log.append('\n');
            }
        }

        private static final String[] levels = new String[] { "ERROR  ", "WARNING",
                "INFO   ", "VERBOSE", "DEBUG  " };

    }

    /**
     * Tests executing the task with the following configuration:
     * 
     * <pre>
     *   &lt;eclipse /&gt;
     * </pre>
     * 
     * @throws Exception
     *         If the task execution fails.
     */
    public void testExecuteWithEmptyEclipseElement() throws Exception {
        MemoryEclipseOutput output = new MemoryEclipseOutput(new EclipseElement());
        EclipseTaskTester task = new EclipseTaskTester(output);
        MemoryLogListener logListener = new MemoryLogListener(Project.MSG_VERBOSE);
        task.getProject().addBuildListener(logListener);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        PrintStream systemOut = System.out;
        PrintStream systemErr = System.err;
        try {
            systemOut.flush();
            systemErr.flush();
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));
            task.execute();
        } finally {
            System.setOut(systemOut);
            System.setErr(systemErr);
        }

        assertEquals(0, out.size());
        assertEquals(0, err.size());
        assertNull(output.openPreferences(OrgEclipseCoreResourcesPreferencesElement
                .getPackageName()));
        assertNull(output.openPreferences(OrgEclipseCoreRuntimePreferencesElement
                .getPackageName()));
        assertNull(output.openPreferences(OrgEclipseJdtCorePreferencesElement
                .getPackageName()));
        assertNull(output.openPreferences(OrgEclipseJdtUiPreferencesElement
                .getPackageName()));
        assertNull(output.openProject());
        assertNull(output.openClassPath());
        assertEquals("WARNING There were no settings found.\n"
                + "WARNING There was no description of a project found.\n"
                + "WARNING There was no description of a classpath found.\n", logListener
                .getLog());
    }

    /**
     * Tests executing the task with the following configuration:
     * 
     * <pre>
     *   &lt;eclipse&gt;
     *     &lt;settings&gt;
     *       &lt;resources version=&quot;2&quot; encoding=&quot;UTF-8&quot; /&gt;
     *     &lt;/settings&gt;
     *   &lt;/eclipse&gt;
     * </pre>
     * 
     * @throws Exception
     *         If the task execution fails.
     */
    public void testExecuteWithSettingsElementWithVersionAndEncoding() throws Exception {
        EclipseElement eclipse = new EclipseElement();
        SettingsElement settings = new SettingsElement();
        OrgEclipseCoreResourcesPreferencesElement resources = settings.createResources();
        resources.setVersion("2");
        resources.setEncoding("UTF-8");
        eclipse.setSettings(settings);

        MemoryEclipseOutput output = new MemoryEclipseOutput(eclipse);
        EclipseTaskTester task = new EclipseTaskTester(output);
        MemoryLogListener logListener = new MemoryLogListener(Project.MSG_VERBOSE);
        task.getProject().addBuildListener(logListener);
        task.execute();

        String settingsOutput = streamToString(output
                .openPreferences(OrgEclipseCoreResourcesPreferencesElement
                        .getPackageName()));
        // "#Fri Aug 19 07:58:50 CEST 2005\n"
        assertTrue(settingsOutput.startsWith("#"));
        assertTrue(settingsOutput.endsWith("\n"));
        assertEqualAllLines("encoding/<project>=UTF-8\n"
                + "eclipse.preferences.version=2\n", skipLine(settingsOutput));
        assertNull(output.openPreferences(OrgEclipseCoreRuntimePreferencesElement
                .getPackageName()));
        assertNull(output.openPreferences(OrgEclipseJdtCorePreferencesElement
                .getPackageName()));
        assertNull(output.openPreferences(OrgEclipseJdtUiPreferencesElement
                .getPackageName()));
        assertNull(output.openProject());
        assertNull(output.openClassPath());
        assertEquals(
                "INFO    Writing the preferences for \"org.eclipse.core.resources\".\n"
                        + "WARNING There was no description of a project found.\n"
                        + "WARNING There was no description of a classpath found.\n",
                logListener.getLog());
    }

    /**
     * Tests executing the task with the following configuration:
     * 
     * <pre>
     *   &lt;eclipse&gt;
     *     &lt;project /&gt;
     *   &lt;/eclipse&gt;
     * </pre>
     * 
     * @throws Exception
     *         If the task execution fails.
     */
    public void testExecuteWithEmptyProjectElement() throws Exception {
        EclipseElement eclipse = new EclipseElement();
        eclipse.setProject(new ProjectElement());

        MemoryEclipseOutput output = new MemoryEclipseOutput(eclipse);
        EclipseTaskTester task = new EclipseTaskTester(output);
        MemoryLogListener logListener = new MemoryLogListener(Project.MSG_VERBOSE);
        task.getProject().addBuildListener(logListener);
        task.execute();

        assertNull(output.openPreferences(OrgEclipseCoreResourcesPreferencesElement
                .getPackageName()));
        assertNull(output.openPreferences(OrgEclipseCoreRuntimePreferencesElement
                .getPackageName()));
        assertNull(output.openPreferences(OrgEclipseJdtCorePreferencesElement
                .getPackageName()));
        assertNull(output.openPreferences(OrgEclipseJdtUiPreferencesElement
                .getPackageName()));
        String projectOutput = streamToString(output.openProject());
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<projectDescription>\n" + "  <name>eclipse</name>\n" + "  <comment>\n"
                + "  </comment>\n" + "  <projects>\n" + "  </projects>\n"
                + "  <buildSpec>\n" + "    <buildCommand>\n"
                + "      <name>org.eclipse.jdt.core.javabuilder</name>\n"
                + "      <arguments>\n" + "      </arguments>\n"
                + "    </buildCommand>\n" + "  </buildSpec>\n" + "  <natures>\n"
                + "    <nature>org.eclipse.jdt.core.javanature</nature>\n"
                + "  </natures>\n" + "</projectDescription>", projectOutput);
        assertNull(output.openClassPath());
        assertEquals("WARNING There were no settings found.\n"
                + "INFO    Writing the project definition in the mode \"java\".\n"
                + "VERBOSE Project name is \"eclipse\".\n"
                + "WARNING There was no description of a classpath found.\n", logListener
                .getLog());
    }

    /**
     * Tests executing the task with the following configuration:
     * 
     * <pre>
     *   &lt;eclipse&gt;
     *     &lt;project /&gt;
     *   &lt;/eclipse&gt;
     * </pre>
     * 
     * @throws Exception
     *         If the task execution fails.
     */
    public void testExecuteWithProjectElementWithName() throws Exception {
        EclipseElement eclipse = new EclipseElement();
        ProjectElement project = new ProjectElement();
        project.setName("test");
        eclipse.setProject(project);

        MemoryEclipseOutput output = new MemoryEclipseOutput(eclipse);
        EclipseTaskTester task = new EclipseTaskTester(output);
        MemoryLogListener logListener = new MemoryLogListener(Project.MSG_VERBOSE);
        task.getProject().addBuildListener(logListener);
        task.execute();

        assertNull(output.openPreferences(OrgEclipseCoreResourcesPreferencesElement
                .getPackageName()));
        assertNull(output.openPreferences(OrgEclipseCoreRuntimePreferencesElement
                .getPackageName()));
        assertNull(output.openPreferences(OrgEclipseJdtCorePreferencesElement
                .getPackageName()));
        assertNull(output.openPreferences(OrgEclipseJdtUiPreferencesElement
                .getPackageName()));
        String projectOutput = streamToString(output.openProject());
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<projectDescription>\n" + "  <name>test</name>\n" + "  <comment>\n"
                + "  </comment>\n" + "  <projects>\n" + "  </projects>\n"
                + "  <buildSpec>\n" + "    <buildCommand>\n"
                + "      <name>org.eclipse.jdt.core.javabuilder</name>\n"
                + "      <arguments>\n" + "      </arguments>\n"
                + "    </buildCommand>\n" + "  </buildSpec>\n" + "  <natures>\n"
                + "    <nature>org.eclipse.jdt.core.javanature</nature>\n"
                + "  </natures>\n" + "</projectDescription>", projectOutput);
        assertNull(output.openClassPath());
        assertEquals("WARNING There were no settings found.\n"
                + "INFO    Writing the project definition in the mode \"java\".\n"
                + "VERBOSE Project name is \"test\".\n"
                + "WARNING There was no description of a classpath found.\n", logListener
                .getLog());
    }

    private String streamToString(InputStream input) throws IOException {
        InputStreamReader reader = new InputStreamReader(input, "UTF-8");
        StringBuffer content = new StringBuffer();
        for (int ch; (ch = reader.read()) != -1;)
            content.append((char) ch);
        return content.toString();
    }

    private String skipLine(String input) {
        int next = input.indexOf('\n');
        return next >= 0 ? input.substring(next + 1) : "";
    }

    private void assertEqualAllLines(String expected, String actual) {
        HashSet expectedLines = new HashSet(Arrays.asList(split(expected, "\n")));
        String[] actualLines = split(actual, "\n");
        for (int i = 0; i != actualLines.length; ++i)
            if (!expectedLines.remove(actualLines[i]))
                fail("The line " + (i + 1) + " (\"" + actualLines[i]
                        + ") from the actual content \"" + actual
                        + "\" does not match any in the expected content \"" + expected
                        + "\".");
        int size = expectedLines.size();
        if (size != 0)
            fail(size + " line(s) ("
                    + join((String[]) expectedLines.toArray(new String[] {}), "\n")
                    + ") from the expected content \"" + expected
                    + "\" were missing in the actual content \"" + actual + "\".");
    }

    /**
     * Splits the input string into an array of strings using the specified delimiter.
     * 
     * @param input
     *        The input string to be splitted.
     * @param delimiter
     *        The delimiting token.
     * @throws NullPointerException
     *         If some of the input parameters are null.
     * @return An array with strings as delimited parts of the input.
     */
    public static String[] split(String input, String delimiter) {
        StringTokenizer tokenizer = new StringTokenizer(input, delimiter);
        String[] result = new String[tokenizer.countTokens()];
        for (int i = 0; tokenizer.hasMoreTokens(); ++i)
            result[i] = tokenizer.nextToken();
        return result;
    }

    /**
     * Splits the input array into a string using the specified delimiter.
     * 
     * @param input
     *        The input array to be joined.
     * @param delimiter
     *        The delimiting token.
     * @throws NullPointerException
     *         If some of the input parameters are null.
     * @return An string joined with the specified delimiter from he input array.
     */
    public static String join(String[] input, String delimiter) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i != input.length; ++i) {
            if (i != 0)
                result.append(delimiter);
            result.append(input[i]);
        }
        return result.toString();
    }

}
