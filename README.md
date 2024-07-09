### Supported Plugins
BentoBox \
CrashClaim \
FabledSkyBlock \
FactionsUUID \
GriefDefender \
GriefPrevention \
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
![GitHub](https://img.shields.io/github/license/Xiao-MoMi/AntiGriefLib)
[![](https://jitpack.io/v/Xiao-MoMi/AntiGriefLib.svg)](https://jitpack.io/#Xiao-MoMi/AntiGriefLib)
[![CodeFactor](https://www.codefactor.io/repository/github/xiao-momi/antigrieflib/badge)](https://www.codefactor.io/repository/github/xiao-momi/antigrieflib) \
Gradle (kts)
```kotlin
repositories {
    maven("https://jitpack.io/")
}
```
```kotlin
dependencies {
    compileOnly("com.github.Xiao-MoMi:AntiGriefLib:{LATEST}")
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