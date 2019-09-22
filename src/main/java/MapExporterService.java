import io.grpc.stub.StreamObserver;
import lombok.Setter;
import test.task.MapExporterGrpc;
import test.task.Service;

@Setter
public class MapExporterService extends MapExporterGrpc.MapExporterImplBase {

    private Service.Campus importResponse;
    private Service.ExportResult exportResponse;

    public void importCampus(Service.ImportRequest request, StreamObserver<Service.Campus> responseObserver) {
        responseObserver.onNext(importResponse);
        responseObserver.onCompleted();
    }

    public void exportCampus(Service.ExportRequest request, StreamObserver<Service.ExportResult> responseObserver) {
        responseObserver.onNext(exportResponse);
        responseObserver.onCompleted();
    }
}
