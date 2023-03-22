package com.webhook.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * The QueryParser class is responsible for parsing a query string and
 * returning a Map of key-value pairs representing the query parameters.
 *
 * Example usage:
 *
 *     Map<String, String> queryParams = QueryParser.parse("name=John&age=30");
 *
 *     System.out.println(queryParams.get("name")); // "John"
 *     System.out.println(queryParams.get("age")); // "30"
 *
 * Query parameters without a value are stored with a null value in the map.
 *
 * Note that the parse() method returns an empty map if the query string is null.
 */
public class QueryParser {
    public static Map<String, String> parse(String query) {
        Map<String, String> result = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                String key = idx > 0 ? pair.substring(0, idx) : pair;
                String value = idx > 0 && pair.length() > idx + 1 ? pair.substring(idx + 1) : null;
                result.put(key, value);
            }
        }
        return result;
    }
}