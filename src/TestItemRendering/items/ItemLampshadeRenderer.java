package TestItemRendering.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemLampshadeRenderer implements IItemRenderer {

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
        return (helper == ItemRendererHelper.BLOCK_3D ||
                helper == ItemRendererHelper.EQUIPPED_BLOCK);
      }
      case EQUIPPED_FIRST_PERSON: {
        return (helper == ItemRendererHelper.EQUIPPED_BLOCK);
      }
      case INVENTORY: {
        return (helper == ItemRendererHelper.INVENTORY_BLOCK);
      }
      default: {
        return false;
      }
    }
  }

  private enum TransformationTypes {NONE, DROPPED, INVENTORY, THIRDPERSONEQUIPPED};

  @Override
  public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

    Tessellator tessellator = Tessellator.instance;
    tessellator.startDrawingQuads();

    // adjust rendering space to match what caller expects
    TransformationTypes transformationToBeUndone = TransformationTypes.NONE;
    switch (type) {
      case EQUIPPED: {  // backface culling is off so we need to turn it back on to render the lampshade correctly, since our faces are single-sided.
                        // Normally this is not necessary even for transparent
                        // objects, unless you don't want to see the inside (back face) of the opposite side of the cube when you look through the cube
        GL11.glEnable(GL11.GL_CULL_FACE);
        transformationToBeUndone = TransformationTypes.THIRDPERSONEQUIPPED;
        break;
      }

      case EQUIPPED_FIRST_PERSON: {
        break; // caller expects us to render over [0,0,0] to [1,1,1], no transformation necessary
      }
      case INVENTORY: {  // caller expects [-0.5, -0.5, -0.5] to [0.5, 0.5, 0.5]
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        transformationToBeUndone = TransformationTypes.INVENTORY;
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

    // we need to use setNormal in order to get the lighting correct, otherwise the faces will all look dark.
    // for cubes this easy because the normal points in the direction of the x,y, or z axis
    // for our lampshade it's harder - the normalised cross product of two edges of each face
    //   for example, for the face pointing south-east-ish the normal is [0.8944, 0, 0.4472]
    // http://www.wolframalpha.com/input/?i=%5B0%2C1%2C0%5D+cross+product+%5B-0.5%2C+0%2C+1%5D
    final float FACE_X_NORMAL = 0.8944F;
    final float FACE_Z_NORMAL = 0.4472F;

    // Triangular Prism, draw the outside in wood
    // ItemLampshade.getSpriteNumber() has been overridden to return 0, which lets us use the block texture instead of an item texture
    Icon icon1 = Block.wood.getIcon(0, 0);
    double minU1 = (double)icon1.getMinU();
    double minV1 = (double)icon1.getMinV();
    double maxU1 = (double)icon1.getMaxU();
    double maxV1 = (double)icon1.getMaxV();

    // South-east face
    tessellator.setNormal(FACE_X_NORMAL, 0, FACE_Z_NORMAL);
    tessellator.addVertexWithUV(1.0, 0.0, 0.0, maxU1, maxV1);
    tessellator.addVertexWithUV(1.0, 1.0, 0.0, maxU1, minV1);
    tessellator.addVertexWithUV(0.5, 1.0, 1.0, minU1, minV1);
    tessellator.addVertexWithUV(0.5, 0.0, 1.0, minU1, maxV1);

    // South-west face
    tessellator.setNormal(-FACE_X_NORMAL, 0, FACE_Z_NORMAL);
    tessellator.addVertexWithUV(0.5, 0.0, 1.0, minU1, maxV1);
    tessellator.addVertexWithUV(0.5, 1.0, 1.0, minU1, minV1);
    tessellator.addVertexWithUV(0.0, 1.0, 0.0, maxU1, minV1);
    tessellator.addVertexWithUV(0.0, 0.0, 0.0, maxU1, maxV1);

    // north face
    tessellator.setNormal(0.0F, 0.0F, -1.0F);
    tessellator.addVertexWithUV(0.0, 0.0, 0.0, minU1, maxV1);
    tessellator.addVertexWithUV(0.0, 1.0, 0.0, minU1, minV1);
    tessellator.addVertexWithUV(1.0, 1.0, 0.0, maxU1, minV1);
    tessellator.addVertexWithUV(1.0, 0.0, 0.0, maxU1, maxV1);

    // Triangular Prism, draw the inside in gold

    Icon icon2 = Block.blockGold.getIcon(0, 0);
    double minU2 = (double)icon2.getMinU();
    double minV2 = (double)icon2.getMinV();
    double maxU2 = (double)icon2.getMaxU();
    double maxV2 = (double)icon2.getMaxV();

    // inside of south-east face
    tessellator.setNormal(-FACE_X_NORMAL, 0, -FACE_Z_NORMAL);
    tessellator.addVertexWithUV(0.5, 0.0, 1.0, minU2, maxV2);
    tessellator.addVertexWithUV(0.5, 1.0, 1.0, minU2, minV2);
    tessellator.addVertexWithUV(1.0, 1.0, 0.0, maxU2, minV2);
    tessellator.addVertexWithUV(1.0, 0.0, 0.0, maxU2, maxV2);

    // inside of south-west face
    tessellator.setNormal(FACE_X_NORMAL, 0, -FACE_Z_NORMAL);
    tessellator.addVertexWithUV(0.0, 0.0, 0.0, minU2, maxV2);
    tessellator.addVertexWithUV(0.0, 1.0, 0.0, minU2, minV2);
    tessellator.addVertexWithUV(0.5, 1.0, 1.0, maxU2, minV2);
    tessellator.addVertexWithUV(0.5, 0.0, 1.0, maxU2, maxV2);

    // inside of north face
    tessellator.setNormal(0.0F, 0.0F, 1.0F);
    tessellator.addVertexWithUV(1.0, 0.0, 0.0, maxU2, maxV2);
    tessellator.addVertexWithUV(1.0, 1.0, 0.0, maxU2, minV2);
    tessellator.addVertexWithUV(0.0, 1.0, 0.0, minU2, minV2);
    tessellator.addVertexWithUV(0.0, 0.0, 0.0, minU2, maxV2);

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
      case THIRDPERSONEQUIPPED: {
        GL11.glDisable(GL11.GL_CULL_FACE);
      }
      default:
        break;
    }
  }
}