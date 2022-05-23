package lab2;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

public class CustomExecutor {

    private int maxWorkers;

    private Queue<WorkItem> queue = new LinkedBlockingQueue();
    private WorkerThread[] workerThreads;

    public CustomExecutor(int maxWorkers) {
        this.maxWorkers = maxWorkers;
    }

    public FutureResult execute(Function function, int arg) {
        WorkItem<Integer, Integer> workItem = new WorkItem<>(function, arg);
        queue.add(workItem);
        workerThreads = new WorkerThread[]{new WorkerThread()};
        workerThreads[0].setQueue(queue);
        workerThreads[0].start();
        return workItem.getFutureResult();
    }

    public FutureResult[] map(Function function, int[] args) {
        WorkItem<Integer, Integer>[] workItems = new WorkItem[args.length];
        FutureResult[] results = new FutureResult[args.length];
        workerThreads = new WorkerThread[maxWorkers];

        for (int i = 0; i < args.length; i++) {
            workItems[i] = new WorkItem<>(function, args[i]);
            queue.add(workItems[i]);
        }
        for (int i = 0; i < workerThreads.length; i++) {
            workerThreads[i] = new WorkerThread();
            workerThreads[i].setQueue(queue);
        }
        for (WorkerThread workerThread : workerThreads) {
            workerThread.start();
        }
        for (int i = 0; i < args.length; i++) {
            results[i] = workItems[i].getFutureResult();
        }
        return results;
    }

    public void shutdown() throws InterruptedException {
        for (int i = 0; i < workerThreads.length; i++) {
            workerThreads[i].join();
        }
    }
}
