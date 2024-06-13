package com.example.is_poi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CompareDate {

    public static int compareDateWithCurrentDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");

        try {
            LocalDate date = LocalDate.parse(dateString, formatter);
            LocalDate currentDate = LocalDate.now();

            if (date.isBefore(currentDate)) {
                return -1;
            } else if (date.isAfter(currentDate)) {
                return 1;
            } else {
                return 0;
            }
        } catch (DateTimeParseException e) {
            return -2;
        }
    }
}
