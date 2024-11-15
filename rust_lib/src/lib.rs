#[no_mangle]
pub extern "C" fn calculate_privacy_loss(precision: f32) -> f32 {
    100.0 / (precision + 1.0)  // Simple formula for demonstration purposes
}

