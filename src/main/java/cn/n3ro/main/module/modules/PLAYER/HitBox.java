package cn.n3ro.main.module.modules.PLAYER;

import java.util.Iterator;
import java.util.List;

import com.darkmagician6.eventapi.EventTarget;

import cn.n3ro.main.events.EventUpdate;
import cn.n3ro.main.module.Category;
import cn.n3ro.main.module.Module;
import cn.n3ro.main.utils.EntitySize;
import cn.n3ro.main.value.Numbers;
import cn.n3ro.main.value.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

public class HitBox extends Module {

	public static Numbers<Double> Width = new Numbers<Double>("Width", 0.6D, 0.0D, 8D, 0.1D);
	public static Numbers<Double> Height = new Numbers<Double>("Height", 1.8D, 0.0D, 8D, 0.1D);
	public static Option<Boolean> SelectMobs = new Option<Boolean>("SelectMobs", false);
	 public HitBox() {
	        super("HitBox", Category.PLAYER);
	        this.addValues(Width,Height,SelectMobs);
	    }
	    
	    @EventTarget
	    private void onUpdate(EventUpdate event) {
	    	if(SelectMobs.getValue()) {
	    		 Iterator worldentities = mc.theWorld.loadedEntityList.iterator();
	    		 List<Entity> entities = mc.theWorld.loadedEntityList;
	    		 while (true)
	    	        {
	    			 Object o;
	    			 do 
	    			 {
	    				 if (!worldentities.hasNext())
	    	                {
	    	                    return;
	    	                }

	    	                o = worldentities.next();
	    			 } 
	    	         while ( !(o instanceof EntityMob) && !(o instanceof EntityVillager) && !(o instanceof EntitySquid) && !(o instanceof EntityMagmaCube) && !(o instanceof EntityBat) && !(o instanceof EntitySlime) && !(o instanceof EntityGhast));
	    			 EntityLiving e = (EntityLiving)o;
	    			 float width = (float)(Width.getValue().floatValue());
	    			 float height = (float)(Height.getValue().floatValue());
	    			 setEntityBoundingBoxSize(e, width, height);
	    			 for(EntityPlayer player : getPlayersList()) {
	    					if(!check(player)) continue;
	    					setEntityBoundingBoxSize(player, width, height);
	    					
	    				}
	    	        }
	    	}else {
	    	for(EntityPlayer player : getPlayersList()) {
				if(!check(player)) continue;
				float width = (float)(Width.getValue().floatValue());
				float height = (float)(Height.getValue().floatValue());
				setEntityBoundingBoxSize(player, width, height);
			}
	    	
	    }
	    	
	    }
	    
	    @Override
	    public void onDisable() {
	    	
	    }
	    
	    public static void setEntityBoundingBoxSize(Entity entity, float width, float height) {
			EntitySize size = getEntitySize(entity);
			entity.width = size.width;
			entity.height = size.height;
			double d0 = (double) (width) / 2.0D;
			entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d0, entity.posY, entity.posZ - d0, entity.posX + d0,
					entity.posY + (double) height, entity.posZ + d0));
		}
		public static EntitySize getEntitySize(Entity entity) {
			EntitySize entitySize = new EntitySize(0.6F, 1.8F);
			
			if(entity instanceof EntitySpider) 
				entitySize = new EntitySize(1.4F, 0.9F);
			if(entity instanceof EntityBat) 
				entitySize = new EntitySize(0.5F, 0.9F);
			if(entity instanceof EntityChicken) 
				entitySize = new EntitySize(0.5F, 0.9F);
			if(entity instanceof EntityCow) 
				entitySize = new EntitySize(0.9F, 1.4F);
			if(entity instanceof EntitySheep) 
				entitySize = new EntitySize(0.9F, 1.4F);
			if(entity instanceof EntityEnderman) 
				entitySize = new EntitySize(0.6F, 2.9F);
			if(entity instanceof EntityGhast) 
				entitySize = new EntitySize(4.0F, 4.0F);
			if(entity instanceof EntityEndermite) 
				entitySize = new EntitySize(0.4F, 0.3F);
			if(entity instanceof EntityGiantZombie) 
				entitySize = new EntitySize(0.6F * 6.0F, 1.8F * 6.0F);
			if(entity instanceof EntityWolf) 
				entitySize = new EntitySize(0.6F, 0.85F);
			if(entity instanceof EntityGuardian) 
				entitySize = new EntitySize(0.85F, 0.85F);
			if(entity instanceof EntitySquid) 
				entitySize = new EntitySize(0.8F, 0.8F);
			if(entity instanceof EntityDragon) 
				entitySize = new EntitySize(16.0F, 8.0F);
			if(entity instanceof EntityRabbit) 
				entitySize = new EntitySize(0.4F, 0.5F);
			return entitySize;
		}
		
		public boolean check(EntityLivingBase entity) {
			if(entity instanceof EntityPlayerSP) { return false; }
			if(entity.isDead) { return false; }
			return true;
	    }
		
	    public static List<EntityPlayer> getPlayersList() {
			return Minecraft.getMinecraft().theWorld.playerEntities;
		}
}
