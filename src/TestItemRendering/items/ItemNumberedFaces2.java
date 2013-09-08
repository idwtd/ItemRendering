package TestItemRendering.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;

// This class demonstrates custom rendering similar to normal items, using IItemRenderer

public class ItemNumberedFaces2 extends Item {

  private Icon [] faceIcons;

  public ItemNumberedFaces2(int id) {
    super(id);
    setMaxStackSize(64);
    setCreativeTab(CreativeTabs.tabMisc);
    setUnlocalizedName("ItemNumberedFaces2");
  }

  // A numbered icon for each face
  @Override
  public void registerIcons(IconRegister iconRegister) {
    faceIcons = new Icon[6];
    int i;
    for (i=0; i < faceIcons.length; ++i) {
      faceIcons[i] = iconRegister.registerIcon("TestItemRendering:NumberedFace"+i);
    }

    this.itemIcon = faceIcons[2];   // make the default icon (2D) equal the "2" face
  }

  public Icon getFace(int face) {
    if (face < 0 || face >= faceIcons.length) face = 0;
    return faceIcons[face];
  }

  @Override
  public boolean isFull3D() {
    return ItemNumberedFaces2Renderer.testflagIsFull3D;      // rotates weapon in hand eg like sword
  }
}