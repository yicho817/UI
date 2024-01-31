// blur.rs

#pragma version(1)
#pragma rs java_package_name(com.example.ui.daily)

// Set the input allocation
rs_allocation gIn;

// Set the output allocation
rs_allocation gOut;

// Set the radius of the blur
float gRadius;

void root(const uchar4 *v_in, uchar4 *v_out, const void *usrData, uint32_t x, uint32_t y) {
    // Ignore edge pixels
    if (x <= gRadius || y <= gRadius || x >= rsAllocationGetDimX(gIn) - gRadius || y >= rsAllocationGetDimY(gIn) - gRadius) {
        *v_out = *v_in;
        return;
    }

    float4 sum = 0;
    for (int i = -gRadius; i <= gRadius; i++) {
        for (int j = -gRadius; j <= gRadius; j++) {
            uchar4 pixel = rsGetElementAt_uchar4(gIn, x + i, y + j);
            sum += rsUnpackColor8888(pixel);
        }
    }

    *v_out = rsPackColorTo8888(sum / ((2 * gRadius + 1) * (2 * gRadius + 1)));
}
