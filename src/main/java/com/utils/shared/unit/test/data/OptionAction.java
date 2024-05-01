package com.utils.shared.unit.test.data;

import java.lang.reflect.Field;

public interface OptionAction {
    void apply(Object object, int key, Field field);
}
