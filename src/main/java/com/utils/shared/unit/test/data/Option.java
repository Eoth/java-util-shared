package com.utils.shared.unit.test.data;

import com.utils.shared.unit.test.data.action.AssignNullAction;
import com.utils.shared.unit.test.data.action.AssignRandomValueAction;
import com.utils.shared.unit.test.data.action.AssignValueAction;
import com.utils.shared.unit.test.data.action.RenameAction;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Option {
    private final Map<String, OptionAction> options = new HashMap<>();
    private final Set<String> ignoreFields = new HashSet<>();

    public Option rename(String fieldName, String newFieldName) {
        options.put(fieldName, new RenameAction(newFieldName));
        return this;
    }

    public Option assignValue(String fieldName, Object value) {
        options.put(fieldName, new AssignValueAction(value));
        return this;
    }

    public Option assignValueChoiceInValues(String fieldName, Object... values) {
        options.put(fieldName, new AssignRandomValueAction(values));
        return this;
    }

    public Option assignNull(String... fieldNames) {
        for (String fieldName : fieldNames) {
            options.put(fieldName, new AssignNullAction());
        }
        return this;
    }

    public Option ignoreFields(String... fieldNames) {
        Collections.addAll(ignoreFields, fieldNames);
        return this;
    }

    public boolean shouldIgnoreField(String fieldName) {
        return ignoreFields.contains(fieldName);
    }

    public OptionAction getFieldAction(String fieldName) {
        return options.get(fieldName);
    }
}
