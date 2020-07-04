package com.failsfast.nbastatendpoints.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class NbaStatResultSet {

    private String Name;
    private List<String> Headers = new ArrayList<>();
    private List<List<String>> rowSet = new ArrayList<>();

    public Map<String, String> findHeaderResultsMapByField(String fieldName, Object value) {

        List<String> rowSet = findResultsByField(fieldName, value);

        Map<String, String> headerResultsMap = new HashMap<>();

        for(int i = 0; i < getHeaders().size(); i++) {
            headerResultsMap.put(getHeaders().get(i), rowSet.get(i));
        }

        return headerResultsMap;

    }

    //business methods
    private List<String> findResultsByField(String fieldName, Object value) {
        int fieldIndex = Headers.indexOf(fieldName);
        if(fieldIndex < 0) {
            throw new RuntimeException(fieldName + "is not present in this result set");
        }
        List<String> row = null;
        for(List<String> rowSet : rowSet) {

            if(value instanceof Integer) {
                if(Integer.parseInt(rowSet
                                        .get(fieldIndex)) == (Integer) value) {
                    row = rowSet;
                }
            }

            if(value instanceof String) {
                if(rowSet.get(fieldIndex)
                         .equalsIgnoreCase((String) value)) {
                    row = rowSet;
                }
            }

        }
        if(row == null) {
            throw new RuntimeException(fieldName + " not found for " + value);
        }
        return row;
    }

}
