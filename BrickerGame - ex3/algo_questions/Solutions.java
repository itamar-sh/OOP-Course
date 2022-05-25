package algo_questions;

import java.util.Arrays;

public class Solutions {
    /**
     * How many ways to fill a trough with n liters with 1 or 2 liters added each time.
     * In other words: we get fibonacci
     * @param n liters in trough
     * @return num of ways to do this.
     */
    public static int bucketWalk(int n){
        if (n == 0 || n == 1){
            return 1;
        }
        int[] nums = new int[2];
        nums[0] = 1;
        nums[1] = 1;
        int i = 1;
        for (; i <= n; i++) {
            nums[i%2] = nums[0] + nums[1];
        }
        return nums[i%2];
    }

    /**
     * Method computing the maximal amount of tasks out of n tasks that can be completed with m time slots.
     * A task can only be completed in a time slot if the length of the time slot
     * is grater than the no. of hours needed to complete the task.
     * @param tasks array holds the time that takes to each task
     * @param timeSlots windows of time to work on those tasks
     * @return num of tasks the we can do.
     */
    public static int alotStudyTime(int[] tasks, int[] timeSlots){
        // we use greedy solution that simply try to make the shortest task each time while spending the minimal time.
        Arrays.sort(tasks);
        Arrays.sort(timeSlots);
        int i = 0;
        int j = 0;
        while(j < timeSlots.length && i < tasks.length){
            if (tasks[i]<=timeSlots[j]){
                i++;
            }
            j++;
        }
        return i;
    }

    /**
     * Method computing the nim amount of leaps a frog needs to jumb across n waterlily leaves, from leaf 1 to leaf n.
     * We use greedy solution that computes the most far leaf we can go with two steps everytime.
     * @param leapNum num of leaves
     * @return minimum amount of jumps to go to last leaf.
     */
    public static int minLeap(int[] leapNum){
        if (leapNum.length == 1){
            return 0;
        }
        int jumps = 0;
        int index = 0;
        while (index < leapNum.length-1){
            jumps++;
            // for every leaf we check all the options to jump from the leaf is points to
            int maxLeaf = leapNum[leapNum[index]] + leapNum[index] + index; // the most far leaf to jump from the current leaf
            int maxIndex = leapNum[index] + index;
            int j = 1;// must jump at least one leaf - we check the max distance we can get.
            for (; j < leapNum[index];j++){
                if (maxLeaf < leapNum[index+j] + index+j){
                    maxLeaf = leapNum[index+j] + index+j;
                    maxIndex = index+j;
                }
            }
            index = maxIndex;
        }
        return jumps;
    }

    /**
     * Method computing the solution to the following problem: Given an integer n,
     * return the number of structurally unique BST's which has exactly n nodes of unique values from 1 to n.
     * @param n num of nodes.
     * @return num of ways to make BST's with n nodes from 1 to n values.
     */
    public static int numTrees(int n){
        if (n == 1){ // only one tree with one node
            return 1;
        }
        if (n == 2){ // only one tree with node of 1 as root and 2 as leaf in the right branch.
            return 2;
        }
        int[] numOfSubTrees = new int[n+1];
        numOfSubTrees[0] = 1;
        // start from 1 because we already have the first option
        for (int i = 1; i < n+1; i++) {
            for (int j = 0; j < i; j++) {
                // this is the brain we calculate all the options with j nodes in left and the rest in the right.
                int leftBranch = numOfSubTrees[j];
                int numOfNodesRight = i - j - 1;
                int rightBranch = numOfSubTrees[numOfNodesRight];  // minus one because of the root of sub tree
                int totalOptions = leftBranch * rightBranch;
                numOfSubTrees[i] = totalOptions + numOfSubTrees[i];
            }
        }
        return numOfSubTrees[n];
    }

}
