package com.stunstyle.miomart2.launcher;

import com.stunstyle.miomart2.ui.view.App;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppLauncher {
    private static final Logger logger = LogManager.getLogger(AppLauncher.class);

    public static void main(String[] args) {
        logger.info("Launching...");
        App.main(args);
    }
}
