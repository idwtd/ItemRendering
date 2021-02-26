package testitemrendering.items;

import testitemrendering.TestItemRenderingMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemSmileyFace extends Item {
  public ItemSmileyFace() {
    setMaxStackSize(64);
    setCreativeTab(CreativeTabs.tabMisc);
    setUnlocalizedName("smileyFace");
  }

  @Override
  public void registerIcons(IIconRegister iconRegister)
  {
    itemIcon = iconRegister.registerIcon(TestItemRenderingMod.MODID+":SmileyFace");
  }

}