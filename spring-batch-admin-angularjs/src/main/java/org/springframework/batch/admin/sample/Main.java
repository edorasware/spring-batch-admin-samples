/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.admin.sample;

import org.springframework.batch.admin.annotation.EnableBatchAdmin;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * <p>Spring Boot launching point for Spring Batch Admin.</p>
 *
 * @author Michael Minella
 */
@SpringBootApplication(exclude = {HypermediaAutoConfiguration.class, MultipartAutoConfiguration.class})
@EnableBatchAdmin
public class Main {

    private static final String CONTENT_PRE_PROCESSING_JOB_NAME = "content-pre-processing";

    // we need to override the jobLocator and fetch the default content pre processing job such that
    // we are able to see the job details in the web app. If we do not set it an error is thrown that the job
    // cannot be found. This would be fixed if we would run this application in the same Spring context as edoras one
    @Bean
    public JobLocator jobLocator() throws DuplicateJobException {
        SimpleJob contentPreProcessingJob = new SimpleJob(CONTENT_PRE_PROCESSING_JOB_NAME);
        MapJobRegistry mapJobRegistry = new MapJobRegistry();
        mapJobRegistry.register(new ReferenceJobFactory(contentPreProcessingJob));
        return mapJobRegistry;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
