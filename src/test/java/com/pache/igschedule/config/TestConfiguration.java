package com.pache.igschedule.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pache.igscheduleuser.IgscheduleuserApplication;

/**
 * Created by lpache on 7/18/17.
 */
@Configuration
@Import({IgscheduleuserApplication.class})
public class TestConfiguration {

}
