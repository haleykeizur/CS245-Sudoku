import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;


class Sudoku
{

    //sets the size to 9
    private static final int SIZE = 9;

    //I created four random puzzles that can be chosen randomly if a user doesn't want to input their own
    private static int[][] matrix0 = {
        {5,3,0,0,7,0,0,0,0},
        {6,0,0,1,9,5,0,0,0},
        {0,9,8,0,0,0,0,6,0},
        {8,0,0,0,6,0,0,0,3},
        {4,0,0,8,0,3,0,0,1},
        {7,0,0,0,2,0,0,0,6},
        {0,6,0,0,0,0,2,8,0},
        {0,0,0,4,1,9,0,0,5},
        {0,0,0,0,8,0,0,7,9}
    };

    private static int[][] matrix1 = {
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,3,0,8,5},
        {0,0,1,0,2,0,0,0,0},
        {0,0,0,5,0,7,0,0,0},
        {0,0,4,0,0,0,1,0,0},
        {0,9,0,0,0,0,0,0,0},
        {5,0,0,0,0,0,0,7,3},
        {0,0,2,0,1,0,0,0,0},
        {0,0,0,0,4,0,0,0,9}
    };

    private static int[][] matrix2 = {
        {0,0,0,8,0,1,0,0,0},
        {0,0,0,0,0,0,4,3,0},
        {5,0,0,0,0,0,0,0,0},
        {0,0,0,0,7,0,8,0,0},
        {0,0,0,0,0,0,1,0,0},
        {0,2,0,0,3,0,0,0,0},
        {6,0,0,0,0,0,0,7,5},
        {0,0,3,4,0,0,0,0,0},
        {0,0,0,2,0,0,6,0,0}
    };

    private static int[][] matrix3 = {
        {6,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0}
    };

    //this method outputs the board, and is called at the end
    //it runs through each row and column and prints it out
    private static void printBoard(int [][] matrix)
    {
        for(int i=0;i<SIZE;i++)
        {
            for(int j=0;j<SIZE;j++)
            {
                System.out.print(matrix[i][j]+"\t");
            }
            System.out.println("");
        }
    }

    //This function checks to see if there are any empty spots
    //if there is an empty spot (if there's a 0), the function will change the value
   
    private static int[] emptyCheck(int row, int col, int [][] matrix)
    {
        int emptySpot = 0;
        for(int i=0;i<SIZE;i++)
        {
            for(int j=0;j<SIZE;j++)
            {
                //if the cell is unassigned and empty
                if(matrix[i][j] == 0)
                {
                    //this changed the values of row and col
                    row = i;
                    col = j;
                    //there is one or more empty cells
                    emptySpot = 1;
                    int[] a = {emptySpot, row, col};
                    return a;
                }
            }
        }
        int[] a = {emptySpot, -1, -1};
        return a;
    }

    //method to check if we can put a certain value in the spot or not
    private static boolean doesItWork(int n, int r, int c, int [][] matrix)
    {
        //checking row
        for(int i=0;i<SIZE;i++)
        {
            //if there is a cell with the same value
            if(matrix[r][i] == n)
                return false;
        }
        //checking column
        for(int i=0;i<SIZE;i++)
        {
            //there is a cell with the value equal to i
            if(matrix[i][c] == n)
                return false;
        }
        //this checks sub matrix
        int row_start = (r/3)*3;
        int col_start = (c/3)*3;
        for(int i=row_start;i<row_start+3;i++)
        {
            for(int j=col_start;j<col_start+3;j++)
            {
                if(matrix[i][j]==n)
                    return false;
            }
        }
        return true;
    }

    //method to solve sudoku using backtracking
    private static boolean fillBoard(int [][] matrix)
    {
        int row=0;
        int col=0;
        int[] a = emptyCheck(row, col, matrix);
        //if there are no empty spots, then the puzzle is solved
        //pass by reference because number_unassigned will change the values of row and col
        if(a[0] == 0)
            return true;
        //number between 1 and 9
        row = a[1];
        col = a[2];
        for(int i=1;i<=SIZE;i++)
        {
            //if we can assign i to the cell or not, then the cell is matrix[row][col]
            if(doesItWork(i, row, col, matrix))
            {
                matrix[row][col] = i;
                //backtracking
                if(fillBoard(matrix))
                    return true;
                //if we can't proceed with this solution, then it reassigns the spot

                matrix[row][col]=0;
            }
        }
        return false;
    }



    //main function that greats users, asks them questions and outputs the final puzzle
    public static void main(String[] args)
    {

        System.out.println("Welcome to Sudoku!");
     
        Scanner input = new Scanner(System.in);

        System.out.print("Would you like a random puzzle, or would you like to enter your own? ");
        System.out.print("Type 'random' for random and 'own' to enter your own: ");
        String answer = input.next();

        //optional section so users can ask for a random puzzle to be solved
        if (answer.toLowerCase().equals("random"))
        {
        
            Random rand = new Random();
            int matrix_random = rand.nextInt(4);
            //System.out.println(matrix_random);
            String num=String.valueOf(matrix_random);
            String matrixFinder = "matrix" + num;
            int [][] matrixFinal = new int[9][9];

            //the above chooses a random matrix, and the following sets that random puzzle to the matrix passed into the other methods
            if (matrixFinder.equals("matrix1"))
                {
                    for (int i=0; i<9; i++) 
                    {
                        matrixFinal[i] = matrix1[i]; 
                        for (int j = 0; j < 9; j++)
                        {
                            matrixFinal[j] = matrix1[j];
                        }

                    }
                }

            else if (matrixFinder.equals("matrix0"))
                {
                    for (int i=0; i<9; i++) 
                    {
                        matrixFinal[i] = matrix0[i]; 
                        for (int j = 0; j < 9; j++)
                        {
                            matrixFinal[j] = matrix0[j];
                        }

                    }
                }

            else if (matrixFinder.equals("matrix2"))
                {
                    for (int i=0; i<9; i++) 
                    {
                        matrixFinal[i] = matrix2[i]; 
                        for (int j = 0; j < 9; j++)
                        {
                            matrixFinal[j] = matrix2[j];
                        }

                    }
                }
            else if (matrixFinder.equals("matrix3"))
                {
                    for (int i=0; i<9; i++) 
                    {
                        matrixFinal[i] = matrix3[i]; 
                        for (int j = 0; j < 9; j++)
                        {
                            matrixFinal[j] = matrix3[j];
                        }

                    }
                }

            if (fillBoard(matrixFinal))
            {
                System.out.println("Here is your puzzle: ");
                printBoard(matrixFinal);
            }
            else
                System.out.println("Invalid, there is no solution. :( ");}

            //this is where users can enter in their own numbers/puzzle
        else if (answer.toLowerCase().equals("own"))
           {
            System.out.println("Please enter the numbers for your puzzle, one at a time. Enter 0 for a blank spot. ");
            int[][] matrixNew = new int[9][9];
               for (int i = 0; i < 9; i++)
                {
                    System.out.println("Row: " + (i+1));
                    for (int j = 0; j < 9; j++)
                    {
                        matrixNew[i][j] = input.nextInt();
                    }
                }

            if  (fillBoard(matrixNew))
            {
                System.out.println("Here is your puzzle: ");
                printBoard(matrixNew);
            }
            else
                System.out.println("Invalid, there is no solution. :( ");}
        else
            System.out.println("Sorry that wasn't a valid option.");




    }
}