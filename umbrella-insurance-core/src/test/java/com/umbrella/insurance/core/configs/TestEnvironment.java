package com.umbrella.insurance.core.configs;

import com.umbrella.insurance.core.models.environments.EnvironmentEnum;

public class TestEnvironment {
    /**
     * Used for normal tests
     */
    public static EnvironmentEnum TEST_ENV = EnvironmentEnum.TEST;
    /**
     * Used for testing create/drop tables and constraints
     */
    public static EnvironmentEnum TEST2_ENV = EnvironmentEnum.TEST2;
}
