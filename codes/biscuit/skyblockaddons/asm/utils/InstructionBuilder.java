/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Type
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.JumpInsnNode
 *  org.objectweb.asm.tree.LabelNode
 *  org.objectweb.asm.tree.LdcInsnNode
 *  org.objectweb.asm.tree.LocalVariableNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.TypeInsnNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package codes.biscuit.skyblockaddons.asm.utils;

import codes.biscuit.skyblockaddons.asm.utils.InjectionHelper;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class InstructionBuilder {
    private static final InstructionBuilder INSTANCE = new InstructionBuilder();
    private MethodNode methodNode;
    private int firstUnusedLocalVariableIndex;
    private InsnList instructions;
    private VariableType currentType;
    private Map<Integer, VariableType> localVariableTypes;
    private LinkedList<LabelNode> branchStack;

    public static InstructionBuilder start(MethodNode methodNode) {
        InstructionBuilder.INSTANCE.instructions = new InsnList();
        InstructionBuilder.INSTANCE.methodNode = methodNode;
        InstructionBuilder.INSTANCE.firstUnusedLocalVariableIndex = INSTANCE.getFirstUnusedVariableIndex();
        InstructionBuilder.INSTANCE.currentType = VariableType.VOID;
        InstructionBuilder.INSTANCE.localVariableTypes = new HashMap<Integer, VariableType>();
        InstructionBuilder.INSTANCE.branchStack = new LinkedList();
        return INSTANCE;
    }

    public InstructionBuilder newInstance(String clazz) {
        return this.newInstance(clazz, "()V");
    }

    public InstructionBuilder newInstance(String clazz, String constructor) {
        this.instructions.add((AbstractInsnNode)new TypeInsnNode(187, clazz));
        this.instructions.add((AbstractInsnNode)new InsnNode(89));
        this.instructions.add((AbstractInsnNode)new MethodInsnNode(183, clazz, "<init>", constructor, false));
        this.currentType = VariableType.OBJECT;
        return this;
    }

    public InstructionBuilder storeAuto(int relativeVariableNumber) {
        return this.store(this.firstUnusedLocalVariableIndex + relativeVariableNumber);
    }

    public InstructionBuilder loadAuto(int relativeVariableNumber) {
        return this.load(this.firstUnusedLocalVariableIndex + relativeVariableNumber);
    }

    public InstructionBuilder storeAuto(VariableType variableType, int relativeVariableNumber) {
        return this.store(variableType, this.firstUnusedLocalVariableIndex + relativeVariableNumber);
    }

    public InstructionBuilder loadAuto(VariableType variableType, int relativeVariableNumber) {
        return this.load(variableType, this.firstUnusedLocalVariableIndex + relativeVariableNumber);
    }

    public InstructionBuilder store(int variableNumber) {
        VariableType variableType = this.currentType;
        if (variableType == null || variableType == VariableType.VOID) {
            throw new IllegalArgumentException("There is no variable to store!");
        }
        return this.store(variableType, variableNumber);
    }

    public InstructionBuilder load(int variableNumber) {
        VariableType variableType = this.localVariableTypes.get(variableNumber);
        if (variableType == null || variableType == VariableType.VOID) {
            throw new IllegalArgumentException("The variable type is not yet known for this variable. Please use load(VariableType, int) instead!");
        }
        return this.load(variableType, variableNumber);
    }

    public InstructionBuilder load(VariableType variableType, int variableNumber) {
        this.instructions.add((AbstractInsnNode)new VarInsnNode(variableType.getOpcode(21), variableNumber));
        this.localVariableTypes.put(variableNumber, variableType);
        this.currentType = variableType;
        return this;
    }

    private InstructionBuilder store(VariableType variableType, int variableNumber) {
        this.instructions.add((AbstractInsnNode)new VarInsnNode(variableType.getOpcode(54), variableNumber));
        this.localVariableTypes.put(variableNumber, variableType);
        this.currentType = VariableType.VOID;
        return this;
    }

    public InstructionBuilder callStaticMethod(String owner, String method, String descriptor) {
        return this.callStaticMethod(owner, method, descriptor, false);
    }

    public InstructionBuilder callStaticMethod(String owner, String method, String descriptor, boolean isInterface) {
        return this.invoke(184, owner, method, descriptor, isInterface);
    }

    public InstructionBuilder invokeInstanceMethod(String owner, String method, String descriptor) {
        return this.invokeInstanceMethod(owner, method, descriptor, false);
    }

    public InstructionBuilder invokeInstanceMethod(String owner, String method, String descriptor, boolean isInterface) {
        return this.invoke(182, owner, method, descriptor, isInterface);
    }

    private InstructionBuilder invoke(int opcode, String owner, String method, String descriptor, boolean isInterface) {
        this.instructions.add((AbstractInsnNode)new MethodInsnNode(opcode, owner, method, descriptor, isInterface));
        this.currentType = VariableType.getTypeFromDescriptor(descriptor);
        return this;
    }

    public InstructionBuilder startIfEqual() {
        if (this.currentType != VariableType.BOOLEAN) {
            throw new IllegalArgumentException("You must supply a boolean before starting an if statement!");
        }
        LabelNode notCancelled = new LabelNode();
        this.instructions.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
        this.branchStack.add(notCancelled);
        this.currentType = VariableType.VOID;
        return this;
    }

    public InstructionBuilder startIfNotEqual() {
        if (this.currentType != VariableType.BOOLEAN) {
            throw new IllegalArgumentException("You must supply a boolean before starting an if statement!");
        }
        LabelNode notCancelled = new LabelNode();
        this.instructions.add((AbstractInsnNode)new JumpInsnNode(154, notCancelled));
        this.branchStack.add(notCancelled);
        this.currentType = VariableType.VOID;
        return this;
    }

    public InstructionBuilder endIf() {
        this.instructions.add((AbstractInsnNode)this.branchStack.removeLast());
        this.currentType = VariableType.VOID;
        return this;
    }

    public InstructionBuilder constantValue(float value) {
        if (value == 0.0f) {
            this.instructions.add((AbstractInsnNode)new InsnNode(11));
        }
        if (value == 1.0f) {
            this.instructions.add((AbstractInsnNode)new InsnNode(12));
        }
        if (value == 2.0f) {
            this.instructions.add((AbstractInsnNode)new InsnNode(13));
        } else {
            this.instructions.add((AbstractInsnNode)new LdcInsnNode((Object)Float.valueOf(value)));
        }
        this.currentType = VariableType.FLOAT;
        return this;
    }

    public InstructionBuilder reeturn() {
        if (this.currentType != VariableType.VOID && this.currentType != VariableType.getTypeFromDescriptor(this.methodNode.desc)) {
            throw new IllegalArgumentException("This type of variable cannot be returned for this method!");
        }
        this.instructions.add((AbstractInsnNode)new InsnNode(this.currentType.getOpcode(172)));
        this.currentType = VariableType.VOID;
        return this;
    }

    public InsnList finishList() {
        return this.instructions;
    }

    public InjectionHelper endCode() {
        return InjectionHelper.resume().setInstructions(this.instructions);
    }

    private int getFirstUnusedVariableIndex() {
        int maxIndex = -1;
        for (LocalVariableNode localVariable : this.methodNode.localVariables) {
            maxIndex = Math.max(maxIndex, localVariable.index);
        }
        return maxIndex + 1;
    }

    public static enum VariableType {
        VOID(Type.VOID_TYPE),
        BOOLEAN(Type.BOOLEAN_TYPE),
        CHAR(Type.CHAR_TYPE),
        BYTE(Type.BYTE_TYPE),
        SHORT(Type.SHORT_TYPE),
        INT(Type.INT_TYPE),
        FLOAT(Type.FLOAT_TYPE),
        LONG(Type.LONG_TYPE),
        DOUBLE(Type.DOUBLE_TYPE),
        OBJECT(Type.getObjectType((String)"Dummy"));

        private Type asmType;

        private VariableType(Type asmType) {
            this.asmType = asmType;
        }

        public int getOpcode(int opcode) {
            return this.asmType.getOpcode(opcode);
        }

        public static VariableType getTypeFromDescriptor(String descriptor) {
            Type type = Type.getReturnType((String)descriptor);
            int sort = type.getSort();
            if (sort == 10 || sort == 9) {
                return OBJECT;
            }
            return VariableType.fromASMType(type);
        }

        public static VariableType fromASMType(Type type) {
            for (VariableType variableType : VariableType.values()) {
                if (variableType.asmType != type) continue;
                return variableType;
            }
            return null;
        }
    }
}

