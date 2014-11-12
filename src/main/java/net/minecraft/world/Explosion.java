package net.minecraft.world;

//TODO: 1>  how to we make explosions drop 100% of items instead of a fraction?
	
//TODO 2> make explosions NOT kill entity items, such as from a broken chest

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

public class Explosion
{ 
    public boolean isFlaming; 
    public boolean isSmoking = true;
    private int range = 16;
    private Random explosionRNG = new Random();
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize; 
    public List affectedBlockPositions = new ArrayList();
    private Map hashMap = new HashMap();
    private static final String __OBFID = "CL_00000134";

    public Explosion(World pWorld, Entity pWho, double pX, double pY, double pZ, float pSize)
    {
        this.worldObj = pWorld;
        this.exploder = pWho;
        this.explosionSize = pSize;
        this.explosionX = pX;
        this.explosionY = pY;
        this.explosionZ = pZ;
    }
 
    public void doExplosionA()
    {
        float oldSize = this.explosionSize;
        HashSet hashset = new HashSet();
        int ix;
        int iy;
        int iz;
        double eX;
        double eY;
        double eZ;

        for (ix = 0; ix < this.range; ++ix)
        {
            for (iy = 0; iy < this.range; ++iy)
            {
                for (iz = 0; iz < this.range; ++iz)
                {
                    if (ix == 0 || ix == this.range - 1 || iy == 0 || iy == this.range - 1 || iz == 0 || iz == this.range - 1)
                    {
                        double dx = (double)((float)ix / ((float)this.range - 1.0F) * 2.0F - 1.0F);
                        double dy = (double)((float)iy / ((float)this.range - 1.0F) * 2.0F - 1.0F);
                        double dz = (double)((float)iz / ((float)this.range - 1.0F) * 2.0F - 1.0F);
                        double pythag = Math.sqrt(dx * dx + dy * dy + dz * dz);
                        dx /= pythag;
                        dy /= pythag;
                        dz /= pythag;
                        float f1 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        eX = this.explosionX;
                        eY = this.explosionY;
                        eZ = this.explosionZ;

                        for (float steps = 0.3F; f1 > 0.0F; f1 -= steps * 0.75F)
                        {
                            int eXloc = MathHelper.floor_double(eX);
                            int eYloc = MathHelper.floor_double(eY);
                            int eZloc = MathHelper.floor_double(eZ);
                            Block block = this.worldObj.getBlock(eXloc, eYloc, eZloc);

                            if (block.getMaterial() != Material.air)
                            {
                                float f3 = this.exploder != null ? this.exploder.func_145772_a(this, this.worldObj, eXloc, eYloc, eZloc, block) : block.getExplosionResistance(this.exploder, worldObj, eXloc, eYloc, eZloc, explosionX, explosionY, explosionZ);
                                f1 -= (f3 + 0.3F) * steps;
                            }

                            if (f1 > 0.0F && (this.exploder == null || this.exploder.func_145774_a(this, this.worldObj, eXloc, eYloc, eZloc, block, f1)))
                            {
                                hashset.add(new ChunkPosition(eXloc, eYloc, eZloc));
                            }

                            eX += dx * (double)steps;
                            eY += dy * (double)steps;
                            eZ += dz * (double)steps;
                        }
                    }
                }
            }
        }

        this.affectedBlockPositions.addAll(hashset);
        this.explosionSize *= 2.0F;
        ix = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0D);
        iy = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0D);
        iz = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0D);
        int i2 = MathHelper.floor_double(this.explosionY + (double)this.explosionSize + 1.0D);
        int l = MathHelper.floor_double(this.explosionZ - (double)this.explosionSize - 1.0D);
        int j2 = MathHelper.floor_double(this.explosionZ + (double)this.explosionSize + 1.0D);
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getBoundingBox((double)ix, (double)iz, (double)l, (double)iy, (double)i2, (double)j2));
        Vec3 vec3 = Vec3.createVectorHelper(this.explosionX, this.explosionY, this.explosionZ);
        double entityDistance;
        double distance;
        for (int i1 = 0; i1 < list.size(); ++i1)
        {
            Entity entity = (Entity)list.get(i1);
            entityDistance = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;

            if (entityDistance <= 1.0D)
            {
                eX = entity.posX - this.explosionX;
                eY = entity.posY + (double)entity.getEyeHeight() - this.explosionY;
                eZ = entity.posZ - this.explosionZ;
                distance = (double)MathHelper.sqrt_double(eX * eX + eY * eY + eZ * eZ);

                if (distance != 0.0D)
                {
                	if(entity instanceof EntityItem)
                	{
                		System.out.println("explosion affecting entity item");
                		//TODO: continue; should make the entity item take no damage..?	 or change attackEntityFrom below
                	}
                	
                    eX /= distance;
                    eY /= distance;
                    eZ /= distance;
                    double blockDensity = (double)this.worldObj.getBlockDensity(vec3, entity.boundingBox);
                    double scale = (1.0D - entityDistance) * blockDensity;
                    entity.attackEntityFrom(DamageSource.setExplosionSource(this), (float)((int)((scale * scale + scale) / 2.0D * 8.0D * (double)this.explosionSize + 1.0D)));
                    double d8 = EnchantmentProtection.func_92092_a(entity, scale);
                    entity.motionX += eX * d8;
                    entity.motionY += eY * d8;
                    entity.motionZ += eZ * d8;

                    if (entity instanceof EntityPlayer)
                    {
                        this.hashMap.put((EntityPlayer)entity, Vec3.createVectorHelper(eX * scale, eY * scale, eZ * scale));
                    }
                }
            }
        }

        this.explosionSize = oldSize;
    }
 
    public void doExplosionB(boolean parFlag)
    {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

        if (this.explosionSize >= 2.0F && this.isSmoking)
        {
            this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }
        else
        {
            this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }

        Iterator iterator;
        ChunkPosition chunkposition;
        int chX;
        int chY;
        int chZ;
        Block block;

        if (this.isSmoking)
        {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext())
            {
                chunkposition = (ChunkPosition)iterator.next();
                chX = chunkposition.chunkPosX;
                chY = chunkposition.chunkPosY;
                chZ = chunkposition.chunkPosZ;
                block = this.worldObj.getBlock(chX, chY, chZ);

                if (parFlag)
                {
                    double d0 = (double)((float)chX + this.worldObj.rand.nextFloat());
                    double d1 = (double)((float)chY + this.worldObj.rand.nextFloat());
                    double d2 = (double)((float)chZ + this.worldObj.rand.nextFloat());
                    double d3 = d0 - this.explosionX;
                    double d4 = d1 - this.explosionY;
                    double d5 = d2 - this.explosionZ;
                    double d6 = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5D / (d6 / (double)this.explosionSize + 0.1D);
                    d7 *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    this.worldObj.spawnParticle("explode", (d0 + this.explosionX * 1.0D) / 2.0D, (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, d3, d4, d5);
                    this.worldObj.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);
                }

                if (block.getMaterial() != Material.air)
                {
                    if (block.canDropFromExplosion(this))
                    {        
//it used to use the ration  1.0F / this.explosionSize to destroy a fraction of the items, based on expl size 
                        block.dropBlockAsItemWithChance(this.worldObj, chX, chY, chZ, this.worldObj.getBlockMetadata(chX, chY, chZ), 1.0F, 0);
                    }

                    block.onBlockExploded(this.worldObj, chX, chY, chZ, this);
                }
            }
        } 
        if (this.isFlaming)
        {
            iterator = this.affectedBlockPositions.iterator(); 
            while (iterator.hasNext())
            {
                chunkposition = (ChunkPosition)iterator.next();
                chX = chunkposition.chunkPosX;
                chY = chunkposition.chunkPosY;
                chZ = chunkposition.chunkPosZ;
                block = this.worldObj.getBlock(chX, chY, chZ);
                Block block1 = this.worldObj.getBlock(chX, chY - 1, chZ);

                if (block.getMaterial() == Material.air && block1.func_149730_j() && this.explosionRNG.nextInt(3) == 0)
                {
                    this.worldObj.setBlock(chX, chY, chZ, Blocks.fire);
                }
            }
        }
    }

    public EntityLivingBase getExplosivePlacedBy()
    {
        return this.exploder == null ? null : (this.exploder instanceof EntityTNTPrimed ? ((EntityTNTPrimed)this.exploder).getTntPlacedBy() : (this.exploder instanceof EntityLivingBase ? (EntityLivingBase)this.exploder : null));
    }
    public Map func_77277_b()
    {
        return this.hashMap;
    }
}
