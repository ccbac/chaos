package com.ccbac.chaos;

import org.dsa.iot.dslink.node.Node;
import org.dsa.iot.dslink.node.Permission;
import org.dsa.iot.dslink.node.actions.Action;
import org.dsa.iot.dslink.node.actions.ActionResult;
import org.dsa.iot.dslink.util.handler.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;

public class GeneratedValueNode {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratedValueNode.class);

    protected static Hashtable<String, GeneratedValueNode> valueNodes = new Hashtable<String, GeneratedValueNode>();

    protected Node node;

    static final private String ACTION_DELETE = "Delete";

    protected GeneratedValueNode(Node node) {
        this.node = node;
        makeDeleteAction();
    }

    public static void updateAllValues() {
        for (GeneratedValueNode valueNode : valueNodes.values()) {
            valueNode.updateValue();
        }
    }

    public void updateValue() {
    }

    public static GeneratedValueNode restoreSession(Node node) {
        RandomValueNode valueNode = RandomValueNode.createFromNode(node);
        if (valueNode != null) {
            valueNodes.put(node.getName(), valueNode);
            return valueNode;
        }
        return null;
    }

    public static void add(String name, GeneratedValueNode valueNode) {
        valueNodes.put(name, valueNode);
    }

    private void makeDeleteAction() {
        Action action = new Action(Permission.READ, new Handler<ActionResult>(){
            @Override
            public void handle(ActionResult event) {
                handleDelete(event);
            }
        });
        Node actionNode = node.getChild(ACTION_DELETE);
        if (actionNode == null) {
            node.createChild(ACTION_DELETE).setAction(action).build().setSerializable(false);
        } else {
            actionNode.setAction(action);
        }
    }

    private void handleDelete(ActionResult event) {
        try {
            valueNodes.remove(node.getName());
            node.delete();
        } catch (SecurityException | IllegalArgumentException e) {
            LOGGER.debug("Could not delete value node", e);
        }
    }

}
