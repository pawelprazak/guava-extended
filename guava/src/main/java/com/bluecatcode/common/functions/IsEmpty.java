package com.bluecatcode.common.functions;

/**
 * Single Method Interface for objects with capability of being empty
 */
public interface IsEmpty {

    /**
     * Returns <tt>true</tt> if object is "empty", it can be any of:
     * <ul>
     * <li>object is null
     * <li>object's length or size is 0
     * <li>object is "empty", as in it doesn't contain any useful data
     * </ul>
     *
     * @return <tt>true</tt> if object is empty, otherwise <tt>false</tt>
     */
    boolean isEmpty();
}
