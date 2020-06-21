package com.fincrime.tictactoe.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CommonConfig {

    @Bean
    public List<String[]> getCriteriaList() {
        List<String[]> criteriaList = new ArrayList<>();
        criteriaList.add(new String[]{"11", "21", "31"});
        criteriaList.add(new String[]{"12", "22", "32"});
        criteriaList.add(new String[]{"13", "23", "33"});
        criteriaList.add(new String[]{"11", "12", "13"});
        criteriaList.add(new String[]{"21", "22", "23"});
        criteriaList.add(new String[]{"31", "32", "33"});
        criteriaList.add(new String[]{"11", "22", "33"});
        criteriaList.add(new String[]{"31", "22", "13"});
        return criteriaList;
    }
}
