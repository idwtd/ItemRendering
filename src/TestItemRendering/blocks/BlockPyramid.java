package testitemrendering.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

/*
This class is used to demonstrate ISimpleBlockRenderingHandler for drawing a 3D block, both as a block in the world and as an item.
 */

public class BlockPyramid extends Block {

  public BlockPyramid(int id, Material material) {
    super(id, material);
    this.setCreativeTab(CreativeTabs.tabBlock);
    this.setUnlocalizedName("BlockPyramid");
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }

  // This icon is never actually displayed because we always render it using BlockPyramidRenderer, however when you destroy a block the
  //   fragments that fly everywhere are derived from the block Icon, so we use something sensible..
  @Override
  public void registerIcons(IconRegister iconRegister) {
    blockIcon = Block.blockLapis.getIcon(0, 0);
  }

  @Override
  public boolean renderAsNormalBlock() {
    return false;
  }

  @Override
  public int getRenderType() {
    return BlockPyramidRenderer.myRenderID;
  }

}
