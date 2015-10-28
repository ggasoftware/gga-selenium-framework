package com.epam.jditests.tests.complex.tableTests;

import com.epam.jditests.dataproviders.TableDP;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Row;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Check;
import org.testng.annotations.Test;

import java.util.List;

import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Column.column;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.complex.table.Row.row;

/**
 * Created by Natalia_Grebenshchikova on 10/5/2015.
 */
public class CellTests extends InitTableTests {

    @Test (dataProvider = "setCellValue", dataProviderClass = TableDP.class)
    public  void getCellValueByRowAndColumn(int columnIndex, int rowIndex, String expectedCellValue){
        String cellValue = support().cell(new Column(columnIndex), new Row(rowIndex)).getValue();
        Assert.areEquals(cellValue, expectedCellValue,
                String.format("In cell (%d, %d) expected %s, but was, %s",rowIndex, columnIndex, expectedCellValue, cellValue));
    }

    @Test
    public void getAllCellsWithValueEqualTo(){
        List<ICell> cells = support().cells("MSTest, NUnit, Epam");

        Assert.areEquals(cells.size(), 2, String.format("Number of found element expectde to 2, but was %d", cells.size()));

        Assert.areEquals(cells.get(0).columnNum(), 3, "Wrong position for element 1");
        Assert.areEquals(cells.get(0).rowNum(), 2, "Wrong position for element 1" + cells.get(0).rowNum());
        Assert.areEquals(cells.get(1).columnNum(), 3, "Wrong position for element 2");
        Assert.areEquals(cells.get(1).rowNum(), 3, "Wrong position for element 2");

    }
    @Test
    public void getAllCellsWithValueEqualToValueInUse(){
        List<ICell> cells = support().cells("Custom");

        Assert.areEquals(cells.size(), 1, String.format("Number of found element expectde to 1, but was %d", cells.size()));

        Assert.areEquals(cells.get(0).columnNum(), 2, "Wrong position for element");
        Assert.areEquals(cells.get(0).rowNum(), 6, "Wrong position for element");
    }
    @Test
    public void getAllCellsWithValueMatchTo(){
        List<ICell> cells = support().cellsMatch("[a-zA-Z, 0-4]*Custom[a-zA-Z, 0-4]*");

        Assert.areEquals(cells.size(), 6, String.format("Number of found element expected to 6, but was %d", cells.size()));

        for (int i=0; i<cells.size(); i++) {
            Assert.areEquals(cells.get(i).columnNum(), 2, "Wrong position for element 1");
            Assert.areEquals(cells.get(i).rowNum(), i + 1, "Wrong position for element 1");
        }
    }


    @Test(dataProvider = "setCellPosition", dataProviderClass = TableDP.class)
    public void getFirstCellWithValueEqualTo(String searchCellValue, int columnIndex, int rowIndex){
        ICell cell = support().cell(searchCellValue);

        Assert.areEquals(cell.columnNum(), columnIndex, "Wrong position first element`s position");
        Assert.areEquals(cell.rowNum(), rowIndex, "Wrong position first element`s position");
    }
    @Test
    public void getFirstCellWithValueMatchTo(){
        ICell cell = support().cellMatch("[a-zA-Z, 0-4]*Custom[a-zA-Z, 0-4]*");

        Assert.areEquals(cell.columnNum(), 2, "Wrong position first element`s position");
        Assert.areEquals(cell.rowNum(), 1, "Wrong position first element`s position");
    }

    @Test(dataProvider = "setValueAndColumn", dataProviderClass = TableDP.class)
    public <T> void getCellWithValueEqualToInColumnByName(String cellValue, T column, int columnIndex, int rowIndex){
        ICell cell;

        if (column.getClass().equals(String.class))
            cell = support().cell(cellValue, column((String) column));
        else
            cell = support().cell(cellValue, column((Integer) column));

        Assert.areEquals(cell.columnNum(), columnIndex,
                String.format("Wrong column number for element with %s value, expected %d, but was %d",cellValue, columnIndex, cell.columnNum()));
        Assert.areEquals(cell.rowNum(), rowIndex, String.format("Wrong row number for element with 'Custom' value, expected 6, but was %d", cell.rowNum()));
    }

    @Test
    public void getCellWithValueEqualToInRowByNumber(){
        ICell cell = support().cell("MSTest, NUnit, Epam", row(2));

        Assert.areEquals(cell.columnNum(), 3, String.format("Wrong column number for element with 'Custom' value, expected 3, but was %d", cell.columnNum()));
        Assert.areEquals(cell.rowNum(), 2, String.format("Wrong row number for element with 'Custom' value, expected 2, but was %d", cell.rowNum()));
    }

    @Test
    public void getCellWithValueMatchToInColumnByName(){

        List<ICell> cells = support().cellsMatch("[a-zA-Z, 0-9]*Custom[a-zA-Z, 0-9]*", column("Now"));
        Assert.areEquals(cells.size(), 6, String.format("Number of found element expected to 6, but was %d", cells.size()));

        for (int i=0; i<cells.size(); i++) {
            Assert.areEquals(cells.get(i).columnNum(), 2, "Wrong position for element 1");
            Assert.areEquals(cells.get(i).rowNum(), i + 1, "Wrong position for element 1");
        }
    }
    @Test
    public void getCellWithValueMatchInColumnByNumber(){
        List<ICell> cells = support().cellsMatch("[a-zA-Z, 0-9]*Custom[a-zA-Z, 0-9]*", column(2));

        Assert.areEquals(cells.size(), 6, String.format("Number of found element expected to 6, but was %d", cells.size()));

        for (int i=0; i<cells.size(); i++) {
            Assert.areEquals(cells.get(i).columnNum(), 2, "Wrong position for element 1");
            Assert.areEquals(cells.get(i).rowNum(), i + 1, "Wrong position for element 1");
        }
    }

    @Test
    public void getCellWithValueMatchInRowByNumber(){
        List<ICell> cells = support().cellsMatch("[a-zA-Z, 0-9\\/]*log[a-zA-Z, 0-9\\/]*", row(4));

        Assert.areEquals(cells.size(), 2, String.format("Number of found element expected to 2, but was %d", cells.size()));

        Assert.areEquals(cells.get(0).columnNum(), 2, String.format("Wrong column number for element 1 match to  '[a-zA-Z, ]*log[a-zA-Z, ]*' value, expected 2, but was %d", cells.get(0).columnNum()));
        Assert.areEquals(cells.get(0).rowNum(), 4, String.format("Wrong row number for element 1 with to '[a-zA-Z, ]*log[a-zA-Z, ]*' value, expected 4, but was %d", cells.get(0).rowNum()));
        new Check("Text in 2-nd column").areEquals(cells.get(1).columnNum(), 3);
        Assert.areEquals(cells.get(1).rowNum(), 4, String.format("Wrong row number for element 2 with to '[a-zA-Z, ]*log[a-zA-Z, ]*' value, expected 4, but was %d", cells.get(1).rowNum()));
    }

    @Test
    public void verifyGetTest(){
        ICell cell = support().cell(column(3), row(4));

        Assert.areEquals(cell.getText(), "Epam, XML/Json logging, Hyper logging",
                String.format("Expected test id 'Epam, XML/Json logging, Hyper logging', but was '%s'", cell.getText()));
    }

}
