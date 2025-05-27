package com.nhnacademy.ink3batch.batch.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelloWorldJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job helloWorldJob;

    @Scheduled(fixedRate = 1000) // 5초마다 실행
    public void runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // 매번 다른 값으로 실행
                    .toJobParameters();
            jobLauncher.run(helloWorldJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
