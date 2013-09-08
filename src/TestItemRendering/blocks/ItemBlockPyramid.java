package TestItemRendering.blocks;

import TestItemRendering.TestItemRenderingMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.Icon;

/*
This is the item that corresponds to BlockPyramid.
 */
public class ItemBlockPyramid extends ItemBlock {

  public ItemBlockPyramid(int id) {
    super(id);
    this.setMaxStackSize(64);
    this.setCreativeTab(CreativeTabs.tabBlock);
    this.setUnlocalizedName("ItemBlockPyramid");
  }
  @Override
  public int getMetadata(int damageValue){
    return damageValue;
  }

  @Override
  public Icon getIconFromDamage(int damage)
  {
    return TestItemRenderingMod.blockPyramid.getIcon(0, damage);
  }

}

