package com.ccbac.chaos;

import org.dsa.iot.dslink.DSLink;
import org.dsa.iot.dslink.DSLinkFactory;
import org.dsa.iot.dslink.DSLinkHandler;
import org.dsa.iot.dslink.node.Node;
import org.dsa.iot.dslink.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * The main class that starts the DSLink. Typically it extends
 * {@link DSLinkHandler} and the main method extends into it.
 */
public class Main extends DSLinkHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private RootNode rootNode;

    @Override
    public boolean isResponder() {
        return true;
    }

    @Override
    public void onResponderInitialized(DSLink link) {
        Node superRoot = link.getNodeManager().getSuperRoot();

        rootNode = new RootNode(superRoot);

        Objects.getDaemonThreadPool().scheduleWithFixedDelay(GeneratedValueNode::updateAllValues, 0, 20, TimeUnit.SECONDS);

        LOGGER.info("Initialized");
    }

    @Override
    public void onResponderConnected(DSLink link) {
        LOGGER.info("Connected");
    }

    public static void main(String[] args) {
        DSLinkFactory.start(args, new Main());
    }
}
