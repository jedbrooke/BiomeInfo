package bl4ckscor3.mod.biomeinfo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.io.*;

public class BiomeInfo implements ClientModInitializer
{

	int counter = 1;
	static final int INTERVAL = 100;
	BufferedWriter out;

	public BiomeInfo() {
		try {
			out = new BufferedWriter(new FileWriter("biomeslist.txt",true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onInitializeClient()
	{
		HudRenderCallback.EVENT.register((matrixStack, delta) -> {
			if(!MinecraftClient.getInstance().options.debugEnabled)
			{
				MinecraftClient mc = MinecraftClient.getInstance();

				if(mc.world != null)
				{
					BlockPos pos = mc.getCameraEntity().getBlockPos();

					if(mc.world.isInBuildLimit(pos))
					{
						Biome biome = mc.world.getBiome(pos);

						if(biome != null)
						{
							if (counter % INTERVAL == 0) {
								System.out.println(mc.player.getEntityWorld().toString());
								String biome_name = mc.world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome).toString();
								String entry = pos.getX() + "," + pos.getY() + "," + pos.getZ() + "," + biome_name;
								try{
									out.write(entry + "\n"s
									);
									out.flush();
								} catch (IOException e) {
									e.printStackTrace();
								}

								counter = 0;
							}
							counter++;
						}
					}
				}
			}
		});
	}
}
