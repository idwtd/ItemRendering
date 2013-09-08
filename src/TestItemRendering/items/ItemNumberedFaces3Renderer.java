package TestItemRendering.items;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemNumberedFaces3Renderer implements IItemRenderer {

  static final boolean testflagColour = true;          // if true - render cube with each face a different colour
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
                helper == ItemRendererHelper.ENTITY_ROTATION ||
                helper == ItemRendererHelper.BLOCK_3D);
      }
      case EQUIPPED: {
        return (helper == ItemRendererHelper.BLOCK_3D || helper == ItemRendererHelper.EQUIPPED_BLOCK);
      }
      case EQUIPPED_FIRST_PERSON: {
        return helper == ItemRendererHelper.EQUIPPED_BLOCK;
      }
      case INVENTORY: {
        return helper == ItemRendererHelper.INVENTORY_BLOCK;
      }
      default: {
        return false;
      }
    }
  }

  private enum TransformationTypes {NONE, DROPPED, INVENTORY};

  @Override
  public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

    ItemNumberedFaces3 facesItem;

    if (item.getItem() instanceof ItemNumberedFaces3) {
      facesItem = (ItemNumberedFaces3)item.getItem();
    } else {  // the renderer was called using the wrong item -?
      if (!wrongRendererMsgWritten) {
        wrongRendererMsgWritten = true;
        System.out.println("ItemNumberFaces3Renderer.renderItem called with wrong Item:" + item.getDisplayName());
      }
      return;
    }

    Tessellator tessellator = Tessellator.instance;
    tessellator.startDrawingQuads();

    // adjust rendering space to match what caller expects
    TransformationTypes transformationToBeUndone = TransformationTypes.NONE;
    switch (type) {
      case EQUIPPED:
      case EQUIPPED_FIRST_PERSON: {
        break; // caller expects us to render over [0,0,0] to [1,1,1], no transformation necessary
      }
      case INVENTORY: {  // caller expects [-0.5, -0.5, -0.5] to [0.5, 0.5, 0.5]
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        break;
      }
      case ENTITY: {
        // translate our coordinates and scale so that [0,0,0] to [1,1,1] translates to the [-0.25, -0.25, -0.25] to [0.25, 0.25, 0.25] expected by the caller.
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        transformationToBeUndone = TransformationTypes.DROPPED;
        break;
      }
      default:
        break; // never here
    }

    // xpos face blue
    Icon icon = facesItem.getFace(5);
    tessellator.setNormal(1.0F, 0.0F, 0.0F);
    if (testflagColour) tessellator.setColorOpaque(0, 0, 255);
    tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
    tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(1.0, 1.0, 1.0, (double)icon.getMinU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(1.0, 0.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV());

    // xneg face purple
    icon = facesItem.getFace(4);
    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
    if (testflagColour) tessellator.setColorOpaque(255, 0, 255);
    tessellator.addVertexWithUV(0.0, 0.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV());
    tessellator.addVertexWithUV(0.0, 1.0, 1.0, (double)icon.getMaxU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());

    // zneg face white
    icon = facesItem.getFace(2);
    tessellator.setNormal(0.0F, 0.0F, -1.0F);
    if (testflagColour) tessellator.setColorOpaque(255, 255, 255);
    tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
    tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());

    // zpos face green
    icon = facesItem.getFace(3);
    tessellator.setNormal(0.0F, 0.0F, -1.0F);
    if (testflagColour) tessellator.setColorOpaque(0, 255, 0);
    tessellator.addVertexWithUV(1.0, 0.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV());
    tessellator.addVertexWithUV(1.0, 1.0, 1.0, (double)icon.getMaxU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(0.0, 1.0, 1.0, (double)icon.getMinU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(0.0, 0.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV());

    // ypos face red
    icon = facesItem.getFace(1);
    tessellator.setNormal(0.0F, 1.0F, 0.0F);
    if (testflagColour) tessellator.setColorOpaque(255, 0, 0);
    tessellator.addVertexWithUV(1.0, 1.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV());
    tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(0.0, 1.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV());

    // yneg face yellow
    icon = facesItem.getFace(0);
    tessellator.setNormal(0.0F, -1.0F, 0.0F);
    if (testflagColour) tessellator.setColorOpaque(255, 255, 0);
    tessellator.addVertexWithUV(0.0, 0.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV());
    tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
    tessellator.addVertexWithUV(1.0, 0.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV());

    tessellator.draw();

    switch (transformationToBeUndone) {
      case NONE: {
        break;
      }
      case DROPPED: {
        GL11.glTranslatef(0.5F, 0.5F, 0.0F);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        break;
      }
      case INVENTORY: {
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        break;
      }
      default:
        break;
    }
  }
}