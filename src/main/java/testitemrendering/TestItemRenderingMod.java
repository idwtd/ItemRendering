package testitemrendering;

import testitemrendering.blocks.*;
import testitemrendering.items.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

/**
 * Created with IntelliJ IDEA.
 * User: Rick
 * Date: 26/08/13
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */

@Mod(modid=TestItemRenderingMod.MODID)
public class TestItemRenderingMod {
  public static final String MODID = "testitemrendering";

  // The instance of your mod that Forge uses.
  @Mod.Instance(MODID)
  public static TestItemRenderingMod instance;

  // custom items
  public final static Item itemLampshade = new ItemLampshade();
  public final static Item itemSmileyFace = new ItemSmileyFace();
  public final static Item itemNumberedFaces2 = new ItemNumberedFaces2();
  public final static Item itemNumberedFaces3 = new ItemNumberedFaces3();

  // custom blocks
  public final static Block blockPyramid = new BlockPyramid(Material.rock);
  public final static Block blockNumberedFaces1 = new BlockNumberedFaces1(Material.rock);
  public final static ItemBlock itemBlockNumberedFaces1 = new ItemBlockNumberedFaces1(blockNumberedFaces1);

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
    proxy.registerRenderers();
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    // Stub Method
  }

  private void addItemsToRegistries() {
    // for all items:
    // GameRegistry for registering the item
    // assets/testitemrendering/lang/en_US.lang for registering the name of the item
    // ClientProxy and MinecraftForgeClient.registerItemRenderer for custom item renderers

    GameRegistry.registerItem(itemSmileyFace, "smiley_face");
    GameRegistry.registerItem(itemLampshade, "lampshade");
    GameRegistry.registerItem(itemBlockNumberedFaces1, "numbered_faces_1");
    GameRegistry.registerItem(itemNumberedFaces2, "numbered_faces_2");
    GameRegistry.registerItem(itemNumberedFaces3, "numbered_faces_3");
  }

  private void addBlocksToRegistries() {
    // for all blocks:
    // GameRegistry for registering the block and associating an item with a block
    // assets/testitemrendering/lang/en_US.lang for registering the name of the block
    // ClientProxy and RenderingRegistry for custom block renderers

    GameRegistry.registerBlock(blockPyramid, ItemBlockPyramid.class, "pyramid");
    GameRegistry.registerBlock(blockNumberedFaces1, null, "numbered_faces_1");
  }

}
