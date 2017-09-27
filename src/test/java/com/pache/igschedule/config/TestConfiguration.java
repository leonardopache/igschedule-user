package com.pache.igschedule.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pache.igscheduleuser.IgscheduleuserApplication;
import com.pache.igscheduleuser.repository.UserRepository;

/**
 * Created by lpache on 7/18/17.
 */
@Configuration
@Import({IgscheduleuserApplication.class})
public class TestConfiguration {
    
    @Bean
    public UserRepository userRepository() {
        return mock(UserRepository.class);
    }
}
