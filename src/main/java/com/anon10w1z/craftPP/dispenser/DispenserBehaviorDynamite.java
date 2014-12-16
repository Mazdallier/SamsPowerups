package com.anon10w1z.craftPP.dispenser;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

import com.anon10w1z.craftPP.entities.EntityDynamite;

public final class DispenserBehaviorDynamite extends BehaviorProjectileDispense
{
    /**
     * Returns the projectile entity spawned by this dispenser behavior.
     */
    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition)
    {
        return new EntityDynamite(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
    }
}
