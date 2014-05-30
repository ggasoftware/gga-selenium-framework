/****************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 *
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 ***************************************************************************/
package com.ggasoftware.uitest.utils;

import static com.ggasoftware.uitest.utils.ConvertUtil.arrayToString;

/**
 * Commandline Executor
 *
 * @author Belin Yury
 */
public final class Executor {

    private Executor(){}

    /**
     *   Executes the specified string command with PAREMETERS in a separate process.
     *
     *   @param path - path where file for execution is located
     *   @param command - command file for execution (ex.: cmd.exe)
     *   @param parameters - parameters for command
     *
     *   @return the exit value for the subprocess.
     */
    public static int runExec( String path, String command, String[] parameters){
        String parameter = arrayToString(parameters, " ");
        ReporterNGExt.logTechnical(String.format("runExec: %s%s %s", path, command, parameter));
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(path + command + " " + parameter);
            pr.waitFor();
            ReporterNG.LOG.info(pr.exitValue());
            return pr.exitValue();

        } catch (Exception e) {
            ReporterNG.LOG.info(e.getStackTrace());
            return -1;
        }
    }

    /**
     *   Executes the specified string command in a separate process.
     *
     *   @param path - path where file for execution is located
     *   @param command - command file for execution (ex.: cmd.exe)
     *
     *   @return the exit value for the subprocess.
     */
    public static int runExec(String path, String command ){
        String[] parameters = {" "};
        return runExec(path, command, parameters);
    }

}
