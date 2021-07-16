rm -rf out-linux
/mnt/teradactyl/bin/OpenJDK11U-jdk_x64_linux_hotspot_11.0.11_9/jdk-11.0.11+9/bin/java -jar packr-all-4.0.0.jar \
    --platform linux64 \
    --jdk /mnt/teradactyl/bin/OpenJDK11U-jdk_x64_linux_hotspot_11.0.11_9.tar.gz \
    --executable KanakanStoriesVol1 \
    --classpath desktop/build/libs/desktop-1.0.jar \
    --mainclass com.orangeegames.suikorm.desktop.DesktopLauncher \
    --output out-linux

rm -rf out-windows
/mnt/teradactyl/bin/OpenJDK11U-jdk_x64_linux_hotspot_11.0.11_9/jdk-11.0.11+9/bin/java -jar packr-all-4.0.0.jar \
    --platform windows64 \
    --jdk /mnt/teradactyl/bin/OpenJDK11U-jdk_x64_windows_hotspot_11.0.11_9.zip \
    --executable KanakanStoriesVol1 \
    --classpath desktop/build/libs/desktop-1.0.jar \
    --mainclass com.orangeegames.suikorm.desktop.DesktopLauncher \
    --output out-windows

rm -rf out-mac
/mnt/teradactyl/bin/OpenJDK11U-jdk_x64_linux_hotspot_11.0.11_9/jdk-11.0.11+9/bin/java -jar packr-all-4.0.0.jar \
    --platform mac \
    --jdk /mnt/teradactyl/bin/OpenJDK11U-jdk_x64_mac_hotspot_11.0.11_9.tar.gz \
    --executable KanakanStoriesVol1 \
    --classpath desktop/build/libs/desktop-1.0.jar \
    --mainclass com.orangeegames.suikorm.desktop.DesktopLauncher \
    --output out-mac

rm -rf bundles
mkdir bundles
zip -r bundles/kanakan_stories_vol1_windows out-windows
tar -zcvf bundles/kanakan_stories_vol1_mac.tar.gz out-mac
tar -zcvf bundles/kanakan_stories_vol1_linux.tar.gz out-linux
cp desktop/build/libs/desktop-1.0.jar bundles/kanakan_stories_vol1.jar
