package utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.*;

public class File {
    public static String readResource(final InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    public static Resource[] getResourceInPath(final String path)
            throws IOException {
        return new PathMatchingResourcePatternResolver().getResources(path);
    }
}