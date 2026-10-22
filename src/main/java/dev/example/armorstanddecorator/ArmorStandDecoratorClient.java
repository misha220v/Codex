package dev.example.armorstanddecorator;

import net.fabricmc.api.ClientModInitializer;

public final class ArmorStandDecoratorClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ArmorStandInteractionHandler.register();
    }
}
