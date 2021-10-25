package io.titandata.titan;

import io.titandata.titan.providers.Provider;
import io.titandata.titan.providers.ProviderFactory;
import kotlin.Pair;

import java.util.*;

public class Titan {

    private final Provider provider;
    private final String container;

    public Titan(String repo) {
        Dependencies dependencies = new Dependencies(new ProviderFactory());
        Pair<Provider, String> found = dependencies.getProviders().byRepository(repo);
        this.provider = found.component1();
        this.container = found.component2();
    }

    public Provider getProvider() {
        return provider;
    }
    public String getContainer() {
        return container;
    }

    public List<String> toList(String commaString) {
        List<String> returnList = new ArrayList<>();
        if (commaString != null) {
            if (commaString.contains(",")) {
                returnList.addAll(Arrays.asList(commaString.split(",", 0)));
            } else {
                returnList.add(commaString);
            }
        }
        return returnList;
    }

    public Map<String, String> toMap(String commaString) {
        Map<String, String> returnMap = new HashMap<>();
        if (commaString != null) {
            if (commaString.contains(",")) {
                for (String param : commaString.split(",", 0)) {
                    String[] paramSplit = param.split("=", 1);
                    returnMap.put(paramSplit[0], paramSplit[1]);
                }
            } else {
                String[] paramSplit = commaString.split("=", 1);
                returnMap.put(paramSplit[0], paramSplit[1]);
            }
        }
        return returnMap;
    }
}
