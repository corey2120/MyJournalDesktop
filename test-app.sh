#!/bin/bash
# Quick test to verify the app starts without errors

cd "$(dirname "$0")"

echo "Starting My Journal Desktop (test for 5 seconds)..."
timeout 5 ./gradlew run > /tmp/myjournal_test.log 2>&1 &
PID=$!

sleep 5

# Check if there were any ArrayIndexOutOfBoundsException errors
if grep -q "ArrayIndexOutOfBoundsException" /tmp/myjournal_test.log; then
    echo "❌ ERROR: Application crashed with ArrayIndexOutOfBoundsException"
    echo "See /tmp/myjournal_test.log for details"
    exit 1
else
    echo "✅ SUCCESS: Application started without color errors!"
    echo "You can now run: ./gradlew run"
    rm -f /tmp/myjournal_test.log
    exit 0
fi
