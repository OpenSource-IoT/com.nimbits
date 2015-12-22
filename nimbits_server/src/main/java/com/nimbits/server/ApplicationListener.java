/*
 * Copyright (c) 2013 Nimbits Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressed or implied.  See the License for the specific language governing permissions and limitations under the License.
 */

package com.nimbits.server;

import com.dmurph.tracking.AnalyticsConfigData;
import com.dmurph.tracking.JGoogleAnalyticsTracker;
import com.nimbits.client.constants.Const;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Logger;


public class ApplicationListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(ApplicationListener.class.getName());




    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("NIMBITS Context Initialised");

        AnalyticsConfigData config = new AnalyticsConfigData("UA-11739682-14");

        config.populateFromSystem();
        JGoogleAnalyticsTracker tracker = new JGoogleAnalyticsTracker(config, JGoogleAnalyticsTracker.GoogleAnalyticsVersion.V_4_7_2);


        tracker.trackEvent("System", "contextInitialized", Const.VERSION);
       log.info("System Ready");


    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("NIMBITS Context Destroyed");
        ClassLoader applicationClassLoader = this.getClass().getClassLoader();
        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
        while (driverEnumeration.hasMoreElements()) {
            Driver driver = driverEnumeration.nextElement();
            ClassLoader driverClassLoader = driver.getClass().getClassLoader();
            if (driverClassLoader != null
                    && driverClassLoader.equals(applicationClassLoader)) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    e.printStackTrace(); //TODO Replace with your exception handling
                }
            }
        }
    }



}
