package com.bluecatcode.common.concurrent;

import com.bluecatcode.common.exceptions.WrappedException;
import com.bluecatcode.common.functions.Effect;
import com.google.common.base.Function;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;

import javax.annotation.CheckReturnValue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static com.bluecatcode.common.base.Eithers.either;
import static com.bluecatcode.common.exceptions.Exceptions.uncheckedException;
import static com.bluecatcode.common.exceptions.Exceptions.unwrapToUncheckedException;

@CheckReturnValue
public class Try {

    public static <T> T tryWith(Callable<T> callable) {
        return tryWith(callable, unwrapToUncheckedException());
    }

    public static <T> T tryWith(Callable<T> callable, Function<WrappedException, RuntimeException> exceptionFunction) {
        return either(callable).orThrow(exceptionFunction);
    }

    public static void tryWith(Effect effect) {
        try {
            effect.cause();
        } catch (Exception e) {
            throw uncheckedException().apply(e);
        }
    }

    public static <T> T tryWith(long timeoutDuration, TimeUnit timeoutUnit, Callable<T> callable) {
        try {
            TimeLimiter limiter = new SimpleTimeLimiter();
            //noinspection unchecked
            Callable<T> proxy = limiter.newProxy(callable, Callable.class, timeoutDuration, timeoutUnit);
            return proxy.call();
        } catch (Exception e) {
            throw uncheckedException().apply(e);
        }
    }

    public static void tryWith(long timeoutDuration, TimeUnit timeoutUnit, Effect effect) {
        try {
            TimeLimiter limiter = new SimpleTimeLimiter();
            //noinspection unchecked
            Effect proxy = limiter.newProxy(effect, Effect.class, timeoutDuration, timeoutUnit);
            proxy.cause();
        } catch (Exception e) {
            throw uncheckedException().apply(e);
        }
    }

    private Try() {
        throw new UnsupportedOperationException();
    }
}
