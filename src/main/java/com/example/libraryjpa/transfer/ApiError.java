package com.example.libraryjpa.transfer;

import java.util.Map;

public record ApiError(int status, String code, String message, Map<String, String> fields) {
}
