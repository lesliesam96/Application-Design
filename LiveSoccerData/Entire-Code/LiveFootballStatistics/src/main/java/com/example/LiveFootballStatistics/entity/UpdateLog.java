package com.example.LiveFootballStatistics.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents an update log entry.
 *
 * This class provides a convenient way to store information about an update log, including the last update ID and the timestamp.
 *
 * @param lastUpdateId The ID of the last update
 * @param timestamp The timestamp of the update log entry
 */
@Data
@AllArgsConstructor
public class UpdateLog {
  private Long lastUpdateId;
  private Long timestamp;
}
