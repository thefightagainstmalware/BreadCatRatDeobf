/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package codes.biscuit.skyblockaddons.asm.utils;

import codes.biscuit.skyblockaddons.asm.utils.InstructionBuilder;
import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.asm.utils.TransformerField;
import codes.biscuit.skyblockaddons.asm.utils.TransformerMethod;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class InjectionHelper {
    private static final InjectionHelper INSTANCE = new InjectionHelper();
    private TransformerMethod method;
    private MethodNode methodNode;
    private InjectionPoint condition = new InjectionPoint();
    private Map<Integer, InjectionPoint> anchorConditions = new TreeMap<Integer, InjectionPoint>();
    private InjectionPosition injectionPosition;
    private int injectionOffset = 0;
    private InsnList instructions;
    private Consumer<AbstractInsnNode> instructionConsumer;

    public static boolean matches(MethodNode methodNode, TransformerMethod method) {
        return INSTANCE.clear().setMethodNode(methodNode).setMethod(method).matches();
    }

    public static InjectionPoint start() {
        return InjectionHelper.INSTANCE.condition;
    }

    public static InjectionHelper resume() {
        return INSTANCE;
    }

    public InjectionPoint addAnchorCondition(int offset) {
        if (offset == 0) {
            return this.condition;
        }
        InjectionPoint condition = new InjectionPoint();
        this.anchorConditions.put(offset, condition);
        return condition;
    }

    public InjectionHelper consumeForEach(Consumer<AbstractInsnNode> instructionConsumer) {
        this.setInstructionConsumer(instructionConsumer);
        return this;
    }

    public InstructionBuilder startCode() {
        return this.injectCodeBefore();
    }

    public InstructionBuilder injectCodeBefore() {
        return this.startCode(InjectionPosition.BEFORE);
    }

    public InstructionBuilder injectCodeAfter() {
        return this.startCode(InjectionPosition.AFTER);
    }

    private InstructionBuilder startCode(InjectionPosition injectionPosition) {
        this.setInjectionPosition(injectionPosition);
        return InstructionBuilder.start(this.methodNode);
    }

    public boolean finish() {
        if (this.instructionConsumer != null) {
            for (AbstractInsnNode abstractNode : this.methodNode.instructions) {
                this.instructionConsumer.accept(abstractNode);
            }
            return true;
        }
        if (this.instructions != null) {
            AbstractInsnNode injectionPointNode = this.findInjectionPoint();
            if (injectionPointNode == null) {
                return false;
            }
            if (this.injectionPosition == InjectionPosition.BEFORE) {
                this.methodNode.instructions.insertBefore(injectionPointNode, this.instructions);
            } else if (this.injectionPosition == InjectionPosition.AFTER) {
                this.methodNode.instructions.insert(injectionPointNode, this.instructions);
            }
            return true;
        }
        return false;
    }

    public boolean matches() {
        if (this.methodNode == null) {
            return false;
        }
        return this.method.matches(this.methodNode);
    }

    private AbstractInsnNode findInjectionPoint() {
        if (this.condition == null) {
            return null;
        }
        if (this.condition.type == MatchType.HEAD) {
            return this.methodNode.instructions.getFirst();
        }
        if (this.condition.type == MatchType.REGULAR) {
            for (AbstractInsnNode instruction : this.methodNode.instructions) {
                if (!this.matchesCondition(this.condition, instruction) || !this.matchesAnchorConditions(instruction) || (instruction = this.getOffsetInstruction(instruction, this.injectionOffset)) == null) continue;
                return instruction;
            }
        }
        return null;
    }

    private boolean matchesCondition(InjectionPoint injectionPoint, AbstractInsnNode instruction) {
        return injectionPoint.matches(instruction);
    }

    private boolean matchesAnchorConditions(AbstractInsnNode originalInstruction) {
        for (Map.Entry<Integer, InjectionPoint> entry : this.anchorConditions.entrySet()) {
            int conditionOffset = entry.getKey();
            AbstractInsnNode instruction = this.getOffsetInstruction(originalInstruction, conditionOffset);
            if (instruction == null) {
                return false;
            }
            InjectionPoint condition = entry.getValue();
            if (this.matchesCondition(condition, instruction)) continue;
            return false;
        }
        return true;
    }

    private AbstractInsnNode getOffsetInstruction(AbstractInsnNode instruction, int offset) {
        if (offset == 0) {
            return instruction;
        }
        AbstractInsnNode conditionInstruction = instruction;
        while (offset < 0) {
            if ((conditionInstruction = conditionInstruction.getPrevious()) == null) {
                return null;
            }
            ++offset;
        }
        while (offset > 0) {
            if ((conditionInstruction = conditionInstruction.getNext()) == null) {
                return null;
            }
            --offset;
        }
        return conditionInstruction;
    }

    public InjectionHelper clear() {
        this.method = null;
        this.methodNode = null;
        this.condition.clear();
        this.anchorConditions.clear();
        this.injectionPosition = null;
        this.injectionOffset = 0;
        return this;
    }

    private InjectionHelper setMethod(TransformerMethod method) {
        this.method = method;
        return this;
    }

    private InjectionHelper setMethodNode(MethodNode methodNode) {
        this.methodNode = methodNode;
        return this;
    }

    private InjectionHelper setCondition(InjectionPoint condition) {
        this.condition = condition;
        return this;
    }

    private InjectionHelper setAnchorConditions(Map<Integer, InjectionPoint> anchorConditions) {
        this.anchorConditions = anchorConditions;
        return this;
    }

    private InjectionHelper setInjectionPosition(InjectionPosition injectionPosition) {
        this.injectionPosition = injectionPosition;
        return this;
    }

    private InjectionHelper setInstructionConsumer(Consumer<AbstractInsnNode> instructionConsumer) {
        this.instructionConsumer = instructionConsumer;
        return this;
    }

    public InjectionHelper setInjectionOffset(int injectionOffset) {
        this.injectionOffset = injectionOffset;
        return this;
    }

    public InjectionHelper setInstructions(InsnList instructions) {
        this.instructions = instructions;
        return this;
    }

    public static enum InjectionPosition {
        BEFORE,
        AFTER;

    }

    public static enum MatchType {
        HEAD,
        REGULAR;

    }

    public static class InstructionMatcher<T> {
        private boolean enabled;
        private InstructionMatcherFunction<T> matchesFunction;
        private T value;

        public InstructionMatcher(InstructionMatcherFunction<T> matchesFunction) {
            this.matchesFunction = matchesFunction;
        }

        public boolean matches(AbstractInsnNode instruction) {
            return this.matchesFunction.matches(instruction, this.value);
        }

        public void setValue(T value) {
            this.value = value;
            this.enabled = true;
        }

        public void reset() {
            this.enabled = false;
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public InstructionMatcher<T> setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        private static interface InstructionMatcherFunction<T> {
            public boolean matches(AbstractInsnNode var1, T var2);
        }
    }

    public static class InjectionPoint {
        private MatchType type;
        private InstructionMatcher<TransformerClass> ownerMatcher = new InstructionMatcher<TransformerClass>((instruction, matchAgainst) -> {
            if (instruction instanceof FieldInsnNode) {
                return matchAgainst.getNameRaw().equals(((FieldInsnNode)instruction).owner);
            }
            if (instruction instanceof MethodInsnNode) {
                return matchAgainst.getNameRaw().equals(((MethodInsnNode)instruction).owner);
            }
            return false;
        });
        private InstructionMatcher<Integer> opcodeMatcher = new InstructionMatcher<Integer>((instruction, matchAgainst) -> matchAgainst.intValue() == instruction.getOpcode());
        private InstructionMatcher<TransformerField> fieldMatcher = new InstructionMatcher<TransformerField>((instruction, matchAgainst) -> instruction instanceof FieldInsnNode && matchAgainst.matches((FieldInsnNode)instruction));
        private InstructionMatcher<TransformerMethod> methodMatcher = new InstructionMatcher<TransformerMethod>((instruction, matchAgainst) -> instruction instanceof MethodInsnNode && matchAgainst.matches((MethodInsnNode)instruction));
        private InstructionMatcher<Integer> localVarMatcher = new InstructionMatcher<Integer>((instruction, matchAgainst) -> instruction instanceof VarInsnNode && matchAgainst == ((VarInsnNode)instruction).var);
        private Set<InstructionMatcher<?>> matchers = Sets.newHashSet((Object[])new InstructionMatcher[]{this.ownerMatcher, this.opcodeMatcher, this.fieldMatcher, this.methodMatcher, this.localVarMatcher});

        public void clear() {
            this.type = MatchType.REGULAR;
            this.matchers.forEach(InstructionMatcher::reset);
        }

        public boolean matches(AbstractInsnNode instruction) {
            for (InstructionMatcher<?> instructionMatcher : this.matchers) {
                if (!instructionMatcher.isEnabled() || instructionMatcher.matches(instruction)) continue;
                return false;
            }
            return true;
        }

        public InjectionHelper matchMethodHead() {
            this.type = MatchType.HEAD;
            return this.endCondition();
        }

        public InjectionPoint matchingOwner(TransformerClass clazz) {
            this.ownerMatcher.setValue(clazz);
            return this;
        }

        public InjectionPoint matchingMethod(TransformerMethod method) {
            this.methodMatcher.setValue(method);
            return this;
        }

        public InjectionPoint matchingField(TransformerField field) {
            this.fieldMatcher.setValue(field);
            return this;
        }

        public InjectionPoint matchingOpcode(int opcode) {
            this.opcodeMatcher.setValue(opcode);
            return this;
        }

        public InjectionPoint matchingLocalVarNumber(int localVarNumber) {
            this.localVarMatcher.setValue(localVarNumber);
            return this;
        }

        public InjectionHelper endCondition() {
            return INSTANCE;
        }

        public Set<InstructionMatcher<?>> getMatchers() {
            return this.matchers;
        }
    }
}

