package com.iheartradio.m3u8.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TrackInfo {
    public final float duration;
    public final String title;
    public final Map<String, String> extraProperties;

    public TrackInfo(float duration, String title) {
        this(duration, title, "");
    }

    public TrackInfo(float duration, String title, String extraProperties) {
        this.duration = duration;
        this.title = title;
        this.extraProperties = parseProperties(cleanupProperties(extraProperties));
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, title, extraProperties);
    }

    private static String cleanupProperties(String extraProperties) {
        return extraProperties == null ? "" : extraProperties.trim().replaceAll("\\s{2}", " ");
    }

    private static Map<String, String> parseProperties(String extraProperties) {
        HashMap<String, String> accumulator = new HashMap<>();
        int nextEquals = 0;
        int nextKvIndex = 0;
        do {
            nextEquals = extraProperties.indexOf("=\"", nextKvIndex);
            if (nextEquals == -1) {
                break;
            }

            int quoteOpen = nextEquals + 2;
            int quoteClose = extraProperties.indexOf("\"", quoteOpen);
            if (quoteClose == -1) {
                break;
            }

            String key = extraProperties.substring(nextKvIndex, nextEquals);
            String value = extraProperties.substring(quoteOpen, quoteClose);
            accumulator.put(key, value);
            nextKvIndex = quoteClose + 2;
        } while (nextKvIndex < extraProperties.length());
        return accumulator;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TrackInfo)) {
            return false;
        }

        TrackInfo other = (TrackInfo) o;
        
        return this.duration == other.duration &&
                Objects.equals(this.title, other.title) &&
                Objects.equals(this.extraProperties, other.extraProperties);
    }

}
