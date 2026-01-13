#!/bin/bash

echo "🚀 Setting up ShopEasy project..."

# Check for JDK
if type -p java > /dev/null; then
    echo "✅ Java version: $(java -version 2>&1 | head -n 1)"
else
    echo "❌ Java not found. Please install JDK 17."
    exit 1
fi

# Check for Android SDK
if [ -z "$ANDROID_HOME" ]; then
    echo "⚠️  ANDROID_HOME is not set. Ensuring you have the SDK installed is required."
fi

# Ensure gradle-wrapper is executable
chmod +x gradlew

# Check for google-services.json
if [ ! -f "app/google-services.json" ]; then
    echo "⚠️  app/google-services.json not found!"
    echo "Please download it from Firebase Console and place it in the app/ folder."
else
    echo "✅ google-services.json found."
fi

echo "📦 Building project..."
./gradlew build

echo "✨ Setup complete! You can now open the project in Android Studio."
