import test.task.Service;

import java.io.IOException;

class Stub {

    private TestServer server;

    private Stub(Config config) {
        new Thread(() -> {
            server = TestServer.createTestServerOnPort(config.getPort());
            try {
                server.start();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    static Stub createStub(Config config) {
        return new Stub(config);
    }

    void setResponseOnImportCampus(Service.Campus response) {
        server.getService().setImportResponse(response);
    }

    void setResponseOnExportCampus(Service.ExportResult response) {
        server.getService().setExportResponse(response);
    }

    void stop() {
        server.stop();
    }
}
