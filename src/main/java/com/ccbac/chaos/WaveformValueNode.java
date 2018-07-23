package com.ccbac.chaos;

import org.dsa.iot.dslink.node.Node;
import org.dsa.iot.dslink.node.value.Value;

import org.dsa.iot.dslink.node.value.ValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaveformValueNode extends GeneratedValueNode {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaveformValueNode.class);

    private static String VALUE_TYPE_IDENTIFIER = "waveform";

    private Number minimum;
    private Number maximum;
    private Number periodSeconds;
    private WaveformGenerator waveformGenerator;

    private WaveformValueNode(Node node) {
        super(node);
    }

    public void updateValue() {
        node.setValueType(ValueType.NUMBER);
        node.setValue(new Value(waveformGenerator.nextValue()));
    }

    public static WaveformValueNode add(Node parent, Value name, Value minimum, Value maximum, Value periodSeconds) {
        try {
            String nameString = name.getString();
            Node node = parent.createChild(nameString).build();
            node.setRoConfig("valueType", new Value(VALUE_TYPE_IDENTIFIER));
            node.setRoConfig("minimum", minimum);
            node.setRoConfig("maximum", maximum);
            node.setRoConfig("periodSeconds", periodSeconds);
            WaveformValueNode valueNode = new WaveformValueNode(node);
            valueNode.restoreSession();
            GeneratedValueNode.add(nameString, valueNode);
            return valueNode;
        } catch (Exception e) {
            return null;
        }
    }

    public static WaveformValueNode createFromNode(Node node) {
        try {
            String valueType = node.getRoConfig("valueType").getString();
            if (valueType.equals(VALUE_TYPE_IDENTIFIER)) {
                WaveformValueNode valueNode = new WaveformValueNode(node);
                valueNode.restoreSession();
                return valueNode;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private void restoreSession() {
        this.minimum = this.node.getRoConfig("minimum").getNumber();
        this.maximum = this.node.getRoConfig("maximum").getNumber();
        this.periodSeconds = this.node.getRoConfig("periodSeconds").getNumber();
        this.waveformGenerator = new SineWaveGenerator(minimum.doubleValue(), maximum.doubleValue(), periodSeconds.doubleValue());
    }

}
