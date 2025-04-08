package ru.dimaskama.cim.recipe;

import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import ru.dimaskama.cim.CustomItemModels;

public class ModRecipes {

    public static final RecipeSerializer<CustomItemModelSmithingRecipe> CUSTOM_ITEM_MODEL_SMITHING_SERIALIZER = new CustomItemModelSmithingRecipe.Serializer();

    public static void init() {
        Registry.register(Registries.RECIPE_SERIALIZER, CustomItemModels.id("smithing"), CUSTOM_ITEM_MODEL_SMITHING_SERIALIZER);
    }

    public static <B, T> PacketCodec<B, T> deprecatedRecipePacketCodec() {
        return PacketCodec.of((o1, o2) -> {
            throw new IllegalStateException("Recipe packet codecs are deprecated");
        }, o -> {
            throw new IllegalStateException("Recipe packet codecs are deprecated");
        });
    }

}
