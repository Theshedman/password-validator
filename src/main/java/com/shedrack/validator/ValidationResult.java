package com.shedrack.validator;

import java.util.List;

public record ValidationResult(boolean isValid, List<String> messages) {
}
