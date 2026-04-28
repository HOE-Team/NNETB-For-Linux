/*
 * SPDX-FileCopyrightText: ©2026 HOE Team
 * SPDX-License-Identifier: GPL-3.0-only
 *
 * Project: NNETB-For-Linux
 */

package utils

/**
 * 包管理器类型
 */
enum class PackageManagerType {
    APT,      // Debian/Ubuntu
    DNF,      // Fedora/RHEL
    PACMAN,   // Arch/Manjaro
    ZYPPER,   // openSUSE
    EMERGE,   // Gentoo
    NIX,      // NixOS
    UNKNOWN   // 未知或未安装
}

/**
 * 包信息类，用于存储不同包管理器对应的包名
 */
data class PackageInfo(
    val name: String,           // 包名
    val aptName: String? = null,      // APT包名
    val dnfName: String? = null,      // DNF包名
    val pacmanName: String? = null,   // Pacman包名
    val zypperName: String? = null,   // Zypper包名
    val emergeName: String? = null,   // Emerge包名
    val nixName: String? = null       // Nix包名
) {
    /**
     * 根据包管理器类型获取对应的包名
     */
    fun getPackageNameForManager(manager: PackageManagerType): String? {
        return when (manager) {
            PackageManagerType.APT -> aptName ?: name
            PackageManagerType.DNF -> dnfName ?: name
            PackageManagerType.PACMAN -> pacmanName ?: name
            PackageManagerType.ZYPPER -> zypperName ?: name
            PackageManagerType.EMERGE -> emergeName ?: name
            PackageManagerType.NIX -> nixName ?: name
            PackageManagerType.UNKNOWN -> null
        }
    }
}

/**
 * 包管理器工具类
 */
object PackageManagerUtils {
    /**
     * 检测当前系统的包管理器
     */
    fun detectPackageManager(): PackageManagerType {
        return try {
            val os = System.getProperty("os.name").lowercase()
            if (!os.contains("linux")) {
                return PackageManagerType.UNKNOWN
            }
            
            // 检查各种包管理器是否存在
            when {
                commandExists("apt-get") -> PackageManagerType.APT
                commandExists("dnf") -> PackageManagerType.DNF
                commandExists("yum") -> PackageManagerType.DNF  // yum是DNF的前身
                commandExists("pacman") -> PackageManagerType.PACMAN
                commandExists("zypper") -> PackageManagerType.ZYPPER
                commandExists("emerge") -> PackageManagerType.EMERGE
                commandExists("nix") -> PackageManagerType.NIX
                else -> PackageManagerType.UNKNOWN
            }
        } catch (e: Exception) {
            PackageManagerType.UNKNOWN
        }
    }
    
    /**
     * 检查命令是否存在
     */
    private fun commandExists(command: String): Boolean {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", "command -v $command"))
            process.waitFor()
            process.exitValue() == 0
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * 获取包管理器的安装命令
     */
    fun getInstallCommand(manager: PackageManagerType, packageName: String): String? {
        return when (manager) {
            PackageManagerType.APT -> "sudo apt-get install $packageName"
            PackageManagerType.DNF -> "sudo dnf install $packageName"
            PackageManagerType.PACMAN -> "sudo pacman -S $packageName"
            PackageManagerType.ZYPPER -> "sudo zypper install $packageName"
            PackageManagerType.EMERGE -> "sudo emerge $packageName"
            PackageManagerType.NIX -> "nix-env -i $packageName"
            PackageManagerType.UNKNOWN -> null
        }
    }
    
    /**
     * 获取包管理器的更新命令
     */
    fun getUpdateCommand(manager: PackageManagerType): String? {
        return when (manager) {
            PackageManagerType.APT -> "sudo apt-get update && sudo apt-get upgrade"
            PackageManagerType.DNF -> "sudo dnf update"
            PackageManagerType.PACMAN -> "sudo pacman -Syu"
            PackageManagerType.ZYPPER -> "sudo zypper update"
            PackageManagerType.EMERGE -> "sudo emerge --sync && sudo emerge -u world"
            PackageManagerType.NIX -> "nix-channel --update && nix-env -u"
            PackageManagerType.UNKNOWN -> null
        }
    }
    
    /**
     * 获取包管理器的搜索命令
     */
    fun getSearchCommand(manager: PackageManagerType, query: String): String? {
        return when (manager) {
            PackageManagerType.APT -> "apt-cache search $query"
            PackageManagerType.DNF -> "dnf search $query"
            PackageManagerType.PACMAN -> "pacman -Ss $query"
            PackageManagerType.ZYPPER -> "zypper search $query"
            PackageManagerType.EMERGE -> "emerge --search $query"
            PackageManagerType.NIX -> "nix-env -qaP | grep $query"
            PackageManagerType.UNKNOWN -> null
        }
    }
}

/**
 * 常用工具的包名映射
 */
object CommonPackages {
    // 系统监控工具
    val HWINFO = PackageInfo(
        name = "hwinfo",
        aptName = "hwinfo",
        dnfName = "hwinfo",
        pacmanName = "hwinfo",
        zypperName = "hwinfo"
    )
    
