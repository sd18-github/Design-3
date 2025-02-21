// Time Complexity : O ( n )
// Space Complexity : O ( l ) where l is the level of nesting
// Did this code successfully run on Leetcode : Yes

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

// This is the interface that allows for creating nested lists.
// You should not implement it, or speculate about its implementation
interface NestedInteger {

    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    boolean isInteger();

    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    Integer getInteger();

    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return empty list if this NestedInteger holds a single integer
    List<NestedInteger> getList();
}


public class NestedIterator implements Iterator<Integer> {
    private final List<NestedInteger> nestedList;
    private NestedIterator nestedIterator; // Reusable nested iterator
    private int index;

    NestedIterator(List<NestedInteger> nestedList) {
        this.nestedList = nestedList;
        this.nestedIterator = null;
        this.index = 0;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        //if the current index has an integer, return it and increment idx
        if (nestedList.get(index).isInteger()) {
            return nestedList.get(index++).getInteger();
        }

        //else delegate to the nested nestedIterator
        return nestedIterator.next();
    }

    @Override
    public boolean hasNext() {
        while (index < nestedList.size()) {
            NestedInteger current = nestedList.get(index);

            if (current.isInteger()) {
                return true; // Found an integer, ready for `next()`
            }

            // Initialize nested iterator only if necessary
            if (nestedIterator == null) {
                nestedIterator = new NestedIterator(current.getList());
            }

            // If the nested iterator has elements, return true
            if (nestedIterator.hasNext()) {
                return true;
            }

            // If nested iterator is exhausted, move to the next element
            nestedIterator = null;
            index++;
        }
        return false;
    }
}