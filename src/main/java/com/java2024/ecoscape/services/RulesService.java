package com.java2024.ecoscape.services;

import com.java2024.ecoscape.models.Rules;
import com.java2024.ecoscape.repository.RulesRepository;
import org.springframework.stereotype.Service;

@Service
public class RulesService {

    private final RulesRepository rulesRepository;

    public RulesService(RulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }

    //metod för Rules validation
    public void validateRules(Rules rules) {
        // Validate that rulesText is not null or empty, and follows the pattern
        if (rules.getRulesText() != null && rules.getRulesText().length() > 250) {
            throw new IllegalArgumentException("Rules text cannot exceed 250 characters");
        }
        if (rules.getRulesText() != null && !rules.getRulesText().matches("^[A-Za-z0-9\\s\\.,!?\'\"\\(\\)\\-\\&\\#\\*\\+\\=]*$")) {
            throw new IllegalArgumentException("Invalid rule description! Only letters, numbers, spaces, commas, periods, exclamation marks, question marks, and other specified characters are allowed.");
        }

        // Validate check-in time format (HH:00)
        if (rules.getCheckInTime() == null || !rules.getCheckInTime().matches("^(0[0-9]|1[0-9]|2[0-3]):00$")) {
            throw new IllegalArgumentException("Invalid check-in time format! The time must be in the format HH:00, where HH is between 00 and 23.");
        }

        // Validate check-out time format (HH:00)
        if (rules.getCheckOutTime() == null || !rules.getCheckOutTime().matches("^(0[0-9]|1[0-9]|2[0-3]):00$")) {
            throw new IllegalArgumentException("Invalid check-out time format! The time must be in the format HH:00, where HH is between 00 and 23.");
        }

        // Validate quiet hours if provided
        if (rules.getQuiteHoursStart() != null && !rules.getQuiteHoursStart().matches("^(0[0-9]|1[0-9]|2[0-3]):00$")) {
            throw new IllegalArgumentException("Invalid quiet hours start time format! The time must be in the format HH:00, where HH is between 00 and 23.");
        }

        if (rules.getQuiteQuiteHoursStop() != null && !rules.getQuiteQuiteHoursStop().matches("^(0[0-9]|1[0-9]|2[0-3]):00$")) {
            throw new IllegalArgumentException("Invalid quiet hours stop time format! The time must be in the format HH:00, where HH is between 00 and 23.");
        }
    }

    //metod för att spara Rules till Rules repositoriet
    public Rules saveRules(Rules rules) {
        return rulesRepository.save(rules);
    }


}
