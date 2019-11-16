package help.lixin.framework.config.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {


    /**
     * Returns {@code true} if the specified {@code Collection} is {@code null} or {@link Collection#isEmpty empty},
     * {@code false} otherwise.
     *
     * @param c the collection to check
     * @return {@code true} if the specified {@code Collection} is {@code null} or {@link Collection#isEmpty empty},
     *         {@code false} otherwise.
     * @since 1.0
     */
    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }
    
    public static boolean isEmpty(Map m) {
        return m == null || m.isEmpty();
    }
}
