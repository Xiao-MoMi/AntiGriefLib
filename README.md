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
ProtectionStones \
RedProtect \
Residence \
SaberFactions \
SuperiorSkyBlock \
Towny \
UltimateClaims \
UltimateClans \
USkyBlock \
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
// create a lib instance on plugin load
var lib = AntiGriefLib.builder(this)
                .silentLogs(true)
                .ignoreOP(true)
                .placeFlag(
                        Flag.builder("custom-place")
                            .defaultValue(false)
                            .displayName("Custom place")
                            .description(List.of("My custom place flag"))
                            .displayItem(new ItemStack(Material.STONE))
                            .build()
                )
                .addCompatibility(new MyCustomAntiGriefImpl())
                .build();

// init the lib after all the compatible plugins enabled
lib.init();

// use the api to check permissions
if (!lib.canPlace(player, location)) {
    player.sendMessage(Component.text("You can't place it here!"));
}
```