package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {

    // operand1=11
    // 쿼리 스트링의 key, value 쌍이 여러개일 경우.
    // List<QueryString> 일급 컬렉션이 필요.
    @Test
    void createTest() {
        QueryString queryString = new QueryString("operand1", "11");

        assertThat(queryString).isNotNull();
    }
}
