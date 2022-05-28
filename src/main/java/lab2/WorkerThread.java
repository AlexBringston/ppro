package lab2;

import java.util.concurrent.SynchronousQueue;

public class WorkerThread extends Thread {
    private SynchronousQueue<WorkItem> queue;

    public WorkerThread(SynchronousQueue<WorkItem> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            WorkItem item = null;
            try {
                item = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (item.getArgument() == null && item.getFunction() == null) {
                return;
            }

            item.getFutureResult().setResult(item.getFunction().apply(item.getArgument()));
        }
    }

}
