package com.ecommerce.ecommerce_events.config;

import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import com.ecommerce.ecommerce_events.processor.OrderItemProcessor;
import com.ecommerce.ecommerce_events.repository.OrderRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class JobConfig {

    @Bean
    public Job processOrdersJob(JobRepository jobRepository, Step processOrdersStep) {
        return new JobBuilder("processOrdersJob", jobRepository)
                .start(processOrdersStep)
                .build();
    }

    @Bean
    public Step processOrdersStep(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  ItemReader<String> orderItemReader,
                                  ItemProcessor<String, CustomerOrder> orderItemProcessor,
                                  ItemWriter<CustomerOrder> orderItemWriter) {
        return new StepBuilder("processOrdersStep", jobRepository)
                .<String, CustomerOrder>chunk(5, transactionManager)
                .reader(orderItemReader)
                .processor(orderItemProcessor)
                .writer(orderItemWriter)
                .build();
    }

    @Bean
    public ItemReader<String> orderItemReader() {
        List<String> orders = Arrays.asList("Order1", "Order2", "Order3", "Order4", "Order5", "Order6");
        return new ListItemReader<>(orders);
    }

    @Bean
    public OrderItemProcessor orderItemProcessor() {
        return new OrderItemProcessor();
    }

    @Bean
    public ItemWriter<CustomerOrder> orderItemWriter(OrderRepository orderRepository) {
        return items -> orderRepository.saveAll(items);
    }
}
