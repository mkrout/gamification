/*
 * This file is part of the Meeds project (https://meeds.io/).
 * Copyright (C) 2020 Meeds Association
 * contact@meeds.io
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.exoplatform.addons.gamification.test;

import org.exoplatform.addons.gamification.service.GamificationServiceTest;
import org.exoplatform.addons.gamification.service.configuration.BadgeServiceTest;
import org.exoplatform.addons.gamification.service.configuration.DomainServiceTest;
import org.exoplatform.addons.gamification.service.configuration.RuleServiceTest;
import org.exoplatform.addons.gamification.service.upgrade.RuleNameUpgradePluginTest;
import org.exoplatform.addons.gamification.storage.dao.BadgeDAOTest;
import org.exoplatform.addons.gamification.storage.dao.RuleDAOTest;
import org.exoplatform.addons.gamification.test.rest.TestManageBadgesEndpoint;
import org.exoplatform.addons.gamification.test.rest.TestManageDomainsEndpoint;
import org.exoplatform.commons.testing.BaseExoContainerTestSuite;
import org.exoplatform.commons.testing.ConfigTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        GamificationServiceTest.class,
        RuleServiceTest.class,
        BadgeServiceTest.class,
        DomainServiceTest.class,
        BadgeDAOTest.class,
        RuleDAOTest.class,
        TestManageDomainsEndpoint.class,
        TestManageBadgesEndpoint.class,
        RuleNameUpgradePluginTest.class
})
@ConfigTestCase(AbstractServiceTest.class)
public class InitContainerTestSuite extends BaseExoContainerTestSuite {

    @BeforeClass
    public static void setUp() throws Exception {
        initConfiguration(InitContainerTestSuite.class);
        beforeSetup();

    }

    @AfterClass
    public static void tearDown() {
        afterTearDown();
    }
}
