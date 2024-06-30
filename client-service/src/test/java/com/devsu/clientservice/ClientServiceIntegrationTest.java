package com.devsu.clientservice;

import com.intuit.karate.junit5.Karate;

public class ClientServiceIntegrationTest {

    @Karate.Test
    Karate testAll() {
        return Karate.run("classpath:karate/ClientServiceIntegrationTest.feature").relativeTo(getClass());
    }
}
