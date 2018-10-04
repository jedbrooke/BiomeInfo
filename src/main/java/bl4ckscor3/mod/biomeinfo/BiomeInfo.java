package bl4ckscor3.mod.biomeinfo;

import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid=BiomeInfo.MODID, name=BiomeInfo.NAME, version=BiomeInfo.VERSION, acceptedMinecraftVersions="[" + BiomeInfo.MC_VERSION + "]", clientSideOnly=true)
public class BiomeInfo
{
	public static final String MODID = "biomeinfo";
	public static final String NAME = "BiomeInfo";
	public static final String VERSION = "v1.1";
	public static final String MC_VERSION = "1.12";
	@Instance(MODID)
	public BiomeInfo instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModMetadata meta = event.getModMetadata();

		meta.authorList = Arrays.asList(new String[]{"bl4ckscor3"});
		meta.autogenerated = false;
		meta.description = "Adds a customizable text to the top left corner saying what biome the player is in.";
		meta.modId = MODID;
		meta.name = NAME;
		meta.version = VERSION;

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent event)
	{
		if(Configuration.enabled && event.getType() == ElementType.TEXT && !Minecraft.getMinecraft().gameSettings.showDebugInfo)
		{
			Minecraft mc = Minecraft.getMinecraft();
			BlockPos pos = new BlockPos(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().getEntityBoundingBox().minY, mc.getRenderViewEntity().posZ);

			if(mc.world != null)
			{
				if(mc.world.isBlockLoaded(pos) && pos.getY() >= 0 && pos.getY() < 256)
				{
					Chunk chunk = mc.world.getChunk(pos);

					if(!chunk.isEmpty())
					{
						GlStateManager.pushMatrix();
						GlStateManager.scale(Configuration.scale, Configuration.scale, Configuration.scale);
						mc.fontRenderer.drawString(chunk.getBiome(pos, mc.world.getBiomeProvider()).getBiomeName(), Configuration.posX, Configuration.posY, Configuration.iColor, Configuration.textShadow);
						GlStateManager.popMatrix();
					}
				}
			}
		}
	}
}
