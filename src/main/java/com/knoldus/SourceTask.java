package com.knoldus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;

import static com.knoldus.SourceConnectorConfig.*;

public class SourceTask extends org.apache.kafka.connect.source.SourceTask {

    private static Logger log = LoggerFactory.getLogger(SourceTask.class);

    private SourceConnectorConfig config;
    private int monitorThreadTimeout;
    private List<String> sources;

    @Override
    public String version() {
        return PropertiesUtil.getConnectorVersion();
    }

    @Override
    public void start(Map<String, String> properties) {
        config = new SourceConnectorConfig(properties);
        monitorThreadTimeout = config.getInt(MONITOR_THREAD_TIMEOUT_CONFIG);
        String sourcesStr = properties.get("sources");
        sources = Arrays.asList(sourcesStr.split(","));
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        Thread.sleep(monitorThreadTimeout / 2);
        List<SourceRecord> records = new ArrayList<>();
        for (String source : sources) {
            log.info("Polling data from the source '" + source + "'");
            records.add(new SourceRecord(
                Collections.singletonMap("source", source),
                Collections.singletonMap("offset", 0),
                source, null, null, null, Schema.BYTES_SCHEMA,
                String.format("Data from %s", source).getBytes()));
        }
        return records;
    }

    @Override
    public void stop() {
    }

}
