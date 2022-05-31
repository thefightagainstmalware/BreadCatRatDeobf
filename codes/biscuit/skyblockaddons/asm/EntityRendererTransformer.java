/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.MethodNode
 */
package codes.biscuit.skyblockaddons.asm;

import codes.biscuit.skyblockaddons.asm.utils.InjectionHelper;
import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.asm.utils.TransformerField;
import codes.biscuit.skyblockaddons.asm.utils.TransformerMethod;
import codes.biscuit.skyblockaddons.tweaker.transformer.ITransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class EntityRendererTransformer
implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.EntityRenderer.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            if (InjectionHelper.matches(methodNode, TransformerMethod.getNightVisionBrightness)) {
                InjectionHelper.start().matchMethodHead().startCode().newInstance("codes/biscuit/skyblockaddons/asm/utils/ReturnValue").storeAuto(0).loadAuto(0).callStaticMethod("codes/biscuit/skyblockaddons/asm/hooks/EntityRendererHook", "onGetNightVisionBrightness", "(Lcodes/biscuit/skyblockaddons/asm/utils/ReturnValue;)V").loadAuto(0).invokeInstanceMethod("codes/biscuit/skyblockaddons/asm/utils/ReturnValue", "isCancelled", "()Z").startIfEqual().constantValue(1.0f).reeturn().endIf().endCode().finish();
                continue;
            }
            if (!InjectionHelper.matches(methodNode, TransformerMethod.updateCameraAndRender)) continue;
            InjectionHelper.start().matchingOwner(TransformerClass.Minecraft).matchingField(TransformerField.currentScreen).endCondition().setInjectionOffset(-2).addAnchorCondition(-6).matchingOwner(TransformerClass.EntityRenderer).matchingField(TransformerField.renderEndNanoTime).endCondition().injectCodeBefore().callStaticMethod("codes/biscuit/skyblockaddons/asm/hooks/EntityRendererHook", "onRenderScreenPre", "()V").endCode().finish();
        }
    }
}

