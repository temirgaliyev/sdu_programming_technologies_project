package source.Additionally;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class UTF8Control extends ResourceBundle.Control {
    /**
     * Control Class that helps to show Cyrillic Letters.
     * Only one line below added, the rest code is copied from default implementation
     *
     * @param baseName {@link String}
     * @param locale {@link Locale}
     * @param format {@link String}
     * @param loader {@link ClassLoader}
     * @param reload boolean
     * @return new {@link ResourceBundle}
     * @throws IllegalAccessException error from parent class{@link java.util.ResourceBundle.Control}
     * @throws InstantiationException error from parent class{@link java.util.ResourceBundle.Control}
     * @throws IOException error from parent class{@link java.util.ResourceBundle.Control}
     */
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        // The below is a copy of the default implementation.
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        ResourceBundle bundle = null;
        InputStream stream = null;
        if (reload) {
            URL url = loader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    connection.setUseCaches(false);
                    stream = connection.getInputStream();
                }
            }
        } else {
            stream = loader.getResourceAsStream(resourceName);
        }
        if (stream != null) {
            try {
                // Only this line is changed to make it to read properties files as UTF-8.
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
            } finally {
                stream.close();
            }
        }
        return bundle;
    }
}
