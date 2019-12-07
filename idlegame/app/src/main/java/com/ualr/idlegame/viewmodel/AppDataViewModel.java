package com.ualr.idlegame.viewmodel;

import androidx.lifecycle.ViewModel;

import com.ualr.idlegame.db.DatabaseManager;

import java.util.HashMap;
import java.util.Map;

public class AppDataViewModel extends ViewModel {
    private DatabaseManager db = DatabaseManager.getInstance();
    private Map<String, Integer> resourceMap = new HashMap<>();

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

    public void saveDataToDB () {
        for (Map.Entry<String, Integer> entry : resourceMap.entrySet()) {
            db.putInt("resource_" + entry.getKey(), entry.getValue());
        }
    }

    public void restoreDataFromDatabase () {
        String[] resourceKeys = db.getKeysByPrefix("resource");

        for (String rKey : resourceKeys) {
            String[] rKeySplit = rKey.split("_");
            String resourceID = rKeySplit[1];

            resourceMap.put(resourceID, db.getInt(rKey));
        }
    }

    public void resetData () {
        for (Map.Entry<String, Integer> entry : resourceMap.entrySet()) {
            resourceMap.put(entry.getKey(), 0);
        }
    }
}
