### Supported Plugins
BentoBox \
CrashClaim \
Dominion \
FabledSkyBlock \
FactionsUUID \
GriefDefender \
GriefPrevention \
hClaims\
HuskClaims \
HuskTowns \
IridiumSkyBlock \
KingdomsX \
Landlord \
Lands \
PlotSquared \
PreciousStones \
ProtectionStones \
RedProtect \
Residence \
SaberFactions \
SuperiorSkyBlock \
Towny \
UltimateClaims \
UltimateClans \
WorldGuard \
XClaim

### Use this project as dependency
Gradle (kts)
```kotlin
repositories {
    maven("https://repo.momirealms.net/releases/")
}
```
```kotlin
dependencies {
    implementation("net.momirealms:antigrieflib:0.16")
}
```

### API Guide
```java
// Create a lib instance on plugin enable
var lib = AntiGriefLib.builder(JavaPlugin)
                .silentLogs(true)
                .ignoreOP(true)
                .addCompatibility(new MyCustomAntiGriefImpl())
                .build();

// use the api to check permissions
if (!lib.canPlace(player, location)) {
    player.sendMessage(Component.text("You can't place it here!"));
}
```