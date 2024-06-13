package com.example.LiveFootballStatistics.scheduler;

import com.example.LiveFootballStatistics.entity.UpdateData;
import com.example.LiveFootballStatistics.handler.SocketHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A scheduler component that performs data synchronization at a fixed rate.
 * This component is responsible for handling socket connections and executing database queries.
 * The scheduler is configured to have an initial delay of 6 seconds and a fixed rate of 20 seconds.
 * 
 * @Autowired socketHandler The socket handler component used for socket connections.
 * @Autowired jdbcTemplate The JdbcTemplate component used for executing database queries.
 * 
 * @Scheduled(initialDelay = 6L, fixedRate = 20L, timeUnit = TimeUnit.SECONDS)
 * The scheduler is scheduled to run with an initial delay of 6 seconds and a fixed rate of 20 seconds.
 * 
 * @throws RuntimeException if there is an error in processing JSON data.
 */
@Component
public class DataSyncScheduler {

  @Autowired
  private SocketHandler socketHandler;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /**
   * This method is scheduled to run periodically to check for data updates.
   * It retrieves the last update ID from the UpdateLog table and queries for new data
   * from the UpdateData table based on the last ID. If new data is found, it sends the
   * sports update to the socket handler and inserts the last ID and current timestamp
   * into the UpdateLog table.
   *
   * @throws JsonProcessingException if there is an error processing the JSON data
   */
  @Scheduled(initialDelay = 6L, fixedRate = 20L, timeUnit = TimeUnit.SECONDS)
  public void checkForDataUpdate() throws JsonProcessingException {
    List<Long> lastSyncId = jdbcTemplate.query("SELECT lastUpdateId FROM UpdateLog order by timestamp desc limit 1",
        (resultSet, rowCount) -> resultSet.getLong(1));
    long lastId = lastSyncId.isEmpty() ? 0 : lastSyncId.get(0);

    List<UpdateData> lastData = jdbcTemplate.query(
        String.format(
            "SELECT * FROM UpdateData where id > %s order by id asc limit 1",
            lastId
        ),
        (resultSet, rowCount) -> new UpdateData(resultSet.getLong(1), resultSet.getString(2)));
    if (lastData.isEmpty()) {
      return;
    }

    // Sending the update to all open websocket clients
    lastData.forEach(data -> {
      try {
        socketHandler.sendSportsUpdate(data.getData());
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    });


    // Updating the last synced data
    jdbcTemplate.execute(String.format("INSERT INTO UpdateLog values(%s, %s)", lastData.get(lastData.size() - 1).getId(),
        System.currentTimeMillis()));
  }
}
