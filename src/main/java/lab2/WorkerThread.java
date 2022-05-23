package lab2;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class WorkerThread extends Thread {

    private Queue<WorkItem> queue = new LinkedBlockingQueue();


    @Override
    public void run() {
        while (true) {
            WorkItem item = queue.poll();

            if (item == null) {
                return;
            }

            item.getFutureResult().setResult(item.getFunction().apply(item.getArgument()));
        }
    }

    public Queue<WorkItem> getQueue() {
        return queue;
    }

    public void setQueue(Queue<WorkItem> queue) {
        this.queue = queue;
    }
}
