package com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table;

import com.ggasoftware.jdiuitests.core.settings.JDISettings;
import com.ggasoftware.jdiuitests.core.utils.common.LinqUtils;
import com.ggasoftware.jdiuitests.core.utils.common.WebDriverByUtils;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.MapInterfaceToElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.apiInteract.ContextType;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.SelectElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.ISelect;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by 12345 on 25.10.2014.
 */

/**
 * Cell have some info about its position:<br>
 * ColumnName, RowName, ColumnIndex, RowIndex<br>
 * You can do some actions with Cell:<br>
 * Click, Select, getText, waitText, waitMatchText<br>
 * Also you can use get() method to get Element of specified for table Type and do any possible action with it<br>
 */
class Cell extends SelectElement implements ISelect, ICell {
    private int rowIndex;
    private int columnIndex;
    private Table table;
    private int columnNum;
    private WebElement webElement;
    private int rowNum;
    private String columnName;
    private String rowName;
    private By cellLocatorTemplate = By.xpath(".//tr[{1}]/td[{0}]");
    private Class<?>[] columnsTemplate;

    public Cell(WebElement webElement, int columnNum, int rowNum, String colName, String rowName,
                By cellLocatorTemplate, Table table) {
        this.webElement = webElement;
        this.columnNum = columnNum;
        this.rowNum = rowNum;
        this.columnName = colName;
        this.rowName = rowName;
        if (cellLocatorTemplate != null)
            this.cellLocatorTemplate = cellLocatorTemplate;
        this.table = table;
    }

    public Cell(int columnIndex, int rowIndex, int columnNum, int rowNum, String colName, String rowName,
                By cellLocatorTemplate, Table table) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
        this.columnNum = columnNum;
        this.rowNum = rowNum;
        this.columnName = colName;
        this.rowName = rowName;
        if (cellLocatorTemplate != null)
            this.cellLocatorTemplate = cellLocatorTemplate;
        this.table = table;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    public int columnNum() {
        return columnNum;
    }

    public int rowNum() {
        return rowNum;
    }

    public String columnName() {
        return (columnName != null && !columnName.equals(""))
                ? columnName
                : table.columns().headers()[columnNum - 1];
    }

    public String rowName() {
        return (rowName != null && !rowName.equals(""))
                ? rowName
                : table.rows().headers()[rowNum - 1];
    }

    @Override
    protected String getTextAction() {
        return get().getText();
    }

    @Override
    protected void clickAction() {
        get().click();
    }

    public SelectElement get() {
        return (webElement != null)
                ? new SelectElement(webElement)
                : new SelectElement(WebDriverByUtils.fillByMsgTemplate(cellLocatorTemplate, columnIndex, rowIndex));
    }

    public <T extends BaseElement> T get(Class<T> clazz) {
        T instance;
        try {
            instance = (clazz.isInterface())
                    ? (T) MapInterfaceToElement.getClassFromInterface(clazz).newInstance()
                    : clazz.newInstance();
        } catch (Exception ex) {
            throw JDISettings.exception("Can't get Cell from interface/class: " + LinqUtils.last((clazz + "").split("\\.")));
        }
        return get(instance);
    }

    public <T extends BaseElement> T get(T cell) {
        By locator = cell.getLocator();
        if (locator == null || locator.toString().equals(""))
            locator = cellLocatorTemplate;
        if (!locator.toString().contains("{0}") || !locator.toString().contains("{1}"))
            throw JDISettings.exception("Can't create cell with locator template " + cell.getLocator() +
                    ". Template for Cell should contains '{0}' - for column and '{1}' - for row indexes.");
        cell.getAvatar().byLocator = WebDriverByUtils.fillByMsgTemplate(locator, rowIndex, columnIndex);
        cell.getAvatar().context.add(ContextType.Locator, table.getLocator());
        return cell;
    }

    public Cell updateData(String colName, String rowName) {
        if ((columnName == null || columnName.equals("")) && !(colName == null || colName.equals("")))
            columnName = colName;
        if ((this.rowName == null || this.rowName.equals("")) && !(rowName == null || rowName.equals("")))
            this.rowName = rowName;
        return this;
    }
}
