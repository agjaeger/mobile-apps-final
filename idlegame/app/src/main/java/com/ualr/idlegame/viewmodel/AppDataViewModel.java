package com.ualr.idlegame.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class AppDataViewModel extends ViewModel {
    Map<String, Integer> resourceMap = new HashMap<>();


    public void registerResource (String resourceName) {
        resourceMap.put(resourceName, 0);
    }

    public void setResourceValue (String resourceName, Integer value) {
        resourceMap.put(resourceName, value);
    }

    public Integer getResourceValue (String resourceName) {
        return resourceMap.get(resourceName);
    }

    public void incrementResource (String resourceName, Integer amount) {
        resourceMap.put(resourceName, resourceMap.get(resourceName) + amount);
    }

}
