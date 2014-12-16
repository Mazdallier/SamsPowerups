package com.anon10w1z.craftPP.dispenser;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class DispenserDamageSource extends DamageSource
{
    public DispenserDamageSource()
    {
        super("mob");
    }

    public IChatComponent func_151519_b(EntityLivingBase p_151519_1_)
    {
        String s = "death.attack." + this.damageType;
        String s1 = s + ".item";
        return StatCollector.canTranslate(s1) ? new ChatComponentTranslation(s1, new Object[] {p_151519_1_.func_145748_c_(), new ChatComponentText("dispenser")}): new ChatComponentTranslation(s, new Object[] {p_151519_1_.func_145748_c_(), new ChatComponentText("dispenser")});
    }

    /**
     * Return whether this damage source will have its damage amount scaled based on the current difficulty.
     */
    public boolean isDifficultyScaled()
    {
        return false;
    }
}