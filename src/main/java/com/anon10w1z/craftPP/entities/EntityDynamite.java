package com.anon10w1z.craftPP.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDynamite extends EntityThrowable
{
	/**
	 * The size of the explosion of the dynamite
	 */
	private float explosionSize;
	
    public EntityDynamite(World par1World)
    {
        super(par1World);
    }
	
    public EntityDynamite(World par1World, EntityLivingBase par2EntityLivingBase, float explosionSize)
    {
        super(par1World, par2EntityLivingBase);
        this.explosionSize = explosionSize;
    }

    public EntityDynamite(World par1World, double par2, double par4, double par6, float explosionSize)
    {
        super(par1World, par2, par4, par6);
        this.explosionSize = explosionSize;
    }

    /**
     * Used by the dispenser, fires with the default explosionSize
     * @param world - The world
     * @param x - IPosition.getX()
     * @param y - IPosition.getY()
     * @param z - IPosition.getZ()
     */
    public EntityDynamite(World world, double x, double y, double z) 
    {
    	this(world, x, y, z, 2.0F);
    }
    
    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionSize, true);
        this.setDead();
    }
}
