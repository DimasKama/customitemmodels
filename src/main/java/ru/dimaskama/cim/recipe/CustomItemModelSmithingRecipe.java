package ru.dimaskama.cim.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import ru.dimaskama.cim.recipe.ingredient.AnyItemIngredient;

import java.util.List;
import java.util.Optional;

public class CustomItemModelSmithingRecipe implements SmithingRecipe {

    private final Ingredient addition;

    private CustomItemModelSmithingRecipe(Ingredient addition) {
        this.addition = addition;
    }

    @Override
    public boolean matches(SmithingRecipeInput input, World world) {
        return input.template().isEmpty()
                && !input.base().isEmpty()
                && addition.test(input.addition());
    }

    @Override
    public ItemStack craft(SmithingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        ComponentChanges.Builder builder = ComponentChanges.builder();
        Text customName = input.addition().get(DataComponentTypes.CUSTOM_NAME);
        if (customName != null) {
            builder.add(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(List.of(), List.of(), List.of(customName.getString()), List.of()));
        } else {
            builder.remove(DataComponentTypes.CUSTOM_MODEL_DATA);
        }
        ItemStack result = input.base().copy();
        result.applyChanges(builder.build());
        return result;
    }

    @Override
    public RecipeSerializer<CustomItemModelSmithingRecipe> getSerializer() {
        return ModRecipes.CUSTOM_ITEM_MODEL_SMITHING_SERIALIZER;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.NONE;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public Optional<Ingredient> template() {
        return Optional.empty();
    }

    @Override
    public Optional<Ingredient> base() {
        return Optional.of(AnyItemIngredient.INSTANCE.toVanilla());
    }

    @Override
    public Optional<Ingredient> addition() {
        return Optional.of(addition);
    }

    public static class Serializer implements RecipeSerializer<CustomItemModelSmithingRecipe> {

        private static final MapCodec<CustomItemModelSmithingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("addition").forGetter(r -> r.addition)
        ).apply(instance, CustomItemModelSmithingRecipe::new));
        private static final PacketCodec<RegistryByteBuf, CustomItemModelSmithingRecipe> PACKET_CODEC = ModRecipes.deprecatedRecipePacketCodec();

        @Override
        public MapCodec<CustomItemModelSmithingRecipe> codec() {
            return CODEC;
        }

        @Override
        @Deprecated
        public PacketCodec<RegistryByteBuf, CustomItemModelSmithingRecipe> packetCodec() {
            return PACKET_CODEC;
        }

    }

}
