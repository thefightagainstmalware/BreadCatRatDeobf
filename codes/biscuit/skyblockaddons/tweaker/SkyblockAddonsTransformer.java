/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ArrayListMultimap
 *  com.google.common.collect.Multimap
 *  net.minecraft.launchwrapper.IClassTransformer
 *  net.minecraft.launchwrapper.Launch
 *  net.minecraftforge.fml.relauncher.FMLRelaunchLog
 *  org.apache.commons.lang3.mutable.MutableInt
 *  org.apache.logging.log4j.Level
 *  org.objectweb.asm.ClassReader
 *  org.objectweb.asm.ClassVisitor
 *  org.objectweb.asm.ClassWriter
 *  org.objectweb.asm.tree.ClassNode
 */
package codes.biscuit.skyblockaddons.tweaker;

import codes.biscuit.skyblockaddons.asm.EffectRendererTransformer;
import codes.biscuit.skyblockaddons.asm.EntityLivingBaseTransformer;
import codes.biscuit.skyblockaddons.asm.EntityPlayerSPTransformer;
import codes.biscuit.skyblockaddons.asm.EntityPlayerTransformer;
import codes.biscuit.skyblockaddons.asm.EntityRendererTransformer;
import codes.biscuit.skyblockaddons.asm.FontRendererTransformer;
import codes.biscuit.skyblockaddons.asm.GuiChestTransformer;
import codes.biscuit.skyblockaddons.asm.GuiContainerTransformer;
import codes.biscuit.skyblockaddons.asm.GuiDisconnectedTransformer;
import codes.biscuit.skyblockaddons.asm.GuiIngameCustomTransformer;
import codes.biscuit.skyblockaddons.asm.GuiIngameMenuTransformer;
import codes.biscuit.skyblockaddons.asm.GuiNewChatTransformer;
import codes.biscuit.skyblockaddons.asm.GuiScreenTransformer;
import codes.biscuit.skyblockaddons.asm.InventoryPlayerTransformer;
import codes.biscuit.skyblockaddons.asm.ItemArmorDispenserBehaviorTransformer;
import codes.biscuit.skyblockaddons.asm.ItemArmorTransformer;
import codes.biscuit.skyblockaddons.asm.ItemTransformer;
import codes.biscuit.skyblockaddons.asm.MinecraftTransformer;
import codes.biscuit.skyblockaddons.asm.ModelEndermanTransformer;
import codes.biscuit.skyblockaddons.asm.MouseHelperTransformer;
import codes.biscuit.skyblockaddons.asm.PlayerControllerMPTransformer;
import codes.biscuit.skyblockaddons.asm.RenderEndermanTransformer;
import codes.biscuit.skyblockaddons.asm.RenderGlobalTransformer;
import codes.biscuit.skyblockaddons.asm.RenderItemTransformer;
import codes.biscuit.skyblockaddons.asm.RenderManagerTransformer;
import codes.biscuit.skyblockaddons.asm.RendererLivingEntityTransformer;
import codes.biscuit.skyblockaddons.asm.SoundManagerTransformer;
import codes.biscuit.skyblockaddons.asm.TileEntityEnderChestRendererTransformer;
import codes.biscuit.skyblockaddons.asm.WorldClientTransformer;
import codes.biscuit.skyblockaddons.asm.WorldVertexBufferUploaderTransformer;
import codes.biscuit.skyblockaddons.tweaker.transformer.ITransformer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.FMLRelaunchLog;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.logging.log4j.Level;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class SkyblockAddonsTransformer
implements IClassTransformer {
    private static final boolean deobfuscated = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
    private static final boolean usingNotchMappings = !deobfuscated;
    private final Multimap<String, ITransformer> transformerMap = ArrayListMultimap.create();

    public SkyblockAddonsTransformer() {
        this.registerTransformer(new TileEntityEnderChestRendererTransformer());
        this.registerTransformer(new MouseHelperTransformer());
        this.registerTransformer(new EntityPlayerSPTransformer());
        this.registerTransformer(new EntityRendererTransformer());
        this.registerTransformer(new SoundManagerTransformer());
        this.registerTransformer(new RenderManagerTransformer());
        this.registerTransformer(new PlayerControllerMPTransformer());
        this.registerTransformer(new MinecraftTransformer());
        this.registerTransformer(new ItemTransformer());
        this.registerTransformer(new GuiScreenTransformer());
        this.registerTransformer(new GuiContainerTransformer());
        this.registerTransformer(new GuiChestTransformer());
        this.registerTransformer(new GuiNewChatTransformer());
        this.registerTransformer(new RendererLivingEntityTransformer());
        this.registerTransformer(new GuiDisconnectedTransformer());
        this.registerTransformer(new GuiIngameMenuTransformer());
        this.registerTransformer(new FontRendererTransformer());
        this.registerTransformer(new RenderItemTransformer());
        this.registerTransformer(new EntityLivingBaseTransformer());
        this.registerTransformer(new InventoryPlayerTransformer());
        this.registerTransformer(new GuiIngameCustomTransformer());
        this.registerTransformer(new RenderEndermanTransformer());
        this.registerTransformer(new ModelEndermanTransformer());
        this.registerTransformer(new RenderGlobalTransformer());
        this.registerTransformer(new EffectRendererTransformer());
        this.registerTransformer(new WorldClientTransformer());
        this.registerTransformer(new EntityPlayerTransformer());
        this.registerTransformer(new ItemArmorDispenserBehaviorTransformer());
        this.registerTransformer(new ItemArmorTransformer());
        this.registerTransformer(new WorldVertexBufferUploaderTransformer());
    }

    private void registerTransformer(ITransformer transformer) {
        for (String cls : transformer.getClassName()) {
            this.transformerMap.put((Object)cls, (Object)transformer);
        }
    }

    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        Collection transformers = this.transformerMap.get((Object)transformedName);
        if (transformers.isEmpty()) {
            return bytes;
        }
        ClassReader reader = new ClassReader(bytes);
        ClassNode node = new ClassNode();
        reader.accept((ClassVisitor)node, 8);
        MutableInt classWriterFlags = new MutableInt(3);
        transformers.forEach(transformer -> {
            this.log(Level.INFO, String.format("Applying transformer %s on %s...", transformer.getClass().getName(), transformedName));
            transformer.transform(node, transformedName);
            if (transformer instanceof FontRendererTransformer && transformedName.equals("club.sk1er.patcher.hooks.FontRendererHook")) {
                classWriterFlags.setValue(0);
            }
        });
        ClassWriter writer = new ClassWriter(classWriterFlags.getValue().intValue());
        try {
            node.accept((ClassVisitor)writer);
        }
        catch (Throwable ex) {
            this.log(Level.ERROR, "An exception occurred while transforming " + transformedName);
            ex.printStackTrace();
            this.outputBytecode(transformedName, writer);
            return bytes;
        }
        this.outputBytecode(transformedName, writer);
        return writer.toByteArray();
    }

    private void outputBytecode(String transformedName, ClassWriter writer) {
        try {
            File bytecodeDirectory = new File("bytecode");
            if (!bytecodeDirectory.exists()) {
                return;
            }
            File bytecodeOutput = new File(bytecodeDirectory, transformedName + ".class");
            if (!bytecodeOutput.exists()) {
                bytecodeOutput.createNewFile();
            }
            FileOutputStream os = new FileOutputStream(bytecodeOutput);
            os.write(writer.toByteArray());
            os.close();
        }
        catch (Exception ex) {
            this.log(Level.ERROR, "An error occurred writing bytecode of transformed class \"" + transformedName + "\" to file");
            ex.printStackTrace();
        }
    }

    public void log(Level level, String message) {
        String name = "SkyblockAddons/" + this.getClass().getSimpleName();
        FMLRelaunchLog.log((String)name, (Level)level, (String)((SkyblockAddonsTransformer.isDeobfuscated() ? "" : "[" + name + "] ") + message), (Object[])new Object[0]);
    }

    public static boolean isDeobfuscated() {
        return deobfuscated;
    }

    public static boolean isUsingNotchMappings() {
        return usingNotchMappings;
    }
}

