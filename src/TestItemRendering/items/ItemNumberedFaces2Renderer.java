package TestItemRendering.items;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemNumberedFaces2Renderer implements IItemRenderer {

  static final boolean testflagColour = true;          // if true - render cube with each face a different colour
  static final boolean testflagIsFull3D = true;       // if true - rotate the icon in EQUIPPED 3rd person view - eg sword.  If false - don't rotate, eg pumpkin pie
  static boolean wrongRendererMsgWritten = false;      // if renderer is called with the wrong item, it prints an error msg once only, by setting this flag to stop subsequent prints

  @Override
  public boolean handleRenderType(ItemStack item, ItemRenderType type) {
    switch (type) {
      case ENTITY:
      case EQUIPPED:
      case EQUIPPED_FIRST_PERSON:
      case INVENTORY:
        return true;
      default:
        return false;
    }
  }

  @Override
  public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
    switch (type) {
      case ENTITY: {
        return (helper == ItemRendererHelper.ENTITY_BOBBING ||
                helper == ItemRendererHelper.ENTITY_ROTATION
                ); // not helper == ItemRendererHelper.BLOCK_3D
      }
      case EQUIPPED: {
        return false;
        // not (helper == ItemRendererHelper.BLOCK_3D || helper == ItemRendererHelper.EQUIPPED_BLOCK);
      }
      case EQUIPPED_FIRST_PERSON: {
        return false;
        // not (helper == ItemRendererHelper.EQUIPPED_BLOCK);
      }
      case INVENTORY: {
        return false;
        // not (helper == ItemRendererHelper.INVENTORY_BLOCK);
      }
      default: {
        return false;
      }
    }
  }

  @Override
  public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

    if (type == ItemRenderType.INVENTORY) {
      drawAs2D(type, item);
    } else {
      drawAsSlice(type, item);
    }

    return;
  }

  // draw the inventory icon as flat 2D
  // caller expects you to render over [0,0,0] to [16, 16, 0]
  private void drawAs2D(ItemRenderType type, ItemStack item) {

    Tessellator tessellator = Tessellator.instance;
    tessellator.startDrawingQuads();

    Icon icon1 = item.getItem().getIconFromDamage(0);

    double minU1 = (double)icon1.getMinU();
    double minV1 = (double)icon1.getMinV();
    double maxU1 = (double)icon1.getMaxU();
    double maxV1 = (double)icon1.getMaxV();

    tessellator.addVertexWithUV(16.0, 16.0, 0.0, maxU1, maxV1);
    tessellator.addVertexWithUV(16.0,  0.0, 0.0, maxU1, minV1);
    tessellator.addVertexWithUV( 0.0,  0.0, 0.0, minU1, minV1);
    tessellator.addVertexWithUV( 0.0, 16.0, 0.0, minU1, maxV1);
    tessellator.draw();
  }

  private enum TransformationTypes {NONE, DROPPED, INFRAME};

  private void drawAsSlice(ItemRenderType type, ItemStack item) {
    ItemNumberedFaces2 facesItem;

    if (item.getItem() instanceof ItemNumberedFaces2) {
      facesItem = (ItemNumberedFaces2)item.getItem();
    } else {  // the renderer was called using the wrong item -?
      if (!wrongRendererMsgWritten) {
        wrongRendererMsgWritten = true;
        System.out.println("ItemNumberFaces2Renderer.renderItem called with wrong Item:" + item.getDisplayName());
      }
      return;
    }

    final float THICKNESS = 0.0625F;

    Tessellator tessellator = Tessellator.instance;
    tessellator.startDrawingQuads();

    // adjust rendering space to match what caller expects
    TransformationTypes transformationToBeUndone = TransformationTypes.NONE;
    switch (type) {
      case EQUIPPED:
      case EQUIPPED_FIRST_PERSON: {
        break; // caller expects us to render over [0,0,0] to [1,1,-THICKNESS], no transformation necessary
      }
      case ENTITY: {
        // translate our coordinates so that [0,0,0] to [1,1,1] translates to the [-0.5, 0.0, 0.0] to [0.5, 1.0, 1.0] expected by the caller.
        if (RenderItem.renderInFrame) {
          transformationToBeUndone = TransformationTypes.INFRAME;   // must undo the transformation when we're finished rendering
          GL11.glTranslatef(-0.5F, -0.3F, THICKNESS/2.0F);
        } else {
          transformationToBeUndone = TransformationTypes.DROPPED;   // must undo the transformation when we're finished rendering
          GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
        }
        break;
      }
      case INVENTORY: {
        break;
      }
      default:
        break; // never here
    }

    // xpos face blue
    Icon icon = facesItem.getFace(5);
    tessellator.setNormal(1.0F, 0.0F, 0.0F);
    if (testflagColour) tessellator.setColorOpaque(0, 0, 255);
    tessellator.addVertexWithUV(1.0, 0.0, -THICKNESS, (double)icon.getMaxU(), (double)icon.getMaxV());
    tessellator.addVertexWithUV(1.0, 1.0, -THICKNESS, (double)icon.getMaxU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());

    // xneg face purple
    icon = facesItem.getFace(4);
    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
    if (testflagColour) tessellator.setColorOpaque(255, 0, 255);
    tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
    tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(0.0, 1.0, -THICKNESS, (double)icon.getMinU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(0.0, 0.0, -THICKNESS, (double)icon.getMinU(), (double)icon.getMaxV());

    // zneg face white
    icon = facesItem.getFace(2);
    tessellator.setNormal(0.0F, 0.0F, -1.0F);
    if (testflagColour) tessellator.setColorOpaque(255, 255, 255);
    tessellator.addVertexWithUV(0.0, 0.0, -THICKNESS, (double)icon.getMaxU(), (double)icon.getMaxV());
    tessellator.addVertexWithUV(0.0, 1.0, -THICKNESS, (double)icon.getMaxU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(1.0, 1.0, -THICKNESS, (double)icon.getMinU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(1.0, 0.0, -THICKNESS, (double)icon.getMinU(), (double)icon.getMaxV());

    // zpos face green
    icon = facesItem.getFace(3);
    tessellator.setNormal(0.0F, 0.0F, -1.0F);
    if (testflagColour) tessellator.setColorOpaque(0, 255, 0);
    tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
    tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());

    // ypos face red
    icon = facesItem.getFace(1);
    tessellator.setNormal(0.0F, 1.0F, 0.0F);
    if (testflagColour) tessellator.setColorOpaque(255, 0, 0);
    tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
    tessellator.addVertexWithUV(1.0, 1.0, -THICKNESS, (double)icon.getMaxU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(0.0, 1.0, -THICKNESS, (double)icon.getMinU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());

    // yneg face yellow
    icon = facesItem.getFace(0);
    tessellator.setNormal(0.0F, -1.0F, 0.0F);
    if (testflagColour) tessellator.setColorOpaque(255, 255, 0);
    tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
    tessellator.addVertexWithUV(0.0, 0.0, -THICKNESS, (double)icon.getMaxU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(1.0, 0.0, -THICKNESS, (double)icon.getMinU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());

    tessellator.draw();

    switch (transformationToBeUndone) {
      case NONE: {
        break;
      }
      case DROPPED: {
        GL11.glTranslatef(0.5F, 0.0F, 0.0F);
        break;
      }
      case INFRAME: {
        GL11.glTranslatef(0.5F, 0.3F, -THICKNESS/2.0F);
        break;
      }
      default:
        break;
    }
 }


}