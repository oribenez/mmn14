public class Ex14 {
    /**
     * function that is calculating the max drop between two numbers within the array.
     * Runtime Complexity:
     * we iterate over the array, starting from the end.
     * In each iteration, we calculate the current drop, and update the maximum drop and minimum element if necessary – ϴ(1) operations for each iteration.
     * Since we iterate over n – 1 elements, we get (n – 1) * ϴ(1) = ϴ(n) for the loop,
     * plus a constant amount of operations outside the loop. Thus, in total ϴ(n) runtime.
     * Space Complexity:
     * The algorithm makes use of a 'n' amount of variables, Thus, we get ϴ(n) space.
     * @param a Integer array which will be checked
     */
    public static int maximalDrop(int[] a) {
        // holds the maximum drop up to this point
        int maxDrop = 0;
        int minVal = a[a.length-1]; // last value of the array is the minimum value

        // start running from end of array to beginning of array, starting at second to last element
        for (int i = a.length-2; i >= 0; i--) {
            int currDrop = a[i] - minVal;

            if (currDrop > maxDrop) maxDrop = currDrop;
            if (a[i] < minVal) minVal = a[i];
        }

        return maxDrop;
    }

    /**
     * function that checks if the given 2D array (size = n*n) has a sink in it,
     * means that it has a row which all of it's values are 0 and a column at the same index of the row which has values that are 1.
     * Runtime Complexity:
     * We iterate through the array rows and cols bounds (max iterations 2n)
     * also iterate through the array row length and perform O(1) operations for each iteration.
     * since we iterate over max 2n elements, plus a constant amount of operations we perform outside the loop,
     * we get ϴ(1) + ϴ(n) + (2n * ϴ(1)) = ϴ(n).
     * Thus, in total ϴ(n) runtime.
     * Space Complexity:
     * The algorithm makes use of a 'n' amount of variables, Thus, we get ϴ(n) space.
     *
     */
    public static int isSink (int [][] mat) {
        int flag = -1;
        // iterate through the main diagonal
        int row = 0;
        int col = 0;

        // iterate through the elements of the array
        while (row < mat.length && col < mat.length) {
            int currNum = mat[row][col];
            if (currNum == 0) col++;
            else row++;
        }
        if (row > mat.length) return -1; // didn't find any suspicious sink

        int sumCol = 0;
        int sumRow = 0;

        // checks if the suspicious sink is valid
        for (int i = 0; i < mat.length; i++) {
            sumCol += mat[i][row];
            sumRow += mat[row][i];
        }

        if (sumCol == mat.length-1 && sumRow == 0) return row;

        return -1; // the suspicious sink is invalid
    }

    /**
     * Wrapper function which manage the get Stain size without destroy the original array
     * method which checks what is the size of the stain that is located within the given coordinates.
     * @return size of the stain that is located within the given coordinates
     * @param mat array to search for stains
     * @param x the row index of the array
     * @param y the column index of the array
     * */
    public static int size(boolean[][] mat, int x, int y) {
        boolean[][] tmp = clone(mat); //clone array
        return getStain(tmp,x,y);
    }

    /*
     * function which checks what is the size of the stain that is located within the given coordinates
     * function has a side effect which is changing the given array and not returning it back to it's original values.
     * Recommendation: send a clone of the array so the original array won't be destroyed.
     */
    private static int getStain(boolean[][] mat, int x, int y) {
        if (x < 0 || y < 0 || x >= mat[0].length || y >= mat.length) return 0; // if x or y values are illegal
        if (mat[x][y]) {
            mat[x][y] = false;
            //checks every combination of the stain to be placed
            return getStain(mat, x-1, y) + getStain(mat, x-1, y-1) + getStain(mat, x, y-1) + getStain(mat, x+1, y-1) + getStain(mat, x+1, y) + getStain(mat, x+1, y+1) + getStain(mat, x, y+1) + getStain(mat, x-1, y+1) + 1;
        } else
            return 0;
    }

    /**
     * function which gets an array and return a clone of the given array.
     * @param mat array to clone
     * @return clone of the given array
     * */
    public static boolean[][] clone(boolean[][] mat) {
        return clone(mat,0,0);
    }
    /*
     * function which gets an array and return a clone of the given array within the the given area.
     * needed to be implemented using a wrapper method.
     */
    private static boolean[][] clone(boolean[][] mat, int i, int j) {
        if (i == mat.length) return new boolean[mat.length][mat[0].length]; // end of array
        boolean[][] tmp;
        if (j == mat[0].length-1) {
            tmp = clone(mat, i + 1, 0); //end of line
        } else {
            tmp = clone(mat, i, j + 1); // increment
        }
        tmp[i][j] = mat[i][j];
        return tmp;
    }

    public static void print(boolean[][] mat, int i, int j) {
        if (i == mat.length)
            return;

        if (j == mat[0].length) {
            System.out.println();
            print(mat, i + 1, 0);
        } else {
            System.out.print(mat[i][j] + " ");
            print(mat,i,j+1);
        }

    }
//    private static boolean[][] clone(boolean[][] mat) {
//        return clone(mat, 0,0);
//    }

    /**
     * A function which gets two arrays and checks if both arrays has the same values, they can be organized in a different order.
     * @return true if each array has the same values as the other array.
     * @param a first array to compare
     * @param b second array to compare
     *  */
    public static boolean isPermutation (int [] a, int [] b) {
        if (a.length != b.length) return false; // checks for the same length
        //sort arrays
        selectionSort(a,0);
        selectionSort(b,0);

        return equals(a,b,0);
    }

    //function which returns true if arrays values are equal and in the same order and size, otherwise false.
    private static boolean equals(int [] a, int [] b, int i) {
        if (i == a.length) return true;
        if (a[i] != b[i]) return false; // elements unequal

        return equals(a,b,i+1);
    }

    /** function which returns the index of the minimum value within the array
     * @param arr the array to search for min value
     * @param i the starting index to search for the min value.
     * @return index of the minimum value within the array
     * */
    private static int minNum(int[] arr, int i) {
        if (i == arr.length-1) return i;
        int currMinInd = minNum(arr, i+1);

        return arr[currMinInd] < arr[i] ? currMinInd : i; // checks which value is smaller
    }

    /** function which is reordering the array in an increasing order
     * @param arr the array to reorder
     * @param i the starting index to start ordering
     * */
    private static void selectionSort(int[] arr, int i) {
        if (i == arr.length-1) return;
        int minIndex = minNum(arr, i); //get min number in new array
        if (minIndex != i) swap(arr, minIndex, i); // swap

        selectionSort(arr, i+1);
    }

    /**
     * function that is swapping between two elements in the array
     * @param arr the array to switch elements
     * @param index1 the index of the first element
     * @param index2 the index of the second element
     * */
    private static void swap(int[] arr, int index1, int index2) {
        //swap
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }
}
