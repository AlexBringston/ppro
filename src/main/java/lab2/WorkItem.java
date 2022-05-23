package lab2;

import java.util.function.Function;

public class WorkItem<T, R> {
    private Function function;
    private T argument;

    private FutureResult<R> futureResult;

    public WorkItem(Function function, T argument) {
        this.function = function;
        this.argument = argument;
        this.futureResult = new FutureResult<>();
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public T getArgument() {
        return argument;
    }

    public void setArgument(T argument) {
        this.argument = argument;
    }

    public FutureResult<R> getFutureResult() {
        return futureResult;
    }

    public void setFutureResult(FutureResult<R> futureResult) {
        this.futureResult = futureResult;
    }
}
