package com.servlet.vote.util;

import java.time.Year;

public class VoterIdUtil {

    public static String generateVoterId(int dbId) {
        int year = Year.now().getValue();
        return "UV-" + year + "-" + String.format("%04d", dbId);
    }
}
