public class Ex14 {
    public static void main(String[] args) {
        int[] a = {1,3,5,4,8,2,4,3,6,5};
        int[] b = {3,3,2,3,3,3,2,2,2,3};
        int[] c = {3};
        int[] d = {4};
        int[][] s = {
            {1,3,8,9},
            {6,4,15,11},
                {36,50,21,22},
                {60,55,30,26}
        };
      /*  System.out.println(search(s,1)); //false
        System.out.println(search(s,3)); //0,0
        System.out.println(search(s,7));//false
        System.out.println(search(s,9));//2,0

        System.out.println(search(s,6));//3,3
        System.out.println(search(s,4)); //false
        System.out.println(search(s,15)); //0,0
        System.out.println(search(s,11));//false

        System.out.println(search(s,36));//2,0
        System.out.println(search(s,50));//3,3
        System.out.println(search(s,21)); //false
        System.out.println(search(s,22)); //0,0
        System.out.println(search(s,60));//false

        System.out.println(search(s,55));//2,0
        System.out.println(search(s,30));//3,3
        System.out.println(search(s,26));//3,3
*/

        //System.out.println(search(s,0));//3,3
       /* System.out.println(search(s,2));//3,3
        System.out.println(search(s,5));//3,3
        System.out.println(search(s,29));//3,3
        System.out.println(search(s,27));//3,3
        System.out.println(search(s,61));//3,3
        System.out.println(search(s,20));//3,3
        System.out.println(search(s,16));//3,3
        System.out.println(search(s,35));//3,3
        System.out.println(search(s,7));//3,3*/
    }

    /**
     * For every number that matches either one(or both) of the parameter numbers we will update its latest index
     * and as long as the other param was found we will measure the (absolute) distance between their indexes
     *
     * This process will guarantee that all possible candidates are checked while not checking numbers which are irrelevant
     * thus guaranteeing O(n) efficiency (single iteration over n length array with a constant amount of actions for each index)
     *
     * @param a array to search
     * @param x firstNumber
     * @param y secondNumber
     * @return the minimum diff between the indexes of the first and seconds number
     */
    public static int findMinDiff (int[] a, int x, int y){

        final int DEFAULT_VALUE = -1; // Default value with no meaning to symbol undefined

        int latestXIndex = DEFAULT_VALUE,latestYIndex = DEFAULT_VALUE;
        int minDiff = Integer.MAX_VALUE; // minimum diff computed which will be returned, Init to max int as it is the default value
        int currentDiff; //Temporary variable used to compare against min diff when an index updates

        for (int i = 0;  i< a.length ; i++) {
            //value of x found! update latestXIndex index
            if(a[i]==x){
                latestXIndex = i;
            }
            //value of y found! update latestYIndex index
            if(a[i]==y){
                latestYIndex = i;
            }
            //Make sure both latestIndexes are init(with meaningful values) before measuring distance between their indexes
            if(latestXIndex !=DEFAULT_VALUE && latestYIndex != DEFAULT_VALUE){
                //Calculate absolute distance
                currentDiff = Math.abs(latestXIndex - latestYIndex);
                //If the distance is shorter than previous distances found update minDiff
                if(currentDiff < minDiff){
                    minDiff = currentDiff;
                }
            }
        }
        return minDiff;
    }

