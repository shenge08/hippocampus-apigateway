package org.dreamlife.hippocampus.apigateway.performance.sink;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.dreamlife.hippocampus.apigateway.performance.service.PerformanceSummaryService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/3/30
 */
public class SinkPerformanceDataTimer implements InitializingBean {
    private final ScheduledExecutorService timer;

    public SinkPerformanceDataTimer() {
        timer = new ScheduledThreadPoolExecutor(1,
                new ThreadFactoryBuilder().setNameFormat("performance-timer-sink-%s").build()
                );
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        // 每分钟打印一次接口性能数据
        int initialDelayInSecond = 60 - now.getSecond();
        timer.scheduleAtFixedRate(() ->
                {
                    PerformanceSummaryService.getInstance().sink();
                }, initialDelayInSecond, 1, TimeUnit.MINUTES
        );
    }
}