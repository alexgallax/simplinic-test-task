import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

import static java.lang.Integer.parseInt;
import static java.util.logging.LogManager.getLogManager;

@Getter
class Config {

    private String host;
    private int port;

    private Config() throws IOException {
        getLogManager().readConfiguration(Config.class.getResourceAsStream("/logging.properties"));

        Properties props = new Properties();
        props.load(Config.class.getResourceAsStream("/run.properties"));
        host = props.getProperty("host");
        port = parseInt(props.getProperty("port"));
    }

    static Config loadConfig() throws IOException {
        return new Config();
    }
}
