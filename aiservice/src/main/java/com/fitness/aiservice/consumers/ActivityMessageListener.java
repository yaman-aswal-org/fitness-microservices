package com.fitness.aiservice.consumers;

import com.fitness.aiservice.dtos.ActivityQueueResponse;
import com.fitness.aiservice.services.ActivityAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAiService activityAiService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processActivity(ActivityQueueResponse activity) {
        System.out.println("Received activity for process: " + activity.getId());
        System.out.println("Generate for process: " + activityAiService.generateRecommendation(activity));
    }

}
