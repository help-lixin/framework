package help.lixin.framework.message.source.model;

import java.io.Serializable;
import java.util.*;

public class MessageSourceCollection implements Serializable {
    private final Map<Locale, List<MessageSourceEntry>> resources = new HashMap<>();

    public void addResource(Locale locale, MessageSourceEntry entry) {
        synchronized (resources) {
            if (!resources.containsKey(locale)) {
                List<MessageSourceEntry> messageSourceEntrys = new ArrayList<>();
                messageSourceEntrys.add(entry);
                resources.put(locale, messageSourceEntrys);
            } else {
                List<MessageSourceEntry> messageSourceEntrys = resources.get(locale);
                messageSourceEntrys.add(entry);
            }
        }
    }

    public List<MessageSourceEntry> getResource(Locale locale) {
        List<MessageSourceEntry> properties = new ArrayList<>(0);
        if (resources.containsKey(locale)) {
            List<MessageSourceEntry> propertiesList = resources.get(locale);
            if (null != propertiesList) {
                properties.addAll(propertiesList);
            }
        }
        return properties;
    }
}
