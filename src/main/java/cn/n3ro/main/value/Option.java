/*
 * Decompiled with CFR 0_132.
 */
package cn.n3ro.main.value;

public class Option<V>
extends Value<V> {
    public Option(String name, V enabled) {
        super(name);
        this.setValue(enabled);
    }
}

