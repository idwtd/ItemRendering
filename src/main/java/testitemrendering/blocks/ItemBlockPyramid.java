package testitemrendering.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;

/*
This is the item that corresponds to BlockPyramid.
 */
public class ItemBlockPyramid extends ItemBlock {

  public ItemBlockPyramid(Block block) {
    super(block);
    this.setMaxStackSize(64);
  }
  @Override
  public int getMetadata(int damageValue){
    return damageValue;
  }

  @Override
  public IIcon getIconFromDamage(int damage)
  {
    return blockInstance.getIcon(0, damage);
  }

}

