package ru.manasyan.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Util {

    public static LocalDateTime currentDateTime() {

        // for production
        //return LocalDateTime.now();

        // for debug
        return LocalDateTime.of(2019, 8, 16, 11,00);
    }
}
