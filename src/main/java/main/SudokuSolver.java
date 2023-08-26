package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/*
 * I could try to overhaul this to be more efficient, I would create a pair class (int, boolean) to eliminate certain
 * values by checking if they are a valid placement at the start. This would allow me to insert some extra starting values
 * (usually, kinda based on the starting positions of the grid).
 */

public class SudokuSolver{

    private final int[][][] grid;
    private final int[][] initValChecker;
    private final int[][] finalGrid;
    private final int gridSize;
    private final Map<GridCoords, GridCoords> startingGridPos;


    //default constructor for a given grid, going to be given them in a specific format
    public SudokuSolver(int [][] passedGrid, int size){
        gridSize = size;
        grid = new int[gridSize][gridSize][gridSize];
        //initialize the checker to help with vert/horiz checks and the final grid
        initValChecker = passedGrid.clone();
        finalGrid = passedGrid;
        for(int row = 0; row < gridSize; row++){
            for(int col = 0; col < gridSize; col++){
                for(int i = 0; i < gridSize; i++){
                    if(i+1 == passedGrid[row][col])
                        grid[row][col][i] = passedGrid[row][col];
                }
            }
        }

        //initialize the grid mapper
        startingGridPos = new HashMap<>();
        int startingRow = 0;
        int startingCol = 0;
        for(int i = 0; i < gridSize; i++){
            startingRow = i%3 == 0 && i != 0 ? startingRow + 3 : startingRow;
            for(int y = 0; y < gridSize; y++){
                startingCol = y%3 == 0 && y != 0 ? startingCol + 3 : startingCol;
                GridCoords g = new GridCoords(i, y);
                GridCoords start = new GridCoords(startingRow, startingCol);
                startingGridPos.put(g, start);
            }
            startingCol = 0;
        }
    }


    //The actual solver
    public int[][] solver(){
        boolean solved = false;
        while(!solved){
            for(int r = 0; r < gridSize; r++){
                for(int c = 0; c < gridSize; c++){
                    for(int val = 0; val < gridSize; val++){
                        if(initValChecker[r][c] != -1 || finalGrid[r][c] != -1)
                            break;
                        else if(!(this.checkHorizontal(r, c, val+1, 0) && this.checkVertical(c, r, val+1, 0) && this.checkSqGrid(r, c, val+1, 0))){
                            grid[r][c][val] = 0;
                        }
                        else{
                            grid[r][c][val] = val+1;
                        }
                    }
                }
            }
            for(int r = 0; r < gridSize; r++){
                for(int c = 0; c < gridSize; c++){
                    this.valChecker(r, c);
                }
            }
            solved = this.isSolved();
        }
        return finalGrid;
    }


    //Checking whether there is a value that can only be used in a specific tile.
    private void valChecker(int rowIdx, int colIdx){
        boolean check;
        if(initValChecker[rowIdx][colIdx] != -1)
            return;
        for(int val : grid[rowIdx][colIdx]){
            if(val != 0){
                check = this.checkHorizontal(rowIdx, colIdx, val, 1) || this.checkVertical(colIdx, rowIdx, val, 1) || this.checkSqGrid(rowIdx, colIdx, val, 1) || checkSmallSquare(rowIdx, colIdx, val);
                if(check){
                    finalGrid[rowIdx][colIdx] = val;
                    for(int i = 0; i < gridSize; i++){
                        if(i + 1 != val)
                            grid[rowIdx][colIdx][i] = 0;
                    }
                    break;
                }
            }
        }
    }


    //Checking function to see if there is only one choice in the selected square
    private boolean checkSmallSquare(int rowIdx, int colIdx, int val){
        int count = 1;
        for(int valCheck : grid[rowIdx][colIdx]){
            if(valCheck != val && valCheck != 0)
                count += 1;
        }
        return count == 1;
    }



    //Checking function for a column, implemented two modes.
    //The first is just checking if a number is valid against the column.
    //The second is checking if a number is the only valid choice for the column.
    private boolean checkVertical(int colIdx, int rowIdx, int val, int mode){
        for(int row = 0; row < gridSize; row++){
            if(mode == 0){
                if(finalGrid[row][colIdx] == val &&  row != rowIdx)
                    return false;
            }
            else{
                for(int value : grid[row][colIdx]){
                    if(row == rowIdx || (initValChecker[row][colIdx] != -1 && initValChecker[row][colIdx] != val))
                        break;
                    else if(initValChecker[row][colIdx] == val || value == val)
                        return false;
                }
            }
        }
        return true;
    }

    //Checking function for a row, implemented two modes.
    //The first is just checking if a number is valid against the row.
    //The second is checking if a number is the only valid choice for the row.
    private boolean checkHorizontal(int rowIdx, int colIdx, int val, int mode){
        for(int col = 0; col < gridSize; col++){
            if(mode == 0){
                if(finalGrid[rowIdx][col] == val && col != colIdx)
                    return false;
            }
            else{
                for(int value : grid[rowIdx][col]){
                    if(col == colIdx || (initValChecker[rowIdx][col] != -1 && initValChecker[rowIdx][col] != val))
                        break;
                    else if(value == val || initValChecker[rowIdx][col] == val)
                        return false;
                }
            }
        }
        return true;
    }

    //Checking function for a square grid, implemented two modes.
    //The first is just checking if a number is valid against the grid.
    //The second is checking if a number is the only valid choice for the grid.
    private boolean checkSqGrid(int rowIdx, int colIdx, int val, int mode){
        GridCoords curPos = new GridCoords(rowIdx, colIdx);
        GridCoords gridPos = startingGridPos.get(curPos);
        for(int row = gridPos.rowVal; row < gridPos.rowVal+3; row++){
            for(int col = gridPos.colVal; col < gridPos.colVal+3; col++){
                if(mode == 0){
                    if(finalGrid[row][col] == val && row != rowIdx && col != colIdx)
                        return false;
                }
                else{
                    for(int value : grid[row][col]){
                        if((initValChecker[row][col] != -1 && initValChecker[row][col] != val) || (row == rowIdx && col == colIdx))
                            break;
                        else if(initValChecker[row][col] == val || value == val)
                            return false;
                    }
                }
            }
        }
        return true;
    }




    //function to check if the grid has been solved.
    private boolean isSolved(){
        for(int row = 0; row < gridSize; row++){
            for(int col = 0; col < gridSize; col++){
                if(finalGrid[row][col] == -1)
                    return false;
            }
        }
        return true;
    }


    //Debugging function to check the number of -1's in finalGrid
    private int negChecker(){
        int retVal = 0;
        for(int r = 0; r < gridSize; r++){
            for(int c = 0; c < gridSize; c++){
                if(finalGrid[r][c] == -1)
                    retVal += 1;
            }
        }
        return retVal;
    }


    //util function to print the sanitized grid.
    public void gridPrinter(){
        for(int row = 0; row < gridSize; row++){
            for(int col = 0; col < gridSize; col++){
                System.out.println(row + ", " + col + ": " + Arrays.toString(grid[row][col]));
            }
        }
    }

    //util function to print the finalized grid.
    public void finalizedGridPrinter(int[][] finalGrid){
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------\n");
        for(int row = 0; row < gridSize; row++){
            for(int col = 0; col < gridSize; col++){
                sb.append('|');
                sb.append(finalGrid[row][col]);

                if(col == 8)
                    sb.append("|\n");
            }
        }
        sb.append("-------------------");
        System.out.println(sb);
    }
}
