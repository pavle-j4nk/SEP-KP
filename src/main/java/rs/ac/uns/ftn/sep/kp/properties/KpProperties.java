package rs.ac.uns.ftn.sep.kp.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kp")
public class KpProperties {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url.endsWith("/"))
            url = url.substring(0, url.length() - 1); // remove / suffix

        this.url = url;
    }
}
