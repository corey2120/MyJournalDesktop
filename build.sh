#!/bin/bash

# My Journal Desktop - Build Helper Script
# This script helps you build and test the application

set -e

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

show_help() {
    cat << EOF
My Journal Desktop - Build Helper

Usage: ./build.sh [command]

Commands:
    run         - Run the application directly (for testing)
    build       - Build the Gradle project
    flatpak     - Build and install Flatpak locally
    bundle      - Create a distributable .flatpak file
    clean       - Clean build directories
    setup       - Install required Flatpak runtimes
    help        - Show this help message

Examples:
    ./build.sh run          # Quick test
    ./build.sh flatpak      # Build and install Flatpak
    ./build.sh bundle       # Create shareable .flatpak file
EOF
}

check_flatpak() {
    if ! command -v flatpak-builder &> /dev/null; then
        echo "❌ flatpak-builder not found!"
        echo "Install it with: sudo dnf install flatpak flatpak-builder"
        exit 1
    fi
}

setup_runtimes() {
    echo "📦 Setting up Flatpak runtimes..."
    check_flatpak
    
    echo "Adding Flathub repository..."
    flatpak remote-add --if-not-exists flathub https://flathub.org/repo/flathub.flatpakrepo
    
    echo "Installing runtimes..."
    flatpak install -y flathub org.freedesktop.Platform//23.08 org.freedesktop.Sdk//23.08
    flatpak install -y flathub org.freedesktop.Sdk.Extension.openjdk17
    
    echo "✅ Runtimes installed!"
}

run_app() {
    echo "🚀 Running My Journal Desktop..."
    ./gradlew run
}

build_gradle() {
    echo "🔨 Building Gradle project..."
    ./gradlew build --no-daemon
    echo "✅ Build successful!"
}

build_flatpak() {
    echo "📦 Building and installing Flatpak..."
    check_flatpak
    flatpak-builder --user --install --force-clean build-dir com.cobrien.MyJournal.yml
    echo ""
    echo "✅ Flatpak installed!"
    echo "Run with: flatpak run com.cobrien.MyJournal"
}

create_bundle() {
    echo "📦 Creating Flatpak bundle..."
    check_flatpak
    
    echo "Building repository..."
    flatpak-builder --repo=repo --force-clean build-dir com.cobrien.MyJournal.yml
    
    echo "Creating bundle..."
    flatpak build-bundle repo myjournal.flatpak com.cobrien.MyJournal
    
    echo ""
    echo "✅ Bundle created: myjournal.flatpak"
    echo "Share this file with others!"
    echo "They can install it with: flatpak install myjournal.flatpak"
}

clean_build() {
    echo "🧹 Cleaning build directories..."
    rm -rf build/ build-dir/ repo/ .gradle/
    echo "✅ Clean complete!"
}

# Main script
case "${1:-help}" in
    run)
        run_app
        ;;
    build)
        build_gradle
        ;;
    flatpak)
        build_flatpak
        ;;
    bundle)
        create_bundle
        ;;
    setup)
        setup_runtimes
        ;;
    clean)
        clean_build
        ;;
    help|--help|-h)
        show_help
        ;;
    *)
        echo "Unknown command: $1"
        echo ""
        show_help
        exit 1
        ;;
esac
