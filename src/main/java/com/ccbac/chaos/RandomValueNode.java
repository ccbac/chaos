package com.ccbac.chaos;

import org.dsa.iot.dslink.node.Node;
import org.dsa.iot.dslink.node.value.Value;
import java.util.Random;

import org.dsa.iot.dslink.node.value.ValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomValueNode extends GeneratedValueNode {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomValueNode.class);

    private static String VALUE_TYPE_IDENTIFIER = "random";

    private Number minimum;
    private Number maximum;
    private Random rand;

    private RandomValueNode(Node node) {
        super(node);
        this.rand = new Random();
    }

    public void updateValue() {
        Integer range = maximum.intValue() - minimum.intValue();
        Integer randomNumber = rand.nextInt(range) + minimum.intValue();
        node.setValueType(ValueType.NUMBER);
        node.setValue(new Value(randomNumber));
    }

    public static RandomValueNode add(Node parent, Value name, Value minimum, Value maximum) {
        try {
            String nameString = name.getString();
            Node node = parent.createChild(nameString).build();
            node.setRoConfig("valueType", new Value(VALUE_TYPE_IDENTIFIER));
            node.setRoConfig("minimum", minimum);
            node.setRoConfig("maximum", maximum);
            RandomValueNode valueNode = new RandomValueNode(node);
            valueNode.restoreSession();
            GeneratedValueNode.add(nameString, valueNode);
            return valueNode;
        } catch (Exception e) {
            return null;
        }
    }

    public static RandomValueNode createFromNode(Node node) {
        try {
            String valueType = node.getRoConfig("valueType").getString();
            if (valueType.equals(VALUE_TYPE_IDENTIFIER)) {
                RandomValueNode valueNode = new RandomValueNode(node);
                valueNode.restoreSession();
                return valueNode;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private void restoreSession() {
        Value minimum = this.node.getRoConfig("minimum");
        Value maximum = this.node.getRoConfig("maximum");
        this.minimum = minimum.getNumber();
        this.maximum = maximum.getNumber();
    }

}
