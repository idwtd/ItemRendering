package testitemrendering.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;

/*
This is the item that corresponds to BlockNumberedFaces1
 */
public class ItemBlockNumberedFaces1 extends ItemBlock {

  public ItemBlockNumberedFaces1(Block block) {
    super(block);
    this.setMaxStackSize(64);
  }

  @Override
  public IIcon getIconFromDamage(int side) {
    // hijack this method to return the icon for each face
    if (side < 0 || side > 5) side = 0;
    return blockInstance.getBlockTextureFromSide(side);
  }
}

