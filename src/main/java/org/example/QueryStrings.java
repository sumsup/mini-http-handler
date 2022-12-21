package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 일급 컬렉션.
 * 필드 멤버로 컬렉션 하나만 갖고 다른 멤버필드는 없는 클래스.
 * 컬렉션 Wrapper Class로 보면 된다.
 * 데이터와 비즈니스 로직을 한 클래스에서 관리할 수 있음.
 * 불변 객체로 만들어서 가지고 있을 수도 있다.
 */
public class QueryStrings {
    private final List<QueryString> queryStrings = new ArrayList<>();
    public QueryStrings(String queryStringLine) {
        String[] queryStringTokens = queryStringLine.split("&");
        Arrays.stream(queryStringTokens)
                .forEach(queryString -> {
                    String[] values = queryString.split("=");
                    if (values.length != 2) {
                        throw new IllegalArgumentException("잘못된 QueryString 포맷을 가진 문자열 입니다.");
                    }
                    queryStrings.add(new QueryString(values[0], values[1]));
                });

    }

    public String getValue(String key) {
        return this.queryStrings.stream().filter(queryString -> queryString.exists(key))
                .map(QueryString::getValue)
                .findFirst()
                .orElse(null);
    }
}
