package hmag.client.renderer;

import hmag.HMaG;
import hmag.client.model.SnowCanineModel;
import hmag.client.renderer.layers.SnowCanineEyesLayer;
import hmag.entity.SnowCanineEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowCanineRenderer extends AbstractGirlRenderer<SnowCanineEntity, SnowCanineModel<SnowCanineEntity>>
{
	private static final ResourceLocation TEX = new ResourceLocation(HMaG.MODID, "textures/entity/snow_canine.png");

	public SnowCanineRenderer(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn, new SnowCanineModel<>(), 0.5F);
		this.addLayer(new SnowCanineEyesLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(SnowCanineEntity entityIn)
	{
		return TEX;
	}
}