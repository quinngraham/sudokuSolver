package main;

public class Main{
    public static void main(String[] args){

        int gridSize = 9;
        int[] grid1 = {3, 7, -1, -1, -1, -1, -1, -1, -1};
        int[] grid2 = {2, -1, -1, -1, 1, 9, 5, 3, -1};
        int[] grid3 = {-1, -1, -1, 2, -1, -1, -1, 1, -1};
        int[] grid4 = {-1, -1, 9, -1, -1, -1, -1, 8, -1};
        int[] grid5 = {-1, 8, -1, -1, -1, -1, -1, 5, -1};
        int[] grid6 = {-1, 3, -1, -1, -1, -1, 2, -1, -1};
        int[] grid7 = {-1, 4, -1, -1, -1, 3, -1, -1, -1};
        int[] grid8 = {-1, 9, 5, 1, 6, -1, -1, -1, 8};
        int[] grid9 = {-1, -1, -1, -1, -1, -1, -1, 2, 7};

        int [][] grid = {grid1, grid2, grid3, grid4, grid5, grid6, grid7, grid8, grid9};



        SudokuSolver sudokuSolver = new SudokuSolver(grid, gridSize);
        int [][] solvedGrid = sudokuSolver.solver();
        sudokuSolver.finalizedGridPrinter(solvedGrid);
    }
}
