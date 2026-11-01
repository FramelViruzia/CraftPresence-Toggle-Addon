package dev.craftpresencetoggle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Stores the on/off switch in its own file (config/craftpresence-toggle.json), apart from craftpresence.json,
 * so a launcher that rewrites CraftPresence's config does not reset it.
 */
public final class ToggleConfig {
    private static final Logger LOG = LoggerFactory.getLogger("CraftPresence Toggle");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("craftpresence-toggle.json");

    // Read every tick on the client thread, written from the Mod Menu screen or /cptoggle
    private static volatile boolean enabled = load();

    private ToggleConfig() {
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
        save();
    }

    private static boolean load() {
        if (!Files.exists(FILE)) {
            return true;
        }
        try (Reader reader = Files.newBufferedReader(FILE)) {
            Data data = GSON.fromJson(reader, Data.class);
            return data == null || data.enabled;
        } catch (IOException | RuntimeException e) {
            LOG.warn("Could not read {}, keeping Discord status on", FILE, e);
            return true;
        }
    }

    private static void save() {
        Data data = new Data();
        data.enabled = enabled;
        try (Writer writer = Files.newBufferedWriter(FILE)) {
            GSON.toJson(data, writer);
        } catch (IOException e) {
            LOG.error("Could not save {}", FILE, e);
        }
    }

    private static final class Data {
        boolean enabled = true;
    }
}
