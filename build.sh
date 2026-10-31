#!/usr/bin/env bash
# 本地构建脚本
set -e
cd "$(dirname "$0")"

if [ ! -f gradlew ]; then
  echo "==> 生成 Gradle Wrapper"
  gradle wrapper --gradle-version 8.11.1
fi

echo "==> 编译 Debug APK"
./gradlew assembleDebug

echo ""
echo "✓ 产物：app/build/outputs/apk/debug/app-debug.apk"
