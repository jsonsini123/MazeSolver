/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam, Jake Sonsini
 * @version 04/3/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // Create a stack to hold all of the solution values
        Stack<MazeCell> sln = new Stack<>();
        // Start the stack with the ending cell
        sln.push(maze.getEndCell());
        // While the top of the stack does not equal the beginning continue loop
        while (!sln.peek().equals(maze.getStartCell())){
            // Add the parent of the current cell
            sln.push(sln.peek().getParent());

        }
        // Make an arraylist to hold final solution
        ArrayList<MazeCell> complete = new ArrayList<>();
        // Run a loop until complete is fully filled, this loop puts it in the correct order
        while (sln.size() > 0){
            complete.add(sln.pop());
        }
        return complete;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // Create the cells to visit stack and initialise current as the beginning cell
        Stack<MazeCell> ctv = new Stack<>();
        MazeCell current = maze.getStartCell();
        // While the current cell does not equal the end, aka go till the maze is finished
        while (!current.equals(maze.getEndCell())) {
            // Run if statements that check cells in the N, E, S, W direction to see if they are valid to move forward
            if (maze.isValidCell(current.getRow() - 1, current.getCol())) {
                // Once a valid cell is detected add it to the cells to visit
                ctv.push(maze.getCell(current.getRow() - 1, current.getCol()));
                // Make the shifted cells parent equal to the current cell
                maze.getCell(current.getRow() - 1, current.getCol()).setParent(current);
            }
            if (maze.isValidCell(current.getRow(), current.getCol() + 1)) {
                ctv.push(maze.getCell(current.getRow(), current.getCol() + 1));
                maze.getCell(current.getRow(), current.getCol() + 1).setParent(current);
            }
            if (maze.isValidCell(current.getRow() + 1, current.getCol())) {
                ctv.push(maze.getCell(current.getRow() + 1, current.getCol()));
                maze.getCell(current.getRow() + 1, current.getCol()).setParent(current);
            }
            if (maze.isValidCell(current.getRow(), current.getCol() - 1)) {
                ctv.push(maze.getCell(current.getRow(), current.getCol() - 1));
                maze.getCell(current.getRow(), current.getCol() - 1).setParent(current);
            }
            // After the if statements make sure current is explored
            current.setExplored(true);
            // Set current to the new cell
            current = ctv.pop();
        }
        // Call get solution to put the solved maze into an ArrayList
        return getSolution();
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // Create the cells to visit queue variable which changes the search pattern to BFS
        Queue<MazeCell> ctv = new LinkedList<>();
        // Sets current cell
        MazeCell current = maze.getStartCell();
        // Run loop until maze is solved which is when current is at the end cell
        while (!current.equals(maze.getEndCell())){
            // Explore using if statements in the N, E, S, W direction
            if (maze.isValidCell(current.getRow() - 1, current.getCol())){
                // Add the current cell that is shifted to cells to visit
                ctv.add(maze.getCell(current.getRow() - 1, current.getCol()));
                // Set the parent of the new cell to the current cell
                maze.getCell(current.getRow() - 1, current.getCol()).setParent(current);
            }
            if (maze.isValidCell(current.getRow(), current.getCol() + 1)){
                ctv.add(maze.getCell(current.getRow(), current.getCol() + 1));
                maze.getCell(current.getRow(), current.getCol() + 1).setParent(current);
            }
            if (maze.isValidCell(current.getRow() - 1, current.getCol())){
                ctv.add(maze.getCell(current.getRow() - 1, current.getCol()));
                maze.getCell(current.getRow() - 1, current.getCol()).setParent(current);
            }
            if (maze.isValidCell(current.getRow(), current.getCol() - 1)){
                ctv.add(maze.getCell(current.getRow(), current.getCol() - 1));
                maze.getCell(current.getRow(), current.getCol() - 1).setParent(current);
            }
            // Make the current cell explored
            current.setExplored(true);
            // Change the current cell to the shifted cell
            current = ctv.remove();
        }
        // Call get solution to put the solved maze into an ArrayList
        return getSolution();
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
