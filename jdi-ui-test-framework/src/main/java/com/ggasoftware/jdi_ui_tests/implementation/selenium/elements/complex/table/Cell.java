package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.SelectElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.ISelect;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.last;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.WebDriverByUtils.fillByMsgTemplate;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.MapInterfaceToElement.getClassFromInterface;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.apiInteract.ContextType.Locator;

/**
 * Created by 12345 on 25.10.2014.
 */
/**
 * Cell have some info about its position:<br>
 * ColumnName, RowName, ColumnIndex, RowIndex<br>
 * You can do some actions with Cell:<br>
 * Click, Select, getText, waitText, waitMatchText<br>
 * Also you can use get() method to get webElement of specified for table Type and do any possible action with it<br>
 * */
class Cell extends SelectElement implements ISelect, ICell {
    private int rowIndex;
    private int columnIndex;
    private WebElement webElement;
    private Table table;
    private int columnNum;
    public int columnNum() { return columnNum; }
    private int rowNum;
    public int rowNum() { return rowNum; }
    private String columnName;
    public String columnName() {
        return (columnName != null && !columnName.equals(""))
            ? columnName
            : table.columns().headers()[columnNum]; }
    private String rowName;
    public String rowName() {
        return (rowName != null && !rowName.equals(""))
                ? rowName
                : table.rows().headers()[rowNum]; }

    @Override
    protected String getTextAction() {return get().getText(); }

    private By cellLocatorTemplate = By.xpath(".//tr[{1}]/td[{0}]");
    private Class<?>[] columnsTemplate;

    @Override
    protected void clickAction() { get().click(); }
    public SelectElement get() { return (webElement != null)
        ? new SelectElement(webElement)
        : new SelectElement(fillByMsgTemplate(cellLocatorTemplate, columnIndex, rowIndex));
    }
    public <T extends BaseElement> T get(Class<?> clazz) {
        T instance;
        try {
            instance = (clazz.isInterface())
                    ? (T) getClassFromInterface(clazz).newInstance()
                    : (T) clazz.newInstance();
        } catch (Exception ex) { throw asserter.exception("Can't get Cell from interface/class: " + last((clazz + "").split("\\."))); }
        return get(instance);
    }
    public <T extends BaseElement> T get(T cell) {
        By locator = cell.getLocator();
        if (locator == null || locator.toString().equals(""))
            locator = cellLocatorTemplate;
        if (!locator.toString().contains("{0}") || !locator.toString().contains("{1}"))
            throw asserter.exception("Can't create cell with locator template " + cell.getLocator() +
                    ". Template for Cell should contains '{0}' - for column and '{1}' - for row indexes.");
        cell.getAvatar().byLocator = fillByMsgTemplate(cell.getLocator(), rowIndex, columnIndex);
        cell.getAvatar().context.add(Locator, getLocator());
        return cell;
    }

    public Cell(WebElement webElement, int columnNum, int rowNum, String colName, String rowName,
                By cellLocatorTemplate, Class<?>[] columnsTemplate, Table table) {
        this.webElement = webElement;
        this.columnNum = columnNum;
        this.rowNum = rowNum;
        this.columnName = colName;
        this.rowName = rowName;
        if (cellLocatorTemplate != null)
            this.cellLocatorTemplate = cellLocatorTemplate;
        this.columnsTemplate = columnsTemplate;
    }
    public Cell(int columnIndex, int rowIndex, int columnNum, int rowNum, String colName, String rowName,
                By cellLocatorTemplate, Class<?>[] columnsTemplate, Table table) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
        this.columnNum = columnNum;
        this.rowNum = rowNum;
        this.columnName = colName;
        this.rowName = rowName;
        if (cellLocatorTemplate != null)
            this.cellLocatorTemplate = cellLocatorTemplate;
        this.columnsTemplate = columnsTemplate;
    }

    public Cell updateData(String colName, String rowName) {
        if ((columnName == null || columnName.equals("")) && !(colName == null || colName.equals("")))
            columnName = colName;
        if ((this.rowName == null || this.rowName.equals("")) && !(rowName == null || rowName.equals("")))
        this.rowName = rowName;
        return this;
    }
}
