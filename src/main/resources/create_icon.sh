#!/bin/bash

# Create a beautiful journal icon with warm colors
# Using the app's theme - warm cream and brown

# Create 512x512 base icon (will be scaled down)
magick -size 512x512 xc:none \
  \( -size 420x520 xc:"#FFF8E1" \
     -background none \
     -gravity center \
     -extent 512x512 \
  \) \
  \( -size 420x520 xc:"#5D4037" \
     -background none \
     -gravity west \
     -extent 512x512 \
     -crop 30x520+0+0 +repage \
     -gravity west \
     -extent 512x512 \
  \) \
  -composite \
  \( -size 380x80 xc:"#5D4037" \
     -background none \
     -gravity center \
     -extent 512x512 \
  \) \
  -composite \
  -strokewidth 3 -stroke "#8D6E63" -fill none \
  -draw "rectangle 40,0 460,512" \
  -fill "#5D4037" -stroke none \
  -pointsize 280 -font DejaVu-Sans-Bold \
  -gravity center \
  -annotate +0-20 "J" \
  -fill "#E0E0E0" \
  -pointsize 40 -font DejaVu-Sans \
  -gravity south \
  -annotate +0+60 "My Journal" \
  icon_512.png

# Create various sizes
magick icon_512.png -resize 256x256 icon.png
magick icon_512.png -resize 128x128 icon_128.png
magick icon_512.png -resize 64x64 icon_64.png
magick icon_512.png -resize 48x48 icon_48.png
magick icon_512.png -resize 32x32 icon_32.png
magick icon_512.png -resize 16x16 icon_16.png

echo "✅ Icon created successfully!"
ls -lh icon*.png

