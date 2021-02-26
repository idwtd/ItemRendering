package testitemrendering.client;

import testitemrendering.CommonProxy;
import testitemrendering.TestItemRenderingMod;
import testitemrendering.blocks.*;
import testitemrendering.items.*;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

  // custom itemrenderers
  private final static ItemLampshadeRenderer itemLampshadeRenderer = new ItemLampshadeRenderer();
  private final static ItemBlockNumberedFaces1Renderer itemBlockNumberedFaces1Renderer = new ItemBlockNumberedFaces1Renderer();
  private final static ItemNumberedFaces2Renderer itemNumberedFaces2Renderer = new ItemNumberedFaces2Renderer();
  private final static ItemNumberedFaces3Renderer itemNumberedFaces3Renderer = new ItemNumberedFaces3Renderer();

  @Override
  public void registerRenderers() {
    MinecraftForgeClient.registerItemRenderer(TestItemRenderingMod.itemLampshade, itemLampshadeRenderer);
    MinecraftForgeClient.registerItemRenderer(TestItemRenderingMod.itemBlockNumberedFaces1, itemBlockNumberedFaces1Renderer);
    MinecraftForgeClient.registerItemRenderer(TestItemRenderingMod.itemNumberedFaces2, itemNumberedFaces2Renderer);
    MinecraftForgeClient.registerItemRenderer(TestItemRenderingMod.itemNumberedFaces3, itemNumberedFaces3Renderer);

    RenderingRegistry.registerBlockHandler(new BlockPyramidRenderer());
  }

}
