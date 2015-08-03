package com.epam.jdi_ui_tests.elements.interfaces.complex;

import com.epam.jdi_ui_tests.elements.base.SelectElement;
import com.epam.jdi_ui_tests.elements.complex.table.*;
import com.epam.jdi_ui_tests.elements.interfaces.common.IText;
import com.epam.jdi_ui_tests.elements.page_objects.annotations.JDIAction;
import com.epam.jdi_ui_tests.utils.map.MapArray;

import java.util.List;

/**
 * Created by roman.i on 20.10.2014.
 */

public interface ITable<T extends SelectElement> extends IText {
    /** Get Cell by column/row index (Int) or name(String) */
    @JDIAction
    ICell<T> cell(Column column, Row row);
    /** Get all Cells with values equals to searched value */
    @JDIAction
    List<ICell<T>> cells(String value);
    /** Get all Cells with values matches to searched regex */
    @JDIAction
    List<ICell<T>> cellsMatch(String regex);
    /** Get first Cell with equals to searched value  */
    @JDIAction
    ICell<T> cell(String value);
    /** Get first Cell with matches to searched regex  */
    @JDIAction
    ICell<T> cellMatch(String regex);
    /** Searches Rows in table matches specified criteria colNameValues - list of search criteria in format columnName=columnValue<br>
     *  e.g. rows("Name=Roman", "Profession=QA") <br>
     *  Each Row is map: columnName:cell
     */
    @JDIAction
    MapArray<String, MapArray<String, ICell<T>>> rows(String... colNameValues);
    /** Searches Columns in table matches specified criteria rowNameValues - list of search criteria in format rowName=rowValue<br>
     *  e.g. columns("Total=100", "Count=10") <br>
     *  Each Column is map: rowName:cell
     */
    @JDIAction
    MapArray<String, MapArray<String, ICell<T>>> columns(String... rowNameValues);
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
    /** Indicates are any rows in table. Check immediately */
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
    ICell<T> cell(String value, Row row);
    /** Get first Cell with searched value in column by index (Int) or name(String)<br>
     *  e.g. cell("Roman", column("Name")) <br>
     *  or   cell("Roman", column(3))
     *  */
    @JDIAction
    ICell<T> cell(String value, Column column);
    /** Get all Cells with values matches to searched in Row by index (Int) or name(String) <br>
     *  e.g. cellsMatch(".*uccess.*", row("Result")) <br>
     *  or   cellsMatch(".*uccess.*", row(5))
     *  */
    @JDIAction
    List<ICell<T>> cellsMatch(String regex, Row row);
    /** Get all Cells with values matches to searched in Column by index (Int) or name(String) <br>
     *  e.g. cellsMatch("Roma.*", column("Name")) <br>
     *  or   cellsMatch("Roma.*", column(3))
     *  */
    @JDIAction
    List<ICell<T>> cellsMatch(String regex, Column column);
    /** Get Row cells for Cell with searched value in Column by index(Int) or name(String) <br>
     *  e.g. row("Roman", column("Name")) <br>
     *  or   row("Roman", column(3)) <br>
     *  Each Row is map: columnName:cell
     *  */
    @JDIAction
    MapArray<String, ICell<T>> row(String value, Column column);
    /** Get Column cells for Cell with searched value in Row by index(Int) or name(String) <br>
     *  e.g. column("100", row("Total") <br>
     *  or   column("100", row(5)) <br>
     *  Each Column is map: rowName:cell
     *  */
    @JDIAction
    MapArray<String, ICell<T>> column(String value, Row row);
    ITableLine rows();
    /** Get Row with index <br>
     *  Each Row is map: columnName:cell
     *  */
    @JDIAction
    MapArray<String, ICell<T>> row(int rowNum);
    /** Get Row with name <br>
     *  Each Row is map: columnName:cell
     *  */
    @JDIAction
    MapArray<String, ICell<T>> row(String rowName);
    ITableLine columns();
    /** Get Column with index <br>
     *  Each Column is map: rowName:cell
     *  */
    @JDIAction
    MapArray<String, ICell<T>> column(int colNum);
    /** Get Column with name <br>
     *  Each Column is map: rowName:cell
     *  */
    @JDIAction
    MapArray<String, ICell<T>> column(String colName);
    /** Get Footer */
    @JDIAction
    String[] footer();
    /** Get All Cells */
    @JDIAction
    List<ICell<T>> getCells();
}
