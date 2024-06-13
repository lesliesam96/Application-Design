package com.example.LiveFootballStatistics.scheduler;

import com.example.LiveFootballStatistics.dto.MatchData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Synchronizes the latest data from an API and persists it in the database.
 * This method is scheduled to run at a fixed rate of 10 seconds.
 * It uses the JdbcTemplate to execute an SQL INSERT statement to store the latest data in the database.
 * If the API call is successful and returns non-empty data, the data is persisted in the database.
 * If the API call is successful but returns empty data, a message is printed indicating empty data.
 * If the API call fails, a message is printed indicating the failure.
 */
@Component
public class LiveDataSyncScheduler {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /**
   * Synchronizes the latest data from an API and persists it in the database.
   * This method is scheduled to run at a fixed rate of 10 seconds.
   *
   * @throws JsonProcessingException if there is an error processing the JSON response
   */
  @Scheduled(fixedRate = 10L, timeUnit = TimeUnit.SECONDS)
  public void syncLatestDataFromAPIAndPersistInDB() throws JsonProcessingException {
    final ObjectMapper mapper = new ObjectMapper();
    final RestTemplate restTemplate = new RestTemplate();
    String url = "https://apiv2.allsportsapi.com/football/?met=Livescore&APIkey" +
        "=e4395ebf0e806c01901b964500369e5488308c1435fe2f50b9363c4da9b22d98";
    ResponseEntity<MatchData> response = restTemplate.getForEntity(url, MatchData.class);

    if (response.getStatusCode().is2xxSuccessful()) {
      MatchData data = response.getBody();

      if (data != null && data.getSuccess() == 1 && data.getResult() != null && !data.getResult().isEmpty()) {
        String dataString = mapper.writeValueAsString(data);

        // Fetch Latest Id and persist latest data
        List<Integer> lastAddedDataId = jdbcTemplate.query("SELECT max(id) from UpdateData", (row, count) ->
            row.getInt(1));
        int newId = lastAddedDataId.isEmpty() ? 0 : lastAddedDataId.get(0) + 1;
        jdbcTemplate.execute(String.format("INSERT INTO UpdateData values(%s, %s)", newId, dataString));

        System.out.println("Persisted Latest Data from LiveSyncAPI :)");
      } else {
        System.out.println("Empty Data from LiveSyncAPI");
      }
    } else {
      System.out.println("API Call to LiveSyncAPI failed !");
    }
  }
}
