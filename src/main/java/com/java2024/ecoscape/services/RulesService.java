package com.java2024.ecoscape.services;

import com.java2024.ecoscape.repository.RulesRepository;
import org.springframework.stereotype.Service;

@Service
public class RulesService {

    private final RulesRepository rulesRepository;

    public RulesService(RulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }


}
