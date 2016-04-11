/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.configs;

import com.hazelcast.config.ListConfig;
import static com.hazelcast.config.MapConfig.DEFAULT_TTL_SECONDS;

/**
 *
 * @author armenar
 */
public class LocalListConfig extends ListConfig {

    private int timeToLiveSeconds = DEFAULT_TTL_SECONDS;

    public LocalListConfig() {
    }
    
    

    public LocalListConfig(LocalListConfig config) {
        this.timeToLiveSeconds = config.timeToLiveSeconds;
    }

    public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }

    public LocalListConfig setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
        return this;
    }

}
