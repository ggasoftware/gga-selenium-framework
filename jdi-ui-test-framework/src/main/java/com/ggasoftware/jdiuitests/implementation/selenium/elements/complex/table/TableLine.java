package com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table;

import com.ggasoftware.jdiuitests.core.utils.common.LinqUtils;
import com.ggasoftware.jdiuitests.core.utils.common.ReflectionUtils;
import com.ggasoftware.jdiuitests.core.utils.common.Timer;
import com.ggasoftware.jdiuitests.core.utils.map.MapArray;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.SelectElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ITableLine;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IText;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.exception;

/**
 * Created by 12345 on 25.10.2014.
 */
abstract class TableLine extends Element implements ITableLine {
    private int startIndex = 1;
    public int getStartIndex() {
        return startIndex;
    }
    public boolean hasHeader;
    public ElementIndexType elementIndex;

    public Table table;

    protected int count = 0;
    public void setCount(int value) { if (table.cache) count = value; }
    public int count() {
        if (count > 0)
             return count;
        else {
            String[] headers = headers();
            return headers != null ? headers.length : 0;
        }
    }

    public void clean() { headers = null; count = 0; }
    protected String[] headers;
    public void setHeaders(String[] value) {
        if (table.cache)
            headers = value.clone();
    }
    protected String[] getHeadersTextAction() {
        return LinqUtils.select(getHeadersAction(), WebElement::getText)
                .toArray(new String[1]);
    }
    protected abstract List<WebElement> getHeadersAction();
    public final MapArray<String, SelectElement> header() {
        return new MapArray<>(getHeadersAction(), WebElement::getText, SelectElement::new);
    }
    public final SelectElement header(String name) {
        return header().get(name);
    }
    public String[] headers() {
        if (headers != null)
            return headers.clone();
        String[] localHeaders = Timer.getResultAction(this::getHeadersTextAction);
        localHeaders = (hasHeader)
            ? localHeaders
            : getNumList(localHeaders.length);
        setHeaders(localHeaders);
        if (localHeaders == null || localHeaders.length == 0)
            throw exception("Can't get headers for Table");
        setCount(localHeaders.length);
        return localHeaders;
    }

    protected String[] getNumList(int count) {
        return getNumList(count, 1);
    }
    protected String[] getNumList(int count, int from) {
        List<String> result = new ArrayList<>();
        for (int i = from; i < count + from; i++)
        result.add(Integer.toString(i));
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
