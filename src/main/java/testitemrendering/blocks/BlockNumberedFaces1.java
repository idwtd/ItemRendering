package testitemrendering.blocks;

import testitemrendering.TestItemRenderingMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

/*
  This class is used to demonstrate IItemRenderer for an ItemBlock.
  The Block (when placed) is drawn using the vanilla renderer - by overriding getIcon(int side, int metadata)
  The corresponding ItemBlock is rendered using an implementation of IItemRenderer
  Three classes are associated with this Block:
  1) BlockNumberedFaces1 which is the Block placed in the world
  2) ItemBlockNumberedFaces1 which is the Item corresponding to the Block
  3) ItemBlockNumberedFaces1Renderer, which is the IItemRenderer used to draw the Item.
*/

public class BlockNumberedFaces1 extends Block {

  private IIcon[] faceIcons;    // holds the icons for each face 0 - 5.

  public BlockNumberedFaces1(Material material) {
    super(material);
    this.setCreativeTab(CreativeTabs.tabBlock);
    this.setUnlocalizedName("numberedFaces1");
  }

  // A numbered icon for each face
  @Override
  public void registerIcons(IIconRegister iconRegister) {
    faceIcons = new IIcon[6];
    int i;
    for (i=0; i < faceIcons.length; ++i) {
      faceIcons[i] = iconRegister.registerIcon(TestItemRenderingMod.MODID+":NumberedFace"+i);
    }
  }

  @Override
  public IIcon getIcon(int side, int metadata) {
     if (side < 0 || side > faceIcons.length) side = 0;
     return faceIcons[side];
  }
}
