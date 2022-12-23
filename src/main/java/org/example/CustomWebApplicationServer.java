package org.example;

import org.example.calculator.domain.Calculator;
import org.example.calculator.domain.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomWebApplicationServer {
    private final int port;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client");

            while ((clientSocket = serverSocket.accept()) != null) {
                logger.info("[CustomWebApplicationServer] client connected!");

                /**
                 * Step1 - 사용자 요청을 메인 Thread가 처리 하도록 한다.
                 */
//                this.resolveHttpRequestAndResponse(clientSocket);

                /**
                 * Step2 - 각 요청마다 별도 Thread가 처리 하도록 한다.
                 * {@link ClientRequestHandler#run()} 에서 처리.
                 */
//                new Thread(new ClientRequestHandler(clientSocket)).start();

                /**
                 * Step2 에서는 Thread가 요청의 수 만큼 무제한 생성. 서버 자원 고갈 위험.
                 * Step3 - Thread Pool을 이용하여 http 요청을 처리한다.
                 */
                executorService.execute(new ClientRequestHandler(clientSocket));

            }
        }
    }

    @Deprecated
    private void resolveHttpRequestAndResponse(Socket clientSocket) throws IOException {
        try (InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            DataOutputStream dos = new DataOutputStream(out);

            // GET /calcaulate?operand1=11&operator=*&operand2=55 HTTP/1.1
            HttpRequest httpRequest = new HttpRequest(br);
            if (httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")) {
                QueryStrings queryStrings = httpRequest.getQueryStrings();

                int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                String operator = queryStrings.getValue("operator");
                int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));

                int result = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));
                byte[] body = String.valueOf(result).getBytes();

                HttpResponse response = new HttpResponse(dos);
                response.response200Header("application/json", body.length);
                response.responseBody(body);
            }

        }
    }

}
