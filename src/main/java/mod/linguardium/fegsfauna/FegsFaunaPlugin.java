package mod.linguardium.fegsfauna;

import mcp.mobius.waila.api.*;
import net.minecraft.text.TranslatableText;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.world.World;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class FegsFaunaPlugin implements IWailaPlugin, IEntityComponentProvider,  IServerDataProvider<LivingEntity> {
    @Override
    public void appendHead(List<Text> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        Entity e= accessor.getEntity();
        if (e instanceof AnimalEntity) {
            CompoundTag tag = accessor.getServerData();
            if (tag != null && tag.contains("AnimalBreedingAge",3)) {
                int breedingAge = tag.getInt("AnimalBreedingAge");
                if (breedingAge < 0) {
                    tooltip.add(new TranslatableText("info.fegsfauna.integer",new TranslatableText("info.fegsfauna.grow_count").formatted(Formatting.WHITE), -breedingAge));
                }else{
                    tooltip.add(new TranslatableText("info.fegsfauna.integer",new TranslatableText("info.fegsfauna.breed_count").formatted(Formatting.RED), breedingAge));
                }
            }
        }
    }
    @Override
    public void appendServerData(CompoundTag tag, ServerPlayerEntity player, World world, LivingEntity entity) {
        if (entity instanceof AnimalEntity)
            tag.putInt("AnimalBreedingAge",(((AnimalEntity)entity).getBreedingAge()));
    }
    @Override 
    public void register(IRegistrar registrar) {
        registrar.registerComponentProvider(this,TooltipPosition.HEAD, AnimalEntity.class);
        registrar.registerEntityDataProvider(this, AnimalEntity.class);
    }
}