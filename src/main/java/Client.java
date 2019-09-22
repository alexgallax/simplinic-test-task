import io.grpc.ManagedChannel;
import io.qameta.allure.Step;
import lombok.extern.java.Log;
import test.task.MapExporterGrpc.MapExporterBlockingStub;
import test.task.Service;

import static io.grpc.ManagedChannelBuilder.forTarget;
import static test.task.MapExporterGrpc.newBlockingStub;

@Log
class Client {

    private final ManagedChannel channel;
    private MapExporterBlockingStub stub;

    private Client(Config config) {
        channel = forTarget(String.format("%s:%s", config.getHost(), config.getPort()))
                .usePlaintext(true)
                .build();
        stub = newBlockingStub(channel);
        log.info(String.format("Client created on %s:%s", config.getHost(), config.getPort()));
    }

    static Client createClient(Config config) {
        return new Client(config);
    }

    void stop() {
        log.info("Client is shutting down");
        channel.shutdown();
    }

    @Step("Perform import request with id '{0}'")
    Service.Campus requestImportById(String id) {
        Service.ImportRequest request = Service.ImportRequest.newBuilder()
                .setId(id)
                .build();
        Service.Campus response = stub.importCampus(request);
        log.info(String.format("Perform import request %s", request));
        log.info(String.format("Receive response %s", response));
        return response;
    }

    @Step("Perform export request with id '{0}'")
    Service.ExportResult requestExportById(String id) {
        Service.ExportRequest request = Service.ExportRequest.newBuilder()
                .setId(id)
                .build();
        Service.ExportResult response = stub.exportCampus(request);
        log.info(String.format("Perform export request %s", request));
        log.info(String.format("Receive response %s", response));
        return response;
    }
}
