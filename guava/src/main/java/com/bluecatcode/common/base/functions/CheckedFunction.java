package com.bluecatcode.common.base.functions;

import javax.annotation.Nullable;

/**
 * Determines an output value based on an input value.
 *
 * @since 1.0.5
 */
public interface CheckedFunction<F, T, E extends Exception> {

    /**
     * Returns the result of applying this function to {@code input}.
     *
     * @throws NullPointerException if {@code input} is null and this function does not accept null arguments
     * @throws E if unable to compute
     */
    @Nullable
    T apply(@Nullable F input) throws E;
}
