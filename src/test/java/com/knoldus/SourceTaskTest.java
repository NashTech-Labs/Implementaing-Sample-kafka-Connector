package com.knoldus;

import org.apache.kafka.connect.source.SourceRecord;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SourceTaskTest {

    @Test
    public void taskVersionShouldMatch() {
        String version = PropertiesUtil.getConnectorVersion();
        assertEquals(version, new SourceTask().version());
    }

    @Test
    public void checkNumberOfRecords() {
        Map<String, String> connectorProps = new HashMap<>();
        connectorProps.put(SourceConnectorConfig.FIRST_REQUIRED_PARAM_CONFIG, "Kafka");
        connectorProps.put(SourceConnectorConfig.SECOND_REQUIRED_PARAM_CONFIG, "Connect");
        Map<String, String> taskProps = getTaskProps(connectorProps);
        SourceTask task = new SourceTask();
        assertDoesNotThrow(() -> {
            task.start(taskProps);
            List<SourceRecord> records = task.poll();
            assertEquals(3, records.size());
        });
    }

    private Map<String, String> getTaskProps(Map<String, String> connectorProps) {
        SourceConnector connector = new SourceConnector();
        connector.start(connectorProps);
        List<Map<String, String>> taskConfigs = connector.taskConfigs(1);
        return taskConfigs.get(0);
    }
    
}
