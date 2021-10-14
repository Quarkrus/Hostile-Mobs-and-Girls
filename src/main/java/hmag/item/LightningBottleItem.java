package hmag.item;

import hmag.entity.projectile.LightningBottleEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class LightningBottleItem extends Item
{
	public LightningBottleItem(Item.Properties builder)
	{
		super(builder);
		DispenserBlock.registerBehavior(this, new ProjectileDispenseBehavior()
		{
			@Override
			protected ProjectileEntity getProjectile(World worldIn, IPosition position, ItemStack stackIn)
			{
				return Util.make(new LightningBottleEntity(worldIn, position.x(), position.y(), position.x()), (e) -> {
					e.setItem(stackIn);
				});
			}

			@Override
			protected float getUncertainty()
			{
				return super.getUncertainty() * 0.5F;
			}

			@Override
			protected float getPower()
			{
				return super.getPower() * 1.25F;
			}
		});
	}

	@Override
	public boolean isFoil(ItemStack stack)
	{
		return true;
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack stack = playerIn.getItemInHand(handIn);
		worldIn.playSound((PlayerEntity)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.EXPERIENCE_BOTTLE_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		playerIn.getCooldowns().addCooldown(this, 20);

		if (!worldIn.isClientSide)
		{
			LightningBottleEntity bottleentity = new LightningBottleEntity(worldIn, playerIn);
			bottleentity.setItem(stack);
			bottleentity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, -20.0F, 0.7F, 1.0F);
			worldIn.addFreshEntity(bottleentity);
		}

		playerIn.awardStat(Stats.ITEM_USED.get(this));

		if (!playerIn.abilities.instabuild)
		{
			stack.shrink(1);
		}

		return ActionResult.sidedSuccess(stack, worldIn.isClientSide);
	}
}