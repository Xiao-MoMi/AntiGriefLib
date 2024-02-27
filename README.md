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
USkyBlock \
WorldGuard 

### Use this project as dependency
![GitHub](https://img.shields.io/github/license/Xiao-MoMi/AntiGriefLib)
[![](https://jitpack.io/v/Xiao-MoMi/AntiGriefLib.svg)](https://jitpack.io/#Xiao-MoMi/AntiGriefLib) \
Gradle (kts)
```
repositories {
    maven("https://jitpack.io/")
}
```
```
dependencies {
    compileOnly("com.github.Xiao-MoMi:AntiGriefLib:{LATEST}")
}
```

### API Guide
```java
// create a lib instance
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