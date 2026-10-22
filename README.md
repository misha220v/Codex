# Armor Stand Decorator

Client-side Fabric mod for Minecraft 1.21.1. While in Creative mode, right-clicking
an armor stand puts a diamond helmet into the selected hotbar slot and immediately
uses it on the stand.

The server remains authoritative: it must allow Creative inventory actions and
normal armor-stand interaction. The mod does not bypass permissions, distance
checks, protected regions, or server-side validation.

## Build

Requires Java 21 and Gradle 8.8+:

```sh
gradle build
```

The remapped mod jar is written to `build/libs/`.

To change the equipped item, replace `Items.DIAMOND_HELMET` in
`ArmorStandInteractionHandler` with another registered item.
