package testitemrendering;

import testitemrendering.blocks.*;
import testitemrendering.items.*;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

/**
 * Created with IntelliJ IDEA.
 * User: Rick
 * Date: 26/08/13
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */

@Mod(modid="TestItemRenderingMod", name="Test Item Rendering Mod", version="0.0.1")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class TestItemRenderingMod {

  // The instance of your mod that Forge uses.
  @Mod.Instance("TestItemRenderingMod")
  public static TestItemRenderingMod instance;

  // custom items
  private final static int STARTITEM = 5000;
  public final static Item itemLampshade = new ItemLampshade(STARTITEM);
  public final static Item itemSmileyFace = new ItemSmileyFace(STARTITEM+1);
  public final static Item itemNumberedFaces2 = new ItemNumberedFaces2(STARTITEM+2);
  public final static Item itemNumberedFaces3 = new ItemNumberedFaces3(STARTITEM+3);

  // custom blocks
  private final static int STARTBLOCK = 500;
  public final static Block blockPyramid = new BlockPyramid(STARTBLOCK, Material.rock);
  public final static Block blockNumberedFaces1 = new BlockNumberedFaces1(STARTBLOCK+1, Material.rock);
  public final static ItemBlock itemBlockNumberedFaces1 = new ItemBlockNumberedFaces1(STARTBLOCK+1-256);

  // custom itemrenderers
  private final static ItemLampshadeRenderer itemLampshadeRenderer = new ItemLampshadeRenderer();
  private final static ItemBlockNumberedFaces1Renderer itemBlockNumberedFaces1Renderer = new ItemBlockNumberedFaces1Renderer();
  private final static ItemNumberedFaces2Renderer itemNumberedFaces2Renderer = new ItemNumberedFaces2Renderer();
  private final static ItemNumberedFaces3Renderer itemNumberedFaces3Renderer = new ItemNumberedFaces3Renderer();

  // Says where the client and server 'proxy' code is loaded.
  @SidedProxy(clientSide="testitemrendering.client.ClientProxy", serverSide="testitemrendering.CommonProxy")
  public static CommonProxy proxy;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    // Stub Method
  }

  @EventHandler
  public void load(FMLInitializationEvent event) {
    addItemsToRegistries();
    addBlocksToRegistries();
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    // Stub Method
  }

  private void addItemsToRegistries() {
    // for all items:
    // LanguageRegistry for registering the name of the item
    // MinecraftForgeClient.registerItemRenderer for custom item renderers

    LanguageRegistry.addName(itemSmileyFace, "Smiley Face");
    LanguageRegistry.addName(itemLampshade, "Lampshade");
    LanguageRegistry.addName(itemNumberedFaces2, "Numbered Faces2 (2D Item)");
    LanguageRegistry.addName(itemNumberedFaces3, "Numbered Faces3 (3D Item)");

    MinecraftForgeClient.registerItemRenderer(itemLampshade.itemID, itemLampshadeRenderer);
    MinecraftForgeClient.registerItemRenderer(itemBlockNumberedFaces1.itemID, itemBlockNumberedFaces1Renderer);
    MinecraftForgeClient.registerItemRenderer(itemNumberedFaces2.itemID, itemNumberedFaces2Renderer);
    MinecraftForgeClient.registerItemRenderer(itemNumberedFaces3.itemID, itemNumberedFaces3Renderer);
  }

  private void addBlocksToRegistries() {
    // for all blocks:
    // GameRegistry for associating an item with a block
    // LanguageRegistry for registering the name of the block
    // RenderingRegistry for custom block renderers

    GameRegistry.registerBlock(blockPyramid, ItemBlockPyramid.class, "Pyramid");
    LanguageRegistry.addName(new ItemStack(blockPyramid), "Pyramid");
    RenderingRegistry.registerBlockHandler(new BlockPyramidRenderer());

    GameRegistry.registerBlock(blockNumberedFaces1, ItemBlockNumberedFaces1.class, "Numbered Faces1 (Block)");
    LanguageRegistry.addName(new ItemStack(blockNumberedFaces1), "Numbered Faces1 (Block)");
  }

}
