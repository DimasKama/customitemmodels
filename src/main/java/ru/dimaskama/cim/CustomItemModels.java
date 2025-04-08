package ru.dimaskama.cim;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dimaskama.cim.recipe.ModRecipes;
import ru.dimaskama.cim.recipe.ingredient.ModIngredients;

public class CustomItemModels implements ModInitializer {

    public static final String MOD_ID = "custom-item-models";
    public static final String MOD_NAMESPACE = "cim";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModContainer modContainer = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow();
        ModIngredients.init();
        ModRecipes.init();
        if (!ResourceManagerHelper.registerBuiltinResourcePack(id("nametag_smithing_recipe"), modContainer, ResourcePackActivationType.NORMAL)) {
            LOGGER.warn("Failed to register default CIM datapack");
        }
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_NAMESPACE, path);
    }

}