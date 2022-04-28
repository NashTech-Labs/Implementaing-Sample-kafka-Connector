package com.knoldus;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.config.ConfigException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SourceConnectorConfigTest {

    @Test
    public void basicParamsAreMandatory() {
        assertThrows(ConfigException.class, () -> {
            Map<String, String> props = new HashMap<>();
            new SourceConnectorConfig(props);
        });
    }

    public void checkingNonRequiredDefaults() {
        Map<String, String> props = new HashMap<>();
        SourceConnectorConfig config = new SourceConnectorConfig(props);
        assertEquals("foo", config.getString(SourceConnectorConfig.FIRST_NONREQUIRED_PARAM_CONFIG));
        assertEquals("bar", config.getString(SourceConnectorConfig.SECOND_NONREQUIRED_PARAM_CONFIG));
    }

}
