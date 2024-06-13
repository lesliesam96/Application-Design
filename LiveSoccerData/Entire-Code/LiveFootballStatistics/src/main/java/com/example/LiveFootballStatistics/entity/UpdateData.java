package com.example.LiveFootballStatistics.entity;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
/**
 * Function to update Json as String in DataBase
 */
public class UpdateData {
  private Long id;
  private String data;
}
