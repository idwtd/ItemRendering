package testitemrendering.items;

import testitemrendering.TestItemRenderingMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

// The Lampshade demonstrates how an Item can be rendered in 3D using a custom renderer implementing IItemRenderer

public class ItemLampshade extends Item {
  public ItemLampshade() {
    setMaxStackSize(64);
    setCreativeTab(CreativeTabs.tabMisc);
    setUnlocalizedName("lampShade");
  }

// this icon is never used because the custom ItemLampshadeRenderer is invoked instead
  @Override
  public void registerIcons(IIconRegister iconRegister)
  {
    itemIcon = iconRegister.registerIcon(TestItemRenderingMod.MODID+":Error");
  }

//  Lampshade uses the Block texture sheet for rendering instead of the Item texture sheet
  @Override
  public int getSpriteNumber() {
    return 0;
  }
}