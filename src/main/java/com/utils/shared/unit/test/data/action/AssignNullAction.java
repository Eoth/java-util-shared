package com.utils.shared.unit.test.data.action;

import com.utils.shared.util.ReflectionUtil;
import com.utils.shared.unit.test.data.OptionAction;

import java.lang.reflect.Field;

public class AssignNullAction implements OptionAction {
    @Override
    public void apply(final Object object, final int key, final Field field) {
        ReflectionUtil.setFieldValue(field, object, null);
    }
}
