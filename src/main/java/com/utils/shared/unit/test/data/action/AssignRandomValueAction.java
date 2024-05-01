package com.utils.shared.unit.test.data.action;

import com.utils.shared.unit.test.data.OptionAction;
import com.utils.shared.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Random;

public class AssignRandomValueAction implements OptionAction {
    private static final Random RANDOM = new Random();
    private final Object[] values;

    public AssignRandomValueAction(final Object... values) {
        this.values = values;
    }
        @Override
        public void apply(final Object object, final int key, final Field field) {
            int index = RANDOM.nextInt(values.length);
            ReflectionUtil.setFieldValue(field, object, values[index]);
        }

}