    val LSHW = PackageInfo(
        name = "lshw",
        aptName = "lshw",
        dnfName = "lshw",
        pacmanName = "lshw",
        zypperName = "lshw"
    )
    
    val DMIDECODE = PackageInfo(
        name = "dmidecode",
        aptName = "dmidecode",
        dnfName = "dmidecode",
        pacmanName = "dmidecode",
        zypperName = "dmidecode"
    )
    
    // 媒体工具
    val VLC = PackageInfo(
        name = "vlc",
        aptName = "vlc",
        dnfName = "vlc",
        pacmanName = "vlc",
        zypperName = "vlc"
    )
    
    val OBS_STUDIO = PackageInfo(
        name = "obs-studio",
        aptName = "obs-studio",
        dnfName = "obs-studio",
        pacmanName = "obs-studio",
        zypperName = "obs-studio"
    )
    
    val KODI = PackageInfo(
        name = "kodi",
        aptName = "kodi",
        dnfName = "kodi",
        pacmanName = "kodi",
        zypperName = "kodi"
    )
    
    val KDENLIVE = PackageInfo(
        name = "kdenlive",
        aptName = "kdenlive",
        dnfName = "kdenlive",
        pacmanName = "kdenlive",
        zypperName = "kdenlive"
    )
    
    val OPENSHOT = PackageInfo(
        name = "openshot",
        aptName = "openshot-qt",
        dnfName = "openshot",
        pacmanName = "openshot",
        zypperName = "openshot"
    )
    
    val SMPLAYER = PackageInfo(
        name = "smplayer",
        aptName = "smplayer",
        dnfName = "smplayer",
        pacmanName = "smplayer",
        zypperName = "smplayer"
    )
    
    // 开发工具
    val GIT = PackageInfo(
        name = "git",
        aptName = "git",
        dnfName = "git",
        pacmanName = "git",
        zypperName = "git"
    )
    
    // 文件工具
    val FILELIGHT = PackageInfo(
        name = "filelight",
        aptName = "filelight",
        dnfName = "filelight",
        pacmanName = "filelight",
        zypperName = "filelight"
    )
    
    // 其他工具
    val RUFUS = PackageInfo(
        name = "woeusb",  // Linux上的Rufus替代品
        aptName = "woeusb",
        dnfName = "woeusb",
        pacmanName = "woeusb-ng",
        zypperName = "woeusb"
    )
    
    // 驱动程序
    val NVIDIA_DRIVER = PackageInfo(
        name = "nvidia-driver",
        aptName = "nvidia-driver-535",
        dnfName = "akmod-nvidia",
        pacmanName = "nvidia",
        zypperName = "nvidia-computeG06"
    )
    
    val MESA_VULKAN_DRIVERS = PackageInfo(
        name = "mesa-vulkan-drivers",
        aptName = "mesa-vulkan-drivers",
        dnfName = "mesa-vulkan-drivers",
        pacmanName = "vulkan-radeon",
        zypperName = "Mesa-libvulkan"
    )
    
    // 新增工具
    val RSYNC = PackageInfo(
        name = "rsync",
        aptName = "rsync",
        dnfName = "rsync",
        pacmanName = "rsync",
        zypperName = "rsync"
    )
    
    val VIM = PackageInfo(
        name = "vim",
        aptName = "vim",
        dnfName = "vim-enhanced",
        pacmanName = "vim",
        zypperName = "vim"
    )
    
    val VSCODE = PackageInfo(
        name = "vscode",
        aptName = "code",
        dnfName = "code",
        pacmanName = "visual-studio-code-bin",
        zypperName = "code"
    )
    
    val WOEUSB = PackageInfo(
        name = "woeusb",
        aptName = "woeusb",
        dnfName = "woeusb",
        pacmanName = "woeusb-ng",
        zypperName = "woeusb"
    )
    
    /**
     * 根据工具名称获取包信息
     */
    fun getPackageInfo(toolName: String): PackageInfo? {
        return when (toolName.lowercase()) {
            "hwinfo", "hwinfo64" -> HWINFO
            "lshw" -> LSHW
            "dmidecode" -> DMIDECODE
            "vlc" -> VLC
            "obs studio", "obs-studio" -> OBS_STUDIO
            "kodi" -> KODI
            "kdenlive" -> KDENLIVE
            "openshot" -> OPENSHOT
            "smplayer" -> SMPLAYER
            "git" -> GIT
            "filelight" -> FILELIGHT
            "rufus" -> RUFUS
            "nvidia-driver" -> NVIDIA_DRIVER
            "mesa-vulkan-drivers" -> MESA_VULKAN_DRIVERS
            "rsync" -> RSYNC
            "vim" -> VIM
            "vscode" -> VSCODE
            "woeusb" -> WOEUSB
            else -> null
        }
    }
}
