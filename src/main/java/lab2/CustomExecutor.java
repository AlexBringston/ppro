package lab2;

import java.util.concurrent.SynchronousQueue;
import java.util.function.Function;

public class CustomExecutor {

    private SynchronousQueue<WorkItem> queue;
    private WorkerThread[] workerThreads;

    public CustomExecutor(int maxWorkers) {
        if (maxWorkers <= 0) throw new IllegalArgumentException("You need to enter a number bigger than 0!");
        queue = new SynchronousQueue<>();
        workerThreads = new WorkerThread[maxWorkers];
        for (int i = 0; i < workerThreads.length; i++) {
            workerThreads[i] = new WorkerThread(queue);
            workerThreads[i].start();
        }
    }

    public FutureResult execute(Function function, int arg) {
        WorkItem<Integer, Integer> workItem = new WorkItem<>(function, arg);
        queue.add(workItem);
        return workItem.getFutureResult();
    }

    public FutureResult[] map(Function function, int[] args) throws InterruptedException {
        WorkItem<Integer, Integer>[] workItems = new WorkItem[args.length];
        FutureResult[] results = new FutureResult[args.length];

        for (int i = 0; i < args.length; i++) {
            workItems[i] = new WorkItem<>(function, args[i]);
            queue.put(workItems[i]);
        }
        for (int i = 0; i < args.length; i++) {
            results[i] = workItems[i].getFutureResult();
        }
        return results;
    }

    public void shutdown() throws InterruptedException {
        for (WorkerThread ignored : workerThreads) {
            queue.put(new WorkItem(null, null));
        }

        for (int i = 0; i < workerThreads.length; i++) {
            workerThreads[i].join();
        }
    }
}