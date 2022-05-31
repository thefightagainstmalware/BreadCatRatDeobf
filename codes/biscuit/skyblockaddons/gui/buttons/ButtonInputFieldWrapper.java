/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiTextField
 *  org.apache.commons.lang3.StringUtils
 */
package codes.biscuit.skyblockaddons.gui.buttons;

import codes.biscuit.skyblockaddons.utils.ColorCode;
import codes.biscuit.skyblockaddons.utils.objects.UpdateCallback;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import org.apache.commons.lang3.StringUtils;

public class ButtonInputFieldWrapper
extends GuiButton {
    private GuiTextField textField;
    private String placeholderText;
    private UpdateCallback<String> textUpdated;

    public ButtonInputFieldWrapper(int x, int y, int w, int h, String buttonText, int maxLength, boolean focused, UpdateCallback<String> textUpdated) {
        this(x, y, w, h, buttonText, null, maxLength, focused, textUpdated);
    }

    public ButtonInputFieldWrapper(int x, int y, int w, int h, String buttonText, String placeholderText, int maxLength, boolean focused, UpdateCallback<String> textUpdated) {
        super(-1, x, y, buttonText);
        this.placeholderText = placeholderText;
        this.textUpdated = textUpdated;
        this.textField = new GuiTextField(-1, Minecraft.func_71410_x().field_71466_p, x, y, w, h);
        this.textField.func_146203_f(maxLength);
        this.textField.func_146195_b(focused);
        this.textField.func_146180_a(buttonText);
    }

    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        this.textField.func_146194_f();
        if (this.placeholderText != null && StringUtils.isEmpty((CharSequence)this.textField.func_146179_b())) {
            mc.field_71466_p.func_78276_b(this.placeholderText, this.field_146128_h + 4, this.field_146129_i + 3, ColorCode.DARK_GRAY.getColor());
        }
    }

    protected void keyTyped(char typedChar, int keyCode) {
        if (this.textField.func_146206_l()) {
            this.textField.func_146201_a(typedChar, keyCode);
        }
        this.textUpdated.onUpdate(this.textField.func_146179_b());
    }

    public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
        this.textField.func_146192_a(mouseX, mouseY, 0);
        return this.textField.func_146206_l();
    }

    public void updateScreen() {
        this.textField.func_146178_a();
    }

    public static void callKeyTyped(List<GuiButton> buttonList, char typedChar, int keyCode) {
        for (GuiButton button : buttonList) {
            if (!(button instanceof ButtonInputFieldWrapper)) continue;
            ((ButtonInputFieldWrapper)button).keyTyped(typedChar, keyCode);
        }
    }

    public static void callUpdateScreen(List<GuiButton> buttonList) {
        for (GuiButton button : buttonList) {
            if (!(button instanceof ButtonInputFieldWrapper)) continue;
            ((ButtonInputFieldWrapper)button).updateScreen();
        }
    }
}

