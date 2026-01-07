package com.fitness.aiservice.consumers;

import com.fitness.aiservice.dtos.ActivityQueueResponse;
import com.fitness.aiservice.repository.RecommendationRepository;
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
    private final RecommendationRepository recommendationRepository;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processActivity(ActivityQueueResponse activity) {
        System.out.println("Received activity for process: " + activity.getId());
        var recommendation = activityAiService.generateRecommendation(activity);
        recommendationRepository.save(recommendation);
    }

}
