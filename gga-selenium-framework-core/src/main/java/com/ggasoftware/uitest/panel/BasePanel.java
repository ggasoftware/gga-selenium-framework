/**
 * *************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 * <p>
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 * <p>
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 * *************************************************************************
 */
package com.ggasoftware.uitest.panel;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.utils.ReporterNGExt;
import com.ggasoftware.uitest.utils.Sleeper;

/**
 * Base Panel
 *
 * @author Alexeenko Yan
 */
public class BasePanel<T extends BasePanel> extends Element<T> {

    private static final char COMPONENT_LEVEL = ReporterNGExt.COMPONENT_LEVEL;

    //Base Panel for future goal
    public final T logFailed(String msg) {
        ReporterNGExt.logFailed(COMPONENT_LEVEL, msg);
        return (T) this;
    }

    /**
     * Sleep in msec
     * @param msec - timeout in msec
     * @return BasePanel instance
     */
    public T sleep(long msec) {
        Sleeper.sleepTight(msec);
        return (T) this;
    }

    @Override
    public T waitForExists() {
        super.waitForExists();
        return (T) this;
    }

}