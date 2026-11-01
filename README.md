# CraftPresence Toggle Addon

Adds a simple on/off button to [CraftPresence](https://modrinth.com/mod/craftpresence)'s General Settings to toggle its Discord status.

CraftPresence's `/cp shutdown` only lasts until the game restarts, and its config has no master switch. This addon adds one, without editing CraftPresence itself.

## Usage

Open CraftPresence's settings (through Mod Menu or its keybind) and go to **General Settings**. The top row, above Client ID, is **Enable Discord Status**:

- **Enable**: CraftPresence works as normal.
- **Disable**: nothing is sent to Discord, and any active status is cleared right away.

Click the button to switch. The choice is saved to `config/craftpresence-toggle.json`, apart from CraftPresence's own config, so it stays after a restart even if a launcher rewrites `craftpresence.json`.

## Requirements

| | Version |
|---|---|
| Minecraft | 1.21.1 |
| Fabric Loader | 0.15.0 or newer |
| CraftPresence | 2.7.0 or newer (built and checked against 2.7.1) |
| UniLib | 1.2.0 or newer |
| Java | 21 |

Fabric API is not needed.

The addon hooks into CraftPresence's internal code, so a future CraftPresence update that changes that code can stop the game from starting. If that happens, remove this addon until it is updated.

## Building

```
gradlew build
```

The jar is written to `build/libs/`. CraftPresence and UniLib are downloaded from Modrinth's Maven for compiling only. They are not bundled.

## License

MIT, see [LICENSE](LICENSE). CraftPresence and UniLib are by CDAGaming, also under MIT.
