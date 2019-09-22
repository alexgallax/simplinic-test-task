import io.grpc.Server;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.IOException;

import static io.grpc.ServerBuilder.forPort;

@Log
class TestServer {

    private Server server;
    @Getter
    private MapExporterService service;
    private int port;

    private TestServer(int port) {
        this.port = port;
        service = new MapExporterService();
        server = forPort(port)
                .addService(service)
                .build();
    }

    static TestServer createTestServerOnPort(int port) {
        return new TestServer(port);
    }

    void start() throws IOException, InterruptedException {
        server.start();
        log.info(String.format("Test server started on port %s", port));
        server.awaitTermination();
    }

    void stop() {
        log.info("Test server is shutting down");
        server.shutdown();
    }
}
