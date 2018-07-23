package com.ccbac.chaos;

import org.dsa.iot.dslink.node.Node;
import org.dsa.iot.dslink.node.Permission;
import org.dsa.iot.dslink.node.actions.Action;
import org.dsa.iot.dslink.node.actions.ActionResult;
import org.dsa.iot.dslink.node.actions.Parameter;
import org.dsa.iot.dslink.node.value.Value;
import org.dsa.iot.dslink.node.value.ValueType;
import org.dsa.iot.dslink.util.handler.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RootNode {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootNode.class);

    private Node node;

    static final private String ACTION_ADD_RANDOM_VALUE = "Add Random Value";
    static final private String ACTION_ADD_WAVEFORM_VALUE = "Add Waveform";

    RootNode(Node node) {
        this.node = node;
        makeAddRandomValueAction();
        makeAddWaveformValueAction();
        restoreSession();
    }

    private void restoreSession() {
        if (node.getChildren() == null) {
            return;
        }

        for (Node child : node.getChildren().values()) {
            if (child.getAction() == null) {
                GeneratedValueNode valueNode = GeneratedValueNode.restoreSession(child);
                if (valueNode == null) {
                    child.delete();
                }
            }
        }
    }

    private void makeAddRandomValueAction() {
        Action action = new Action(Permission.READ, new Handler<ActionResult>(){
            @Override
            public void handle(ActionResult event) {
                handleAddRandomValue(event);
            }
        });
        action.addParameter(new Parameter("Name", ValueType.STRING));
        action.addParameter(new Parameter("Minimum", ValueType.NUMBER, new Value(0)));
        action.addParameter(new Parameter("Maximum", ValueType.NUMBER, new Value(1000)));
        Node actionNode = node.getChild(ACTION_ADD_RANDOM_VALUE);
        if (actionNode == null) {
            node.createChild(ACTION_ADD_RANDOM_VALUE).setAction(action).build().setSerializable(false);
        } else {
            actionNode.setAction(action);
        }
    }

    private void handleAddRandomValue(ActionResult event) {
        Value name = event.getParameter("Name", ValueType.STRING);
        Value minimum = event.getParameter("Minimum", ValueType.NUMBER);
        Value maximum = event.getParameter("Maximum", ValueType.NUMBER);
        try {
            RandomValueNode.add(node, name, minimum, maximum);
        } catch (SecurityException | IllegalArgumentException e) {
            LOGGER.debug("Could not add random value node", e);
        }
    }

    private void makeAddWaveformValueAction() {
        Action action = new Action(Permission.READ, new Handler<ActionResult>(){
            @Override
            public void handle(ActionResult event) {
                handleAddWaveformValue(event);
            }
        });
        action.addParameter(new Parameter("Name", ValueType.STRING));
        action.addParameter(new Parameter("Minimum", ValueType.NUMBER, new Value(0)));
        action.addParameter(new Parameter("Maximum", ValueType.NUMBER, new Value(1000)));
        action.addParameter(new Parameter("Period in Seconds", ValueType.NUMBER, new Value(300)));
        Node actionNode = node.getChild(ACTION_ADD_WAVEFORM_VALUE);
        if (actionNode == null) {
            node.createChild(ACTION_ADD_WAVEFORM_VALUE).setAction(action).build().setSerializable(false);
        } else {
            actionNode.setAction(action);
        }
    }

    private void handleAddWaveformValue(ActionResult event) {
        Value name = event.getParameter("Name", ValueType.STRING);
        Value minimum = event.getParameter("Minimum", ValueType.NUMBER);
        Value maximum = event.getParameter("Maximum", ValueType.NUMBER);
        Value wavelengthSeconds = event.getParameter("Period in Seconds", ValueType.NUMBER);
        try {
            WaveformValueNode.add(node, name, minimum, maximum, wavelengthSeconds);
        } catch (SecurityException | IllegalArgumentException e) {
            LOGGER.debug("Could not add waveform value node", e);
        }
    }

}
