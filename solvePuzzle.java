import java.util.Set;
import java.util.HashSet;


/*
 *  Backtracking algorithm for solving 4x4 Skyscrapers problem.
 *  Starting point: [0,0] in 4x4 int array filled with zeros
 *  Basic principle: increment current cell by 1, test if result is correct,
 *                   move forward if yes, move backward if not, repeat until
 *                   result is correct when standing in [3,3] 
 */
public class SkyScrapers {
  

  //Static global variables for easier array movement
  static int i, j;
  

  //Testing method - check if current result is correct
  static boolean testResult (int[][] result, int [] clues) {
  
    // Basic test - check if no two skyscrapers in a row or column have the same number of floors
    //              using sets
    Set <Integer> verticalTestSet = new HashSet();
    Set <Integer> horizontalTestSet = new HashSet();
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result.length; j++) {
        if (result[i][j] != 0)
          if (horizontalTestSet.add(result[i][j]) == false || result[i][j] > 4 || result[i][j] <= 0)
            return false;
        if (result[j][i] != 0)
          if (verticalTestSet.add(result[j][i]) == false || result[j][i] > 4 || result[j][i] <= 0)
            return false;
      }
      verticalTestSet.clear();
      horizontalTestSet.clear();
    }
    // Advanced test - after row i is full, check clues
    if (j == 3) {
      if (horizontalCluesTest(result, clues, i) == false)
        return false;
    }
    // Advanced test 2 - after column j is full, check clues
    if (i == 3) {
      if (verticalCluesTest(result, clues, j) == false)
        return false;
    }
    return true;
  }
  
  // Testing horizontal clues:
  // Move from left/right of given row, count how many times max value will change,
  // and compare it with clues array
  static boolean horizontalCluesTest (int [][] result, int [] clues, int row) {
    int maxLeft = 0, maxRight = 0, leftCounter = 0, rightCounter = 0;
    for (int j = 0; j<4; j++) {
      if (result[row][j] > maxLeft) {
        maxLeft = result[row][j];
        leftCounter++;
      }
      if (result[row][3-j] > maxRight) {
        maxRight = result[row][3-j];
        rightCounter++;
      }
    }
    if ((leftCounter == clues[15-row] || clues[15-row] == 0 ) && (rightCounter == clues[4+row] || clues[4+row] == 0))
      return true;
    else
      return false;
  }
  
  // Testing vertical clues:
  // Move from top/bottom of given column, count how many times max value will change,
  // and compare it with clue array
  static boolean verticalCluesTest (int [][] result, int [] clues, int col) {
    int maxTop = 0, maxBottom = 0, topCounter = 0, bottomCounter = 0;
    for (int i = 0; i<4; i++) {
      if (result[i][col] > maxTop) {
        maxTop = result[i][col];
        topCounter++;
      }
      if (result[3-i][col] > maxBottom) {
        maxBottom = result[3-i][col];
        bottomCounter++;
      }
    }
    if ((topCounter == clues[col] || clues[col] == 0) && (bottomCounter == clues[11-col] || clues[11-col] == 0))
      return true;
    else
      return false;
  }
  
  //moving forward in array - line by line
  static void next() {
    if (j == 3) {
      j = 0;
      i++;
    } else j++;
  }
  
  //moving backwards in array - line by line
  static void previous() {
    if (j == 0) {
      j = 3;
      i--;
    } else j--;
  }
    
  // starting point
  static int[][] solvePuzzle (int[] clues) {
    int[][] result = new int[4][4];
    i=0; j=0;
    boolean goAgain = true;
    
    // main movement loop
    while (goAgain) {
    
      // increasing value of cell
      result[i][j]++;
      
      // testing if result is correct
      // yes -> move to next cell
      // no -> do nothing (next loop will increment value of cell again)
      //       or change cell value to 0, and move backwards
      if (testResult(result, clues) == true)
        next();
      else {
        if (result[i][j] >=4) {
          result[i][j]=0;
          previous();
        }
      }
      
      // end condition
      if (i==4 && j==0) {    
        goAgain = false;
      }
    }
    return result;
  }
}
