package com.bluecatcode.common.base;

import com.bluecatcode.common.contract.errors.ContractViolation;
import com.google.common.base.Function;
import com.google.common.base.Objects;

import javax.annotation.Nullable;

import static com.bluecatcode.common.contract.Preconditions.require;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

/**
 * Implementation of an {@link Either} containing a right reference.
 */
final class Right<L, R> extends Either<L, R> {

    private static final long serialVersionUID = 0L;

    private final R right;

    Right(R right) {
        this.right = checkNotNull(right, "Expected non-null right");
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isRight() {
        return true;
    }

    @Override
    public L left() {
        throw new ContractViolation("Left value is absent");
    }

    @Override
    public R right() {
        return this.right;
    }

    @Override
    public Either<L, R> or(Either<? extends L, ? extends R> secondChoice) {
        require(secondChoice != null, "Expected non-null secondChoice");
        return this;
    }

    @Override
    public <E extends Exception> R orThrow(Function<L, E> leftFunction) throws E {
        require(leftFunction != null, "Expected non-null leftFunction");
        return right;
    }

    @Override
    public <V> V either(Function<L, V> leftFunction, Function<R, V> rightFunction) {
        require(rightFunction != null, "Expected non-null rightFunction");
        return rightFunction.apply(right());
    }

    @Override
    public <A, B> Either<A, B> transform(Function<L, A> leftFunction, Function<R, B> rightFunction) {
        require(rightFunction != null, "Expected non-null rightFunction");
        //noinspection ConstantConditions
        return rightOf(rightFunction.apply(right()));
    }

    @Override
    public Either<R, L> swap() {
        return leftOf(right());
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Right<?, ?> right1 = (Right<?, ?>) other;
        return Objects.equal(right, right1.right);
    }

    @Override
    public int hashCode() {
        return 0xc4dcd0b4 + right.hashCode();
    }

    @Override
    public String toString() {
        return format("Right.of(%s)", right);
    }
}
