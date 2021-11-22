package cn.tucci.sso.server.core.task;

import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author tucci.lee
 */
@Component
public class AsyncTaskExecutor {

    private final SimpleAsyncTaskExecutor simpleAsyncTaskExecutor;

    public AsyncTaskExecutor(SimpleAsyncTaskExecutor simpleAsyncTaskExecutor) {
        this.simpleAsyncTaskExecutor = simpleAsyncTaskExecutor;
    }

    public void execute(Runnable task){
        simpleAsyncTaskExecutor.execute(task);
    }
}
