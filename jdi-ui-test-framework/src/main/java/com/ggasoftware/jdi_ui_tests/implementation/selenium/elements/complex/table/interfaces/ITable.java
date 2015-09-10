package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces;

import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.SelectElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Column;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Columns;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Row;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Rows;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.JDIAction;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by roman.i on 20.10.2014.
 */

public interface ITable<T extends SelectElement> extends IText {
    /** Get Cell by column/row index (Int) or name(String) */
    @JDIAction
    ICell cell(Column column, Row row);
    /** Get all Cells with values equals to searched value */
    @JDIAction
    List<ICell> cells(String value);
    /** Get all Cells with values matches to searched regex */
    @JDIAction
    List<ICell> cellsMatch(String regex);
    /** Get first Cell with equals to searched value  */
    @JDIAction
    ICell cell(String value);
    /** Get first Cell with matches to searched regex  */
    @JDIAction
    ICell cellMatch(String regex);
    /** Searches Rows in table matches specified criteria colNameValues - list of search criteria in format columnName=columnValue<br>
     *  e.g. rows("Name=Roman", "Profession=QA") <br>
     *  Each Row is map: columnName:cell
     */
    @JDIAction
    MapArray<String, MapArray<String, ICell>> rows(String... colNameValues);
    /** Searches Columns in table matches specified criteria rowNameValues - list of search criteria in format rowName=rowValue<br>
     *  e.g. columns("Total=100", "Count=10") <br>
     *  Each Column is map: rowName:cell
     */
    @JDIAction
    MapArray<String, MapArray<String, ICell>> columns(String... rowNameValues);
    /** Waits while value appear in Row <br>
     *  e.g. waitValue("100", row("Total")) <br>
     *  or   waitValue("100", row(5))
     */
    @JDIAction
    boolean waitValue(String value, Row row);
    /** Waits while value appear in Column <br>
     *  e.g. waitValue("Roman", column("Name")) <br>
     *  or   waitValue("Roman", column(3))
     */
    @JDIAction
    boolean waitValue(String value, Column column);
    /** Indicates are any rows in table. BaseChecker immediately */
    @JDIAction
    boolean isEmpty();
    /** Wait while at least one row appear in table */
    @JDIAction
    boolean waitHaveRows();
    /** Wait while at least count of rows appear in table */
    @JDIAction
    boolean waitRows(int count);
    /** Get first Cell with searched value in row by index (Int) or name(String)<br>
     *  e.g. cell("100", row("Total")) <br>
     *  or   cell("100", row(5))
     *  */
    @JDIAction
    ICell cell(String value, Row row);
    /** Get first Cell with searched value in column by index (Int) or name(String)<br>
     *  e.g. cell("Roman", column("Name")) <br>
     *  or   cell("Roman", column(3))
     *  */
    @JDIAction
    ICell cell(String value, Column column);
    /** Get all Cells with values matches to searched in Row by index (Int) or name(String) <br>
     *  e.g. cellsMatch(".*uccess.*", row("Result")) <br>
     *  or   cellsMatch(".*uccess.*", row(5))
     *  */
    @JDIAction
    List<ICell> cellsMatch(String regex, Row row);
    /** Get all Cells with values matches to searched in Column by index (Int) or name(String) <br>
     *  e.g. cellsMatch("Roma.*", column("Name")) <br>
     *  or   cellsMatch("Roma.*", column(3))
     *  */
    @JDIAction
    List<ICell> cellsMatch(String regex, Column column);
    /** Get Row cells for Cell with searched value in Column by index(Int) or name(String) <br>
     *  e.g. row("Roman", column("Name")) <br>
     *  or   row("Roman", column(3)) <br>
     *  Each Row is map: columnName:cell
     *  */
    @JDIAction
    MapArray<String, ICell> row(String value, Column column);
    /** Get Column cells for Cell with searched value in Row by index(Int) or name(String) <br>
     *  e.g. column("100", row("Total") <br>
     *  or   column("100", row(5)) <br>
     *  Each Column is map: rowName:cell
     *  */
    @JDIAction
    MapArray<String, ICell> column(String value, Row row);
    Rows rows();
    /** Get Row with index <br>
     *  Each Row is map: columnName:cell
     *  */
    @JDIAction
    MapArray<String, ICell> row(int rowNum);
    /** Get Row with name <br>
     *  Each Row is map: columnName:cell
     *  */
    @JDIAction
    MapArray<String, ICell> row(String rowName);
    Columns columns();
    /** Get Column with index <br>
     *  Each Column is map: rowName:cell
     *  */
    @JDIAction
    MapArray<String, ICell> column(int colNum);
    /** Get Column with name <br>
     *  Each Column is map: rowName:cell
     *  */
    @JDIAction
    MapArray<String, ICell> column(String colName);
    /** Get Header */
    @JDIAction
    List<WebElement> header();
    /** Get Header */
    @JDIAction
    String[] headers();
    /** Get Footer */
    @JDIAction
    String[] footer();
    /** Get All Cells */
    @JDIAction
    List<ICell> getCells();

    /** Clean all already founded Cells */
    @JDIAction
    void clean();
    /** Similar to clean */
    @JDIAction
    void clear();
}
