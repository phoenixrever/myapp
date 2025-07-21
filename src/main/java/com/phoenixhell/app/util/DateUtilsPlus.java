package com.phoenixhell.app.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 日期时间工具类，基于 Java 8 时间 API 封装
 */
public class DateUtilsPlus {

  public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

  /** 当前时间字符串，格式: yyyy-MM-dd HH:mm:ss */
  public static String now() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
  }

  /** 格式化 LocalDateTime */
  public static String format(LocalDateTime dateTime, String pattern) {
    return dateTime.format(DateTimeFormatter.ofPattern(pattern));
  }

  /** 解析字符串为 LocalDateTime */
  public static LocalDateTime parse(String dateTimeStr, String pattern) {
    return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
  }

  /** 计算两个 LocalDate 之间的天数 */
  public static long betweenDays(LocalDate start, LocalDate end) {
    return ChronoUnit.DAYS.between(start, end);
  }

  /** 计算两个 LocalDateTime 之间的分钟数 */
  public static long betweenMinutes(LocalDateTime start, LocalDateTime end) {
    return ChronoUnit.MINUTES.between(start, end);
  }

  /** Date 转 LocalDateTime */
  public static LocalDateTime toLocalDateTime(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  /** LocalDateTime 转 Date */
  public static Date toDate(LocalDateTime dateTime) {
    return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
}
