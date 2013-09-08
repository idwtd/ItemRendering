package TestItemRendering.blocks;

import TestItemRendering.TestItemRenderingMod;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.Icon;

/*
This is the item that corresponds to BlockNumberedFaces1
 */
public class ItemBlockNumberedFaces1 extends ItemBlock {

  public ItemBlockNumberedFaces1(int id) {
    super(id);
    this.setMaxStackSize(64);
    this.setCreativeTab(CreativeTabs.tabBlock);
    this.setUnlocalizedName("ItemBlockNumberedFaces1");
  }

  @Override
  public Icon getIconFromDamage(int side) {
    // hijack this method to return the icon for each face
    if (side < 0 || side > 5) side = 0;
    return Block.blocksList[this.getBlockID()].getBlockTextureFromSide(side);
  }
}

