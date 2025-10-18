# Color Conversion Fix

## Issue
The application was crashing with `ArrayIndexOutOfBoundsException` when trying to render colors.

## Root Cause
The issue was in the color conversion between stored Long values (ARGB format) and Compose's Color object. Desktop Compose uses a different color representation than Android, and directly converting the color value was causing an invalid color space index.

## Solution
Modified `Models.kt` to properly extract ARGB components and create Color objects using component values:

```kotlin
fun Long.toComposeColor(): Color {
    // Extract ARGB components from Int
    val argb = this.toInt()
    val a = ((argb shr 24) and 0xFF) / 255f
    val r = ((argb shr 16) and 0xFF) / 255f
    val g = ((argb shr 8) and 0xFF) / 255f
    val b = (argb and 0xFF) / 255f
    return Color(red = r, green = g, blue = b, alpha = a)
}
```

## Status
✅ **FIXED** - Application now starts and runs without color-related crashes.

## Testing
Run `./test-app.sh` to verify the fix, or simply:
```bash
./gradlew run
```
