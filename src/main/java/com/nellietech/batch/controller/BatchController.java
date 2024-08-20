package com.nellietech.batch.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nellietech.batch.tasks.ScheduledTasks;

@RestController
public class BatchController {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	@PostMapping("/process")
	public String processBatch(@RequestParam String fileName) 
			throws JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		log.info("The time is now {}", dateFormat.format(new Date()));
		String uuidString = UUID.randomUUID().toString();
		JobParameters params = new JobParametersBuilder().addString("JobID", uuidString)
				.toJobParameters();
		jobLauncher.run(job, params);
		
		return "STARTED BATCH JOB: " + uuidString;
	}

}