/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.fml.client.registry.ClientRegistry
 *  org.apache.commons.lang3.ArrayUtils
 */
package codes.biscuit.skyblockaddons.misc;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.apache.commons.lang3.ArrayUtils;

public class SkyblockKeyBinding {
    private final KeyBinding keyBinding;
    private final String name;
    private final int defaultKeyCode;
    private final Message message;
    private boolean registered = false;
    private boolean isFirstRegistration = true;
    private int previousKeyCode = 999;

    public SkyblockKeyBinding(String name, int defaultKey, Message message) {
        this.name = name;
        this.defaultKeyCode = defaultKey;
        this.message = message;
        this.keyBinding = new KeyBinding("key.skyblockaddons." + this.getName(), this.getDefaultKeyCode(), "SkyblockAddons");
    }

    public int getKeyCode() {
        return this.keyBinding.func_151463_i();
    }

    public boolean isKeyDown() {
        if (this.registered) {
            return this.keyBinding.func_151470_d();
        }
        return false;
    }

    public boolean isPressed() {
        if (this.registered) {
            return this.keyBinding.func_151468_f();
        }
        return false;
    }

    public void register() {
        if (this.registered) {
            SkyblockAddons.getLogger().error("Tried to register a key binding with the name \"" + this.name + "\" which is already registered.");
            return;
        }
        ClientRegistry.registerKeyBinding((KeyBinding)this.keyBinding);
        if (this.isFirstRegistration) {
            this.isFirstRegistration = false;
        } else if (this.previousKeyCode < 256) {
            this.keyBinding.func_151462_b(this.defaultKeyCode);
            KeyBinding.func_74508_b();
        }
        this.registered = true;
    }

    public void deRegister() {
        if (this.registered) {
            int index = ArrayUtils.indexOf((Object[])Minecraft.func_71410_x().field_71474_y.field_74324_K, (Object)this.keyBinding);
            if (index == -1) {
                SkyblockAddons.getLogger().error("Keybinding was registered but no longer exists in the registry. Something else must have removed it. This shouldn't happen; please inform an SBA developer.");
                this.registered = false;
                return;
            }
            Minecraft.func_71410_x().field_71474_y.field_74324_K = (KeyBinding[])ArrayUtils.remove((Object[])Minecraft.func_71410_x().field_71474_y.field_74324_K, (int)index);
            this.previousKeyCode = this.keyBinding.func_151463_i();
            this.keyBinding.func_151462_b(0);
            KeyBinding.func_74508_b();
            this.registered = false;
        } else {
            SkyblockAddons.getLogger().error("Tried to de-register a key binding with the name \"" + this.name + "\" which wasn't registered.");
        }
    }

    public KeyBinding getKeyBinding() {
        return this.keyBinding;
    }

    public String getName() {
        return this.name;
    }

    public int getDefaultKeyCode() {
        return this.defaultKeyCode;
    }

    public Message getMessage() {
        return this.message;
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public boolean isFirstRegistration() {
        return this.isFirstRegistration;
    }

    public int getPreviousKeyCode() {
        return this.previousKeyCode;
    }
}

