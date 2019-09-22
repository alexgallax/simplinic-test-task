import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import test.task.Service;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestCases {

    private static Client client;
    private static Stub stub;

    @BeforeClass
    public static void setUp() throws IOException {
        Config config = Config.loadConfig();
        client = Client.createClient(config);
        stub = Stub.createStub(config);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Import campus by id with rooms with sites and gateway positions")
    public void importCampusWithRooms() {
        Service.Campus campus = Service.Campus.newBuilder()
                .setId("testId")
                .setName("testName")
                .putRooms("one",
                        Service.Campus.Room.newBuilder()
                                .addSites(Service.Campus.Site.newBuilder()
                                        .setId(2)
                                        .build())
                                .addSites(Service.Campus.Site.newBuilder()
                                        .setId(201)
                                        .build())
                                .addGatewayPositions(Service.Campus.GatewayPosition.newBuilder()
                                        .setId(100)
                                        .build())
                                .addGatewayPositions(Service.Campus.GatewayPosition.newBuilder()
                                        .setId(130)
                                        .build())
                                .build())
                .putRooms("two",
                        Service.Campus.Room.newBuilder()
                                .addSites(Service.Campus.Site.newBuilder()
                                        .setId(3)
                                        .build())
                                .addGatewayPositions(Service.Campus.GatewayPosition.newBuilder()
                                        .setId(300)
                                        .build())
                                .build())
                .build();
        stub.setResponseOnImportCampus(campus);

        Service.Campus response = client.requestImportById("testId");

        assertThat("Imported campus data not match expected", response, is(equalTo(campus)));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Import empty campus")
    public void importEmptyCampus() {
        Service.Campus campus = Service.Campus.newBuilder()
                .setId("testId")
                .build();
        stub.setResponseOnImportCampus(campus);

        Service.Campus response = client.requestImportById("testId");

        assertThat("Imported campus data not match expected", response, is(equalTo(campus)));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Import campus by id without name")
    public void importCampusWithoutName() {
        Service.Campus campus = Service.Campus.newBuilder()
                .setId("testId")
                .setName("")
                .putRooms("one",
                        Service.Campus.Room.newBuilder()
                                .addSites(Service.Campus.Site.newBuilder()
                                        .setId(2)
                                        .build())
                                .addGatewayPositions(Service.Campus.GatewayPosition.newBuilder()
                                        .setId(130)
                                        .build())
                                .build())
                .build();
        stub.setResponseOnImportCampus(campus);

        Service.Campus response = client.requestImportById("testId");

        assertThat("Imported campus data not match expected", response, is(equalTo(campus)));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Import non existed campus")
    public void importNonExistedCampus() {
        Service.Campus campus = Service.Campus.newBuilder()
                .setId("testId")
                .build();
        stub.setResponseOnImportCampus(null);

        Service.Campus response = client.requestImportById("testId");

        assertThat("Imported campus data not match expected", response, is(not(equalTo(campus))));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Export campus with rooms with sites and gateway positions")
    public void exportCampusWithRooms() {
        Service.Campus campus = Service.Campus.newBuilder()
                .setId("testId")
                .setName("testName")
                .putRooms("one",
                        Service.Campus.Room.newBuilder()
                                .addSites(Service.Campus.Site.newBuilder()
                                        .setId(2)
                                        .build())
                                .addSites(Service.Campus.Site.newBuilder()
                                        .setId(201)
                                        .build())
                                .addGatewayPositions(Service.Campus.GatewayPosition.newBuilder()
                                        .setId(100)
                                        .build())
                                .addGatewayPositions(Service.Campus.GatewayPosition.newBuilder()
                                        .setId(130)
                                        .build())
                                .build())
                .putRooms("two",
                        Service.Campus.Room.newBuilder()
                                .addSites(Service.Campus.Site.newBuilder()
                                        .setId(3)
                                        .build())
                                .addGatewayPositions(Service.Campus.GatewayPosition.newBuilder()
                                        .setId(300)
                                        .build())
                                .build())
                .build();
        stub.setResponseOnExportCampus(Service.ExportResult.newBuilder().setResult("OK").build());

        Service.ExportResult response = client.requestExportById("testId");

        assertThat("Export campus data failed", response,
                is(equalTo(Service.ExportResult.newBuilder().setResult("OK").build())));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Export empty campus")
    public void exportEmptyCampus() {
        Service.Campus campus = Service.Campus.newBuilder()
                .setId("testId")
                .build();
        stub.setResponseOnExportCampus(Service.ExportResult.newBuilder().setResult("OK").build());

        Service.ExportResult response = client.requestExportById("testId");

        assertThat("Export campus data failed", response,
                is(equalTo(Service.ExportResult.newBuilder().setResult("OK").build())));
    }

    @AfterClass
    public static void tearDown() {
        client.stop();
        stub.stop();
    }
}
