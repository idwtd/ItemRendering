package TestItemRendering.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;

// This Item shows how to use IItemRenderer to render Items in 3D.  Unlike ItemBlockNumberedFaces1, this Item does not have an associated Block.

public class ItemNumberedFaces3 extends Item {

  private Icon [] faceIcons;

  public ItemNumberedFaces3(int id) {
    super(id);
    setMaxStackSize(64);
    setCreativeTab(CreativeTabs.tabMisc);
    setUnlocalizedName("ItemNumberedFaces3");
  }

  // A numbered icon for each face
  @Override
  public void registerIcons(IconRegister iconRegister) {
    faceIcons = new Icon[6];
    int i;
    for (i=0; i < faceIcons.length; ++i) {
      faceIcons[i] = iconRegister.registerIcon("TestItemRendering:NumberedFace"+i);
    }

    this.itemIcon = faceIcons[3];   // make the default icon (2D) equal the "3" face
  }

  public Icon getFace(int face) {
    if (face < 0 || face >= faceIcons.length) face = 0;
    return faceIcons[face];
  }
}