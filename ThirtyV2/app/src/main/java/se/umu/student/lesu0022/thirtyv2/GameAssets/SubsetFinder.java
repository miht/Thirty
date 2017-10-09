package se.umu.student.lesu0022.thirtyv2.GameAssets;

import java.util.*;

/**
 *  Extension of CareerCup.com user TechTycoon's implementation of the subset sum problem.
 *  What this class does is find every possible subset, no repetition of elements allowed,
 *  within a set of integers that add up to a given number.
 *
 *  My modifications involve a simple recursive extension that allows for the algorithm to continue
 *  searching for further subsets should a subset have been found. The code then returns these
 *  subsets in the form of an ArrayList of integers of depth three.
 */

public class SubsetFinder {

    private int sum_final;
    private int sum;
    private LinkedList<Integer> subset;
    private HashSet<String> subsets;
    private Integer[] numbers={1,1,1,2,4,5};

    /**
     *
     * @param numbers, the set of integers that correspond to the set in which we are looking for subsets
     * @param sum_final
     */
    public SubsetFinder(Integer[] numbers,int sum_final) {
        this.numbers=numbers;
        this.sum_final=sum_final;
        sum=0;
        subset = new LinkedList<Integer>();
        subsets = new HashSet<String>();

    }

    public HashSet<String> getResults() {
        return subsets;
    }

    /**For each unique subset that add up to the given sum we also store the remaining possible subsets out of a total
     * of 6 die. For instance, given a die combination of 1, 2, 3, 4, 5, 6 and a sum of 4 this function would return
     * an arraylist containing the following arraylists; [0] = {1, 2}, {3}
     *
     * This function takes every subset string and converts them into the corresponding arraylist of depth three of integers.
     */
    public ArrayList<ArrayList<ArrayList<Integer>>> getCombos() {
        ArrayList<ArrayList<ArrayList<Integer>>> res = new ArrayList<>();

        for(String s : subsets) {
            ArrayList<ArrayList<Integer>> comboOuter = new ArrayList<>();

            for(String s2 : s.replace("[", "").trim().split("]")) {

                if(s2 != "") {
                    ArrayList<Integer> comboInner = new ArrayList<>();
                    for(String s3 : s2.trim().replace(",", "").split(" ")) {
                        comboInner.add(Integer.parseInt(s3.trim()));
                    }
                    comboOuter.add(comboInner);
                }
            }

            res.add(comboOuter);
        }

        return res;
    }

    /**
     * Examine the array of numbers from the index of startPoint in order to check for subsets summing up to the
     * desired sum
     * @param startPoint the current index in the array of numbers that we are evaluating for subsets
     */
    public void findSubset(int startPoint)
    {
        if(sum==sum_final)
        {
            LinkedList<Integer> numbers_linkedList = new LinkedList<>();
            for(int i : numbers) {
                numbers_linkedList.add(i);
            }

            LinkedList<Integer> filtered_numbers = difference(numbers_linkedList, subset);
            Integer[] filteredNumbers_array = Arrays.copyOf(filtered_numbers.toArray(), filtered_numbers.size(), Integer[].class);

            //Recursively check for further subsets
            SubsetFinder k_new =new SubsetFinder(filteredNumbers_array, sum_final);
            k_new.findSubset(0);

            HashSet<String> ll = k_new.getResults();
            if(ll.size() > 0) {
                for(String s : ll) {
                    subsets.add("" + subset + "" + s + "\n");
                }
            }
            else {
                subsets.add("" + subset);
            }
        }
        else {
            //No subsets found.
            for(int i=startPoint;i<numbers.length;i++) {
                sum = sum + numbers[i];
                if (sum > sum_final) {
                    sum = sum - numbers[i];
                    break;
                }
                subset.add((int) numbers[i]);
                findSubset(i + 1);
                sum = sum - numbers[i];
                subset.removeLast();
            }
        }
    }

    /*
    Calculates and returns the boolean difference between a list l1 and list l2
     */
    public LinkedList<Integer> difference(LinkedList<Integer> l1, LinkedList<Integer> l2) {
        for(Integer i : l2) {
            l1.remove(i);
        }
        return l1;
    }

    /*
    Calculates and returns the boolean union between a list l1 and list l2
     */
    public LinkedList<Integer> union(LinkedList<Integer> l1, LinkedList<Integer> l2) {
        for(Integer i : l2) {
            l1.add(i);
        }
        return l1;
    }
}
