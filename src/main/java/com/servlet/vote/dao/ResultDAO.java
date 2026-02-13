package com.servlet.vote.dao;

import java.util.List;
import java.util.Map;

public interface ResultDAO {
    List<Map<String, Object>> getElectionWiseResults();
}
