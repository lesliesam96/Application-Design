package com.example.LiveFootballStatistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The main class for the Live Football Statistics application.
 * This class is responsible for initializing the application and running the necessary tasks.
 */
/**
 * The main class for the Live Football Statistics application.
 * This class is responsible for initializing and running the application.
 */
@SpringBootApplication
@EnableScheduling
public class LiveFootballStatisticsApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

 /**
  * The entry point of the Live Football Statistics application.
  *
  * @param args The command line arguments
  */
	public static void main(String[] args) {
		SpringApplication.run(LiveFootballStatisticsApplication.class, args);
	}


 /**
  * Executes SQL statements to create/update necessary tables and delete existing data.
  *
  * @param args Command line arguments
  * @throws Exception If an error occurs while executing the SQL statements
  */
	@Override
	public void run(String... args) throws Exception {
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS UpdateLog(lastUpdateId int, timestamp int)");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS UpdateData(id int, data string)");
		jdbcTemplate.execute("DELETE FROM UpdateLog");
	}
}
