package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table;

import com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils;
import com.ggasoftware.jdi_ui_tests.core.utils.common.ReflectionUtils;
import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ITableLine;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;

/**
 * Created by 12345 on 25.10.2014.
 */
abstract class TableLine extends Element implements ITableLine {
    public int startIndex = 1;
    public boolean hasHeader;
    public ElementIndexType elementIndex;

    public Table table;

    protected int count = -1;
    public void setCount(int value) { count = value; }
    public int count() {
        return (count > 0)
            ? count
            : headers() != null ? headers.length : 0;
    }

    protected String[] headers;
    public void setHeaders(String[] value) { headers = value; }
    protected String[] getHeadersTextAction() {
        return LinqUtils.select(getHeadersAction(), WebElement::getText)
                .toArray(new String[1]);
    }
    protected abstract List<WebElement> getHeadersAction();
    public final List<WebElement> header() { return getHeadersAction(); }
    public final String[] headers() {
        if (headers != null)
            return headers;
        String[] localHeaders = Timer.getResultAction(this::getHeadersTextAction);
        setHeaders((hasHeader)
            ? localHeaders
            : getNumList(localHeaders.length));
        if (headers == null || headers.length == 0)
            throw asserter.exception("Can't get headers for Table");
        setCount(headers.length);
        return headers;
    }

    protected String[] getNumList(int count) {
        return getNumList(count, 1);
    }
    protected String[] getNumList(int count, int from) {
        List<String> result = new ArrayList<>();
        for (int i = from; i < count + from; i++)
        result.add(i + "");
        return result.toArray(new String[count]);
    }

    public final void update(TableLine tableLine) {
        if (tableLine.count > 0)
            setCount(tableLine.count());
        if (tableLine.startIndex != 1)
            startIndex = tableLine.startIndex;
        if (tableLine.headers != null && tableLine.headers.length > 0)
            setHeaders(tableLine.headers());
        if ((ReflectionUtils.isClass(tableLine.getClass(), Columns.class) && !tableLine.hasHeader)
                || (ReflectionUtils.isClass(tableLine.getClass(), Rows.class) && tableLine.hasHeader))
            hasHeader = tableLine.hasHeader;
        if (tableLine.elementIndex != ElementIndexType.Nums)
        elementIndex = tableLine.elementIndex;
    }

    public final MapArray<String, MapArray<String, String>> getAsText() {
        return get().toMapArray(line -> line.toMapArray(IText::getText));
    }
}