    /**
     * This will search the array for a specific number
     *
     * As documented inside the function, this process will use a similar method to the classic binary search,
     * with the main difference being that each iteration will split the array twice(instead of one split per iteration)
     * This will result in a O(log2(N)) efficiency similarly to binary search
     * Note: splitting the array which does not change the efficiency relative to binary search as we twice
     *       the amount of actions per iteration(2 splits instead of 1) but for half the iterations
     *
     * @param mat square, nxn that is "cycle sorted"
     * @param num number to find in array
     * @return true if number exists
     */
    public static boolean search (int [][] mat, int num){

        //Will define the current subsquare we are searching
        int maxHeight=mat.length,maxWidth=mat[0].length;
        int minHeight=0,minWidth=0;

        //These will hold the coordinates for the biggest number in the top-middle row
        int row,col;

        //These variables will split the array into 4 even squares as they will hold the biggest value of thier respective square
        int bottomTop,leftRightSmaller,leftRightBigger;
        // When this value is true the current iteration will check the final numbers and thus the loop will break next.
        boolean lastIter = false;


        while(!lastIter ){

            //This will detect when we are checking a single square and toggles lastIter so that the loop breaks if the number is not found
            if((maxHeight-minHeight) <=1 ){
                lastIter = true;
            }
            //This expression will calculate the index for the biggest number in the top-middle row
            //Note that this will always be the top right value within the middle 4 cells
            row = (maxHeight-minHeight)/2 + minHeight -1;
            col = (maxWidth-minWidth)/2 + minWidth;

            /*
                If num is smaller than the smallest number minHeight will remain zero and max height will also become
                equal to 1 in the final loop before terminating, this will result in row=-1 so before the loop terminates
                we have to make sure row is non-negative for the final check
            */
            if(row < 0){
                row = 0;
            }

            //This number represents the biggest number in the top half of the array
            bottomTop= mat[row][col];

            //This number represents the biggest number in the top left side of the board
            leftRightBigger= mat[row][minWidth];

            //This number represents the biggest number in the bottom right side of the board
            leftRightSmaller= mat[maxHeight - 1][col];

            /*
                If any of these numbers match num, end
             */
            if(bottomTop==num){
                System.out.println(row+","+col);
                return true;
            }
            if(leftRightBigger ==num){
                System.out.println(row+","+minWidth);
                return true;
            }

            if(leftRightSmaller ==num){
                System.out.println(maxHeight - 1+","+col);
                return true;
            }

            /*
                Using the biggest numbers from 3 squares we:
                1) Use bottomTop to check if num is at the top or bottom side of the array
                   knowing that if bottomTop is greater/smaller than num surly no number after/before(in the sequence) bottomTop is smaller/greater
                   because the array is sorted, so we can discard the bottom/top part using maxHeight/minHeight
                2) From then we use leftRightBigger,leftRightSmaller to determine which square within the bottom/top part does
                   num belong to, using a similar method to bottomTop
                3) We repeat the process, in each iteration we discard the 1 subSquare out of the 4 available thus eliminating
                   3/4 of the cells without checking them
                4) When the algorithm has narrowed it down to a single cell, lastIter will trigger, thus if num isn't found
                   in the following iteration it doesn't exist in the array and thus the loop terminates.
             */
            if(bottomTop > num){
                //Anything after this will also be too big discard the next half of squares
                //One of the top squares
                maxHeight = maxHeight/2;
                if(leftRightBigger > num){
                    //Top left square has been selected
                    maxWidth = maxWidth/2;
                }else{
                    //Top Right square has been selected
                    minWidth = (maxWidth - minWidth)/2 + minWidth;

                }
            }else{
                //Anything before this will also be too small discard the previous half of squares
                //One of the bottom squares
                minHeight = (maxHeight-minHeight)/2 + minHeight;
                if(leftRightSmaller>num){
                    //Bottom Right has been selected
                    minWidth = (maxWidth - minWidth)/2 + minWidth;
                }else{
                    //Bottom Left has been selected
                    maxWidth = (maxWidth)/2;
                }
            }
        }
        //The value was not found in the array, return false
        return false;
    }

    /*public static boolean equalSplit (int[] arr){
        return equalSplit(arr,1);
    }

    public static boolean equalSplit (int[] arr,int n){
        if(n>arr.length/2 || n<1){
            return false;
        }


        //Now we need to check if exists an n sized group
        return equalSplit(arr,n+1);
    }
    public static int equalSplit (int[] arr,int ,){
        //Now we need to check if exists an n sized group
        return  || equalSplit(arr,n+1);
    }*/

    /**
     * Returns whether the number is special number
     * @param n number in the sequence to check
     * @return true if the number is a special number
     */
    public static boolean isSpecial (int n){
        return isSpecial(n,2);
    }

    /**
     * This function determines if a number is a special number given a number in the sequence
     * and starting iteration jumps (number whose duplicates we delete)
     * @param n number in sequence
     * @param iter each number which is a multiple of this will be deleted
     * @return true if a special number with given iter
     */
    public static boolean isSpecial(int n, int iter){
        //If n is a multiple of iter it will be deleted and thus the result is false
        if(n%iter==0) return false;

        //If iter is greater than n , n will not be deleted in this iteration(and any following one), return true
        if(iter>n){
            return true;
        }
        /*
            We know that n/iter(floor) numbers have been deleted "behind" n, so n position has moved back
            by that much, we call the function again with n moved back the amount of items deleted, and iter increased.
        */
        return isSpecial(n - n/iter,iter+1);
    }

    /**
     * This function will determine whether an (even numbered cell)array can be split into 2 groups of equal size,
     * such as that the sum of the numbers in each group equals each other
     * @param arr the array to split
     * @return true if array is able to be split into 2 groups of equal sums and length
     */
    public static boolean equalSplit (int[] arr){
        if(arr.length%2!=0) return false;
        return equalSplit(arr,0,0,0,0,0);
    }

    /**
     * This function will determine whether an (even numbered cell)array can be split into 2 groups of equal size,
     * such as that the sum of the numbers in each group equals each other
     * @param arr the array to split
     * @param index index to check from(any indexes before this will be ignored)
     * @param sumA offset for group A
     * @param lenA initial length for group A
     * @param sumB offset for group B
     * @param lenB initial length for group B
     * @return true if array is able to split into 2 groups of equal sums(plus offset) and length(plus initial)
     */
    public static boolean equalSplit (int[] arr,int index,int sumA,int lenA,int sumB,int lenB){
        if(index > (arr.length -1)){
            /*
                Here the index is at its limit and we must return a definitive answer,
                We check if this specific track is valid
                1) The sum of both groups must match
                2) The length of both groups must match
             */
            return (sumA == sumB) &&
                        ( lenA == lenB );
        }
        //We get here if we are not done splitting the array(there are values left)

        /*
            At each non-final index we have 2 choices, as any number must belong to exactly one group,
            Either we add the number to SumA and increase its length, or we do the same for Sum B
            By definition we can't ignore any number, and we can't use it in both sums

            That leaves us with exactly 2 option which we'll both check in the return statment.
         */
        return      equalSplit(arr,index+1,sumA+arr[index],lenA+1,sumB,lenB)||
                        equalSplit(arr,index+1,sumA,lenA,sumB+arr[index],lenB+1);

    }
}
