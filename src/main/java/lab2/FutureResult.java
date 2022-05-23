package lab2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FutureResult<T> {

    private boolean hasResult;
    private T result;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    public void setResult(T result) {
        lock.lock();
        try {
            this.hasResult = true;
            this.result = result;
            condition.signal();
        }
        finally {
            lock.unlock();
        }
    }

    public T getResult() throws InterruptedException {
        lock.lock();
        try {
            while (!hasResult) {
                condition.await();
            }
        }
        finally {
            lock.unlock();
        }
        return result;
    }

    public boolean hasResult() {
        return hasResult;
    }

    public void setHasResult(boolean hasResult) {
        this.hasResult = hasResult;
    }
}
