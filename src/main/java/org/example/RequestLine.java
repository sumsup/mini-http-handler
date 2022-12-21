package org.example;

import java.util.Objects;

public class RequestLine {
    private final String method; // GET
    private final String urlPath; // /calculate
    private  QueryStrings queryStrings; // operand1=11&operator=*&operand2=55

    public RequestLine(String method, String urlPath, String queryStrings) {
        this.method = method;
        this.urlPath = urlPath;
        this.queryStrings = new QueryStrings(queryStrings);
    }

    /**
     * GET /calcaulate?operand1=11&operator=*&operand2=55 HTTP/1.1
     * @param requestLine
     */
    public RequestLine(String requestLine) {
        // 요청받은 첫번째 줄의 request정보는 아래와 같다.
        // GET /calcaulate?operand1=11&operator=*&operand2=55 HTTP/1.1

        String[] tokens = requestLine.split(" ");
        this.method = tokens[0]; // GET

        String[] urlPathTokens = tokens[1].split("\\?");
        this.urlPath = urlPathTokens[0]; // /calcaulate?operand1=11&operator=*&operand2=55

        if (urlPathTokens.length == 2) { // ? 를 기점으로 뒤에 쿼리스트링이 있으면 배열의 크기가 2 이므로.
            this.queryStrings = new QueryStrings(urlPathTokens[1]); // operand1=11&operator=*&operand2=55
        }

    }

    public boolean isGetRequest() {
        return "GET".equals(this.method);
    }

    public boolean matchPath(String requestPath) {
        return urlPath.equals(requestPath);
    }

    public QueryStrings getQueryStrings() {
        return queryStrings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) && Objects.equals(urlPath, that.urlPath) && Objects.equals(queryStrings, that.queryStrings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, urlPath, queryStrings);
    }
}
