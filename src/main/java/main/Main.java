package main;

public class Main{
    public static void main(String[] args){

        int gridSize = 9;
        int[] grid1 = {7, -1, 6, -1, -1, 4, -1, -1, 8};
        int[] grid2 = {-1, 1, 4, -1, -1, 5, -1, 6, -1};
        int[] grid3 = {5, 2, -1, 6, 9, -1, 7, -1, -1};
        int[] grid4 = {-1, 6, 1, -1, 4, 2, -1, -1, -1};
        int[] grid5 = {-1, -1, 7, -1, -1, -1, 1, -1, -1};
        int[] grid6 = {-1, -1, -1, 9, 1, -1, 4, 7, -1};
        int[] grid7 = {-1, -1, 2, -1, 8, 9, 6, 4, 3};
        int[] grid8 = {-1, 5, -1, 2, -1, -1, 8, 9, -1};
        int[] grid9 = {6, -1, -1, 4, -1, -1, 5, -1, 7};

        int [][] grid = {grid1, grid2, grid3, grid4, grid5, grid6, grid7, grid8, grid9};



        SudokuSolver sudokuSolver = new SudokuSolver(grid, gridSize);
        int [][] solvedGrid = sudokuSolver.solver();
        sudokuSolver.finalizedGridPrinter(solvedGrid);
    }
}
