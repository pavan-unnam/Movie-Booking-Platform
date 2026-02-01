package com.booking.error;

import java.util.List;

public record ErrorResponse(Integer code,
                            String message,
                            List<FieldErrorDetail> details) {
}
